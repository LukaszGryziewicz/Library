package com.library.bookTests;


import com.library.book.Book;
import com.library.book.BookController;
import com.library.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllBooks() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Adam 123", "Dominik Adam", "987654321");
        //when
        when(bookService.getBooks()).thenReturn(List.of(book, book2));
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Adam z Nikiszowca"))
                .andExpect(jsonPath("$[0].author").value("Adam Dominik"))
                .andExpect(jsonPath("$[0].isbn").value("123456789"))
                .andExpect(jsonPath("$[1].title").value("Adam 123"))
                .andExpect(jsonPath("$[1].author").value("Dominik Adam"))
                .andExpect(jsonPath("$[1].isbn").value("987654321"));
    }

    @Test
    void shouldReturnBookWithGivenId() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        book.setId(1);
        //when
        when(bookService.findBook(book.getId())).thenReturn(book);
        //than
        mockMvc.perform(MockMvcRequestBuilders.get("/book/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value("Adam z Nikiszowca"))
                .andExpect(jsonPath("$.author").value("Adam Dominik"))
                .andExpect(jsonPath("$.isbn").value("123456  789"));

    }

    @Test
    void shouldAddBook() throws Exception {
        //given
        String json = "{\n" +
                "  \"title\":\"Adam z Nikiszowca\",\n" +
                "  \"author\":\"Adam Domnik\",\n" +
                "  \"isbn\": \"123456789\"\n" +
                "}";
        //than
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Adam z Nikiszowca"))
                .andExpect(jsonPath("$.author").value("Adam Dominik"))
                .andExpect(jsonPath("$.isbn").value("123456789"));
    }
}
