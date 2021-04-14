package com.incloud.hcp.service.notificacion;

import com.incloud.hcp.config.MailConfiguration;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import okhttp3.*;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrador on 13/11/2017.
 */

class NotificarMail {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${destination.email}")
    protected String destinationEmail;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailConfiguration mailConfig;

    public NotificarMail() {
    }

    VelocityEngine getVelocityEngine() {
        logger.error("Inicialización de VelocityEngine");
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();

        return velocity;
    }

    String getContentMail(VelocityContext context, String template) {
        logger.error("Generando el contenido de la notificación");
        VelocityEngine velocity = getVelocityEngine();
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }

    String generateCid(HtmlEmail htmlMail, String imagen) {

        return Optional.ofNullable(NotificarMail.class.getClassLoader().getResource(imagen))
                .map(url -> {
                    logger.error("Obteniendo el path de la imagen ");
                    logger.error("url inicial : " + url);
                    String aux = url + "";
                    aux = aux.replace("file:", "");
                    logger.error("La ruta de la imagen es la siguiente " + aux);
                    return aux;
                })
                .map(p -> new File(p))
                .map(f -> {
                    try {
                        logger.error("Colocando la imagen en el contenido HTML");
                        String cid = htmlMail.embed(f);
                        return cid;
                    } catch (EmailException ex) {
                        logger.error("Error al colocar la imagen en el contenido HTML", ex);
                        return null;
                    }
                }).orElse(null);

    }

