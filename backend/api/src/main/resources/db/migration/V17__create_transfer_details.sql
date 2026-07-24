CREATE TABLE transfer_details (
    movement_id UUID PRIMARY KEY,

    origin_payment_method UUID NOT NULL,

    destination_payment_method UUID NOT NULL,

    CONSTRAINT fk_transfer_movement
        FOREIGN KEY (movement_id)
        REFERENCES movements(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_transfer_origin
        FOREIGN KEY (origin_payment_method)
        REFERENCES payment_methods(id),

    CONSTRAINT fk_transfer_destination
        FOREIGN KEY (destination_payment_method)
        REFERENCES payment_methods(id)
);