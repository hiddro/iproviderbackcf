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

import com.incloud.hcp.domain.ProveedorPreguntaInformacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProveedorPreguntaInformacionRepository extends JpaRepository<ProveedorPreguntaInformacion, Integer> {

    @Modifying
    @Query("DELETE FROM ProveedorPreguntaInformacion p WHERE p.idProveedor.idProveedor= ?1")
    void deletePreguntaInformacionByIdProveedor(Integer idProveedor);


    @Query("SELECT p FROM ProveedorPreguntaInformacion p WHERE p.idProveedor.idProveedor= ?1 AND p.idPreguntaInformacion.id =?2")
    ProveedorPreguntaInformacion findByProveedorPregunta(Integer idProveedor, Integer idPregunta);

}