package com.pixsecurity.pix_security.risk.api;

import com.pixsecurity.pix_security.risk.domain.RiskLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiskValidationResponse {

    @Schema(description = "Nível de risco calculado", example = "HIGH") // Documentação no Swagger
    private RiskLevel riskLevel;

    @Schema(description = "Mensagem explicando o resultado da validação", example = "Transação acima do limite, mas com uma validação confiável.") // Documentação no Swagger
    private String message; // Vai explicar o motivo do riskLevel

}
