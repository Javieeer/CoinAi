ALTER TABLE families
ADD COLUMN invite_code VARCHAR(20);

UPDATE families
SET invite_code = gen_random_uuid()::text;

ALTER TABLE families
ALTER COLUMN invite_code SET NOT NULL;

ALTER TABLE families
ADD CONSTRAINT uk_family_invite_code
UNIQUE (invite_code);

ALTER TABLE families
ADD COLUMN financial_mode financial_mode NOT NULL
DEFAULT 'INDIVIDUAL';

ALTER TABLE families
ADD COLUMN updated_at TIMESTAMP NOT NULL
DEFAULT CURRENT_TIMESTAMP;

DROP TABLE family_invitations;

ALTER TABLE family_members
ADD COLUMN left_at TIMESTAMP;

ALTER TABLE family_members
ADD COLUMN created_at TIMESTAMP NOT NULL
DEFAULT CURRENT_TIMESTAMP;