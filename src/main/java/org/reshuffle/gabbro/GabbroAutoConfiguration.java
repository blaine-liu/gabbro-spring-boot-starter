package org.reshuffle.gabbro;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * @author liubin
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Import(SecurityProblemSupport.class)
@ComponentScan(basePackageClasses = GabbroAutoConfiguration.class)
public class GabbroAutoConfiguration {

}
