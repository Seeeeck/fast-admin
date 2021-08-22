package pers.syq.fastadmin.backstage.annotation.sensitive;

import com.github.houbb.sensitive.annotation.metadata.SensitiveStrategy;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveStrategy(FastAdminPasswordStrategy.class)
public @interface FastAdminSensitivePasswordStrategy {
}
