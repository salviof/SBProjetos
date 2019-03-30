/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.util;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreDiretorio;

import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreShellBasico;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStringFiltros;

import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.FabMensagens;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.shellcommands.model.Comando;
import com.super_bits.shellcommands.model.RespostaCMD;
import com.super_bits.shellcommands.model.SvnStatusArquivosRepositorio;
import com.super_bits.shellcommands.model.TIPOCMD;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.junit.Assert;

/**
 *
 *
 *
 * @author sfurbino
 */
public class UtilSBProjetos {

    public static final String NOME_PROJETO_BASE = "SuperBitsWPStarter";
    public static final String NOME_PASTA_TEMPORARIA = "projetoTemp";
    public static final String CAMINHO_PROJETO_BASE = "/home/superBits/projetos/Super_Bits/source/SuperBits_FrameWork";
    // Recebe parametros nome da pasta do cliente, e do projeto
    public static final String SCRIPT_PREPARAR_ESTACAO = "/home/superBits/superBitsDevOps/estacaoDeveloperOps/trabalharNovoProjeto.sh";
    // Recebe parametro nome da pasta do cliente e do projeto

    public static final String PASTA_SCRIPTS_REMOTOS = "/home/superBits/superBitsDevOps/SCRIPTS_SERVIDOR";
    public static final String SCRIPT_REMOTO_CRIAR_NOVO_REP_RELEASE = PASTA_SCRIPTS_REMOTOS + "/criarRepositorioRelease.sh";
    public static final String SCRIPT_REMOTO_CRIAR_NOVO_REP_SOURCE = PASTA_SCRIPTS_REMOTOS + "/criarRepositorioSource.sh";
    public static final String SCRIPT_REMOTO_REP_RELEASE_EXISTE = PASTA_SCRIPTS_REMOTOS + "/existeRepositorioRelease.sh";
    public static final String SCRIPT_REMOTO_REP_SOURCE_EXISTE = PASTA_SCRIPTS_REMOTOS + "/existeRepositorioSource.sh";
    public static final String SCRIPT_CHAMADA_REMOTA_EM_PROJETO = "executarScriptDeProejtoRemoto.sh";

    public static void criarNovoProjetoOffline(Projeto p) {
        copiarArquivosNovoProjeto(p, false);
    }

    private static void copiarArquivosNovoProjeto(Projeto p) {
        copiarArquivosNovoProjeto(p, false);
    }

    /**
     *
     *
     *
     * @param p
     * @return
     */
    public static boolean verificarCondicoesSubstituirTodosArquivosParaNovoProjeto(Projeto p) {

        Comando novoComando = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "verificaExistenciaPom.sh");
        novoComando.configParametro("caminhoProjetoSource", p.getCaminhoPastaDoProjetoSourceLocal());

        novoComando.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
        novoComando.setRegexResultadoEsperado("From ");
        RespostaCMD resp = novoComando.executarComando();

        boolean encontrouUmPom = false;
        boolean servidorGitAtivo = false;

        if (resp.getRetorno().contains("From ")) {
            servidorGitAtivo = true;
        }

        if (!servidorGitAtivo) {
            System.out.println("O servidor de codigo fonte do projeto " + p.getNomeComercial() + " não está acessivel");
            return false;
        }

        if (resp.getRetorno().contains("pom.xml")) {
            encontrouUmPom = true;
        }

