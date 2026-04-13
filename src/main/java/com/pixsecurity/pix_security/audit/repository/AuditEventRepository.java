package com.pixsecurity.pix_security.audit.repository;

import com.pixsecurity.pix_security.audit.domain.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditEventRepository extends MongoRepository<AuditEvent, String> {
    // AuditEvent é a classe que esse repository gerencia
    // String pois MongoDB já gera ID aleatório internamente e a Spring já me devolve como String

    // // Buscar eventos do cliente, dividos em paginação e ordenado por ordem decrescente de ocorrências
    Page<AuditEvent> findByClientIdOrderByOccurredAtDesc(String clientId, Pageable pageable);
}
