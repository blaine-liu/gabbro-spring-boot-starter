package io.github.aliothliu.gabbro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aliothliu.gabbro.jsend.JSend;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liubin
 */
public class GabbroResponseAdvice implements ProblemHandling, SecurityAdviceTrait, ResponseBodyAdvice<Object> {

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
        if (!(MediaType.APPLICATION_JSON.equals(mediaType) || MediaType.APPLICATION_PROBLEM_JSON.equals(mediaType))) {
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
        } else if (body instanceof Problem) {
            Problem problem = (Problem) body;
            int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String detail = problem.getDetail();
            if (Objects.nonNull(problem.getStatus())) {
                statusCode = problem.getStatus().getStatusCode();
            }
            if (statusCode >= HttpStatus.BAD_REQUEST.value() && statusCode < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                if (problem instanceof ConstraintViolationProblem) {
                    ConstraintViolationProblem violationProblem = (ConstraintViolationProblem) problem;
                    detail = violationProblem.getViolations().stream().map(Violation::getMessage).distinct().collect(Collectors.joining(","));
                }
                return JSend.fail(statusCode, detail, problem.getParameters());
            }
            return JSend.error(statusCode, detail, problem.getParameters());
        }

        return JSend.success(body);
    }
}
