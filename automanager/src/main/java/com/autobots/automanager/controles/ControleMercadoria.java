package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdionarLinkMercadoria;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/mercadoria")
public class ControleMercadoria {

    @Autowired
    private RepositorioMercadoria repositorio;

    @Autowired
    private AdionarLinkMercadoria adicionadorLink;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    
    @Autowired
    private RepositorioVenda repositorioVenda;

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(mercadoria);
        return new ResponseEntity<>(mercadoria, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Mercadoria>> obterMercadorias() {
        List<Mercadoria> lista = repositorio.findAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        if (mercadoria.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        repositorio.save(mercadoria);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMercadoria(@PathVariable Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        if (mercadoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove mercadoria de todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getMercadorias().remove(mercadoria);
        }
        
        // Remove mercadoria de todas as empresas
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            empresa.getMercadorias().remove(mercadoria);
        }
        
        // Remove mercadoria de todas as vendas
        for (Venda venda : repositorioVenda.findAll()) {
            venda.getMercadorias().remove(mercadoria);
        }
        
        repositorio.delete(mercadoria);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
