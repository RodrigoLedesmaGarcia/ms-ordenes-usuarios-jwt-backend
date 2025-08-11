package com.spring.www.usuarios_microservicios.repositories;

import com.spring.www.usuarios_microservicios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByNombreUsuarioIgnoreCase(String nombreUsuario);

    Optional<Usuario> findUsuarioByCorreoIgnoreCase(String correo);

    List<Usuario> findUsuarioByAlcaldiaIgnoreCase(String alcaldia);

    @Query("""
           SELECT u FROM Usuario u 
           WHERE ( :nombre IS NULL OR LOWER(u.nombre) LIKE LOWER (CONCAT('%', :nombre, '%')))
           AND (:apellidoPaterno IS NULL OR LOWER(u.apellidoPaterno) LIKE LOWER (CONCAT('%', :apellidoPaterno, '%')))
           AND (:apellidoMaterno IS NULL OR LOWER (u.apellidoMaterno) LIKE LOWER (CONCAT ('%', :apellidoMaterno, '%')))                                
           """)
    List<Usuario> findUsuarioByFullName
            (@Param("nombre") String nombre, @Param("apellidoPaterno") String apellidoPaterno, @Param("apellidoMaterno") String apellidoMaterno);
}
