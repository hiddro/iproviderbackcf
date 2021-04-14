package com.incloud.hcp.service.cmiscf.bean;

public class CmisFolder {

    private String id;
    private String mensaje;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "CmisFolder{" +
                "id='" + id + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
