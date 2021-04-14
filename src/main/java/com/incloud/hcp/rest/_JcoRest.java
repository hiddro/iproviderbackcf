package com.incloud.hcp.rest;


import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.jco.centro.dto.CentroRFCResponseDto;
import com.incloud.hcp.jco.centro.service.JCOCentroServiceNew;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenService;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenServiceNew;
import com.incloud.hcp.jco.consultaProveedor.dto.ConsultaProveedorRFCResponseDto;
import com.incloud.hcp.jco.consultaProveedor.service.JCOConsultaProveedorService;
import com.incloud.hcp.jco.grupoArticulo.dto.GrupoArticuloRFCResponseDto;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloService;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloServiceNew;
import com.incloud.hcp.jco.materiales.dto.MaterialesRFCResponseDto;
import com.incloud.hcp.jco.materiales.service.JCOMaterialesService;
import com.incloud.hcp.jco.materiales.service.JCOMaterialesServiceNew;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.service.JCOProveedorService;
import com.incloud.hcp.jco.servicios.dto.ServiciosRFCResponseDto;
import com.incloud.hcp.jco.servicios.service.JCOServiciosService;
import com.incloud.hcp.jco.servicios.service.JCOServiciosServiceNew;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCResponseDto;
import com.incloud.hcp.jco.peticionOferta.service.JCOPeticionOfertaService;
import com.incloud.hcp.jco.tipoCambio.dto.TipoCambioRFCResponseDto;
import com.incloud.hcp.jco.tipoCambio.service.JCOTipoCambioService;
import com.incloud.hcp.jco.ubigeo.dto.UbigeoRFCResponseDto;
import com.incloud.hcp.jco.ubigeo.service.JCOUbigeoService;
import com.incloud.hcp.jco.unidadMedida.dto.UnidadMedidaRFCResponseDto;
import com.incloud.hcp.jco.unidadMedida.service.JCOUnidadMedidaServiceNew;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/_jcoRest")
public class _JcoRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOPeticionOfertaService jcoPeticionOfertaService;

    @Autowired
    private JCOGrupoArticuloServiceNew jcoGrupoArticuloServiceNew;

    @Autowired
    private JCOGrupoArticuloService jcoGrupoArticuloService;

    @Autowired
    private JCOUbigeoService jcoUbigeoService;

    @Autowired
    private JCOServiciosServiceNew jcoServiciosServiceNew;

    @Autowired
    private JCOConsultaProveedorService jcoConsultaProveedorService;

    @Autowired
    private JCOMaterialesServiceNew jcoMaterialesServiceNew;

    @Autowired
    private JCOMaterialesService jcoMaterialesService;

    @Autowired
    private JCOServiciosService jcoServiciosService;

    @Autowired
    private JCOTipoCambioService jcoTipoCambioService;

    @Autowired
    private JCOCentroServiceNew jcoCentroServiceNew;

    @Autowired
    private JCOUnidadMedidaServiceNew jcoUnidadMedidaServiceNew;

    @Autowired
    private JCOCentroAlmacenService jcoCentroAlmacenService;

    @Autowired
    private JCOCentroAlmacenServiceNew jcoCentroAlmacenServiceNew;

    @Autowired
    private JCOProveedorService jcoProveedorService;

    /* Peticion de Oferta */
    @ApiOperation(value = "Busca Peticion de Oferta en base al Numero de Solicitud o Numero de Licitacion SAP", produces = "application/json")
    @GetMapping(value = "/getSolpedByCodigo/{solped}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PeticionOfertaRFCResponseDto> getSolpedByCodigo(@PathVariable String solped) throws URISyntaxException {
        log.debug("Find by id getPeticionOfertaByCodigo : {}", solped);
        try {
            return Optional.ofNullable(this.jcoPeticionOfertaService.getPeticionOfertaResponseByCodigo(solped, false))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    /* Grupo Articulo */
    @ApiOperation(value = "Lista Grupo Articulos by Codigo", produces = "application/json")
    @GetMapping(value = "/getGrupoArticuloByCodigo/{codigo}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GrupoArticuloRFCResponseDto> getGrupoByCodigo(@PathVariable String codigo) throws URISyntaxException {
        log.debug("Find by id getSolpedByCodigo : {}", codigo);
        try {
            return Optional.ofNullable(this.jcoGrupoArticuloServiceNew.getGrupoArticulo(codigo))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Grupo Articulos nuevos o actualizados", produces = "application/json")
    @PostMapping(value = "/actualizarGrupoArticulo/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <List<RubroBien>> actualizarGrupoArticulo() throws URISyntaxException {
        log.debug("Lista Grupo Articulos : {}");
        try {
            return Optional.ofNullable(this.jcoGrupoArticuloService.actualizarGrupoArticulo())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    /* Tasa de Cambio */
    @ApiOperation(value = "Graba Tasa Cambio del RFC de acuerdo a la Fecha Actual", produces = "application/json")
    @GetMapping(value = "/actualizarTipoCambioFechaActual/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <TipoCambioRFCResponseDto> actualizarTipoCambioFechaActual() throws URISyntaxException {
        log.debug("Lista actualizarTipoCambioFechaActual : {}");
        Date fechaActual = DateUtils.obtenerFechaActual();
        String sFecha = DateUtils.convertDateToString("yyyyMMdd",fechaActual);
        try {
            return Optional.ofNullable(this.jcoTipoCambioService.actualizarTipoCambio(sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    /* Materiales */
    @ApiOperation(value = "Lista Materiales del RFC", produces = "application/json")
    @GetMapping(value = "/getListMaterialesByRFC/{fechaInicio}/{fechaFin}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <MaterialesRFCResponseDto> getListaMaterialesByRFC(
            @PathVariable String fechaInicio,
            @PathVariable String fechaFin
    ) throws URISyntaxException {
        try {
            return Optional.ofNullable(this.jcoMaterialesServiceNew.getListMaterialesRFC(fechaInicio,fechaFin))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista Materiales del RFC", produces = "application/json")
    @GetMapping(value = "/getListMaterialesByRFCFechaActual/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <MaterialesRFCResponseDto> getListMaterialesByRFCFechaActual(
    ) throws URISyntaxException {
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd",fecha);
            return Optional.ofNullable(this.jcoMaterialesServiceNew.getListMaterialesRFC(sFecha,sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Materiales nuevos o actualizados", produces = "application/json")
    @PostMapping(value = "/actualizarMaterialFechaActual/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <MaterialesRFCResponseDto> actualizarMaterialFechaActual() throws URISyntaxException {
        log.debug("Lista actualizarMaterialFechaActual : {}");
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd",fecha);
            return Optional.ofNullable(this.jcoMaterialesService.actualizarMaterialesRFC(sFecha, sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Materiales nuevos o actualizados (Todos)", produces = "application/json")
    @PostMapping(value = "/actualizarMaterialTodos/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <MaterialesRFCResponseDto> actualizarMaterialTodos() throws URISyntaxException {
        log.debug("Lista actualizarMaterialTodos : {}");
        try {

            String sFecha = "";
            return Optional.ofNullable(this.jcoMaterialesService.actualizarMaterialesRFC(sFecha, sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Materiales nuevos o actualizados (Por Fecha)", produces = "application/json")
    @PostMapping(value = "/actualizarMaterialxFecha/{fechaInicio}/{fechaFin}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <MaterialesRFCResponseDto> actualizarMaterialxFecha(
            @PathVariable String fechaInicio,
            @PathVariable String fechaFin
    ) throws URISyntaxException {
        log.debug("Lista actualizarMaterialTodos : {}");
        try {

            String sFecha = "";
            return Optional.ofNullable(this.jcoMaterialesService.actualizarMaterialesRFC(fechaInicio, fechaFin))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



    /* Servicios */
    @ApiOperation(value = "Lista Servicios del RFC By Rango Fechas", produces = "application/json")
    @GetMapping(value = "/getListaServiciosbyRfc/{fechaInicio}/{fechaFin}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ServiciosRFCResponseDto> getListaServiciosbyRFC(
            @PathVariable String fechaInicio,
            @PathVariable String fechaFin) throws URISyntaxException {
        log.debug("Lista Grupo Servicios : {}");
        try {
            return Optional.ofNullable(this.jcoServiciosServiceNew.getListServicios(fechaInicio,fechaFin))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista Servicios del RFC fechaActual", produces = "application/json")
    @GetMapping(value = "/getListServiciosByRFCFechaActual/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ServiciosRFCResponseDto> getListServiciosByFechaActual(
    ) throws URISyntaxException {
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd",fecha);
            return Optional.ofNullable(this.jcoServiciosServiceNew.getListServicios(sFecha,sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Servicios nuevos o actualizados", produces = "application/json")
    @PostMapping(value = "/actualizarServicioFechaActual/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ServiciosRFCResponseDto> actualizarServicioFechaActual() throws URISyntaxException {
        log.debug("Lista actualizarServicioFechaActual : {}");
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd",fecha);
            return Optional.ofNullable(this.jcoServiciosService.actualizarMaterialesRFC(sFecha, sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @ApiOperation(value = "Lista y Guarda Servicios nuevos o actualizados (Todos)", produces = "application/json")
    @PostMapping(value = "/actualizarServicioTodos/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ServiciosRFCResponseDto> actualizarServicioTodos() throws URISyntaxException {
        log.debug("Lista actualizarServicioTodos : {}");
        try {

            String sFecha = "";
            return Optional.ofNullable(this.jcoServiciosService.actualizarMaterialesRFC(sFecha, sFecha))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista y Guarda Servicios nuevos o actualizados (Desde fechas indicadas)", produces = "application/json")
    @PostMapping(value = "/actualizarServicioxFecha/{fechaInicio}/{fechaFin}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ServiciosRFCResponseDto> actualizarServicioxFecha(
            @PathVariable String fechaInicio,
            @PathVariable String fechaFin
    ) throws URISyntaxException {
        log.debug("Lista actualizarServicioxFecha : {}");
        try {

            return Optional.ofNullable(this.jcoServiciosService.actualizarMaterialesRFC(fechaInicio, fechaFin))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    /* Centro Almacen */
    @ApiOperation(value = "Actualiza Centro Almacen x Centro", produces = "application/json")
    @PostMapping(value = "/actualizaCentroAlmacenxCentro/{centro}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <CentroAlmacenRFCResponseDto> actualizaCentroAlmacen(@PathVariable String centro) throws URISyntaxException {
        log.debug("Lista Centro Almacen : {}");
        try {
            return Optional.ofNullable(this.jcoCentroAlmacenService.actualizaCentroAlmacen(centro))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Actualiza Centro Almacen(Todos)", produces = "application/json")
    @PostMapping(value = "/actualizaCentroAlmacenTodos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <CentroAlmacenRFCResponseDto> actualizaCentroAlmacenTodos() throws URISyntaxException {
        log.debug("Lista Centro Almacen : {}");
        try {
            return Optional.ofNullable(this.jcoCentroAlmacenService.actualizaCentroAlmacen(""))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista Centro Almacen(Todos)", produces = "application/json")
    @PostMapping(value = "/getListaCentroAlmacenTodos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <CentroAlmacenRFCResponseDto> getListaCentroAlmacenTodos() throws URISyntaxException {
        log.debug("Lista Centro Almacen : {}");
        try {
            return Optional.ofNullable(this.jcoCentroAlmacenServiceNew.getListaCentroAlmacen(""))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



    @ApiOperation(value = "Actualiza Centros RFC", produces = "application/json")
    @GetMapping(value = "/actualizarCentrosRFC", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <CentroRFCResponseDto> actualizarCentrosRFC() throws URISyntaxException {
        log.debug("Lista Centro  : {}");

        //String sFecha = DateUtils.convertDateToString("yyyyMMdd",fecha);
//        String sociedad="SFER";
        try {
            return Optional.ofNullable(this.jcoCentroServiceNew.actualizarCentro(""))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Lista Unidad Medida RFC", produces = "application/json")
    @GetMapping(value = "/actualizarUnidadMedidaRFC", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <UnidadMedidaRFCResponseDto> actualizarUnidadMedidaRFC() throws URISyntaxException {
        log.debug("Lista Unidad Medida  : {}");
        try {
            return Optional.ofNullable(this.jcoUnidadMedidaServiceNew.actualizarUnidadMedida())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Crear Proveedor RFC", produces = "application/json")
    @PostMapping(value = "/crearProveedor/{idProveedor}/{usuarioSap}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ProveedorRFCResponseDto> crearProveedorSap(@PathVariable Integer idProveedor,@PathVariable String usuarioSap) throws URISyntaxException {
        log.debug("Lista Centro Almacen : {}");
        try {
            return Optional.ofNullable(this.jcoProveedorService.grabarProveedor(idProveedor,usuarioSap))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @ApiOperation(value = "Crear UbigeoSAP", produces = "application/json")
    @PostMapping(value = "/crearUbigeo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <UbigeoRFCResponseDto> crearUbigeo() throws URISyntaxException {
        log.debug("Lista Ubigeos : {}");
        try {
            return Optional.ofNullable(this.jcoUbigeoService.listarandActualizarUbigeoRFC())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

//    @ApiOperation(value = "Lista Proveedores RFC", produces = "application/json")
//    @GetMapping(value = "/listaProveedoresByRFC/{nroAcreedor}/{fechaInicio}/{fechaFin}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity <ConsultaProveedorRFCResponseDto> listarProveedoresRFC(@PathVariable String nroAcreedor,
//                                                                                 @PathVariable String fechaInicio,
//                                                                                 @PathVariable String fechaFin) throws URISyntaxException {
//        log.debug("Lista Unidad Medida  : {}");
//        try {
//            return Optional.ofNullable(this.jcoConsultaProveedorService.listaProveedorByRFC(nroAcreedor,fechaInicio,fechaFin,""))
//                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        } catch (Exception e) {
//            String error = Utils.obtieneMensajeErrorException(e);
//            throw new RuntimeException(error);
//        }
//    }


    @ApiOperation(value = "Lista Proveedores RFC", produces = "application/json")
    @GetMapping(value = "/listaProveedoresByRFC", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <ConsultaProveedorRFCResponseDto> listarProveedoresRFC(@RequestParam(value = "nroAcreedor", required = false) String nroAcreedor,
                                                                                 @RequestParam(value = "email", required = false) String email,
                                                                                 @RequestParam(value = "tipoPersona", required = false) String tipoPersona,
                                                                                 @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
                                                                                 @RequestParam(value = "fechaFin", required = false) String fechaFin) {
        try {
            return Optional.ofNullable(this.jcoConsultaProveedorService.listaProveedorByRFC(nroAcreedor,fechaInicio,fechaFin,(email != null ? email : ""),tipoPersona))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

}
