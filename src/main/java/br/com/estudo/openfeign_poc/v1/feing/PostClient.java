package br.com.estudo.openfeign_poc.v1.feing;

import br.com.estudo.openfeign_poc.v1.dto.CommentDTO;
import br.com.estudo.openfeign_poc.v1.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
name:
    Um nome lógico para o cliente. É obrigatório e usado pelo Spring Cloud para criar configurações
    específicas (Load Balancer, Circuit Breaker).
url:
    A URL base do serviço que o cliente irá consumir. Neste caso, estamos usando a API pública do JSONPlaceholder.
 */
@FeignClient(
        name = "PostClient",
        url = "https://jsonplaceholder.typicode.com",
        configuration = br.com.estudo.openfeign_poc.v1.config.ClientConfig.class
        )
public interface PostClient {
    @GetMapping("/posts")
    List<PostDTO> getPosts();

    @GetMapping("/posts/{id}")
    PostDTO getPost(@PathVariable("id") Long id);

    @PostMapping("/posts")
    PostDTO createPost(@RequestBody PostDTO post);

    //Para filtrar recursos, como buscar comentários de um post específico.
    @GetMapping("/comments")
    List<CommentDTO> getCommentsByPostId(@RequestParam("postId") Long postId);

    /*
        Corpo da Requisição (@RequestBody)
        Para operações POST e PUT.
     */
    @PostMapping("/posts")
    PostDTO createPostBody(@RequestBody PostDTO newPost);


}