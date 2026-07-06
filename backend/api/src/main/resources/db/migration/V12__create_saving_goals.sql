CREATE TABLE saving_goals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    name VARCHAR(100) NOT NULL,

    target_amount NUMERIC(15,2) NOT NULL,

    current_amount NUMERIC(15,2) NOT NULL DEFAULT 0,

    deadline DATE,

    status goal_status NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_goal_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);