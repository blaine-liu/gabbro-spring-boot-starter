package org.reshuffle.gabbro;

import java.lang.annotation.*;

/**
 * @author liubin
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface WithoutJSend {

}
