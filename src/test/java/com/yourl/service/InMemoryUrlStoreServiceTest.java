package com.yourl.service;

import com.yourl.domain.UrlEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InMemoryUrlStoreServiceTest {

    @Autowired
    private IUrlStoreService urlStoreService;

    @BeforeEach
    void beforeEach() {
        urlStoreService.clear();
    }

    @Test
    void should_not_find_any_url_when_empty() {
        String longUrl = urlStoreService.findUrlById("id432");
        assertThat(longUrl).isNull();
    }

    @Test
    void should_find_url_when_stored() {
        urlStoreService.storeUrl("id432", "http://abc.example.org");
        String longUrl = urlStoreService.findUrlById("id432");
        assertThat(longUrl).isEqualTo("http://abc.example.org");
    }

    @Test
    void should_get_all_urls_when_getAll() {

        // Given
        urlStoreService.storeUrl("short1", "http://long1");
        urlStoreService.storeUrl("short2", "http://long2");

        // When
        List<UrlEntity> urlResponseDTOs = urlStoreService.getAll();

        // Then
        assertThat(urlResponseDTOs).isNotNull();
        assertThat(urlResponseDTOs).size().isEqualTo(2);

        urlResponseDTOs.stream().findAny().filter(dto -> dto.getShortUrl().equals("short1") && dto.getUrl().equals("http://long1"));
        urlResponseDTOs.stream().findAny().filter(dto -> dto.getShortUrl().equals("short2") && dto.getUrl().equals("http://long2"));
    }
}