package cpw.mods.fml.common;

import java.lang.reflect.Field;
import java.util.logging.Logger;



















public class ReflectionHelper
{
  public static boolean obfuscation;
  
  public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, int fieldIndex)
  {
    try
    {
      Field f = classToAccess.getDeclaredFields()[fieldIndex];
      f.setAccessible(true);
      return (T)f.get(instance);
    }
    catch (Exception e)
    {
      FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem getting field %d from %s", new Object[] { Integer.valueOf(fieldIndex), classToAccess.getName() }));
      FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", e);
      throw new RuntimeException(e);
    }
  }
  

  public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, String fieldName)
  {
    try
    {
      Field f = classToAccess.getDeclaredField(fieldName);
      f.setAccessible(true);
      return (T)f.get(instance);
    }
    catch (Exception e)
    {
      if (((fieldName.length() > 3) && (!obfuscation)) || ((fieldName.length() <= 3) && (obfuscation))) {
        FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem getting field %s from %s", new Object[] { fieldName, classToAccess.getName() }));
        FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", e);
      }
      throw new RuntimeException(e);
    }
  }
  
  public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, int fieldIndex, E value)
  {
    try
    {
      Field f = classToAccess.getDeclaredFields()[fieldIndex];
      f.setAccessible(true);
      f.set(instance, value);
    }
    catch (Exception e)
    {
      FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem setting field %d from %s", new Object[] { Integer.valueOf(fieldIndex), classToAccess.getName() }));
      FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", e);
      throw new RuntimeException(e);
    }
  }
  
  public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, String fieldName, E value)
  {
    try
    {
      Field f = classToAccess.getDeclaredField(fieldName);
      f.setAccessible(true);
      f.set(instance, value);
    }
    catch (Exception e)
    {
      if (((fieldName.length() > 3) && (!obfuscation)) || ((fieldName.length() <= 3) && (obfuscation))) {
        FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem setting field %s from %s", new Object[] { fieldName, classToAccess.getName() }));
        FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", e);
      }
      throw new RuntimeException(e);
    }
  }
  



  public static void detectObfuscation(Class<?> clazz)
  {
    obfuscation = !clazz.getSimpleName().equals("World");
  }
}
