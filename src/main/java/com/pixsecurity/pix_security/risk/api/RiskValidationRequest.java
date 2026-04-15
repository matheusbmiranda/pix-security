package com.pixsecurity.pix_security.risk.api;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiskValidationRequest {

    @Schema(description = "ID do cliente", example = "57d8a63b-47af-4d4a-a6ce-c5fc1ada50c8") // Documentação no Swagger
    private String clientId;

    @Schema(description = "Valor da transação em centavos", example = "700000") // Documentação no Swagger
    private Long amount; // Valor da transação em centavos

    @Schema(description = "Tipo da transação PIX", example = "TRANSFER") // Documentação no Swagger
    private String type; // Tipo da operação

}
