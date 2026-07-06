CREATE TABLE movement_tags (
    movement_id UUID NOT NULL,

    tag_id UUID NOT NULL,

    PRIMARY KEY (movement_id, tag_id)
);