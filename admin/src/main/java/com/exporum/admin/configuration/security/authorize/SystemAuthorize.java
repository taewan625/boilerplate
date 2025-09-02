package com.exporum.admin.configuration.security.authorize;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('AUTHOR0000')")
public @interface SystemAuthorize {
}
