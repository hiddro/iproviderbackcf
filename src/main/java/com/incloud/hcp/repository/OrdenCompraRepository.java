package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap = 'L'")
    List<OrdenCompra> getAllActive();

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.id = ?1 and oc.estadoSap = 'L'")
    OrdenCompra getOrdenCompraById(Integer idOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.proveedorRuc = ?3")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetweenAndProveedorRuc(Date fechaInicio, Date fechaFin, String proveedorRuc);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenCompra oc where oc.id= ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenCompra> findByIdAndIsActive(Integer idOrdenCompra);


    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1'")
    Optional<OrdenCompra> getOrdenCompraActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenCompra> getOrdenCompraLiberadaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L' and oc.idEstadoOrdenCompra not in (4,5)")
    Optional<OrdenCompra> getOrdenCompraLiberadaActivaValidaByNumero(String numeroOrdenCompra);


}