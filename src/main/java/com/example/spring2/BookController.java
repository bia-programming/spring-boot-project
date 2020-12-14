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
//@RequiredArgsConstructor(onConstructor = @__(@Autowired)) - asa nu mergea ( repository era null )
public class BookController {

    private BookRepository repository;

    @Autowired
    public BookController(BookRepository bookRepository){
        this.repository = bookRepository;
    }

    @GetMapping("/books")
    List<BookDto> all() {
        return repository.findAll().stream()
                .map(bookEntity -> {
                    BookDto bookDto = new BookDto(bookEntity.getAuthor(), bookEntity.getTitle());
                    bookDto.setId(bookEntity.getId());
                    return bookDto;
                }).collect(Collectors.toList());
    }

    @PostMapping("/books")
    BookEntity newBook(@RequestBody BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        return repository.save(bookEntity);
    }

    // Single item

    @GetMapping("/books/{id}")
    BookDto one(@PathVariable Long id) {

        BookEntity bookEntity = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = new BookDto(bookEntity.getAuthor(), bookEntity.getTitle());
        bookDto.setId(bookEntity.getId());
        return bookDto;
    }

    @PutMapping("/books/{id}")
    BookEntity replaceBook(@RequestBody BookDto newBook, @PathVariable Long id) {



        return repository.findById(id)
                .map(bookEntity -> {
                    bookEntity.setTitle(newBook.getTitle());
                    bookEntity.setAuthor(newBook.getAuthor());
                    return repository.save(bookEntity);
                }).orElseThrow(() -> new BookNotFoundException(id));
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
