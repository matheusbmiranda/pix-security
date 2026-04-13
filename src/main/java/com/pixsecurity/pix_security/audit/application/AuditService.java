package com.pixsecurity.pix_security.audit.application;

import com.pixsecurity.pix_security.audit.domain.AuditEvent;
import com.pixsecurity.pix_security.audit.repository.AuditEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuditService {

    private final AuditEventRepository auditEventRepository; // Implementando Repository nessa classe

    // Construtor (receber o repository e guardar dentro da classe)
    public AuditService(AuditEventRepository auditEventRepository) {
       this.auditEventRepository = auditEventRepository;
    }

    // Recebe o clientId, um evento e o payload (dados binding como bindingId, type, value)
    public void recordEvent(String clientId, String eventType, Map<String, Object> payload) {

        AuditEvent event = new AuditEvent(); // Cria um objeto novo

        // Seta os dados recebidos
        event.setClientId(clientId);
        event.setEventType(eventType);
        event.setPayload(payload);
        event.setOccurredAt(LocalDateTime.now());

        auditEventRepository.save(event); // Salva no Mongo
    }

    // Chama o metodo do Repository para buscar os TrustedBinding através do clientId e retornar toda a lista com paginação
    public Page<AuditEvent> findByClientId(String clientId, Pageable pageable) {
        return auditEventRepository.findByClientIdOrderByOccurredAtDesc(clientId, pageable);
    }
}
