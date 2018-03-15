package cpw.mods.fml.common;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mod
{
  String name() default "";
  
  String version() default "";
  
  boolean wantsPreInit() default false;
  
  boolean wantsPostInit() default false;
  
  public static @interface PostInit {}
  
  public static @interface Init {}
  
  public static @interface PreInit {}
}
