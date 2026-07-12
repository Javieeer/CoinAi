package com.coinai.api.paymentMethods.dto.request;

import com.coinai.api.paymentMethods.PaymentMethodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentMethodRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private PaymentMethodType type;

    @Size(max = 20)
    private String color;

    @Size(max = 100)
    private String icon;

}