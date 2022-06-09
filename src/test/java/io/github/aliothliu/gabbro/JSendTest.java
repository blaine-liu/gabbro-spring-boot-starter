package io.github.aliothliu.gabbro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JSendTest {

    @Test
    void testString(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/hello-world")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().string("{\"status\":\"success\",\"code\":200,\"message\":null,\"data\":\"Hello World\"}"));
    }

    @Test
    void testMap(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/map")).andExpect(status().isOk()).andExpect(content().string("{\"status\":\"success\",\"code\":200,\"message\":null,\"data\":{\"words\":\"Hello World\"}}"));
    }

    @Test
    void testBoolean(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/boolean")).andExpect(status().isOk()).andExpect(content().string("{\"status\":\"success\",\"code\":200,\"message\":null,\"data\":true}"));
    }

    @Test
    void testWithJSend(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/without-jsend-method")).andExpect(status().isOk()).andExpect(content().string("true"));
    }

    @Test
    void testFile(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/file")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.TEXT_MARKDOWN_VALUE));
    }
}
