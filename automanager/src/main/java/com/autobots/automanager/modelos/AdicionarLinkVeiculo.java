package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleVeiculo;
import com.autobots.automanager.entitades.Veiculo;

@Component
public class AdicionarLinkVeiculo {

    public void adicionarLink(Veiculo veiculo) {
        veiculo.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleVeiculo.class)
                    .obterVeiculo(veiculo.getId())
            ).withSelfRel()
        );

        veiculo.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleVeiculo.class)
                    .obterVeiculos()
            ).withRel("veiculos")
        );
    }

    public void adicionarLink(List<Veiculo> veiculos) {
        for (Veiculo v : veiculos) {
            adicionarLink(v);
        }
    }
}
