package com.example.taller4POA.aspecto;
import com.example.taller4POA.excepcion.RegistroNoEncontradoExcep;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

public class AspectoExcepcion {
    @Around("execution(* com.example.taller4POA.servicio.*.*(..))")
    public Object wrapExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            throw new RegistroNoEncontradoExcep(
                    "Error en el servicio: " + ex.getMessage(),
                    "SERVICE_ERROR",
                    ex
            );
        }
    }
}
