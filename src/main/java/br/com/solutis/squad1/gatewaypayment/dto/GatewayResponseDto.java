package br.com.solutis.squad1.gatewaypayment.dto;

public record GatewayResponseDto (
        Long paymentId,
        Boolean confirmed
) {
}