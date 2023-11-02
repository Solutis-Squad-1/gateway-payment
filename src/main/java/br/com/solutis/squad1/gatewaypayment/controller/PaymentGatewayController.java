package br.com.solutis.squad1.gatewaypayment.controller;

import br.com.solutis.squad1.gatewaypayment.service.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment-gateway")
@RequiredArgsConstructor
public class PaymentGatewayController {
    private PaymentGatewayService paymentGatewayService;

    @GetMapping("/pix/validation")
    public void confirmPaymentPix() {
        //envia requisição para o pagamento com a confirmação do pagamento (confirmed ou recused)
    }

    @GetMapping("/BankBill/validation")
    public void confirmPaymentBankBill() {
        //envia requisição para o pagamento com a confirmação do pagamento
    }

    @GetMapping("/CreditCard/validation")
    public void confirmPaymentCreditCard() {
        //Recebe dados do pagamento
        //envia requisição para o pagamento com a confirmação do pagamento
    }

    @GetMapping("/pix/key/generation")
    public String generateValidPixKey(String paymentId, double total) {
        return paymentGatewayService.generateValidPixKey(paymentId, total);
    }


    @GetMapping("/BankBill/code/generation")
    public String generateValidBankBill(String paymentId, double total) {
        return paymentGatewayService.generateValidBankBill(paymentId, total);
    }
}
