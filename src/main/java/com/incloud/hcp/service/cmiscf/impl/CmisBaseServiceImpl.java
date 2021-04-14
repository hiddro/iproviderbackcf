package com.incloud.hcp.service.cmiscf.impl;

import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.cmiscf.bean.CmisFile;
import com.incloud.hcp.service.cmiscf.bean.CmisFolder;
import com.incloud.hcp.service.cmiscf.bean.CmisFolderSession;
import com.incloud.hcp.service.cmiscf.bean.CmisToken;
import com.sap.ecm.api.EcmService;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Administrador on 26/09/2017.
 */
@Profile("!devlocal")
@Component("cmisServiceCF")
public class CmisBaseServiceImpl implements CmisBaseService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${cmis.server}")
    private String SERVER_CMIS;

    @Value("${cmis.cliente.id}")
    private String CLIENTE_ID;

    @Value("${cmis.cliente.secret}")
    private String CLIENTE_SECRET;

    @Value("${cmis.repository.id}")
    private String REPOSITORY_ID;

    @Value("${cmis.server.credential}")
    private String SERVER_CREDENTIAL_CMIS;

    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }


    public String getToken() throws Exception {
        String url = SERVER_CREDENTIAL_CMIS +  "/oauth/token?grant_type=client_credentials";
        ResponseEntity<CmisToken> token = restTemplate.exchange
                (url, HttpMethod.POST, new HttpEntity<String>(createHeaders(CLIENTE_ID, CLIENTE_SECRET)), CmisToken.class);
        CmisToken account = token.getBody();

        return account.getAccessToken();

    }

    public List<Repository> getListaRepositoryToken() throws Exception {
        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Map parameter = new HashMap();
        parameter.put(SessionParameter.USER, CLIENTE_ID);
        parameter.put(SessionParameter.PASSWORD, CLIENTE_SECRET);

        String token = this.getToken();
        parameter.put(SessionParameter.AUTH_HTTP_BASIC, false);
        parameter.put(SessionParameter.AUTH_SOAP_USERNAMETOKEN, false);
        parameter.put(SessionParameter.AUTH_OAUTH_BEARER, true);
        parameter.put(SessionParameter.OAUTH_ACCESS_TOKEN, token);

        parameter.put(SessionParameter.BROWSER_URL, SERVER_CMIS + "browser");
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
        List<Repository> repositories = sessionFactory.getRepositories(parameter);

        return repositories;
    }


    private Session getSessionRepositoryToken() throws Exception {
        List<Repository> repositories = this.getListaRepositoryToken();
        Repository repository = repositories.get(0);
        Session session = repository.createSession();
        return session;
    }

    public CmisFolder createFolder(String nameFolder) throws Exception {
        Session session = this.getSessionRepositoryToken();
        Folder root = session.getRootFolder();
        ItemIterable<CmisObject> children = root.getChildren();
        CmisFolder cmisFolder = new CmisFolder();
        for (CmisObject cmisObject : children) {
            if (cmisObject.getName().equals(nameFolder)) {
                cmisFolder.setId(cmisObject.getId());
                cmisFolder.setMensaje("La carpeta " + nameFolder + " existe!");
                return cmisFolder;
            }
        }
        Map<String, String> newFolderProps = new HashMap<>();
        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        newFolderProps.put(PropertyIds.NAME, nameFolder);

        Folder newFolder = root.createFolder(newFolderProps);
        cmisFolder.setId(newFolder.getId());
        cmisFolder.setMensaje("Se ha creado un folder en " + newFolder.getPath());
        return cmisFolder;

    }

    private CmisFolderSession obtenerFolderSession(String nameFolder) throws Exception {
        Session session = this.getSessionRepositoryToken();
        Folder root = session.getRootFolder();
        ItemIterable<CmisObject> children = root.getChildren();
        CmisFolder cmisFolder = new CmisFolder();
        CmisFolderSession cmisFolderSession = new CmisFolderSession();
        cmisFolderSession.setSession(session);
        for (CmisObject cmisObject : children) {
            if (cmisObject.getName().equals(nameFolder)) {
                cmisFolder.setId(cmisObject.getId());
                cmisFolder.setMensaje("La carpeta " + nameFolder + " existe!");
                cmisFolderSession.setCmisFolder(cmisFolder);
                return cmisFolderSession;
            }
        }
        Map<String, String> newFolderProps = new HashMap<>();
        newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        newFolderProps.put(PropertyIds.NAME, nameFolder);

        Folder newFolder = root.createFolder(newFolderProps);
        cmisFolder.setId(newFolder.getId());
        cmisFolder.setMensaje("Se ha creado un folder en " + newFolder.getPath());
        cmisFolderSession.setCmisFolder(cmisFolder);
        return cmisFolderSession;

    }

    public CmisFile createDocumento(String nameFolder, MultipartFile file) throws Exception {
        CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
        Session session = cmisFolderSession.getSession();
        CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();

        String extension = Optional.ofNullable(file.getOriginalFilename())
                .map(s -> s.split("\\."))
                .filter(s -> s.length > 0)
                .map(s -> "." + s[s.length - 1])
                .orElse("");
        log.error("createDocumento 00 extension: " + extension);
        String fileNewName = file.getOriginalFilename().replace(extension, "") ;
        log.error("createDocumento 01 fileNewName: " + fileNewName);

        //***********************************************
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";

        //String nombreFinal =fileNewName;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            fileNewName = fileNewName.replace(original.charAt(i), ascii.charAt(i));
        }
        log.error("createDocumento 02 fileNewName: " + fileNewName);
        fileNewName = fileNewName.replaceAll("[^a-zA-Z0-9]", "");
        log.error("createDocumento 03 fileNewName: " + fileNewName);
        log.error("createDocumento 04: " + extension + " fileName: " + fileNewName);
        fileNewName = fileNewName + extension;
        log.error("createDocumento 05 fileNewName: " + fileNewName);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, fileNewName);
        Folder folder = (Folder) session.getObject(cmisFolder.getId());

        InputStream stream = new ByteArrayInputStream(file.getBytes());
        ContentStream contentStream = session.getObjectFactory()
                .createContentStream(fileNewName, file.getSize(), file.getContentType(), stream);
        Document doc = null;
        try {
            doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
        }
        catch (Exception ex) {
            ItemIterable<CmisObject> children = folder.getChildren();
            for (CmisObject cmisObject : children) {
                if (cmisObject.getName().equals(fileNewName)) {
                    doc = (Document) session.getObject(cmisObject.getId());
                    doc.delete(true);
                    doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
                    break;
                }
            }
        }

        CmisFile cmisFile = new CmisFile();
        cmisFile.setId(doc.getId());
        cmisFile.setName(file.getOriginalFilename());
        cmisFile.setNameFinal(fileNewName);
        cmisFile.setExtension(extension);
        cmisFile.setCarpetaId(folder.getId());
        cmisFile.setNombreFolder(nameFolder);
        cmisFile.setType(file.getContentType());
        cmisFile.setSize(file.getSize());
        StringBuilder path = new StringBuilder("/");
        path.append(session.getRootFolder().getId());
        path.append("/root/");
        path.append(folder.getName());
        path.append("/");
        path.append(fileNewName);
        cmisFile.setUrl(path.toString());

        return cmisFile;
    }


    public ByteArrayOutputStream getDocumento(String archivoId) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Session session = this.getSessionRepositoryToken();
        Document doc = (Document) session.getObject(archivoId);
        ContentStream contentStream = doc.getContentStream();
        if (contentStream != null) {
            InputStream inputStream = contentStream.getStream();
            String fila = contentStream.getFileName();
            Long tam = contentStream.getLength();
            String mime = contentStream.getMimeType();
            byte[] bytes = IOUtils.toByteArray(inputStream);

            //String content = getContentAsString(contentStream);
            outputStream.write(bytes);

        }
        return outputStream;

    }

    public CmisFile createDocumentoFromBytes(String nameFolder, String extension, String fileNewName, String contentType, ByteArrayOutputStream file) throws Exception {
        CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
        Session session = cmisFolderSession.getSession();
        CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();

        //***********************************************
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";

        //String nombreFinal =fileNewName;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            fileNewName = fileNewName.replace(original.charAt(i), ascii.charAt(i));
        }
        log.error("createDocumento 02 fileNewName: " + fileNewName);
        fileNewName = fileNewName.replaceAll("[^a-zA-Z0-9]", "");
        log.error("createDocumento 03 fileNewName: " + fileNewName);
        log.error("createDocumento 04: " + extension + " fileName: " + fileNewName);
        fileNewName = fileNewName + extension;
        log.error("createDocumento 05 fileNewName: " + fileNewName);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, fileNewName);
        Folder folder = (Folder) session.getObject(cmisFolder.getId());

        InputStream stream = new ByteArrayInputStream(file.toByteArray());
        ContentStream contentStream = session.getObjectFactory()
                .createContentStream(fileNewName, file.size(), contentType, stream);
        Document doc = null;
        try {
            doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
        }
        catch (Exception ex) {
            ItemIterable<CmisObject> children = folder.getChildren();
            for (CmisObject cmisObject : children) {
                if (cmisObject.getName().equals(fileNewName)) {
                    doc = (Document) session.getObject(cmisObject.getId());
                    doc.delete(true);
                    doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
                    break;
                }
            }
        }

        CmisFile cmisFile = new CmisFile();
        cmisFile.setId(doc.getId());
        cmisFile.setName(fileNewName);
        cmisFile.setNameFinal(fileNewName);
        cmisFile.setExtension(extension);
        cmisFile.setCarpetaId(folder.getId());
        cmisFile.setNombreFolder(nameFolder);
        cmisFile.setType(contentType);
        cmisFile.setSize(Integer.valueOf(file.size()).longValue());
        StringBuilder path = new StringBuilder("/");
        path.append(session.getRootFolder().getId());
        path.append("/root/");
        path.append(folder.getName());
        path.append("/");
        path.append(fileNewName);
        cmisFile.setUrl(path.toString());

        return cmisFile;
    }

    @Override
    public List<CmisFile> updateFileAndMoveVerificar(List<CmisFile> files, String nameFolder) {
        try {
            log.error("Actualización de version del archivo");
            CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
            Session session = cmisFolderSession.getSession();
            CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();

            //InitialContext ctx = new InitialContext();
            //EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            //Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            List<CmisFile> newFiles = new ArrayList<>();
            Optional.ofNullable(files)
                    .ifPresent(l -> l.stream()
                            .peek(item -> log.error("Actualizando el archivo id " + item.getId()))
                            .filter(item -> session.exists(item.getId())) //openCmisSession
                            .forEach(item -> {
                                FileableCmisObject object = (FileableCmisObject) session.getObject(item.getId());
                                CmisObject sourceFolderId = object.getParents().get(0);
                                CmisObject targetFolderId = session.getObject(cmisFolder.getId());//folderId
                                Folder folder = (Folder) session.getObject(cmisFolder.getId());//folderId

                                if (!sourceFolderId.getId().equals(targetFolderId.getId())) {

                                    CmisObject fileMove = object.move(sourceFolderId, targetFolderId);

                                    StringBuilder path = new StringBuilder("/");
                                    path.append(session.getRootFolder().getId());
                                    path.append("/root/");
                                    path.append(folder.getName());
                                    path.append("/");
                                    path.append(fileMove.getName());
                                    item.setUrl(path.toString());

                                    newFiles.add(item);
                                    log.error("ARCHIVO MOVIDO !!!: ");
                                    Map<String, Object> properties = new HashMap<>();
                                    properties.put(PropertyIds.DESCRIPTION, "REGISTRADO");
                                    fileMove.updateProperties(properties, true);
                                }
                            }));
            return newFiles;
        } catch (Exception ex) {
            log.error("Error al actualizar la version de los archivos", ex);
            throw new ServiceException("Error al actualizar la version de los archivos");
        }
    }

    @Override
    public void deleteFiles(List<String> archivoIds, String nameFolder) {
        try {
            log.debug("Actualización de version del archivo");
            /*InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);*/

            CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
            Session session = cmisFolderSession.getSession();
            CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();
            Optional.ofNullable(archivoIds)
                    .ifPresent(l -> l.stream()
                            .peek(id -> log.debug("Actualizando el archivo id " + id))
                            .filter(session::exists)
                            .forEach(id -> {
                                Document doc = (Document) session.getObject(id);
                                doc.delete();
                            }));

        } catch (Exception ex) {
            log.error("Error al elilminar los archivos", ex);
            throw new PortalException("Error al elilminar los archivos");
        }
    }



    @Override
    public void updateFileLastVersionTrue(List<String> filesId, String nameFolder) {
        /*if (isDev) {
            return;
        }*/
        try {
            log.debug("Actualización de version del archivo");
            /*InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);*/
            CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
            Session session = cmisFolderSession.getSession();
            CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();
            Optional.ofNullable(filesId)
                    .ifPresent(l -> l.stream()
                            .peek(id -> log.debug("Actualizando el archivo id " + id))
                            .filter(session::exists)
                            .forEach(id -> {
                                Document doc = (Document) session.getObject(id);
                                Map<String, Object> properties = new HashMap<>();
                                properties.put(PropertyIds.DESCRIPTION, "REGISTRADO");
                                doc.updateProperties(properties, true);
                            }));

        } catch (Exception ex) {
            log.error("Error al actualizar la version de los archivos", ex);
            throw new PortalException("Error al actualizar la vserion de los archivos");
        }
    }

    private static String getContentAsString(ContentStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        //Reader reader = new InputStreamReader(stream.getStream(), "UTF-8");
        Reader reader = new InputStreamReader(stream.getStream());
        try {
            final char[] buffer = new char[4 * 1024];
            int b;
            while (true) {
                b = reader.read(buffer, 0, buffer.length);
                if (b > 0) {
                    sb.append(buffer, 0, b);
                } else if (b == -1) {
                    break;
                }
            }
        } finally {
            reader.close();
        }

        return sb.toString();
    }

    @Override
    public CmisObject getDocumentByFolderAndId(String nameFolder, String archivoId) {
        CmisObject objectByPath = null;
        try {
            CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
            Session session = cmisFolderSession.getSession();

            objectByPath = session.getObject(archivoId);

        } catch (Exception ex) {
            log.error("Error al obtener el documento", ex);
            throw new PortalException("Error al obtener el documento");
        }

        return objectByPath;
    }

    @Override
    public CmisObject getFolder(String nameFolder) {
        CmisObject objectByPath = null;
        try {
            CmisFolderSession cmisFolderSession = this.obtenerFolderSession(nameFolder);
            Session session = cmisFolderSession.getSession();
            CmisFolder cmisFolder = cmisFolderSession.getCmisFolder();

            objectByPath = session.getObject(cmisFolder.getId());

        } catch (Exception ex) {
            log.error("Error al obtener Folder", ex);
            throw new PortalException("Error al obtener Folder");
        }

        return objectByPath;
    }
}
