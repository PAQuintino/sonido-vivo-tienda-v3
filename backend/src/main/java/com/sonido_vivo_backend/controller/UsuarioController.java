package com.sonido_vivo_backend.controller;

import com.sonido_vivo_backend.model.Usuario;
import com.sonido_vivo_backend.repository.UsuarioRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository r, PasswordEncoder e) {
        repo = r;
        encoder = e;
    }

    public record UsuarioRequest(
        @NotBlank String nombre,
        @Email @NotBlank String correo,
        String password,
        @Pattern(regexp = "ADMIN|CLIENTE") String rol
    ) {}

    private Map<String, Object> vista(Usuario u) {
        return Map.of(
                "id", u.getId(),
                "nombre", u.getNombre(),
                "correo", u.getCorreo(),
                "rol", u.getRol()
        );
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return repo.findAll()
                .stream()
                .map(this::vista)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {

        return repo.findById(id)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(vista(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody UsuarioRequest r) {

        if (r.password() == null || r.password().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "mensaje",
                            "La contraseña debe tener al menos 8 caracteres"
                    ));
        }

        Usuario u = new Usuario(
                r.nombre(),
                r.correo().toLowerCase(),
                encoder.encode(r.password()),
                r.rol() == null ? "CLIENTE" : r.rol()
        );

        return ResponseEntity
                .status(201)
                .body(vista(repo.save(u)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest r) {

        return repo.findById(id)
                .<ResponseEntity<?>>map(u -> {

                    u.setNombre(r.nombre());
                    u.setCorreo(r.correo().toLowerCase());

                    if (r.password() != null && !r.password().isBlank()) {
                        u.setPassword(encoder.encode(r.password()));
                    }

                    if (r.rol() != null) {
                        u.setRol(r.rol());
                    }

                    return ResponseEntity.ok(vista(repo.save(u)));
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