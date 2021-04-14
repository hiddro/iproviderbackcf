/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/repository/EntityRepository.java.e.vm
 */
package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionDetalle;
import com.incloud.hcp.domain.LicitacionProveedorDetalleEvaluacion;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicitacionProveedorDetalleEvaluacionRepository extends JpaRepository<LicitacionProveedorDetalleEvaluacion, Integer> {

    void deleteByIdLicitacion(Licitacion licitacion);

    @Modifying
    @Query("DELETE FROM LicitacionProveedorDetalleEvaluacion p WHERE p.idLicitacion.idLicitacion = ?1")
    void deleteByLicitacion02(Integer idLicitacion);

    void deleteByIdLicitacionAndIdProveedor(Licitacion licitacion, Proveedor proveedor);

    @Query("select u from LicitacionProveedorDetalleEvaluacion u " +
            "where u.idLicitacion.idLicitacion = ?1 " +
            "order by " +
            "u.idLicitacionDetalle.descripcion, u.idProveedor.razonSocial")
    List<LicitacionProveedorDetalleEvaluacion> findByIdLicitacionOrdenado(Integer idLicitacion);


    LicitacionProveedorDetalleEvaluacion getByIdLicitacionAndIdProveedorAndIdLicitacionDetalle(
            Licitacion licitacion,
            Proveedor proveedor,
            LicitacionDetalle licitacionDetalle
    );

    List<LicitacionProveedorDetalleEvaluacion> findByIdLicitacionAndIdProveedor(
            Licitacion licitacion,
            Proveedor proveedor
    );


    @Query("select u from LicitacionProveedorDetalleEvaluacion u " +
            "where u.idLicitacion.idLicitacion = ?1 " +
            "  and u.idLicitacionDetalle.idLicitacionDetalle = ?2 " +
            "  and u.porcentajeTotalObtenido = (select max(x.porcentajeTotalObtenido) from LicitacionProveedorDetalleEvaluacion x " +
            "                                   where x.idLicitacion.idLicitacion = u.idLicitacion.idLicitacion " +
            "                                     and x.idLicitacionDetalle.idLicitacionDetalle = u.idLicitacionDetalle.idLicitacionDetalle)" )
    LicitacionProveedorDetalleEvaluacion getByMaximoPorcentajeLicitacionDetalle(
            Integer idLicitacion,
            Integer idLicitacionDetalle
    );

    @Query("select u from LicitacionProveedorDetalleEvaluacion u " +
            "where u.idLicitacion.idLicitacion = ?1 " +
            "and u.idLicitacionDetalle.idLicitacionDetalle = ?2 " +
            "and u.porcentajeTotalObtenido = (select max(x.porcentajeTotalObtenido) from LicitacionProveedorDetalleEvaluacion x " +
            "                                   where x.idLicitacion.idLicitacion = u.idLicitacion.idLicitacion " +
            "                                     and x.idLicitacionDetalle.idLicitacionDetalle = u.idLicitacionDetalle.idLicitacionDetalle) " +
            "order by u.idProveedor.razonSocial asc" )
    List<LicitacionProveedorDetalleEvaluacion> getListaByMaximoPorcentajeLicitacionDetalleOrdenadaByRazonSocial(
            Integer idLicitacion,
            Integer idLicitacionDetalle
    );
}