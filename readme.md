### 基于Spring Boot的JSON响应格式包装器

![JDK](https://img.shields.io/badge/JDK-1.8+-green?logo=appveyor)
![SpringBoot](https://img.shields.io/badge/springboot-%202.x-green?logo=appveyor)

### Gabbro

Gabbro是一个基于Spring Boot的JSON响应格式包装器，允许开发者通过简单的配置完成全局的JSON响应格式处理，提高开发效率，其提供以下两种情况的JSON响应格式处理

1. RestController的数据响应
2. 常见异常的数据响应，已处理的异常请参考problem-spring-web异常列表

### 开始使用

一、引入依赖

```
<dependency>
    <groupId>io.github.aliothliu</groupId>
    <artifactId>gabbro-spring-boot-starter</artifactId>
    <version>${gabbro.version}</version>
 </dependency>
```

二、配置

添加``@EnableGabbro``配置

````
@SpringBootApplication
@EnableGabbro
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
````

三、如果项目使用Spring Security，需要配置Spring Security

````
...
public class SecurityConfiguration ... {
    ...
    // 引入安全异常捕获
    private final SecurityProblemSupport problemSupport;
    ...
    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.exceptionHandling()
                 // 添加安全异常捕获
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
            ...
        }
}
````

四、在Controller中使用

````
@RestController
public class TestController {

    @GetMapping(value = "/hello-world", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping(value = "/error")
    public ResponseEntity<Void> error() {
        throw new NullPointerException("Just for test");
    }
}
````

**注意：若TestController返回的是String对象时，需要显示声明响应的数据格式，如上所示：``produces = MediaType.APPLICATION_JSON_VALUE``**

五、自定义异常

自定义异常如需要通过Gabbro统一处理的，应继承自``AbstractThrowableProblem``，关于异常处理更多信息可查询[problem-spring-web](https://github.com/zalando/problem-spring-web)文档

六、@WithoutJSend

若接口返回的数据不需要JSend格式包装，可以通过``@WithouJSend``注解生命RestController或者请求方法忽略响应格式处理

示例如下：

1. 注解类

````
@RestController
@WithoutJSend
public class TestController {
  ...
}
````

2. 注解方法

````
@GetMapping(value = "/error")
@WithoutJSend
 public ResponseEntity<Void> error() {
    throw new NullPointerException("Just for test");
 }
````