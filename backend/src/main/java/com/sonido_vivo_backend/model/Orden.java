package com.sonido_vivo_backend.model;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.LocalDateTime;
@Entity @Table(name="ordenes")
public class Orden {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Usuario usuario;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal total;
 @Column(nullable=false,length=30) private String estado="PENDIENTE";
 @Column(nullable=false) private LocalDateTime fecha=LocalDateTime.now();
 @Column(length=1000) private String detalle;
 public Orden(){}
 public Long getId(){return id;} public Usuario getUsuario(){return usuario;} public void setUsuario(Usuario v){usuario=v;}
 public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
 public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
 public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime v){fecha=v;}
 public String getDetalle(){return detalle;} public void setDetalle(String v){detalle=v;}
}
