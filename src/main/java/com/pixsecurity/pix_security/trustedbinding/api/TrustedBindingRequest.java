package com.pixsecurity.pix_security.trustedbinding.api;

import com.pixsecurity.pix_security.trustedbinding.domain.BindingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

// Classe para receber dados da API
@Getter
@Setter
@Schema(description = "Dados para criar um vínculo confiável do cliente")
public class TrustedBindingRequest {

    @Schema(description = "Tipo do vínculo confiável",example = "DEVICE")
    private BindingType type;

    @Schema(description = "Valor do vínculo. Pode ser o identificador do dispositivo, nome da rede Wi-Fi ou chave/contato PIX confiável",
            example = "iphone-15-pro-matheus")
    private String value;

}
