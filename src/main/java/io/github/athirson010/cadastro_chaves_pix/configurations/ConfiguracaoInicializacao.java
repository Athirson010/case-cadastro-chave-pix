package io.github.athirson010.cadastro_chaves_pix.configurations;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.PostConstruct;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

public class ConfiguracaoInicializacao {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("PIX").pathsToMatch("/**").build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("Modulo de cadastro de chaves PIX"));
    }

    @Bean
    @PostConstruct
    public void setTimeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("Brazil/East"));
    }
}
