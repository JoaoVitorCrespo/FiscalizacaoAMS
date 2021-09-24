package br.edu.fsma.fiscalizacao.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fsma.fiscalizacao.main.controller.dto.UnidadeFederativaRs;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;
import br.edu.fsma.fiscalizacao.main.repository.UnidadeFederativaRepository;


@RestController
@RequestMapping("/uf")
public class UnidadeFederativaController {
    
    private final UnidadeFederativaRepository unidadeFederativaRepository;

    public UnidadeFederativaController(UnidadeFederativaRepository unidadeFederativaRepository){
        this.unidadeFederativaRepository = unidadeFederativaRepository;
    }

    @CrossOrigin
    @PostMapping("/cadastrar")
    private void gravarUf(@RequestBody UnidadeFederativaRs unidadeFederativaRs) throws Exception{
        var uftemp = unidadeFederativaRepository.findByNomeAndSigla(unidadeFederativaRs.getNome(), unidadeFederativaRs.getSigla());
        if(uftemp.isPresent()){
            throw new Exception("UF já cadastrado");
        } else {
            UnidadeFederativa uf = UnidadeFederativaRs.getUnidadeFederativa(unidadeFederativaRs);
            unidadeFederativaRepository.save(uf);
        }
    }


    @CrossOrigin
    @GetMapping("/")
    private List<UnidadeFederativaRs> getUnidadesFederativas(){
        List<UnidadeFederativa> ufs = unidadeFederativaRepository.findAll();
        List<UnidadeFederativaRs> ufrs = new ArrayList<UnidadeFederativaRs>();
        
        for (UnidadeFederativa u: ufs){
            ufrs.add(UnidadeFederativaRs.getUnidadeFederativaRs(u));
        }

        return ufrs;
    }

    @CrossOrigin
    @GetMapping("/excluir/{estado}")
    private void excluirUf(@PathVariable("estado") String estado) throws Exception{
        var uftemp1 = unidadeFederativaRepository.findByNome(estado);
        var uftemp2 = unidadeFederativaRepository.findBySigla(estado);
        if(uftemp1.isPresent()){
            UnidadeFederativa uf = uftemp1.get();
            unidadeFederativaRepository.delete(uf);
        } else{
            if(uftemp2.isPresent()){
                UnidadeFederativa uf = uftemp2.get();
                unidadeFederativaRepository.delete(uf);
            } else {
                throw new Exception("Estado nao encontrado");
            }
        }
    }

    @CrossOrigin
    @GetMapping("/editar/{id}")
    private void editarUF(@RequestBody UnidadeFederativaRs unidadeFederativaRs, @PathVariable("id") Long id) throws Exception{
        UnidadeFederativa uf = UnidadeFederativaRs.getUnidadeFederativa(unidadeFederativaRs);
        uf.setId(id);
        unidadeFederativaRepository.save(uf);
    }


    @CrossOrigin
    @GetMapping("/atualizar")
    private void atualizarUfs() throws Exception{
        System.out.println("Atualizando Estados");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("fiscalizacoes.csv")));
        String linha = null;
        Integer contador = 0;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if(dados.length == 9 && contador>0){
                var uftemp = unidadeFederativaRepository.findByNome(dados[8]);
                if(uftemp.isPresent()){ 
                } else {
                    UnidadeFederativa uf = new UnidadeFederativa();
                    uf.setNome(dados[8]);
                    uf.setSigla("SC");
                    unidadeFederativaRepository.save(uf);
                }
            }
            contador++;
        }
        reader.close();
    }

}
