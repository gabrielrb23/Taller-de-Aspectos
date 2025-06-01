package com.example.taller4POA.controlador;

import com.example.taller4POA.modelo.Nota;
import com.example.taller4POA.servicio.ServicioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;


import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notas")
public class ControladorNota {
    @Autowired
    private ServicioNota servicioNota;

    public ControladorNota(ServicioNota servicioNota) {
        this.servicioNota = servicioNota;
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping
    public List<Nota> getAll() {
        return servicioNota.findAll();
    }

    @PreAuthorize("hasRole('PROFESOR') or hasRole('ESTUDIANTE')")
    @GetMapping("/estudiante/{id}")
    public Nota getById(@PathVariable Long id, Authentication auth) {
        Nota nota = servicioNota.findById(id);

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ESTUDIANTE"))) {
            String correoLogueado = auth.getName(); // correo del estudiante logueado
            if (!nota.getEstudiante().getCorreo().equals(correoLogueado)) {
                throw new AccessDeniedException("No puedes ver notas de otro estudiante");
            }
        }

        return nota;
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/crea")
    public Nota create(@RequestBody Nota nota) {
        return servicioNota.save(nota);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PutMapping("/{id}")
    public Nota update(@PathVariable Long id, @Valid @RequestBody Nota nota) {
        nota.setId(id);
        return  servicioNota.save(nota);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        servicioNota.deleteById(id);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @GetMapping("/promedio/{id}")
    public ResponseEntity<Double> promedio(@PathVariable Long id){
        return ResponseEntity.ok(servicioNota.calcularPromedio(id));
    }

}
