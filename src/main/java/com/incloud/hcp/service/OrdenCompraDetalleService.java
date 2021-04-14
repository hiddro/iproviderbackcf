package com.incloud.hcp.service;

import com.incloud.hcp.domain.OrdenCompraDetalle;

import java.util.List;

public interface OrdenCompraDetalleService {

   List<OrdenCompraDetalle> getOrdenCompraDetalleListByIdOcLiberada(Integer idOrdenCompra);

   List<OrdenCompraDetalle> getOrdenCompraDetalleListByIdOc(Integer idOrdenCompra);
}