        if (encontrouUmPom) {
            System.out.println("Um arquivo pom já  foi encontrado, não é possível criar um novo projeto em branco para" + p.getNomeComercial());
            return false;
        } else {
            return true;
        }

    }

    private static void copiarArquivosNovoProjeto(Projeto p, boolean ignorarPresensaDePastaExistente) {
        File pastadoProjeto = new File(p.getCaminhoPastaDoProjetoSourceLocal());
        File pastaSourceCliente = new File(p.getCliente().getCaminhoPastaClinteSource());

        String caminhoPastaSourceCliente = p.getCliente().getCaminhoPastaClinteSource();
        String caminhoPastaSourceProjeto = p.getCaminhoPastaDoProjetoSourceLocal();
        String caminhoPastaModelo = CAMINHO_PROJETO_BASE + "/" + NOME_PROJETO_BASE + "/";
        String caminhoPastaSourceTemporaria = p.getCliente().getCaminhoPastaClinteSource() + "/" + NOME_PASTA_TEMPORARIA;

        File pastaSourceTemporaria = new File(caminhoPastaSourceTemporaria);
        if (!ignorarPresensaDePastaExistente) {
            if (pastadoProjeto.exists()) {
                SBCore.enviarMensagemUsuario("Não foi possível criar o projeto, A pasta do projeto já existe (apague e tente novamente)", FabMensagens.AVISO);
                return;
            }
        }

        Comando criarPasta = TIPOCMD.LNXDIR_MAKEDIR.getComando();
        criarPasta.configParametro("pastaCriar", caminhoPastaSourceCliente);
        criarPasta.executarComando();
        Assert.assertTrue("pasta do SOURCE  cliente não encontrada em " + pastaSourceCliente.getAbsolutePath(), pastaSourceCliente.exists());

        criarPasta.configParametro("pastaCriar", pastadoProjeto.getAbsolutePath());
        criarPasta.executarComando();
        Assert.assertTrue("pasta do SOURCE  cliente não encontrada em " + pastadoProjeto.getAbsolutePath(), pastadoProjeto.exists());

        Comando copiarPastaNovoProjeto = TIPOCMD.LNXDIR_COPIAR_PASTA.getComando();
        copiarPastaNovoProjeto.configParametro("pastaCopOri", caminhoPastaModelo);
        copiarPastaNovoProjeto.configParametro("pastaCopDest", pastaSourceTemporaria.getAbsolutePath());
        copiarPastaNovoProjeto.executarComando();
        Assert.assertTrue("pasta temporaria não encontrada em " + pastaSourceTemporaria.getAbsolutePath(), pastaSourceTemporaria.exists());

        Comando moverpastaOficial = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, "moverPastaOficial.sh");
        String arquivosPastaTemporarias = caminhoPastaSourceTemporaria + "/*";
        moverpastaOficial.configParametro("pastaCopOri", arquivosPastaTemporarias);
        moverpastaOficial.configParametro("pastaCopDest", caminhoPastaSourceProjeto + "/");
        moverpastaOficial.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.SEMRESPOSTA);

        System.out.println("Caminho temp::" + caminhoPastaSourceTemporaria);
        System.out.println("Caminho source::" + caminhoPastaSourceProjeto);

        RespostaCMD resp = moverpastaOficial.executarComando();
        if (!resp.getResultado().equals(RespostaCMD.RESULTADOCMD.OK)) {
            throw new UnsupportedOperationException("Erro copiando arquivo para diretorio" + resp.getRetorno());
        }
        System.out.println(resp.getRetorno());
        //Assert.assertTrue("pasta source do cliente não encontrada", pastadoProjeto.exists());

        String extensoesEditaveis[] = new String[]{"*.java", "*.xml"};

        Map<String, String> mapaPalavras = new HashMap<>();

        mapaPalavras.put("InomeProjetoI", UtilSBCoreStringFiltros.gerarUrlAmigavel(p.getNomeProjeto()));
        mapaPalavras.put("InomeClienteI", UtilSBCoreStringFiltros.gerarUrlAmigavel(p.getCliente().getNome()));

        for (String extencaoArquivos : extensoesEditaveis) {

            for (String nomeParametro : mapaPalavras.keySet()) {

                Comando renomearNomesProjetoArqJava = TIPOCMD.LNXSUBSTITUIR_PALAVRA_EM_ARQUIVOS.getComando();
                renomearNomesProjetoArqJava.configParametro("pastaRecursiva", p.getCaminhoPastaDoProjetoSourceLocal());
                renomearNomesProjetoArqJava.configParametro("novoTexto", mapaPalavras.get(nomeParametro));
                renomearNomesProjetoArqJava.configParametro("textoAntigo", nomeParametro);
                renomearNomesProjetoArqJava.configParametro("arquivosPesquisados", extencaoArquivos);
                renomearNomesProjetoArqJava.executarComando();
            }
        }

        List<String> subpastaras = UtilSBCoreDiretorio.getDiretoriosRecursivoOrdemMaoirArvore(new File(p.getCaminhoPastaDoProjetoSourceLocal()));
        for (String subpasta : subpastaras) {
            for (String nomeAntigo : mapaPalavras.keySet()) {

                Comando renomearNomesProjetoArqJava = TIPOCMD.LNX_RENOMEAR_TODOS_ARQUIVOS_E_PASTAS_DO_DIRETORIO_.getComando();
                renomearNomesProjetoArqJava.configParametro("diretorio", subpasta);
                renomearNomesProjetoArqJava.configParametro("novoTexto", mapaPalavras.get(nomeAntigo));
                renomearNomesProjetoArqJava.configParametro("textoAntigo", nomeAntigo);
                //   renomearNomesProjetoArqJava.configParametro("arquivosPesquisados", "*");
                renomearNomesProjetoArqJava.executarComando();
            }
        }

        limparPastaDoProjeto(p);
        System.out.println("__________________________Fim Criação de Projeto____________________________________");

    }

    public static void criarNovoProjeto(Projeto p) {

        System.out.println("Criando novo projeto chamado:" + p.getNomeComercial());
        System.out.println("________________________________________________________________");

        if (isPastaRepositorioReleasePresenteEmServidor(p)) {
            System.out.println("O REpositorio Release deste projeto já Existe! ");

            int dialogResult = JOptionPane.showConfirmDialog(null,
                    " O repositorio release deste projeto já existe no servidor pode ser que ele já foi criado anteriormente "
                    + "Deseja continuar", "Deseja continuar?", 0);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.out.println("Sim");
            } else {
                System.out.println("não");
                return;
            }

        } else if (!criarREpositorioReleaseDoProjetoNoServidor(p)) {
            SBCore.enviarAvisoAoUsuario("Ouve um erro ao criar o repositorio release do projeto, a operação será abortada");
            return;
        }

        if (isPastaRepositorioSourcePresenteEmServidor(p)) {

            int dialogResult = JOptionPane.showConfirmDialog(null,
                    " O repositorio source deste projeto já existe no servidor pode ser que ele já foi criado anteriormente "
                    + "Deseja continuar", "Deseja continuar? (Casou Houver arquivos no repositorio a operação será abortada)", 0);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.out.println("Sim");
            } else {
                System.out.println("não");
            }

        } else if (!criarREpositorioDoProjetoSource(p)) {
            SBCore.enviarAvisoAoUsuario("Ouve um erro ao criar o repositorio source do projeto, a operação será abortada");
            return;
        }
        configurarPastaProjeto(p);
        if (verificarCondicoesSubstituirTodosArquivosParaNovoProjeto(p)) {
            copiarArquivosNovoProjeto(p, true);
        }

        //  efetuarCheckout(p);
        //     adicionarArquivosSourcenoRepositorio(p);
        //sincronizarSVN.configParametro("pasta", p.getPastaDoProjetoSource());
        //sincronizarSVN.configParametro("endCheckout", p.getLinkSVNSource());
        //sincronizarSVN.configParametro("usuario", "SBAdmin");
        //    System.out.println("pasta do projeto=" + p.getPastaDoProjetoSource());
        //  List<Comando> comandos = new ArrayList();
        //  comandos.add(criarPasta);
        //  comandos.add(copiarPastaNovoProjeto);
        //  comandos.add(renomearPasta);
        //  comandos.add(renomearNomesClienteArqJAva);
        // comandos.add(sincronizarSVN);
        // Script criarNovoProjeto = new Script(comandos);
        //        criarNovoProjeto.executarScript();
        //System.out.println("ResultadoExecucao=" + criarNovoProjeto.getResultadoExecucao());
    }

    public static boolean isPastaRepositorioReleasePresenteEmServidor(Projeto p) {
        System.out.println("Verificando presença de repositorio release em servidor para:" + p.getNomeComercial());
        System.out.println("________________________________________________________________");
        Comando novoComando = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, UtilSBProjetos.SCRIPT_CHAMADA_REMOTA_EM_PROJETO);
        novoComando.configParametro("scriptProjeto", UtilSBProjetos.SCRIPT_REMOTO_REP_RELEASE_EXISTE);
        novoComando.configParametro("projeto", p.getNomePastaProjeto());
        novoComando.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
        novoComando.setRegexResultadoEsperado("sim");
        RespostaCMD resp = novoComando.executarComando();
        System.out.println(resp.getRetorno());

        if (resp.getRetorno().contains("já existe")) {
            System.out.println("Repositório já existe");
            return true;
        }

        return resp.getResultado().equals(RespostaCMD.RESULTADOCMD.OK);

    }

    private static boolean isPastaRepositorioSourcePresenteEmServidor(Projeto p) {

        System.out.println("Verificando presença de repositorio source em servidor para:" + p.getNomeComercial());
        System.out.println("________________________________________________________________");
        Comando novoComando = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, UtilSBProjetos.SCRIPT_CHAMADA_REMOTA_EM_PROJETO);
        novoComando.configParametro("scriptProjeto", UtilSBProjetos.SCRIPT_REMOTO_REP_SOURCE_EXISTE);
        novoComando.configParametro("projeto", p.getNomePastaProjeto());
        novoComando.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
        novoComando.setRegexResultadoEsperado("sim");
        RespostaCMD resp = novoComando.executarComando();
        System.out.println(resp.getRetorno());
        System.out.println("_____________________________FIM___________________________________");
        return resp.getResultado().equals(RespostaCMD.RESULTADOCMD.OK);
    }

    public static boolean criarREpositorioReleaseDoProjetoNoServidor(Projeto p) {
        System.out.println("Criando repositorio release em Servidor para" + p.getNomeComercial());
        System.out.println("________________________________________________________________");
        Comando novoComando = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, UtilSBProjetos.SCRIPT_CHAMADA_REMOTA_EM_PROJETO);
        novoComando.configParametro("scriptProjeto", UtilSBProjetos.SCRIPT_REMOTO_CRIAR_NOVO_REP_RELEASE);
        novoComando.configParametro("projeto", p.getNomePastaProjeto());
        novoComando.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
        novoComando.setRegexResultadoEsperado("sucesso");
        RespostaCMD resp = novoComando.executarComando();
        System.out.println(resp.getRetorno());
        System.out.println("_____________________________FIM___________________________________");
        return isPastaRepositorioReleasePresenteEmServidor(p);

    }

    private static boolean criarREpositorioDoProjetoSource(Projeto p) {

        Comando novoComando = new Comando(Comando.TIPO_EXECUCAO.CRIAR_SCRIPTLNX, UtilSBProjetos.SCRIPT_CHAMADA_REMOTA_EM_PROJETO);
        novoComando.configParametro("scriptProjeto", UtilSBProjetos.SCRIPT_REMOTO_CRIAR_NOVO_REP_SOURCE);
        novoComando.configParametro("projeto", p.getNomePastaProjeto());
        novoComando.setTipoRespostaEsperada(Comando.TIPO_RESPOSTA_ESPERADA.MACH);
        novoComando.setRegexResultadoEsperado("sucesso");
        RespostaCMD resp = novoComando.executarComando();
        System.out.println(resp.getRetorno());
        return isPastaRepositorioSourcePresenteEmServidor(p);

    }

    public static void limparPastaDoProjeto(Projeto p) {
        Comando deletarArquivosTemporarios = TIPOCMD.LNX_REMOVER_TODOS_ARQUIVOS_COM_ESTE_NOME.getComando();
        deletarArquivosTemporarios.setDiretorioExecucao(p.getCaminhoPastaDoProjetoSourceLocal());
        deletarArquivosTemporarios.configParametro("pastaRecursivaExclusaoArquivo", p.getCaminhoPastaDoProjetoSourceLocal());
        deletarArquivosTemporarios.configParametro("pNomeArquivo", "*.*~");
        deletarArquivosTemporarios.executarComando();
        Comando deletarPastaTemporaria = TIPOCMD.LNX_REMOVER_TODAS_PASTAS_COM_ESTE_NOME.getComando();
        deletarPastaTemporaria.configParametro("pastaRecursiva", p.getCaminhoPastaDoProjetoSourceLocal());
        deletarPastaTemporaria.configParametro("nomePastaExclusao", "target");
        deletarPastaTemporaria.executarComando();

        Comando deletarPastaTemporaria2 = TIPOCMD.LNX_REMOVER_TODAS_PASTAS_COM_ESTE_NOME.getComando();
        deletarPastaTemporaria2.configParametro("pastaRecursiva", p.getPastaCliente() + "/source");
        deletarPastaTemporaria2.configParametro("nomePastaExclusao", NOME_PASTA_TEMPORARIA);
        deletarPastaTemporaria2.executarComando();

    }

    public static void configurarPastaProjeto(Projeto pProjeto) {

        configurarPastaCliente(pProjeto.getCliente());
        String pastaSourceDoProjeto = pProjeto.getCaminhoPastaDoProjetoSourceLocal();
        String pastaReleaseDoProjeto = pProjeto.getPastaRelease();
        String pastaConfigReleaseCliente = pProjeto.getPastaRelease();
        List<String> configuracaoProjetoInfo = new ArrayList<>();
        configuracaoProjetoInfo.add("SERVIDOR_HOMOLOGACAO=" + pProjeto.getEnderecoHomologacao());
        configuracaoProjetoInfo.add("SERVIDOR_REQUISITOS=" + pProjeto.getEnderecoRequisitos());
        configuracaoProjetoInfo.add("PASTA_GIT_RELEASE=" + pProjeto.getPastaGitRelease());
        configuracaoProjetoInfo.add("PASTA_GIT_SOURCE=" + pProjeto.getPastaGitSource());
        configuracaoProjetoInfo.add("NOME_PASTA_PROJETO=" + pProjeto.getNomePastaProjeto());

        // criarPastaComVerificacao(pastaSourceDoProjeto);
        criarPastaComVerificacao(pastaReleaseDoProjeto);
        criarNovoArquivoLinhasComVerificacao(pastaConfigReleaseCliente + "/" + pProjeto.getNomeLikeUrlAmigavel() + ".info", configuracaoProjetoInfo);
        //  "./superBitsDevOps/estacaoDeveloperOps/trabalharNovoProjeto.sh vip superkompras";
        // UtilSBShellScript.Process proc = null;

        UtilSBCoreShellBasico.abrirScriptEmConsole(SCRIPT_PREPARAR_ESTACAO, pProjeto.getCliente().getNomePasta(), pProjeto.getNomePastaProjeto());

        try {
            Thread.sleep(25000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UtilSBProjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        File pastaProjetoSource = new File(pProjeto.getCaminhoPastaDoProjetoSourceLocal());
        if (!pastaProjetoSource.exists()) {
            SBCore.enviarAvisoAoUsuario("O diretorio do projeto não existe " + pProjeto.getCaminhoPastaDoProjetoSourceLocal());

            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "O clone da aplicação  com o repositorio não funcionou, "
                    + "voce pode Criar um novo repositório para o projeto [" + pProjeto.getNomeComercial() + "] e tentar novamente, deseja fazer isso?", "Aviso Importante", 0);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.out.println("Sim");
            } else {
                System.out.println("não");
            }
        }
    }

    public static void configurarPastaCliente(Cliente pCliente) {

        String pastaCliente = pCliente.getCaminhoPastaClinte();
        String pastaClienteSource = pCliente.getCaminhoPastaClinte() + "/source";
        String pastaClienteRelease = pCliente.getCaminhoPastaClinte() + "/release";
        Comando criarPasta = TIPOCMD.LNXDIR_MAKEDIR.getComando();
        criarPasta.configParametro("pastaCriar", pastaCliente);
        criarPasta.executarComando();
        Assert.assertTrue("pasta do  cliente não pode ser criada", new File(pastaCliente).exists());

        criarPasta = TIPOCMD.LNXDIR_MAKEDIR.getComando();
        criarPasta.configParametro("pastaCriar", pastaClienteSource);
        criarPasta.executarComando();

        Assert.assertTrue("pasta do SOURCE  do cliente não pode ser criada", new File(pastaClienteSource).exists());

        criarPasta = TIPOCMD.LNXDIR_MAKEDIR.getComando();
        criarPasta.configParametro("pastaCriar", pastaClienteRelease);
        criarPasta.executarComando();

        Assert.assertTrue("pasta do RELEASE  do cliente não pode ser criada", new File(pastaClienteRelease).exists());

        List<String> servidoresGitRelease = new ArrayList<>();
        servidoresGitRelease.add(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_RELEASE.toString() + "=" + pCliente.getServicorGitRelease());

        List<String> servidoresGitSource = new ArrayList<>();
        servidoresGitSource.add(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_SOURCE.toString() + "=" + pCliente.getServidorGitCodigoFonte());

        String arquivoInfoClienteRelease = pastaClienteRelease + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente();
        String arquivoInfoClienteSource = pastaClienteSource + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente();

        servidoresGitRelease.add(pastaCliente);

        UtilSBCoreArquivoTexto.escreveLinhasEmNovoArquivo(arquivoInfoClienteRelease, servidoresGitRelease);
        UtilSBCoreArquivoTexto.escreveLinhasEmNovoArquivo(arquivoInfoClienteSource, servidoresGitSource);

        Assert.assertTrue("O cliente.info release do cliente não foi encontrado", new File(arquivoInfoClienteRelease).exists());
        Assert.assertTrue("O cliente.info source do cliente não foi encontrado", new File(arquivoInfoClienteRelease).exists());

        // Criar a pasta se não existir
        // Criar arquivo cliente.info em Source contendo o servidor de gitHUB ex:SERVIDOR_GIT_SOURCE=https://github.com/salviof
        // criar o arquivo cliente.info em release contendo o endereco do servidor de homologacao ex
        //SERVIDOR_GIT_RELEASE=https://github.com/salviof/NAO_IMPLEMENTADO
    }

    private static void criarPastaComVerificacao(String pPasta) {
        Comando criarPasta = TIPOCMD.LNXDIR_MAKEDIR.getComando();
        criarPasta.configParametro("pastaCriar", pPasta);
        criarPasta.executarComando();
        Assert.assertTrue("pasta do  " + pPasta + " não pode ser criada", new File(pPasta).exists());
    }

    private static void criarNovoArquivoLinhasComVerificacao(String pArquivo, List<String> pLinhasEscrita) {
        UtilSBCoreArquivoTexto.escreveLinhasEmNovoArquivo(pArquivo, pLinhasEscrita);
        Assert.assertTrue("O cliente.info release do cliente não foi encontrado", new File(pArquivo).exists());

    }

    public static void efetuarCheckout(Projeto p) {
        Comando criaRepositorio = TIPOCMD.LNXSVN_CHECKOUT.getComando();

        criaRepositorio.configParametro("pstSource", p.getCaminhoPastaDoProjetoSourceLocal());
        criaRepositorio.configParametro("pasta", p.getNomeCurtoURLAmigavel());
        criaRepositorio.configParametro("usuario", "");
        criaRepositorio.configParametro("senha", "");
        criaRepositorio.configParametro("caminhoRepositorio", p.getLinkSVNSource());
        criaRepositorio.executarComando();
    }

    public static void adicionarArquivosSourcenoRepositorio(Projeto p) {

        SvnStatusArquivosRepositorio repostorio = new SvnStatusArquivosRepositorio(p.getCaminhoPastaDoProjetoSourceLocal());

        if (repostorio.getAdicionados().size() > 0) {
            Comando testeAddSVN = TIPOCMD.LNXSVN_ADD_ARQUIVO_REPOSITORIO.getComando();
            testeAddSVN.setDiretorioExecucao(p.getCaminhoPastaDoProjetoSourceLocal());
            testeAddSVN.configParametro("arquivo", repostorio.getAdicionadosSeparadosPorEspaco());
            testeAddSVN.setDiretorioExecucao(p.getCaminhoPastaDoProjetoSourceLocal());
            testeAddSVN.executarComando();
        }

    }

    public static void prepararEstacaoDeTravalhoParaProjeto(Projeto p) {

    }

}
