package com.library.bookTests;


import com.library.book.Book;
import com.library.book.BookController;
import com.library.book.BookRepository;
import com.library.book.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mvc;


    @Test
    void shouldReturnAllBooks() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        //when
        when(bookService.getBooks()).thenReturn(List.of(book));
        //then
        mvc.perform(MockMvcRequestBuilders.get("/book"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Adam z Nikiszowca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Adam Dominik"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("123456789"));
    }

    @Test
    void shouldAddBook() throws Exception {
        //given
        String json = "{\n" +
                "\"title\": \"Adam z Nikiszowca\",\n" +
                "\"author\": \"Adam Domninik\",\n" +
                "\"isbn:\": \"123456789\"\n" +
                "}";
        //when
        //than
        mvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"{ \"title\": \"Adam z Nikiszowca\", \"author\": Adam Dominik }\",\"isbn\": 123456789}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Adam z Nikiszowca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Adam Dominik"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("123456789"));
    }
}
