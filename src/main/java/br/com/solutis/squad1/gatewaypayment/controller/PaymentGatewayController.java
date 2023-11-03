package br.com.solutis.squad1.gatewaypayment.controller;

import br.com.solutis.squad1.gatewaypayment.dto.PaymentCreditCardDto;
import br.com.solutis.squad1.gatewaypayment.dto.PaymentGenerationCodDto;
import br.com.solutis.squad1.gatewaypayment.service.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment-gateway")
@RequiredArgsConstructor
public class PaymentGatewayController {
    private PaymentGatewayService paymentGatewayService;

    @PutMapping("/pix/validation")
    public void confirmPaymentPix() {
        paymentGatewayService.confirmPaymentPix();
    }

    @PutMapping("/BankBill/validation")
    public void confirmPaymentBankBill() {
        paymentGatewayService.confirmPaymentBankBill();
    }

    @PutMapping("/CreditCard/validation")
    public void confirmPaymentCreditCard(@RequestBody PaymentCreditCardDto dto) {
        paymentGatewayService.confirmPaymentCreditCard(dto);
    }

    @GetMapping("/pix/key/generation")
    public String generateValidPixKey(@RequestBody PaymentGenerationCodDto dto) {
        return paymentGatewayService.generateValidPixKey(dto);
    }


    @GetMapping("/BankBill/code/generation")
    public String generateValidBankBill(@RequestBody PaymentGenerationCodDto dto) {
        return paymentGatewayService.generateValidBankBill(dto);
    }
}
