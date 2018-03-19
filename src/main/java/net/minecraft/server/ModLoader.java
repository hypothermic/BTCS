package net.minecraft.server;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ReflectionHelper;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.registry.FMLRegistry;
import cpw.mods.fml.common.registry.IMinecraftRegistry;
import cpw.mods.fml.server.FMLBukkitHandler;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class ModLoader
{
  public static void addAchievementDesc(Achievement achievement, String name, String description)
  {
    String achName = achievement.getName();
    addLocalization(achName, name);
    addLocalization(achName + ".desc", description);
  }

  @Deprecated
  public static int addAllFuel(int id, int metadata)
  {
    return 0;
  }

  public static void addAllRenderers(Map<Class<? extends Entity>, Object> renderers) {}

  public static void addAnimation(Object anim) {}

  public static int addArmor(String armor)
  {
    return 0;
  }

  public static void addBiome(BiomeBase biome)
  {
    FMLRegistry.addBiome(biome);
  }

  public static void addLocalization(String key, String value)
  {
    addLocalization(key, "en_US", value);
  }

  public static void addLocalization(String key, String lang, String value)
  {
    FMLCommonHandler.instance().addStringLocalization(key, lang, value);
  }

  public static void addName(Object instance, String name)
  {
    addName(instance, "en_US", name);
  }

  public static void addName(Object instance, String lang, String name)
  {
    FMLCommonHandler.instance().addNameForObject(instance, lang, name);
  }

  public static int addOverride(String fileToOverride, String fileToAdd)
  {
    return 0;
  }

  public static void addOverride(String path, String overlayPath, int index) {}

  public static void addRecipe(ItemStack output, Object... params)
  {
    FMLRegistry.addRecipe(output, params);
  }

  public static void addShapelessRecipe(ItemStack output, Object... params)
  {
    FMLRegistry.addShapelessRecipe(output, params);
  }

  public static void addSmelting(int input, ItemStack output)
  {
    FMLRegistry.addSmelting(input, output);
  }

  public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList)
  {
    FMLRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
  }
  
  public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeBase... biomes)
  {
    FMLRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, biomes);
  }

  public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList)
  {
    FMLRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
  }

  public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeBase... biomes)
  {
    FMLRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, biomes);
  }
  













  @Deprecated
  public static boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item)
  {
    return false;
  }
  








  public static void genericContainerRemoval(World world, int x, int y, int z)
  {
    TileEntity te = world.getTileEntity(x, y, z);
    
    if (!(te instanceof IInventory))
    {
      return;
    }
    
    IInventory inv = (IInventory)te;
    
    for (int l = 0; l < inv.getSize(); l++)
    {
      ItemStack itemstack = inv.getItem(l);
      
      if (itemstack != null)
      {



        float f = world.random.nextFloat() * 0.8F + 0.1F;
        float f1 = world.random.nextFloat() * 0.8F + 0.1F;
        float f2 = world.random.nextFloat() * 0.8F + 0.1F;
        
        while (itemstack.count > 0)
        {
          int i1 = world.random.nextInt(21) + 10;
          
          if (i1 > itemstack.count)
          {
            i1 = itemstack.count;
          }
          
          itemstack.count -= i1;
          EntityItem entityitem = new EntityItem(world, te.x + f, te.y + f1, te.z + f2, new ItemStack(itemstack.id, i1, itemstack.getData()));
          float f3 = 0.05F;
          entityitem.motX = ((float)world.random.nextGaussian() * f3);
          entityitem.motY = ((float)world.random.nextGaussian() * f3 + 0.2F);
          entityitem.motZ = ((float)world.random.nextGaussian() * f3);
          
          if (itemstack.hasTag())
          {
            entityitem.itemStack.setTag((NBTTagCompound)itemstack.getTag().clone());
          }
          
          world.addEntity(entityitem);
        }
      }
    }
  }
  





  public static List<BaseMod> getLoadedMods()
  {
    return ModLoaderModContainer.findAll(BaseMod.class);
  }
  





  public static Logger getLogger()
  {
    return FMLCommonHandler.instance().getFMLLogger();
  }
  
  public static Object getMinecraftInstance()
  {
    return getMinecraftServerInstance();
  }
  





  public static MinecraftServer getMinecraftServerInstance()
  {
    return FMLBukkitHandler.instance().getServer();
  }
  








  public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, int fieldindex)
  {
    return (T)ReflectionHelper.getPrivateValue(instanceclass, instance, fieldindex);
  }
  









  public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, String field)
  {
    return (T)ReflectionHelper.getPrivateValue(instanceclass, instance, field);
  }
  




  public static int getUniqueBlockModelID(BaseMod mod, boolean inventoryRenderer)
  {
    return -1;
  }
  






  public static int getUniqueEntityId()
  {
    return FMLCommonHandler.instance().nextUniqueEntityListId();
  }
  
  public static int getUniqueSpriteIndex(String path)
  {
    return -1;
  }
  








  public static boolean isChannelActive(EntityPlayer player, String channel)
  {
    return FMLCommonHandler.instance().isChannelActive(channel, player);
  }
  
  public static boolean isGUIOpen(Class<?> gui)
  {
    return false;
  }
  







  public static boolean isModLoaded(String modname)
  {
    return Loader.isModLoaded(modname);
  }
  


  @Deprecated
  public static void loadConfig() {}
  


  public static Object loadImage(Object renderEngine, String path)
    throws Exception
  {
    return null;
  }
  




  @Deprecated
  public static void onItemPickup(EntityPlayer player, ItemStack item) {}
  




  @Deprecated
  public static void onTick(float tick, Object game) {}
  




  public static void openGUI(EntityPlayer player, Object gui) {}
  




  @Deprecated
  public static void populateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world) {}
  




  @Deprecated
  public static void receivePacket(Packet250CustomPayload packet) {}
  




  @Deprecated
  public static Object[] registerAllKeys(Object[] keys)
  {
    return keys;
  }
  




  @Deprecated
  public static void registerAllTextureOverrides(Object cache) {}
  



  public static void registerBlock(Block block)
  {
    FMLRegistry.registerBlock(block);
  }

  public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass)
  {
    FMLRegistry.registerBlock(block, itemclass);
  }

  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id)
  {
    FMLRegistry.registerEntityID(entityClass, entityName, id);
  }

  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id, int background, int foreground)
  {
    FMLRegistry.registerEntityID(entityClass, entityName, id, background, foreground);
  }

  public static void registerKey(BaseMod mod, Object keyHandler, boolean allowRepeat) {}

  public static void registerPacketChannel(BaseMod mod, String channel)
  {
    FMLCommonHandler.instance().registerChannel(ModLoaderModContainer.findContainerFor(mod), channel);
  }

  public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
  {
    FMLRegistry.registerTileEntity(tileEntityClass, id);
  }
  
  public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id, Object renderer)
  {
    FMLRegistry.instance().registerTileEntity(tileEntityClass, id);
  }

  public static void removeBiome(BiomeBase biome)
  {
    FMLRegistry.removeBiome(biome);
  }

  public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList)
  {
    FMLRegistry.removeSpawn(entityClass, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
  }

  public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList, BiomeBase... biomes)
  {
    FMLRegistry.removeSpawn(entityClass, spawnList, biomes);
  }

  public static void removeSpawn(String entityName, EnumCreatureType spawnList)
  {
    FMLRegistry.removeSpawn(entityName, spawnList, FMLBukkitHandler.instance().getDefaultOverworldBiomes());
  }

  public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeBase... biomes)
  {
    FMLRegistry.removeSpawn(entityName, spawnList, biomes);
  }
  
  @Deprecated
  public static boolean renderBlockIsItemFull3D(int modelID)
  {
    return false;
  }

  @Deprecated
  public static void renderInvBlock(Object renderer, Block block, int metadata, int modelID) {}
  

  @Deprecated
  public static boolean renderWorldBlock(Object renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID)
  {
    return false;
  }

  @Deprecated
  public static void saveConfig() {}

  @Deprecated
  public static void serverChat(String text) {}

  @Deprecated
  public static void serverLogin(Object handler, Packet1Login loginPacket) {}

  public static void setInGameHook(BaseMod mod, boolean enable, boolean useClock)
  {
    ModLoaderHelper.updateStandardTicks(mod, enable, useClock);
  }
  

  public static void setInGUIHook(BaseMod mod, boolean enable, boolean useClock)
  {
    ModLoaderHelper.updateGUITicks(mod, enable, useClock);
  }

  public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, int fieldindex, E value)
  {
    ReflectionHelper.setPrivateValue(instanceclass, instance, fieldindex, value);
  }

  public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, String field, E value)
  {
    ReflectionHelper.setPrivateValue(instanceclass, instance, field, value);
  }

  @Deprecated
  public static void takenFromCrafting(EntityHuman player, ItemStack item, IInventory matrix) {}

  @Deprecated
  public static void takenFromFurnace(EntityHuman player, ItemStack item) {}

  public static void throwException(String message, Throwable e)
  {
    FMLBukkitHandler.instance().raiseException(e, message, true);
  }

  public static boolean isChannelActive(EntityHuman player, String channel)
  {
    return FMLCommonHandler.instance().isChannelActive(channel, player);
  }
}
