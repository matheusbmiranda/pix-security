package com.pixsecurity.pix_security.pixlimit.repository;

import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;



// Repository é a ponte entre o código e o banco de dados, permite salvar, buscar ID, listar, deletar

public interface PixLimitRepository extends MongoRepository<PixLimit, String> {
    // PixLimit é a classe que esse repository gerencia
    // String pois MongoDB já gera ID aleatório internamente e a Spring já me devolve como String

    // Buscar um objeto PixLimit pelo clientId, se encontrar devolve o objeto se não encontrar cria e devolve vazio graças ao "Optional"
    Optional<PixLimit> findByClientId(String clientId);
}
