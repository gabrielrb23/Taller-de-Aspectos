package com.example.taller4POA.aspecto;

import com.example.taller4POA.modelo.Nota;
import com.example.taller4POA.modelo.Estudiante;
import com.example.taller4POA.repositorio.RepositorioNota;
import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class SeguridadNotaAspecto {

    @Autowired
    private RepositorioNota repositorioNota;

    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = Logger.getLogger(SeguridadNotaAspecto.class.getName());

    @Pointcut("execution(* controlador.NotaControlador.*(..))")
    public void metodosDeNota() {}

    @Before("metodosDeNota()")
    public void verificarPermisos(JoinPoint joinPoint) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = auth.getAuthorities().iterator().next().getAuthority(); // e.g. ROLE_ALUMNO
        String metodoHttp = request.getMethod();

        if ("ROLE_PROFESOR".equals(rol)) {
            return; // acceso total
        }

        if ("ROLE_ALUMNO".equals(rol)) {
            if (!metodoHttp.equals("GET")) {
                logger.warning("Intento de acceso denegado: " + auth.getName() + " intentó " + metodoHttp);
                throw new SecurityException("Los alumnos solo pueden consultar sus notas.");
            }

            // si es GET, validar que la nota pertenece al estudiante autenticado
            if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof Long idNota) {
                Nota nota = repositorioNota.findById(idNota).orElse(null);
                if (nota == null || !nota.getEstudiante().getCorreo().equals(auth.getName())) {
                    logger.warning("Acceso no autorizado a nota ajena por " + auth.getName());
                    throw new SecurityException("No puedes acceder a notas de otros estudiantes.");
                }
            }
        }
    }
}
