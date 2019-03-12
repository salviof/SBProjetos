/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Sphera.codesp.WebService.acessos;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.TratamentoDeErros.ErroSB;
import com.super_bits.modulosSB.SBCore.UtilGeral.UTilSBCoreInputs;
import com.super_bits.modulosSB.SBCore.UtilGeral.UtilSBCoreStrings;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.jws.WebService;

/**
 *
 *
 *
 * @author Alline Basile
 */
@WebService(endpointInterface = "com.Sphera.codesp.WebService.acessos.ItfWSAcessosMifare")
public class WSAcessosMifare implements ItfWSAcessosMifare {

    @Override
    public boolean informaAcesso(List<CartaoDeAcesso> acessos) {
        if (acessos == null) {
            return false;
        }
        for (CartaoDeAcesso cartao : acessos) {
            if (cartao.isAcessoPermitido()) {
                // UtilSBPersistencia.executaSQL("Insert Into Ca_historico(Codigo,Credencial,Controladora,Data,Evento,Descricao) Values(seqhist.nextval,'"+cartao.getCodigoMifar()+"','G12Can01',sysdate,1,'Dispositivo Móvel')");
                //10.1.2.16:1429/crednotify.asp?ctrl=G12Can01&hora=aaaammddhhmmss&type=0&cred=y
                List<String> resposta = UTilSBCoreInputs.getStringsByURL(null);

            } else {
                // UtilSBPersistencia.executaSQL("Insert Into Ca_historico(Codigo,Credencial,Controladora,Data,Evento,Descricao) Values(seqhist.nextval,'"+cartao.getCodigoMifar()+"','G12Can01',sysdate,801,'Dispositivo Móvel')");
                //10.1.2.16:1429/crednotify.asp?ctrl=G12Can01&hora=aaaammddhhmmss&type=1&cred=y
            }

        }
        return false;
    }

