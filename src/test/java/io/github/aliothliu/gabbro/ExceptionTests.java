package io.github.aliothliu.gabbro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExceptionTests {

    @Test
    void test404(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/404")).andExpect(status().is(404)).andExpect(content().string("{\"status\":\"fail\",\"code\":404,\"message\":\"Not Found\",\"data\":{}}"));
    }

    @Test
    void testOrderNotFound(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/order")).andExpect(status().is(400)).andExpect(content().string("{\"status\":\"fail\",\"code\":40002001,\"message\":\"Can't find order by [1]\",\"data\":{}}"));
    }

    @Test
    void testOrderNpe(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/npe")).andExpect(status().is(500)).andExpect(content().string("{\"status\":\"error\",\"code\":500,\"message\":\"Just for test\",\"data\":{}}"));
    }

    @Test
    void testBadCredentials(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/bad-credentials")).andExpect(status().is(401)).andExpect(content().string("{\"status\":\"fail\",\"code\":401,\"message\":\"Username or password incorrect\",\"data\":{}}"));
    }

    @Test
    void testArguments(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(post("/arguments").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"status\":\"fail\",\"code\":400,\"message\":\"must not be blank\",\"data\":{}}"));
    }
}
