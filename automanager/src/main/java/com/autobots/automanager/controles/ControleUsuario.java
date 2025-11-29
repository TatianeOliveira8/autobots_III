package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/usuario")
public class ControleUsuario {

    @Autowired
    private RepositorioUsuario repositorio;

    @Autowired
    private AdicionadorLinkUsuario adicionadorLink;

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adicionadorLink.adicionarLink(usuarios);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (!perfisValidos(usuario.getPerfis())) {
            return new ResponseEntity<>("Perfis inválidos fornecidos.", HttpStatus.BAD_REQUEST);
        }

        repositorio.save(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario atualizacao) {
        if (atualizacao.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Usuario existente = repositorio.findById(atualizacao.getId()).orElse(null);
        if (existente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!perfisValidos(atualizacao.getPerfis())) {
            return new ResponseEntity<>("Perfis inválidos fornecidos.", HttpStatus.BAD_REQUEST);
        }

        repositorio.save(atualizacao);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        for (Empresa empresa : repositorioEmpresa.findAll()) {
            if (empresa.getUsuarios().removeIf(u -> u.getId().equals(id))) {
                repositorioEmpresa.save(empresa);
            }
        }
        
        repositorio.delete(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private boolean perfisValidos(Set<PerfilUsuario> perfis) {
        if (perfis == null) return false;
        for (PerfilUsuario perfil : perfis) {
            if (perfil == null) return false;
            try {
                PerfilUsuario.valueOf(perfil.name());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }
}
