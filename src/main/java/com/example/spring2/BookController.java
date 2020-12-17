package com.example.spring2;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private BookService bookService;


    @GetMapping("/books")
    List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/book")
    BookEntity createNewBook(@RequestBody BookDto bookDto) {
        return bookService.saveNewBook(bookDto);
    }

    @GetMapping("/books/{id}")
    BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/books/{id}")
    BookEntity updateBook(@RequestBody BookDto newBook, @PathVariable Long id) {
        return bookService.updateBookById(newBook, id);
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }
}
