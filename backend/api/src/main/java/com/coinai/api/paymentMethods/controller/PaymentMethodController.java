package com.coinai.api.paymentMethods.controller;

import com.coinai.api.paymentMethods.dto.request.CreatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.request.UpdatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.response.PaymentMethodResponse;
import com.coinai.api.paymentMethods.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodResponse create(
            @Valid @RequestBody CreatePaymentMethodRequest request
    ) {
        return paymentMethodService.create(request);
    }

    @GetMapping
    public List<PaymentMethodResponse> getAll() {
        return paymentMethodService.getAll();
    }

    @PutMapping("/{id}")
    public PaymentMethodResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePaymentMethodRequest request
    ) {
        return paymentMethodService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        paymentMethodService.delete(id);
    }
}