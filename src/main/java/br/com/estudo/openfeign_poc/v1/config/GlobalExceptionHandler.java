package br.com.estudo.openfeign_poc.v1.config;

import br.com.estudo.openfeign_poc.v1.exception.ApiError;
import br.com.estudo.openfeign_poc.v1.exception.ResourceNotFoundException;
import br.com.estudo.openfeign_poc.v1.exception.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @ControllerAdvice informa ao Spring que esta classe irá "aconselhar"
// os controllers sobre como lidar com exceções.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // O método agora está aqui, e não no controller.
    // Ele será acionado sempre que uma PostNotFoundException for lançada
    // por QUALQUER controller da aplicação.
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Para uma resposta mais estruturada, podemos criar um objeto de erro.
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<Object> handleServiceUnavailableException(ServiceUnavailableException ex) {
        // Para uma resposta mais estruturada, podemos criar um objeto de erro.
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex) {
        // Logar a exceção é uma boa prática em produção
        logger.error("Erro inesperado ocorrido: ", ex);
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
