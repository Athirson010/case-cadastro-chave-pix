package io.github.athirson010.cadastro_chaves_pix;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ApplicationTest {
    @Autowired
     Application app;

    @Test
    void main() {
        assertNotNull(app);
    }
}