package com.spring.www.usuarios_microservicios.services;

import com.spring.www.usuarios_microservicios.entity.Usuario;
import com.spring.www.usuarios_microservicios.repositories.IUsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private  final IUsuarioRepository repository;

    public UsuarioServiceImpl(IUsuarioRepository repository) {
        this.repository = repository;
    }


    @Override
    public Page<Usuario> lista(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Usuario> buscarUsuarioId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarCorreoUsuario(String correo) {
        return repository.findUsuarioByCorreoIgnoreCase(correo);
    }

    @Override
    public List<Usuario> buscarUsuarioAlcaldia(String alcaldia) {
        return repository.findUsuarioByAlcaldiaIgnoreCase(alcaldia);
    }


    @Override
    public Optional<Usuario> buscarPorUsuarioPorUsername(String nombreUsuario) {
        return repository.findUsuarioByNombreUsuarioIgnoreCase(nombreUsuario);
    }

    @Override
    public List<Usuario> buscarUsuarioPorNombre(String nombre, String apellidoPaterno, String apellidoMaterno) {
        return repository.findUsuarioByFullName(nombre, apellidoPaterno, apellidoMaterno);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
         repository.deleteById(id);
    }
}
