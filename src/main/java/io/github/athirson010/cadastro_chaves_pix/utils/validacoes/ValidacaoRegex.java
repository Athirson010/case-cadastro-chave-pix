package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacaoRegex {
    public static void validar(String regex, String valor) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valor);

        if (!matcher.matches()) {
            throw new ValidacaoException(valor + " não é valido");
        }
    }
}
