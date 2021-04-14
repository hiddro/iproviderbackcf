package com.incloud.hcp.repository;

import com.incloud.hcp.domain.TipoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Joel on 02/09/2017.
 */
public interface TipoProveedorRepository extends JpaRepository<TipoProveedor, Integer> {

    TipoProveedor findByCodigoSap(String codigoSap);
}
