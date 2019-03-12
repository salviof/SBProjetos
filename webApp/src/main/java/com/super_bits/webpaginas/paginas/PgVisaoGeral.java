/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.config.webPaginas.forms.FrmInfoRequisito;
import com.super_bits.config.webPaginas.forms.FrmNovoRequisito;
import com.super_bits.modulosSB.Persistencia.dao.DaoGenerico;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfCentralMensagens;
import com.super_bits.modulosSB.SBCore.TratamentoDeErros.ErroSB;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.MB_PaginaConversation;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.ParametroURL;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.siteMap.anotacoes.InfoPagina;
import com.super_bits.modulosSB.webPaginas.controller.sessao.SessaoAtualSBWP;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.Requisito;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Salvio
 */
@ViewScoped
@Named
@InfoPagina(recurso = "/site/visaoGeral.xhtml", nomeCurto = "VG", tags = "Visao Geral")
public class PgVisaoGeral extends MB_PaginaConversation implements Serializable {

    @Inject
    private SessaoAtualSBWP sessao;

    @Inject
    private FrmNovoRequisito frmNovoRequisito;
    @Inject
    private FrmInfoRequisito inforequisito;
    private ParametroURL pCliente;
    private List<Projeto> projetos;
    private String motivacaoNovoRequisito;
    private String descricaoNovoRequisito;
    private String nomeNovoRequisito;
    private List<Desenvolvedor> desenvovedores;
    private Desenvolvedor desenvolvedorSelecioncado;
    private int teste = 0;

    public void atualizarDados() {

        try {
            getEMPagina().clear();
            System.out.println("Atualizando dados");
            renovarEMPagina();
            aplicaValoresURLEmParametros(true);

            Cliente teste = (Cliente) UtilSBPersistencia.getRegistroByID(Cliente.class, ((Cliente) pCliente.getValor()).getId(), getEMPagina());
            projetos = teste.getProjetos();

            System.out.println(((Cliente) pCliente.getValor()).getProjetos().get(0).getDataFimVersaoProjeto());
            System.out.println("horasDIA:" + ((Cliente) pCliente.getValor()).getProjetos().get(0).getDesenvolvedoresInfoCompleta().get(0).getHorasDia());
            aplicaValoresURLEmParametros(foiInjetado);
            desenvovedores = (List<Desenvolvedor>) UtilSBPersistencia.getListaTodos(Desenvolvedor.class, getEMPagina());
            desenvolvedorSelecioncado = desenvovedores.get(0);
            System.out.println("fim atualização de dados");

        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro atualizando dados", e);

        }
    }
// 2 primeiro cadastra os clientes na mao então...
    // depois acessa /AD/.wp...
    // quando entrar em /ad/.wp  a tela não está muito organizada.../
    // os usuarios também vc vai ter q cadastrar na mao.. (a
    // vai dar trabalho..
    // e só vai servir para esta versão, depois quando mudar os campos do banco vai ser outro scritp..
    // Não tem como salvar enviar os dados para executar aqui?
    // é aquele arquivo .sql que eu te mandei...
    // mas tá com o lixo que vc ta querendo apagar, (ainda não entendi o motivo) -> "lixo"
    //

