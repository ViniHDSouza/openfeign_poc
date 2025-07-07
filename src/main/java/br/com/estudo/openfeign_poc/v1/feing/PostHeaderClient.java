package br.com.estudo.openfeign_poc.v1.feing;

import br.com.estudo.openfeign_poc.v1.dto.CommentDTO;
import br.com.estudo.openfeign_poc.v1.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
name:
    Um nome lógico para o cliente. É obrigatório e usado pelo Spring Cloud para criar configurações
    específicas (Load Balancer, Circuit Breaker).
url:
    A URL base do serviço que o cliente irá consumir. Neste caso, estamos usando a API pública do JSONPlaceholder.
 */
@FeignClient(name = "PostHeaderClient", url = "https://postman-echo.com", configuration = br.com.estudo.openfeign_poc.v1.config.ClientConfig.class)
public interface PostHeaderClient {

    /*
    Acessar http://localhost:8080/test-headers retornará um JSON contendo os cabeçalhos que sua aplicação enviou,
    incluindo x-custom-header e authorization.
     */
    @GetMapping("/headers")
    Map<String, Object> getHeaders(
            @RequestHeader("X-Custom-Header") String customHeader,
            @RequestHeader("Authorization") String authorization
    );

}