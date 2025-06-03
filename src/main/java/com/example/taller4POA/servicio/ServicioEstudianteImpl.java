package com.example.taller4POA.servicio;

import com.example.taller4POA.excepcion.RegistroNoEncontradoExcep;
import com.example.taller4POA.modelo.Estudiante;
import com.example.taller4POA.repositorio.RepositorioEstudiante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEstudianteImpl implements ServicioEstudiante{

    private final RepositorioEstudiante repositorioEstudiante;

    public ServicioEstudianteImpl(RepositorioEstudiante repositorioEstudiante) {
        this.repositorioEstudiante = repositorioEstudiante;
    }

    @Override
    public List<Estudiante> findAll() {
        return repositorioEstudiante.findAll();
    }

    @Override
    public Estudiante findById(Long id) {
        return repositorioEstudiante.findById(id).orElseThrow(() -> new RegistroNoEncontradoExcep("Estudiante No encontrado", "404", new RuntimeException()));
    }


    @Override
    public Estudiante save(Estudiante estudiante) {
        return repositorioEstudiante.save(estudiante);
    }

    @Override
    public void deleteById(Long id) {
        repositorioEstudiante.deleteById(id);
    }

}
