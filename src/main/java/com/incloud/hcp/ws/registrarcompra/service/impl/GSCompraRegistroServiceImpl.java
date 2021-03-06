package com.incloud.hcp.ws.registrarcompra.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.ws.OkHttpClientSync;
import com.incloud.hcp.ws.registrarcompra.bean.CompraRegistroBodyResponse;
import com.incloud.hcp.ws.registrarcompra.bean.CompraRegistroResponse;
import com.incloud.hcp.ws.registrarcompra.dto.RegistroCompraGSDto;
import com.incloud.hcp.ws.registrarcompra.service.GSCompraRegistroService;
import com.incloud.hcp.ws.registrarcompra.service.builder.CompraRegistroGSBuilder;
import com.incloud.hcp.ws.util.GSConstant;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class GSCompraRegistroServiceImpl implements GSCompraRegistroService {

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CompraRegistroResponse registro(RegistroCompraGSDto dto) {

        StringBuilder logJson = new StringBuilder();
        HashMap<String, String> data = new HashMap<>();
        Gson g = new Gson();
        long l = System.currentTimeMillis();
        String respuestaJson = "";
        OkHttpClientSync mOkHttpClientSync = new OkHttpClientSync("COMPRA_REGISTRO");

        String url = GSConstant.URL_API_GS_TEST;
        String key = GSConstant.KEY_API_GS_TEST;

        try {
            // 1. Instanciamos el response del ws
            CompraRegistroResponse responseWS = new CompraRegistroResponse();
            // 2. Obtenemos los datos de entrada del ws en formato json y se crea un RequestBody
            String jsonInput = CompraRegistroGSBuilder.newBuilder(dto).build();
            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonInput);
            // 3. Generamos la petici??n (Request) POST mandando como par??metro URL/path, body, key
            Request request = mOkHttpClientSync.getRequestPostAuthorization(String.format("%s/%s",url, GSConstant.PATH_API_GS_COMPRA_REGISTRAR), body, key);
            logger.info("Enviando trama registro compra -> {}", jsonInput);
            //System.out.println(jsonInput);
            Call call = mOkHttpClientSync.getmOkHttpClient().newCall(request);
            // 4. Respuesta del servicio
            Response response = call.execute();

            // 5. Aplicar la l??gica necesaria para capturar la respuesta del ws
            if(response.isSuccessful() && !response.message().contains("ya existe")){
                // Respuesta OK: 200
                respuestaJson = response.body().string();
                logger.info("Respuesta WS -> {}", respuestaJson);
                Map<String,Object> result = new ObjectMapper().readValue(respuestaJson, HashMap.class);
                // Solo si en la trama de respuesta retorna status diferente, se instancia el objeto del bodyResponse ya que del ws retorna un(boolean)
                if(!String.valueOf(result.get("status")).equals("200")) {
                    result.put("body", new CompraRegistroBodyResponse());
                    Gson gson = new GsonBuilder().create();
                    respuestaJson = gson.toJson(result);
                }
                responseWS = g.fromJson(respuestaJson, CompraRegistroResponse.class);
            } else {
                // Respuesta ERROR: 500, 404, 403, entre otros.
                //respuestaJson = String.format("%s : %s", String.valueOf(response.code()), response.message());
                //responseWS = g.fromJson(respuestaJson, ProveedorRegistroResponse.class);
                responseWS.setStatus(String.valueOf(response.code()));
                responseWS.setMessage(response.message());
                responseWS.setBody(new CompraRegistroBodyResponse());
            }

            data.put("time", (System.currentTimeMillis() - l) / 1000 + " seconds");
            data.put("response", respuestaJson);
            Long time = (System.currentTimeMillis() - l) / 1000;
            return responseWS;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex.getCause());
            // Instanciamos el response del ws e ingresamos los datos de exception
            CompraRegistroResponse responseWS = new CompraRegistroResponse();
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            responseWS.setMessageException("Error al registrar la compra: " + ex.getMessage());
            responseWS.setMessageCause(ex.toString());
            responseWS.setCause(ex.getCause());
            Long time = (System.currentTimeMillis() - l) / 1000;
            return responseWS;
        }
    }
}
