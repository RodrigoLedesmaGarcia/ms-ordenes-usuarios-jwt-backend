package com.spring.www.ordenes_microservicio.services;

import com.spring.www.ordenes_microservicio.entity.Orden;
import com.spring.www.ordenes_microservicio.repositories.OrdenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository repository;

    public OrdenServiceImpl(OrdenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Orden> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Orden> buscarOrdenPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Orden crearOrden(Orden orden) {
        return repository.save(orden);
    }


    @Override
    public void eliminarOrden(Long id) {
         repository.deleteById(id);
    }
}
