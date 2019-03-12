/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDataHora;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStrings;
import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.FabErro;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoCampo;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.anotacoes.InfoObjetoSB;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.FabTipoAtributoObjeto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import org.hibernate.annotations.Where;

/**
 *
 *
 * @author Salvio
 */
@Entity
@InfoObjetoSB(plural = "Projetos", tags = {"Projeto", "Projeto Super Bits"})
public class Projeto extends EntidadeSimples implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @InfoCampo(tipo = FabTipoAtributoObjeto.ID)
    private int id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPrevista;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataCriacao;

    @InfoCampo(tipo = FabTipoAtributoObjeto.AAA_NOME)
    private String nomeProjeto;
    private String nomeComercial;
    @Column(length = 1000)
    private String descricao;
    private String linkSVNSource;
    private String linkSVNRelease;
    private String enderecoHomologacao;
    private String enderecoRequisitos;
    private String caminhoPastaDoProjetoSourceLocal;
    private String caminhoPastaDoProjetoReleaseLocal;
    private String nomePastaProjeto;
    private String nomeArquivoInfo;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "projeto")
    private List<PreRequisito> preRequisitos;

    @ManyToMany
    @JoinTable(name = "Projeto_Desenvolvedor", joinColumns = {
        @JoinColumn(name = "projeto_id")}, inverseJoinColumns = {
        @JoinColumn(name = "desenvolvedor_id")})
    private List<Desenvolvedor> desenvolvedores;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Projeto_Desenvolvedor> desenvolvedoresInfoCompleta;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Projeto_GerenteProjeto", joinColumns = {
        @JoinColumn(name = "gerente_id")}, inverseJoinColumns = {
        @JoinColumn(name = "projeto_id")})
    private List<GerenteProjeto> gerentesDeProjetos;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    private List<Requisito> requisitos;

    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY)
    @Where(clause = "statusRequisito_id = 3")
    private List<Requisito> requisitosProximaVersao;

    private String pastaGitRelease;
    private String pastaGitSource;

    public Projeto(int id, Date dataPrevista, Date dataCriacao, String nomeProjeto, String descricao, String linkSVN, String pastaDoProjeto, Cliente cliente, List<Desenvolvedor> desenvolvedores, List<GerenteProjeto> gerentesDeProjetos, List<Requisito> requisitos, List<Requisito> requisitosProximaVersao) {
        this();

        this.id = id;
        this.dataPrevista = dataPrevista;
        this.dataCriacao = dataCriacao;
        this.nomeProjeto = nomeProjeto;
        this.descricao = descricao;
        this.linkSVNSource = linkSVN;
        this.caminhoPastaDoProjetoSourceLocal = pastaDoProjeto;
        this.cliente = cliente;
        this.desenvolvedores = desenvolvedores;
        this.gerentesDeProjetos = gerentesDeProjetos;
        this.requisitos = requisitos;
        this.requisitosProximaVersao = requisitosProximaVersao;
    }

    public Projeto() {
        super();
        dataCriacao = new Date();
        desenvolvedores = new ArrayList<>();
        gerentesDeProjetos = new ArrayList<>();

    }

    @Override
    public String getNome() {
        return nomeProjeto;
    }

    public void adcionaDesenvolvedor(Desenvolvedor pNovoDesenvolvedor) {
        getDesenvolvedores().add(pNovoDesenvolvedor);
    }

    public void adcionaGerente(GerenteProjeto pNovoGerente) {
        getGerentesDeProjetos().add(pNovoGerente);
    }

    public String getPastaCliente() {

        if (cliente != null) {
            if (cliente.getNome() != null) {
                if (nomeProjeto != null) {

                    return getCliente().getCaminhoPastaClinte();
                }
            }

        }
        return null;

    }

    public String getNomeLikeUrlAmigavel() {
        return UtilSBCoreStrings.makeStrUrlAmigavel(nomeProjeto);
    }

    public String getCaminhoPastaDoProjetoSourceLocal() {

        if (getPastaCliente() != null) {
            if (cliente.getNome() != null) {
                if (nomeProjeto != null) {
                    return getPastaCliente() + "/" + "source/" + getNomePastaProjeto();
                }
            }

        }
        return null;

    }

    public String getPastaRelease() {

        if (getPastaCliente() != null) {
            if (cliente.getNome() != null) {
                if (nomeProjeto != null) {
                    return getPastaCliente() + "/" + "release/" + getNomePastaProjeto();
                }
            }

        }
        return null;

    }

    public String getNomePastaProjeto() {

        nomePastaProjeto = UtilSBCoreStrings.makeStrUrlAmigavel(getNomeProjeto());

        return nomePastaProjeto;
    }

    public String getCaminhoPastaDoProjetoReleaseLocal() {

        caminhoPastaDoProjetoReleaseLocal = getPastaRelease() + getNomePastaProjeto();
        return caminhoPastaDoProjetoReleaseLocal;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLinkSVNSource() {

        if (linkSVNSource != null) {
            return linkSVNSource;
        }
        linkSVNSource = VariaveisSBProject.diretorioSevidorSVNSource + "/" + getNomePastaProjeto() + "/trunk/";
        return linkSVNSource;

    }

    public void setLinkSVNSource(String linkSVNSource) {
        this.linkSVNSource = linkSVNSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Requisito> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<Requisito> requisitos) {
        this.requisitos = requisitos;
    }

    public List<UsuarioSB> getEquipe() {
        List<UsuarioSB> resposta = new ArrayList<>();
        for (Desenvolvedor dev : getDesenvolvedores()) {
            resposta.add(dev);
        }
        for (GerenteProjeto gerente : getGerentesDeProjetos()) {
            resposta.add(gerente);
        }
        return resposta;
    }

    private List<Requisito> filtroRequisito(StatusRequisito pParametro) {
        List<Requisito> requisitosFiltro = new ArrayList<>();
        for (Requisito req : getRequisitos()) {
            if (req.getStatusRequisito().getId() == pParametro.getId()) {
                requisitosFiltro.add(req);
            }
        }
        return requisitosFiltro;
    }

    public List<Requisito> getRequisitosProximaVersao() {
        return filtroRequisito(StatusRequisito.proximaVersao);
    }

    public List<Requisito> getRequisitosFuturo() {
        return filtroRequisito(StatusRequisito.futuro);
    }

    public List<Requisito> getRequisitosConcluido() {
        return filtroRequisito(StatusRequisito.finalizado);
    }

    public List<Requisito> getRequisitosSujestao() {
        return filtroRequisito(StatusRequisito.sujestao);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Desenvolvedor> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public List<GerenteProjeto> getGerentesDeProjetos() {
        return gerentesDeProjetos;
    }

    public void setGerentesDeProjetos(List<GerenteProjeto> gerentesDeProjetos) {
        this.gerentesDeProjetos = gerentesDeProjetos;
    }

    public Double getHorasRestantesProximaVersao() {
        double horasRestantes = 0;
        for (Requisito req : getRequisitos()) {
            if (req.getStatusRequisito().getId() == StatusRequisito.proximaVersao.getId()) {
                horasRestantes = horasRestantes + Math.abs(req.getHorasEstimadas() - req.getHorasTrabalhadas());

            }
        }
        return horasRestantes;

    }

    public String getDataFimVersaoProjetoString() {
        return UtilSBCoreDataHora.getDataSTRFormatoUsuario(getDataFimVersaoProjeto());

    }

    public Date getDataFimVersaoProjeto() {
        try {

            int horasDisponiveisDia = 0;
            System.out.println("DEsenvolvedores" + getDesenvolvedores());

            for (Projeto_Desenvolvedor devFull : getDesenvolvedoresInfoCompleta()) {
                horasDisponiveisDia = horasDisponiveisDia + devFull.getHorasDia();
            }

            if (horasDisponiveisDia == 0) {
                return UtilSBCoreDataHora.incrementaDias(new Date(), 999999999);
            }

            Double horasNescessarias = getHorasRestantesProximaVersao();

            Date fimVersao = new Date();

            Double diasTotalTrabalho = horasNescessarias / horasDisponiveisDia;

            int diasTrabalho = diasTotalTrabalho.intValue();

            Date dataEntrega = new Date();

            int i = diasTrabalho;
            while (i > 0) {
                dataEntrega = UtilSBCoreDataHora.incrementaDias(dataEntrega, 1);
                if (!(dataEntrega.getDay() == 1 || dataEntrega.getDay() == 6)) {
                    i--;
                }
            }

            return dataEntrega;
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro calculando contagem regressiva do projeto", e);
            return null;
        }

    }

    public Integer[] getRegressivaFimDoProjeto() {
        Date datafim = getDataFimVersaoProjeto();
        Integer[] resultado = UtilSBCoreDataHora.intervaloTempoDiasHorasMinitosSegundos(new Date(), datafim);
        System.out.println(resultado);
        return resultado;
    }

    public Integer[] getRegressivaFimVersao() {
        Date datafim = getDataFimVersaoProjeto();
        Integer[] resultado = UtilSBCoreDataHora.intervaloTempoDiasHorasMinitosSegundos(new Date(), getDataFimVersaoProjeto());
        System.out.println(resultado);
        return resultado;
    }

    public Map<Desenvolvedor, Integer> getQuadroHorario() {
        Map<Desenvolvedor, Integer> resposta = new HashMap<>();
        List<Projeto_Desenvolvedor> usuariosDoProjeto = (List<Projeto_Desenvolvedor>) UtilSBPersistencia.getListaRegistrosByHQL(" from " + Projeto_Desenvolvedor.class
                .getSimpleName() + "  where projeto=" + getId() + " ", 0);
        System.out.println(
                "usuarios do projeto encontrados" + usuariosDoProjeto.size());
        for (Projeto_Desenvolvedor pd : usuariosDoProjeto) {

            resposta.put(pd.getDesenvolvedor(), pd.getHorasDia());

        }
        return resposta;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Projeto_Desenvolvedor> getDesenvolvedoresInfoCompleta() {
        return desenvolvedoresInfoCompleta;
    }

    public void setDesenvolvedoresInfoCompleta(List<Projeto_Desenvolvedor> desenvolvedoresInfoCompleta) {
        this.desenvolvedoresInfoCompleta = desenvolvedoresInfoCompleta;
    }

    public List<PreRequisito> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<PreRequisito> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public String getLinkSVNRelease() {

        linkSVNRelease = VariaveisSBProject.diretorioSevidorSVNSource + "/" + UtilSBCoreStrings.makeStrUrlAmigavel(getNomeProjeto()) + "/trunk/";
        return linkSVNRelease;
    }

    public String getEnderecoHomologacao() {
        return enderecoHomologacao;
    }

    public void setEnderecoHomologacao(String enderecoHomologacao) {
        this.enderecoHomologacao = enderecoHomologacao;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getEnderecoRequisitos() {
        return enderecoRequisitos;
    }

    public void setEnderecoRequisitos(String enderecoRequisitos) {
        this.enderecoRequisitos = enderecoRequisitos;
    }

    public String getPastaGitRelease() {

        pastaGitRelease = getNomePastaProjeto() + ".git";

        return pastaGitRelease;
    }

    public void setPastaGitRelease(String pastaGitRelease) {
        this.pastaGitRelease = pastaGitRelease;
    }

    public String getPastaGitSource() {

        pastaGitSource = getNomePastaProjeto() + ".git";

        return pastaGitSource;
    }

    public void setPastaGitSource(String pastaGitSource) {
        this.pastaGitSource = pastaGitSource;
    }

    public String getNomeArquivoInfo() {
        return nomeArquivoInfo;
    }

    public void setNomeArquivoInfo(String nomeArquivoInfo) {
        this.nomeArquivoInfo = nomeArquivoInfo;
    }

}
