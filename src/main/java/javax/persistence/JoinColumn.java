package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn
{
  String name() default "";
  
  String referencedColumnName() default "";
  
  boolean unique() default false;
  
  boolean nullable() default true;
  
  boolean insertable() default true;
  
  boolean updatable() default true;
  
  String columnDefinition() default "";
  
  String table() default "";
}
