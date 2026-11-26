package com.sonido_vivo_backend.model;
import jakarta.persistence.*;
@Entity @Table(name="usuarios")
public class Usuario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80) private String nombre;
 @Column(nullable=false,unique=true,length=120) private String correo;
 @Column(nullable=false) private String password;
 @Column(nullable=false,length=20) private String rol="CLIENTE";
 public Usuario() {}
 public Usuario(String nombre,String correo,String password,String rol){this.nombre=nombre;this.correo=correo;this.password=password;this.rol=rol;}
 public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
 public String getCorreo(){return correo;} public void setCorreo(String v){correo=v;}
 public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public String getRol(){return rol;} public void setRol(String v){rol=v;}
}
