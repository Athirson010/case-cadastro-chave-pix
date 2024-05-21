package io.github.athirson010.cadastro_chaves_pix.configurations;

import io.github.athirson010.cadastro_chaves_pix.business.ChavePixBusiness;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Mock {
    private final ChavePixBusiness business;

    public Mock(ChavePixBusiness business) {
        this.business = business;
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            List<CadastroChavePixRequest> cadastroChavePixRequests = new ArrayList<>();

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.CPF, "482.744.678-41",
                    TipoContaEnum.CORRENTE, "1234", "12345678", "Athirson", "Luz", TipoPessoaEnum.FISICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.EMAIL, "teste@teste.com",
                    TipoContaEnum.POUPANCA, "1233", "12345679", "Paulo", "Santos", TipoPessoaEnum.JURIDICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.EMAIL, "athirson@teste.com",
                    TipoContaEnum.CORRENTE, "1234", "12345678", "Athirson", "Lima", TipoPessoaEnum.FISICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.CPF, "436.492.840-22",
                    TipoContaEnum.CORRENTE, "1235", "12345680", "Carlos", "Daniel", TipoPessoaEnum.FISICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.CPF, "117.275.480-27",
                    TipoContaEnum.POUPANCA, "1236", "12345681", "Maria", "betania", TipoPessoaEnum.FISICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.CNPJ, "12.345.678/0001-99",
                    TipoContaEnum.POUPANCA, "1233", "12345679", "Paulo", "Mock", TipoPessoaEnum.JURIDICA));

            cadastroChavePixRequests.add(new CadastroChavePixRequest(TipoChaveEnum.CPF, "095.466.600-35",
                    TipoContaEnum.CORRENTE, "1237", "12345682", "João", "E maria", TipoPessoaEnum.FISICA));

            //cadastroChavePixRequests.forEach(business::criarChaveComConta);
        };
    }
}
