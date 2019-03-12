/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfCentralMensagens;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.MB_PaginaConversation;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.reflexao.anotacoes.InfoPagina;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.reflexao.anotacoes.beans.InfoMB_Bean;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.PreRequisito;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.Projeto_Desenvolvedor;
import com.super_bits.sbProjetos.Model.Requisito;
import com.super_bits.sbProjetos.Model.Trabalho;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 *
 * Pagina de Administração
 *
 * @author Salvio
 */
@InfoPagina(nomeCurto = "AD", tags = "Admin")
@Named
@ConversationScoped
public class PgAdmin extends MB_PaginaConversation implements Serializable {

    @InfoMB_Bean(descricao = "Projeto selecionado")
    private Projeto projetoSelecionado;

    private Requisito requisitoSelecionado;
    @InfoMB_Bean(descricao = "Usuario Selecionado")
    private UsuarioSB usuarioSelecionado;
    @InfoMB_Bean(descricao = "Horas Semanais")
    private Integer horasSemanais;
    @InfoMB_Bean(descricao = "Clientes Ativos")
    private List<Cliente> clientes;
    @InfoMB_Bean(descricao = "Clientes Selecionado")
    private Cliente clienteSelecionado;
    private Cliente novoCliente;
    @InfoMB_Bean(descricao = "Todos usuários")
    private List<UsuarioSB> todosUsuarios;
    @InfoMB_Bean(descricao = "Todos Desenvolvedores")
    private List<Desenvolvedor> todosDesenvolvedores;

    private Desenvolvedor desenvolvedorSelecionado;
    @InfoMB_Bean(descricao = "Bean para criacao de novo projeto")
    private Projeto novoProjeto;
    private Desenvolvedor novoDesenvolvedor;
    private Date dataHoraFimTrabalho;
    private Date dataHorainicioTrabalho;

    private Projeto_Desenvolvedor desenvolvedoresNoProjeto;
    private PreRequisito novoPreRequisito;

