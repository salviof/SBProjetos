/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.util;

import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStrings;
import com.super_bits.modulosSB.SBCore.modulos.objetos.InfoCampos.campo.TIPO_PRIMITIVO;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfBeanSimples;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Projeto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author SalvioF
 */
public class UtilSBProjetosCommandLine {

    private static final String CAMINHO_LOGO_ASCII = "/home/superBits/superBitsDevOps/logoAsci.txt";
    private static MENU_ETAPAS etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;

    public enum MENU_ETAPAS {
        ESCOLHA_CLIENTES,
        OPCOES_CLIENTE,
        ESCOLHA_PROJETO,
        OPCOES_PROJETO
    }

    public enum MENU_OPCOES_CLIENTE {
        LISTAR_PROJETOS,
        EDITAR,
        ATUALIZAR_ARQUIVOS_CONFIG,
        NOVO_PROJETO

    }

    public enum MENU_OPCOES_PROJETO {
        EDITAR_PROJETO,
        ATUALIZAR_ARQUIVOS_CONFIG,
        CRIAR_NOVO_PROJETO_EM_SERVIDOR,
        CRIAR_NOVO_PROJETO_OFFLINE;

        @Override
        public String toString() {
            switch (this) {
                case EDITAR_PROJETO:
                    return "Editar Projeto (Não suporta alterações em projeto existe)";

                case ATUALIZAR_ARQUIVOS_CONFIG:
                    return "Baixar Projeto Sincronizar";

                case CRIAR_NOVO_PROJETO_EM_SERVIDOR:

                    return "Criar estrutura de diretórios e novo Projeto baseado em modelo";

                default:
                    return super.toString();

            }

        }
    }

    public enum PALAVRAS_CHAVE {
        NENHUMA,
        VOLTAR,
        SAIR;

        @Override
        public String toString() {
            switch (this) {
                case VOLTAR:
                    return "voltar";

                case SAIR:
                    return "sair";
                case NENHUMA:
                    return "";

                default:
                    throw new AssertionError(this.name());

            }
        }

        public static boolean isUmaPalavraChave(String pPalavraChave) {
            for (Enum palavra : PALAVRAS_CHAVE.class.getEnumConstants()) {
                if (palavra.toString().equals(pPalavraChave)) {
                    return true;
                }
            }
            return false;
        }

        public void executarPalavraChave() {
            switch (this) {
                case VOLTAR:
                    switch (etapaAtual) {
                        case ESCOLHA_CLIENTES:
                            etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;
                            break;
                        case OPCOES_CLIENTE:
                            etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;
                            break;
                        case ESCOLHA_PROJETO:
                            etapaAtual = MENU_ETAPAS.OPCOES_CLIENTE;
                            break;
                        case OPCOES_PROJETO:
                            etapaAtual = MENU_ETAPAS.ESCOLHA_PROJETO;
                            break;
                        default:
                            throw new AssertionError(etapaAtual.name());

                    }
                    break;
                case SAIR:
                    System.out.println("Thal, participe da cominidade, coletivoJava.com.br");
                    System.exit(0);
                case NENHUMA:
                    break;

                default:
                    throw new AssertionError(this.name());

            }

        }

        public static PALAVRAS_CHAVE getPalavraByTexto(String pPalavraTexto) {
            for (Enum palavra : PALAVRAS_CHAVE.class.getEnumConstants()) {
                if (palavra.toString().equals(pPalavraTexto)) {
                    return (PALAVRAS_CHAVE) palavra;
                }
            }
            return NENHUMA;
        }

    }

    private static String getValorOperadorSet(String pTextoPadrao, TIPO_PRIMITIVO pTipoDado) {

        boolean valorDefinido = false;
        while (!valorDefinido) {
            System.out.println("Digite o valor atualizado");
            String valorUsuario = getValorUsuario(pTipoDado);

            if (UtilSBCoreStrings.isNuloOuEmbranco(valorUsuario)) {

                System.out.println("Deseja usar um valor padrão? digite sim");

                if (getValorUsuario(TIPO_PRIMITIVO.LETRAS).equals("sim")) {
                    return pTextoPadrao;
                } else {
                    System.out.println("Vamos tentar novamente...");
                }

            } else {
                return valorUsuario;
            }
        }

        return pTextoPadrao;
    }

