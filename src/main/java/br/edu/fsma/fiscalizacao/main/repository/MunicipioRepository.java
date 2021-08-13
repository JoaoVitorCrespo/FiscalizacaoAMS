package br.edu.fsma.fiscalizacao.main.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fsma.fiscalizacao.main.model.Municipio;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;

public interface MunicipioRepository extends JpaRepository<Municipio, String>{
    
    Optional<Municipio> findByNome(String nome);
    Optional<Municipio> findByNomeAndUf(String nome, UnidadeFederativa uf);
}
