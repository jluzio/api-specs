package com.example.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.jsonplaceholder.feign.api.ApiClient;
import com.example.api.jsonplaceholder.feign.api.v1.JsonPlaceholderApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@Slf4j
class JsonPlaceholderFeignClientTest {

  @TestConfiguration
  static class Config {

    @Bean
    JsonPlaceholderApi jsonPlaceholderApi(ApiClient apiClient) {
      return apiClient.buildClient(JsonPlaceholderApi.class);
    }

    @Bean
    ApiClient apiClient() {
      return new ApiClient();
    }
  }

  @Autowired
  JsonPlaceholderApi api;


  @Test
  void test() {
    var user = api.getUser(1);
    log.info("output: {}", user);
    assertThat(user)
        .isNotNull();
  }

}
