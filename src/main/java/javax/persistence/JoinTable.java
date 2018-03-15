package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable
{
  String name() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  JoinColumn[] joinColumns() default {};
  
  JoinColumn[] inverseJoinColumns() default {};
  
  UniqueConstraint[] uniqueConstraints() default {};
}
