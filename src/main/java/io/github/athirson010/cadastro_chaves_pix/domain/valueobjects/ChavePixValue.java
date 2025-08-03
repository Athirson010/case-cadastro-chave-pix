package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.factory.ChavePixValidationFactory;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationService;

import java.util.Objects;

public class ChavePixValue {
    private static final ChavePixValidationService validationService = ChavePixValidationFactory.createValidationService();

    private final String value;

    private ChavePixValue(String value) {
        this.value = Objects.requireNonNull(value, "Valor da chave PIX não pode ser nulo").trim();
        if (this.value.isEmpty()) {
            throw new IllegalArgumentException("Valor da chave PIX não pode ser vazio");
        }
    }

    public static ChavePixValue of(String value) {
        return new ChavePixValue(value);
    }

    public boolean isValidForType(TipoChaveEnum tipoChave) {
        return validationService.validate(tipoChave, value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChavePixValue that = (ChavePixValue) o;
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