package forge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.server.EnumArt;

public class EnumHelper
{
  private static Object reflectionFactory = null;
  private static Method newConstructorAccessor = null;
  private static Method newInstance = null;
  private static Method newFieldAccessor = null;
  private static Method fieldAccessorSet = null;
  private static boolean isSetup = false;

  private static Class[][] ctrs = { { net.minecraft.server.EnumAnimation.class }, { net.minecraft.server.EnumArmorMaterial.class, Integer.TYPE, int[].class, Integer.TYPE }, { EnumArt.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE }, { net.minecraft.server.MonsterType.class }, { net.minecraft.server.EnumCreatureType.class, Class.class, Integer.TYPE, net.minecraft.server.Material.class, Boolean.TYPE }, { net.minecraft.server.WorldGenStrongholdDoorType.class }, { net.minecraft.server.EnchantmentSlotType.class }, { net.minecraft.server.EnumMobType.class }, { net.minecraft.server.EnumMovingObjectType.class }, { net.minecraft.server.EnumSkyBlock.class, Integer.TYPE }, { net.minecraft.server.EnumBedResult.class }, { net.minecraft.server.EnumToolMaterial.class, Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE } };
  
  private static boolean[] decompiledFlags = new boolean[ctrs.length];
  
  public static org.bukkit.block.Biome addBukkitBiome(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (org.bukkit.block.Biome)addEnum(decompiledFlags[0], org.bukkit.block.Biome.class, name, new Class[0], new Object[0]);
  }

