package com.coinai.api.paymentMethods.service.impl;

import com.coinai.api.paymentMethods.PaymentMethodType;
import com.coinai.api.paymentMethods.dto.request.CreatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.request.UpdatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.response.PaymentMethodResponse;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import com.coinai.api.paymentMethods.exception.PaymentMethodAlreadyExistsException;
import com.coinai.api.paymentMethods.exception.PaymentMethodNotFoundException;
import com.coinai.api.paymentMethods.mapper.PaymentMethodMapper;
import com.coinai.api.paymentMethods.repository.PaymentMethodRepository;
import com.coinai.api.security.service.AuthenticatedUserService;
import com.coinai.api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceImplTest {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private PaymentMethodMapper paymentMethodMapper;

    @Mock
    private AuthenticatedUserService authenticatedUserService;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @Test
    void shouldCreatePaymentMethodSuccessfully() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        CreatePaymentMethodRequest request = new CreatePaymentMethodRequest();
        request.setName("Nequi");
        request.setType(PaymentMethodType.DEBIT_CARD);
        request.setColor("#000000");
        request.setIcon("credit-card");

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .name("Nequi")
                .type(PaymentMethodType.DEBIT_CARD)
                .color("#000000")
                .icon("credit-card")
                .user(user)
                .build();

        PaymentMethodResponse response = PaymentMethodResponse.builder()
                .name("Nequi")
                .type(PaymentMethodType.DEBIT_CARD)
                .color("#000000")
                .icon("credit-card")
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);
        when(paymentMethodRepository.existsByUserIdAndNameIgnoreCase(user.getId(), "Nequi"))
                .thenReturn(false);
        when(paymentMethodMapper.toEntity(request)).thenReturn(paymentMethod);
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);
        when(paymentMethodMapper.toResponse(paymentMethod)).thenReturn(response);

        PaymentMethodResponse result = paymentMethodService.create(request);

        assertEquals("Nequi", result.getName());
        assertEquals(PaymentMethodType.DEBIT_CARD, result.getType());

        verify(paymentMethodRepository).save(any(PaymentMethod.class));
    }

    @Test
    void shouldThrowWhenPaymentMethodAlreadyExists() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        CreatePaymentMethodRequest request = new CreatePaymentMethodRequest();
        request.setName("Nequi");
        request.setType(PaymentMethodType.DEBIT_CARD);

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);
        when(paymentMethodRepository.existsByUserIdAndNameIgnoreCase(user.getId(), "Nequi"))
                .thenReturn(true);

        assertThrows(
                PaymentMethodAlreadyExistsException.class,
                () -> paymentMethodService.create(request)
        );

        verify(paymentMethodRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllPaymentMethods() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        List<PaymentMethod> methods = List.of(
                PaymentMethod.builder().name("Banco").build(),
                PaymentMethod.builder().name("Nequi").build()
        );

        List<PaymentMethodResponse> responses = List.of(
                PaymentMethodResponse.builder().name("Banco").build(),
                PaymentMethodResponse.builder().name("Nequi").build()
        );

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);
        when(paymentMethodRepository.findByUserIdAndArchivedFalse(user.getId()))
                .thenReturn(methods);

        when(paymentMethodMapper.toResponse(methods.get(0)))
                .thenReturn(responses.get(0));

        when(paymentMethodMapper.toResponse(methods.get(1)))
                .thenReturn(responses.get(1));

        List<PaymentMethodResponse> result = paymentMethodService.getAll();

        assertEquals(2, result.size());
        assertEquals("Banco", result.get(0).getName());
        assertEquals("Nequi", result.get(1).getName());
    }

    @Test
    void shouldUpdatePaymentMethodSuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID paymentMethodId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(paymentMethodId)
                .name("Nequi")
                .type(PaymentMethodType.DEBIT_CARD)
                .color("#000000")
                .icon("credit-card")
                .user(user)
                .build();

        UpdatePaymentMethodRequest request = new UpdatePaymentMethodRequest();
        request.setName("Bancolombia");
        request.setType(PaymentMethodType.CREDIT_CARD);
        request.setColor("#FFFFFF");
        request.setIcon("bank");

        PaymentMethodResponse response = PaymentMethodResponse.builder()
                .id(paymentMethodId)
                .name("Bancolombia")
                .type(PaymentMethodType.CREDIT_CARD)
                .color("#FFFFFF")
                .icon("bank")
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);

        when(paymentMethodRepository.findById(paymentMethodId))
                .thenReturn(Optional.of(paymentMethod));

        when(paymentMethodRepository.existsByUserIdAndNameIgnoreCase(userId, "Bancolombia"))
                .thenReturn(false);

        when(paymentMethodRepository.save(paymentMethod))
                .thenReturn(paymentMethod);

        when(paymentMethodMapper.toResponse(paymentMethod))
                .thenReturn(response);

        PaymentMethodResponse result =
                paymentMethodService.update(paymentMethodId, request);

        assertEquals("Bancolombia", result.getName());
        assertEquals(PaymentMethodType.CREDIT_CARD, result.getType());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingPaymentMethod() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);

        when(paymentMethodRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentMethodNotFoundException.class,
                () -> paymentMethodService.update(
                        UUID.randomUUID(),
                        new UpdatePaymentMethodRequest()
                )
        );
    }

    @Test
    void shouldArchivePaymentMethodSuccessfully() {

        UUID userId = UUID.randomUUID();
        UUID paymentMethodId = UUID.randomUUID();

        User user = User.builder()
                .id(userId)
                .build();

        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(paymentMethodId)
                .archived(false)
                .user(user)
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);

        when(paymentMethodRepository.findById(paymentMethodId))
                .thenReturn(Optional.of(paymentMethod));

        paymentMethodService.delete(paymentMethodId);

        assertTrue(paymentMethod.isArchived());

        verify(paymentMethodRepository).save(paymentMethod);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingPaymentMethod() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(authenticatedUserService.getCurrentUser()).thenReturn(user);

        when(paymentMethodRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentMethodNotFoundException.class,
                () -> paymentMethodService.delete(UUID.randomUUID())
        );
    }

}