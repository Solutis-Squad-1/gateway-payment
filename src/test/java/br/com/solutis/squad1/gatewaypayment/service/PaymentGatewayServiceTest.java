package br.com.solutis.squad1.gatewaypayment.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentGatewayServiceTest {

    @MockBean
    private PaymentGatewayService paymentGatewayService;

    @Test
    void testGenerateValidPixKey() {
        String paymentId = "123";
        double total = 100.0;

        String expectedKey = "VALIDPIX-123-100.0";

        when(paymentGatewayService.generateValidPixKey(paymentId, total)).thenReturn(expectedKey);

        String generatedKey = paymentGatewayService.generateValidPixKey(paymentId, total);

        assertEquals(expectedKey, generatedKey);
    }


    @Test
    void testGenerateValidBankBill() {
        String paymentId = "123";
        double total = 100.0;
        LocalDate date = LocalDate.now().plusDays(2);

        String expectedKey = "9999000091239" + date.getDayOfMonth() + date.getMonthValue() + date.getYear() + "100001";

        when(paymentGatewayService.generateValidBankBill(paymentId, total)).thenReturn(expectedKey);

        String generatedKey = paymentGatewayService.generateValidBankBill(paymentId, total);

        assertEquals(expectedKey, generatedKey);
    }
}