package com.pixsecurity.pix_security.trustedbinding.api;

import com.pixsecurity.pix_security.trustedbinding.domain.BindingType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Classe para receber dados da API
@Getter
@Setter
@Data
public class TrustedBindingRequest {

    private BindingType type;
    private String value;

}
