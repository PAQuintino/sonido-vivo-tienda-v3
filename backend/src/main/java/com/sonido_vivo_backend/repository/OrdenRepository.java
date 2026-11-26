package com.sonido_vivo_backend.repository;
import com.sonido_vivo_backend.model.Orden; import org.springframework.data.jpa.repository.JpaRepository; 
public interface OrdenRepository extends JpaRepository<Orden,Long> {}
