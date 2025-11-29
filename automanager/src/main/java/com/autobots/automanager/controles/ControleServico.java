package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdicionarLinkServico;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/servico")
public class ControleServico {

    @Autowired
    private RepositorioServico repositorio;

    @Autowired
    private AdicionarLinkServico adicionadorLink;
    
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    
    @Autowired
    private RepositorioVenda repositorioVenda;

    @GetMapping("/{id}")
    public ResponseEntity<Servico> obterServico(@PathVariable Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico != null) {
            adicionadorLink.adicionarLink(servico);
            return new ResponseEntity<>(servico, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> lista = repositorio.findAll();
        if (!lista.isEmpty()) {
            adicionadorLink.adicionarLink(lista);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        if (servico.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        repositorio.save(servico);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        if (servico == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            empresa.getServicos().remove(servico);
        }
        
        for (Venda venda : repositorioVenda.findAll()) {
            venda.getServicos().remove(servico);
        }
        
        repositorio.delete(servico);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
