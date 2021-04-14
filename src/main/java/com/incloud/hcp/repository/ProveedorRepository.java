package com.incloud.hcp.repository;

import com.incloud.hcp.bean.ProveedorCustom;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.ProveedorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 29/08/2017.
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT p FROM Proveedor p WHERE p.ruc = ?1")
    Proveedor getProveedorByRuc(String ruc);

    @Query("SELECT p FROM Proveedor p WHERE p.idHcp = ?1")
    Proveedor getProveedorByIdHcp(String idHcp);

    @Query("SELECT p FROM Proveedor p WHERE p.ruc = ?1 AND p.password = ?2")
    Proveedor getProveedorByRucAndPassword(String ruc, String password);

    @Query("SELECT p FROM Proveedor p WHERE p.acreedorCodigoSap = ?1")
    Proveedor getProveedorByAcreedorCodigoSap(String acreedorSap);

    Proveedor getProveedorByIdProveedor(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Proveedor t SET t.acreedorCodigoSap = ?1 WHERE t.idProveedor=?2")
    public void updateAcreedorCodigoSAP(String acreedorCodigoSap, Integer idProveedor);

    @Transactional
    @Modifying
    @Query("UPDATE Proveedor t SET t.idHcp = ?1 WHERE t.ruc=?2")
    public void updateIdHCP(String idHCP, String ruc);

    @Query("SELECT p.idHcp FROM Proveedor p WHERE p.ruc = ?1")
    Optional<String> getUsuarioIdpProveedorByRuc(String ruc);

    @Query("SELECT new com.incloud.hcp.bean.ProveedorCustom(p.idProveedor, p.idHcp) FROM Proveedor p WHERE p.ruc = ?1")
    Optional<ProveedorCustom> getProveedorIdHcpByRuc(String ruc);

    public List<Proveedor> findByFechaCreacionBetweenOrderByRuc(Date fechaCreacionIni, Date fechaCreacionFin);

    public List<Proveedor> findByFechaCreacionGreaterThanEqualOrderByRuc(Date fechaCreacionIni);

    public List<Proveedor> findAllByOrderByRuc();

    Optional<Proveedor> findByRuc(String ruc);

    @Query("SELECT p FROM Proveedor p WHERE p.ruc IN (:rucs)")
    List<Proveedor> findByRucs(@Param("rucs") List<String> rucs);
}
