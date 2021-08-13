package br.edu.fsma.fiscalizacao.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fsma.fiscalizacao.main.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    
   Optional<Empresa> findByCnpj(String cnpj);

}
