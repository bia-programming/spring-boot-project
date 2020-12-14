package com.example.spring2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

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
    private BookRepository repo;


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
                .perform(post("/books").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}