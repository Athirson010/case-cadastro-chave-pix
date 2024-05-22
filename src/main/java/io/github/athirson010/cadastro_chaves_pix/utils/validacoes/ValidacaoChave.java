package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacaoChave {
    private final ChaveService chaveService;

    private final ChaveServiceV2 chaveServiceV2;

    public ValidacaoChave(ChaveService chaveService) {
        this.chaveService = chaveService;
        this.chaveServiceV2 = null;
    }

    public ValidacaoChave(ChaveServiceV2 chaveServiceV2) {
        this.chaveService = null;
        this.chaveServiceV2 = chaveServiceV2;
    }

    public void validarExistencia(String valor) {
        if (chaveService != null) {
            if (chaveService.buscarChavePorValorChave(valor).isPresent()) {
                throw new ValidacaoException("Chave já cadastrada e ativa");
            }
        }

        if (chaveServiceV2 != null) {
            if (chaveServiceV2.buscarChavePorValorChave(valor).isPresent()) {
                throw new ValidacaoException("Chave já cadastrada e ativa");
            }
        }
    }

    public static void validarRegex(String regex, String valor) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valor);

        if (!matcher.matches()) {
            throw new ValidacaoException(valor + " não é valido");
        }
    }

    public static boolean isCPF(String cpf) {
        final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        if (cpf == null) return false;

        String cpfSomenteDigitos = cpf.replaceAll("\\D", "");

        if (cpfSomenteDigitos.length() != 11 || cpfSomenteDigitos.matches("(\\d)\\1{10}")) {
            return false;
        }

        int digito1 = calcularDigito(cpfSomenteDigitos.substring(0, 9), pesoCPF);
        int digito2 = calcularDigito(cpfSomenteDigitos.substring(0, 9) + digito1, pesoCPF);

        return cpfSomenteDigitos.equals(cpfSomenteDigitos.substring(0, 9) + digito1 + digito2);
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static ChavePixValidacao resgatarTipoValidacao(TipoChaveEnum tipoChave) {
        return switch (tipoChave) {
            case CELULAR -> new ChavePixValidacaoCelular();
            case EMAIL -> new ChavePixValidacaoEmail();
            case CPF -> new ChavePixValidacaoCPF();
            case CNPJ -> new ChavePixValidacaoCNPJ();
            case ALEATORIA -> new ChavePixValidacaoAleatoria();
        };
    }
}
