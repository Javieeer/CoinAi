package com.coinai.api.paymentMethods.service.impl;

import com.coinai.api.common.exception.PaymentMethodAlreadyExistsException;
import com.coinai.api.common.exception.PaymentMethodNotFoundException;
import com.coinai.api.paymentMethods.dto.request.CreatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.request.UpdatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.response.PaymentMethodResponse;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import com.coinai.api.paymentMethods.mapper.PaymentMethodMapper;
import com.coinai.api.paymentMethods.repository.PaymentMethodRepository;
import com.coinai.api.paymentMethods.service.PaymentMethodService;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public PaymentMethodResponse create(CreatePaymentMethodRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (paymentMethodRepository.existsByUserIdAndNameIgnoreCase(
                user.getId(),
                request.getName()
        )) {
            throw new PaymentMethodAlreadyExistsException();
        }

        PaymentMethod paymentMethod = paymentMethodMapper.toEntity(request);

        paymentMethod.setUser(user);
        paymentMethod.setArchived(false);
        paymentMethod.setCreatedAt(LocalDateTime.now());
        paymentMethod.setUpdatedAt(LocalDateTime.now());

        paymentMethod = paymentMethodRepository.save(paymentMethod);

        return paymentMethodMapper.toResponse(paymentMethod);
    }

    @Override
    public List<PaymentMethodResponse> getAll() {

        User user = authenticatedUserService.getCurrentUser();

        return paymentMethodRepository.findByUserIdAndArchivedFalse(user.getId())
                .stream()
                .map(paymentMethodMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentMethodResponse update(
            UUID id,
            UpdatePaymentMethodRequest request
    ) {

        User user = authenticatedUserService.getCurrentUser();

        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new PaymentMethodNotFoundException());

        if (!paymentMethod.getUser().getId().equals(user.getId())) {
            throw new PaymentMethodNotFoundException();
        }

        if (!paymentMethod.getName().equalsIgnoreCase(request.getName())
                && paymentMethodRepository.existsByUserIdAndNameIgnoreCase(
                        user.getId(),
                        request.getName()
                )) {
            throw new PaymentMethodAlreadyExistsException();
        }

        paymentMethod.setName(request.getName());
        paymentMethod.setType(request.getType());
        paymentMethod.setColor(request.getColor());
        paymentMethod.setIcon(request.getIcon());
        paymentMethod.setUpdatedAt(LocalDateTime.now());

        paymentMethod = paymentMethodRepository.save(paymentMethod);

        return paymentMethodMapper.toResponse(paymentMethod);

    }

    @Override
    public void delete(UUID id) {

        User user = authenticatedUserService.getCurrentUser();

        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new PaymentMethodNotFoundException());

        if (!paymentMethod.getUser().getId().equals(user.getId())) {
            throw new PaymentMethodNotFoundException();
        }

        paymentMethod.setArchived(true);
        paymentMethod.setUpdatedAt(LocalDateTime.now());

        paymentMethodRepository.save(paymentMethod);

    }

}