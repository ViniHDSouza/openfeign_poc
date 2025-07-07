package br.com.estudo.openfeign_poc.v1.controller;


import br.com.estudo.openfeign_poc.v1.feing.PostClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class) // Testa apenas a camada web (controller)
class PostControllerTest {
    /*
    @Autowired
    private MockMvc mockMvc; // Permite fazer requisições HTTP mockadas

    //@MockBean // Cria um Mock do nosso cliente Feign e o injeta no contexto do Spring
    @MockitoBean
    private PostClient postClient;

    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos em JSON

    @MockBean
    private PostService postService;

    @Test
    void getPostFromExternalApi_shouldReturnPost_whenClientSucceeds() throws Exception {
        // Arrange
        PostDTO requestPost = new PostDTO();
        requestPost.setUserId(101L);
        requestPost.setTitle("New Post");
        requestPost.setBody("Content of new post");

        PostDTO responsePost = new PostDTO();
        responsePost.setId(201L);
        responsePost.setUserId(101L);
        responsePost.setTitle("New Post");
        responsePost.setBody("Content of new post");

        // Mock do serviço ao invés do client
        when(postService.getPostById(any(Long.class))).thenReturn(responsePost);

        // Act & Assert
        mockMvc.perform(get("/v1/posts-feign") // Alterado de get para post
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPost)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.id").value(201L))
                //.andExpect(jsonPath("$.title").value("New Post"));
    }

    @Test
    void createPostInExternalApi_shouldReturnCreatedPost() throws Exception {
        // Arrange
        PostDTO requestPost = new PostDTO();
        requestPost.setUserId(101L);
        requestPost.setTitle("New Post");
        requestPost.setBody("Content of new post");

        PostDTO responsePost = new PostDTO();
        responsePost.setId(201L); // A API externa normalmente gera o ID
        responsePost.setUserId(101L);
        responsePost.setTitle("New Post");
        responsePost.setBody("Content of new post");

        // Quando o método createPost for chamado com qualquer objeto PostDTO, retorne o responsePost
        when(postClient.getPostById(any(Long.class))).thenReturn(responsePost);

        // Act & Assert
        mockMvc.perform(get("/v1/posts-feign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPost)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.id").value(201L))
                //.andExpect(jsonPath("$.title").value("New Post"));
    }

    @Test
    void getPostFromExternalApi_shouldReturnNotFound_whenClientThrowsException() throws Exception {
        // Arrange
        // Mockamos o client para lançar nossa exceção customizada
        // (Isso simula o comportamento do nosso ErrorDecoder)
        when(postService.getPostById(anyLong()))
                .thenThrow(new PostNotFoundException("Post não encontrado."));

        // Act & Assert
        mockMvc.perform(get("/v1/posts-feign/111111111111111111"))
                .andExpect(status().isNotFound())
                .andExpect(result -> result.getResponse().getContentAsString()
                        .contains("Post não encontrado."));
    }

     */
}