package io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;

import java.util.regex.Pattern;

public class CnpjValidationStrategy implements ChavePixValidationStrategy {
    
    private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");

    @Override
    public boolean supports(TipoChaveEnum tipoChave) {
        return TipoChaveEnum.CNPJ.equals(tipoChave);
    }

    @Override
    public boolean isValid(String valor) {
        if (valor == null || !CNPJ_PATTERN.matcher(valor).matches()) {
            return false;
        }

        if (valor.chars().distinct().count() == 1) {
            return false;
        }

        try {
            int[] digits = valor.chars().map(c -> c - '0').toArray();
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum1 = 0;
            for (int i = 0; i < 12; i++) {
                sum1 += digits[i] * weights1[i];
            }
            int checkDigit1 = sum1 % 11 < 2 ? 0 : 11 - (sum1 % 11);

            int sum2 = 0;
            for (int i = 0; i < 13; i++) {
                sum2 += digits[i] * weights2[i];
            }
            int checkDigit2 = sum2 % 11 < 2 ? 0 : 11 - (sum2 % 11);

            return digits[12] == checkDigit1 && digits[13] == checkDigit2;
        } catch (Exception e) {
            return false;
        }
    }
}