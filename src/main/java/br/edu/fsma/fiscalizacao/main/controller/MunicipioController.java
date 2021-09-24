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

import br.edu.fsma.fiscalizacao.main.controller.dto.MunicipioRs;
import br.edu.fsma.fiscalizacao.main.model.Municipio;
import br.edu.fsma.fiscalizacao.main.model.UnidadeFederativa;
import br.edu.fsma.fiscalizacao.main.repository.MunicipioRepository;
import br.edu.fsma.fiscalizacao.main.repository.UnidadeFederativaRepository;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {
    
    private final MunicipioRepository municipioRepository;
    private final UnidadeFederativaRepository unidadeFederativaRepository;

    public MunicipioController(MunicipioRepository municipioRepository, UnidadeFederativaRepository unidadeFederativaRepository){
        this.municipioRepository = municipioRepository;
        this.unidadeFederativaRepository = unidadeFederativaRepository;
    }

    @CrossOrigin
    @PostMapping("/cadastrar")
    private void gravarMunicipio(@RequestBody MunicipioRs municipioRs) throws Exception{
        var municipiotemp = municipioRepository.findByNome(municipioRs.getNome());
        if(municipiotemp.isPresent()){
            throw new Exception("Municipio já cadastrado");
        } else {
            var uftemp = unidadeFederativaRepository.findByNome(municipioRs.getUf());
            if(uftemp.isPresent()){
                UnidadeFederativa uf = uftemp.get();
                Municipio municipio = MunicipioRs.getMunicipio(municipioRs, uf);
                municipioRepository.save(municipio);
            } else {
                throw new Exception("UF não encontrado");
            }
        }
    }


    @CrossOrigin
    @GetMapping("/")
    private List<MunicipioRs> getMunicipios(){
        List<Municipio> municipios = municipioRepository.findAll();
        List<MunicipioRs> municipiosRs = new ArrayList<MunicipioRs>();

        for(Municipio municipio: municipios){
            municipiosRs.add(MunicipioRs.getMunicipioRs(municipio));
        }

        return municipiosRs;
    }

    @CrossOrigin
    @GetMapping("/editar/{id}")
    private void editarMunicipio(@RequestBody MunicipioRs municipioRs, @PathVariable("id") Long id) throws Exception{
        var uftemp = unidadeFederativaRepository.findByNome(municipioRs.getUf());
        if(uftemp.isPresent()){
            UnidadeFederativa uf = uftemp.get();
            Municipio municipio = MunicipioRs.getMunicipio(municipioRs, uf);
            municipio.setId(id);
            municipioRepository.save(municipio);
        } else {
            throw new Exception("UF não encontrado");
        }
    
    }

    @CrossOrigin
    @GetMapping("/excluir/{municipio}")
    private void excluirMunicipio(@PathVariable("municipio") String municipio) throws Exception{
        var municipiotemp = municipioRepository.findByNome(municipio);
        if(municipiotemp.isPresent()){
            Municipio mu = municipiotemp.get();
            municipioRepository.delete(mu);
        } else {
            throw new Exception("Municipio nao encontrado");
        }
    }

    @CrossOrigin
    @GetMapping("/atualizar")
    private void atualizarMunicipios() throws Exception{
        System.out.println("Atualizando Empresas");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("fiscalizacoes.csv")));
        String linha = null;
        Integer contador = 0;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if(dados.length == 9 && contador>0){
                var uftemp = unidadeFederativaRepository.findByNome(dados[8]);
                if(uftemp.isPresent()){
                    var municipiotemp = municipioRepository.findByNomeAndUf(dados[7], uftemp.get());
                    if(municipiotemp.isPresent()){
                    } else {
                        Municipio mu = new Municipio();
                        mu.setNome(dados[7]);
                        if(uftemp.isPresent()){
                        mu.setUf(uftemp.get());
                         }
                        municipioRepository.save(mu);
                    }
                }
                
            }
            contador++;
        }
        reader.close();
    }
}
