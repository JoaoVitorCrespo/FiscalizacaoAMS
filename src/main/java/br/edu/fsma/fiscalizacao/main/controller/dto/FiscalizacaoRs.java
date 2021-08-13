package br.edu.fsma.fiscalizacao.main.controller.dto;

import java.time.LocalDate;

import br.edu.fsma.fiscalizacao.main.model.Bairro;
import br.edu.fsma.fiscalizacao.main.model.Empresa;
import br.edu.fsma.fiscalizacao.main.model.Fiscalizacao;
import br.edu.fsma.fiscalizacao.main.model.Municipio;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;

public class FiscalizacaoRs {

    private String id;
    private String data;
    private String logradouro;
    private String cep;
    private String empresa;
    private String bairro;
    private String municipio;
    private String uf;


    public static Fiscalizacao getFiscalizacao(FiscalizacaoRs fiscalizacaoRs, Empresa empresa, Bairro bairro, Municipio municipio, UnidadeFederativa uf){
        Fiscalizacao fiscalizacao = new Fiscalizacao();
        fiscalizacao.setData(LocalDate.parse(fiscalizacaoRs.getData()));
        fiscalizacao.setLogradouro(fiscalizacaoRs.getLogradouro());
        fiscalizacao.setCep(fiscalizacaoRs.getCep());
        fiscalizacao.setEmpresa(empresa);
        fiscalizacao.setBairro(bairro);
        fiscalizacao.setMunicipio(municipio);
        fiscalizacao.setUf(uf);
        return fiscalizacao;
    }

    public static FiscalizacaoRs getFiscalizacaoRs(Fiscalizacao fiscalizacao){
        FiscalizacaoRs fiscalizacaoRs = new FiscalizacaoRs();
        fiscalizacaoRs.setId(String.valueOf(fiscalizacao.getId()));
        fiscalizacaoRs.setData(String.valueOf(fiscalizacao.getData()));
        fiscalizacaoRs.setLogradouro(fiscalizacao.getLogradouro());
        fiscalizacaoRs.setCep(fiscalizacao.getCep());
        fiscalizacaoRs.setEmpresa(fiscalizacao.getEmpresa().getCnpj());
        fiscalizacaoRs.setBairro(fiscalizacao.getBairro().getNome());
        fiscalizacaoRs.setMunicipio(fiscalizacao.getMunicipio().getNome());
        fiscalizacaoRs.setUf(fiscalizacao.getUf().getNome());
        return fiscalizacaoRs;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getMunicipio() {
        return municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
}
