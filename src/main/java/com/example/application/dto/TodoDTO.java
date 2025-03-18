package com.example.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// @Builder
public class TodoDTO {

    private Integer id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
