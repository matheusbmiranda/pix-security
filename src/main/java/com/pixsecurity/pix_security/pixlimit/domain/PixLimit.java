package com.pixsecurity.pix_security.pixlimit.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pix_limits") // Indica uma nova coleção no Mongo
@Getter
@Setter
@Schema(description = "Limite PIX configurado para o cliente")
public class PixLimit {

    @Id // ID é o principal identificador do documento
    @Schema(description = "ID do documento no MongoDB", example = "661f1c2a9f1b2c0012345678")
    private String id;

    @Schema(description = "ID do cliente associado", example = "3fd965d0-3ea8-4e23-83ec-2a2080f35726")
    private String clientId;

    @Schema(description = "Valor do limite PIX em centavos", example = "100000")
    private Long limitValue; // Em centavos

    @Schema(description = "Data da última atualização do limite", example = "2026-04-15T12:00:00")
    private LocalDateTime updatedAt;

}

