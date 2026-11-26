package com.sonido_vivo_backend.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiration=expiration;
 }
 public String generar(String correo,String rol){long now=System.currentTimeMillis();return Jwts.builder().subject(correo).claim("rol",rol).issuedAt(new Date(now)).expiration(new Date(now+expiration)).signWith(key).compact();}
 public String extraerCorreo(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean validar(String token){try{Jwts.parser().verifyWith(key).build().parseSignedClaims(token);return true;}catch(JwtException|IllegalArgumentException e){return false;}}
}
