package org.reshuffle.gabbro.web.jsend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reshuffle.gabbro.WithoutJSend;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * @author liubin
 */
@ControllerAdvice
@Order(value = 8)
public class JSendResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> IGNORE_ANNOTATION = WithoutJSend.class;

    private final ThreadLocal<ObjectMapper> mapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        return !AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), IGNORE_ANNOTATION) && !returnType.hasMethodAnnotation(IGNORE_ANNOTATION);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        // just wrap json
        if (!MediaType.APPLICATION_JSON.equals(mediaType)) {
            return body;
        }
        // 防止重复包裹的问题出现
        if (body instanceof JSend) {
            return body;
        } else if (body instanceof String) {
            JSend result = JSend.success(body);
            try {
                //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
                ObjectMapper mapper = mapperThreadLocal.get();
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                return JSend.error(e.getMessage());
            }
        }

        return JSend.success(body);
    }
}
