package br.com.estudo.openfeign_poc.v1.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/*
Interceptors (RequestInterceptor) são um dos recursos mais poderosos do OpenFeign. Eles permitem modificar programaticamente cada requisição antes de ser enviada.

Isso é útil para adicionar cabeçalhos, autenticação, manipulação de parâmetros, entre outros.

Casos de Uso Comuns:

Injetar Tokens de Autenticação: Adicionar um Authorization header dinamicamente a todas as chamadas.

Propagação de Contexto: Passar adiante cabeçalhos de tracing (ex: X-B3-TraceId) ou de tenant (X-Tenant-ID) entre microsserviços.

Adicionar Cabeçalhos Padrão: Enviar cabeçalhos como Accept ou Content-Type em todas as requisições de um cliente.
 */

@Component
public class CustomRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", "Bearer fake-token");
        template.header("X-App", "OpenFeign-Tutorial");
        template.header("Username", "teste");
        template.header("Password", "1234");
    }
}