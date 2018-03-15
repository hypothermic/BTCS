package org.bukkit.craftbukkit.generator;

import java.util.List;
import java.util.Random;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.ChunkSection;
import net.minecraft.server.EnumCreatureType;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.WorldChunkManager;
import net.minecraft.server.WorldGenStronghold;
import net.minecraft.server.WorldServer;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class CustomChunkGenerator extends InternalChunkGenerator
{
  private final ChunkGenerator generator;
  private final WorldServer world;
  private final Random random;
  private final WorldGenStronghold strongholdGen = new WorldGenStronghold();
  
  private static class CustomBiomeGrid implements ChunkGenerator.BiomeGrid {
    BiomeBase[] biome;
    
    public Biome getBiome(int x, int z) {
      return CraftBlock.biomeBaseToBiome(this.biome[(z << 4 | x)]);
    }
    
    public void setBiome(int x, int z, Biome bio) {
      this.biome[(z << 4 | x)] = CraftBlock.biomeToBiomeBase(bio);
    }
  }
  
  public CustomChunkGenerator(net.minecraft.server.World world, long seed, ChunkGenerator generator) {
    this.world = ((WorldServer)world);
    this.generator = generator;
    
    this.random = new Random(seed);
  }
  
  public boolean isChunkLoaded(int x, int z) {
    return true;
  }
  
  public Chunk getOrCreateChunk(int x, int z) {
    this.random.setSeed(x * 341873128712L + z * 132897987541L);
    
    Chunk chunk;
    
    CustomBiomeGrid biomegrid = new CustomBiomeGrid();
    biomegrid.biome = new BiomeBase[256];
    this.world.getWorldChunkManager().getBiomeBlock(biomegrid.biome, x << 4, z << 4, 16, 16);
    

    short[][] xbtypes = this.generator.generateExtBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
    if (xbtypes != null) {
      chunk = new Chunk(this.world, x, z);
      
      ChunkSection[] csect = chunk.h();
      int scnt = Math.min(csect.length, xbtypes.length);
      

      for (int sec = 0; sec < scnt; sec++) {
        if (xbtypes[sec] != null)
        {

          byte[] secBlkID = new byte[4096];
          byte[] secExtBlkID = (byte[])null;
          short[] bdata = xbtypes[sec];
          
          int i = 0; for (int j = 0; i < bdata.length; j++) {
            short b1 = bdata[i];
            short b2 = bdata[(i + 1)];
            byte extb = (byte)(b1 >> 8 | b2 >> 4 & 0xF0);
            
            secBlkID[i] = ((byte)b1);
            secBlkID[(i + 1)] = ((byte)b2);
            
            if (extb != 0) {
              if (secExtBlkID == null) {
                secExtBlkID = new byte[2048];
              }
              secExtBlkID[j] = extb;
            }
            i += 2;
          }
          csect[sec] = new ChunkSection(sec << 4, secBlkID, secExtBlkID);
        }
      }
    } else {
      byte[][] btypes = this.generator.generateBlockSections(this.world.getWorld(), this.random, x, z, biomegrid);
      
      if (btypes != null) {
        chunk = new Chunk(this.world, x, z);
        
        ChunkSection[] csect = chunk.h();
        int scnt = Math.min(csect.length, btypes.length);
        
        for (int sec = 0; sec < scnt; sec++) {
          if (btypes[sec] != null)
          {

            csect[sec] = new ChunkSection(sec << 4, btypes[sec], null);
          }
        }
      }
      else {
        byte[] types = this.generator.generate(this.world.getWorld(), this.random, x, z);
        int ydim = types.length / 256;
        int scnt = ydim / 16;
        
        chunk = new Chunk(this.world, x, z);
        
        ChunkSection[] csect = chunk.h();
        
        scnt = Math.min(scnt, csect.length);
        
        for (int sec = 0; sec < scnt; sec++) {
          ChunkSection cs = null;
          byte[] csbytes = (byte[])null;
          
          for (int cy = 0; cy < 16; cy++) {
            int cyoff = cy | sec << 4;
            
            for (int cx = 0; cx < 16; cx++) {
              int cxyoff = cx * ydim * 16 + cyoff;
              
              for (int cz = 0; cz < 16; cz++) {
                byte blk = types[(cxyoff + cz * ydim)];
                
                if (blk != 0) {
                  if (cs == null) {
                    cs = csect[sec] = new ChunkSection(sec << 4);
                    csbytes = cs.g();
                  }
                  csbytes[(cy << 8 | cz << 4 | cx)] = blk;
                }
              }
            }
          }
          
          if (cs != null) {
            cs.d();
          }
        }
      }
    }
    
    byte[] biomeIndex = chunk.l();
    for (int i = 0; i < biomeIndex.length; i++) {
      biomeIndex[i] = ((byte)(biomegrid.biome[i].id & 0xFF));
    }
    
    chunk.initLighting();
    
    return chunk;
  }
  

  public void getChunkAt(IChunkProvider icp, int i, int i1) {}
  
  public boolean saveChunks(boolean bln, IProgressUpdate ipu)
  {
    return true;
  }
  
  public boolean unloadChunks() {
    return false;
  }
  
  public boolean canSave() {
    return true;
  }
  
  public byte[] generate(org.bukkit.World world, Random random, int x, int z)
  {
    return this.generator.generate(world, random, x, z);
  }
  
  public byte[][] generateBlockSections(org.bukkit.World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
    return this.generator.generateBlockSections(world, random, x, z, biomes);
  }
  
  public short[][] generateExtBlockSections(org.bukkit.World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
    return this.generator.generateExtBlockSections(world, random, x, z, biomes);
  }
  
  public Chunk getChunkAt(int x, int z) {
    return getOrCreateChunk(x, z);
  }
  
  public boolean canSpawn(org.bukkit.World world, int x, int z)
  {
    return this.generator.canSpawn(world, x, z);
  }
  
  public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world)
  {
    return this.generator.getDefaultPopulators(world);
  }
  
  public List<?> getMobsFor(EnumCreatureType type, int x, int y, int z) {
    BiomeBase biomebase = this.world.getBiome(x, z);
    
    return biomebase == null ? null : biomebase.getMobs(type);
  }
  
  public ChunkPosition findNearestMapFeature(net.minecraft.server.World world, String type, int x, int y, int z) {
    return ("Stronghold".equals(type)) && (this.strongholdGen != null) ? this.strongholdGen.getNearestGeneratedFeature(world, x, y, z) : null;
  }
}
