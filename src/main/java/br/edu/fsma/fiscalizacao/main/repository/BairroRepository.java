package br.edu.fsma.fiscalizacao.main.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fsma.fiscalizacao.main.model.Bairro;
import br.edu.fsma.fiscalizacao.main.model.Municipio;

public interface BairroRepository extends JpaRepository<Bairro, String>{
    
    Optional<Bairro> findByNome(String nome);
    Optional<Bairro> findByNomeAndMunicipio(String nome, Municipio Municipio);
}
