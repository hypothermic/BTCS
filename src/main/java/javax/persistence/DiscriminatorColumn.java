package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscriminatorColumn
{
  String name() default "DTYPE";
  
  DiscriminatorType discriminatorType() default DiscriminatorType.STRING;
  
  String columnDefinition() default "";
  
  int length() default 31;
}
