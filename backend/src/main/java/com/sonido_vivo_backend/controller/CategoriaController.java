package com.sonido_vivo_backend.controller;

import com.sonido_vivo_backend.model.Categoria;
import com.sonido_vivo_backend.repository.CategoriaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository repo;

    public CategoriaController(CategoriaRepository r) {
        repo = r;
    }

    public record CategoriaRequest(
        @NotBlank String nombre,
        String imagen
    ) {}

    @GetMapping
    public List<Categoria> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(
            @Valid @RequestBody CategoriaRequest r) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repo.save(new Categoria(r.nombre(), r.imagen())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequest r) {

        return repo.findById(id)
                .map(c -> {
                    c.setNombre(r.nombre());
                    c.setImagen(r.imagen());

                    return ResponseEntity.ok(repo.save(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}