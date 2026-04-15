package com.pixsecurity.pix_security.trustedbinding.application;

import com.pixsecurity.pix_security.audit.application.AuditService;
import com.pixsecurity.pix_security.shared.exception.BindingAlreadyExistsException;
import com.pixsecurity.pix_security.trustedbinding.domain.BindingType;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import com.pixsecurity.pix_security.trustedbinding.api.TrustedBindingRequest;
import com.pixsecurity.pix_security.trustedbinding.repository.TrustedBindingRepository;
import com.pixsecurity.pix_security.trustedbinding.validation.PixKeyValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


// Classe service = cérebro (regras), decide o que fazer com o que vier do controller

@Service // Transforma a classe em um objeto gerenciado diretamente pelo Spring
public class TrustedBindingService {

    // Atributo para guardar o Repository que conversa com o Mongo
    private final TrustedBindingRepository trustedBindingRepository;

    // Atributo para guardar o AuditService nessa classe
    private final AuditService auditService;

    // Construtor (receber o repository e o auditservice e guardar dentro da classe)
    public TrustedBindingService(TrustedBindingRepository trustedBindingRepository, AuditService auditService) {
        this.trustedBindingRepository = trustedBindingRepository;
        this.auditService = auditService;
    }

    public TrustedBinding bind(String clientId, TrustedBindingRequest request) { // Metodo que recebe o ID do cliente do vínculo, type e value e retorna um TrustedBinding salvo

        boolean alreadyExists = trustedBindingRepository.existsByClientIdAndTypeAndValue( // Verificar no Mongo se esse vínculo já está cadastrado para esse cliente
                clientId,
                request.getType(),
                request.getValue()
        );

        if (alreadyExists) { // Se já existir (boolean = true) interrompe o metodo e mostra esse erro
            throw new BindingAlreadyExistsException("Vínculo já cadastrado para este cliente");
        }

        // Se o Type for WIFI e o value NÃO for um MAC Adress válido, interrompe e dá erro
        if (request.getType() == BindingType.WIFI && !PixKeyValidator.isValidMacAddress(request.getValue())) {
            throw new RuntimeException("MAC address inválido para vínculo WIFI");
        }

        // Se o Type for PIX_CONTACT e não for uma chave pix válida, interrempo e dá erro
        if (request.getType() == BindingType.PIX_CONTACT && !PixKeyValidator.isValidPixContact(request.getValue())) {
            throw new RuntimeException("Valor inválido para vínculo PIX");
        }

        // Se ainda não existir (boolean = false) segue o bloco abaixo
        TrustedBinding binding = new TrustedBinding(); // Cria um novo objeto TrustedBinding

        // Preenche os campos TrustedBinding com o que veio da requisição (POST, neste caso)
        binding.setClientId(clientId);
        binding.setType(request.getType());
        binding.setValue(request.getValue());
        binding.setCreatedAt(LocalDateTime.now()); // Data criação deste vínculo
        binding.setExpiresAt(LocalDateTime.now().plusDays(365)); // Expiração do vínculo

        TrustedBinding savedBinding = trustedBindingRepository.save(binding); // Salva no Mongo e já guarda na variável savedBinding

        auditService.recordEvent( // Chama o metodo que registra o evento para auditoria
                clientId,"BIND_CREATED", // Quem fez a ação e qual tipo do evento
                Map.of( // Detalhes do evento
                        "bindingId", savedBinding.getId(),
                        "type", savedBinding.getType().name(),
                        "value", savedBinding.getValue()
                )
        );

        return savedBinding; // Devolve o Binding para controller
    }

    // Metodo para apagar um vinculo de um cliente
    public void unbind(String clientId, String bindingId) { // Indicação de quem é o cliente e qual vínculo apagar
        TrustedBinding binding = trustedBindingRepository.findByIdAndClientId(bindingId, clientId) // Verificar se existe um binding com esse ID e desse cliente
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado para este cliente")); // Se não existir ou estiver vazio interrompe e dá erro

        auditService.recordEvent( // Registrar o evento para auditoria, identico metodo bind acima
                clientId,"BIND_REMOVED",
                Map.of(
                        "bindingId", binding.getId(),
                        "type", binding.getType().name(),
                        "value", binding.getValue()
                )
        );

        trustedBindingRepository.delete(binding);
    }

    // Chama o metodo do Repository para buscar os TrustedBinding através do clientId e retornar toda a lista
    public List<TrustedBinding> findByClientId(String clientId) {
        return trustedBindingRepository.findByClientId(clientId);
    }


}
