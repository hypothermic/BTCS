package org.bukkit.craftbukkit;

import net.minecraft.server.BiomeBase;
import org.bukkit.ChunkSnapshot;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.block.CraftBlock;


public class CraftChunkSnapshot
  implements ChunkSnapshot
{
  private final int x;
  private final int z;
  private final String worldname;
  private final short[][] blockids;
  private final byte[][] blockdata;
  private final byte[][] skylight;
  private final byte[][] emitlight;
  private final boolean[] empty;
  private final int[] hmap;
  private final long captureFulltime;
  private final BiomeBase[] biome;
  private final double[] biomeTemp;
  private final double[] biomeRain;
  
  CraftChunkSnapshot(int x, int z, String wname, long wtime, short[][] sectionBlockIDs, byte[][] sectionBlockData, byte[][] sectionSkyLights, byte[][] sectionEmitLights, boolean[] sectionEmpty, int[] hmap, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain)
  {
    this.x = x;
    this.z = z;
    this.worldname = wname;
    this.captureFulltime = wtime;
    this.blockids = sectionBlockIDs;
    this.blockdata = sectionBlockData;
    this.skylight = sectionSkyLights;
    this.emitlight = sectionEmitLights;
    this.empty = sectionEmpty;
    this.hmap = hmap;
    this.biome = biome;
    this.biomeTemp = biomeTemp;
    this.biomeRain = biomeRain;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getZ() {
    return this.z;
  }
  
  public String getWorldName() {
    return this.worldname;
  }
  
  public final int getBlockTypeId(int x, int y, int z) {
    return this.blockids[(y >> 4)][((y & 0xF) << 8 | z << 4 | x)];
  }
  
  public final int getBlockData(int x, int y, int z) {
    int off = (y & 0xF) << 7 | z << 3 | x >> 1;
    return this.blockdata[(y >> 4)][off] >> ((x & 0x1) << 2) & 0xF;
  }
  
  public final int getBlockSkyLight(int x, int y, int z) {
    int off = (y & 0xF) << 7 | z << 3 | x >> 1;
    return this.skylight[(y >> 4)][off] >> ((x & 0x1) << 2) & 0xF;
  }
  
  public final int getBlockEmittedLight(int x, int y, int z) {
    int off = (y & 0xF) << 7 | z << 3 | x >> 1;
    return this.emitlight[(y >> 4)][off] >> ((x & 0x1) << 2) & 0xF;
  }
  
  public final int getHighestBlockYAt(int x, int z) {
    return this.hmap[(z << 4 | x)];
  }
  
  public final Biome getBiome(int x, int z) {
    return CraftBlock.biomeBaseToBiome(this.biome[(z << 4 | x)]);
  }
  
  public final double getRawBiomeTemperature(int x, int z) {
    return this.biomeTemp[(z << 4 | x)];
  }
  
  public final double getRawBiomeRainfall(int x, int z) {
    return this.biomeRain[(z << 4 | x)];
  }
  
  public final long getCaptureFullTime() {
    return this.captureFulltime;
  }
  
  public final boolean isSectionEmpty(int sy) {
    return this.empty[sy];
  }
}
