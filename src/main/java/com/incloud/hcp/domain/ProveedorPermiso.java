/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/domain/Entity.java.e.vm
 */
package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "proveedor_permiso")
public class ProveedorPermiso extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ProveedorPermiso.class.getName());

    // Raw attributes
    @Column(name = "id_proveedor_permiso", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_proveedor_permiso")
    @Id
    @SequenceGenerator(name = "seq_proveedor_permiso", sequenceName = "seq_proveedor_permiso", allocationSize = 1)
    private Integer id;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "codigo_tipo_permiso", nullable = false, length = 30)
    private String codigoTipoPermiso;

    @NotEmpty
    @Size(max = 300)
    @Column(name = "entidad_emisora", nullable = false, length = 300)
    private String entidadEmisora;

    @Size(max = 1)
    @Column(name = "vigencia", length = 1)
    private String vigencia;

    // Many to one
    @NotNull
    @JoinColumn(name = "id_proveedor", nullable = false)
    @ManyToOne
    private Proveedor idProveedor;

    @Transient
    private String vigenciaText;

    @Transient
    private Integer idBuscarProveedor;

    @Override
    public String entityClassName() {
        return ProveedorPermiso.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public ProveedorPermiso id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [codigoTipoPermiso] ------------------------


    public String getCodigoTipoPermiso() {
        return codigoTipoPermiso;
    }

    public void setCodigoTipoPermiso(String codigoTipoPermiso) {
        this.codigoTipoPermiso = codigoTipoPermiso;
    }

    public ProveedorPermiso codigoTipoPermiso(String codigoTipoPermiso) {
        setCodigoTipoPermiso(codigoTipoPermiso);
        return this;
    }
    // -- [entidadEmisora] ------------------------

    public String getEntidadEmisora() {
        return entidadEmisora;
    }

    public void setEntidadEmisora(String entidadEmisora) {
        this.entidadEmisora = entidadEmisora;
    }

    public ProveedorPermiso entidadEmisora(String entidadEmisora) {
        setEntidadEmisora(entidadEmisora);
        return this;
    }
    // -- [vigencia] ------------------------

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public ProveedorPermiso vigencia(String vigencia) {
        setVigencia(vigencia);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: ProveedorPermiso.idProveedor ==> Proveedor.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    /**
     * Set the {@link #idProveedor} without adding this ProveedorPermiso instance on the passed {@link #idProveedor}
     */
    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ProveedorPermiso idProveedor(Proveedor idProveedor) {
        setIdProveedor(idProveedor);
        return this;
    }

    /**
     * Apply the default values.
     */
    public ProveedorPermiso withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof ProveedorPermiso && hashCode() == other.hashCode());
    }


    public String getVigenciaText() {
        return vigenciaText;
    }

    public void setVigenciaText(String vigenciaText) {
        this.vigenciaText = vigenciaText;
    }

    public Integer getIdBuscarProveedor() {
        return idBuscarProveedor;
    }

    public void setIdBuscarProveedor(Integer idBuscarProveedor) {
        this.idBuscarProveedor = idBuscarProveedor;
    }

    @Override
    public String toString() {
        return "ProveedorPermiso{" +
                "id=" + id +
                ", codigoTipoPermiso='" + codigoTipoPermiso + '\'' +
                ", entidadEmisora='" + entidadEmisora + '\'' +
                ", vigencia='" + vigencia + '\'' +
                ", idProveedor=" + idProveedor +
                ", vigenciaText='" + vigenciaText + '\'' +
                ", idBuscarProveedor=" + idBuscarProveedor +
                '}';
    }
}