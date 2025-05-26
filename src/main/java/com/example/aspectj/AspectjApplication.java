package com.example.aspectj;

import com.example.aspectj.modelo.Producto;
import com.example.aspectj.servicio.ProductoServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AspectjApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AspectjApplication.class, args);
		ProductoServicio productoServicio = context.getBean(ProductoServicio.class);

		System.out.println("\n--- Buscando todos los productos ---");
		productoServicio.findAll().forEach(System.out::println);

		System.out.println("\n--- Buscando un producto por ID ---");
		Producto product = productoServicio.findById(1L);
		System.out.println(product);

		System.out.println("\n--- Actualizando precio de un producto ---");
		productoServicio.updatePrice(1L, 1299.99);

		System.out.println("\n--- Provocando un error (ID no existente) ---");
		try {
			productoServicio.updatePrice(99L, 500.00);
		} catch (Exception e) {
			System.out.println("Error capturado: " + e.getMessage());
		}

		System.out.println("\n--- Verificando producto actualizado ---");
		System.out.println(productoServicio.findById(1L));
	}
}
