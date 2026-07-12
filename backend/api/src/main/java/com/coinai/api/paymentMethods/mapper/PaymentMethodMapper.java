package com.coinai.api.paymentMethods.mapper;

import com.coinai.api.paymentMethods.dto.request.CreatePaymentMethodRequest;
import com.coinai.api.paymentMethods.dto.response.PaymentMethodResponse;
import com.coinai.api.paymentMethods.entity.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethod toEntity(CreatePaymentMethodRequest request);

    PaymentMethodResponse toResponse(PaymentMethod paymentMethod);

}