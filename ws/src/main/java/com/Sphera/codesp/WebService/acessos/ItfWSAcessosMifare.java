/*

 */
package com.Sphera.codesp.WebService.acessos;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Salvio
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ItfWSAcessosMifare {

    @WebMethod(operationName = "credenciaisOperador")
    public List<CartaoDeAcesso> getCredenciaisOperador();

    @WebMethod(operationName = "credenciaisTecnico")
    public List<CartaoDeAcesso> getCredenciaisTecnico();

    /**
     *
     * @return Lista de CartÃµes Mifare cadastrados no sitema informando nome do
     * usuÃ¡rio e STATUS de permissÃ£o
     */
    @WebMethod(operationName = "AcessoPerimitidoPorGate")
    public List<CartaoDeAcesso> getAcessosPermitidosPorGate(@WebParam(name = "codGate") int idGate);

    /**
     *
     * @param pCodigoMifare Codigo do MIFARE
     * @return true Permitido false Negado
     */
    public CartaoDeAcesso acessoPermitido(@WebParam(name = "codMifare") String pCodigoMifare, @WebParam(name = "gate") int pGate);

    public boolean informaAcesso(@WebParam List<CartaoDeAcesso> acessos);

}
