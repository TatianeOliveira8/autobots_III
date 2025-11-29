package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.modelos.AdicionarLinkEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@RestController
@RequestMapping("/empresa")
public class ControleEmpresa {

    @Autowired
    private RepositorioEmpresa repositorio;

    @Autowired
    private AdicionarLinkEmpresa adicionadorLink;

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);

        if (empresa == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(empresa);
        return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> obterEmpresas() {
        List<Empresa> lista = repositorio.findAll();

        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(lista);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
        if (empresa.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        repositorio.save(empresa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarEmpresa(@RequestBody Empresa atualizacao) {
        if (atualizacao.getId() == null || !repositorio.existsById(atualizacao.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        Empresa empresa = repositorio.findById(id).orElse(null);

        if (empresa != null) {
            repositorio.delete(empresa);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
