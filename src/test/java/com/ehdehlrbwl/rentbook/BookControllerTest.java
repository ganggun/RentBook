package com.ehdehlrbwl.rentbook;

import com.ehdehlrbwl.rentbook.config.BaseController;
import com.ehdehlrbwl.rentbook.dto.BookRequestDto;
import com.ehdehlrbwl.rentbook.repository.BookRepository;
import com.ehdehlrbwl.rentbook.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BookControllerTest extends BaseController {

    @Autowired
    private MockMvc mockMvc;


    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {}

    @Test
    void createBook_success() throws Exception {
        BookRequestDto dto = new BookRequestDto("테스트 책", "테스트 저자");
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
