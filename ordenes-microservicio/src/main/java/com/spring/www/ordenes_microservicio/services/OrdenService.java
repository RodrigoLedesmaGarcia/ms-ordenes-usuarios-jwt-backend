package com.spring.www.ordenes_microservicio.services;

import com.spring.www.ordenes_microservicio.entity.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrdenService {


    Page<Orden> listar (Pageable pageable);

    Optional<Orden> buscarOrdenPorId (Long id);

    Orden crearOrden (Orden orden);

    void eliminarOrden(Long id);
}
