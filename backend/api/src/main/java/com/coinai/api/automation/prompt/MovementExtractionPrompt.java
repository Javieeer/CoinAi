package com.coinai.api.automation.prompt;

import org.springframework.stereotype.Component;

@Component
public class MovementExtractionPrompt {

    public String build(String email) {

        return """
            Eres un asistente especializado en analizar correos electrónicos de bancos colombianos.

            Tu única tarea es extraer información financiera.

            Responde ÚNICAMENTE un JSON válido.

            No agregues explicaciones.
            No uses markdown.
            No uses ```json.
            No escribas texto antes o después del JSON.

            El formato debe ser exactamente:

            {
              "movementType": "...",
              "amount": 0,
              "date": "...",
              "merchant": "...",
              "recipient": "...",
              "account": "...",
              "category": null,
              "needsUserInput": false,
              "missingFields": []
            }

            Reglas:

            - movementType puede ser:
              EXPENSE
              INCOME
              TRANSFER

            - amount debe ser un número.

            - date debe estar en formato ISO-8601.

            - merchant es el comercio cuando exista.

            - recipient es la persona a quien se transfirió el dinero.

            - account es la cuenta origen o destino cuando aparezca.

            - category siempre debe ser null.

            - Si falta información para registrar correctamente el movimiento:

                needsUserInput = true

            y missingFields debe contener los nombres de los campos faltantes.

            Si un dato no existe utiliza null.

            Analiza el siguiente correo:

            =========================

            %s

            =========================
            """.formatted(email);

    }

}