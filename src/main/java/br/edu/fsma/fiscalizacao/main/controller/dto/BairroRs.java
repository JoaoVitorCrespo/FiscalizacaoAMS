package br.edu.fsma.fiscalizacao.main.controller.dto;

import br.edu.fsma.fiscalizacao.main.model.Bairro;
import br.edu.fsma.fiscalizacao.main.model.Municipio;

public class BairroRs {
    
    private String id;
    private String nome;
    private String municipio;

    public static Bairro getBairro(BairroRs bairroRs, Municipio municipio){
        Bairro bairro = new Bairro();
        bairro.setNome(bairroRs.getNome());
        bairro.setMunicipio(municipio);
        return bairro;
    }

    public static BairroRs getBairroRs(Bairro bairro){
        BairroRs bairroRs = new BairroRs();
        bairroRs.setId(String.valueOf(bairro.getId()));
        bairroRs.setNome(bairro.getNome());
        bairroRs.setMunicipio(bairro.getMunicipio().getNome());
        return bairroRs;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getMunicipio() {
        return municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
