package com.spring.www.ordenes_microservicio.feign;

import com.spring.www.ordenes_microservicio.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-microservicios", url = "http://localhost:8050/api/usuarios")
public interface UsuariosFeign {


    @GetMapping("/id/{id}")
    public Usuario buscarUsuarioPorId (@PathVariable Long id);

}
