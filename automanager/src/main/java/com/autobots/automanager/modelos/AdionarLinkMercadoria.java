package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleMercadoria;
import com.autobots.automanager.entitades.Mercadoria;

@Component
public class AdionarLinkMercadoria {

    public void adicionarLink(Mercadoria mercadoria) {
        mercadoria.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleMercadoria.class)
                    .obterMercadoria(mercadoria.getId())
            ).withSelfRel()
        );

        mercadoria.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleMercadoria.class)
                    .obterMercadorias()
            ).withRel("mercadorias")
        );
    }

    public void adicionarLink(List<Mercadoria> mercadorias) {
        for (Mercadoria mercadoria : mercadorias) {
            adicionarLink(mercadoria);
        }
    }
}
