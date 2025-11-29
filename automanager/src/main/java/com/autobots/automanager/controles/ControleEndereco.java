package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/endereco")
public class ControleEndereco {

    @Autowired
    private RepositorioEndereco repositorio;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
        Endereco endereco = repositorio.findById(id).orElse(null);
        if (endereco != null) {
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> obterEnderecos() {
        List<Endereco> lista = repositorio.findAll();
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        if (endereco.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        repositorio.save(endereco);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEndereco(@PathVariable Long id) {
        Endereco endereco = repositorio.findById(id).orElse(null);
        if (endereco == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove referência do endereço em todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            if (usuario.getEndereco() != null && usuario.getEndereco().getId().equals(id)) {
                usuario.setEndereco(null);
            }
        }
        
        // Remove referência do endereço em todas as empresas
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            if (empresa.getEndereco() != null && empresa.getEndereco().getId().equals(id)) {
                empresa.setEndereco(null);
            }
        }
        
        repositorio.delete(endereco);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
