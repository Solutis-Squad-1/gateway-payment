package br.com.solutis.squad1.gatewaypayment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentCreditCardDto (
        @NotNull
        Long id,
        @NotBlank
        String cardNumber,
        @NotBlank
        String cvv,
        @NotBlank
        String expiryDate
) {
}
