package br.com.estudo.openfeign_poc.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {
    private Long postId;
    private Long id;
    private String name;
    private String email;
    private String body;
}