    /**
     *
     * obs: O retorno -1 indica que o valor inteiro foi ignorado
     *
     * @param pTipoDado
     * @return
     */
    public static String getValorUsuario(TIPO_PRIMITIVO pTipoDado) {
        System.out.println("Você também pode usar os comandos -> [voltar] e [sair]");

        Scanner scan = new Scanner(System.in);
        boolean opcaoValida = false;
        String valorTextoColetado = null;
        while (!opcaoValida) {

            String textoUsuario = scan.nextLine();

            PALAVRAS_CHAVE palavrachave = PALAVRAS_CHAVE.getPalavraByTexto(textoUsuario);
            palavrachave.executarPalavraChave();
            switch (palavrachave) {
                case NENHUMA:

                    try {
                        switch (pTipoDado) {
                            case INTEIRO:
                                Integer.parseInt(textoUsuario);
                                break;
                            default: {

                            }

                        }
                        opcaoValida = true;
                        valorTextoColetado = textoUsuario;
                    } catch (Throwable t) {
                        opcaoValida = false;
                    }
                    if (!opcaoValida) {
                        System.out.println("Não entendi :O :(");
                    }

                    break;
                case VOLTAR:
                    switch (pTipoDado) {
                        case INTEIRO:
                            return "-1";
                        default:
                            return null;

                    }

                case SAIR:
                    break;
                default:
                    throw new AssertionError(palavrachave.name());

            }

        }

        return valorTextoColetado;
    }

    private static ItfBeanSimples menuOpcoes(List lista) {
        return menuOpcoes(builtMapaOpcoes(lista));
    }

    private static ItfBeanSimples menuOpcoes(Map<Integer, ItfBeanSimples> mapaDeOpcoes) {
        boolean opcaoValida = false;
        int opcao = 0;
        if (mapaDeOpcoes == null || mapaDeOpcoes.isEmpty()) {
            return null;
        }
        System.out.println("Digite o número correspondente, ou 0 para criar um novo " + mapaDeOpcoes.get(1).getNomeDoObjeto() + ", ou escreva [sair] \n");
        for (Integer opcaoMenu : mapaDeOpcoes.keySet()) {
            System.out.println("" + opcaoMenu + " ->" + mapaDeOpcoes.get(opcaoMenu).getNome());

        }

        while (!opcaoValida) {

            try {
                int escolha = Integer.valueOf(getValorUsuario(TIPO_PRIMITIVO.INTEIRO));
                if (mapaDeOpcoes.get(escolha) != null) {
                    return mapaDeOpcoes.get(escolha);
                }
                if (escolha == 0) {
                    return null;
                }
            } catch (Throwable t) {
                System.out.println("Erro ->" + t.getMessage());
            }

            System.out.println("Opção Inválida!?");

        }
        return null;

    }

    public static Cliente getNovoClienteUsuario() {
        Cliente novoCliente = new Cliente();
        atualizarCliente(novoCliente);
        return novoCliente;
    }

    private static <I extends Enum> I opCaoSelecionada(Class enumOpcoes) {
        System.out.println("Escolha entre as opções:");
        for (Object objOpcao : enumOpcoes.getEnumConstants()) {
            Enum opcao = (Enum) objOpcao;
            System.out.println("" + (opcao.ordinal() + 1) + " ->" + opcao.name());

        }

        int opcao = Integer.valueOf(getValorUsuario(TIPO_PRIMITIVO.INTEIRO));

        for (Object objOpcao : enumOpcoes.getEnumConstants()) {
            I item = (I) objOpcao;
            if (item.ordinal() == opcao - 1) {
                return item;
            }
        }
        return null;
    }

    private static Map<Integer, ItfBeanSimples> builtMapaOpcoes(List opcoes) {
        Map<Integer, ItfBeanSimples> mapaOpcoes = new HashMap<>();
        int codCli = 1;
        for (Object item : opcoes) {
            mapaOpcoes.put(codCli++, (ItfBeanSimples) item);
        }
        return mapaOpcoes;
    }

    private static void atualizarObjeto(ItfBeanSimples pObjeto, String[] pCampos) {

        String[] campos = pCampos;

        if (pObjeto == null) {
            System.out.println("O Objeto enviado é nulo, impossível atualizar");
            return;
        }

        for (String cmapo : campos) {
            try {
                pObjeto.getCampoByNomeOuAnotacao(cmapo);
            } catch (Throwable t) {
                System.out.println("Impossivel editar " + pObjeto.getClass().getSimpleName() + " o campo  " + cmapo + " não foi encontrado");
                return;
            }
        }
        for (String cmapo : campos) {
            System.out.println("Digite o --->" + pObjeto.getCampoByNomeOuAnotacao(cmapo).getLabel().toUpperCase() + "<-- do " + pObjeto.getNomeDoObjeto());
            boolean valorDefinido = false;
            while (!valorDefinido) {
                String valor = getValorOperadorSet(pObjeto.getNome(), TIPO_PRIMITIVO.LETRAS);
                if (UtilSBCoreStrings.isNAO_NuloNemBranco(valor)) {
                    try {
                        pObjeto.getCampoByNomeOuAnotacao(cmapo).setValor(valor);
                        valorDefinido = true;

                    } catch (Throwable t) {
                        System.out.println("Erro definindo valor" + t.getMessage());
                    }
                } else {
                    return;
                }

            }
        }

    }

