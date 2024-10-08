package com.example.api.client.test;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LiveTestSupport {

  public static final String LIVE_TEST_ENABLE_RULE = "#{systemEnvironment['LIVE_TEST'] == 'true'}";
}
