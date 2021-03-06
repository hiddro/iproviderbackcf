package com.incloud.hcp.rest._framework;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.bean.UserSessionFront;
import com.incloud.hcp.config.BindingErrorsResponse;
import com.incloud.hcp.config.SystemLoggedUser;
import com.incloud.hcp.exception.PortalException;
import com.sap.security.um.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrador on 09/10/2017.
 */
public abstract class AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String CONSTANTE_SEPARADOR = " / ";

    @Autowired
    private SystemLoggedUser systemLoggedUser;


    public User getUserSCP(HttpServletRequest request) {
        return this.systemLoggedUser.getUserSCP(request);
    }

    public UserSession getUserSession() throws PortalException {
        return this.systemLoggedUser.getUserSession();

    }

    // Temporal
    public UserSession getUserSessionProveedor() throws PortalException {
        return this.systemLoggedUser.getUserSessionProveedor();
    }

    public UserSession getUserSession(UserSessionFront userFront) throws PortalException {
        return this.systemLoggedUser.getUserSession(userFront);
    }

    protected ResponseEntity<?> processList(List lista) {
        return Optional.ofNullable(lista)
                .map(l -> {
                    Map response = new HashMap<>();
                    response.put("total", l.size());
                    response.put("data", l);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    protected ResponseEntity<Map> processObject(Object object) {
        return Optional.ofNullable(object)
                .map(oj -> {
                    Map response = new HashMap<>();
                    response.put("data", oj);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    protected ResponseEntity<?> processListEmpty() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected String devuelveErrorHeaders(BindingResult bindingResult, BindingErrorsResponse errors) {
        errors.addAllErrors(bindingResult);
        String errorDevuelve = "";
        int tam = bindingResult.getFieldErrors().size();
        int contador = 0;
        for (FieldError beanError: bindingResult.getFieldErrors()) {
            contador++;
            errorDevuelve += beanError.getDefaultMessage() ;
            if (contador < tam)
                errorDevuelve += CONSTANTE_SEPARADOR;
        }
        return errorDevuelve;
    }

}