    protected void enviarCorreoSap(String paraCorreo, String asuntoCorreo, String bodyCorreo) {
        HttpURLConnection urlConnection = null;
        try {
            //logger.error("tipoCorreo :: " + tipoCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\"", "'");
            logger.error("Ingresando enviarCorreoSap 01a - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\\r", "");
            //log.debug("Ingresando enviarCorreoSap 01b - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\r", "");
            //log.debug("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\\r|\\n", "");
            logger.error("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);

            //bodyCorreo = bodyCorreo.replace(System.getProperty("line.separator"), " ");
            //bodyCorreo = HtmlEscapers.htmlEscaper().escape(bodyCorreo);
            Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");
            logger.error("Ingresando enviarCorreoSap 02 - configuration: " + configuration);
            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration(this.destinationEmail);
            if (destConfiguration == null) {
                return;
            }
            logger.error("Ingresando enviarCorreoSap 03 - destConfiguration: " + destConfiguration);

            // Get the destination URL
            String valueURL = destConfiguration.getProperty("URL");
            //URL url = new URL(valueURL);
            //log.debug("Ingresando enviarCorreoSap 04 - url: " + url);

            //String proxyType = destConfiguration.getProperty("ProxyType");
            //Proxy proxy = getProxy(proxyType);
            //log.debug("Ingresando enviarCorreoSap 05 - proxyType: " + proxyType);
            //log.debug("Ingresando enviarCorreoSap 05b - proxy: " + proxy);

            //urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection = (HttpURLConnection) url.openConnection(proxy);

            // Insert the required header in the request for on-premise destinations
            //injectHeader(urlConnection, proxyType);
            logger.error("Ingresando enviarCorreoSap 09 - urlConnection: " + urlConnection);
            Map<String, String> mapProperties = destConfiguration.getAllProperties();
            for (Map.Entry<String, String> entry : mapProperties.entrySet())
            {
                logger.error("Ingresando enviarCorreoSap 06b properties - " + entry.getKey() + "/" + entry.getValue());

            }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            logger.error("Ingresando enviarCorreoSap 09");

            // Copy content from the incoming response to the outgoing response
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("text/xml");
            logger.error("Ingresando enviarCorreoSap 09 correos " + paraCorreo);

            String variablePrueba = "";
            variablePrueba = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ";
            variablePrueba = variablePrueba + "xmlns:sen=\"http://www.example.org/sendMailIprovider/\">";
            variablePrueba = variablePrueba + "<soapenv:Header/>";
            variablePrueba = variablePrueba + "<soapenv:Body>";
            variablePrueba = variablePrueba + "<sen:NewOperation>";
            variablePrueba = variablePrueba + "<auxiliar>auxi</auxiliar>";
            variablePrueba = variablePrueba + "<subject>" + asuntoCorreo + "</subject>";
            variablePrueba = variablePrueba + "<body><![CDATA[" + bodyCorreo + "]]></body>";
            int count = 0;
            if (paraCorreo != null && !paraCorreo.isEmpty()) {
                paraCorreo = paraCorreo.concat(",admin_iprovider@copeinca.com.pe");
                String[] to = paraCorreo.split(",");
                if (to != null && to.length > 0) {
                    for (String t : to) {
                        if (!t.isEmpty()) {
                            count++;
                            variablePrueba = variablePrueba + "<to" + count + ">" + t.trim()  +"</to" + count +">";
                        }
                    }
                }
            }
            //variablePrueba = variablePrueba + "<to1>pavelprincipe@gmail.com</to1>";
            //variablePrueba = variablePrueba + "<to2>pprincipe@csticorp.biz</to2>";
            variablePrueba = variablePrueba + "</sen:NewOperation>";
            variablePrueba = variablePrueba + "</soapenv:Body>";
            variablePrueba = variablePrueba + "</soapenv:Envelope>";

            logger.error("Ingresando enviarCorreoSap 09 bodyResponse - variablePrueba: " + variablePrueba);
            RequestBody body = RequestBody.create(mediaType, variablePrueba);


            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic cHByaW5jaXBlLmNvbnNAZ21haWwuY29tOkluaWNpbzAxJCQ=")
                    .build();
            logger.error("Ingresando enviarCorreoSap 09");
            Response response = client.newCall(request).execute();
            logger.error("Ingresando enviarCorreoSap 10");

            //InputStream instream = urlConnection.getInputStream();
            //OutputStream outstream = response.getOutputStream();
            //copyStream(instream, outstream);
            //ResponseBody response_=response.body();
            //response_.string();
        }
        catch(Exception e) {
            e.printStackTrace();

            // Connectivity operation failed
            String errorMessage = "Connectivity operation failed with reason: "
                    + e.getMessage()
                    + ". See "
                    + "logs for details. Hint: Make sure to have an HTTP proxy configured in your "
                    + "local environment in case your environment uses "
                    + "an HTTP proxy for the outbound Internet "
                    + "communication.";
            logger.error("Connectivity operation failed", e);

        }
    }
    protected void enviarCorreoSap2(String paraCorreo, String asuntoCorreo, String bodyCorreo) {
        HttpURLConnection urlConnection = null;
        try {
            bodyCorreo = bodyCorreo.replaceAll("\"", "'");
            logger.error("Ingresando enviarCorreoSap 01a - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\\r", "");
            //log.debug("Ingresando enviarCorreoSap 01b - bodyCorreo: " + bodyCorreo);
            //bodyCorreo = bodyCorreo.replaceAll("\r", "");
            //log.debug("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);
            bodyCorreo = bodyCorreo.replaceAll("\\r|\\n", "");
            logger.error("Ingresando enviarCorreoSap 01c - bodyCorreo: " + bodyCorreo);

            //bodyCorreo = bodyCorreo.replace(System.getProperty("line.separator"), " ");
            //bodyCorreo = HtmlEscapers.htmlEscaper().escape(bodyCorreo);
            Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                    "java:comp/env/connectivityConfiguration");
            logger.error("Ingresando enviarCorreoSap 02 - configuration: " + configuration);
            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration(this.destinationEmail);
            if (destConfiguration == null) {
                return;
            }
            logger.error("Ingresando enviarCorreoSap 03 - destConfiguration: " + destConfiguration);

            // Get the destination URL
            String valueURL = destConfiguration.getProperty("URL");
            //URL url = new URL(valueURL);
            //log.debug("Ingresando enviarCorreoSap 04 - url: " + url);

            //String proxyType = destConfiguration.getProperty("ProxyType");
            //Proxy proxy = getProxy(proxyType);
            //log.debug("Ingresando enviarCorreoSap 05 - proxyType: " + proxyType);
            //log.debug("Ingresando enviarCorreoSap 05b - proxy: " + proxy);

            //urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection = (HttpURLConnection) url.openConnection(proxy);

            // Insert the required header in the request for on-premise destinations
            //injectHeader(urlConnection, proxyType);
//            log.debug("Ingresando enviarCorreoSap 06 - urlConnection: " + urlConnection);
            Map<String, String> mapProperties = destConfiguration.getAllProperties();
            for (Map.Entry<String, String> entry : mapProperties.entrySet())
            {
                logger.error("Ingresando enviarCorreoSap 06b properties - " + entry.getKey() + "/" + entry.getValue());

            }
            //String cloudConnectorLocationId = destConfiguration.getProperty("CloudConnectorLocationId");
            logger.error("Ingresando enviarCorreoSap 07");

            // Copy content from the incoming response to the outgoing response
//            OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            MediaType mediaType = MediaType.parse("text/xml");
            logger.error("Ingresando enviarCorreoSap 08");

            String variablePrueba = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cxf=\"http://cxf.component.camel.apache.org/\"><soapenv:Header/> <soapenv:Body> <cxf:invoke> <msg> <![CDATA[\t{\"para\":\"" +
                    paraCorreo +
                    "\",\"asunto\":\"" +
                    asuntoCorreo +
                    "\", \"cuerpo\":\""+ "" +
                    bodyCorreo +
                    "\"} ]]> </msg>" +
                    "\t</cxf:invoke>" +
                    "   </soapenv:Body> " +
                    "</soapenv:Envelope>";

            logger.error("Ingresando enviarCorreoSap 08 bodyResponse - variablePrueba: " + variablePrueba);
            RequestBody body = RequestBody.create(mediaType, variablePrueba);


            Request request = new Request.Builder()
                    .url(valueURL)
                    .post(body)
                    .addHeader("content-type", "text/xml")
                    .addHeader("Authorization", "Basic cHByaW5jaXBlLmNvbnNAZ21haWwuY29tOkluaWNpbzAxJCQ=")
                    .build();
            logger.error("Ingresando enviarCorreoSap 09");
            Response response = client.newCall(request).execute();
            logger.error("Ingresando enviarCorreoSap 10: " + response);

            //InputStream instream = urlConnection.getInputStream();
            //OutputStream outstream = response.getOutputStream();
            //copyStream(instream, outstream);
            //ResponseBody response_=response.body();
            //response_.string();
        }
        catch(Exception e) {
            e.printStackTrace();

            // Connectivity operation failed
            String errorMessage = "Connectivity operation failed with reason: "
                    + e.getMessage()
                    + ". See "
                    + "logs for details. Hint: Make sure to have an HTTP proxy configured in your "
                    + "local environment in case your environment uses "
                    + "an HTTP proxy for the outbound Internet "
                    + "communication.";
            logger.error("Connectivity operation failed", e);

        }
    }

