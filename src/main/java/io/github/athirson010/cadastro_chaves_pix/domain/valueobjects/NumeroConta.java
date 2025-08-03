package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import java.util.Objects;

public class NumeroConta {
    private final String value;

    private NumeroConta(String value) {
        this.value = Objects.requireNonNull(value, "Número da conta não pode ser nulo").trim();
        validateFormat();
    }

    public static NumeroConta of(String value) {
        return new NumeroConta(value);
    }

    private void validateFormat() {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio");
        }
        if (!value.matches("^\\d{8}$")) {
            throw new IllegalArgumentException("Número da conta deve conter exatamente 8 dígitos");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumeroConta that = (NumeroConta) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}