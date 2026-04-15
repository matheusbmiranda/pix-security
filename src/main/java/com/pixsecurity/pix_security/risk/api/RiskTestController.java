package com.pixsecurity.pix_security.risk.api;


import com.pixsecurity.pix_security.customer.api.dto.CustomerResponse;
import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import com.pixsecurity.pix_security.risk.infrastructure.CustomerApiClient;
import com.pixsecurity.pix_security.risk.infrastructure.SecurityApiClient;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


// Classe controller = interface com o mundo (HTTP), apenas receber e devolver os dados


@RestController // Essa classe vai receber requisições HTTP
@RequestMapping("/api/v1/risk/test") // Rota base HTTP
@Hidden // Esconde do Swagger
public class RiskTestController {

    private final CustomerApiClient customerApiClient; // Variavel para guardar o CustomerApiClient

    private final SecurityApiClient securityApiClient; // Variavel para guardar o SecurityApiClient

    // Construtor (receber o CustomerApiClient e o SecurityApiClient e injetar dentro da classe)
    public RiskTestController(CustomerApiClient customerApiClient, SecurityApiClient securityApiClient) {
        this.customerApiClient = customerApiClient;
        this.securityApiClient = securityApiClient;
    }

    @GetMapping("/customer/{clientId}") // Metodo GET do HTTP
    public CustomerResponse testCustomerClient(@PathVariable String clientId) {
        return customerApiClient.getCustomerById(clientId); // Busca o cliente
    }

    @GetMapping("/security/{clientId}") // Metodo GET do HTTP
    public Map<String, Object> testSecurityClient(@PathVariable String clientId) {
        TrustedBinding[] bindings = securityApiClient.getTrustedBindings(clientId); // Busca os vinculos deste cliente
        PixLimit pixLimit = securityApiClient.getPixLimit(clientId); // Busca limite PIX deste cliente

        Map<String, Object> response = new HashMap<>(); // Cria um objeto genérico e seta os dados
        response.put("bindings", bindings);
        response.put("pixLimit", pixLimit);

        return response;
    }
}
