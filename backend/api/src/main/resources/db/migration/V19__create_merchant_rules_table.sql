CREATE TABLE merchant_rules (

    id BIGSERIAL PRIMARY KEY,
    
    user_id UUID NOT NULL,
    
    raw_merchant VARCHAR(255) NOT NULL,

    normalized_merchant VARCHAR(255) NOT NULL,

    category_id UUID NOT NULL,

    confirmed_by_user BOOLEAN NOT NULL DEFAULT FALSE,

    times_used INTEGER NOT NULL DEFAULT 1,

    last_used TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_merchant_rule_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_merchant_rule_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE SET NULL,

    CONSTRAINT uk_merchant_rule_user_raw
        UNIQUE (user_id, raw_merchant)

);

CREATE INDEX idx_merchant_rule_user
    ON merchant_rules(user_id);

CREATE INDEX idx_merchant_rule_raw
    ON merchant_rules(raw_merchant);

CREATE INDEX idx_merchant_rule_normalized
    ON merchant_rules(normalized_merchant);