package br.com.solutis.squad1.gatewaypayment.service;

import br.com.solutis.squad1.gatewaypayment.dto.PaymentCreditCardDto;
import br.com.solutis.squad1.gatewaypayment.dto.PaymentGenerationCodDto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class PaymentGatewayService {

    /**
     * Generates a valid PIX key based on payment information.
     *
     * @param dto The PaymentGenerationCodDto object containing the payment identifier and total amount.
     * @return A valid PIX key based on the provided payment information.
     */
    public String generateValidPixKey(PaymentGenerationCodDto dto) {
        String paymentId = dto.paymentId();
        Double total = dto.total();

        return "VALIDPIX-" + paymentId + "-" + total;
    }

    /*
        4 dígitos: código do banco
        5 dígitos: código da moeda (Real, no caso)
        10 dígitos: campo livre para uso do banco (utilizado para identificação do cedente e do documento)
        1 dígito: dígito verificador do campo livre
        4 dígitos: fator de vencimento (representa a data de vencimento do boleto)
        10 dígitos: valor do boleto (com duas casas decimais)
        1 dígito: dígito verificador geral do código de barras
    */
    /**
     * Generates a valid bank bill code based on payment information.
     *
     * @param dto The PaymentGenerationCodDto object containing the payment identifier and total amount.
     * @return A valid bank bill code based on the provided payment information.
     */
    public String generateValidBankBill(PaymentGenerationCodDto dto) {
        String paymentId = dto.paymentId();
        Double total = dto.total();
        LocalDate date = LocalDate.now().plusDays(2);

        return "9999"+ "00009" + paymentId + "9" + date.getDayOfMonth() + date.getMonthValue() + date.getYear() + (long) (total * 100) + "1";
    }

    /**
     * Confirms the payment via credit card using the provided payment details.
     * Validates the credit card information and proceeds with the payment confirmation or rejection accordingly.
     *
     * @param dto The PaymentCreditCardDto object containing payment details such as card number, CVV, expiry date, and payment ID.
     */
    public void confirmPaymentCreditCard(PaymentCreditCardDto dto) {
        Long id = dto.id();
        String cardNumber = dto.cardNumber(), cvv = dto.cvv(), expiryDate = dto.expiryDate();

        if (validateCreditCard(cardNumber, cvv, expiryDate)){
            //envia para pagamento a confirmação do pagamento
        }

        //Envia para pagamento a recusa do pagamento
    }

    /**
     * Confirms the payment made through PIX.
     */
    public void confirmPaymentPix() {
        //Envia para pagamento a confirmação do pagamento
    }

    /**
     * Confirms the payment made through a bank bill.
     */
    public void confirmPaymentBankBill() {
        //Envia para pagamento a confirmação do pagamento
    }

    /**
     * Validates a credit card using the provided card number, CVV, and expiry date.
     *
     * @param cardNumber The credit card number.
     * @param cvv        The card's CVV (Card Verification Value).
     * @param expiryDate The card's expiry date in the format "MM/yy".
     * @return True if the credit card is considered valid based on its number, CVV, and expiry date.
     */
    public boolean validateCreditCard(String cardNumber, String cvv, String expiryDate) {
        return (validateCreditCardNumber(cardNumber) && validateCvv(cvv) && validatExpiryDate(expiryDate));
    }

    /**
     * Validates a credit card number using the Luhn algorithm.
     * This algorithm checks the validity of a credit card number through a mathematical formula.
     *
     * @param cardNumber The credit card number to be validated.
     * @return Returns true if the credit card number is valid according to the Luhn algorithm
     */
    public boolean validateCreditCardNumber(String cardNumber) {
        int sum = 0;
        int parity = cardNumber.length() % 2;
        for (int i = 0; i < cardNumber.length(); i++) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (i % 2 == parity) digit *= 2;
            if (digit > 9) digit -= 9;
            sum += digit;
        }
        return sum % 10 == 0;
    }

    /**
     * Validates a CVV (Card Verification Value) of a credit card.
     *
     * @param cvv The CVV to be validated.
     * @return True if the CVV format is valid (3 digits).
     */
    public boolean validateCvv(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    /**
     * Validates the expiry date of a credit card.
     *
     * @param expiryDate The card's expiry date in the format "MM/yy".
     * @return True if the expiry date is in the future in comparison to the current date.
     */
    public boolean validatExpiryDate(String expiryDate) {
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth cardYearMonth = YearMonth.parse(expiryDate, DateTimeFormatter.ofPattern("MM/yy"));
        return cardYearMonth.isAfter(currentYearMonth);
    }
}
