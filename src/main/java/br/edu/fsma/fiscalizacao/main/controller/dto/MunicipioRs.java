package br.edu.fsma.fiscalizacao.main.controller.dto;

import br.edu.fsma.fiscalizacao.main.model.Municipio;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;

public class MunicipioRs {
    
    private String id;
    private String nome;
    private String uf;

    public static Municipio getMunicipio(MunicipioRs municipioRs, UnidadeFederativa uf){
        Municipio municipio = new Municipio();
        municipio.setNome(municipioRs.getNome());
        municipio.setUf(uf);
        return municipio;
    }

    public static MunicipioRs getMunicipioRs(Municipio municipio){
        MunicipioRs municipioRs = new MunicipioRs();
        municipioRs.setId(String.valueOf(municipio.getId()));
        municipioRs.setNome(municipio.getNome());
        municipioRs.setUf(municipio.getUf().getNome());
        return municipioRs;
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
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    
}
