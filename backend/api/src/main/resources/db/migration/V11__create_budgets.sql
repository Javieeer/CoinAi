CREATE TABLE budgets (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    family_id UUID,

    category_id UUID NOT NULL,

    amount NUMERIC(15,2) NOT NULL,

    month SMALLINT NOT NULL,

    year SMALLINT NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_budget_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_budget_family
        FOREIGN KEY (family_id)
        REFERENCES families(id),

    CONSTRAINT fk_budget_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);