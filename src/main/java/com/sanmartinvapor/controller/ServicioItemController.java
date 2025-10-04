package com.sanmartinvapor.controller;

import com.sanmartinvapor.model.ServicioItem;
import com.sanmartinvapor.repository.ServicioItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/servicios")
public class ServicioItemController {

    private final ServicioItemRepository repo;

    public ServicioItemController(ServicioItemRepository repo) {
        this.repo = repo;
    }

    // Crear un servicio
    @PostMapping
    public ServicioItem crear(@RequestBody ServicioItem servicio) {
        return repo.save(servicio);
    }

    // Listar todos los servicios
    @GetMapping
    public List<ServicioItem> listar() {
        return repo.findAll();
    }

    // Editar un servicio
    @PutMapping("/{id}")
    public ServicioItem editar(@PathVariable Long id, @RequestBody ServicioItem datos) {
        return repo.findById(id).map(s -> {
            s.setFecha(datos.getFecha());
            s.setDescripcion(datos.getDescripcion());
            s.setHoraInicio(datos.getHoraInicio());
            s.setDetalle(datos.getDetalle());
            return repo.save(s);
        }).orElseThrow(() -> new RuntimeException("No se encontró servicio con id " + id));
    }

    // Eliminar un servicio
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Servicio con id " + id + " eliminado correctamente.";
        } else {
            return "No se encontró servicio con id " + id;
        }
    }
}
