package br.edu.fsma.fiscalizacao.main.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fsma.fiscalizacao.main.controller.dto.EmpresaRs;
import br.edu.fsma.fiscalizacao.main.model.Empresa;
import br.edu.fsma.fiscalizacao.main.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    
    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository){
        this.empresaRepository = empresaRepository;
    }


    @CrossOrigin
    @PostMapping("/cadastrar")
    private void gravarEmpresa(@RequestBody EmpresaRs empresaRs) throws Exception{
        var empresatemp = empresaRepository.findByCnpj(empresaRs.getCnpj());
        if(empresatemp.isPresent()){
            throw new Exception("Empresa ja cadastrada");
        } else {
            Empresa empresa = EmpresaRs.getEmpresa(empresaRs);
            empresaRepository.save(empresa);
        }
    }

    @CrossOrigin
    @GetMapping("/")
    private List<EmpresaRs> getEmpresas(){
        List<Empresa> empresas = empresaRepository.findAll();
        List<EmpresaRs> empresasRs = new ArrayList<EmpresaRs>();

        for(Empresa empresa: empresas){
            empresasRs.add(EmpresaRs.getEmpresaRs(empresa));
        }

        return empresasRs;
    }

    @CrossOrigin
    @GetMapping("/excluir/{cnpj}")
    private void excluirMunicipio(@PathVariable("cnpj") String cnpj) throws Exception{
        var empresatemp = empresaRepository.findByCnpj(cnpj);
        if(empresatemp.isPresent()){
            Empresa emp = empresatemp.get();
            empresaRepository.delete(emp);
        } else {
            throw new Exception("Empresa nao encontrada");
        }
    }

    @CrossOrigin
    @GetMapping("/atualizar")
    private void atualizarMunicipios() throws Exception{
        System.out.println("Atualizando Municipios");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("fiscalizacoes.csv")));
        String linha = null;
        Integer contador = 0;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if(dados.length == 9 && contador>0){
                var empresatemp = empresaRepository.findByCnpj(dados[2]);
                if(empresatemp.isPresent()){
                } else {
                    Empresa empresa = new Empresa();
                    empresa.setCnpj(dados[2]);
                    empresa.setRazaoSocial(dados[3]);
                    empresaRepository.save(empresa);
                }
            }
            contador++;
        }
        reader.close();
    }
}
