package br.com.solutis.squad1.gatewaypayment.dto;


import br.com.solutis.squad1.gatewaypayment.enums.FormPayment;

/**
 * GatewayDto
 */
public record GatewayDto (
        Long paymentId,
        String formPayment
) {
}
