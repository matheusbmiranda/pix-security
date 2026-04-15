package com.pixsecurity.pix_security.trustedbinding.repository;

import com.pixsecurity.pix_security.trustedbinding.domain.BindingType;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// Repository é a ponte entre o código e o banco de dados, permite salvar, buscar ID, listar, deletar

public interface TrustedBindingRepository extends MongoRepository<TrustedBinding, String> {
    // TrustedBinding é a classe que esse repository gerencia
    // String pois MongoDB já gera ID aleatório internamente e a Spring já me devolve como String

    // Verificar se já existe algum TrustedBinding com esse clientId, type e value
    boolean existsByClientIdAndTypeAndValue(String clientId, BindingType type, String value);

    // Buscar um objeto TrustedBinding pelos dois ID's, se encontrar devolve o objeto se não encontrar cria e devolve vazio graças ao "Optional"
    Optional<TrustedBinding> findByIdAndClientId(String id, String clientId);

    // Buscar os TrustedBinding através do clientId e retornar toda a lista
    List<TrustedBinding> findByClientId(String clientId);
}


