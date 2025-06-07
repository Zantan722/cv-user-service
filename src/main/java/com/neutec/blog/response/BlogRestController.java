package com.neutec.blog.response;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
public @interface BlogRestController {

    @AliasFor(annotation = RestController.class)
    String value() default "";
}