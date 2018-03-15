package net.minecraft.server;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface MLProp
{
  String info() default "";
  
  double max() default Double.MAX_VALUE;
  
  double min() default Double.MIN_VALUE;
  
  String name() default "";
}
