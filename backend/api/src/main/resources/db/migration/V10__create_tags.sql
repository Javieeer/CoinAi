CREATE TABLE tags (

    id UUID PRIMARY KEY,

    user_id UUID NOT NULL,

    name VARCHAR(100) NOT NULL,

    color VARCHAR(20),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_tags_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_tag_user_name
        UNIQUE (user_id, name)

);