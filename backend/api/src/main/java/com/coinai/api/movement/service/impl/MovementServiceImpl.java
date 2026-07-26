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
import com.coinai.api.movement.filter.MovementFilterRequest;
import com.coinai.api.movement.filter.MovementSpecification;
import com.coinai.api.movement.mapper.MovementMapper;
import com.coinai.api.movement.repository.MovementRepository;
import com.coinai.api.movement.service.MovementService;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import com.coinai.api.paymentMethods.repository.PaymentMethodRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.subcategory.entity.Subcategory;
import com.coinai.api.subcategory.repository.SubcategoryRepository;
import com.coinai.api.tag.entity.Tag;
import com.coinai.api.tag.exception.TagNotFoundException;
import com.coinai.api.tag.repository.TagRepository;
import com.coinai.api.user.entity.User;
import com.coinai.api.notification.service.BudgetAlertService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final TagRepository tagRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final BudgetAlertService budgetAlertService;

    @Override
    @Transactional
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

        Set<Tag> tags = getTags(
                request.getTagIds(),
                user.getId()
        );

        Movement movement = movementMapper.toEntity(request);

        movement.setUser(user);
        movement.setAccount(account);
        movement.setDestinationAccount(destinationAccount);
        movement.setCategory(category);
        movement.setPaymentMethod(paymentMethod);
        movement.setSubcategory(subcategory);
        movement.setTags(tags);

        movement.setCreatedAt(LocalDateTime.now());
        movement.setUpdatedAt(LocalDateTime.now());

        movement = movementRepository.save(movement);

        applyMovement(movement);

        budgetAlertService.checkBudgetAlerts(movement);

        return movementMapper.toResponse(movement);

    }

    @Override
    public List<MovementResponse> findAll(
        MovementFilterRequest filter
    ) {

        User user = authenticatedUserService.getCurrentUser();

        filter.setUserId(user.getId());

        Specification<Movement> specification =
                MovementSpecification.withFilters(filter);

        List<Movement> movements =
                movementRepository.findAll(specification);

        movements.sort(
                (a, b) -> b.getMovementDate().compareTo(a.getMovementDate())
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


    private Set<Tag> getTags(
        Set<UUID> tagIds,
        UUID userId
    ) {

        if (tagIds == null || tagIds.isEmpty()) {
                return new HashSet<>();
        }

        Set<Tag> tags = new HashSet<>();

        for (UUID tagId : tagIds) {

                Tag tag = tagRepository
                        .findByIdAndUserId(tagId, userId)
                        .orElseThrow(TagNotFoundException::new);

                tags.add(tag);

        }

        return tags;

    }

    @Override
    @Transactional
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

        Set<Tag> tags = getTags(
                request.getTagIds(),
                user.getId()
        );

        revertMovement(movement);

        movement.setMovementType(request.getMovementType());
        movement.setAmount(request.getAmount());
        movement.setDescription(request.getDescription());
        movement.setMovementDate(request.getMovementDate());

        movement.setAccount(account);
        movement.setDestinationAccount(destinationAccount);

        movement.setCategory(category);
        movement.setSubcategory(subcategory);

        movement.setPaymentMethod(paymentMethod);
        movement.setTags(tags);

        movement.setVisibility(request.getVisibility());
        movement.setStatus(request.getStatus());
        movement.setSource(request.getSource());

        movement.setUpdatedAt(LocalDateTime.now());

        movement = movementRepository.save(movement);

        applyMovement(movement);
        return movementMapper.toResponse(movement);

    }

    @Override
    @Transactional
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        Movement movement = movementRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(MovementNotFoundException::new);

        revertMovement(movement);

        movementRepository.delete(movement);

    }

    private void applyMovement(Movement movement) {

        switch (movement.getMovementType()) {

                case INCOME -> {

                        Account account = movement.getAccount();

                        account.setBalance(
                                account.getBalance().add(movement.getAmount())
                        );

                        accountRepository.save(account);

                }

                case EXPENSE -> {

                        Account account = movement.getAccount();

                        account.setBalance(
                                account.getBalance().subtract(movement.getAmount())
                        );

                        accountRepository.save(account);

                }

                case TRANSFER -> {

                        Account origin = movement.getAccount();

                        Account destination = movement.getDestinationAccount();

                        if (destination == null) {
                                throw new MovementNotFoundException();
                        }

                        origin.setBalance(
                                origin.getBalance().subtract(movement.getAmount())
                        );

                        destination.setBalance(
                                destination.getBalance().add(movement.getAmount())
                        );

                        accountRepository.save(origin);
                        accountRepository.save(destination);

                }

        }

    }

    private void revertMovement(Movement movement) {

        switch (movement.getMovementType()) {

                case INCOME -> {

                        Account account = movement.getAccount();

                        account.setBalance(
                                account.getBalance().subtract(movement.getAmount())
                        );

                        accountRepository.save(account);

                }

                case EXPENSE -> {

                        Account account = movement.getAccount();

                        account.setBalance(
                                account.getBalance().add(movement.getAmount())
                        );

                        accountRepository.save(account);

                }

                case TRANSFER -> {

                        Account origin = movement.getAccount();
                        Account destination = movement.getDestinationAccount();

                        if (destination == null) {
                                throw new MovementNotFoundException();
                        }
                        
                        origin.setBalance(
                                origin.getBalance().add(movement.getAmount())
                        );

                        destination.setBalance(
                                destination.getBalance().subtract(movement.getAmount())
                        );

                        accountRepository.save(origin);
                        accountRepository.save(destination);

                }

        }

    }


}