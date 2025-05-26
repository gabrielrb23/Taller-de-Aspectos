package com.example.aspectj.servicio;

import com.example.aspectj.modelo.Producto;
import com.example.aspectj.repositorio.ProductoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoServicioImpl implements ProductoServicio {

    private final ProductoRepository productoRepository;

    public ProductoServicioImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el producto con id: " + id));
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public void save(Producto product) {
        productoRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void updatePrice(Long id, double newPrice) {
        Producto product = findById(id);
        product.setPrecio(newPrice);
        productoRepository.save(product);
    }
}