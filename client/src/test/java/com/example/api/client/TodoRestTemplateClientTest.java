package com.example.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.jsonplaceholder.resttemplate.api.v1.JsonPlaceholderApi;
import com.example.api.jsonplaceholder.resttemplate.api.v1.model.Todo;
import com.example.api.todo.resttemplate.api.v1.TodoApi;
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
class TodoRestTemplateClientTest {

  @TestConfiguration
  @ComponentScan("com.example.api.todo.resttemplate")
  static class TestConfig {

    @Bean
    RestTemplate restTemplate() {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
      return restTemplate;
    }
  }

  @Autowired
  TodoApi api;
  @Autowired
  RestTemplate restTemplate;

  
  @Test
  void test() {
    var todo = api.getTodo(1);
    log.info("output: {}", todo);
    assertThat(todo)
        .isNotNull();
  }

}
