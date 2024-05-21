package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

public class ValidacaoCPF {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean isCPF(String cpf) {
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
}
