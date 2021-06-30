package com.library.book;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookService.addNewBook(book);
        bookService.addNewBook(book2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }

    @Test
    void shouldReturnBookWithGivenId() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookService.addNewBook(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));

    }

    @Test
    void shouldReturnBookWithGivenTitleAndAuthor() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookService.addNewBook(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getTitle() + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()));

    }

    @Test
    void shouldAddBook() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        String content = objectMapper.writeValueAsString(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));

    }

    @Test
    void shouldFindBookById() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookService.addNewBook(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));
    }

    @Test
    void shouldReturnListOfBooksByGivenTitleAndAuthor() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookService.addNewBook(book);
        bookService.addNewBook(book2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/book/" + book.getTitle() + "/" + book.getAuthor()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        Book book2 = new Book("Łukasz z Bytomia", "Łukasz Gryziewicz", "987654321");
        bookService.addNewBook(book);
        String content = objectMapper.writeValueAsString(book2);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.put("/book/update/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book2.getTitle()))
                .andExpect(jsonPath("$.author").value(book2.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book2.getIsbn()));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        //given
        Book book = new Book("Adam z Nikiszowca", "Adam Dominik", "123456789");
        bookService.addNewBook(book);
        //expect
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}