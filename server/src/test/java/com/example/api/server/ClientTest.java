package com.example.api.server;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.api.jsonplaceholder.api.ApiClient;
import com.example.api.jsonplaceholder.api.v1.JsonPlaceholderApi;
import com.example.api.jsonplaceholder.api.v1.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

@RestClientTest
@Slf4j
class ClientTest {

  public static final String ENDPOINT = "https://jsonplaceholder.typicode.com";

  @Configuration
  @Import({JsonPlaceholderApi.class})
  static class Config {

    @Bean
    RestClient restClient(RestClient.Builder builder) {
      return builder
          .baseUrl(ENDPOINT)
          .build();
    }

    @Bean
    ApiClient apiClient(RestClient restClient) {
      // using default basePath, which is the same of ENDPOINT
      return new ApiClient(restClient)
//          .setBasePath(ENDPOINT)
          ;
    }
  }

  @Autowired
  JsonPlaceholderApi api;
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
    var expected = objectMapper.readValue(json, User.class);

    mockRestServiceServer.expect(ExpectedCount.twice(), MockRestRequestMatchers.requestTo(ENDPOINT + "/users/1"))
        .andRespond(MockRestResponseCreators.withSuccess(json, MediaType.APPLICATION_JSON));

    var restClientResponse = restClient.get()
        .uri("/users/1")
        .retrieve()
        .body(User.class);
    log.info("restClientResponse: {}", restClientResponse);
    assertThat(restClientResponse)
        .isEqualTo(expected);

    var apiResponse = api.getUser(1);
    log.info("apiResponse: {}", apiResponse);
    assertThat(apiResponse)
        .isNotNull()
        .isEqualTo(expected);
  }

}
