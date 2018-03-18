package org.bukkit;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.util.Vector;

public abstract interface World
  extends PluginMessageRecipient, Metadatable
{
  public abstract Block getBlockAt(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Block getBlockAt(Location paramLocation);
  
  public abstract int getBlockTypeIdAt(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getBlockTypeIdAt(Location paramLocation);
  
  public abstract int getHighestBlockYAt(int paramInt1, int paramInt2);
  
  public abstract int getHighestBlockYAt(Location paramLocation);
  
  public abstract Block getHighestBlockAt(int paramInt1, int paramInt2);
  
  public abstract Block getHighestBlockAt(Location paramLocation);
  
  public abstract Chunk getChunkAt(int paramInt1, int paramInt2);
  
  public abstract Chunk getChunkAt(Location paramLocation);
  
  public abstract Chunk getChunkAt(Block paramBlock);
  
  public abstract boolean isChunkLoaded(Chunk paramChunk);
  
  public abstract Chunk[] getLoadedChunks();
  
  public abstract void loadChunk(Chunk paramChunk);
  
  public abstract boolean isChunkLoaded(int paramInt1, int paramInt2);
  
  public abstract void loadChunk(int paramInt1, int paramInt2);
  
  public abstract boolean loadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract boolean unloadChunk(Chunk paramChunk);
  
  public abstract boolean unloadChunk(int paramInt1, int paramInt2);
  
  public abstract boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean unloadChunkRequest(int paramInt1, int paramInt2);
  
  public abstract boolean unloadChunkRequest(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract boolean regenerateChunk(int paramInt1, int paramInt2);
  
  public abstract boolean refreshChunk(int paramInt1, int paramInt2);
  
  public abstract Item dropItem(Location paramLocation, ItemStack paramItemStack);
  
  public abstract Item dropItemNaturally(Location paramLocation, ItemStack paramItemStack);
  
  public abstract Arrow spawnArrow(Location paramLocation, Vector paramVector, float paramFloat1, float paramFloat2);
  
  public abstract boolean generateTree(Location paramLocation, TreeType paramTreeType);
  
  public abstract boolean generateTree(Location paramLocation, TreeType paramTreeType, BlockChangeDelegate paramBlockChangeDelegate);
  
  public abstract Entity spawnEntity(Location paramLocation, EntityType paramEntityType);
  
  @Deprecated
  public abstract LivingEntity spawnCreature(Location paramLocation, EntityType paramEntityType);
  
  @Deprecated
  public abstract LivingEntity spawnCreature(Location paramLocation, CreatureType paramCreatureType);
  
  public abstract LightningStrike strikeLightning(Location paramLocation);
  
  public abstract LightningStrike strikeLightningEffect(Location paramLocation);
  
  public abstract List<Entity> getEntities();
  
  public abstract List<LivingEntity> getLivingEntities();
  
  @Deprecated
  public abstract <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... paramVarArgs);
  
  public abstract <T extends Entity> Collection<T> getEntitiesByClass(Class<T> paramClass);
  
  public abstract Collection<Entity> getEntitiesByClasses(Class<?>... paramVarArgs);
  
  public abstract List<Player> getPlayers();
  
  public abstract String getName();
  
  public abstract UUID getUID();
  
  public abstract Location getSpawnLocation();
  
  public abstract boolean setSpawnLocation(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract long getTime();
  
  public abstract void setTime(long paramLong);
  
  public abstract long getFullTime();
  
  public abstract void setFullTime(long paramLong);
  
  public abstract boolean hasStorm();
  
  public abstract void setStorm(boolean paramBoolean);
  
  public abstract int getWeatherDuration();
  
  public abstract void setWeatherDuration(int paramInt);
  
  public abstract boolean isThundering();
  
  public abstract void setThundering(boolean paramBoolean);
  
  public abstract int getThunderDuration();
  
  public abstract void setThunderDuration(int paramInt);
  
  public abstract boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);
  
  public abstract boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean);
  
  public abstract boolean createExplosion(Location paramLocation, float paramFloat);
  
  public abstract boolean createExplosion(Location paramLocation, float paramFloat, boolean paramBoolean);
  
  public abstract Environment getEnvironment();
  
  public abstract long getSeed();
  
  public abstract boolean getPVP();
  
  public abstract void setPVP(boolean paramBoolean);
  
  public abstract ChunkGenerator getGenerator();
  
  public abstract void save();
  
  public abstract List<BlockPopulator> getPopulators();
  
  public abstract <T extends Entity> T spawn(Location paramLocation, Class<T> paramClass)
    throws IllegalArgumentException;
  
  public abstract void playEffect(Location paramLocation, Effect paramEffect, int paramInt);
  
  public abstract void playEffect(Location paramLocation, Effect paramEffect, int paramInt1, int paramInt2);
  
  public abstract <T> void playEffect(Location paramLocation, Effect paramEffect, T paramT);
  
  public abstract <T> void playEffect(Location paramLocation, Effect paramEffect, T paramT, int paramInt);
  
  public abstract ChunkSnapshot getEmptyChunkSnapshot(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void setSpawnFlags(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean getAllowAnimals();
  
  public abstract boolean getAllowMonsters();
  
  public abstract Biome getBiome(int paramInt1, int paramInt2);
  
  public abstract void setBiome(int paramInt1, int paramInt2, Biome paramBiome);
  
  public abstract double getTemperature(int paramInt1, int paramInt2);
  
  public abstract double getHumidity(int paramInt1, int paramInt2);
  
  public abstract int getMaxHeight();
  
  public abstract int getSeaLevel();
  
  public abstract boolean getKeepSpawnInMemory();
  
  public abstract void setKeepSpawnInMemory(boolean paramBoolean);
  
  public abstract boolean isAutoSave();
  
  public abstract void setAutoSave(boolean paramBoolean);
  
  public abstract void setDifficulty(Difficulty paramDifficulty);
  
  public abstract Difficulty getDifficulty();
  
  public abstract File getWorldFolder();
  
  public abstract WorldType getWorldType();
  
  public abstract boolean canGenerateStructures();
  
  public abstract long getTicksPerAnimalSpawns();
  
  public abstract void setTicksPerAnimalSpawns(int paramInt);
  
  public abstract long getTicksPerMonsterSpawns();
  
  public abstract void setTicksPerMonsterSpawns(int paramInt);
  
  public abstract int getMonsterSpawnLimit();
  
  public abstract void setMonsterSpawnLimit(int paramInt);
  
  public abstract int getAnimalSpawnLimit();
  
  public abstract void setAnimalSpawnLimit(int paramInt);
  
  public abstract int getWaterAnimalSpawnLimit();
  
  public abstract void setWaterAnimalSpawnLimit(int paramInt);
  
  public static enum Environment
  {
    NORMAL(0), 
    


    NETHER(-1), 
    


    THE_END(1);
    
    private final int id;
    private static final Map<Integer, Environment> lookup;
    
    private Environment(int id) {
      this.id = id;
    }
    




    public int getId()
    {
      return this.id;
    }
    




    public static Environment getEnvironment(int id)
    {
      return (Environment)lookup.get(Integer.valueOf(id));
    }
    
    static
    {
      lookup = new HashMap();
      























      for (Environment env : values()) {
        lookup.put(Integer.valueOf(env.getId()), env);
      }
    }
    
    public static void registerEnvironment(Environment env) {
      lookup.put(Integer.valueOf(env.getId()), env);
    }
  }
}
