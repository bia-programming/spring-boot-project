package com.example.spring2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String author;
    private String title;

    public BookDto(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public BookDto(){

    }
}
