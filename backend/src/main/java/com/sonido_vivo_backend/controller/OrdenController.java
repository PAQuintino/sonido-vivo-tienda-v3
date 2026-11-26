package com.sonido_vivo_backend.controller;

import com.sonido_vivo_backend.model.Orden;
import com.sonido_vivo_backend.repository.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenRepository ordenes;
    private final UsuarioRepository usuarios;

    public OrdenController(OrdenRepository o, UsuarioRepository u) {
        ordenes = o;
        usuarios = u;
    }

    public record OrdenRequest(
        @NotNull @DecimalMin("0.0") BigDecimal total,
        @NotBlank String detalle
    ) {}

    @GetMapping
    public List<Orden> listar(Authentication a) {

        boolean admin = a.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));

        return admin
                ? ordenes.findAll()
                : ordenes.findAll().stream()
                        .filter(o -> o.getUsuario().getCorreo().equals(a.getName()))
                        .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtener(
            @PathVariable Long id,
            Authentication a) {

        return ordenes.findById(id)
                .filter(o ->
                        o.getUsuario().getCorreo().equals(a.getName())
                        || a.getAuthorities().stream()
                                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"))
                )
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Orden> crear(
            @Valid @RequestBody OrdenRequest r,
            Authentication a) {

        Orden o = new Orden();

        o.setUsuario(
                usuarios.findByCorreo(a.getName()).orElseThrow()
        );

        o.setTotal(r.total());
        o.setDetalle(r.detalle());
        o.setEstado("PAGADA");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ordenes.save(o));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Orden> estado(
            @PathVariable Long id,
            @RequestParam String estado) {

        return ordenes.findById(id)
                .map(o -> {
                    o.setEstado(estado);
                    return ResponseEntity.ok(ordenes.save(o));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}