    protected void enviarCorreoSMTP(String paraCorreo, String asuntoCorreo, String bodyCorreo, MailSetting mailSetting) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(asuntoCorreo);

            helper.setTo(paraCorreo);
            helper.setFrom(mailSetting.getEmailFrom());

            /*if (mail.getContentType() != null) {
                helper.setText(mail.getContentType());
            }*/
            helper.setText(bodyCorreo, true);

            /*if (mail.getCc() != null) {
                helper.setCc(mail.getCc().trim());
            } else if (mail.getCcArray() != null) {
                helper.setCc(mail.getCcArray());
            } else if (mail.getBcc() != null) {
                helper.setBcc(mail.getBcc().trim());
            } else if (mail.getBccArray() != null) {
                helper.setBcc(mail.getBccArray());
            } else if (mail.getReplyTo() != null) {
                helper.setReplyTo(mail.getReplyTo().trim());
            }*/

            /*if (mail.getFileName() != null && (mail.getFile() != null || mail.getInputStreamSource() != null)) {
                if (mail.getFile() != null) {
                    helper.addAttachment(mail.getFileName(), mail.getFile());
                } else if (mail.getInputStreamSource() != null) {
                    helper.addAttachment(mail.getFileName(), mail.getInputStreamSource());
                } else if (mail.getInputStreamSource() != null && mail.getContentType() != null) {
                    helper.addAttachment(mail.getFileName(), mail.getInputStreamSource(), mail.getContentType());
                }
            }*/

            emailSender = mailConfig.getMailSender();

            emailSender.send(message);
            //response.setMessage("mail send to : " + mail.getTo());
            //response.setStatus(Boolean.TRUE);
        }
        catch (MessagingException e) {
            logger.error("SMTP Mail Sending failure : {}", e.getMessage());
            //response.setMessage("Mail Sending failure : "+e.getMessage());
            //response.setStatus(Boolean.FALSE);
        }
    }

}
