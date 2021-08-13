package br.edu.fsma.fiscalizacao.main.controller.dto;

import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;

public class UnidadeFederativaRs {
    
    private String id;
    private String sigla;
    private String nome;

    public static UnidadeFederativa getUnidadeFederativa(UnidadeFederativaRs unidadeFederativaRs){
        UnidadeFederativa unidadeFederativa = new UnidadeFederativa();
        unidadeFederativa.setNome(unidadeFederativaRs.getNome());
        unidadeFederativa.setSigla(unidadeFederativaRs.getSigla());
        return unidadeFederativa;
    }

    public static UnidadeFederativaRs getUnidadeFederativaRs(UnidadeFederativa unidadeFederativa){
        UnidadeFederativaRs unidadeFederativaRs = new UnidadeFederativaRs();
        unidadeFederativaRs.setId(String.valueOf(unidadeFederativa.getId()));
        unidadeFederativaRs.setNome(unidadeFederativa.getNome());
        unidadeFederativaRs.setSigla(unidadeFederativa.getSigla());
        return unidadeFederativaRs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
