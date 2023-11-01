package br.com.solutis.squad1.gatewaypayment.controller;

import br.com.solutis.squad1.gatewaypayment.model.FormPayment;
import br.com.solutis.squad1.gatewaypayment.service.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.solutis.squad1.gatewaypayment.model.FormPayment.*;

@RestController
@RequestMapping("payment-gateway")
@RequiredArgsConstructor
public class PaymentGatewayController {
    private PaymentGatewayService paymentGatewayService;

    @PostMapping("/pix/validation")
    public void handlePaymentNotification() {
        //envia requisição para o pagamento com a confirmação do pagamento
    }

    public String generateValidPayment(String paymentId, FormPayment formPayment, double total) {
        if (formPayment.equals(PIX)){
            return paymentGatewayService.generateValidPixKey(paymentId, total);
        } else if (formPayment.equals(BANK_BILL)){
            return paymentGatewayService.generateValidBankBill(paymentId, total);
        }
        return null;
    }
}
