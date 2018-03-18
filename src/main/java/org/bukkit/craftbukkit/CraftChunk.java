package org.bukkit.craftbukkit;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.ChunkSection;
import net.minecraft.server.EmptyChunk;
import net.minecraft.server.NibbleArray;
import net.minecraft.server.WorldChunkManager;
import net.minecraft.server.WorldServer;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlock;

public class CraftChunk implements org.bukkit.Chunk
{
  private WeakReference<net.minecraft.server.Chunk> weakChunk;
  private WorldServer worldServer;
  private int x;
  private int z;
  private static final byte[] emptyData = new byte[2048];
  private static final short[] emptyBlockIDs = new short[4096];
  private static final byte[] emptySkyLight = new byte[2048];
  
  public CraftChunk(net.minecraft.server.Chunk chunk) {
    if (!(chunk instanceof EmptyChunk)) {
      this.weakChunk = new WeakReference(chunk);
    }
    
    this.worldServer = (WorldServer) getHandle().world;
    this.x = getHandle().x;
    this.z = getHandle().z;
  }
  
  public org.bukkit.World getWorld() {
    nl.hypothermic.btcs.XLogger.generr("WS type: " + this.worldServer.getClass().getName());
    nl.hypothermic.btcs.XLogger.generr("RETR1: Getting world from worldServer...");
    org.bukkit.World w = this.worldServer.getWorld();
    nl.hypothermic.btcs.XLogger.generr("RETR1: Done");
    if (w == null) {
		nl.hypothermic.btcs.XLogger.gencrit("org.bukkit.World == null");
	} else {
		nl.hypothermic.btcs.XLogger.generr("org.bukkit.World != null");
	}
    return w;
  }
  
  public CraftWorld getCraftWorld() {
    return (CraftWorld)getWorld();
  }
  
  public net.minecraft.server.Chunk getHandle() {
    net.minecraft.server.Chunk c = (net.minecraft.server.Chunk)this.weakChunk.get();
    
    if (c == null) {
      c = this.worldServer.getChunkAt(this.x, this.z);
      
      if (!(c instanceof EmptyChunk)) {
        this.weakChunk = new WeakReference(c);
      }
    }
    
    return c;
  }
  
