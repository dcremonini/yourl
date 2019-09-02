package com.yourl.repository;

import com.yourl.domain.UrlEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    void find_all_should_return_2_urls() {

        // Given
        UrlEntity urlEntity1 = new UrlEntity();
        urlEntity1.setShortUrl("http://shortUrl.com/12345");
        urlEntity1.setUrl("http://abc,example.org");

        testEntityManager.persist(urlEntity1);

        UrlEntity urlEntity2 = new UrlEntity();
        urlEntity2.setShortUrl("http://shortUrl.com/abcde");
        urlEntity2.setUrl("http://def.example.org");

        testEntityManager.persist(urlEntity2);

        testEntityManager.flush();

        // When
        List<UrlEntity> urlEntities = urlRepository.findAll();

        // Then
        assertThat(urlEntities).isNotEmpty();
        assertThat(urlEntities.size()).isEqualTo(2);
    }
}