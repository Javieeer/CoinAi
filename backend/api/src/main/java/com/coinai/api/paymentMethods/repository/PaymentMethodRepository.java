package com.coinai.api.paymentMethods.repository;

import com.coinai.api.paymentMethods.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    boolean existsByUserIdAndNameIgnoreCase(UUID userId, String name);

    List<PaymentMethod> findByUserIdAndArchivedFalse(UUID userId);

}