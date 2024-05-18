package io.github.athirson010.cadastro_chaves_pix.utils;

public class ValidacaoCPF {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isCPF(String cpf) {

        if (cpf == null) return false;

        String cpfSomenteDigitos = cpf.replaceAll("\\D", "");

        if (cpfSomenteDigitos.length() != 11 || cpfSomenteDigitos.equals("00000000000") || cpfSomenteDigitos.equals("11111111111") || cpfSomenteDigitos.equals("22222222222") || cpfSomenteDigitos.equals("33333333333") || cpfSomenteDigitos.equals("44444444444") || cpfSomenteDigitos.equals("55555555555") || cpfSomenteDigitos.equals("66666666666") || cpfSomenteDigitos.equals("77777777777") || cpfSomenteDigitos.equals("88888888888") || cpfSomenteDigitos.equals("99999999999")) {
            return false;
        }

        int digito1 = calcularDigito(cpfSomenteDigitos.substring(0, 9), pesoCPF);
        int digito2 = calcularDigito(cpfSomenteDigitos.substring(0, 9) + digito1, pesoCPF);

        return cpfSomenteDigitos.equals(cpfSomenteDigitos.substring(0, 9) + digito1 + digito2);
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        int digito = 0;
        for (int indice = str.length() - 1; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

}