    @Override
    public void abrePagina() {
        super.abrePagina(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("ABRIU PAGINA ADMIN");
        try {
            todosUsuarios = (List<UsuarioSB>) UtilSBPersistencia.getListaTodos(UsuarioSB.class, getEMPagina());
            clientes = (List<Cliente>) UtilSBPersistencia.getListaTodos(Cliente.class, getEMPagina());
            todosUsuarios = (List<UsuarioSB>) UtilSBPersistencia.getListaTodos(UsuarioSB.class, getEMPagina());
            todosDesenvolvedores = (List<Desenvolvedor>) UtilSBPersistencia.getListaTodos(Desenvolvedor.class, getEMPagina());
            desenvolvedorSelecionado = todosDesenvolvedores.get(0);
            clienteSelecionado = clientes.get(0);
            criarNovoPrerequisito();
            criarNovoCliente();
            criarNovoDesenvolvedor();
            System.out.println("Projetos do Cliente" + clienteSelecionado.getProjetos());
            System.out.println(getClienteSelecionado());
            System.out.println("classe:" + clienteSelecionado.getProjetos().getClass().getSimpleName());

            System.out.println("Projetos do Cliente" + getClienteSelecionado() + ":::" + getClienteSelecionado().getProjetos());
            // projetoSelecionado = clienteSelecionado.getProjetos().get(0);
            usuarioSelecionado = todosUsuarios.get(0);

            criarNovoProjeto();
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro iniciando pagina ADM", e);
        }

    }

    public PgAdmin() {
        super();
        dataHoraFimTrabalho = new Date();
        dataHorainicioTrabalho = new Date();
    }

    public void criarNovoPrerequisito() {
        novoPreRequisito = new PreRequisito();

    }

    @InfoMB_Acao(descricao = "Salva alteraçoes no Usuario Selecionado")
    public void salvarAlteracoesUsuario() {

    }

    @InfoMB_Acao(descricao = "Cria novo usuário utilizando o usuário Selecionado")
    public void criarNovoUsuario() {

    }

    @InfoMB_Acao(descricao = "Limpa dados do usuário para criar novo usuario. ")
    public void resetUsuario() {

    }

    @InfoMB_Acao(descricao = "Adciona usuário no projeto selecionado")
    public void adcionaUsuarioAoProjeto() {

    }

    public Projeto getProjetoSelecionado() {
        return projetoSelecionado;
    }

    public void setProjetoSelecionado(Projeto projetoSelecionado) {
        this.projetoSelecionado = projetoSelecionado;
    }

    @Override
    public String defineTitulo() {
        return "Administração de projetos";
    }

    @Override
    public String defineNomeLink() {

        return "Admin Projetos";
    }

    @Override
    public String defineDescricao() {
        return "Pagina para edição";
    }

    public UsuarioSB getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(UsuarioSB usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public Integer getHorasSemanais() {
        return horasSemanais;
    }

    public void setHorasSemanais(Integer horasSemanais) {
        this.horasSemanais = horasSemanais;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        //  try {
        this.clienteSelecionado = clienteSelecionado;
        //  } finally {
        //      if (clienteSelecionado.getProjetos().size() > 0) {
        //   setProjetoSelecionado(this.clienteSelecionado.getProjetos().get(0));
        //      } else {
        //          System.out.println("Nenum projeto neste cliente");
        //      }
        // }

    }

    public void criarNovoProjeto() {
        novoProjeto = new Projeto();
        if (clienteSelecionado == null) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Selecione o cliente", ItfCentralMensagens.TP_MENSAGEM.ALERTA);
        }
        novoProjeto.setCliente(clienteSelecionado);
    }

    public void criarNovoCliente() {
        novoCliente = new Cliente();

    }

    public void salvarNovoCliente() {
        ModuloGestaoProjeto.criarCliente(novoCliente);
        clientes = (List<Cliente>) UtilSBPersistencia.getListaTodos(Cliente.class);
    }

    public void salvarNovoProjeto() {
        System.out.println("Criando Novo Projeto" + novoProjeto.getNomeProjeto());
        ModuloGestaoProjeto.criarProjeto(novoProjeto);
    }

    public Projeto getNovoProjeto() {
        return novoProjeto;
    }

    public void setNovoProjeto(Projeto novoProjeto) {
        this.novoProjeto = novoProjeto;
    }

    public List<UsuarioSB> getTodosUsuarios() {
        return todosUsuarios;
    }

    public void setTodosUsuarios(List<UsuarioSB> todosUsuarios) {
        this.todosUsuarios = todosUsuarios;
    }

    public Cliente getNovoCliente() {
        return novoCliente;
    }

    public void setNovoCliente(Cliente novoCliente) {
        this.novoCliente = novoCliente;
    }

    public void criarNovoDesenvolvedor() {
        novoDesenvolvedor = new Desenvolvedor();
    }

    public void salvarNovoDesenvolvedor() {
        try {
            if (UtilSBPersistencia.persistirRegistro(novoDesenvolvedor)) {
                SBCore.getCentralDeMensagens().enviaMensagemUsuario("Usuário cadastrado", ItfCentralMensagens.TP_MENSAGEM.AVISO);
            }
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro cadastrnado desenvolvedor", e);
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Não foi possível cadastrar" + e.getMessage(), ItfCentralMensagens.TP_MENSAGEM.AVISO);
        }
    }

    public Desenvolvedor getNovoDesenvolvedor() {
        return novoDesenvolvedor;
    }

    public void setNovoDesenvolvedor(Desenvolvedor novoDesenvolvedor) {
        this.novoDesenvolvedor = novoDesenvolvedor;
    }

    public List<Desenvolvedor> getTodosDesenvolvedores() {
        return todosDesenvolvedores;
    }

    public void setTodosDesenvolvedores(List<Desenvolvedor> todosDesenvolvedores) {
        this.todosDesenvolvedores = todosDesenvolvedores;
    }

    public Desenvolvedor getDesenvolvedorSelecionado() {
        return desenvolvedorSelecionado;
    }

    public void setDesenvolvedorSelecionado(Desenvolvedor desenvolvedorSelecionado) {
        this.desenvolvedorSelecionado = desenvolvedorSelecionado;
    }

    public void adcionarDesenvolvedorAoProjeto() {
        ModuloGestaoProjeto.adcionarDesenvolvedorAoProjeto(projetoSelecionado, desenvolvedorSelecionado, horasSemanais);
        criarNovoDesenvolvedor();
    }

    public Projeto_Desenvolvedor getDesenvolvedoresNoProjeto() {
        return desenvolvedoresNoProjeto;
    }

    public void setDesenvolvedoresNoProjeto(Projeto_Desenvolvedor desenvolvedoresNoProjeto) {
        this.desenvolvedoresNoProjeto = desenvolvedoresNoProjeto;
    }

    public void salvarPrerequisito() {

        novoPreRequisito.setProjeto(projetoSelecionado);

        UtilSBPersistencia.persistirRegistro(novoPreRequisito);

        criarNovoPrerequisito();
    }

    public PreRequisito getNovoPreRequisito() {
        return novoPreRequisito;
    }

    public void setNovoPreRequisito(PreRequisito novoPreRequisito) {
        this.novoPreRequisito = novoPreRequisito;
    }

    public void testeErro() {
        throw new NullPointerException("Um NullPointerException!");
    }

    public Date getDataHoraFimTrabalho() {
        return dataHoraFimTrabalho;
    }

    public void setDataHoraFimTrabalho(Date dataHoraFimTrabalho) {
        this.dataHoraFimTrabalho = dataHoraFimTrabalho;
    }

    public Date getDataHorainicioTrabalho() {
        return dataHorainicioTrabalho;
    }

    public void setDataHorainicioTrabalho(Date dataHorainicioTrabalho) {
        this.dataHorainicioTrabalho = dataHorainicioTrabalho;
    }

    public Requisito getRequisitoSelecionado() {
        return requisitoSelecionado;
    }

    public void setRequisitoSelecionado(Requisito requisitoSelecionado) {
        this.requisitoSelecionado = requisitoSelecionado;
    }

    public void novoJob() {

        Trabalho novoTrabalho = new Trabalho();

        novoTrabalho.setInicio(dataHorainicioTrabalho);
        novoTrabalho.setFim(dataHoraFimTrabalho);
        novoTrabalho.setDesenvolvedor(desenvolvedorSelecionado);
        novoTrabalho.setHistorico("---");
        novoTrabalho.setRequisito(requisitoSelecionado);

        if (UtilSBPersistencia.persistirRegistro(novoTrabalho, getEMPagina())) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Cadastro realizado com sucesso", ItfCentralMensagens.TP_MENSAGEM.AVISO);
            requisitoSelecionado.atualizarHorasTrabalhadadas();
            if (UtilSBPersistencia.mergeRegistro(requisitoSelecionado, getEMPagina()) != null) {
                SBCore.getCentralDeMensagens().enviaMensagemUsuario("Requisito atualizado com sucesso", ItfCentralMensagens.TP_MENSAGEM.AVISO);

            } else {
                SBCore.getCentralDeMensagens().enviaMensagemUsuario("Erro ao cadastrar requisito", ItfCentralMensagens.TP_MENSAGEM.ERRO);
            }
        } else {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Cadastro realizado com sucesso", ItfCentralMensagens.TP_MENSAGEM.ERRO);

        }
    }

    @Override
    public int getId() {
        return 1;
    }

}
