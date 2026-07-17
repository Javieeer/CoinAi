CREATE TABLE google_credentials (

    id BIGSERIAL PRIMARY KEY,

    user_id UUID NOT NULL UNIQUE,

    refresh_token TEXT NOT NULL,

    access_token TEXT,

    expires_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_google_credentials_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE

);