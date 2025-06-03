package com.example.taller4POA.controlador;

import com.example.taller4POA.modelo.Nota;
import com.example.taller4POA.servicio.ServicioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class ControladorNota {
    @Autowired
    private ServicioNota servicioNota;

    public ControladorNota(ServicioNota servicioNota) {
        this.servicioNota = servicioNota;
    }

    @GetMapping
    public List<Nota> getAll() {
        return servicioNota.findAll();
    }

    @GetMapping("/estudiante/{id}")
    public Nota getById(@PathVariable Long id) {
        return servicioNota.findById(id);
    }

    @PostMapping("/crea")
    public Nota create(@RequestBody Nota nota) {
        return servicioNota.save(nota);
    }

    @PutMapping("/{id}")
    public Nota update(@PathVariable Long id, @Valid @RequestBody Nota nota) {
        nota.setId(id);
        return  servicioNota.save(nota);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicioNota.deleteById(id);
    }

    @GetMapping("/promedio/{id}")
    public ResponseEntity<Double> promedio(@PathVariable Long id){
        return ResponseEntity.ok(servicioNota.calcularPromedio(id));
    }

}
