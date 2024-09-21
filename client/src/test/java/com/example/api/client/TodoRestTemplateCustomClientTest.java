package com.example.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.client.test.LiveTestSupport;
import com.example.api.todo.resttemplatecustom.api.v1.TodoApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Slf4j
// TODO: make a WireMock target for the test, it currently depends on having the server module running
// NOTE: run with environment variable LIVE_TEST=true
@EnabledIf(LiveTestSupport.LIVE_TEST_ENABLE_RULE)
class TodoRestTemplateCustomClientTest {

  @TestConfiguration
  @ComponentScan("com.example.api.todo.resttemplatecustom")
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
