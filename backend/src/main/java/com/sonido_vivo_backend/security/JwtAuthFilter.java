package com.sonido_vivo_backend.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final CustomUserDetailsService users;
 public JwtAuthFilter(JwtService jwt,CustomUserDetailsService users){this.jwt=jwt;this.users=users;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){String token=h.substring(7);if(jwt.validar(token)){
   String correo=jwt.extraerCorreo(token); if(SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails u=users.loadUserByUsername(correo);var auth=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities());auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));SecurityContextHolder.getContext().setAuthentication(auth);
   }
  }}
  chain.doFilter(req,res);
 }
}
