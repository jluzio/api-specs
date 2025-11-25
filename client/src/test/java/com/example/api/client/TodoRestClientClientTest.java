package com.example.api.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.client.test.LiveTestSupport;
import com.example.api.jsonplaceholder.feign.api.v1.JsonPlaceholderApi;
import com.example.api.todo.restclient.api.ApiClient;
import com.example.api.todo.restclient.api.v1.TodoApi;
import com.example.api.todo.restclient.api.v1.model.Todo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@RestClientTest
@Slf4j
class TodoRestClientClientTest {

  public static final String ENDPOINT = "https://some-server.com";

  @Configuration
  @Import({TodoApi.class})
  static class TestConfig {

    @Bean
    RestClient restClient(RestClient.Builder builder) {
      return builder
          .baseUrl(ENDPOINT)
          .build();
    }

    @Bean
    ApiClient apiClient(RestClient restClient) {
      return new ApiClient(restClient)
          .setBasePath(ENDPOINT);
    }
  }

  @Autowired
  TodoApi api;
  @Autowired
  RestClient restClient;
  @Autowired
  MockRestServiceServer mockRestServiceServer;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void test() throws JsonProcessingException {
    var json = """
        {"id": 1, "title": "some-todo"}
        """;
    var expected = objectMapper.readValue(json, Todo.class);

    mockRestServiceServer.expect(ExpectedCount.twice(), MockRestRequestMatchers.requestTo(ENDPOINT + "/todos/1"))
        .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

    var restClientResponse = restClient.get()
        .uri("/todos/1")
        .retrieve()
        .body(Todo.class);
    log.info("restClientResponse: {}", restClientResponse);
    assertThat(restClientResponse)
        .isEqualTo(expected);

    var apiResponse = api.getTodo(1);
    log.info("apiResponse: {}", apiResponse);
    assertThat(apiResponse)
        .isNotNull()
        .isEqualTo(expected);
  }

}
