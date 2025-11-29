package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdicionarLinkVenda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/venda")
public class ControleVenda {

    @Autowired
    private RepositorioVenda repositorio;

    @Autowired
    private AdicionarLinkVenda adicionadorLink;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;
    
    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    @GetMapping("/{id}")
    public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);

        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(venda);
        return new ResponseEntity<>(venda, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> obterVendas() {
        List<Venda> lista = repositorio.findAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        if (venda.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        repositorio.save(venda);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVenda(@RequestBody Venda atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        if (venda == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove venda de todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getVendas().remove(venda);
        }
        
        // Remove venda de todas as empresas
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            empresa.getVendas().remove(venda);
        }
        
        // Remove venda de todos os veículos
        for (Veiculo veiculo : repositorioVeiculo.findAll()) {
            veiculo.getVendas().remove(venda);
        }
        
        repositorio.delete(venda);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
