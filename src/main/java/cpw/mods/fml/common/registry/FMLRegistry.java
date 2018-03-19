package cpw.mods.fml.common.registry;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.IRecipe;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;

public class FMLRegistry
{
  private static IMinecraftRegistry instance;
  
  public static void registerRegistry(IMinecraftRegistry registry)
  {
    if (instance != null)
    {
      throw new RuntimeException("Illegal attempt to replace FML registry");
    }
    
    instance = registry;
  }
  
  public static void addRecipe(ItemStack output, Object... params) {
    instance.addRecipe(output, params);
  }
  
  public static void addShapelessRecipe(ItemStack output, Object... params)
  {
    instance.addShapelessRecipe(output, params);
  }
  
  public static void addRecipe(IRecipe recipe)
  {
    instance.addRecipe(recipe);
  }
  
  public static void addSmelting(int input, ItemStack output)
  {
    instance.addSmelting(input, output);
  }
  
  public static void registerBlock(Block block)
  {
    instance.registerBlock(block);
  }
  
  public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass)
  {
    instance.registerBlock(block, itemclass);
  }
  
  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id)
  {
    instance.registerEntityID(entityClass, entityName, id);
  }
  
  public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id, int backgroundEggColour, int foregroundEggColour)
  {
    instance.registerEntityID(entityClass, entityName, id, backgroundEggColour, foregroundEggColour);
  }
  
  public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
  {
    instance.registerTileEntity(tileEntityClass, id);
  }
  
  public static void addBiome(BiomeGenBase biome)
  {
    instance.addBiome(biome);
  }
  
  public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
  {
    instance.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
  }
  

  public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes)
  {
    instance.addSpawn(entityName, weightedProb, min, max, spawnList, biomes);
  }
  
  public static void removeBiome(BiomeGenBase biome)
  {
    instance.removeBiome(biome);
  }
  
  public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
  {
    instance.removeSpawn(entityClass, typeOfCreature, biomes);
  }
  

  public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase... biomes)
  {
    instance.removeSpawn(entityName, spawnList, biomes);
  }

  public static IMinecraftRegistry instance()
  {
    return instance;
  }
}
