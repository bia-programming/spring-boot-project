package com.example.spring2;

public class BookNotFoundException extends RuntimeException{
    BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}
