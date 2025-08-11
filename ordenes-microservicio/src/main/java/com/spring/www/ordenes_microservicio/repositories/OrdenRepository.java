package com.spring.www.ordenes_microservicio.repositories;

import com.spring.www.ordenes_microservicio.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
