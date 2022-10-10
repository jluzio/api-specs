package com.example.api.client;

import com.example.api.jsonplaceholder.api.v1.JsonPlaceholderApi;
import com.example.api.jsonplaceholder.api.v1.model.Album;
import com.example.api.jsonplaceholder.api.v1.model.Post;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Slf4j
class ClientTest {

  @TestConfiguration
  @ComponentScan("com.example.api")
  static class TestConfig {

    @Bean
    RestTemplate restTemplate() {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
      return restTemplate;
    }
  }

  @Autowired
  JsonPlaceholderApi api;
  @Autowired
  RestTemplate restTemplate;

  @Test
  void test() {
    log.info("output: {}", api.getUserAlbums(1));

    restTemplate.postForEntity(
        "https://jsonplaceholder.typicode.com/posts",
        new Post()
            .title("user1 new-title")
            .body("user1 new-body")
            .userId(1)
        , String.class
    );
  }

}
