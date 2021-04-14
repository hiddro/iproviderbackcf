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

import com.incloud.hcp.domain.LicitacionProveedorRenegociacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicitacionProveedorRenegociacionRepository extends JpaRepository<LicitacionProveedorRenegociacion, Integer> {

    @Query("select u from LicitacionProveedorRenegociacion u " +
            "where u.idLicitacion.licitacion.idLicitacion = ?1 and " +
            "u.idLicitacion.proveedor.idProveedor = ?2 " +
            "order by u.fechaCierreRecepcion")
    List<LicitacionProveedorRenegociacion> findByLicitacionOrderByFechaCierreRecepcion(
            Integer idlicitacion,
            Integer idProveedor);


    @Query("select u from LicitacionProveedorRenegociacion u where u.idLicitacion.licitacion.idLicitacion = ?1 " +
            "order by u.idLicitacion.proveedor.razonSocial, u.fechaCierreRecepcion ")
    List<LicitacionProveedorRenegociacion> findByLicitacionOrderByProveedorFechaCierreRecepcion(
            Integer idlicitacion);

    @Query("select u from LicitacionProveedorRenegociacion u where u.idLicitacion.licitacion.idLicitacion = ?1 ")
    List<LicitacionProveedorRenegociacion> findByLicitacion(Integer idlicitacion);

    @Query("select count(u) from LicitacionProveedorRenegociacion u " +
            "where u.idLicitacion.licitacion.idLicitacion = ?1 and " +
            "u.idLicitacion.proveedor.idProveedor = ?2 " )
    Integer countByLicitacionAndProveedor(
            Integer idlicitacion,
            Integer idProveedor);

}