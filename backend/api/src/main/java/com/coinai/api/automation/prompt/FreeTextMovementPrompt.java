package com.coinai.api.automation.prompt;

import org.springframework.stereotype.Component;

@Component
public class FreeTextMovementPrompt {

    public String build(String text) {

        return """
            Eres un asistente financiero.

            Analiza el texto escrito por un usuario y responde ÚNICAMENTE un JSON válido.

            No escribas explicaciones.
            No uses markdown.
            No uses ```json.
            No agregues texto antes ni después del JSON.

            El formato debe ser exactamente:

            {
              "bank": null,
              "notificationType": null,
              "movementType": "...",
              "amount": 0,
              "currency": "COP",
              "date": null,
              "rawMerchant": "...",
              "merchant": "...",
              "recipient": null,
              "paymentMethod": "...",
              "account": null,
              "cardLastDigits": null,
              "accountLastDigits": null,
              "installmentPurchase": false,
              "installments": null,
              "category": "...",
              "needsUserInput": false,
              "missingFields": [],
              "confidence": "HIGH"
            }

            Reglas:

            - movementType solo puede ser:
              EXPENSE
              INCOME
              TRANSFER

            - amount debe contener únicamente el número.

            - currency usa siempre COP salvo que el usuario indique otra.

            - paymentMethod debe ser un nombre legible.
              Ejemplos:
              Nequi
              Davivienda
              Bancolombia
              Efectivo
              Tarjeta Crédito
              Tarjeta Débito

            - merchant es el comercio.
              Ejemplo:
              Domino's Pizza
              D1
              Éxito
              Starbucks

            - rawMerchant debe ser igual a merchant.

            - category intenta inferirla.
              Ejemplos:
              Alimentación
              Transporte
              Salud
              Entretenimiento
              Servicios
              Educación
              Hogar

            - Si falta el valor, el método de pago o el tipo de movimiento,
              marca:
                needsUserInput = true

            Analiza el siguiente texto:

            ======================

            %s

            ======================
            """.formatted(text);

    }

}