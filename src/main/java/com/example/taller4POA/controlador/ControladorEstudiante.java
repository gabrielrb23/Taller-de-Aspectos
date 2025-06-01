package com.example.taller4POA.controlador;


import com.example.taller4POA.modelo.Estudiante;
import com.example.taller4POA.modelo.Nota;
import com.example.taller4POA.servicio.ServicioEstudiante;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class ControladorEstudiante {
    @Autowired
    private ServicioEstudiante servicioEstudiante;

    public ControladorEstudiante(ServicioEstudiante servicioEstudiante) {
        this.servicioEstudiante = servicioEstudiante;
    }

    @GetMapping
    public List<Estudiante> getAll() {
        return servicioEstudiante.findAll();
    }

    @GetMapping("/{id}")
    public Estudiante getById(@PathVariable Long id) {
        return servicioEstudiante.findById(id);
    }
    @PostMapping("/crea")
    public Estudiante create(@RequestBody Estudiante estudiante) {
        return servicioEstudiante.save(estudiante);
    }

    @PutMapping("/{id}")
    public Estudiante update(@PathVariable Long id, @Valid @RequestBody Estudiante estudiante) {
        estudiante.setId(id);
        return  servicioEstudiante.save(estudiante);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicioEstudiante.deleteById(id);
    }
}
