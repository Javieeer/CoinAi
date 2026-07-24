CREATE TABLE movement_tags (

    movement_id UUID NOT NULL,

    tag_id UUID NOT NULL,

    PRIMARY KEY (
        movement_id,
        tag_id
    ),

    CONSTRAINT fk_movement_tags_movement
        FOREIGN KEY (movement_id)
        REFERENCES movements(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_movement_tags_tag
        FOREIGN KEY (tag_id)
        REFERENCES tags(id)
        ON DELETE CASCADE

);