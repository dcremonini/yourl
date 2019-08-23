package com.yourl.controller;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(urlStoreService.findUrlById("vclkxvje")).thenReturn("http://abc123.com");

        // When
        mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.GET,"/{a}", "xxx_doesnt_exist")
                        .characterEncoding("UTF-8"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void should_return_200_when_found_shortened_url() throws Exception {
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

}