package com.pixsecurity.pix_security.audit.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

// Classe de auditoria para registrar um histórico dos eventos que acontecem, como bind ou unbind

@Document(collection = "audit_history") // Será armazenado no Mongo
@Getter
@Setter
@Schema(description = "Evento de auditoria registrado no histórico do cliente")
public class AuditEvent {

    @Id
    @Schema(description = "ID do evento de auditoria", example = "661f1c2a9f1b2c0012345678")
    private String id;

    @Schema(description = "ID do cliente relacionado ao evento", example = "3fd965d0-3ea8-4e23-83ec-2a2080f35726")
    private String clientId;

    @Schema(description = "Tipo do evento de auditoria", example = "PIX_LIMIT_UPDATED")
    private String eventType;

    @Schema(hidden = true)
    private Map<String, Object> payload;

    @Schema(description = "Data e hora em que o evento ocorreu", example = "2026-04-15T10:30:00")
    private LocalDateTime occurredAt;
}
