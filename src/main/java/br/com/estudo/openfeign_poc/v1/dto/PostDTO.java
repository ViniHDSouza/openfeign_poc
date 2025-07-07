package br.com.estudo.openfeign_poc.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private Long userId;
    private Long id;
    private String title;
    private String body;

}
