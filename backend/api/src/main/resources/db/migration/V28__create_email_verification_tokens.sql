CREATE TABLE email_verification_tokens (

    id UUID PRIMARY KEY,

    token VARCHAR(255) NOT NULL UNIQUE,

    user_id UUID NOT NULL,

    created_at TIMESTAMP NOT NULL,

    expires_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_email_verification_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE

);