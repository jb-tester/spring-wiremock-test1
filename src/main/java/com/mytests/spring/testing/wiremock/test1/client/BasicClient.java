package com.mytests.spring.testing.wiremock.test1.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * *
 * <p>Created by irina on 7/4/2022.</p>
 * <p>Project: spring-wiremock-test1</p>
 * *
 */
@FeignClient(name = "basic", url = "http://localhost:8081/server/basic")
public interface BasicClient {

    @GetMapping("/home")
    String home();

    @GetMapping("/str")
    String str();
    @PostMapping("/str")
    String str(String str);
}
