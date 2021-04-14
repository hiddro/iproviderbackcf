package com.incloud.hcp.rest;

import com.incloud.hcp.jco.comprobantePago.dto.ComprobantePagoDto;
import com.incloud.hcp.service.ComprobantePagoService;
import com.incloud.hcp.service.extractor.ComprobantePagoGsService;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.ws.docspendientes.dto.ComprobantePagoGsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ComprobantePago")
public class ComprobantePagoRest {

    private ComprobantePagoService comprobantePagoService;

    @Autowired
    public ComprobantePagoRest(ComprobantePagoService comprobantePagoService) {
        this.comprobantePagoService = comprobantePagoService;
    }
    @Autowired
    ComprobantePagoGsService comprobantePagoGsService;

   /* @GetMapping(value = "/getComprobantePagoList")
    public ResponseEntity<List<ComprobantePagoDto>> getComprobantePagoList(
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc,
            @RequestParam(value = "numeroComprobante", required = false) String numeroComprobante){
        try{
            List<ComprobantePagoDto> comprobantePagoDtoList = comprobantePagoService.getComprobantePagoListPorFechasAndRuc(fechaInicio, fechaFin, ruc, numeroComprobante);

            if(comprobantePagoDtoList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(comprobantePagoDtoList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }*/
   @GetMapping(value = "/getComprobantePagoList")
   public ResponseEntity<List<ComprobantePagoGsDto>> getComprobantePagoList(
           @RequestParam(value = "fechaInicio", required = false)  String fechaInicio,
           @RequestParam(value = "fechaFin", required = false) String fechaFin,
           @RequestParam(value = "ruc", required = false) String ruc){
       try{
           List<ComprobantePagoGsDto> comprobantePagoDtoList = comprobantePagoGsService.getComprobantePagoList(fechaInicio,fechaFin,ruc);

           if(comprobantePagoDtoList.isEmpty()){
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(comprobantePagoDtoList, HttpStatus.OK);
       } catch (Exception e) {
           String error = Utils.obtieneMensajeErrorException(e);
           throw new RuntimeException(error);
       }
   }
}
