package com.example.spring2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/books")
    List<BookDto> all() {
        return repository.findAll().stream()
                .map(book -> {
                    BookDto bookDto = new BookDto(book.getAuthor(), book.getTitle());
                    bookDto.setId(book.getId());
                    return bookDto;
                }).collect(Collectors.toList());
    }

    @PostMapping("/books")
    Book newBook(@RequestBody BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        return repository.save(book);
    }

    // Single item

    @GetMapping("/books/{id}")
    BookDto one(@PathVariable Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = new BookDto(book.getAuthor(), book.getTitle());
        bookDto.setId(book.getId());
        return bookDto;
    }

    @PutMapping("/books/{id}")
    Book replaceBook(@RequestBody BookDto newBook, @PathVariable Long id) {



        return repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setAuthor(newBook.getAuthor());
                    return repository.save(book);
                }).orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
