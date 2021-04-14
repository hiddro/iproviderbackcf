
package com.incloud.hcp.wsdl.proveedor;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SI_P028_MaestroProveedores_Sync_Req_OutService", targetNamespace = "urn:sanmartinperu.pe:portal028:MaestroProveedores", wsdlLocation = "SI_P028_MaestroProveedores_Sync_Req_OutService.wsdl")
public class SIP028MaestroProveedoresSyncReqOutService
    extends Service
{

    private final static URL SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_WSDL_LOCATION;
    private final static WebServiceException SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_EXCEPTION;
    private final static QName SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_QNAME = new QName("urn:sanmartinperu.pe:portal028:MaestroProveedores", "SI_P028_MaestroProveedores_Sync_Req_OutService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("SI_P028_MaestroProveedores_Sync_Req_OutService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_WSDL_LOCATION = url;
        SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_EXCEPTION = e;
    }

    public SIP028MaestroProveedoresSyncReqOutService() {
        super(__getWsdlLocation(), SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_QNAME);
    }

    public SIP028MaestroProveedoresSyncReqOutService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_QNAME, features);
    }

    public SIP028MaestroProveedoresSyncReqOutService(URL wsdlLocation) {
        super(wsdlLocation, SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_QNAME);
    }

    public SIP028MaestroProveedoresSyncReqOutService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_QNAME, features);
    }

    public SIP028MaestroProveedoresSyncReqOutService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SIP028MaestroProveedoresSyncReqOutService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SIP028MaestroProveedoresSyncReqOut
     */
    @WebEndpoint(name = "HTTP_Port")
    public SIP028MaestroProveedoresSyncReqOut getHTTPPort() {
        return super.getPort(new QName("urn:sanmartinperu.pe:portal028:MaestroProveedores", "HTTP_Port"), SIP028MaestroProveedoresSyncReqOut.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SIP028MaestroProveedoresSyncReqOut
     */
    @WebEndpoint(name = "HTTP_Port")
    public SIP028MaestroProveedoresSyncReqOut getHTTPPort(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sanmartinperu.pe:portal028:MaestroProveedores", "HTTP_Port"), SIP028MaestroProveedoresSyncReqOut.class, features);
    }

    /**
     * 
     * @return
     *     returns SIP028MaestroProveedoresSyncReqOut
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SIP028MaestroProveedoresSyncReqOut getHTTPSPort() {
        return super.getPort(new QName("urn:sanmartinperu.pe:portal028:MaestroProveedores", "HTTPS_Port"), SIP028MaestroProveedoresSyncReqOut.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SIP028MaestroProveedoresSyncReqOut
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SIP028MaestroProveedoresSyncReqOut getHTTPSPort(WebServiceFeature... features) {
        return super.getPort(new QName("urn:sanmartinperu.pe:portal028:MaestroProveedores", "HTTPS_Port"), SIP028MaestroProveedoresSyncReqOut.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_EXCEPTION!= null) {
            throw SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_EXCEPTION;
        }
        return SIP028MAESTROPROVEEDORESSYNCREQOUTSERVICE_WSDL_LOCATION;
    }

}
