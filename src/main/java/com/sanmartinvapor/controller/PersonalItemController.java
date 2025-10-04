package com.sanmartinvapor.controller;

import com.sanmartinvapor.model.PersonalItem;
import com.sanmartinvapor.repository.PersonalItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/personal")
public class PersonalItemController {

    private final PersonalItemRepository repo;

    public PersonalItemController(PersonalItemRepository repo) {
        this.repo = repo;
    }

    // Crear una caja
    @PostMapping
    public PersonalItem crear(@RequestBody PersonalItem item) {
        System.out.println(">>> LLEGO AL POST: " + item.getNombre());
        return repo.save(item);
    }

    // Listar todas las cajas
    @GetMapping
    public List<PersonalItem> listar() {
        return repo.findAll();
    }
    @PutMapping("/{id}")
    public PersonalItem editar(@PathVariable Long id, @RequestBody PersonalItem datos) {
        return repo.findById(id).map(item -> {
            item.setFecha(datos.getFecha());
            item.setNombre(datos.getNombre());
            item.setApellido(datos.getApellido());
            item.setLegajo(datos.getLegajo());
            item.setDisponibleDesde(datos.getDisponibleDesde());
            item.setNotas(datos.getNotas());
            return repo.save(item);
        }).orElseThrow(() -> new RuntimeException("No se encontró el personal con id " + id));
    }
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Personal con id " + id + " eliminado correctamente.";
        } else {
            return "No se encontró personal con id " + id;
        }
    }

}
