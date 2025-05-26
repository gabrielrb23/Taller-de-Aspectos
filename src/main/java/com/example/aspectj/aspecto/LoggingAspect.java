package com.example.aspectj.aspecto;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.aspectj.servicio.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("\nAntes de ejecutar: " + joinPoint.getSignature().getName());
        System.out.println("Argumentos: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Método " + joinPoint.getSignature().getName() + " ejecutado con éxito");
        if (result != null) {
            System.out.println("Resultado: " + result);
        }
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println("Excepción en el método " + joinPoint.getSignature().getName());
        System.out.println("Excepción: " + exception.getMessage());
    }

    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("Después de ejecutar: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* com.example.servicio.ProductoServicio.updatePrice(..))")
    public Object logAroundUpdatePrice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=== Iniciando operación de actualización de precio ===");
        Long productId = (Long) joinPoint.getArgs()[0];
        double newPrice = (double) joinPoint.getArgs()[1];
        System.out.println("Actualizando producto ID: " + productId);
        System.out.println("Nuevo precio: " + newPrice);

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            System.out.println("Precio actualizado correctamente");
        } catch (Exception e) {
            System.out.println("Error al actualizar el precio: " + e.getMessage());
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución: " + (endTime - startTime) + "ms");
            System.out.println("=== Operación de actualización de precio finalizada ===");
        }
        return result;
    }
}