    public PgVisaoGeral() {
        super();
        try {
            System.out.println("Selecionando novo cliente");
            getConversation();
            Cliente clientepadrao = (Cliente) UtilSBPersistencia.getRegistroByID(Cliente.class, 2, getEMPagina());
            clientepadrao.loadByID(2);
            System.out.println("Novo Cliente selecionado");
            pCliente = new ParametroURL("cliente", clientepadrao, ParametroURL.tipoPrURL.ENTIDADE, Cliente.class);
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "erro criando parametro de url", e);
            pCliente = null;
        }
    }

    @Override
    public String defineTitulo() {
        return "Projetos Sphera Security";
    }

    @Override
    public String defineNomeLink() {
        return "Projetos Sphera";
    }

    @Override
    public String defineDescricao() {
        return "Acompanhamento dos projetos desenvolvidos para Sphera Security";
    }

    @Override
    public void abrePagina() {
        super.abrePagina();
        //iniciaConvesa();
        System.out.println("ParametroURL=" + pCliente.getValor());

        atualizarDados();
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public String getMotivacaoNovoRequisito() {
        return motivacaoNovoRequisito;
    }

    public void setMotivacaoNovoRequisito(String motivacaoNovoRequisito) {
        this.motivacaoNovoRequisito = motivacaoNovoRequisito;
    }

    public String getDescricaoNovoRequisito() {
        return descricaoNovoRequisito;
    }

    public void setDescricaoNovoRequisito(String descricaoNovoRequisito) {
        this.descricaoNovoRequisito = descricaoNovoRequisito;
    }

    public void enviaMensagemTeste() {
        SBCore.getCentralDeMensagens().enviaMensagemUsuario("Mensagem teste Funciona, como pode tanta tecnologia funcionar, impressionante !!", ItfCentralMensagens.TP_MENSAGEM.AVISO);
    }

    public void promoverRequisito(Requisito pRequisito) {
        if (pRequisito == null) {
            SBCore.getCentralDeMensagens().enviaMensagemSistema("Parametro requisito não enviado", ItfCentralMensagens.TP_MENSAGEM.AVISO);
            return;
        }
        System.out.println("promoção de requisito com " + pRequisito.getHorasEstimadas() + "horas estimadas");

        if (ModuloGestaoProjeto.promoverRequisito(pRequisito)) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Requisito " + pRequisito.getNome() + " foi promovido", ItfCentralMensagens.TP_MENSAGEM.AVISO);
            atualizarDados();

        }

    }

    public void rebaixarRequisito(Requisito pRequisito) {
        System.out.println("rebaixando" + pRequisito.getNome());
        ModuloGestaoProjeto.rebaixarRequisito(pRequisito);
        projetos = new DaoGenerico<Projeto>(Projeto.class).todos();

    }

    public String getNomeNovoRequisito() {
        return nomeNovoRequisito;
    }

    public void setNomeNovoRequisito(String nomeNovoRequisito) {
        this.nomeNovoRequisito = nomeNovoRequisito;
    }

    public void formNovoRequisito(Projeto pProjeto) {
        System.out.println("Abrindo formulario cadastro projeto:" + pProjeto.getNomeCurto());
        frmNovoRequisito.setProjeto(pProjeto);

        frmNovoRequisito.abrirFormulario();
        System.out.println("Fim formulario" + pProjeto.getNomeCurto());
    }

    public void formInfoRequsito(Requisito pRequisito) {
        inforequisito.setRequisito(pRequisito);
        inforequisito.abrirFormulario();

    }

    public List<Desenvolvedor> getDesenvovedores() {
        return desenvovedores;
    }

    public void setDesenvovedores(List<Desenvolvedor> desenvovedores) {
        this.desenvovedores = desenvovedores;
    }

    public Desenvolvedor getDesenvolvedorSelecioncado() {
        return desenvolvedorSelecioncado;
    }

    public void setDesenvolvedorSelecioncado(Desenvolvedor desenvolvedorSelecioncado) {
        this.desenvolvedorSelecioncado = desenvolvedorSelecioncado;
    }

    public void incrementarHorasSemanais(Projeto pProjeto, Desenvolvedor pDesenvolvedor) {
        try {
            ModuloGestaoProjeto.incrementarHorasSemanaisDesenvolvedor(pProjeto, pDesenvolvedor);
            teste++;
            atualizarDados();
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro executando metodo ", e);
        }

    }

    public void decrementarHorasSemanais(Projeto pProjeto, Desenvolvedor pDesenvolvedor) {

        try {
            ModuloGestaoProjeto.decrementarHorasSemanaisDesenvolvedor(pProjeto, pDesenvolvedor);
            teste++;
            atualizarDados();
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro executando metodo ", e);
        }

    }

    public int getTeste() {
        return teste;
    }

    public void setTeste(int teste) {
        this.teste = teste;
    }

    public void atualizateste1() {
        teste++;
        System.out.println(teste);
    }

    public void atualizateste2() {
        teste++;
        Projeto prteste = new Projeto();
        prteste.loadByID(1);
        prteste.setNomeProjeto(prteste.getNomeProjeto() + teste);

        UtilSBPersistencia.mergeRegistro(prteste);
        // atualizarDados();

    }

    @Override
    public int getId() {
        return 0;
    }

}
