package com.example.taller4POA.servicio;

import com.example.taller4POA.modelo.Nota;

import java.util.List;

public interface ServicioNota {
    List<Nota> findAll();
    Nota findById(Long id);
    Nota save(Nota nota);
    void deleteById(Long id);
    double calcularPromedio(Long estudianteId);
}
