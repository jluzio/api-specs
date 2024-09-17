package com.example.api.client.playground;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.jsonplaceholder.feign.api.v1.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootTest
@Slf4j
class FeignTest {

  @TestConfiguration
  @EnableFeignClients(clients = DataApi.class)
  static class TestConfig {

  }

  @FeignClient(name = "data-api")
  public interface DataApi {

    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Integer id);

  }

  @Autowired
  DataApi api;

  
  @Test
  void test() {
    var user = api.getUser(1);
    log.info("output: {}", user);
    assertThat(user)
        .isNotNull();
  }

}
