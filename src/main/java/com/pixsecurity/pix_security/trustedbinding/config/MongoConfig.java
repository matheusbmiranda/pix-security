package com.pixsecurity.pix_security.trustedbinding.config;

import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import jakarta.annotation.PostConstruct;

@Configuration // Essa classe é a configuração de um indice TTL (campos com tempo de vida para expirar) no MongoDB
public class MongoConfig {

    private final MongoTemplate mongoTemplate; // Criação de um objeto do tipo MongoTemplate (ferramenta para mexer no Mongo), para ter acesso ao Mongo

    // Construtor (roda quando a classe é criada)
    public MongoConfig(MongoTemplate mongoTemplate) { // Para criar essa classe MongoConfig, preciso receber um MongoTemplate
        this.mongoTemplate = mongoTemplate; // Pegar o objeto MongoTemplate criado e guardar nesse atributo
    }

    // O bloco acima foi criado para que essa classe tenha um objeto que consiga conversar com o Mongo

    @PostConstruct // Para depois que Spring criar essa classe e injetar e executar o construtor executar este metodo automaticamente
    public void initIndexes() { // Metodo para criação dos indices no Mongo
        mongoTemplate.indexOps(TrustedBinding.class) // Trabalhar com os indices da collection da classe TrustedBinding
                .ensureIndex(new Index() // Garante que o indice exista, se não existir, vai criar
                        .on("expiresAt", Sort.Direction.ASC) // O indice será sobre o campo expiresAt
                        .expire(0)); // O 0 significa que terão 0 segundos extras após a data escolhida para expiração do campo
    }

    // O bloco acima foi criado para que, na collection TrustedBinding, garantir um indice TTL no campos expiresAt
    // para que os documentos expirem automaticamente quando a data definida chegar


}
