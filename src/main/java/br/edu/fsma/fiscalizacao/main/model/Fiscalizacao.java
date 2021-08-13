package br.edu.fsma.fiscalizacao.main.model;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Fiscalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private String logradouro;
    private String cep;

    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Bairro bairro;
    @ManyToOne
    private Municipio municipio;
    @ManyToOne
    private UnidadeFederativa uf;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
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
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public Bairro getBairro() {
        return bairro;
    }
    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }
    public Municipio getMunicipio() {
        return municipio;
    }
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    public UnidadeFederativa getUf() {
        return uf;
    }
    public void setUf(UnidadeFederativa uf) {
        this.uf = uf;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fiscalizacao other = (Fiscalizacao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Fiscalizacao [logradouro=" + logradouro + "]";
    }
    
}
