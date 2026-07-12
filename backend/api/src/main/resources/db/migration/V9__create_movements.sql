CREATE TABLE movements (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    movement_type movement_type NOT NULL,

    amount NUMERIC(15,2) NOT NULL,

    description TEXT,

    movement_date TIMESTAMP NOT NULL,

    account_id UUID NOT NULL,
    
    payment_method_id UUID,

    category_id UUID NOT NULL,

    subcategory_id UUID,

    visibility movement_visibility NOT NULL DEFAULT 'PRIVATE',

    status movement_status NOT NULL DEFAULT 'CONFIRMED',

    source movement_source NOT NULL DEFAULT 'MANUAL',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    deleted_at TIMESTAMP,

    CONSTRAINT fk_movement_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_movement_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id),

    CONSTRAINT fk_movement_payment
        FOREIGN KEY (payment_method_id)
        REFERENCES payment_methods(id),

    CONSTRAINT fk_movement_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id),

    CONSTRAINT fk_movement_subcategory
        FOREIGN KEY (subcategory_id)
        REFERENCES subcategories(id)
);