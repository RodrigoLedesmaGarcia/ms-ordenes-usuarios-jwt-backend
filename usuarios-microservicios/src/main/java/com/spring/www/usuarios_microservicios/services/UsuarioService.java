package com.spring.www.usuarios_microservicios.services;

import com.spring.www.usuarios_microservicios.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Page<Usuario> lista (Pageable pageable);

    Optional<Usuario> buscarUsuarioId (Long id);

    Optional<Usuario> buscarCorreoUsuario (String correo);

    List<Usuario> buscarUsuarioAlcaldia (String alcaldia);

    Optional<Usuario> buscarPorUsuarioPorUsername (String nombreUsuario);

    List<Usuario> buscarUsuarioPorNombre (String nombre, String apellidoPaterno, String apellidoMaterno);

    Usuario crearUsuario (Usuario usuario);

    void eliminarUsuario (Long id);
}