    public static void atualizarProjeto(Projeto pProjeot) {
        atualizarObjeto(pProjeot, new String[]{"nomeProjeto", "nomeComercial", "enderecoHomologacao", "enderecoRequisitos"});
    }

    public static void atualizarCliente(Cliente pCliente) {
        atualizarObjeto(pCliente, new String[]{"nome", "servicorGitRelease", "servidorGitCodigoFonte"});
    }

    public static void iniciarNavegacao() {

        for (String linha : UTilSBCoreInputs.getStringsByArquivoLocal(CAMINHO_LOGO_ASCII)) {
            System.out.println(linha);
        }
        System.out.println("http://coletivoJava.com.br");
        System.out.println("Bem Vindo, ao Coletivo Java! :D");
        Cliente clienteEscolido = null;
        Projeto projetoEscolhido = null;
        boolean sair = false;
        while (!sair) {
            switch (etapaAtual) {
                case ESCOLHA_CLIENTES:
                    clienteEscolido = null;
                    projetoEscolhido = null;
                    clienteEscolido = (Cliente) menuOpcoes(MapaProjetos.getTodosClientes());
                    if (clienteEscolido != null) {
                        etapaAtual = MENU_ETAPAS.OPCOES_CLIENTE;
                    } else {
                        clienteEscolido = getNovoClienteUsuario();
                        MapaProjetos.adicionarCliente(clienteEscolido);
                        if (clienteEscolido != null) {
                            etapaAtual = MENU_ETAPAS.OPCOES_CLIENTE;
                        }
                    }

                    break;
                case OPCOES_CLIENTE:
                    if (clienteEscolido == null) {
                        System.out.println("Nenhum cliente foi Selecionado");
                        etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;
                    } else {
                        System.out.println("Cliente Selecionado:");
                        System.out.println(clienteEscolido.getNome());
                        System.out.println("Pasta do codigo fonte do  Cliente:");
                        System.out.println(clienteEscolido.getCaminhoPastaClinteSource());
                        System.out.println("Url Git Source:");
                        System.out.println(clienteEscolido.getServidorGitCodigoFonte());
                        System.out.println("Url Git Release:");
                        System.out.println(clienteEscolido.getServicorGitRelease());
                        MENU_OPCOES_CLIENTE opcaoClientecolhida = (MENU_OPCOES_CLIENTE) opCaoSelecionada(MENU_OPCOES_CLIENTE.class);
                        if (opcaoClientecolhida != null) {
                            switch (opcaoClientecolhida) {
                                case LISTAR_PROJETOS:
                                    etapaAtual = MENU_ETAPAS.ESCOLHA_PROJETO;
                                    break;
                                case EDITAR:
                                    atualizarCliente(clienteEscolido);

                                    break;
                                case ATUALIZAR_ARQUIVOS_CONFIG:

                                    System.out.println("O Sistema irá atualizar a pasta do cliente:" + clienteEscolido.getNome());
                                    UtilSBProjetos.configurarPastaCliente(clienteEscolido);

                                    break;
                                case NOVO_PROJETO:
                                    Projeto novoProjeto = new Projeto();
                                    novoProjeto.setCliente(clienteEscolido);

                                    atualizarProjeto(novoProjeto);
                                    MapaProjetos.adicionarProjeto(novoProjeto);

                                    break;
                                default:
                                    throw new AssertionError(opcaoClientecolhida.name());
                            }
                        }
                        if (opcaoClientecolhida == null) {
                            etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;
                        }

                    }

                    break;
                case ESCOLHA_PROJETO:
                    if (clienteEscolido != null) {

                        projetoEscolhido = (Projeto) menuOpcoes(clienteEscolido.getProjetos());
                        MENU_OPCOES_PROJETO opcoesProjeto = opCaoSelecionada(MENU_OPCOES_PROJETO.class);
                        if (opcoesProjeto != null) {
                            switch (opcoesProjeto) {
                                case EDITAR_PROJETO:
                                    atualizarProjeto(projetoEscolhido);
                                    break;
                                case ATUALIZAR_ARQUIVOS_CONFIG:
                                    UtilSBProjetos.configurarPastaProjeto(projetoEscolhido);
                                    break;
                                case CRIAR_NOVO_PROJETO_EM_SERVIDOR:
                                    UtilSBProjetos.criarNovoProjeto(projetoEscolhido);
                                    break;
                                case CRIAR_NOVO_PROJETO_OFFLINE:
                                    UtilSBProjetos.criarNovoProjetoOffline(projetoEscolhido);
                                    break;

                                default:
                                    throw new AssertionError(opcoesProjeto.name());

                            }
                        }
                    } else {
                        etapaAtual = MENU_ETAPAS.ESCOLHA_CLIENTES;
                    }
                    break;

                case OPCOES_PROJETO:
                    break;
                default:
                    throw new AssertionError(etapaAtual.name());

            }

        }

    }

}
