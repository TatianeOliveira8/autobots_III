package com.autobots.automanager.modelos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleServico;
import com.autobots.automanager.entitades.Servico;

@Component
public class AdicionarLinkServico {

    public void adicionarLink(Servico servico) {
        Link selfLink = linkTo(methodOn(ControleServico.class).obterServico(servico.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ControleServico.class).listarServicos()).withRel("servicos");
        servico.add(selfLink);
        servico.add(allLink);
    }

    public void adicionarLink(List<Servico> servicos) {
        for (Servico servico : servicos) {
            adicionarLink(servico);
        }
    }
}
