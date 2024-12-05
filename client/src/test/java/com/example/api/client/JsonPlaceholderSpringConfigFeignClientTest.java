package com.example.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.client.JsonPlaceholderSpringConfigFeignClientTest.DefaultJsonPlaceholderApi;
import com.example.api.jsonplaceholder.feign.api.v1.JsonPlaceholderApi;
import feign.Contract;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@Slf4j
@EnableFeignClients(clients = DefaultJsonPlaceholderApi.class)
class JsonPlaceholderSpringConfigFeignClientTest {

  @FeignClient(value = "data-api")
  // Instead of application.yml, use a configuration class in annotation
//  @FeignClient(value = "data-api", configuration = DefaultContractConfiguration.class)
  interface DefaultJsonPlaceholderApi extends JsonPlaceholderApi {

  }

  // Example of configuration class
  static class DefaultContractConfiguration {

    @Bean
    public Contract defaultContract() {
      return new feign.Contract.Default();
    }
  }

  @TestConfiguration
  static class TestConfig {

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
