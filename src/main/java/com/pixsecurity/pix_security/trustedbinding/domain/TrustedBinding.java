package com.pixsecurity.pix_security.trustedbinding.domain;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Vínculo confiável do cliente utilizado para validação de segurança em transações PIX")
public class TrustedBinding {

    @Id // ID é o principal identificador do documento
    @Schema(description = "ID do vínculo no MongoDB", example = "661f1c2a9f1b2c0012345678")
    private String id;

    @Schema(description = "ID do cliente associado ao vínculo", example = "3fd965d0-3ea8-4e23-83ec-2a2080f35726")
    private String clientId;

    @Schema(description = "Tipo do vínculo confiável", example = "DEVICE")
    private BindingType type;

    @Schema(description = "Valor do vínculo. Pode representar dispositivo, rede Wi-Fi ou contato PIX confiável",
            example = "iphone-15-pro-matheus")
    private String value;

    @Schema(description = "Data de criação do vínculo", example = "2026-04-15T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Data de expiração do vínculo confiável", example = "2026-12-31T23:59:59")
    private LocalDateTime expiresAt;

}
