package org.bukkit.craftbukkit.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.Chunk;
import net.minecraft.server.EnumCreatureType;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class NormalChunkGenerator extends InternalChunkGenerator
{
  private final IChunkProvider provider;
  
  public NormalChunkGenerator(net.minecraft.server.World world, long seed)
  {
    this.provider = world.worldProvider.getChunkProvider();
  }
  
  public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
    throw new UnsupportedOperationException("Not supported.");
  }
  
  public boolean canSpawn(org.bukkit.World world, int x, int z) {
    return ((CraftWorld)world).getHandle().worldProvider.canSpawn(x, z);
  }
  
  public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
    return new ArrayList();
  }
  
  public boolean isChunkLoaded(int i, int i1) {
    return this.provider.isChunkLoaded(i, i1);
  }
  
  public Chunk getOrCreateChunk(int i, int i1) {
	nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 360");
	nl.hypothermic.btcs.XLogger.debug(" ChunckProvidor = " + this.provider.getClass().getName());
    return this.provider.getOrCreateChunk(i, i1);
  }
  
  public Chunk getChunkAt(int i, int i1) {
    return this.provider.getChunkAt(i, i1);
  }
  
  public void getChunkAt(IChunkProvider icp, int i, int i1) {
    this.provider.getChunkAt(icp, i, i1);
  }
  
  public boolean saveChunks(boolean bln, IProgressUpdate ipu) {
    return this.provider.saveChunks(bln, ipu);
  }
  
  public boolean unloadChunks() {
    return this.provider.unloadChunks();
  }
  
  public boolean canSave() {
    return this.provider.canSave();
  }
  
  public List<?> getMobsFor(EnumCreatureType ect, int i, int i1, int i2) {
    return this.provider.getMobsFor(ect, i, i1, i2);
  }
  
  public net.minecraft.server.ChunkPosition findNearestMapFeature(net.minecraft.server.World world, String string, int i, int i1, int i2) {
    return this.provider.findNearestMapFeature(world, string, i, i1, i2);
  }
}
