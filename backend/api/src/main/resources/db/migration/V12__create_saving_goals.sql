CREATE TABLE goals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    family_id UUID,

    name VARCHAR(100) NOT NULL,

    description TEXT,

    target_amount NUMERIC(15,2) NOT NULL,

    current_amount NUMERIC(15,2) NOT NULL DEFAULT 0,

    target_date DATE NOT NULL,

    status goal_status NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_goal_family
        FOREIGN KEY (family_id)
        REFERENCES families(id)
);