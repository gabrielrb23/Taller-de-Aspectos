package com.example.taller4POA.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String materia;
    @Column (nullable = false)
    private String observacion;
    @Column (nullable = false)
    private Double valor;
    @Column (nullable = false)
    @Min(0)
    @Max(100)
    private Double porcentaje;
    @ManyToOne (optional = false)
    private Estudiante estudiante;

    public Nota(Long id, String materia, String observacion, Double valor, Double porcentaje, Estudiante estudiante) {
        this.id = id;
        this.materia = materia;
        this.observacion = observacion;
        this.valor = valor;
        this.porcentaje = porcentaje;
        this.estudiante = estudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
