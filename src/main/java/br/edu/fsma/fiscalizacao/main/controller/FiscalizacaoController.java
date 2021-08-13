package br.edu.fsma.fiscalizacao.main.controller;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.edu.fsma.fiscalizacao.main.controller.dto.FiscalizacaoRs;
import br.edu.fsma.fiscalizacao.main.model.Fiscalizacao;
import br.edu.fsma.fiscalizacao.main.repository.BairroRepository;
import br.edu.fsma.fiscalizacao.main.repository.EmpresaRepository;
import br.edu.fsma.fiscalizacao.main.repository.FiscalizacaoRepository;
import br.edu.fsma.fiscalizacao.main.repository.MunicipioRepository;
import br.edu.fsma.fiscalizacao.main.repository.UnidadeFederativaRepository;

@RestController
@RequestMapping("/fiscalizacao")
public class FiscalizacaoController {

    private final FiscalizacaoRepository fiscalizacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final BairroRepository bairroRepository;
    private final MunicipioRepository municipioRepository;
    private final UnidadeFederativaRepository unidadeFederativaRepository;

    public FiscalizacaoController(FiscalizacaoRepository fiscalizacaoRepository, EmpresaRepository empresaRepository,
            BairroRepository bairroRepository, MunicipioRepository municipioRepository,
            UnidadeFederativaRepository unidadeFederativaRepository) {
        this.fiscalizacaoRepository = fiscalizacaoRepository;
        this.empresaRepository = empresaRepository;
        this.bairroRepository = bairroRepository;
        this.municipioRepository = municipioRepository;
        this.unidadeFederativaRepository = unidadeFederativaRepository;
    }

    @CrossOrigin
    @PostMapping("/cadastrar")
    private void gravarFiscalizacao(@RequestBody FiscalizacaoRs fiscalizacaoRs) throws Exception {
        var empresatemp = empresaRepository.findByCnpj(fiscalizacaoRs.getEmpresa());
        if (empresatemp.isPresent()) {
            var uftemp = unidadeFederativaRepository.findByNome(fiscalizacaoRs.getUf());
            if (uftemp.isPresent()) {
                var municipiotemp = municipioRepository.findByNomeAndUf(fiscalizacaoRs.getMunicipio(), uftemp.get());
                if (municipiotemp.isPresent()) {
                    var bairrotemp = bairroRepository.findByNomeAndMunicipio(fiscalizacaoRs.getBairro(),
                            municipiotemp.get());
                    if (bairrotemp.isPresent()) {
                        Fiscalizacao fiscalizacao = FiscalizacaoRs.getFiscalizacao(fiscalizacaoRs, empresatemp.get(),
                                bairrotemp.get(), municipiotemp.get(), uftemp.get());
                        fiscalizacaoRepository.save(fiscalizacao);
                    } else {
                        throw new Exception("Bairro nao encontrado");
                    }
                } else {
                    throw new Exception("Municipio nao encontrado");
                }
            } else {
                throw new Exception("UF nao encontrado");
            }
        } else {
            throw new Exception("Empresa nao encontrada");
        }

    }

    @CrossOrigin
    @GetMapping("/")
    private List<FiscalizacaoRs> getFiscalizacoes() {
        List<Fiscalizacao> fiscalizacoes = fiscalizacaoRepository.findAll();
        List<FiscalizacaoRs> fiscalizacoesRs = new ArrayList<FiscalizacaoRs>();

        for (Fiscalizacao fiscalizacao : fiscalizacoes) {
            fiscalizacoesRs.add(FiscalizacaoRs.getFiscalizacaoRs(fiscalizacao));
        }
        return fiscalizacoesRs;
    }

    @CrossOrigin
    @GetMapping("/excluir/{id}")
    private void excluirFiscalizacao(@PathVariable("id") Long id) throws Exception {
        var fiscalizacaotemp = fiscalizacaoRepository.findById(id);
        if (fiscalizacaotemp.isPresent()) {
            Fiscalizacao fi = fiscalizacaotemp.get();
            fiscalizacaoRepository.delete(fi);
        } else {
            throw new Exception("Fiscalizacao nao encontrado");
        }
    }

    @CrossOrigin
    @GetMapping("/atualizar")
    private void atualizarFiscalizacoes() throws Exception {
        System.out.println("Atualizando fiscalizacoes");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("fiscalizacoes.csv")));
        String linha = null;
        Integer contador = 0;
        while ((linha = reader.readLine()) != null) {
            String[] dados = linha.split(";");
            if (dados.length == 9 && contador > 0) {
                var municipiotemp = municipioRepository.findByNome(dados[7]);
                if (municipiotemp.isPresent()) {
                    var bairrotemp = bairroRepository.findByNomeAndMunicipio(dados[6], municipiotemp.get());
                    if (bairrotemp.isPresent()) {
                        var uftemp = unidadeFederativaRepository.findByNome(dados[8]);
                        if (uftemp.isPresent()) {
                            var empresatemp = empresaRepository.findByCnpj(dados[2]);
                            if (empresatemp.isPresent()) {
                                Fiscalizacao fiscalizacao = new Fiscalizacao();
                                String newData = dados[1];
                                newData = newData.replace("/", "-");
                                newData = newData.concat("-01");
                                fiscalizacao.setData(LocalDate.parse(newData));
                                fiscalizacao.setCep(dados[5]);
                                fiscalizacao.setLogradouro(dados[4]);
                                fiscalizacao.setBairro(bairrotemp.get());
                                fiscalizacao.setMunicipio(municipiotemp.get());
                                fiscalizacao.setUf(uftemp.get());
                                fiscalizacao.setEmpresa(empresatemp.get());
                                fiscalizacaoRepository.save(fiscalizacao);
                            }
                        }
                    }
                }
            }
            contador++;
        }
        reader.close();
    }
}
