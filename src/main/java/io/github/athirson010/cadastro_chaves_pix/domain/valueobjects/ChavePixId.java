package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class ChavePixId {
    private final String value;

    private ChavePixId(String value) {
        this.value = Objects.requireNonNull(value, "ID não pode ser nulo");
    }

    public static ChavePixId generate() {
        return new ChavePixId(UUID.randomUUID().toString());
    }

    public static ChavePixId of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
        return new ChavePixId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChavePixId that = (ChavePixId) o;
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