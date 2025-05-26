package com.example.aspectj.servicio;

import com.example.aspectj.modelo.Producto;

import java.util.List;

public interface ProductoServicio {
    Producto findById(Long id);
    List<Producto> findAll();
    void save(Producto product);
    void delete(Long id);
    void updatePrice(Long id, double newPrice);
}