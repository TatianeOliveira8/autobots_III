package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.CredencialCodigoBarra;
import com.autobots.automanager.entitades.CredencialUsuarioSenha;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioCredencialCodigoBarra;
import com.autobots.automanager.repositorios.RepositorioCredencialUsuarioSenha;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/credencial")
public class ControleCredencial {

    @Autowired
    private RepositorioCredencialCodigoBarra repositorioCodigoBarra;

    @Autowired
    private RepositorioCredencialUsuarioSenha repositorioUsuarioSenha;
    
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    // --- CredencialCodigoBarra ---
    @GetMapping("/codigobarra/{id}")
    public ResponseEntity<CredencialCodigoBarra> obterCodigoBarra(@PathVariable Long id) {
        CredencialCodigoBarra cred = repositorioCodigoBarra.findById(id).orElse(null);
        return cred != null
            ? new ResponseEntity<>(cred, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/codigobarra")
    public ResponseEntity<List<CredencialCodigoBarra>> listarCodigoBarra() {
        List<CredencialCodigoBarra> lista = repositorioCodigoBarra.findAll();
        return !lista.isEmpty()
            ? new ResponseEntity<>(lista, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/codigobarra/cadastro")
    public ResponseEntity<?> cadastrarCodigoBarra(@RequestBody CredencialCodigoBarra cred) {
        if (cred.getId() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        repositorioCodigoBarra.save(cred);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/codigobarra/atualizar")
    public ResponseEntity<?> atualizarCodigoBarra(@RequestBody CredencialCodigoBarra cred) {
        if (cred.getId() == null || !repositorioCodigoBarra.existsById(cred.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        repositorioCodigoBarra.save(cred);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/codigobarra/{id}")
    public ResponseEntity<?> deletarCodigoBarra(@PathVariable Long id) {
        CredencialCodigoBarra cred = repositorioCodigoBarra.findById(id).orElse(null);
        if (cred == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove credencial de todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getCredenciais().remove(cred);
        }
        
        repositorioCodigoBarra.delete(cred);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // --- CredencialUsuarioSenha ---
    @GetMapping("/usuariosenha/{id}")
    public ResponseEntity<CredencialUsuarioSenha> obterUsuarioSenha(@PathVariable Long id) {
        CredencialUsuarioSenha cred = repositorioUsuarioSenha.findById(id).orElse(null);
        return cred != null
            ? new ResponseEntity<>(cred, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/usuariosenha")
    public ResponseEntity<List<CredencialUsuarioSenha>> listarUsuarioSenha() {
        List<CredencialUsuarioSenha> lista = repositorioUsuarioSenha.findAll();
        return !lista.isEmpty()
            ? new ResponseEntity<>(lista, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/usuariosenha/cadastro")
    public ResponseEntity<?> cadastrarUsuarioSenha(@RequestBody CredencialUsuarioSenha cred) {
        if (cred.getId() != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        repositorioUsuarioSenha.save(cred);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/usuariosenha/atualizar")
    public ResponseEntity<?> atualizarUsuarioSenha(@RequestBody CredencialUsuarioSenha cred) {
        if (cred.getId() == null || !repositorioUsuarioSenha.existsById(cred.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        repositorioUsuarioSenha.save(cred);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/usuariosenha/{id}")
    public ResponseEntity<?> deletarUsuarioSenha(@PathVariable Long id) {
        CredencialUsuarioSenha cred = repositorioUsuarioSenha.findById(id).orElse(null);
        if (cred == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Remove credencial de todos os usuários
        for (Usuario usuario : repositorioUsuario.findAll()) {
            usuario.getCredenciais().remove(cred);
        }
        
        repositorioUsuarioSenha.delete(cred);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
