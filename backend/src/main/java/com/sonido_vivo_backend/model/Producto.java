package com.sonido_vivo_backend.model;
import jakarta.persistence.*; import java.math.BigDecimal;
@Entity @Table(name="productos")
public class Producto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=150) private String nombre;
 @Column(length=1000) private String descripcion;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal precio=BigDecimal.ZERO;
 @Column(precision=12,scale=2) private BigDecimal precioOferta;
 @Column(nullable=false) private Integer stock=0;
 private String imagen; private boolean oferta=false;
 @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="categoria_id") private Categoria categoria;
 public Producto(){}
 public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
 public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
 public BigDecimal getPrecio(){return precio;} public void setPrecio(BigDecimal v){precio=v;}
 public BigDecimal getPrecioOferta(){return precioOferta;} public void setPrecioOferta(BigDecimal v){precioOferta=v;}
 public Integer getStock(){return stock;} public void setStock(Integer v){stock=v;}
 public String getImagen(){return imagen;} public void setImagen(String v){imagen=v;}
 public boolean isOferta(){return oferta;} public void setOferta(boolean v){oferta=v;}
 public Categoria getCategoria(){return categoria;} public void setCategoria(Categoria v){categoria=v;}
}
