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

import br.edu.fsma.fiscalizacao.main.controller.dto.BairroRs;
import br.edu.fsma.fiscalizacao.main.model.Bairro;
import br.edu.fsma.fiscalizacao.main.model.Municipio;
import br.edu.fsma.fiscalizacao.main.repository.BairroRepository;
import br.edu.fsma.fiscalizacao.main.repository.MunicipioRepository;

@RestController
@RequestMapping("/bairro")
public class BairroController {

    private final BairroRepository bairroRepository;
    private final MunicipioRepository municipioRepository;

    public BairroController(BairroRepository bairroRepository, MunicipioRepository municipioRepository){
        this.bairroRepository = bairroRepository;
        this.municipioRepository = municipioRepository;
    }


    @CrossOrigin
    @PostMapping("/cadastrar")
    private void gravarBairro(@RequestBody BairroRs bairroRs) throws Exception{
        var bairrotemp = bairroRepository.findByNome(bairroRs.getNome());
        if(bairrotemp.isPresent()){
            throw new Exception("Bairro ja cadastrado");
        } else {
            var municipiotemp = municipioRepository.findByNome(bairroRs.getMunicipio());
            if(municipiotemp.isPresent()){
                Municipio municipio = municipiotemp.get();
                Bairro bairro = BairroRs.getBairro(bairroRs, municipio);
                bairroRepository.save(bairro);
            } else {
                throw new Exception("Municipio nao encontrado");
            }
        }
    }

    @CrossOrigin
    @GetMapping("/")
    private List<BairroRs> getBairros(){
        List<Bairro> bairros = bairroRepository.findAll();
        List<BairroRs> bairrosRs = new ArrayList<BairroRs>();
        
        for(Bairro bairro: bairros){
            bairrosRs.add(BairroRs.getBairroRs(bairro));
        }

        return bairrosRs;
    }

    @CrossOrigin
    @GetMapping("/excluir/{bairro}")
    private void excluirMunicipio(@PathVariable("bairro") String bairro) throws Exception{
        var bairrotemp = bairroRepository.findByNome(bairro);
        if(bairrotemp.isPresent()){
            Bairro ba = bairrotemp.get();
            bairroRepository.delete(ba);
        } else {
            throw new Exception("Bairro nao encontrado");
        }
    }

    @CrossOrigin
    @GetMapping("/atualizar")
    private void atualizarMunicipios() throws Exception{
        System.out.println("Atualizando Bairros");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("fiscalizacoes.csv")));
        String linha = null;
        Integer contador = 0;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if(dados.length == 9 && contador>0){
                var municipiotemp = municipioRepository.findByNome(dados[7]);
                if(municipiotemp.isPresent()){
                    var bairrotemp = bairroRepository.findByNomeAndMunicipio(dados[6], municipiotemp.get());
                    if(bairrotemp.isPresent()){
                    } else {
                        Bairro ba = new Bairro();
                        ba.setNome(dados[6]);
                        if(municipiotemp.isPresent()){
                        ba.setMunicipio(municipiotemp.get());
                         }
                        bairroRepository.save(ba);
                    }
                }
                
            }
            contador++;
        }
        reader.close();
    }
    
}
