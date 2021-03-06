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

import com.incloud.hcp.domain.LicitacionProveedorPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicitacionProveedorPreguntaRepository extends JpaRepository<LicitacionProveedorPregunta, Integer> {

    @Modifying
    @Query("DELETE FROM LicitacionProveedorPregunta d WHERE d.idLicitacion= ?1 and d.idProveedor=?2")
    void deleteDetailByLicitacionProveedor(Integer idLicitacion, Integer idProveedor);


    public List<LicitacionProveedorPregunta> findByIdLicitacionOrderByIdProveedor(Integer id);
}