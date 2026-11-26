package com.sonido_vivo_backend.security;
import com.sonido_vivo_backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {
 private final UsuarioRepository usuarios;
 public CustomUserDetailsService(UsuarioRepository usuarios){this.usuarios=usuarios;}
 @Override public UserDetails loadUserByUsername(String correo){
  var u=usuarios.findByCorreo(correo).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
  return User.withUsername(u.getCorreo()).password(u.getPassword()).roles(u.getRol()).build();
 }
}
