package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Access(AccessType.FIELD)
@Table(name="TIPO_DOCUMENTO_ACEPTACION")
public class TipoDocumentoAceptacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_DOCUMENTO_ACEPTACION", unique=true, nullable=false)
	private Integer id;

	@Column(name="DESCRIPCION", nullable=false, length=40)
	private String descripcion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return "TipoDocumentoAceptacion{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}