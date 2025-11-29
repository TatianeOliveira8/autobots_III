package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleUsuario;
import com.autobots.automanager.entitades.Usuario;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {

    @Override
    public void adicionarLink(List<Usuario> lista) {
        for (Usuario usuario : lista) {
            adicionarLink(usuario);
        }
    }

    @Override
    public void adicionarLink(Usuario usuario) {
        Long id = usuario.getId();

        // Link para si mesmo
        Link self = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ControleUsuario.class).obterUsuario(id))
                .withSelfRel();
        usuario.add(self);

        // Link para a lista de usuários
        Link todos = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ControleUsuario.class).obterUsuarios())
                .withRel("usuarios");
        usuario.add(todos);

        // Link para atualizar
        Link atualizar = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ControleUsuario.class).atualizarUsuario(usuario))
                .withRel("atualizar");
        usuario.add(atualizar);

        // Link para excluir via id (PathVariable)
        Link excluir = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(ControleUsuario.class).deletarUsuario(id))
                .withRel("excluir");
        usuario.add(excluir);
    }
}
