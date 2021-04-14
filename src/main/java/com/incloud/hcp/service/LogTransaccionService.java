package com.incloud.hcp.service;


import com.incloud.hcp.domain.LogTransaccion;

public interface LogTransaccionService {

    void grabarNuevaLineaLogTransaccion(String tipoTransaccion, String tipoRegistro, String envioTrama, Integer idRegistro, String respuestaCodigo, String respuestaTexto, boolean isJob);
}
