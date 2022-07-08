package com.mytests.spring.testing.wiremock.test1;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class SpringWiremockTest1ApplicationTests {


    @Resource
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Test
    public void testAnyMethods() throws Exception {
        stubFor(any(urlPathEqualTo("/server/basic/str"))  // no url injection - https://youtrack.jetbrains.com/issue/IDEA-297648
        //stubFor(any(urlMatching("/server/basic/str")) // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651
        //stubFor(any(urlPathMatching("/server/basic/str")) // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651
        //stubFor(any(urlEqualTo("/server/basic/str")) // ok

                .willReturn(aResponse().withBody("test string")));
        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult get_response = mockMvc.perform(MockMvcRequestBuilders.get("/basic/get_str"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(get_response.getResponse().getContentAsString(),"test string");
        MvcResult post_response = mockMvc.perform(MockMvcRequestBuilders.post("/basic/post_str").content("test string"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(post_response.getResponse().getContentAsString(),"test string");

        ///
    }
    @Test
    public void testMethods() throws Exception {
        //stubFor(request("GET",urlEqualTo("/server/basic/str"))  // ok
        //stubFor(request("GET",urlPathEqualTo("/server/basic/str"))  // no URL injection - https://youtrack.jetbrains.com/issue/IDEA-297648
        //stubFor(request("GET",urlMatching("/server/basic/s.*"))  // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651
        stubFor(request("GET",urlPathMatching("/server/basic/s.*"))  // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651

                .willReturn(aResponse().withBody("test string")));
        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult get_response = mockMvc.perform(MockMvcRequestBuilders.get("/basic/get_str"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(get_response.getResponse().getContentAsString(),"test string");

        ///
    }
    @Test
    public void getBasicHome() throws Exception {
        //stubFor(get("/server/basic/home")   // ok
        //stubFor(get(urlEqualTo("/server/basic/home"))   // ok
        // stubFor(get(urlPathEqualTo("/server/basic/home")) // ok
        //stubFor(get(urlMatching("/server/basic/h.*")) // no regex injection, URL instead - https://youtrack.jetbrains.com/issue/IDEA-297651
        stubFor(get(urlPathMatching("/server/basic/h.*")) // no regex injection, URL instead - https://youtrack.jetbrains.com/issue/IDEA-297651
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello!!!!")));
        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult myresponse = mockMvc.perform(MockMvcRequestBuilders.get("/basic/home"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain; charset=utf-8"))
                .andReturn();
        assertEquals(myresponse.getResponse().getContentAsString(),"Hello!!!!");
        ///
        verify(1,getRequestedFor(urlPathEqualTo("/server/basic/home"))); // no url injection - https://youtrack.jetbrains.com/issue/IDEA-297648
        verify(1, getRequestedFor(urlEqualTo("/server/basic/home")));    // ok
        verify(1, getRequestedFor(urlMatching("/server/b.*/home"))); // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651
        verify(1, getRequestedFor(urlPathMatching("/server/b.*/home"))); // no regex injection - https://youtrack.jetbrains.com/issue/IDEA-297651


    }

    @Test
    public void getPOJO() throws Exception {
        stubFor(get(urlEqualTo("/server/pojo/home"))   // ok
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")

                        .withJsonBody(Json.read("{'id':'1','name':'Vasya','age':18}", JsonNode.class))))
        ; // no injection


        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult myresponse = mockMvc.perform(MockMvcRequestBuilders.get("/pojo/home"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(myresponse.getResponse().getContentAsString(),"MyPOJO{id='1', name='Vasya', age=18}");

        ///
        verify(1, getRequestedFor(urlEqualTo("/server/pojo/home")));
    }
    @Test
    public void getPOJO_shortcut() throws Exception {
        stubFor(get(urlPathEqualTo("/server/pojo/home"))
                .willReturn(okJson("{ \"id\":\"2\",\"name\":\"Masha\",\"age\":25 }")));


        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult myresponse = mockMvc.perform(MockMvcRequestBuilders.get("/pojo/home"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(myresponse.getResponse().getContentAsString(),"MyPOJO{id='2', name='Masha', age=25}");

        ///

        verify(1, getRequestedFor(urlPathEqualTo("/server/pojo/home")));
    }

    @Test
    public void postPOJO_JSONPathMatching() throws Exception {
        stubFor(
                post(urlEqualTo("/server/pojo/add"))
                        //.withRequestBody(equalToJson("{\"id\":\"3\",\"name\":\"Ivan\",\"age\":25 }")) //  ok
                        .withRequestBody(equalToJson("{\"id\":\"3\",\"name\":\"Ivan\",\"age\":25 }",true,true)) // no JSON injection - https://youtrack.jetbrains.com/issue/IDEA-297652
                        .willReturn(okJson("{ \"id\":\"3\",\"name\":\"Ivan\",\"age\":25 }"))); // ok


        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult myresponse = mockMvc.perform(MockMvcRequestBuilders.post("/pojo/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\":\"3\",\"name\":\"Ivan\",\"age\":25 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(myresponse.getResponse().getContentAsString(),"MyPOJO{id='3', name='Ivan', age=25}");

        ///
        verify(1, postRequestedFor(urlEqualTo("/server/pojo/add"))
                .withRequestBody(matchingJsonPath("$.[?(@.age == 25)]")) // ok
                .withRequestBody(matchingJsonPath("$..name", containing("Ivan"))) // no jsonpath injection - https://youtrack.jetbrains.com/issue/IDEA-297654
        );
    }

    @Test
    void withParams() throws Exception {
        stubFor(
                get(urlPathEqualTo("/server/pojo/byId"))
                        .withQueryParam("id", matching("\\d+")) // ok
                        //.withQueryParam("id", equalTo("1"))
                        .willReturn(okJson("{\"id\":\"1\",\"name\":\"Vasya\",\"age\":18}"))
        );
        ///
        mockMvc = webAppContextSetup(webApplicationContext).build();
        MvcResult myresponse = mockMvc.perform(MockMvcRequestBuilders.get("/pojo/{id}","1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(myresponse.getResponse().getStatus(),200);

    }



}
