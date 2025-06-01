package com.example.taller4POA.servicio;

import com.example.taller4POA.modelo.Estudiante;
import com.example.taller4POA.repositorio.RepositorioEstudiante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ServicioEstudiante {
    List<Estudiante> findAll();
    Estudiante findById(Long id);
    Estudiante save(Estudiante estudiante);
    void deleteById(Long id);

}
