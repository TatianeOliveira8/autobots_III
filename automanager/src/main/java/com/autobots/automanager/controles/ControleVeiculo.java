package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.entitades.Veiculo;
import com.autobots.automanager.entitades.Venda;
import com.autobots.automanager.modelos.AdicionarLinkVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/veiculo")
public class ControleVeiculo {

    @Autowired
    private RepositorioVeiculo repositorio;

    @Autowired
    private AdicionarLinkVeiculo adicionadorLink;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioVenda repositorioVenda;

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);

        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(veiculo);
        return new ResponseEntity<>(veiculo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> obterVeiculos() {
        List<Veiculo> lista = repositorio.findAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        repositorio.save(veiculo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove veículo de todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getVeiculos().remove(veiculo);
        }
        
        // Remove referência do veículo em todas as vendas
        for (Venda venda : repositorioVenda.findAll()) {
            if (venda.getVeiculo() != null && venda.getVeiculo().getId().equals(id)) {
                venda.setVeiculo(null);
            }
        }
        
        repositorio.delete(veiculo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
