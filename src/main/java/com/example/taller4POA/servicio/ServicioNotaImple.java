package com.example.taller4POA.servicio;

import com.example.taller4POA.excepcion.RegistroNoEncontradoExcep;
import com.example.taller4POA.modelo.Nota;
import com.example.taller4POA.repositorio.RepositorioNota;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioNotaImple implements ServicioNota {

    private final RepositorioNota repositorioNota;

    public ServicioNotaImple(RepositorioNota repositorioNota) {
        this.repositorioNota = repositorioNota;
    }

    @Override
    public List<Nota> findAll() {
        return repositorioNota.findAll();
    }

    @Override
    public Nota findById(Long id) {
        return repositorioNota.findById(id).orElseThrow(() -> new RegistroNoEncontradoExcep("Nota No encontrada", "404", new RuntimeException()));
    }

    @Override
    public Nota save(Nota nota) {
        return repositorioNota.save(nota);
    }

    @Override
    public void deleteById(Long id) {
        repositorioNota.deleteById(id);
    }

    @Override
    public double calcularPromedio(Long estudianteId) {
        Optional<Nota> notas = repositorioNota.findById(estudianteId);
        return notas.stream()
                .mapToDouble(n -> (n.getValor() * n.getPorcentaje()) /100.0)
                .sum();
    }
}
