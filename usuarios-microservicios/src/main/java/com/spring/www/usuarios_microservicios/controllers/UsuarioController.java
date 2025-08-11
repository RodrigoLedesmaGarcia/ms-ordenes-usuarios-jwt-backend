package com.spring.www.usuarios_microservicios.controllers;

import com.spring.www.usuarios_microservicios.entity.Usuario;
import com.spring.www.usuarios_microservicios.exceptions.HandlerHttpRequestExceptions;
import com.spring.www.usuarios_microservicios.services.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(value = "http://localhost/4200")
public class UsuarioController {

    private final UsuarioServiceImpl service;

    public UsuarioController(UsuarioServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarUusarios (Pageable pageable){
        Page<Usuario> listadoUsuarios = service.lista(pageable);
        if (listadoUsuarios.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(listadoUsuarios.getContent()); // 200 OK
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarUsuarioPorId (@PathVariable Long id){
        Optional<Usuario> usuarioId = service.buscarUsuarioId(id);
        if (usuarioId.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(usuarioId.get()); // 200 ok
        }
    }

    @GetMapping("/correo")
    public ResponseEntity<?> buscarUsuarioCorreo (@RequestParam String correo){
        Optional<Usuario> usuarioCorreo = service.buscarCorreoUsuario(correo);
        if (usuarioCorreo.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(usuarioCorreo.get()); // 200 ok
        }
    }

    @GetMapping("/alcaldia")
    public ResponseEntity<?> buscarUsuarioAlcaldia (@RequestParam String alcaldia){
        List<Usuario> usuarioAlcaldia = service.buscarUsuarioAlcaldia(alcaldia);
        if (usuarioAlcaldia.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(usuarioAlcaldia);
        }
    }

    @GetMapping("/nombre-completo")
    public ResponseEntity<?> buscarUsuarioNombrePila (@RequestParam String nombre, @RequestParam String apellidoPaterno,@RequestParam String apellidoMaterno){
        List<Usuario> nombrePila = service.buscarUsuarioPorNombre(nombre, apellidoPaterno, apellidoMaterno );
        if (nombrePila.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(nombrePila);
        }
    }

    @GetMapping("/nombre-usuario")
    public ResponseEntity<?> buscarUsuarioPorNombreUsuario (@RequestParam String nombreUsuario){
        Optional<Usuario> usuarionombreUsuario = service.buscarPorUsuarioPorUsername(nombreUsuario);
        if (usuarionombreUsuario.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(usuarionombreUsuario.get());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario (@Valid @RequestBody Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            return getMapResponseEntityHttpErrors(result);
        } else {
            service.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario); // 201 created
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return getMapResponseEntityHttpErrors(result);
        } else {
            Optional<Usuario> usuarioId = service.buscarUsuarioId(id);
            if (usuarioId.isEmpty()){
                return HandlerHttpRequestExceptions.notFound("error 404 not found");
            }

            Usuario entity = usuarioId.get();

            entity.setNombre(usuario.getNombre());
            entity.setApellidoPaterno(usuario.getApellidoPaterno());
            entity.setApellidoMaterno(usuario.getApellidoMaterno());
            entity.setAlcaldia(usuario.getAlcaldia());
            entity.setCodigoPostal(usuario.getCodigoPostal());
            entity.setNombreUsuario(usuario.getNombreUsuario());
            entity.setCodigoPostal(usuario.getCodigoPostal());
            entity.setNumeroTelefono(usuario.getNumeroTelefono());
            entity.setAlcaldia(usuario.getAlcaldia());

            Usuario entityEdited = entity;
            try {
                service.crearUsuario(entity);
                return ResponseEntity.ok(entityEdited);
            } catch (Exception e){
                return HandlerHttpRequestExceptions.badRequest("error 400 bad request");
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsario (@PathVariable Long id){
        Optional<Usuario> buscarId = service.buscarUsuarioId(id);
        if (buscarId.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            try {
                service.eliminarUsuario(id);
                return ResponseEntity.ok(Map.of("message","usuario borrado con exito"));
            } catch (Exception e){
                return HandlerHttpRequestExceptions.badRequest("error 400 bad request");
            }
        }
    }






    // metodo para buscar errores al crear o editar una nueva entidad
    private static ResponseEntity<Map<String, Object>> getMapResponseEntityHttpErrors(BindingResult result) {
        Map<String, Object> errors =new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "este campo "+e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
