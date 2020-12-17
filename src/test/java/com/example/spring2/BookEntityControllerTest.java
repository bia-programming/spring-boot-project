package com.example.spring2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookEntityControllerTest {

    private static final String PATH = "/books";
    private MockMvc mockMvc;
    private String requestJson;

    @Mock
    private BookService bookService;


    @InjectMocks
    private BookController bookController;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void saveBook_expectSuccess() throws Exception {
        File file = ResourceUtils.getFile("classpath:dto/book.json");
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        ObjectMapper mapper = new ObjectMapper();
        BookDto createBookDto = mapper.readValue(file, BookDto.class);
        requestJson = mapper.writeValueAsString(createBookDto);
        this.mockMvc
                .perform(post("/book").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getBookById_expectSucess() throws Exception {
        when(bookService.getBookById(21L)).thenThrow(new BookNotFoundException(21L));
        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + "21")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andReturn();

    }

}