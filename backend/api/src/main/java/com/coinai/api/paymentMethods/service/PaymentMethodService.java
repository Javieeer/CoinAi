package com.coinai.api.paymentMethods.service;

import com.coinai.api.paymentMethods.dto.request.CreatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.request.UpdatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.response.PaymentMethodResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService {

    PaymentMethodResponse create(CreatePaymentMethodRequest request);

    List<PaymentMethodResponse> getAll();

    PaymentMethodResponse update(UUID id, UpdatePaymentMethodRequest request);

    void delete(UUID id);

}