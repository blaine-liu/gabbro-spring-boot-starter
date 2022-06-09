package io.github.aliothliu.gabbro;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liubin
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GabbroAutoConfiguration.class)
public @interface EnableGabbro {
}
