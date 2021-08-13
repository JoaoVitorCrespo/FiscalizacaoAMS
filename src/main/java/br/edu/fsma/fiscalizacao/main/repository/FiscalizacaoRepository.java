package br.edu.fsma.fiscalizacao.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fsma.fiscalizacao.main.model.Fiscalizacao;

public interface FiscalizacaoRepository extends JpaRepository<Fiscalizacao, String>{

    Optional<Fiscalizacao> findById(Long id);
}
