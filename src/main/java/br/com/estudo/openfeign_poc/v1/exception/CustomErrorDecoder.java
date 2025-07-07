package br.com.estudo.openfeign_poc.v1.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // methodKey é o nome do método do client: "JsonPlaceholderClient#getPostById(Long)"
        System.err.println("Erro na chamada: " + methodKey);
        System.err.println("Status: " + response.status());

        switch (response.status()) {
            case 404:
                return new ResourceNotFoundException("Recurso não encontrado na chamada: " + methodKey);
            case 503:
                return new ServiceUnavailableException("Serviço indisponível: " + methodKey);
            default:
                // Delega para o decodificador padrão para outros erros
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
