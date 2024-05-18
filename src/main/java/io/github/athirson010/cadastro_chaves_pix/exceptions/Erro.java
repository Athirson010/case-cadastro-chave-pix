package io.github.athirson010.cadastro_chaves_pix.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Erro {
    private String messagem;
    private List<String> detalhes = new ArrayList<>();
}
