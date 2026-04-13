package com.pixsecurity.pix_security.audit.domain;

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
public class AuditEvent {

    @Id
    private String id;

    private String clientId;
    private String eventType;
    private Map<String, Object> payload;
    private LocalDateTime occurredAt;
}
