package com.example.demo.controller;

import com.example.demo.models.Pais;
import com.example.demo.repos.PaisRepository;
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
@RequestMapping("/pais")
@RequiredArgsConstructor
@Tag(name = "Pais", description = "El controlador de países")
public class PaisController {

    private final PaisRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Pais>> findAll(){

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @Operation(summary = "Obtiene un país en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el país",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pais.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el país por el ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pais> findOne(
            @Parameter(description = "ID del país a buscar")
            @PathVariable Long id
    ) {

        return ResponseEntity
                .of(repository.findById(id));

    }

    @PostMapping("/")
    public ResponseEntity<Pais> create(@RequestBody Pais nuevo) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(nuevo));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Pais> edit(
            @RequestBody Pais p,
            @PathVariable Long id) {

        return ResponseEntity.of(
                repository.findById(id).map(c -> {
                    c.setNombre(p.getNombre());
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
