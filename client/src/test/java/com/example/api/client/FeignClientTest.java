package com.example.api.client;

import com.example.api.jsonplaceholder.feign.api.v1.JsonPlaceholderApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@Slf4j
class FeignClientTest {

  @TestConfiguration
  @ComponentScan("com.example.api.jsonplaceholder.feign")
  static class TestConfig {

  }

  @Autowired
  JsonPlaceholderApi api;

  // TODO: fix test
  @Test
  void test() {
    log.info("output: {}", api.getUserAlbums(1));
  }

}
