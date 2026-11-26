package com.sonido_vivo_backend.controller;
import com.sonido_vivo_backend.model.Usuario; import com.sonido_vivo_backend.repository.UsuarioRepository; import com.sonido_vivo_backend.security.JwtService;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.authentication.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthenticationManager auth; private final UsuarioRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(AuthenticationManager a,UsuarioRepository u,PasswordEncoder e,JwtService j){auth=a;users=u;encoder=e;jwt=j;}
 public record LoginRequest(@Email @NotBlank String correo,@NotBlank String password){}
 public record RegistroRequest(@NotBlank String nombre,@Email @NotBlank String correo,@Size(min=8) String password){}
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody LoginRequest r){
  auth.authenticate(new UsernamePasswordAuthenticationToken(r.correo(),r.password()));Usuario u=users.findByCorreo(r.correo()).orElseThrow();
  return Map.of("token",jwt.generar(u.getCorreo(),u.getRol()),"usuario",Map.of("id",u.getId(),"nombre",u.getNombre(),"correo",u.getCorreo(),"rol",u.getRol()));
 }
 @PostMapping("/registro") public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequest r){
  if(users.existsByCorreo(r.correo()))return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje","El correo ya está registrado"));
  Usuario u=users.save(new Usuario(r.nombre(),r.correo().toLowerCase(),encoder.encode(r.password()),"CLIENTE"));
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",u.getId(),"nombre",u.getNombre(),"correo",u.getCorreo(),"rol",u.getRol()));
 }
}