  void breakLink() {
    this.weakChunk.clear();
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getZ() {
    return this.z;
  }
  
  public String toString()
  {
    return "CraftChunk{x=" + getX() + "z=" + getZ() + '}';
  }
  
  public Block getBlock(int x, int y, int z) {
    return new CraftBlock(this, getX() << 4 | x & 0xF, y & 0xFF, getZ() << 4 | z & 0xF);
  }
  
  public org.bukkit.entity.Entity[] getEntities() {
    int count = 0;int index = 0;
    net.minecraft.server.Chunk chunk = getHandle();
    
    for (int i = 0; i < 16; i++) {
      count += chunk.entitySlices[i].size();
    }
    
    org.bukkit.entity.Entity[] entities = new org.bukkit.entity.Entity[count];
    
    for (int i = 0; i < 16; i++) {
      for (Object obj : chunk.entitySlices[i].toArray()) {
        if ((obj instanceof net.minecraft.server.Entity))
        {


          entities[(index++)] = ((net.minecraft.server.Entity)obj).getBukkitEntity();
        }
      }
    }
    return entities;
  }
  
  public BlockState[] getTileEntities() {
    int index = 0;
    net.minecraft.server.Chunk chunk = getHandle();
    BlockState[] entities = new BlockState[chunk.tileEntities.size()];
    
    for (Object obj : chunk.tileEntities.keySet().toArray())
      if ((obj instanceof ChunkPosition))
      {


        ChunkPosition position = (ChunkPosition)obj;
        entities[(index++)] = this.worldServer.getWorld().getBlockAt(position.x + (chunk.x << 4), position.y, position.z + (chunk.z << 4)).getState();
      }
    return entities;
  }
  
  public boolean isLoaded() {
    return getWorld().isChunkLoaded(this);
  }
  
  public boolean load() {
    return getWorld().loadChunk(getX(), getZ(), true);
  }
  
  public boolean load(boolean generate) {
    return getWorld().loadChunk(getX(), getZ(), generate);
  }
  
  public boolean unload() {
    return getWorld().unloadChunk(getX(), getZ());
  }
  
  public boolean unload(boolean save) {
    return getWorld().unloadChunk(getX(), getZ(), save);
  }
  
  public boolean unload(boolean save, boolean safe) {
    return getWorld().unloadChunk(getX(), getZ(), save, safe);
  }
  
  public ChunkSnapshot getChunkSnapshot() {
    return getChunkSnapshot(true, false, false);
  }
  
  public ChunkSnapshot getChunkSnapshot(boolean includeMaxBlockY, boolean includeBiome, boolean includeBiomeTempRain) {
    net.minecraft.server.Chunk chunk = getHandle();
    
    ChunkSection[] cs = chunk.h();
    short[][] sectionBlockIDs = new short[cs.length][];
    byte[][] sectionBlockData = new byte[cs.length][];
    byte[][] sectionSkyLights = new byte[cs.length][];
    byte[][] sectionEmitLights = new byte[cs.length][];
    boolean[] sectionEmpty = new boolean[cs.length];
    
    for (int i = 0; i < cs.length; i++) {
      if (cs[i] == null) {
        sectionBlockIDs[i] = emptyBlockIDs;
        sectionBlockData[i] = emptyData;
        sectionSkyLights[i] = emptySkyLight;
        sectionEmitLights[i] = emptyData;
        sectionEmpty[i] = true;
      } else {
        short[] blockids = new short['က'];
        byte[] baseids = cs[i].g();
        

        for (int j = 0; j < 4096; j++) {
          blockids[j] = ((short)(baseids[j] & 0xFF));
        }
        
        if (cs[i].h() != null) {
          byte[] extids = cs[i].h().a;
          
          for (int j = 0; j < 2048; j++) {
            short b = (short)(extids[j] & 0xFF);
            
            if (b != 0)
            {


              int tmp222_221 = (j << 1); short[] tmp222_216 = blockids;tmp222_216[tmp222_221] = ((short)(tmp222_216[tmp222_221] | (b & 0xF) << 8)); int 
                tmp243_242 = ((j << 1) + 1); short[] tmp243_235 = blockids;tmp243_235[tmp243_242] = ((short)(tmp243_235[tmp243_242] | (b & 0xF0) << 4));
            }
          }
        }
        sectionBlockIDs[i] = blockids;
        

        sectionBlockData[i] = new byte['ࠀ'];
        System.arraycopy(cs[i].i().a, 0, sectionBlockData[i], 0, 2048);
        sectionSkyLights[i] = new byte['ࠀ'];
        System.arraycopy(cs[i].k().a, 0, sectionSkyLights[i], 0, 2048);
        sectionEmitLights[i] = new byte['ࠀ'];
        System.arraycopy(cs[i].j().a, 0, sectionEmitLights[i], 0, 2048);
      }
    }
    
    int[] hmap = null;
    
    if (includeMaxBlockY) {
      hmap = new int['Ā'];
      System.arraycopy(chunk.heightMap, 0, hmap, 0, 256);
    }
    
    BiomeBase[] biome = null;
    double[] biomeTemp = null;
    double[] biomeRain = null;
    
    if ((includeBiome) || (includeBiomeTempRain)) {
      WorldChunkManager wcm = chunk.world.getWorldChunkManager();
      
      if (includeBiome) {
        biome = new BiomeBase['Ā'];
        for (int i = 0; i < 256; i++) {
          biome[i] = chunk.a(i & 0xF, i >> 4, wcm);
        }
      }
      
      if (includeBiomeTempRain) {
        biomeTemp = new double['Ā'];
        biomeRain = new double['Ā'];
        float[] dat = wcm.getTemperatures((float[])null, getX() << 4, getZ() << 4, 16, 16);
        
        for (int i = 0; i < 256; i++) {
          biomeTemp[i] = dat[i];
        }
        
        dat = wcm.getWetness((float[])null, getX() << 4, getZ() << 4, 16, 16);
        
        for (int i = 0; i < 256; i++) {
          biomeRain[i] = dat[i];
        }
      }
    }
    
    org.bukkit.World world = getWorld();
    return new CraftChunkSnapshot(getX(), getZ(), world.getName(), world.getFullTime(), sectionBlockIDs, sectionBlockData, sectionSkyLights, sectionEmitLights, sectionEmpty, hmap, biome, biomeTemp, biomeRain);
  }
  
  public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
    BiomeBase[] biome = null;
    double[] biomeTemp = null;
    double[] biomeRain = null;
    
    if ((includeBiome) || (includeBiomeTempRain)) {
      WorldChunkManager wcm = world.getHandle().getWorldChunkManager();
      
      if (includeBiome) {
        biome = new BiomeBase['Ā'];
        for (int i = 0; i < 256; i++) {
          biome[i] = world.getHandle().getBiome((x << 4) + (i & 0xF), (z << 4) + (i >> 4));
        }
      }
      
      if (includeBiomeTempRain) {
        biomeTemp = new double['Ā'];
        biomeRain = new double['Ā'];
        float[] dat = wcm.getTemperatures((float[])null, x << 4, z << 4, 16, 16);
        
        for (int i = 0; i < 256; i++) {
          biomeTemp[i] = dat[i];
        }
        
        dat = wcm.getWetness((float[])null, x << 4, z << 4, 16, 16);
        
        for (int i = 0; i < 256; i++) {
          biomeRain[i] = dat[i];
        }
      }
    }
    

    int hSection = world.getMaxHeight() >> 4;
    short[][] blockIDs = new short[hSection][];
    byte[][] skyLight = new byte[hSection][];
    byte[][] emitLight = new byte[hSection][];
    byte[][] blockData = new byte[hSection][];
    boolean[] empty = new boolean[hSection];
    
    for (int i = 0; i < hSection; i++) {
      blockIDs[i] = emptyBlockIDs;
      skyLight[i] = emptySkyLight;
      emitLight[i] = emptyData;
      blockData[i] = emptyData;
      empty[i] = true;
    }
    
    return new CraftChunkSnapshot(x, z, world.getName(), world.getFullTime(), blockIDs, blockData, skyLight, emitLight, empty, new int['Ā'], biome, biomeTemp, biomeRain);
  }
  
  static {
    Arrays.fill(emptySkyLight, (byte)-1);
  }
}
