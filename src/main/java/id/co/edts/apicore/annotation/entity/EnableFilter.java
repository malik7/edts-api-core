package id.co.edts.apicore.annotation.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnableFilter {
    String[] columns() default {};
    Class<? extends Enum>[] enumClass() default {};
}