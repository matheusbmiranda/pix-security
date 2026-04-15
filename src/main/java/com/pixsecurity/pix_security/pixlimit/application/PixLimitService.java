package com.pixsecurity.pix_security.pixlimit.application;

import com.pixsecurity.pix_security.audit.application.AuditService;
import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import com.pixsecurity.pix_security.pixlimit.repository.PixLimitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

// Classe service = cérebro (regras), decide o que fazer com o que vier do controller

@Service // Transforma a classe em um objeto gerenciado diretamente pelo Spring
public class PixLimitService {

    // Atributo para guardar o Repository que conversa com o Mongo
    private final PixLimitRepository pixLimitRepository;

    // Atributo para guardar o AuditService nessa classe
    private final AuditService auditService;

    // Construtor (receber o repository e o auditservice e guardar dentro da classe)
    public PixLimitService(PixLimitRepository pixLimitRepository, AuditService auditService) {
        this.pixLimitRepository = pixLimitRepository;
        this.auditService = auditService;
    }

    // Metodo para limitar o valor limite de um cliente
    public PixLimit setLimit(String clientId, Long limitValue) { // Indica quem é o cliente pelo Id e qual será o limite

        if (limitValue == null || limitValue < 1 || limitValue > 99999999) { // Se o limite vier vazio ou menor que 1 ou maior que 99999999 = erro abaixo
            throw new RuntimeException("O limite PIX deve estar entre 1 e 99999999 centavos");
        }

        PixLimit pixLimit = pixLimitRepository.findByClientId(clientId).orElseGet(PixLimit::new);
        // Chama repository para verificar se este cliente já possui algum limite registrado, se já existe vai atualizar e se ainda não existe vai criar um novo

        // Preenche os campos PixLimit com o que veio da requisição (POST, neste caso)
        pixLimit.setClientId(clientId);
        pixLimit.setLimitValue(limitValue);
        pixLimit.setUpdatedAt(LocalDateTime.now());

        PixLimit savedPixLimit = pixLimitRepository.save(pixLimit); // Salva no Mongo e já guarda na variável savedPixLimit

        auditService.recordEvent( // Chama o metodo que registra o evento para auditoria
                clientId,"PIX_LIMIT_UPDATED", // Quem fez a ação e qual tipo do evento
                Map.of( // Detalhes do evento
                        "pixLimitId", savedPixLimit.getId(),
                        "limitValue", savedPixLimit.getLimitValue()
                )
        );

        return savedPixLimit; // Devolve o PixLimit para controller
    }

    // Metodo para buscar por um clientId e retornar seu limite registrado
    public PixLimit getByClientId(String clientId) {
        return pixLimitRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Limite PIX não encontrado para este cliente")); // Se não existir ou estiver vazio interrompe e dá erro
    }
}
