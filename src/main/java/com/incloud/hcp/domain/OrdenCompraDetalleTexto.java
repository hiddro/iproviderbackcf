package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name="ORDEN_COMPRA_DETALLE_TEXTO")
public class OrdenCompraDetalleTexto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_COMPRA_DETALLE_TEXTO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_COMPRA_DETALLE_TEXTO_ID_SEQ", sequenceName = "ORDEN_COMPRA_DETALLE_TEXTO_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_COMPRA_DETALLE_TEXTO", unique=true, nullable=false)
	private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenCompraDetalle.class)
    @JoinColumn(name="ID_ORDEN_COMPRA_DETALLE", referencedColumnName = "ID_ORDEN_COMPRA_DETALLE", insertable = false, updatable = false)
    private OrdenCompraDetalle ordenCompraDetalle;

	@Column(name="ID_ORDEN_COMPRA_DETALLE", nullable=false)
	private Integer idOrdenCompraDetalle;

	@Column(name="NUMERO_ORDEN_COMPRA", nullable=false, length=10)
	private String numeroOrdenCompra;

    @Column(name="POSICION", length=5)
    private String posicion;

    @Column(name="LINEA", length=140)
    private String linea;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenCompraDetalle getOrdenCompraDetalle() {
        return ordenCompraDetalle;
    }

    public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
        this.ordenCompraDetalle = ordenCompraDetalle;
    }

    public Integer getIdOrdenCompraDetalle() {
        return idOrdenCompraDetalle;
    }

    public void setIdOrdenCompraDetalle(Integer idOrdenCompraDetalle) {
        this.idOrdenCompraDetalle = idOrdenCompraDetalle;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }


    @Override
    public String toString() {
        return "OrdenCompraDetalleTexto{" +
                "id=" + id +
//                ", ordenCompraDetalle=" + ordenCompraDetalle +
                ", idOrdenCompraDetalle=" + idOrdenCompraDetalle +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}