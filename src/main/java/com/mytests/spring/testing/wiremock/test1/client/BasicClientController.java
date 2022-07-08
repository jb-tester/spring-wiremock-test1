package com.mytests.spring.testing.wiremock.test1.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * *
 * <p>Created by irina on 7/7/2022.</p>
 * <p>Project: spring-wiremock-test1</p>
 * *
 */
@RestController
public class BasicClientController {

    @Autowired
    private BasicClient client;

    @RequestMapping("/basic/home")
    public String basic_home() {
        return client.home();
    }

    @PostMapping("/basic/post_str")
    public String post_str(@RequestBody String str){
        return client.str(str);
    }

    @GetMapping("/basic/get_str")
    public String get_str(){
        return client.str();
    }
}
