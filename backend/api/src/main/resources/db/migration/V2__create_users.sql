CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    profile_picture TEXT,

    preferred_currency VARCHAR(3) NOT NULL DEFAULT 'COP',

    timezone VARCHAR(50) NOT NULL DEFAULT 'America/Bogota',

    language VARCHAR(10) NOT NULL DEFAULT 'es',

    email_verified BOOLEAN NOT NULL DEFAULT FALSE,

    status user_status NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);