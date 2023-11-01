package br.com.solutis.squad1.gatewaypayment.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PaymentGatewayService {

    public String generateValidPixKey(String paymentId, double total) {
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
    public String generateValidBankBill(String paymentId, double total) {
        LocalDate date = LocalDate.now().plusDays(2);
        return "9999"+ "00009" + paymentId + "9" + date.getDayOfMonth() + date.getMonthValue() + date.getYear() + (long) (total * 100) + "1";
    }

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

    public boolean validateCvv(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    public boolean validatExpiryDate(String expiryDate) {
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth cardYearMonth = YearMonth.parse(expiryDate, DateTimeFormatter.ofPattern("MM/yy"));
        return cardYearMonth.isAfter(currentYearMonth);
    }

    public boolean validateCreditCard(String cardNumber, String cvv, String expiryDate) {
        if (validateCreditCardNumber(cardNumber) && validateCvv(cvv) && validatExpiryDate(expiryDate)) {
            return true;
        }
        return false;
    }
}
