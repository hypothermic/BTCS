package javax.persistence;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableGenerator
{
  String name();
  
  String table() default "";
  
  String catalog() default "";
  
  String schema() default "";
  
  String pkColumnName() default "";
  
  String valueColumnName() default "";
  
  String pkColumnValue() default "";
  
  int initialValue() default 0;
  
  int allocationSize() default 50;
  
  UniqueConstraint[] uniqueConstraints() default {};
}
