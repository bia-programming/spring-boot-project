package com.example.spring2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
    BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}
