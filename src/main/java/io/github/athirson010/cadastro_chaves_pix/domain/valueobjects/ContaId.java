package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class ContaId {
    private final String value;

    private ContaId(String value) {
        this.value = Objects.requireNonNull(value, "ID da conta não pode ser nulo");
    }

    public static ContaId generate() {
        return new ContaId(UUID.randomUUID().toString());
    }

    public static ContaId of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da conta não pode ser nulo ou vazio");
        }
        return new ContaId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaId contaId = (ContaId) o;
        return Objects.equals(value, contaId.value);
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