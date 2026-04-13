package com.pixsecurity.pix_security.trustedbinding.domain;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// Essa classe um vínculo seguro no MongoDB, com vinculos confiaveis como Wifi, dispositivo, contato pix, para maior segurança das transações
// Então essa classe é o "registro" desse vinculo
@Document(collection = "trusted_bindings") // Significa que a classe será armazenada no Mongo, equivale ao @Entity do MySQL
@Getter
@Setter
@Data
public class TrustedBinding {

    @Id // ID é o principal identificador do documento
    private String id;
    private String clientId;
    private BindingType type;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

}
