package net.minecraft.server;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface SidedProxy
{
  String clientSide() default "";
  
  String serverSide() default "";
  
  String bukkitSide() default "";
}
