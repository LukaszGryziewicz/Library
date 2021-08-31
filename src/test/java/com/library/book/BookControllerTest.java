package com.library.book;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookFacade bookFacade;
    ObjectMapper objectMapper = new ObjectMapper();

    private BookDTO createBook(String title, String author, String isbn) {
        BookDTO book = new BookDTO(title, author, isbn);
        bookFacade.addBook(book);
        return book;
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("The Odyssey", "Homer", "987654321");
        //expect
        mockMvc.perform(get("/books"))
                .andExpect(jsonPath("$[0].bookId").value(book1.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book1.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$[0].rented").value(book1.isRented()))
                .andExpect(jsonPath("$[1].bookId").value(book2.getBookId()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()))
                .andExpect(jsonPath("$[1].rented").value(book2.isRented()));
    }

    @Test
    void shouldReturnBookWithGivenId() throws Exception {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //expect
        mockMvc.perform(get("/books/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.rented").value(book.isRented()));
    }

    @Test
    void shouldReturnBookWithGivenTitleAndAuthor() throws Exception {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //expect
        mockMvc.perform(get("/books/" + book.getTitle() + "/" + book.getAuthor()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].bookId").value(book.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$[0].rented").value(book.isRented()));
    }

    @Test
    void shouldAddBook() throws Exception {
        //given
        BookDTO book = new BookDTO("Hamlet", "William Shakespeare", "123456789");
        String content = objectMapper.writeValueAsString(book);
        //expect
        mockMvc.perform(post("/books")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.rented").value(book.isRented()));
    }

    @Test
    void shouldFindBookById() throws Exception {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //expect
        mockMvc.perform(get("/books/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.rented").value(book.isRented()));
    }

    @Test
    void shouldReturnListOfBooksByGivenTitleAndAuthor() throws Exception {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = createBook("Hamlet", "William Shakespeare", "123456789");
        //expect
        mockMvc.perform(get("/books/" + book1.getTitle() + "/" + book1.getAuthor()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].bookId").value(book1.getBookId()))
                .andExpect(jsonPath("$[0].title").value(book1.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book1.getAuthor()))
                .andExpect(jsonPath("$[0].isbn").value(book1.getIsbn()))
                .andExpect(jsonPath("$[0].rented").value(book1.isRented()))
                .andExpect(jsonPath("$[1].bookId").value(book2.getBookId()))
                .andExpect(jsonPath("$[1].title").value(book2.getTitle()))
                .andExpect(jsonPath("$[1].author").value(book2.getAuthor()))
                .andExpect(jsonPath("$[1].isbn").value(book2.getIsbn()))
                .andExpect(jsonPath("$[1].rented").value(book2.isRented()));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        //given
        BookDTO book1 = createBook("Hamlet", "William Shakespeare", "123456789");
        BookDTO book2 = new BookDTO("The Odyssey", "Homer", "987654321");
        String content = objectMapper.writeValueAsString(book2);
        //expect
        mockMvc.perform(put("/books/" + book1.getBookId())
                        .contentType(APPLICATION_JSON)
                        .content(content)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(book1.getBookId()))
                .andExpect(jsonPath("$.title").value(book2.getTitle()))
                .andExpect(jsonPath("$.author").value(book2.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(book2.getIsbn()))
                .andExpect(jsonPath("$.rented").value(book2.isRented()));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        //given
        BookDTO book = createBook("Hamlet", "William Shakespeare", "123456789");
        //expect
        mockMvc.perform(delete("/books/" + book.getBookId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
