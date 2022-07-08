package com.mytests.spring.testing.wiremock.test1.client;

import com.mytests.spring.testing.wiremock.test1.model.MyPOJO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * *
 * <p>Created by irina on 7/4/2022.</p>
 * <p>Project: spring-wiremock-test1</p>
 * *
 */
@FeignClient(name = "pojo", url = "http://localhost:8081/server/pojo")
public interface POJOClient {

    @GetMapping("/home")
    MyPOJO home();

    @PostMapping("/add")
    MyPOJO add(MyPOJO pojo);

    ;

    @GetMapping("/byId")
    MyPOJO byId(@RequestParam String id);

    @GetMapping("/all")
    List<MyPOJO> all();
}
