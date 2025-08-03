package io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;

import java.util.regex.Pattern;

public class CpfValidationStrategy implements ChavePixValidationStrategy {
    
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");

    @Override
    public boolean supports(TipoChaveEnum tipoChave) {
        return TipoChaveEnum.CPF.equals(tipoChave);
    }

    @Override
    public boolean isValid(String valor) {
        if (valor == null || !CPF_PATTERN.matcher(valor).matches()) {
            return false;
        }

        if (valor.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int[] digits = valor.chars().map(c -> c - '0').toArray();
            
            int sum1 = 0;
            for (int i = 0; i < 9; i++) {
                sum1 += digits[i] * (10 - i);
            }
            int checkDigit1 = 11 - (sum1 % 11);
            if (checkDigit1 >= 10) checkDigit1 = 0;

            int sum2 = 0;
            for (int i = 0; i < 10; i++) {
                sum2 += digits[i] * (11 - i);
            }
            int checkDigit2 = 11 - (sum2 % 11);
            if (checkDigit2 >= 10) checkDigit2 = 0;

            return digits[9] == checkDigit1 && digits[10] == checkDigit2;
        } catch (Exception e) {
            return false;
        }
    }
}