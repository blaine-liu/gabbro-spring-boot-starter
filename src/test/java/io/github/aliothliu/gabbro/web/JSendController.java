package io.github.aliothliu.gabbro.web;

import io.github.aliothliu.gabbro.WithoutJSend;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JSendController {

    @RequestMapping(value = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @RequestMapping(value = "/map")
    public ResponseEntity<Map<String, String>> map() {
        Map<String, String> map = new HashMap<>();
        map.put("words", "Hello World");
        return ResponseEntity.ok(map);
    }

    @RequestMapping(value = "/boolean")
    public ResponseEntity<Boolean> getBoolean() {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @RequestMapping(value = "/without-jsend-method")
    @WithoutJSend
    public ResponseEntity<Boolean> withoutJSend() {
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @RequestMapping(value = "/file")
    public void file(HttpServletResponse response) throws IOException {
        //告知浏览器文件的类型(响应体）
        response.setContentType(MediaType.TEXT_MARKDOWN_VALUE);
        //告知浏览器以附件的方式提供下载功能 而不是解析
        response.setHeader("Content-Disposition", "attachment;filename=test.md");
        //服务器获取后开始进行复制的程序：获取字节输出流
        OutputStream os = response.getOutputStream();
        os.write("#test".getBytes());
        os.flush();
        os.close();
    }
}
