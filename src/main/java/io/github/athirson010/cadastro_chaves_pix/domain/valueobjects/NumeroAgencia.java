package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import java.util.Objects;

public class NumeroAgencia {
    private final String value;

    private NumeroAgencia(String value) {
        this.value = Objects.requireNonNull(value, "Número da agência não pode ser nulo").trim();
        validateFormat();
    }

    public static NumeroAgencia of(String value) {
        return new NumeroAgencia(value);
    }

    private void validateFormat() {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("Número da agência não pode ser vazio");
        }
        if (!value.matches("^\\d{4}$")) {
            throw new IllegalArgumentException("Número da agência deve conter exatamente 4 dígitos");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumeroAgencia that = (NumeroAgencia) o;
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