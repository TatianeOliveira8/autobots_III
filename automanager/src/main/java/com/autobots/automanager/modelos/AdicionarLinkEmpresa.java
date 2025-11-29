package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ControleEmpresa;
import com.autobots.automanager.entitades.Empresa;

@Component
public class AdicionarLinkEmpresa {

    public void adicionarLink(Empresa empresa) {
        empresa.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleEmpresa.class)
                    .obterEmpresa(empresa.getId())
            ).withSelfRel()
        );

        empresa.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ControleEmpresa.class)
                    .obterEmpresas()
            ).withRel("empresas")
        );
    }

    public void adicionarLink(List<Empresa> empresas) {
        for (Empresa empresa : empresas) {
            adicionarLink(empresa);
        }
    }
}
