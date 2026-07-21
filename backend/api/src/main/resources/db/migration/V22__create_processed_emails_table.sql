CREATE TABLE processed_emails (

    id UUID PRIMARY KEY,

    gmail_message_id VARCHAR(100) NOT NULL UNIQUE,

    bank_type VARCHAR(50) NOT NULL,

    processed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);