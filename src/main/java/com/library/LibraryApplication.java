package com.library;

import com.library.book.Book;
import com.library.book.BookController;
import com.library.book.BookService;
import org.apache.catalina.filters.CorsFilter;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LibraryApplication {


	public static void main(String[] args) {

		SpringApplication.run(LibraryApplication.class, args);
	}





}
