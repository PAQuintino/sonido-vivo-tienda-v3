package com.sonido_vivo_backend.controller;
import com.sonido_vivo_backend.model.*; import com.sonido_vivo_backend.repository.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api/productos")
public class ProductoController {
 private final ProductoRepository repo; private final CategoriaRepository categorias;
 public ProductoController(ProductoRepository r,CategoriaRepository c){repo=r;categorias=c;}
 public record ProductoRequest(@NotBlank String nombre,String descripcion,@NotNull @DecimalMin("0.0") BigDecimal precio,BigDecimal precioOferta,@NotNull @Min(0) Integer stock,String imagen,boolean oferta,Long categoriaId){}
 private Producto aplicar(Producto p,ProductoRequest r){p.setNombre(r.nombre());p.setDescripcion(r.descripcion());p.setPrecio(r.precio());p.setPrecioOferta(r.precioOferta());p.setStock(r.stock());p.setImagen(r.imagen());p.setOferta(r.oferta());p.setCategoria(r.categoriaId()==null?null:categorias.findById(r.categoriaId()).orElseThrow());return p;}
 @GetMapping public List<Producto> listar(){return repo.findAll();}
 @GetMapping("/{id}") public ResponseEntity<Producto> obtener(@PathVariable Long id){return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
 @PostMapping public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(aplicar(new Producto(),r)));}
 @PutMapping("/{id}") public ResponseEntity<Producto> actualizar(@PathVariable Long id,@Valid @RequestBody ProductoRequest r){return repo.findById(id).map(p->ResponseEntity.ok(repo.save(aplicar(p,r)))).orElse(ResponseEntity.notFound().build());}
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id){if(!repo.existsById(id))return ResponseEntity.notFound().build();repo.deleteById(id);return ResponseEntity.noContent().build();}
}
