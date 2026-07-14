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

CREATE TYPE payment_method_type AS ENUM (
    'CASH',
    'DEBIT_CARD',
    'CREDIT_CARD',
    'BANK_TRANSFER',
    'PSE',
    'QR',
    'NFC',
    'DIGITAL_WALLET'
);

CREATE TYPE account_type AS ENUM (
    'CASH',
    'BANK',
    'CREDIT_CARD',
    'SAVINGS',
    'CRYPTO',
    'INVESTMENT'
);

CREATE TYPE financial_mode AS ENUM (
    'INDIVIDUAL',
    'SHARED'
);

CREATE TYPE goal_status AS ENUM (
    'ACTIVE',
    'COMPLETED',
    'CANCELLED'
);