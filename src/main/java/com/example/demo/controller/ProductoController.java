package com.example.demo.controller;

import com.example.demo.models.Producto;
import com.example.demo.repos.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
@Tag(name = "Producto", description = "El controlador de producto")
public class ProductoController {

    private final ProductoRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Producto>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @Operation(summary = "Obtiene un producto en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el producto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el producto por el ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> findOne(
            @Parameter(description = "ID del producto a buscar")
            @PathVariable Long id
    ) {

        return ResponseEntity
                .of(repository.findById(id));

    }

    @PostMapping("/")
    public ResponseEntity<Producto> create(@RequestBody Producto nuevo) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(nuevo));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> edit(
            @RequestBody Producto p,
            @PathVariable Long id) {

        return ResponseEntity.of(
                repository.findById(id).map(c -> {
                    c.setNombre(p.getNombre());
                    c.setPrecio(p.getPrecio());
                    c.setImagen(p.getImagen());
                    c.setDescripcion(p.getDescripcion());
                    repository.save(c);
                    return c;
                })
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}