package com.ggpsgeorge.spring_testing_tutorial;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hamcrest.Matchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTests {
    private static final String ENDPOINT = "/api/v1/users" ;
    private static final String CONTENT_TYPE = "application/json";

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetUserShouldReturn200Ok() throws Exception{
        String email = "sonGoku@dbz.com";
        Long userId = 1L;
        String uri = ENDPOINT + "/" + userId;
        User newUser = new User.UserBuilder()
            .id(userId)
            .firstName("Goku")
            .lastName("Son")
            .email(email)
            .password(MyUtils.generatePassword(7))
            .build();
      
        Mockito.when(userService.findUser(userId)).thenReturn(Optional.of(newUser));

        mockMvc.perform(get(uri))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", Matchers.is(email)))
            .andExpect(jsonPath("$.password").doesNotExist())
            .andDo(MockMvcResultHandlers.print());   
    }

    @Test
    public void testAddShouldReturn201Created() throws JsonProcessingException, Exception{
        String email = "sonGoku@dbz.com";
        User newUser = new User.UserBuilder()
            .id(1L)
            .firstName("Goku")
            .lastName("Son")
            .email(email)
            .password(MyUtils.generatePassword(7))
            .build();

        Mockito.when(userService.saveUser(newUser)).thenReturn(newUser);

        String requestBody = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post(ENDPOINT + "/add")
            .contentType(CONTENT_TYPE)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("Location", Matchers.is(ENDPOINT + "/1")))
            .andExpect(jsonPath("$.email", Matchers.is(email)))
            .andExpect(jsonPath("$.password").doesNotExist())
            .andDo(MockMvcResultHandlers.print());   

    }

    @Test
    public void testGetAllUsersShouldReturn204NoContent() throws Exception{
        mockMvc.perform(get(ENDPOINT + "/all"))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAddShouldReturn400BadRequest() throws JsonProcessingException, Exception{
        User newUser = new User.UserBuilder()
            .firstName("")
            .lastName("")
            .email("")
            .build();

        String requestBody = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post(ENDPOINT + "/add").contentType(CONTENT_TYPE)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testGetUserShouldReturn404NotFound() throws Exception{
        Long id = 1L;
        String requestIdURI = ENDPOINT + "/" + id;

        Mockito.when(userService.findUser(id)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(requestIdURI)
            .contentType(CONTENT_TYPE))
            .andExpect(status().isNotFound())
            .andDo(MockMvcResultHandlers.print());   

    }

}
