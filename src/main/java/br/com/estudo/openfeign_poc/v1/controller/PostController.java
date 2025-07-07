package br.com.estudo.openfeign_poc.v1.controller;

import br.com.estudo.openfeign_poc.v1.dto.CommentDTO;
import br.com.estudo.openfeign_poc.v1.dto.PostDTO;
import br.com.estudo.openfeign_poc.v1.feing.PostClient;
import br.com.estudo.openfeign_poc.v1.feing.PostHeaderClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostClient client;
    private final PostHeaderClient clientHeader;

    public PostController(PostClient client, PostHeaderClient clientHeader) {
        this.client = client;
        this.clientHeader = clientHeader;
    }

    @GetMapping
    public List<PostDTO> listar() {
        return client.getPosts();
    }

    @GetMapping("/{id}")
    public PostDTO getPost(@PathVariable("id") Long id){
        return client.getPost(id);
    }

    @PostMapping
    public PostDTO criar(@RequestBody PostDTO post) {
        return client.createPost(post);
    }

    @GetMapping("/posts-feign/{id}")
    public List<CommentDTO> getPostById(@PathVariable Long id) {
        return client.getCommentsByPostId(id);
    }

    @GetMapping("/test-headers")
    public Map<String, Object> testHeaders() {
        return clientHeader.getHeaders("MeuValorCustomizado", "Bearer meu-token-123");
    }

}
