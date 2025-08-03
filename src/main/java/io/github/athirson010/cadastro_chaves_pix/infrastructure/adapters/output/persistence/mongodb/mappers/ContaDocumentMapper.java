package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.mappers;

import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ContaDocument;

public final class ContaDocumentMapper {

    private ContaDocumentMapper() {
    }

    public static ContaDocument toDocument(Conta conta) {
        return new ContaDocument(
                conta.getId().getValue(),
                conta.getTipoConta(),
                conta.getNumeroAgencia().getValue(),
                conta.getNumeroConta().getValue(),
                conta.getNomeCorrentista(),
                conta.getTipoPessoa(),
                conta.getDataInclusao()
        );
    }

    public static Conta toDomain(ContaDocument document) {
        return new Conta(
                ContaId.of(document.getId()),
                document.getTipoConta(),
                NumeroAgencia.of(document.getNumeroAgencia()),
                NumeroConta.of(document.getNumeroConta()),
                document.getNomeCorrentista(),
                document.getTipoPessoa()
        );
    }
}