package com.pixsecurity.pix_security.customer.infrastructure;

import com.pixsecurity.pix_security.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

//Repository é a ponte entre o código e o banco de dados, permite salvar customer, buscar ID, listar, deletar
public interface CustomerRepository extends JpaRepository<Customer, UUID>  {

    boolean existsByCpf(String cpf);
}
