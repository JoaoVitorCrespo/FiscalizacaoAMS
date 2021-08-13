package br.edu.fsma.fiscalizacao.main.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;

public interface UnidadeFederativaRepository extends JpaRepository<UnidadeFederativa, String>{
    

    Optional<UnidadeFederativa> findByNomeAndSigla(String nome, String sigla);
    Optional<UnidadeFederativa> findByNome(String nome);
    Optional<UnidadeFederativa> findBySigla(String sigla);
}
