package com.example.api.client.playground;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
class FeignRetryerTest {

  @TestConfiguration
  @EnableFeignClients(clients = ExampleRetryerApi.class)
  static class TestConfig {

  }

  @FeignClient(name = "data-retryer-api")
  public interface ExampleRetryerApi {

    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Integer id);

  }

  @Autowired
  ExampleRetryerApi api;

  @Test
  void test() {
    // verify in logs that retry has occurred (default config is maxAttempts=5)
    assertThatThrownBy(() -> api.getUser(1))
        .isInstanceOf(feign.RetryableException.class);
  }

}
