package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleVenda;
import com.autobots.automanager.entitades.Venda;

@Component
public class AdicionarLinkVenda {

    public void adicionarLink(Venda venda) {
        venda.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleVenda.class)
                    .obterVenda(venda.getId())
            ).withSelfRel()
        );

        venda.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleVenda.class)
                    .obterVendas()
            ).withRel("vendas")
        );
    }

    public void adicionarLink(List<Venda> vendas) {
        for (Venda venda : vendas) {
            adicionarLink(venda);
        }
    }
}
