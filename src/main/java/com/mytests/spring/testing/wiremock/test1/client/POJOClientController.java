package com.mytests.spring.testing.wiremock.test1.client;

import com.mytests.spring.testing.wiremock.test1.model.MyPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * *
 * <p>Created by irina on 7/7/2022.</p>
 * <p>Project: spring-wiremock-test1</p>
 * *
 */
@RestController
public class POJOClientController {

    @Autowired
    private POJOClient client;

    @GetMapping("/pojo/home")
    public String pojo_home() {
        return client.home().toString();
    }

    @PostMapping("/pojo/add")
    public String addPojo(@RequestBody MyPOJO pojo) {
        return client.add(pojo).toString();
    }

    @GetMapping("/pojo/{id}")
    public String pojoById(@PathVariable String id) {
        return client.byId(id).toString();
    }

}

