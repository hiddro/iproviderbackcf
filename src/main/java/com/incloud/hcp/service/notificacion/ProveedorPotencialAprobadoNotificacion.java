package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.domain.PreRegistroProveedor;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Administrador on 13/11/2017.
 */
@Component
public class ProveedorPotencialAprobadoNotificacion extends NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String LOGO_VENDOR = "com/incloud/hcp/image/vendor_logo.png";
    private final String LOGO_SAN_MARTIN = "com/incloud/hcp/image/sm_logo.png";
    private final String TEMPLATE = "com/incloud/hcp/templates/portal/TmpProveedorPotencialAprobado.html";
//    private final String TEMPLATERECHAZO = "com/incloud/hcp/templates/portal/TmpProveedorPotencialRechazado.html";
    private final String ASUNTO = "IProvider - Proveedor potencial aprobado";
//    private final String ASUNTORECHAZO = "IProvider - Proveedor potencial";

//    @Value("${sm.portal.url}")
    @Value("${cfg.portal.url}")
    private  String urlPortal;

    @Value("${cfg.notificacion.consulta.url}")
    private String urlConsulta;

//    public void enviar(MailSetting mailSetting, PreRegistroProveedor preRegistro) {
//
//        Mail mail = new Mail();
//        VelocityContext context = new VelocityContext();
//        context.put("contacto", preRegistro.getContacto());
//        context.put("url",this.urlPortal);
//
//        //String templateMail = (preRegistro.getEstado().equalsIgnoreCase(EstadoPreRegistroEnum.APROBADA.getCodigo()))? TEMPLATE:TEMPLATERECHAZO;
//        //String asuntoMail = (preRegistro.getEstado().equalsIgnoreCase(EstadoPreRegistroEnum.APROBADA.getCodigo()))? ASUNTO:ASUNTORECHAZO;
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
//            mail.enviar(preRegistro.getEmail(), null, ASUNTO, content);
//        } catch (EmailException ex) {
//            logger.error("Error al enviar notificacion al proveedor ", ex);
//        }
//    }


    public void enviar(MailSetting mailSetting, PreRegistroProveedor preRegistro){
        String contactoProveedor = preRegistro.getContacto();
        String rucProveedor = preRegistro.getRuc();
        String nombreProveedor = preRegistro.getRazonSocial();


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
        body = body + "Estimado " + contactoProveedor + "&#44;</h1>";
        body = body + "<p style='font-family: Arial;font-size: 14px;line-height: 18px'>";
        body = body + "Gracias por registrarse en el IProvider de Silvestre&#44;<br/>";
        body = body + "por favor ingrese al siguiente enlace para completar su informaci??n:<br/>";
        body = body + "<a href=\"" + urlPortal + "\">Click para ingresar a su cuenta</a>";
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
        body = body + "Silvestre S.A.C.";
        body = body + "</p>";
        body = body + "</td>";
        body = body + "</tr>";
        body = body + "</table>";

        body = body + "</body>";
        body = body + "</html>";

        //////////////////////  END  METODO VELPA //////////////////////


        try {
            this.enviarCorreoSMTP(preRegistro.getEmail(), ASUNTO, body, mailSetting);
        } catch (Exception ex) {
            logger.error("Error al enviar el correo por ProveedorPotencialAprobadoNotificacion al proveedor " + nombreProveedor + " con RUC " + rucProveedor, ex);
        }
    }
}
