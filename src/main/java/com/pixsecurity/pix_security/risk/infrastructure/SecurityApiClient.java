package com.pixsecurity.pix_security.risk.infrastructure;


import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component // Significa que essa classe pode ser injetada em outras classes
public class SecurityApiClient {

    private final RestTemplate restTemplate; // Implementando RestTemplate nessa classe

    // Construtor (receber o RestTemplate e guardar dentro da classe)
    public SecurityApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Metodo para fazer requisições HTTP dos TrustedBinding
    public TrustedBinding[] getTrustedBindings(String clientId) { // Recebe um clientId e retorna um TrustedBinding (vinculos de um cliente)
        String url = "http://localhost:8080/api/v1/customers/" + clientId + "/trusted-bindings"; // Endpoint (igual a requisições via Postman)

        return restTemplate.getForObject(url, TrustedBinding[].class); // Simula o modo GET, converte o JSON em array (lista) do objeto TrustedBinding e retorna
    }

    // Metodo para fazer requisições HTTP dos PixLimit
    public PixLimit getPixLimit(String clientId) { // Recebe um clientId e retorna um PixLimit (limite de um cliente)
        String url = "http://localhost:8080/api/v1/customers/" + clientId + "/pix-limit"; // Endpoint (igual a requisições via Postman)

        return restTemplate.getForObject(url, PixLimit.class); // Simula o modo GET, converte o JSON no tipo de objeto PixLimit e retorna
    }
}
