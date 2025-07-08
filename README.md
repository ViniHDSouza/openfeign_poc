# 📚 Projeto de Estudo: Spring Cloud OpenFeign com Resilience4J (Análise Detalhada)

Este projeto é uma Prova de Conceito (PoC) desenvolvida para dissecar e demonstrar as capacidades do **Spring Cloud OpenFeign** em um ambiente Spring Boot. O objetivo é ir além do básico e explorar como construir clientes HTTP robustos e resilientes, cobrindo:

- **Clientes Declarativos**: A essência do OpenFeign.
- **Resiliência e Circuit Breaking**: Proteção contra falhas com **Resilience4J**.
- **Mecanismos de Fallback**: Estratégias para lidar com a indisponibilidade de serviços.
- **Tratamento de Erros Customizado**: Transformando erros HTTP em exceções de negócio.
- **Interceptação de Requisições**: Adição de headers globais para segurança e rastreamento.
- **Estratégias de Teste**: Uso de **WireMock** para testes de integração isolados.

---

## 🧠 Por que OpenFeign? A Vantagem Declarativa

Em arquiteturas de microsserviços, a comunicação entre serviços é constante. A abordagem tradicional com `RestTemplate` ou `WebClient` é imperativa e pode gerar código repetitivo e verboso.

**Abordagem Tradicional (Imperativa):**
```java
// Precisa construir a URL, tratar o response, extrair o body, etc.
RestTemplate restTemplate = new RestTemplate();
String url = "https://jsonplaceholder.typicode.com/posts/1";
try {
    ResponseEntity<PostDTO> response = restTemplate.getForEntity(url, PostDTO.class);
    if (response.getStatusCode().is2xxSuccessful()) {
        return response.getBody();
    }
} catch (HttpClientErrorException ex) {
    // Lógica para tratar erros 4xx, 5xx...
}
```

**Abordagem OpenFeign (Declarativa):**
```java
// Você apenas declara a interface. O Spring faz o resto.
@FeignClient(name = "PostClient", url = "https://jsonplaceholder.typicode.com")
public interface PostClient {
    @GetMapping("/posts/{id}")
    PostDTO getPost(@PathVariable("id") Long id);
}

// No seu serviço, a chamada é limpa e direta:
PostDTO post = postClient.getPost(1L);
```
OpenFeign abstrai toda a complexidade da montagem da requisição, envio, recebimento e decodificação da resposta, permitindo que o desenvolvedor foque na lógica de negócio.

---

## 🔬 Anatomia Detalhada dos Componentes

### 1. O Cliente Feign (`PostClient.java`)

A anotação `@FeignClient` é o coração da configuração. Vamos analisar cada atributo usado:

```java
@FeignClient(
    name = "PostClient",
    url = "https://jsonplaceholder.typicode.com",
    configuration = ClientConfig.class
)
public interface PostClient { /* ... */ }
```

- **`name = "PostClient"`**: Este é um nome **lógico** e **obrigatório**. Ele não é usado apenas para identificação, mas é a chave que o Spring Cloud usa para vincular configurações específicas, como as do **Resilience4J** e do Load Balancer (se estivesse usando Eureka ou Consul).
- **`url = "..."`**: A URL base da API que será consumida. Todas as requisições definidas na interface serão relativas a esta URL.
- **`configuration = ClientConfig.class`**: Aponta para uma classe de configuração específica para *este cliente*. Neste projeto, a `ClientConfig` define um `Bean` para o nosso `CustomErrorDecoder`. Isso permite customizar o comportamento do cliente sem poluir a configuração global da aplicação.


### 2. Tratamento de Erros Personalizado (`CustomErrorDecoder.java`)

Por padrão, qualquer resposta HTTP com status `4xx` ou `5xx` lança uma `FeignException` genérica. Isso dificulta o tratamento de erros específicos. O `ErrorDecoder` nos permite interceptar a resposta e lançar exceções de negócio mais significativas.

**O Fluxo Completo:**
1.  **`CustomErrorDecoder`**: Implementa a interface `feign.codec.ErrorDecoder`. Ele inspeciona o `response.status()` e, se for um erro conhecido (como 404), lança nossa exceção customizada (`ResourceNotFoundException`).
2.  **`ClientConfig`**: Registra o `CustomErrorDecoder` como um `@Bean`, tornando-o disponível para o Feign.
3.  **`@FeignClient(configuration = ...)`**: O cliente é instruído a usar essa configuração específica.
4.  **`GlobalExceptionHandler`**: Um `@ControllerAdvice` captura a `ResourceNotFoundException` e a transforma em uma resposta JSON amigável para o cliente final da nossa API, com o status HTTP correto.

### 3. Interceptadores de Requisição (`CustomRequestInterceptor.java`)

Interceptores são ganchos que modificam uma requisição *antes* de ela ser enviada. São ideais para tarefas transversais, como:
- Adicionar um header `Authorization` com um token JWT.
- Propagar headers de rastreamento (tracing) entre microsserviços.
- Adicionar headers padrão, como `Accept` ou `Content-Type`.

Como o `CustomRequestInterceptor` é um `@Component`, ele é aplicado automaticamente a **todos os clientes Feign** da aplicação.

```java
@Component
public class CustomRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // Este código será executado para cada chamada de cada cliente Feign
        template.header("Authorization", "Bearer fake-token");
        template.header("X-App", "OpenFeign-Tutorial");
    }
}
```

---

## 🚦 O Fluxo de uma Requisição na Prática

Compreender como os componentes interagem é fundamental.

#### Cenário 1: Chamada com Sucesso (`GET /v1/posts/1`)
1.  `PostController` recebe a requisição.
2.  Chama `postClient.getPost(1L)`.
3.  O proxy do OpenFeign cria uma requisição HTTP.
4.  O `CustomRequestInterceptor` adiciona os headers `Authorization` e `X-App`.
5.  A requisição é enviada para `https://jsonplaceholder.typicode.com/posts/1`.
6.  A API retorna `200 OK` com um JSON.
7.  O decodificador padrão do Feign (Jackson) converte o JSON para um objeto `PostDTO`.
8.  O objeto `PostDTO` é retornado ao `PostController` e, em seguida, ao usuário.

#### Cenário 2: Recurso Não Encontrado (`GET /v1/posts/9999`)
1.  Os passos 1-4 são os mesmos.
2.  A API externa retorna `404 Not Found`.
3.  O **`CustomErrorDecoder`** é acionado. Ele vê o status 404 e lança uma `new ResourceNotFoundException(...)`.
4.  A exceção sobe a pilha de chamadas.
5.  O **`GlobalExceptionHandler`** captura a `ResourceNotFoundException`.
6.  Ele cria uma resposta JSON de erro estruturada com status `404 NOT_FOUND` e a retorna ao usuário.

👨‍💻 Autor
Vinicius Henrique Dias de Souza
Desenvolvedor backend Java | Estudo prático sobre OpenFeign