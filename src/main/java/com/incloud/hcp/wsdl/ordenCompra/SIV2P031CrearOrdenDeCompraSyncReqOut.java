
package com.incloud.hcp.wsdl.ordenCompra;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SI_v2_P031_CrearOrdenDeCompra_Sync_Req_Out", targetNamespace = "urn:sanmartinperu.pe:portal031:CrearOrdendeCompra")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SIV2P031CrearOrdenDeCompraSyncReqOut {


    /**
     * 
     * @param mtV2P031CrearOrdenDeCompraReq
     * @return
     *     returns com.ordenCompra.DTV2P031CrearOrdenDeCompraRes
     */
    @WebMethod(operationName = "SI_P031_CrearOrdenDeCompra_Sync_Req_In", action = "http://sap.com/xi/WebService/soap1.1")
    @WebResult(name = "MT_v2_P031_CrearOrdenDeCompra_Res", targetNamespace = "urn:sanmartinperu.pe:portal031:CrearOrdendeCompra", partName = "MT_v2_P031_CrearOrdenDeCompra_Res")
    public DTV2P031CrearOrdenDeCompraRes siP031CrearOrdenDeCompraSyncReqIn(
            @WebParam(name = "MT_v2_P031_CrearOrdenDeCompra_Req", targetNamespace = "urn:sanmartinperu.pe:portal031:CrearOrdendeCompra", partName = "MT_v2_P031_CrearOrdenDeCompra_Req")
                    DTV2P031CrearOrdenDeCompraReq mtV2P031CrearOrdenDeCompraReq);

}