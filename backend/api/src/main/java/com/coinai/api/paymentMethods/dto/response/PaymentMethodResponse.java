package com.coinai.api.paymentMethods.dto.response;

import com.coinai.api.paymentMethods.PaymentMethodType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {

    private UUID id;

    private String name;

    private PaymentMethodType type;

    private String color;

    private String icon;

}