package com.pixsecurity.pix_security.pixlimit.api;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PixLimitRequest {

    @Schema(description = "Valor do limite PIX em centavos", example = "500000") // Documentação no Swagger
    private Long limitValue;
}
