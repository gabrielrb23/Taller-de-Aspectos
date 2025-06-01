package com.example.taller4POA.repositorio;

import com.example.taller4POA.modelo.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioNota extends JpaRepository<Nota, Long> {
}
