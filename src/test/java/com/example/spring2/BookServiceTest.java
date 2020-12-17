package com.example.spring2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void givenAnEntity_getBookById_shouldReturnValidDto(){
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        BookDto bookById = bookService.getBookById(1L);

        //assertEquals(bookDto.getId(), bookById.getId());
        assertThat(bookDto.getId()).isEqualTo(bookById.getId());
    }

}
