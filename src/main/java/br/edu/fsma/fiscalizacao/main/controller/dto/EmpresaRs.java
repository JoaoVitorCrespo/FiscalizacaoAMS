package br.edu.fsma.fiscalizacao.main.controller.dto;

import br.edu.fsma.fiscalizacao.main.model.Empresa;

public class EmpresaRs {
    
    private String id;
    private String cnpj;
    private String razaoSocial;

    public static Empresa getEmpresa(EmpresaRs empresaRs){
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaRs.getCnpj());
        empresa.setRazaoSocial(empresaRs.getRazaoSocial());
        return empresa;
    }

    public static EmpresaRs getEmpresaRs(Empresa empresa){
        EmpresaRs empresaRs = new EmpresaRs();
        empresaRs.setId(String.valueOf(empresa.getId()));
        empresaRs.setCnpj(empresa.getCnpj());
        empresaRs.setRazaoSocial(empresa.getRazaoSocial());
        return empresaRs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    } 
}
