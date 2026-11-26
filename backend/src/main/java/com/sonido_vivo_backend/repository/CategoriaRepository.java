package com.sonido_vivo_backend.repository;
import com.sonido_vivo_backend.model.Categoria; import org.springframework.data.jpa.repository.JpaRepository; 
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {}
