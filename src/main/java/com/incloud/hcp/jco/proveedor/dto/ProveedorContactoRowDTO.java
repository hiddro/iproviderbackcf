package com.incloud.hcp.jco.proveedor.dto;public class ProveedorContactoRowDTO {    private String tratamiento;    private String nombre;    private String apellido;    private String funcion;    private String telefono;    private String telefonoMov;    private String email;    public ProveedorContactoRowDTO() {    }    public ProveedorContactoRowDTO(String tratamiento, String nombre, String apellido, String funcion, String telefono, String telefonoMov, String email) {        this.tratamiento = tratamiento;        this.nombre = nombre;        this.apellido = apellido;        this.funcion = funcion;        this.telefono = telefono;        this.telefonoMov = telefonoMov;        this.email = email;    }    public String getTratamiento() {        return tratamiento;    }    public void setTratamiento(String tratamiento) {        this.tratamiento = tratamiento;    }    public String getNombre() {        return nombre;    }    public void setNombre(String nombre) {        this.nombre = nombre;    }    public String getApellido() {        return apellido;    }    public void setApellido(String apellido) {        this.apellido = apellido;    }    public String getFuncion() {        return funcion;    }    public void setFuncion(String funcion) {        this.funcion = funcion;    }    public String getTelefono() {        return telefono;    }    public void setTelefono(String telefono) {        this.telefono = telefono;    }    public String getTelefonoMov() {        return telefonoMov;    }    public void setTelefonoMov(String telefonoMov) {        this.telefonoMov = telefonoMov;    }    public String getEmail() {        return email;    }    public void setEmail(String email) {        this.email = email;    }}