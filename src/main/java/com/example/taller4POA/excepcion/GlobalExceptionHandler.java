package com.example.taller4POA.excepcion;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class GlobalExceptionHandler {

    // Para APIs REST
    @ExceptionHandler(RegistroNoEncontradoExcep.class)
    public ResponseEntity<ErrorResponse> handleApiException(RegistroNoEncontradoExcep ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getUserMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Clase interna para la respuesta de error
    private static class ErrorResponse {
        private final String error;
        private final String message;
        private final String path;

        public ErrorResponse(String error, String message, String path) {
            this.error = error;
            this.message = message;
            this.path = path;
        }

        // Getters
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
    }
}
