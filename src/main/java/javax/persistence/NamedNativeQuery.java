package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedNativeQuery
{
  String name() default "";
  
  String query();
  
  QueryHint[] hints() default {};
  
  Class resultClass() default void.class;
  
  String resultSetMapping() default "";
}
