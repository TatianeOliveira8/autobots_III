package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/telefone")
public class ControleTelefone {

    @Autowired
    private RepositorioTelefone repositorio;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id) {
        Telefone telefone = repositorio.findById(id).orElse(null);
        if (telefone != null) {
            return new ResponseEntity<>(telefone, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Telefone>> obterTelefones() {
        List<Telefone> lista = repositorio.findAll();
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone) {
        if (telefone.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        repositorio.save(telefone);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTelefone(@PathVariable Long id) {
        Telefone telefone = repositorio.findById(id).orElse(null);
        if (telefone == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getTelefones().remove(telefone);
        }
        
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            empresa.getTelefones().remove(telefone);
        }
        
        repositorio.delete(telefone);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
