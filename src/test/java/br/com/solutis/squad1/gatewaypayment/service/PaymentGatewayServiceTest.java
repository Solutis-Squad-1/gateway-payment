package br.com.solutis.squad1.gatewaypayment.service;

import br.com.solutis.squad1.gatewaypayment.dto.PaymentGenerationCodDto;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentGatewayServiceTest {

    @MockBean
    private PaymentGatewayService paymentGatewayService;

    @Test
    @DisplayName("Check if the generated PIX key is valid")
    void testGenerateValidPixKey() {
        PaymentGenerationCodDto dto = createPaymentGenerationDto("123", 100.0);

        String expectedKey = "VALIDPIX-123-100.0";

        when(paymentGatewayService.generateValidPixKey(dto)).thenReturn(expectedKey);

        String generatedKey = paymentGatewayService.generateValidPixKey(dto);

        assertEquals(expectedKey, generatedKey);
    }


    @Test
    @DisplayName("Check if the generated bill code is valid")
    void testGenerateValidBankBill() {
        PaymentGenerationCodDto dto = createPaymentGenerationDto("123", 100.0);
        LocalDate date = LocalDate.now().plusDays(2);

        String expectedKey = "9999000091239" + date.getDayOfMonth() + date.getMonthValue() + date.getYear() + "100001";

        when(paymentGatewayService.generateValidBankBill(dto)).thenReturn(expectedKey);

        String generatedKey = paymentGatewayService.generateValidBankBill(dto);

        assertEquals(expectedKey, generatedKey);
    }

    @Test
    @DisplayName("Check if your credit card number is valid")
    public void testValidateCreditCardNumber_ValidCardNumber() {
        PaymentGatewayService service = new PaymentGatewayService();
        assertTrue(service.validateCreditCardNumber("79927398713"));
    }

    @Test
    @DisplayName("Check if your credit card number is invalid")
    public void testValidateCreditCardNumber_InvalidCardNumber() {
        PaymentGatewayService service = new PaymentGatewayService();
        assertFalse(service.validateCreditCardNumber("536989905"));
    }

    @Test
    @DisplayName("Check if the cvv number is considered valid when passing the required number of characters")
    public void testValidateCvv_ValidCvv() {
        PaymentGatewayService service = new PaymentGatewayService();
        assertTrue(service.validateCvv("123"));
    }

    @Test
    @DisplayName("Check if the cvv number is considered invalid due to a null value being passed")
    public void testValidateCvv_NullCvv() {
        PaymentGatewayService service = new PaymentGatewayService();
        assertFalse(service.validateCvv(null));
    }

    @Test
    @DisplayName("Check if the cvv number is considered invalid because the required number of characters was not passed")
    public void testValidateCvv_InvalidCvv() {
        PaymentGatewayService service = new PaymentGatewayService();
        assertFalse(service.validateCvv("12"));
    }

    @Test
    @DisplayName("Check whether future data is considered valid")
    public void testValidateExpiryDate_FutureDate() {
        PaymentGatewayService service = new PaymentGatewayService();

        YearMonth futureDate = YearMonth.now().plusYears(1);

        assertTrue(service.validatExpiryDate(futureDate.format(DateTimeFormatter.ofPattern("MM/yy"))));
    }

    @Test
    @DisplayName("Check whether the current date is considered invalid when a card expiration date that is exactly the same as the current date is not considered valid")
    public void testValidateExpiryDate_CurrentDate() {
        PaymentGatewayService service = new PaymentGatewayService();

        YearMonth currentDate = YearMonth.now();

        assertFalse(service.validatExpiryDate(currentDate.format(DateTimeFormatter.ofPattern("MM/yy"))));
    }

    @Test
    @DisplayName("Check whether a past date is considered invalid")
    public void testValidateExpiryDate_PastDate() {
        PaymentGatewayService service = new PaymentGatewayService();

        YearMonth pastDate = YearMonth.now().minusYears(1);

        assertFalse(service.validatExpiryDate(pastDate.format(DateTimeFormatter.ofPattern("MM/yy"))));
    }

    @Test
    void testValidateCreditCard(){
        PaymentGatewayService service = new PaymentGatewayService();

        String cardNumber = "79927398713";
        String cvv = "123";
        YearMonth expiryDate = YearMonth.now().plusMonths(3);

        assertTrue(service.validateCreditCard(cardNumber, cvv, expiryDate.format(DateTimeFormatter.ofPattern("MM/yy"))));
    }

    private PaymentGenerationCodDto createPaymentGenerationDto(String paymentId, Double total){
        return new PaymentGenerationCodDto(
                paymentId,
                total
        );
    }
}