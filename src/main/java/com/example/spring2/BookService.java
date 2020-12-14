package com.example.spring2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookRepository repository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.repository = bookRepository;
    }


    public List<BookDto> getAllBooks(){
        return repository.findAll().stream()
                .map(bookEntity -> {
                    BookDto bookDto = new BookDto(bookEntity.getAuthor(), bookEntity.getTitle());
                    bookDto.setId(bookEntity.getId());
                    return bookDto;
                }).collect(Collectors.toList());
    }

    public BookEntity saveNewBook(BookDto bookDto){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        return repository.save(bookEntity);
    }

    public BookDto getBookById(long id){
        BookEntity bookEntity = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        BookDto bookDto = new BookDto(bookEntity.getAuthor(), bookEntity.getTitle());
        bookDto.setId(bookEntity.getId());
        return bookDto;
    }

    public BookEntity updateBookById(@RequestBody BookDto newBook, Long id){
        return repository.findById(id)
                .map(bookEntity -> {
                    bookEntity.setTitle(newBook.getTitle());
                    bookEntity.setAuthor(newBook.getAuthor());
                    return repository.save(bookEntity);
                }).orElseThrow(() -> new BookNotFoundException(id));
    }

    public void deleteBookById(Long id){
        repository.deleteById(id);
    }
}
