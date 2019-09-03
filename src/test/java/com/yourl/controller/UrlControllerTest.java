package com.yourl.controller;

import com.yourl.domain.UrlEntity;
import com.yourl.service.IUrlStoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @SpringBootTest is the general test annotation.
 * It does not use slicing at all which means it'll start your full application context
 * and not customize component scanning at all.
 * By default, @SpringBootTest does not start the server. If you have web endpoints that
 * you want to test against this mock environment, you can additionally configure MockMvc.
 * If you want Spring to autoconfigure MocMvc then you have to add the annotation @AutoConfigureMockMvc.
 *
 *
 * @WebMvcTest is only going to scan the controller you've defined and the MVC infrastructure.
 * This is much faster as we only load a tiny portion of your app. This annotation uses slicing.
 * If you want to focus only on the web layer and not start a complete ApplicationContext,
 * consider using @WebMvcTest instead.
 *
 * Sliced solution (lighter and faster)
 * @ExtendWith(SpringExtension.class)
 * @WebMvcTest
 *
 * Full injection, slower
 * @ExtendWith(SpringExtension.class)
 * @SpringBootTest
 * @AutoConfigureMockMvc
 *
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
class UrlControllerTest {

    @Autowired
    private UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUrlStoreService urlStoreService;

    @Test
    void should_return_404_when_missing_shortened_url() throws Exception {
        // Given
        // when(urlStoreService.findUrlById("vclkxvje")).thenReturn("http://abc123.com");

        // When
        mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/{a}", "xxx_doesnt_exist")
                        .characterEncoding("UTF-8"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void should_return_404_when_invalid_url() throws Exception {

        // When
        mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/{a}", "xxx_doesnt_exist")
                        .characterEncoding("UTF-8"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void should_return_301_when_found_shortened_url() throws Exception {
        // Given(
        when(urlStoreService.findUrlById("abc_123")).thenReturn("http://abc123.com");

        // When
        mockMvc.perform(
                    MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/{a}", "abc_123")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                .andExpect(status().is(301))
                .andDo(print());
    }

    @Test
    void should_return_200_when_listing_all_urls_is_empty() throws Exception {
        // Given
        List<UrlEntity> urlEntities = new ArrayList<>();

        when(urlStoreService.getAll()).thenReturn(urlEntities);

        // When
        mockMvc.perform(
                    MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/urls")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void should_return_entity_when_present() throws Exception {
        // Given
        when(urlStoreService.findUrlById("aa77cd")).thenReturn("http://WD5.example.org");

        // When and then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/urls/aa77cd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.url", is("http://WD5.example.org")))
                .andExpect(jsonPath("$.shortUrl", is("aa77cd")))
                .andDo(print());

    }


    @Test
    void should_return_one_url_when_listing_all_urls_contains_one_item() throws Exception {
        // Given
        List<UrlEntity> urlEntities = new ArrayList<>();
        UrlEntity urlEntity = new UrlEntity("http://shortdomain.com/bvkcje", "http://abc.example.org");
        urlEntities.add(urlEntity);

        when(urlStoreService.getAll()).thenReturn(urlEntities);

        // When
        mockMvc.perform(
                    MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/urls")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].url", is("http://abc.example.org")))
                .andExpect(jsonPath("$[0].shortUrl", is("http://shortdomain.com/bvkcje")))
                .andDo(print());
    }

    @Test
    void should_return_201_when_created_shortened_url() throws Exception {
        // Given(
        // when(urlStoreService.findUrlById("abc_123")).thenReturn("http://abc123.com");

        // When
        mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST,"/urls")
                        .param("url","http://abc.example.org")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().is(201))
                .andExpect(header().exists("Content-Type"))
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.url", equalTo("http://abc.example.org")))
                .andExpect(jsonPath("$.shortUrl", not(isEmptyOrNullString())))
                .andDo(print());
    }

}