/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimplesORM;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilCRCStringFiltros;

import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Salvio
 */
@Entity
@InfoObjetoSB(tags = {"Cliente", "Cliente Coletivo Java"}, plural = "Clientes")
public class Cliente extends EntidadeSimplesORM implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nome;
    private String nomePasta;
    private String servidorGitCodigoFonte;

    private String servicorGitRelease;

    @OneToMany(mappedBy = "cliente")
    private List<Projeto> projetos;

    public Cliente() {
        super();
        projetos = new ArrayList<>();
    }

    public Cliente(Long id, String nome, List<Projeto> projetos) {
        this();
        this.id = id;
        this.nome = nome;
        this.projetos = projetos;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public String getServidorGitCodigoFonte() {
        return servidorGitCodigoFonte;
    }

    public void setServidorGitCodigoFonte(String servidorGitCodigoFonte) {
        this.servidorGitCodigoFonte = servidorGitCodigoFonte;
    }

    public String getServicorGitRelease() {
        return servicorGitRelease;
    }

    public void setServicorGitRelease(String servicorGitRelease) {

        this.servicorGitRelease = servicorGitRelease;
    }

    public String getCaminhoPastaClinte() {
        if (getNome() == null) {
            throw new UnsupportedOperationException("O nome do cliente não foi definido, impossível definir a pasta do cliente");
        }
        return Desenvolvedor.PASTADEVELOPER + "/" + getNomePasta();

    }

    public String getCaminhoPastaClinteSource() {
        if (getNome() == null) {
            throw new UnsupportedOperationException("O nome do cliente não foi definido, impossível definir a pasta do cliente");
        }
        return Desenvolvedor.PASTADEVELOPER + "/" + getNomePasta() + "/source";

    }

    public String getCaminhoPastaClinteRelease() {
        if (getNome() == null) {
            throw new UnsupportedOperationException("O nome do cliente não foi definido, impossível definir a pasta do cliente");
        }
        return Desenvolvedor.PASTADEVELOPER + "/" + getNomePasta() + "/release";

    }

    public String getNomePasta() {

        nomePasta = UtilCRCStringFiltros.gerarUrlAmigavel(getNome());

        return nomePasta;
    }

}
