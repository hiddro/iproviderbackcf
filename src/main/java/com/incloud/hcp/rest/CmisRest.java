package com.incloud.hcp.rest;

import com.incloud.hcp.bean.CmisFile;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.dto.CuentaBancariaDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.*;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.delta.ProveedorAdjuntoSunatDeltaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrador on 26/09/2017.
 */

@RestController
@RequestMapping(value = "/api/repositorio")
public class CmisRest extends AppRest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CmisService cmisService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorCatalogoService proveedorCatalogoService;

    @Autowired
    private ProveedorAdjuntoSunatDeltaService proveedorAdjuntoSunatService;

    @Autowired
    private CotizacionAdjuntoRepository cotizacionAdjuntoRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Autowired
    private CotizacionAdjuntoService cotizacionAdjuntoService;

    @Autowired
    private LicitacionAdjuntoRepository licitacionAdjuntoRepository;

    @Autowired
    private LicitacionAdjuntoService licitacionAdjuntoService;

    @Autowired
    private AdjuntoOrdenCompraService adjuntoOrdenCompraService;

    @Autowired
    private AdjuntoOrdenCompraRepository adjuntoOrdenCompraRepository;

    @Autowired
    private PreRegistroProveedorService preRegistroProveedorService;

    @Autowired
    private CmisBaseService cmisBaseServicecf;


    @RequestMapping(value = "/proveedor/catalogos",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addCatalogo(@RequestParam("files") MultipartFile file) throws Exception {
        logger.debug("Agregrando catalogo [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";
        //String folderId = cmisService.createFolder(newFolder);
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        String folderId = cmisBaseServicecf.createFolder(newFolder).getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);
        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        ProveedorCatalogo proveedorCatalogo = new ProveedorCatalogo();
        proveedorCatalogo.setArchivoId(cmisFileCF.getId());
        proveedorCatalogo.setArchivoNombre(cmisFileCF.getName());
        proveedorCatalogo.setRutaCatalogo(cmisFileCF.getUrl());
        proveedorCatalogo.setArchivoTipo(cmisFileCF.getType());

        return this.processObject(proveedorCatalogo);
    }

    @RequestMapping(value = "/proveedor/adjuntosSunat",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoSunat(@RequestParam("fileSunat") MultipartFile file) throws Exception {
        logger.debug("Agregrando adjunto sunat [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";
        //String folderId = cmisService.createFolder(newFolder);
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        String folderId = cmisBaseServicecf.createFolder(newFolder).getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);

        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        ProveedorAdjuntoSunat proveedorAdjuntoSunat = new ProveedorAdjuntoSunat();
        proveedorAdjuntoSunat.setArchivoId(cmisFileCF.getId());
        proveedorAdjuntoSunat.setArchivoNombre(cmisFileCF.getName());
        proveedorAdjuntoSunat.setRutaAdjunto(cmisFileCF.getUrl());
        proveedorAdjuntoSunat.setArchivoTipo(cmisFileCF.getType());

        return this.processObject(proveedorAdjuntoSunat);
    }

    @RequestMapping(value = "/licitacionAdjuntoRespuesta/adjuntos",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoLicitacionRespuesta(@RequestParam("file") MultipartFile file) throws Exception {
        logger.debug("Agregrando adjuntos Respuesta Licitacion  [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";
        //String folderId = cmisService.createFolder(newFolder);
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        String folderId = cmisBaseServicecf.createFolder(newFolder).getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);


        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        LicitacionAdjuntoRespuesta licitacionAdjuntoRespuesta = new LicitacionAdjuntoRespuesta();
        licitacionAdjuntoRespuesta.setArchivoId(cmisFileCF.getId());
        licitacionAdjuntoRespuesta.setArchivoNombre(cmisFileCF.getName());
        licitacionAdjuntoRespuesta.setRutaAdjunto(cmisFileCF.getUrl());
        licitacionAdjuntoRespuesta.setArchivoTipo(cmisFileCF.getType());

        return this.processObject(licitacionAdjuntoRespuesta);
    }

    @RequestMapping(value = "/proveedor/adjuntoCuentaBancaria",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoCuentaBancaria(@RequestParam("fileCuenta") MultipartFile file) throws Exception {
        logger.debug("Agregrando adjunto de cuenta bancaria [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";
        //String folderId = cmisService.createFolder(newFolder);
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        String folderId = cmisBaseServicecf.createFolder(newFolder).getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);
        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
        cuentaBancariaDto.setArchivoId(cmisFileCF.getId());
        cuentaBancariaDto.setArchivoNombre(cmisFileCF.getName());
        cuentaBancariaDto.setRutaAdjunto(cmisFileCF.getUrl());
        cuentaBancariaDto.setArchivoTipo(cmisFileCF.getType());

        return this.processObject(cuentaBancariaDto);
    }


 /*   @RequestMapping(value = "/proveedor/adjuntosSunat2",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoSunat2(HttpServletRequest request,
                                         @RequestParam("file") MultipartFile file) {
        UserSession userSession = this.getUserSession();
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }

        Optional<PreRegistroProveedor> preRegistroProveedor = Optional.ofNullable(userSession.getRuc())
                .map(preRegistroProveedorService::getByRuc);
        logger.debug("Se agregara un archivo");
        if (preRegistroProveedor.isPresent()) {
            //Proveedor p = oProveedor.get();
            Proveedor p = new Proveedor();
           String folderId = Optional.ofNullable(p.getCarpetaId())
                    .orElseGet(() -> {
                        logger.debug("Se creara un folder " + preRegistroProveedor.get().getRuc());
                        String fi = cmisService.createFolder(preRegistroProveedor.get().getRuc());
                        //p.setCarpetaId(fi);
                        logger.debug("Se guardara el ID de la carpeta " + fi);
                        //proveedorService.save(p);
                        return fi;
                    });
            /* String folderId=cmisService.createFolder(preRegistroProveedor.get().getRuc());
            logger.debug("Se creara el documento en el repositorio");
            CmisFile cmisFile = cmisService.createDocumento(folderId, file);
            //cmisService.createDocumento()
            //ProveedorAdjuntoSunat proveedorAdjuntoSunat = new
            //ProveedorCatalogo catalogo = new ProveedorCatalogo();
            ProveedorAdjuntoSunat proveedorAdjuntoSunat= new ProveedorAdjuntoSunat();
            proveedorAdjuntoSunat.setArchivoId(cmisFile.getId());
            proveedorAdjuntoSunat.setArchivoNombre(cmisFile.getName());
            proveedorAdjuntoSunat.setArchivoTipo(cmisFile.getType());
            proveedorAdjuntoSunat.setRutaAdjunto(cmisFile.getUrl());
            proveedorAdjuntoSunat.setCarpetaId(folderId);

            ///añadiendo el Id hcp

            //catalogo.setProveedor(p);
            p.setIdHcp(preRegistroProveedor.get().getIdHcp());
            proveedorAdjuntoSunat.setIdProveedor(p);
            logger.debug("Se guardara el catalogo en la base de datos");

            proveedorAdjuntoSunatService.save(proveedorAdjuntoSunat);

            //proveedorCatalogoService.save(catalogo);
            ProveedorCatalogoDTOMapper mapper = new ProveedorCatalogoDTOMapper();
            return this.processObject(mapper.toDto(proveedorAdjuntoSunat));
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }*/


    @RequestMapping(value = "/proveedor/{idProveedor}/catalogos",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListCatalogo(@PathVariable("idProveedor") Integer idProveedor) {
        return this.processList(proveedorCatalogoService.getListCatalogoDtoByIdProveedor(idProveedor));
    }


    @RequestMapping(value = "/proveedor/catalogos/{archivoId}",
            method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteCatalogo(HttpServletRequest request,
                                            @PathVariable("archivoId") String archivoId) {
        logger.debug("Eliminando el archivo con ID " + archivoId);
        UserSession userSession = this.getUserSession();
        if (!Optional.ofNullable(userSession.getRuc()).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        proveedorCatalogoService.deleteCatalogoByIdProveedorCatalogoById(p.getIdProveedor(), archivoId);
        cmisService.deleteFile(archivoId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/proveedor/{folderId}",
            method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListCatalogoByFolderId(@PathVariable("folderId") String folderId) {
        logger.debug("Listar catalogos del proveedor del folderId " + folderId);

        if (!Optional.ofNullable(folderId).filter(s -> !s.trim().isEmpty()).isPresent()) {
            return this.processListEmpty();
        }

        return this.processList(cmisService.getListFileByFolderId(folderId));
    }

    @RequestMapping(value = "/black-list/adjuntos",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> AddAttachBlackList(@RequestParam("file") MultipartFile file) throws Exception {
        logger.debug("Agregrando adjunto al BlackList [" + file.getName() + " , " + file.getSize() + " ]");
        Long current = System.currentTimeMillis();
        String newFolder = "temp-" + current;
        UserSession userSession = this.getUserSession();
        //String folderId = cmisService.createFolder(userSession.getId() + "-" + "blackList");
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);

        String folderId = cmisBaseServicecf.createFolder(userSession.getId() + "-" + "blackList").getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);
        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        return this.processObject(cmisFileCF);
    }


    @RequestMapping(value = "/black-list/adjuntos/{idSolicitud}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListFileByFolderId(@PathVariable("idSolicitud") Integer idSolicitud) {
        UserSession userSession = this.getUserSession();
        String folderId = userSession.getId() + "-blackList";
        logger.debug("Listar adjuntos de blackList del folderId " + folderId);

        if (!Optional.ofNullable(folderId).filter(s -> !s.trim().isEmpty()).isPresent()) {
            return this.processListEmpty();
        }

        return this.processList(cmisService.getListFileByFolderId(folderId));
    }

    /*@RequestMapping(value = "/black-list/adjuntos/{folderId}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListFileByFolderId(@PathVariable("folderId") String folderId) {
        logger.debug("Listar adjuntos de blackList del folderId " + folderId);

        if(!Optional.ofNullable(folderId).filter(s -> !s.trim().isEmpty()).isPresent()){
            return this.processListEmpty();
        }

        return this.processList(cmisService.getListFileByFolderId(folderId));
    }*/


    @RequestMapping(value = "/proveedor/homologacion/{ruc}",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> addAttachHomologacion(HttpServletRequest request, @PathVariable("ruc") String ruc, @RequestParam("file") MultipartFile file) throws Exception {
        logger.debug("Adjuntando archivo a la homologación");
        //UserSession userSession = this.getUserSession(value.getUserFront());
        //UserSession userSession = this.getUserSession(); // Temporal
        if (!Optional.ofNullable(ruc).isPresent()) {
            throw new PortalException("El usuario no esta registrado como proveedor");
        }

        Optional<Proveedor> oProveedor = Optional.ofNullable(ruc)
                .map(proveedorService::getProveedorByRuc);
        if (!oProveedor.isPresent()) {
            logger.error("El proveedor no esta disponible");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Proveedor p = oProveedor.get();
        String folderId = Optional.ofNullable(p.getCarpetaId())
                .orElseGet(() -> {
                    logger.debug("Se creara un folder " + p.getRuc());
                    String fi = null;
                    try {
                        fi = cmisBaseServicecf.createFolder(p.getRuc()).getId();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    p.setCarpetaId(fi);
                    logger.debug("Se guardara el ID de la carpeta " + fi);
                    proveedorService.save(p);
                    return fi;
                });

        logger.debug("Se esta adjuntando el archivo para el proveedor " + p.getRuc());
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFile = cmisBaseServicecf.createDocumento(folderId, file);
        logger.debug("Archivo cargado al repositorio : " + cmisFile);
        return this.processObject(cmisFile);
    }

    @RequestMapping(value = "/cotizacion/adjunto",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntosCotizacion(@RequestParam("file") MultipartFile file) {
        logger.debug("Agregrando adjunto [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";
        String folderId = cmisService.createFolder(newFolder);
        CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        logger.debug("Archivo cargado al repositorio : " + cmisFile);



        CotizacionAdjunto cotizacionAdjunto = new CotizacionAdjunto();
        //cotizacionAdjunto.setCotizacion(cotizacion);
        cotizacionAdjunto.setArchivoId(cmisFile.getId());
        cotizacionAdjunto.setArchivoNombre(cmisFile.getName());
        cotizacionAdjunto.setArchivoTipo(cmisFile.getType());
        cotizacionAdjunto.setRutaAdjunto(cmisFile.getUrl());


        logger.debug("Archivo cargado al repositorio : " + cmisFile);
        return this.processObject(cotizacionAdjunto);
    }







    @RequestMapping(value = "/cotizacion/adjuntos/{archivoId}/{idCotizacion}",
            method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteAdjuntoCotizacion(
            @PathVariable("archivoId") String archivoId,
            @PathVariable("idCotizacion") Integer idCotizacion) {
        logger.debug("Eliminando el archivo con ID " + archivoId);
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(idCotizacion);
        this.cotizacionAdjuntoService.deleteCotizacionAdjuntoByCotizacionArchivoId(cotizacion, archivoId);
        cmisService.deleteFile(archivoId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/deleteAdjuntoCotizacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> deleteAdjuntoCotizacion(
            @RequestBody Map<String, Object> json) {
        Integer idCotizacion = 0;
        String sidCotizacion = "";
        Cotizacion cotizacion = new Cotizacion();
        String archivoId = (String) json.get("archivoId");

        try {
            idCotizacion = (Integer) json.get("idCotizacion");
            cotizacion.setIdCotizacion(idCotizacion);
        } catch (Exception e) {
            sidCotizacion = (String) json.get("idCotizacion");
            sidCotizacion = sidCotizacion.trim();
            idCotizacion = new Integer(sidCotizacion);
            cotizacion.setIdCotizacion(idCotizacion);
        }

        this.cotizacionAdjuntoService.deleteCotizacionAdjuntoByCotizacionArchivoId(cotizacion, archivoId);
        cmisService.deleteFile(archivoId);
        return ResponseEntity.ok().body(archivoId);
    }

    @RequestMapping(value = "/licitacion/adjuntos",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoLicitacion(@RequestParam("file") MultipartFile file) throws Exception {
        logger.debug("Agregrando adjunto a licitacion [" + file.getName() + " , " + file.getSize() + " ]");
        //Long current = System.currentTimeMillis();
        String newFolder = "temp";

        //String folderId = cmisService.createFolder(newFolder);
        //CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        String folderId = cmisBaseServicecf.createFolder(newFolder).getId();
        com.incloud.hcp.service.cmiscf.bean.CmisFile cmisFileCF = cmisBaseServicecf.createDocumento(folderId, file);

        logger.debug("Archivo cargado al repositorio : " + cmisFileCF);
        LicitacionAdjunto licitacionAdjunto = new LicitacionAdjunto();
        licitacionAdjunto.setArchivoId(cmisFileCF.getId());
        licitacionAdjunto.setArchivoNombre(cmisFileCF.getName());
        licitacionAdjunto.setRutaAdjunto(cmisFileCF.getUrl());
        licitacionAdjunto.setArchivoTipo(cmisFileCF.getType());

        return this.processObject(licitacionAdjunto);
    }


    @RequestMapping(value = "/deleteAdjuntoLicitacion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> deleteAdjuntoLicitacion(
            @RequestBody Map<String, Object> json) {
        //Integer idLicitacion = 0;
        //String sidLicitacion = "";
        //Licitacion licitacion = new Licitacion();
        String archivoId = (String) json.get("archivoId");
        /*
        try {
            idLicitacion = (Integer) json.get("idLicitacion");
            licitacion.setIdLicitacion(idLicitacion);
        }
        catch(Exception e) {
            sidLicitacion = (String) json.get("idLicitacion");
            sidLicitacion = sidLicitacion.trim();
            idLicitacion = new Integer(sidLicitacion);
            licitacion.setIdLicitacion(idLicitacion);
        }

        this.licitacionAdjuntoService.deleteLicitacionAdjuntoByLicitacionArchivoId(licitacion, archivoId);*/
        if (archivoId != null && !archivoId.isEmpty()) {
            cmisService.deleteFile(archivoId);
        }

        return ResponseEntity.ok().body(archivoId);
    }

/*
    @RequestMapping(value = "/proveedor/adjuntoOrdenCompra",
            method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addAdjuntoOrdenCompra(HttpServletRequest request,
                                                   @RequestParam("arrayByte") String arrayByte ,@RequestParam("contentType") String contentType,
                                                   @RequestParam("size") long size,@RequestParam("nameFile") String nameFile,@RequestParam("numOC") String ordenCompra) throws IOException {
*/
        //arrayByte , contentType, sizen name
        /*MultipartFile   file = new MultipartFile() {
            @Override
            public String getName() {
                return nameFile;
            }

            @Override
            public String getOriginalFilename() {
                return nameFile;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return size;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return arrayByte;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };

        String folderId = "sW722UwS4Ho8CpOY13gQA2YQlW7ZucZPllwIuds5SUg";
        logger.error("folderId ============== "+folderId);
        CmisFile cmisFile = cmisService.createDocumento(folderId, file);
        logger.error("Archivo guardado correctamente 1:"+cmisFile);*/
      /*  AdjuntoOrdenCompra adjunto = new AdjuntoOrdenCompra();
        adjunto.setIdAdjunto(0);
        adjunto.setArchivoId("");
        adjunto.setArchivoNombre(nameFile);
        adjunto.setArchivoTipo(contentType);
        adjunto.setRutaCatalogo("");
        adjunto.setArchivo(arrayByte);*/
        /*adjunto.setArchivoId("setArchivoId");
        adjunto.setArchivoNombre("ArchivoNombre");
        adjunto.setArchivoTipo("ArchivoTipo");
        adjunto.setRutaCatalogo("RutaCatalogo");*/
  /*      adjunto.setUsuario("");
        adjunto.setNombreUsurio("");
        adjunto.setOrdencompra(ordenCompra);

        logger.debug("Se guardara el catalogo en la base de datos");
        adjuntoOrdenCompraRepository.save(adjunto);
        logger.error("Archivo guardado correctamente 2");
        AdjuntoOrdenCompraDTOMapper mapper = new AdjuntoOrdenCompraDTOMapper();
        return this.processObject(mapper.toDto(adjunto));
    }*/

    @RequestMapping(value = "/obtenerOrdenCompra/{ordenCompra}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> obtenerProveedorByRuc(@PathVariable("ordenCompra") String ordenCompra) throws PortalException {

        AdjuntoOrdenCompra adjuntoOrdenCompra = adjuntoOrdenCompraRepository.getProveedorCatalogoByIdProveedor(ordenCompra);
        //Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);

        return ResponseEntity.ok().body(adjuntoOrdenCompra);
    }


}
