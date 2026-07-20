CREATE TABLE oauth_states (

    id BIGSERIAL PRIMARY KEY,

    state VARCHAR(255) NOT NULL UNIQUE,

    user_id UUID NOT NULL,

    expires_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_oauth_state_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE

);