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

public abstract interface IMinecraftRegistry
{
  public abstract void removeSpawn(String paramString, EnumCreatureType paramEnumCreatureType, BiomeGenBase... paramVarArgs);
  
  public abstract void removeSpawn(Class<? extends EntityLiving> paramClass, EnumCreatureType paramEnumCreatureType, BiomeGenBase... paramVarArgs);
  
  public abstract void removeBiome(BiomeGenBase paramBiomeGenBase);
  
  public abstract void addSpawn(String paramString, int paramInt1, int paramInt2, int paramInt3, EnumCreatureType paramEnumCreatureType, BiomeGenBase... paramVarArgs);
  
  public abstract void addSpawn(Class<? extends EntityLiving> paramClass, int paramInt1, int paramInt2, int paramInt3, EnumCreatureType paramEnumCreatureType, BiomeGenBase... paramVarArgs);
  
  public abstract void addBiome(BiomeGenBase paramBiomeGenBase);
  
  public abstract void registerTileEntity(Class<? extends TileEntity> paramClass, String paramString);
  
  public abstract void registerEntityID(Class<? extends Entity> paramClass, String paramString, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void registerEntityID(Class<? extends Entity> paramClass, String paramString, int paramInt);
  
  public abstract void registerBlock(Block paramBlock, Class<? extends ItemBlock> paramClass);
  
  public abstract void registerBlock(Block paramBlock);
  
  public abstract void addSmelting(int paramInt, ItemStack paramItemStack);
  
  public abstract void addShapelessRecipe(ItemStack paramItemStack, Object... paramVarArgs);
  
  public abstract void addRecipe(ItemStack paramItemStack, Object... paramVarArgs);
  
  public abstract void addRecipe(IRecipe paramIRecipe);
}
