CREATE TABLE ai_movements (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    movement_id UUID NOT NULL,

    model VARCHAR(100) NOT NULL,

    confidence NUMERIC(5,2) NOT NULL,

    email_subject TEXT,

    email_sender VARCHAR(255),

    raw_text TEXT,

    confirmed BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ai_movement
        FOREIGN KEY (movement_id)
        REFERENCES movements(id)
        ON DELETE CASCADE
);