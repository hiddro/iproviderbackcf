package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionSubetapa;
import com.incloud.hcp.domain.Proveedor;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 15/11/2017.
 */

@Component
public class LicitacionActualizarEtapasNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpLicitacionActualizarEtapas.html";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;


//    public void enviar(
//            MailSetting mailSetting,
//            Proveedor proveedor,
//            Licitacion licitacion,
//            List<LicitacionSubetapa> licitacionSubetapaList,
//            String camposEdit,
//            String userRepublica,
//            String emailRepublica) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//
//        context.put("nombreProveedor", proveedor.getRazonSocial());
//        context.put("nroLicitacion", licitacion.getNroLicitacionString());
//        context.put("nombreUsuarioPublicacion", userRepublica);
//        context.put("emailUsuarioPublicacion", emailRepublica);
//        context.put("licitacionSubetapaList", licitacionSubetapaList);
//        context.put("url",this.urlPortal);
//
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_VENDOR))
//                .ifPresent(cid -> context.put("vendor_logo", cid));
//
//        Optional.ofNullable(generateCid(mail.getHtmlMail(), LOGO_SAN_MARTIN))
//                .ifPresent(cid -> context.put("sm_logo", cid));
//
//        String content = Optional.ofNullable(TEMPLATE)
//                .map(url -> url + "")
//                .map(template -> {
//                    int i = 0;
//                    return getContentMail(context, template);
//                })
//                .orElse("");
//        mail.setMailSetting(mailSetting);
//        try {
//            String asunto = "Actualizaci??n Etapas - Licitaci??n Nro. " + licitacion.getNroLicitacionString();
//
//            mail.enviar(proveedor.getEmail(), null, asunto, content);
//
//
//        } catch (EmailException ex) {
//
//            logger.error("Error al enviar notificacion LicitacionActualizarEtapasNotificacion" + ex.getMessage());
//            ex.printStackTrace();
//        }
//    }

    public void  enviar(MailSetting mailSetting, Proveedor proveedor,
                        Licitacion licitacion, List<LicitacionSubetapa> licitacionSubetapaList,
                        String camposEdit, String userRepublica, String emailRepublica) {
        String rucProveedor = proveedor.getRuc();
        String nombreProveedor = proveedor.getRazonSocial();
        String numeroLicitacion = licitacion.getNroLicitacionString();
        String asunto = "Actualizaci??n Etapas - Licitaci??n Nro. " + numeroLicitacion;


        ////////////////////// BEGIN METODO VELPA //////////////////////

        String body = "<!DOCTYPE html>";
        body = body + "<html lang='en'>";
        body = body + "<head>";
        body = body + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>";
        body = body + "</head>";
        body = body + "<body style='width: 600px;margin: auto;padding: 0;font-family: Arial;font-size: 14px;color: #333'>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<h1 style='font-family: Arial;font-weight: bold;font-size: 16px;color: #555;margin-bottom: 24px'>";
        body = body + "Estimado " + nombreProveedor + "&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Se le informa que la Licitaci??n Nro. " + numeroLicitacion + "<br/>";
        body = body + "ha sido actualizada en las fechas de Cierre de sus Etapas.<br/>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' border='1px #deb887'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td bgcolor='#add8e6' align='center' ";
        body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
        body = body + "<span style='font-weight: bold'>Etapa de Licitaci??n</span>";
        body = body + "</td>";
        body = body + "<td bgcolor='#add8e6' align='center' ";
        body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
        body = body + "<span style='font-weight: bold'>Fecha de Cierre</span>";
        body = body + "</td>";
        body = body + "</tr>";
        for(LicitacionSubetapa licSubetapa : licitacionSubetapaList) {
            body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0; border: 1px #2b3f7b '>";
            body = body + "<td align='center' ";
            body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
            body = body + licSubetapa.getIdSubetapa().getDescripcionSubetapa();
            body = body + "</td>";
            body = body + "<td align='center' ";
            body = body + "style='border-collapse: collapse;width: 300px;padding: 0;margin-left: 5px;padding-top: 36px;padding-bottom: 12px'>";
            body = body + licSubetapa.getFechaCierreSubetapaString();
            body = body + "</td>";
            body = body + "</tr>";
        }
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_first' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 12px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "En caso tenga alguna duda&#44; puede contactarse a " + emailRepublica + "&#44;<br/>";
        body = body + "caso contrario s??rvase hacer click en el siguiente enlace para ingresar a su cuenta:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click aqu?? para ingresar a su cuenta</a>";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellpadding='0' cellspacing='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='colophon' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;font-size: 11px;color: #888;line-height: 13px'>";
        body = body + "<p style='font-family: Arial;font-size: 12px;line-height: 13px;color: #888'>";
        body = body + "AGRADECEREMOS NO RESPONDER ESTE CORREO. SI LO DESEA ENVIE SU CONSULTA A:<br/>";
        body = body + urlConsulta;
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "<table cellspacing='0' cellpadding='0' width='600px'>";
        body = body + "<tr style='border-collapse: collapse;width: 600px;padding: 0;margin: 0'>";
        body = body + "<td class='main_last' ";
        body = body + "style='border-collapse: collapse;width: 600px;padding: 0;margin: 0;padding-top: 12px;padding-bottom: 36px'>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Atentamente&#44;<br/>";
        body = body + "Equipo IProvider<br/>";
        body = body + "CFG - Copeinca";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "</body>";
        body = body + "</html>";

        //////////////////////  END  METODO VELPA //////////////////////


        try {
            this.enviarCorreoSap(proveedor.getEmail(), asunto, body);
        } catch (Exception ex) {
            logger.error("Error al enviar el correo por LicitacionActualizarEtapasNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor + " por la licitacion " + numeroLicitacion, ex);
        }
    }
}
