package com.incloud.hcp.bean;

/**
 * Created by Administrador on 28/08/2017.
 */
public class Linea {
    private int id;
    private String descripcion;

    public Linea() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Linea{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
