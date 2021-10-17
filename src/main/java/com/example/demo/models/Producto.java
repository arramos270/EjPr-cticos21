package com.example.demo.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class Producto implements Serializable {

    @GeneratedValue @Id
    private Long id;

    private String nombre;
    private double precio;
    private String imagen;
    private String descripcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Double.compare(producto.precio, precio) == 0 && id.equals(producto.id) && nombre.equals(producto.nombre) && Objects.equals(imagen, producto.imagen) && Objects.equals(descripcion, producto.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, imagen, descripcion);
    }
}
