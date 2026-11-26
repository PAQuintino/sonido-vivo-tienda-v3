package com.sonido_vivo_backend.model;
import jakarta.persistence.*;
@Entity @Table(name="categorias")
public class Categoria {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=100) private String nombre;
 private String imagen;
 public Categoria(){} public Categoria(String n,String i){nombre=n;imagen=i;}
 public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
 public String getImagen(){return imagen;} public void setImagen(String v){imagen=v;}
}
