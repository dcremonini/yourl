package com.yourl.service;

import com.yourl.controller.dto.UrlResponseDto;
import com.yourl.controller.entity.UrlEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        List<UrlEntity> urlResponseDtos = urlStoreService.getAll();

        // Then
        UrlEntity urlEntity1 = new UrlEntity("short1", "http://long1");
        UrlEntity urlEntity2 = new UrlEntity("short2", "http://long2");
        assertThat(urlResponseDtos).isNotNull();
        assertThat(urlResponseDtos).size().isEqualTo(2);
        assertThat(urlResponseDtos).contains(urlEntity1);
        assertThat(urlResponseDtos).contains(urlEntity2);

    }
}