    @Override
    public List<CartaoDeAcesso> getCredenciaisOperador() {
        throw new UnsupportedOperationException("NAO FOI IMPLEMENTADO."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CartaoDeAcesso> getCredenciaisTecnico() {
        throw new UnsupportedOperationException("NAO FOI IMPLEMENTADO."); //To change body of generated methods, choose Tools | Templates.
    }

    private enum TipoOrigem {

        CSV, BANCO, TESTES
    }

    private final TipoOrigem origem = TipoOrigem.TESTES;

    protected List<CartaoDeAcesso> getCartoesPorCSV(int pGate) {
        try {

            List<String> listaacesso = UTilSBCoreInputs.getStringsByURL("http://10.1.2.16:1429/credlist.asp?gate=" + Integer.toString(pGate));
            List<CartaoDeAcesso> resposta = new ArrayList<>();
            for (String linha : listaacesso) {
                resposta.add(buildCartaoDeAcessoByLinhaCSV(linha));
            }

            List<CartaoDeAcesso> respTeste = new ArrayList<>();
            respTeste.add(resposta.get(0));
            respTeste.add(resposta.get(1));
            respTeste.add(resposta.get(2));
            return resposta;
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro ao obter a lista de acessos CSV.", e);
        }
        return null;
    }

    protected List<CartaoDeAcesso> getCartoesPorBanco(int pGate) {

        List<CartaoDeAcesso> resposta = new ArrayList<>();
        List<Object[]> registros = (List<Object[]>) UtilSBPersistencia.getListaRegistrosBySQL("SELECT lpad(D.CREDENCIAL, 8, '0')CREDENCIAL, A.NOME, A.ID_PESSOA ID FROM TB_PESSOA A, TB_PESSOA_EMPRESA B, TB_PESSOA_LOCAL C, TB_CREDENCIAL_PESSOA D, TB_EMPRESA E "
                + "WHERE A.ID_PESSOA = B.ID_PESSOA AND "
                + "B.ID_PESSOA_EMPRESA = C.ID_PESSOA_EMPRESA AND "
                + "A.ID_PESSOA = D.ID_PESSOA AND "
                + "B.ID_EMPRESA = E.ID_EMPRESA AND "
                + "A.FLAG_ATIVO = 5 AND "
                + "C.FLAG_ATIVO = 7 AND "
                + "D.FLAG_ATIVO = 21 AND "
                + "B.ID_EMPRESA <> 2 AND "
                + "E.FLAG_ATIVO = 6 AND "
                + "C.ID_LOCAL = " + pGate + " group by D.CREDENCIAL, A.NOME, A.ID_PESSOA "
                + "", 0);

        for (Object[] registro : registros) {
            resposta.add(buildCartaoDeAcessoByRegistroSQL(registro));
        }
        List<CartaoDeAcesso> respTeste = new ArrayList<>();
        respTeste.add(resposta.get(0));
        respTeste.add(resposta.get(1));
        respTeste.add(resposta.get(2));

        return resposta;
    }

    protected CartaoDeAcesso buildCartaoDeAcessoByLinhaCSV(String linha) {
        CartaoDeAcesso novoCartao = new CartaoDeAcesso();
        novoCartao.setCodigoMifar(linha.substring(0, 8));
        novoCartao.setNome(linha.substring(9, linha.length()));
        novoCartao.setAcessoPermitido(true);

        return novoCartao;
    }

    /**
     * Monta o Cartão de Acesso pelo registro de SQL seguindo o padrão:
     *
     * Codigo Mifare + Nome + Obeservação
     *
     * @param registro Registro Obtido via JQPL
     * @return Cartao De acesso
     */
    protected CartaoDeAcesso buildCartaoDeAcessoByRegistroSQL(Object[] registro) {
        CartaoDeAcesso novoCartao = new CartaoDeAcesso();
        int i = 0;
        for (Object campo : registro) {
            switch (i) {
                case 0:
                    novoCartao.setCodigoMifar(campo.toString());
                    break;
                case 1:
                    novoCartao.setNome(campo.toString());
                    break;
                case 2:
                    novoCartao.setUrlFoto("http://10.1.2.8/fotos/id" + UtilSBCoreStrings.getLpad(campo.toString(), 12, "0") + ".JPG");
                    break;

            }
            novoCartao.setAcessoPermitido(true);
            i++;
        }
        return novoCartao;
    }

    private String getMiFareAleatorio() {
        Random radom = new Random();
        int numeroTmp = 0;
        String numStr = new String();
        for (int i = 0; i < 10; i++) {
            numeroTmp = radom.nextInt(10);

            numStr = numStr + numeroTmp;
        }

        BigInteger toHex = new BigInteger(numStr, 10);
        return toHex.toString(16);

    }

    protected List<CartaoDeAcesso> getCartoesTestes() {

        List<CartaoDeAcesso> resp = new ArrayList<>();
        CartaoDeAcesso cartaoChaveiro = new CartaoDeAcesso();
        cartaoChaveiro.setCodigoMifar("D9F5EA6B");
        cartaoChaveiro.setNome("Chaveiro Azul da Silva");
        cartaoChaveiro.setUrlFoto("http://sspp.codesp.com.br/fotos/id000000100465.jpg");
        cartaoChaveiro.setAcessoPermitido(true);

        for (int i = 0; i < 10; i++) {
            CartaoDeAcesso novoCartao = new CartaoDeAcesso();
            String mifairAleatorio = getMiFareAleatorio();
            novoCartao.setCodigoMifar(mifairAleatorio);
            novoCartao.setNome("Teste" + mifairAleatorio);
            novoCartao.setAcessoPermitido(true);
            resp.add(novoCartao);
        }
        CartaoDeAcesso cartaoelemento = new CartaoDeAcesso();
        cartaoelemento.setCodigoMifar("B6D1D67A");
        cartaoelemento.setNome("Alline Basile");
        cartaoelemento.setUrlFoto("http://sspp.codesp.com.br/fotos/id000000100465.jpg");
        cartaoelemento.setAcessoPermitido(true);

        CartaoDeAcesso cartaoelemento2 = new CartaoDeAcesso();
        cartaoelemento2.setCodigoMifar("041b32d4");
        cartaoelemento2.setNome("cartao bug oj");
        cartaoelemento2.setUrlFoto("http://sspp.codesp.com.br/fotos/id000000100465.jpg");
        cartaoelemento2.setAcessoPermitido(true);

        CartaoDeAcesso chico = new CartaoDeAcesso();
        chico.setCodigoMifar("1896303E");
        chico.setNome("Francisco Sphera Security ");
        chico.setUrlFoto("http://www.fotossaopaulo.com.br/clubes-de-sao-paulo/Santos/Santos1.jpg");
        chico.setAcessoPermitido(true);
        resp.add(chico);

        CartaoDeAcesso joaquim = new CartaoDeAcesso();
        joaquim.setCodigoMifar("40660915");
        joaquim.setNome("Joaquim Da Silva Marques Neto");
        joaquim.setUrlFoto("http://sspp.codesp.com.br/fotos/id000000039716.JPG");
        resp.add(joaquim);
        resp.add(cartaoelemento);
        resp.add(cartaoelemento2);
        resp.add(cartaoChaveiro);
        return resp;
    }

    @Override
    public List<CartaoDeAcesso> getAcessosPermitidosPorGate(int pGate) {
        try {
            switch (origem) {
                case BANCO:
                    return getCartoesPorBanco(pGate);
                case CSV:
                    return getCartoesPorCSV(pGate);
                case TESTES:
                    return getCartoesTestes();
            }

        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro ao obter a lista de acessos permitidos por gate.", e);
        }
        return null;
    }

    @Override
    public CartaoDeAcesso acessoPermitido(String pCodigoMifare, int pGate) {
        Object[] resposta = (Object[]) UtilSBPersistencia.getRegistroBySQL("SELECT A.NOME,A.ID_PESSOA ID FROM TB_PESSOA A, TB_PESSOA_EMPRESA B, TB_PESSOA_LOCAL C, TB_CREDENCIAL_PESSOA D "
                + "WHERE A.ID_PESSOA = B.ID_PESSOA AND "
                + "B.ID_PESSOA_EMPRESA = C.ID_PESSOA_EMPRESA AND "
                + "A.ID_PESSOA = D.ID_PESSOA AND "
                + "A.FLAG_ATIVO = 5 AND "
                + "C.FLAG_ATIVO = 7 AND "
                + "D.FLAG_ATIVO = 21 AND "
                + "D.CREDENCIAL = '" + pCodigoMifare + "'  AND "
                + "C.ID_LOCAL =  " + pGate + " group by D.CREDENCIAL, A.NOME, A.ID_PESSOA "
                + " ");
        System.out.println("resposta" + resposta);
        if (resposta != null) {
            CartaoDeAcesso cartao = new CartaoDeAcesso();
            cartao.setNome(resposta[0].toString());
            BigDecimal idUsuario = (BigDecimal) resposta[1];
            cartao.setUrlFoto("http://10.1.2.8/fotos/id" + UtilSBCoreStrings.getLpad(idUsuario.toString(), 12, "0") + ".JPG");
            return cartao;
        } else {
            return null;
        }

    }

}
