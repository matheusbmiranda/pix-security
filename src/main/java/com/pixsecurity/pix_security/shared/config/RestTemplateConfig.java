package com.pixsecurity.pix_security.shared.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


// RestTemplate é a ferramenta que permite fazer requisições HTTP via código

@Configuration
public class RestTemplateConfig {

    @Bean // Indica que este metodo retorna um objeto gerenciado pelo Spring
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

}
