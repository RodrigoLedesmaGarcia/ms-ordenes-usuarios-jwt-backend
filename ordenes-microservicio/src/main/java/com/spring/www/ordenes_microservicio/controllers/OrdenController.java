package com.spring.www.ordenes_microservicio.controllers;

import com.spring.www.ordenes_microservicio.entity.Orden;
import com.spring.www.ordenes_microservicio.exceptions.HandlerHttpRequestExceptions;
import com.spring.www.ordenes_microservicio.services.OrdenServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(value = "http://localhost/4200")
public class OrdenController {

    private final OrdenServiceImpl service;

    public OrdenController(OrdenServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listarOrdenes (Pageable pageable){
        Page<Orden> listaOrdenes = service.listar(pageable);
        if (listaOrdenes.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(listaOrdenes.getContent());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOrdenPorId (@PathVariable Long id){
        Optional<Orden> ordenId = service.buscarOrdenPorId(id);
        if (ordenId.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            return ResponseEntity.ok(ordenId.get());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearOrden (@Valid @RequestBody Orden orden, BindingResult result){
        if (result.hasErrors()){
            return getMapResponseEntityErrors(result);
        } else {
            service.crearOrden(orden);
            return ResponseEntity.status(HttpStatus.CREATED).body(orden);
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarOrden(@Valid @RequestBody Orden orden, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return getMapResponseEntityErrors(result);
        } else {
            Optional<Orden> ordenId = service.buscarOrdenPorId(id);
            if (ordenId.isEmpty()) {
                return HandlerHttpRequestExceptions.notFound("error 404 not found");
            }

            Orden entity = ordenId.get();

            entity.setDescripcion(orden.getDescripcion());
            entity.setPrecio(orden.getPrecio());

            Orden edited = entity;
            try {
                service.crearOrden(edited);
                return ResponseEntity.ok(edited);
            } catch (Exception e) {
                return HandlerHttpRequestExceptions.badRequest("error 400 bad request");
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOrden (@PathVariable Long id){
        Optional<Orden> ordenId = service.buscarOrdenPorId(id);
        if (ordenId.isEmpty()){
            return HandlerHttpRequestExceptions.notFound("error 404 not found");
        } else {
            try {
                service.eliminarOrden(id);
                return ResponseEntity.ok(Map.of("message","200 OK : orden eliminada con exito"));
            } catch (Exception e){
                return HandlerHttpRequestExceptions.badRequest("error 400 bad request : no se pudo eliminar la orden id: + "+id);
            }
        }
    }

    private static ResponseEntity<Map<String, Object>> getMapResponseEntityErrors(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "este campo "+e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
