package com.sonido_vivo_backend.repository;
import com.sonido_vivo_backend.model.Producto; import org.springframework.data.jpa.repository.JpaRepository; 
public interface ProductoRepository extends JpaRepository<Producto,Long> {}
