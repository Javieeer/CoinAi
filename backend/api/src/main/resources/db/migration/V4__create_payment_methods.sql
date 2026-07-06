CREATE TABLE payment_methods (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    name VARCHAR(100) NOT NULL,

    type VARCHAR(50) NOT NULL,

    color VARCHAR(20),

    icon VARCHAR(100),

    initial_balance NUMERIC(15,2) NOT NULL DEFAULT 0,

    current_balance NUMERIC(15,2) NOT NULL DEFAULT 0,

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_method_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE credit_card_details (
    payment_method_id UUID PRIMARY KEY,

    credit_limit NUMERIC(15,2) NOT NULL,

    available_credit NUMERIC(15,2) NOT NULL,

    current_debt NUMERIC(15,2) NOT NULL DEFAULT 0,

    closing_day SMALLINT NOT NULL,

    due_day SMALLINT NOT NULL,

    CONSTRAINT fk_credit_card_payment
        FOREIGN KEY (payment_method_id)
        REFERENCES payment_methods(id)
        ON DELETE CASCADE
);