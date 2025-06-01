package com.example.taller4POA.repositorio;

import com.example.taller4POA.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioEstudiante extends JpaRepository<Estudiante, Long> {
}
