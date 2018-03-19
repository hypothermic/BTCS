package net.minecraft.server;

import cpw.mods.fml.common.registry.IMinecraftRegistry;
import cpw.mods.fml.server.FMLBukkitHandler;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IRecipe;

public class BukkitRegistry implements IMinecraftRegistry {
  public void addRecipe(net.minecraft.src.ItemStack output, Object... params)
  {
    CraftingManager.getInstance().registerShapedRecipe((ItemStack)output, params);
  }

  public void addShapelessRecipe(net.minecraft.src.ItemStack output, Object... params)
  {
    CraftingManager.getInstance().registerShapelessRecipe((ItemStack)output, params);
  }

  public void addRecipe(IRecipe recipe)
  {
    CraftingManager.getInstance().getRecipies().add(recipe);
  }

  public void addSmelting(int input, net.minecraft.src.ItemStack output)
  {
    FurnaceRecipes.getInstance().registerRecipe(input, (ItemStack)output);
  }

  public void registerBlock(net.minecraft.src.Block block)
  {
    registerBlock(block, ItemBlock.class);
  }

  public void registerBlock(net.minecraft.src.Block block, Class<? extends net.minecraft.src.ItemBlock> itemclass)
  {
    try
    {
      assert (block != null) : "registerBlock: block cannot be null";
      assert (itemclass != null) : "registerBlock: itemclass cannot be null";
      int blockItemId = ((Block)block).id - 256;
      itemclass.getConstructor(new Class[] { Integer.TYPE }).newInstance(new Object[] { Integer.valueOf(blockItemId) });
    }
    catch (Exception e) {}
  }
  
  public void registerEntityID(Class<? extends net.minecraft.src.Entity> entityClass, String entityName, int id)
  {
    EntityTypes.addNewEntityListMapping((Class<? extends Entity>) entityClass, entityName, id); // BTCS: added cast (Class<? extends Entity>)
  }

  public void registerEntityID(Class<? extends net.minecraft.src.Entity> entityClass, String entityName, int id, int backgroundEggColour, int foregroundEggColour)
  {
    EntityTypes.addNewEntityListMapping((Class<? extends Entity>) entityClass, entityName, id, backgroundEggColour, foregroundEggColour); // BTCS: added cast (Class<? extends Entity>)
  }
  
  public void registerTileEntity(Class<? extends net.minecraft.src.TileEntity> tileEntityClass, String id)
  {
    TileEntity.addNewTileEntityMapping((Class<? extends TileEntity>) tileEntityClass, id); // BTCS: added cast (Class<? extends TileEntity>)
  }

  public void addBiome(BiomeGenBase biome)
  {
    FMLBukkitHandler.instance().addBiomeToDefaultWorldGenerator((BiomeBase)biome);
  }

  public void addSpawn(Class<? extends net.minecraft.src.EntityLiving> entityClass, int weightedProb, int min, int max, net.minecraft.src.EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
  {
    BiomeBase[] realBiomes = (BiomeBase[])biomes;
    for (BiomeBase biome : realBiomes)
    {

      List<BiomeMeta> spawns = biome.getMobs((EnumCreatureType)typeOfCreature);
      
      for (BiomeMeta entry : spawns)
      {

        if (entry.a == entityClass)
        {
          entry.d = weightedProb;
          entry.b = min;
          entry.c = max;
          break;
        }
      }
      
      spawns.add(new BiomeMeta(entityClass, weightedProb, min, max));
    }
  }

  public void addSpawn(String entityName, int weightedProb, int min, int max, net.minecraft.src.EnumCreatureType spawnList, BiomeGenBase... biomes)
  {
    Class<? extends Entity> entityClazz = (Class) EntityTypes.getEntityToClassMapping().get(entityName);
    
    if (EntityLiving.class.isAssignableFrom(entityClazz))
    {
      addSpawn((Class<? extends net.minecraft.src.EntityLiving>) entityClazz, weightedProb, min, max, spawnList, biomes); // BTCS: added cast (Class<? extends net.minecraft.src.EntityLiving>)
    }
  }

  public void removeBiome(BiomeGenBase biome)
  {
    FMLBukkitHandler.instance().removeBiomeFromDefaultWorldGenerator((BiomeBase)biome);
  }

  public void removeSpawn(Class<? extends net.minecraft.src.EntityLiving> entityClass, net.minecraft.src.EnumCreatureType typeOfCreature, BiomeGenBase... biomesO)
  {
    BiomeBase[] biomes = (BiomeBase[])biomesO;
    for (BiomeBase biome : biomes)
    {

      List<BiomeMeta> spawns = biome.getMobs((EnumCreatureType)typeOfCreature);
      
      Iterator<BiomeMeta> entries = spawns.iterator();
      while (entries.hasNext())
      {
        BiomeMeta entry = (BiomeMeta)entries.next();
        if (entry.a == entityClass)
        {
          entries.remove();
        }
      }
    }
  }

  public void removeSpawn(String entityName, net.minecraft.src.EnumCreatureType spawnList, BiomeGenBase... biomes)
  {
    Class<? extends Entity> entityClazz = (Class)EntityTypes.getEntityToClassMapping().get(entityName);
    
    if (EntityLiving.class.isAssignableFrom(entityClazz))
    {
      removeSpawn((Class<? extends net.minecraft.src.EntityLiving>) entityClazz, spawnList, biomes); // BTCS: added cast (Class<? extends net.minecraft.src.EntityLiving>)
    }
  }
}
