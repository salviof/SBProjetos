/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.controller.getaoProjeto;

import com.super_bits.modulos.SBAcessosModel.model.acoes.AcaoDoSistema;
import com.super_bits.modulos.SBAcessosModel.model.acoes.UtilFabricaDeAcoesAcessosModel;
import com.super_bits.modulosSB.SBCore.modulos.Controller.anotacoes.InfoTipoAcaoGestaoEntidade;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabricaAcoes;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.Requisito;

/**
 *
 * @author salvioF
 */
public enum FabAcaoGestaoProjetos implements ItfFabricaAcoes {

    @InfoTipoAcaoGestaoEntidade()
    REQUISITO_MB_GESTAO,
    REQUISITO_FRM_LISTAR_REQUISITOS_PROXIMA_VERSAO,
    REQUISITO_FRM_LISTAR_REQUISITOS_APROVADOS,
    REQUISITO_FRM_LISTAR_REQUISITOS_SUGERIDOS,
    REQUISITO_FRM_LISTAR_REQUISITOS_REJEITADOS,
    REQUISITO_FRM_DETALHES_REQUISITO,
    REQUISITO_CTR_PROMOVER_REQUISITO,
    REQUISITO_CTR_CRIAR_REQUISITO,
    REQUISITO_CTR_REBAIXAR_REQUISITO,
    REQUISITO_CTR_APROVAR_REQUISITO,
    REUISITIO_CTR_FINALIZAR_REQUISITO,
    PROJETO_MB_GESTAO,
    PROJETO_FRM_LISTAR_PROJETOS,
    PROJETO_DESENVOLVEDOR_FRM_LISTAR,
    PROJETO_DESENVOLVEDOR_CTRL_ALTERAR_HORAS_DIARIAS,
    PROJETO_DESENVOLVEDOR_CTRL_INCREMENTAR_HORAS_SEMANAIS,
    PROJETO_DESENVOLVEDOR_CTR_DECREMENTAR_HORAS_SEMANAIS,
    PROJETO_CTR_ADICIONAR_DESENVOLVEDOR_AO_PROJETO,
    PROJETO_CTR_CRIAR_PROJETO;

    @Override
    public Class getEntidadeDominio() {
        switch (this) {
            case REQUISITO_MB_GESTAO:

            case REQUISITO_FRM_LISTAR_REQUISITOS_PROXIMA_VERSAO:

            case REQUISITO_FRM_LISTAR_REQUISITOS_APROVADOS:

            case REQUISITO_FRM_LISTAR_REQUISITOS_SUGERIDOS:

            case REQUISITO_FRM_LISTAR_REQUISITOS_REJEITADOS:

            case REQUISITO_FRM_DETALHES_REQUISITO:

            case REQUISITO_CTR_PROMOVER_REQUISITO:

            case REQUISITO_CTR_REBAIXAR_REQUISITO:
            case REQUISITO_CTR_CRIAR_REQUISITO:

            case REQUISITO_CTR_APROVAR_REQUISITO:

            case REUISITIO_CTR_FINALIZAR_REQUISITO:
                return Requisito.class;
            case PROJETO_MB_GESTAO:

            case PROJETO_FRM_LISTAR_PROJETOS:

            case PROJETO_DESENVOLVEDOR_FRM_LISTAR:

            case PROJETO_DESENVOLVEDOR_CTRL_ALTERAR_HORAS_DIARIAS:

            case PROJETO_DESENVOLVEDOR_CTRL_INCREMENTAR_HORAS_SEMANAIS:

            case PROJETO_DESENVOLVEDOR_CTR_DECREMENTAR_HORAS_SEMANAIS:

            case PROJETO_CTR_CRIAR_PROJETO:
            case PROJETO_CTR_ADICIONAR_DESENVOLVEDOR_AO_PROJETO:
                return Projeto.class;

            default:
                throw new AssertionError(this.name());

        }

    }

    @Override
    public String getNomeModulo() {
        return UtilFabricaDeAcoesAcessosModel.getModuloByFabrica(this).getNome();
    }

    @Override
    public AcaoDoSistema getRegistro() {
        AcaoDoSistema acao = (AcaoDoSistema) UtilFabricaDeAcoesAcessosModel.getNovaAcao(this);

        return acao;

    }
}
