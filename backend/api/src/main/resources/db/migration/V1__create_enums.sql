CREATE TYPE user_status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

CREATE TYPE family_role AS ENUM (
    'ADMIN',
    'MEMBER'
);

CREATE TYPE movement_type AS ENUM (
    'INCOME',
    'EXPENSE',
    'TRANSFER'
);

CREATE TYPE movement_status AS ENUM (
    'PENDING',
    'CONFIRMED',
    'CANCELLED'
);

CREATE TYPE movement_visibility AS ENUM (
    'PRIVATE',
    'SHARED'
);

CREATE TYPE movement_source AS ENUM (
    'MANUAL',
    'AI'
);

CREATE TYPE invitation_status AS ENUM (
    'PENDING',
    'ACCEPTED',
    'REJECTED',
    'EXPIRED'
);

CREATE TYPE goal_status AS ENUM (
    'ACTIVE',
    'COMPLETED'
);