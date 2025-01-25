package com.api.bytebank.utils;

public class CpfValidator {

    public static boolean isCpfValido(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); 

        if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false; 
        }

        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int firstDigit = calculateCheckDigit(cpf.substring(0, 9), weightsFirstDigit);
        int secondDigit = calculateCheckDigit(cpf.substring(0, 10), weightsSecondDigit);

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static int calculateCheckDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Character.digit(str.charAt(i), 10) * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
