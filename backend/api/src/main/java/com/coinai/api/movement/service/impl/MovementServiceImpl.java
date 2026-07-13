package com.coinai.api.movement.service.impl;

import com.coinai.api.account.entity.Account;
import com.coinai.api.account.repository.AccountRepository;
import com.coinai.api.category.entity.Category;
import com.coinai.api.category.repository.CategoryRepository;
import com.coinai.api.movement.dto.request.CreateMovementRequest;
import com.coinai.api.movement.dto.request.UpdateMovementRequest;
import com.coinai.api.movement.dto.response.MovementResponse;
import com.coinai.api.movement.entity.Movement;
import com.coinai.api.movement.exception.MovementNotFoundException;
import com.coinai.api.movement.mapper.MovementMapper;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.movement.service.MovementService;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import com.coinai.api.paymentMethods.repository.PaymentMethodRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.subcategory.entity.Subcategory;
import com.coinai.api.subcategory.repository.SubcategoryRepository;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final SubcategoryRepository subcategoryRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public MovementResponse create(CreateMovementRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        Account account = accountRepository
                .findByIdAndUserId(request.getAccountId(), user.getId())
                .orElseThrow(MovementNotFoundException::new);

        Account destinationAccount = null;

        if (request.getDestinationAccountId() != null) {

            destinationAccount = accountRepository
                    .findByIdAndUserId(
                            request.getDestinationAccountId(),
                            user.getId()
                    )
                    .orElseThrow(MovementNotFoundException::new);

        }

        Category category = categoryRepository
                .findByIdAndUserId(request.getCategoryId(), user.getId())
                .orElseThrow(MovementNotFoundException::new);

        PaymentMethod paymentMethod = null;

        if (request.getPaymentMethodId() != null) {

            paymentMethod = paymentMethodRepository
                    .findByIdAndUserId(
                            request.getPaymentMethodId(),
                            user.getId()
                    )
                    .orElseThrow(MovementNotFoundException::new);

        }

        Subcategory subcategory = null;

        if (request.getSubcategoryId() != null) {

            subcategory = subcategoryRepository
                    .findByIdAndUserId(
                            request.getSubcategoryId(),
                            user.getId()
                    )
                    .orElseThrow(MovementNotFoundException::new);

        }

        Movement movement = movementMapper.toEntity(request);

        movement.setUser(user);
        movement.setAccount(account);
        movement.setDestinationAccount(destinationAccount);
        movement.setCategory(category);
        movement.setPaymentMethod(paymentMethod);
        movement.setSubcategory(subcategory);

        movement.setCreatedAt(LocalDateTime.now());
        movement.setUpdatedAt(LocalDateTime.now());

        movement = movementRepository.save(movement);

        return movementMapper.toResponse(movement);

    }

    @Override
    public List<MovementResponse> findAll() {

        User user = authenticatedUserService.getCurrentUser();

        List<Movement> movements =
                movementRepository.findByUserIdOrderByMovementDateDesc(
                        user.getId()
                );

        return movementMapper.toResponseList(movements);

    }

    private Account getAccount(UUID id, UUID userId) {

        return accountRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(MovementNotFoundException::new);

    }

    private Category getCategory(UUID id, UUID userId) {

        return categoryRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(MovementNotFoundException::new);

    }

    private PaymentMethod getPaymentMethod(UUID id, UUID userId) {

        return paymentMethodRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(MovementNotFoundException::new);

    }

    private Subcategory getSubcategory(UUID id, UUID userId) {

        return subcategoryRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(MovementNotFoundException::new);

    }

    @Override
    public MovementResponse update(
            UUID id,
            UpdateMovementRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        Movement movement = movementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(MovementNotFoundException::new);

        Account account = getAccount(
                request.getAccountId(),
                user.getId()
        );

        Account destinationAccount = null;

        if (request.getDestinationAccountId() != null) {
            destinationAccount = getAccount(
                    request.getDestinationAccountId(),
                    user.getId()
            );
        }

        Category category = getCategory(
                request.getCategoryId(),
                user.getId()
        );

        PaymentMethod paymentMethod = null;

        if (request.getPaymentMethodId() != null) {
            paymentMethod = getPaymentMethod(
                    request.getPaymentMethodId(),
                    user.getId()
            );
        }

        Subcategory subcategory = null;

        if (request.getSubcategoryId() != null) {
            subcategory = getSubcategory(
                    request.getSubcategoryId(),
                    user.getId()
            );
        }

        movement.setMovementType(request.getMovementType());
        movement.setAmount(request.getAmount());
        movement.setDescription(request.getDescription());
        movement.setMovementDate(request.getMovementDate());

        movement.setAccount(account);
        movement.setDestinationAccount(destinationAccount);

        movement.setCategory(category);
        movement.setSubcategory(subcategory);

        movement.setPaymentMethod(paymentMethod);

        movement.setVisibility(request.getVisibility());
        movement.setStatus(request.getStatus());
        movement.setSource(request.getSource());

        movement.setUpdatedAt(LocalDateTime.now());

        movement = movementRepository.save(movement);

        return movementMapper.toResponse(movement);

    }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Movement movement = movementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(MovementNotFoundException::new);

        movementRepository.delete(movement);

    }

}