package com.coinai.api.movement.filter;

import com.coinai.api.movement.entity.Movement;
import org.springframework.data.jpa.domain.Specification;

public class MovementSpecification {

    private MovementSpecification() {
    }

    public static Specification<Movement> withFilters(
            MovementFilterRequest filter
    ) {

        return Specification
                .where(hasUser(filter.getUserId()))
                .and(hasMovementType(filter.getMovementType()))
                .and(hasCategory(filter.getCategoryId()))
                .and(hasPaymentMethod(filter.getPaymentMethodId()))
                .and(hasTag(filter.getTagId()))
                .and(hasStartDate(filter.getStartDate()))
                .and(hasEndDate(filter.getEndDate()));

    }

    private static Specification<Movement> hasUser(java.util.UUID userId) {

        return (root, query, cb) ->
                userId == null
                        ? null
                        : cb.equal(root.get("user").get("id"), userId);

    }

    private static Specification<Movement> hasMovementType(
            com.coinai.api.movement.MovementType movementType
    ) {

        return (root, query, cb) ->
                movementType == null
                        ? null
                        : cb.equal(root.get("movementType"), movementType);

    }

    private static Specification<Movement> hasCategory(
            java.util.UUID categoryId
    ) {

        return (root, query, cb) ->
                categoryId == null
                        ? null
                        : cb.equal(root.get("category").get("id"), categoryId);

    }

    private static Specification<Movement> hasPaymentMethod(
            java.util.UUID paymentMethodId
    ) {

        return (root, query, cb) ->
                paymentMethodId == null
                        ? null
                        : cb.equal(root.get("paymentMethod").get("id"), paymentMethodId);

    }

    private static Specification<Movement> hasTag(
            java.util.UUID tagId
    ) {

        return (root, query, cb) -> {

            if (tagId == null) {
                return null;
            }

            return cb.equal(
                    root.join("tags").get("id"),
                    tagId
            );

        };

    }

    private static Specification<Movement> hasStartDate(
            java.time.LocalDate startDate
    ) {

        return (root, query, cb) ->
                startDate == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                                root.get("movementDate"),
                                startDate.atStartOfDay()
                        );

    }

    private static Specification<Movement> hasEndDate(
            java.time.LocalDate endDate
    ) {

        return (root, query, cb) ->
                endDate == null
                        ? null
                        : cb.lessThanOrEqualTo(
                                root.get("movementDate"),
                                endDate.atTime(23, 59, 59)
                        );

    }

}