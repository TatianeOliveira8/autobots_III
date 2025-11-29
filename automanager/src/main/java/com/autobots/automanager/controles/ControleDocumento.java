package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/documento")
public class ControleDocumento {

    @Autowired
    private RepositorioDocumento repositorio;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = repositorio.findAll();
        if (documentos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documentos);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento) {
        if (documento.getId() != null) {
            return ResponseEntity.status(409).build(); 
        }
        repositorio.save(documento);
        return ResponseEntity.status(201).build(); 
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
        if (atualizacao.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return repositorio.findById(atualizacao.getId())
                .map(existente -> {
                    if (atualizacao.getTipo() != null) existente.setTipo(atualizacao.getTipo());
                    if (atualizacao.getNumero() != null) existente.setNumero(atualizacao.getNumero());
                    if (atualizacao.getDataEmissao() != null) existente.setDataEmissao(atualizacao.getDataEmissao());

                    repositorio.save(existente);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDocumento(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(documento -> {
                    for (Usuario usuario : repositorioUsuario.findAll()) {
                        if (usuario.getDocumentos().removeIf(d -> d.getId().equals(id))) {
                            repositorioUsuario.save(usuario);
                        }
                    }
                    repositorio.delete(documento);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
