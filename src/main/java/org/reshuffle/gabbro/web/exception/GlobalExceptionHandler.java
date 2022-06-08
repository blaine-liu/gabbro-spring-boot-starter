package org.reshuffle.gabbro.web.exception;

import org.reshuffle.gabbro.web.jsend.JSend;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liubin
 */
@ControllerAdvice
@Order(value = 10)
public class GlobalExceptionHandler implements ProblemHandling, SecurityAdviceTrait, ResponseBodyAdvice<Object> {

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        Type type = returnType.getGenericParameterType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return Arrays.asList(parameterizedType.getActualTypeArguments()).contains(Problem.class);
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object object,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (object instanceof JSend) {
            return object;
        }
        Problem problem = (Problem) object;
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
}
