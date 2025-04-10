/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.FilterDef;

/**
 *
 *
 *
 * @author Salvio
 */
@Entity
@FilterDef(name = "proximaVersao", defaultCondition = "statusRequisito=1")
public class Requisito extends EntidadeSimples implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @InfoCampo(tipo = FabTipoAtributoObjeto.NOME)
    private String nome;
    private Double horasEstimadas;
    private Double horasTrabalhadas;
    @Column(length = 2500)
    private String descricao;

    @Column(length = 2500)
    private String motivacao;
    private String observacao;
    private String linkTarefaClokingIT;
    @ManyToOne
    private StatusRequisito statusRequisito;
    @ManyToOne
    private Projeto projeto;

    @OneToMany(mappedBy = "requisito")
    private List<Trabalho> trabalhos;

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(List<Trabalho> trabalhos) {
        this.trabalhos = trabalhos;
    }

    public Requisito() {
        horasEstimadas = Double.valueOf(0);
        horasTrabalhadas = Double.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(Double horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public Double getHorasTrabalhadas() {

        return horasTrabalhadas;
    }

    public Double atualizarHorasTrabalhadadas() {
        List<Trabalho> works = getTrabalhos();
        horasTrabalhadas = 0.0;
        for (Trabalho work : works) {
            if (work.getInicio() != null && work.getFim() != null) {
                double horas = work.getFim().getTime() - work.getInicio().getTime();
                horas = horas / 10000; // Segundos
                horas = horas / 60; // Minutos
                horas = horas / 60;// Horas
                horasTrabalhadas = horasTrabalhadas + horas;
            }
        }
        this.horasTrabalhadas = horasTrabalhadas;
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(Double horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public String getLinkTarefaClokingIT() {
        return linkTarefaClokingIT;
    }

    public void setLinkTarefaClokingIT(String linkTarefaClokingIT) {
        this.linkTarefaClokingIT = linkTarefaClokingIT;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public double getPorcentagem() {

        return (100 / horasEstimadas) * horasTrabalhadas;
    }

    public StatusRequisito getStatusRequisito() {
        return statusRequisito;
    }

    public void setStatusRequisito(StatusRequisito statusRequisito) {
        this.statusRequisito = statusRequisito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
