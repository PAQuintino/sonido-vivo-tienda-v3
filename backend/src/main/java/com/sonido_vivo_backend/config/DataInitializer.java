package com.sonido_vivo_backend.config;
import com.sonido_vivo_backend.model.*; import com.sonido_vivo_backend.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner inicializar(UsuarioRepository usuarios,CategoriaRepository categorias,ProductoRepository productos,PasswordEncoder encoder){
  return args->{
   if(!usuarios.existsByCorreo("max@duoc.cl"))usuarios.save(new Usuario("Max Herrera","max@duoc.cl",encoder.encode("admin123"),"ADMIN"));
   if(!usuarios.existsByCorreo("javi@gmail.com"))usuarios.save(new Usuario("Javiera Sanches","javi@gmail.com",encoder.encode("cliente123"),"CLIENTE"));
   if(categorias.count()==0){categorias.save(new Categoria("Instrumentos de Cuerda","/img/guitarra.jpg"));categorias.save(new Categoria("Percusión","/img/bateria.jpg"));categorias.save(new Categoria("Amplificadores","/img/amplificador-15w.jpg"));categorias.save(new Categoria("Accesorios","/img/pedal.jpg"));}
  };
 }
}
