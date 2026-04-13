package com.pixsecurity.pix_security.audit.api;

import com.pixsecurity.pix_security.audit.application.AuditService;
import com.pixsecurity.pix_security.audit.domain.AuditEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



// Classe controller = interface com o mundo (HTTP), apenas receber e devolver os dados

@RestController // Essa classe vai receber requisições HTTP
@RequestMapping("/api/v1/customers/{clientId}/audit-history") // Rota base HTTP
@Tag(name = "Audit History", description = "Consulta do histórico de auditoria do cliente") // Organiza e descreve um grupo de endpoints
public class AuditController {

    private final AuditService auditService; // Variavel para guardar o service

    public AuditController(AuditService auditService) { // Traz e guarda o service
        this.auditService = auditService;
    }

    @GetMapping // Metodo GET do HTTP
    @Operation(summary = "Listar histórico de auditoria", description = "Retorna o histórico de auditoria do cliente com paginação") // Documentação, Swagger neste caso
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public Page<AuditEvent> findByClientId(@PathVariable String clientId, Pageable pageable) { // Chama esse metodo do Service que retorna a lista AuditEvent com paginação
        return auditService.findByClientId(clientId, pageable);
    }
}
