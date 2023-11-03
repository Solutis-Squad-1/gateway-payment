package br.com.solutis.squad1.gatewaypayment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentGenerationCodDto(
        @NotBlank
        String paymentId,
        @NotNull
        Double total
) {
}