  public static org.bukkit.World.Environment addBukkitEnvironment(int id, String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (org.bukkit.World.Environment)addEnum(decompiledFlags[0], org.bukkit.World.Environment.class, name, new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(id) });
  }
  

  public static net.minecraft.server.EnumAnimation addAction(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumAnimation)addEnum(decompiledFlags[0], net.minecraft.server.EnumAnimation.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumArmorMaterial addArmorMaterial(String name, int durability, int[] reductionAmounts, int enchantability)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumArmorMaterial)addEnum(decompiledFlags[1], net.minecraft.server.EnumArmorMaterial.class, name, new Class[] { Integer.TYPE, int[].class, Integer.TYPE }, new Object[] { Integer.valueOf(durability), reductionAmounts, Integer.valueOf(enchantability) });
  }
  

  public static EnumArt addArt(String name, String tile, int sizeX, int sizeY, int offsetX, int offsetY)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (EnumArt)addEnum(decompiledFlags[2], EnumArt.class, name, new Class[] { String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE }, new Object[] { tile, Integer.valueOf(sizeX), Integer.valueOf(sizeY), Integer.valueOf(offsetX), Integer.valueOf(offsetY) });
  }
  

  public static net.minecraft.server.MonsterType addCreatureAttribute(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.MonsterType)addEnum(decompiledFlags[3], net.minecraft.server.MonsterType.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumCreatureType addCreatureType(String name, Class typeClass, int maxNumber, net.minecraft.server.Material material, boolean peaceful)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumCreatureType)addEnum(decompiledFlags[4], net.minecraft.server.EnumCreatureType.class, name, new Class[] { Class.class, Integer.TYPE, net.minecraft.server.Material.class, Boolean.TYPE }, new Object[] { typeClass, Integer.valueOf(maxNumber), material, Boolean.valueOf(peaceful) });
  }
  

  public static net.minecraft.server.WorldGenStrongholdDoorType addDoor(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.WorldGenStrongholdDoorType)addEnum(decompiledFlags[5], net.minecraft.server.WorldGenStrongholdDoorType.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnchantmentSlotType addEnchantmentType(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnchantmentSlotType)addEnum(decompiledFlags[6], net.minecraft.server.EnchantmentSlotType.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumMobType addMobType(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumMobType)addEnum(decompiledFlags[7], net.minecraft.server.EnumMobType.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumMovingObjectType addMovingObjectType(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumMovingObjectType)addEnum(decompiledFlags[8], net.minecraft.server.EnumMovingObjectType.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumSkyBlock addSkyBlock(String name, int lightValue)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumSkyBlock)addEnum(decompiledFlags[9], net.minecraft.server.EnumSkyBlock.class, name, new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(lightValue) });
  }
  

  public static net.minecraft.server.EnumBedResult addStatus(String name)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumBedResult)addEnum(decompiledFlags[10], net.minecraft.server.EnumBedResult.class, name, new Class[0], new Object[0]);
  }
  

  public static net.minecraft.server.EnumToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, int damage, int enchantability)
  {
    if (!isSetup)
    {
      setup();
    }
    
    return (net.minecraft.server.EnumToolMaterial)addEnum(decompiledFlags[11], net.minecraft.server.EnumToolMaterial.class, name, new Class[] { Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE }, new Object[] { Integer.valueOf(harvestLevel), Integer.valueOf(maxUses), Float.valueOf(efficiency), Integer.valueOf(damage), Integer.valueOf(enchantability) });
  }
  


  private static void setup()
  {
    if (isSetup)
    {
      return;
    }
    

    for (int x = 0; x < ctrs.length; x++)
    {
      try
      {
        Class<?>[] enumHeaders = new Class[ctrs[x].length + 3];
        enumHeaders[0] = String.class;
        enumHeaders[1] = Integer.TYPE;
        enumHeaders[2] = String.class;
        enumHeaders[3] = Integer.TYPE;
        
        for (int y = 1; y < ctrs[x].length; y++)
        {
          enumHeaders[(3 + y)] = ctrs[x][y];
        }
        
        ctrs[x][0].getDeclaredConstructor(enumHeaders);
        decompiledFlags[x] = true;
      }
      catch (Exception e) {}
    }
    







    try
    {
      Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory", new Class[0]);
      reflectionFactory = getReflectionFactory.invoke(null, new Object[0]);
      newConstructorAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newConstructorAccessor", new Class[] { java.lang.reflect.Constructor.class });
      newInstance = Class.forName("sun.reflect.ConstructorAccessor").getDeclaredMethod("newInstance", new Class[] { Object[].class });
      newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", new Class[] { Field.class, Boolean.TYPE });
      fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", new Class[] { Object.class, Object.class });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    isSetup = true;
  }
  






  private static Object getConstructorAccessor(boolean decompiled, Class<?> enumClass, Class<?>[] additionalParameterTypes)
    throws Exception
  {
    Class<?>[] parameterTypes = null;
    if (decompiled)
    {
      parameterTypes = new Class[additionalParameterTypes.length + 4];
      parameterTypes[0] = String.class;
      parameterTypes[1] = Integer.TYPE;
      parameterTypes[2] = String.class;
      parameterTypes[3] = Integer.TYPE;
      System.arraycopy(additionalParameterTypes, 0, parameterTypes, 4, additionalParameterTypes.length);
    }
    else
    {
      parameterTypes = new Class[additionalParameterTypes.length + 2];
      parameterTypes[0] = String.class;
      parameterTypes[1] = Integer.TYPE;
      System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
    }
    return newConstructorAccessor.invoke(reflectionFactory, new Object[] { enumClass.getDeclaredConstructor(parameterTypes) });
  }
  
  private static <T extends Enum<?>> T makeEnum(boolean decompiled, Class<T> enumClass, String value, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception
  {
    Object[] parms = null;
    if (decompiled)
    {
      parms = new Object[additionalValues.length + 4];
      parms[0] = value;
      parms[1] = Integer.valueOf(ordinal);
      parms[2] = value;
      parms[3] = Integer.valueOf(ordinal);
      System.arraycopy(additionalValues, 0, parms, 4, additionalValues.length);
    }
    else
    {
      parms = new Object[additionalValues.length + 2];
      parms[0] = value;
      parms[1] = Integer.valueOf(ordinal);
      System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
    }
    // BTCS start
    /*return (Enum)enumClass.cast(newInstance.invoke(getConstructorAccessor(decompiled, enumClass, additionalTypes), new Object[] { parms }));*/
    return enumClass.cast(newInstance.invoke(getConstructorAccessor(decompiled, enumClass, additionalTypes), new Object[] {parms}));
    // BTCS end
  }
  
  public static void setFailsafeFieldValue(Field field, Object target, Object value) throws Exception
  {
    field.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
    Object fieldAccessor = newFieldAccessor.invoke(reflectionFactory, new Object[] { field, Boolean.valueOf(false) });
    fieldAccessorSet.invoke(fieldAccessor, new Object[] { target, value });
  }
  
  private static void blankField(Class<?> enumClass, String fieldName) throws Exception
  {
    for (Field field : Class.class.getDeclaredFields())
    {
      if (field.getName().contains(fieldName))
      {
        field.setAccessible(true);
        setFailsafeFieldValue(field, enumClass, null);
        break;
      }
    }
  }
  
  private static void cleanEnumCache(Class<?> enumClass) throws Exception
  {
    blankField(enumClass, "enumConstantDirectory");
    blankField(enumClass, "enumConstants");
  }
  
  public static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, boolean decompiled)
  {
    return addEnum(decompiled, enumType, enumName, new Class[0], new Object[0]);
  }
  

  public static <T extends Enum<?>> T addEnum(boolean decompiled, Class<T> enumType, String enumName, Class<?>[] paramTypes, Object[] paramValues)
  {
    if (!isSetup)
    {
      setup();
    }
    
    Field valuesField = null;
    Field[] fields = enumType.getDeclaredFields();
    int flags = 4122;
    String valueType = String.format("[L%s;", new Object[] { enumType.getName().replace('.', '/') });
    
    for (Field field : fields)
    {
      if (decompiled)
      {
        if (field.getName().contains("$VALUES"))
        {
          valuesField = field;
          break;
        }
        

      }
      else if (((field.getModifiers() & flags) == flags) && (field.getType().getName().replace('.', '/').equals(valueType)))
      {

        valuesField = field;
        break;
      }
    }
    
    valuesField.setAccessible(true);
    
    try
    {
      // BTCS start
      /*T[] previousValues = (Enum[])valuesField.get(enumType);*/
      T[] previousValues = (T[])valuesField.get(enumType);
      // BTCS end
      java.util.List<T> values = new java.util.ArrayList(java.util.Arrays.asList(previousValues));
      T newValue = makeEnum(decompiled, enumType, enumName, values.size(), paramTypes, paramValues);
      values.add(newValue);
      setFailsafeFieldValue(valuesField, null, values.toArray((Enum[])java.lang.reflect.Array.newInstance(enumType, 0)));
      cleanEnumCache(enumType);
      
      return newValue;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
  }
  
  static
  {
    if (!isSetup)
    {
      setup();
    }
  }
}
