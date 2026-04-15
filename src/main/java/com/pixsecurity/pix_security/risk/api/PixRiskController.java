package com.pixsecurity.pix_security.risk.api;

import com.pixsecurity.pix_security.risk.application.PixRiskOrchestratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Essa classe vai receber requisições HTTP
@RequestMapping("/api/v1/pix/risk") // Rota base HTTP
@Tag(name = "Pix Risk", description = "Validação de risco de transações PIX") // Organiza e descreve um grupo de endpoints no Swagger
public class PixRiskController {

    private final PixRiskOrchestratorService pixRiskOrchestratorService; // Variavel para guardar o PixRiskOrchestratorService

    // Construtor (receber o PixRiskOrchestratorService e injetar dentro da classe)
    public PixRiskController(PixRiskOrchestratorService pixRiskOrchestratorService) {
        this.pixRiskOrchestratorService = pixRiskOrchestratorService;
    }

    @PostMapping("/validate") // Metodo POST do HTTP
    @Operation(summary = "Validar risco da transação PIX", description = "Analisa o valor da transação e os vínculos confiáveis do cliente para determinar o nível de risco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para validação", content = @Content)
    })
    // RequestBody recebe o corpo da requisição JSON
    // Vai devolver um objeto do tipo RiskValidationResponse
    // ResponseEntity controla o status que vai retornar no HTTP
    public ResponseEntity<RiskValidationResponse> validate(@RequestBody RiskValidationRequest request) {
        RiskValidationResponse response = pixRiskOrchestratorService.validate(request); // Chama o metodo validate do Service e guarda o resultado em response
        return ResponseEntity.ok(response); // Define o status para '200 OK' e retorna response no JSON
    }
}
