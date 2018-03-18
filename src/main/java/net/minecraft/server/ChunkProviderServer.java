package net.minecraft.server;

import cpw.mods.fml.server.FMLBukkitHandler;
import forge.ForgeHooks;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.LongHashset;
import org.bukkit.craftbukkit.util.LongHashtable;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.PluginManager;



public class ChunkProviderServer implements IChunkProvider
{
  public LongHashset unloadQueue = new LongHashset();
  public Chunk emptyChunk;
  public IChunkProvider chunkProvider;
  private IChunkLoader e;
  public boolean forceChunkLoad = false;
  public LongHashtable<Chunk> chunks = new LongHashtable();
  public List chunkList = new ArrayList();
  public WorldServer world;
  
  public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
  {
    this.emptyChunk = new EmptyChunk(worldserver, 0, 0);
    this.world = worldserver;
    this.e = ichunkloader;
    this.chunkProvider = ichunkprovider;
  }
  
  public boolean isChunkLoaded(int i, int j) {
    return this.chunks.containsKey(i, j);
  }
  
  public void queueUnload(int i, int j) {
    if (!ForgeHooks.canUnloadChunk(this.world.getChunkAt(i, j))) {
      return;
    }
    
    if (this.world.worldProvider.c()) {
      ChunkCoordinates chunkcoordinates = this.world.getSpawn();
      int k = i * 16 + 8 - chunkcoordinates.x;
      int l = j * 16 + 8 - chunkcoordinates.z;
      short short1 = 128;
      
      if ((k < -short1) || (k > short1) || (l < -short1) || (l > short1) || (!this.world.keepSpawnInMemory)) {
        this.unloadQueue.add(i, j);
      }
    } else {
      this.unloadQueue.add(i, j);
    }
  }
  
  public void c() {
    Iterator iterator = this.chunkList.iterator();
    
    while (iterator.hasNext()) {
      Chunk chunk = (Chunk)iterator.next();
      
      queueUnload(chunk.x, chunk.z);
    }
  }
  
  public Chunk getChunkAt(int i, int j)
  {
	  nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 100");
    this.unloadQueue.remove(i, j);
    Chunk chunk = (Chunk)this.chunks.get(i, j);
    boolean newChunk = false;
    

    if (chunk == null) {
      chunk = loadChunk(i, j);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 310");
      if (chunk == null) {
        if (this.chunkProvider == null) {
        	nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 340");
          chunk = this.emptyChunk;
        } else {
          nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 360");
          nl.hypothermic.btcs.XLogger.debug(" ChunckProvidor = " + this.chunkProvider.getClass().getName());
          chunk = this.chunkProvider.getOrCreateChunk(i, j);
        }
        nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 380");
        newChunk = true;
      }
      
      this.chunks.put(i, j, chunk);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 300");
      this.chunkList.add(chunk);
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 400");
      if (chunk != null) {
    	  nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 500");
        chunk.loadNOP();
        chunk.addEntities();
      }
      
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 600");
      Server server = this.world.getServer();
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 700");
      if (server != null)
      {
        server.getPluginManager().callEvent(new ChunkLoadEvent((CraftChunk) chunk.bukkitChunk, newChunk));
      }
      nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 800");
      chunk.a(this, this, i, j);
    }
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: ChunckProviderServer.getChunckAt() - 900");
    return chunk;
  }
  
  public Chunk getOrCreateChunk(int i, int j)
  {
	nl.hypothermic.btcs.XLogger.debug("BTCS: start ChunckProvidorServer.getOrCreateChunck()");
	// CraftBukkit start
    Chunk chunk = (Chunk) this.chunks.get(i, j);

    chunk = chunk == null ? (!this.world.isLoading && !this.forceChunkLoad ? this.emptyChunk : this.getChunkAt(i, j)) : chunk;
    if (chunk == this.emptyChunk) return chunk;
    if (i != chunk.x || j != chunk.z) {
        MinecraftServer.log.severe("Chunk (" + chunk.x + ", " + chunk.z + ") stored at  (" + i + ", " + j + ") in world '" + world.getWorld().getName() + "'");
        MinecraftServer.log.severe(chunk.getClass().getName());
        Throwable ex = new Throwable();
        ex.fillInStackTrace();
        ex.printStackTrace();
    }
    nl.hypothermic.btcs.XLogger.debug("BTCS: end ChunckProvidorServer.getOrCreateChunck()");
    return chunk;
    // CraftBukkit end
  }
  
  public Chunk loadChunk(int i, int j)
  {
    if (this.e == null) {
      return null;
    }
    try {
      Chunk chunk = this.e.a(this.world, i, j);
      
      if (chunk != null) {
        chunk.n = this.world.getTime();
      }
      
      return chunk;
    } catch (Exception exception) {
      exception.printStackTrace(); }
    return null;
  }
  

  public void saveChunkNOP(Chunk chunk)
  {
    if (this.e != null) {
      try {
        this.e.b(this.world, chunk);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
  
  public void saveChunk(Chunk chunk) {
    if (this.e != null) {
      try {
        chunk.n = this.world.getTime();
        this.e.a(this.world, chunk);
      } catch (Exception ioexception) {
        ioexception.printStackTrace();
      }
    }
  }
  
  public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
    Chunk chunk = getOrCreateChunk(i, j);
    
    if (!chunk.done) {
      chunk.done = true;
      if (this.chunkProvider != null) {
        this.chunkProvider.getChunkAt(ichunkprovider, i, j);
        FMLBukkitHandler.instance().onChunkPopulate(ichunkprovider, i, j, this.world, this.chunkProvider);
        

        BlockSand.instaFall = true;
        Random random = new Random();
        random.setSeed(this.world.getSeed());
        long xRand = random.nextLong() / 2L * 2L + 1L;
        long zRand = random.nextLong() / 2L * 2L + 1L;
        random.setSeed(i * xRand + j * zRand ^ this.world.getSeed());
        nl.hypothermic.btcs.XLogger.generr("---- BTCS XRETR6: rq cps.world from ChunckProvidorServer.getChunckAt(iCP, int, int");
        org.bukkit.World world = this.world.getWorld(); // BTCS: 'org.bukkit.World' --> 'org.bukkit.craftbukkit.CraftWorld' // edit: nvm
        nl.hypothermic.btcs.XLogger.generr("---- BTCS XRETR6: rq cps.world completed!");
        if (world != null) {
          nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 100");
          for (BlockPopulator populator : world.getPopulators()) {
        	nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 150 (POPULATING)");
            populator.populate(world, random, chunk.bukkitChunk);
            nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 175 (DONE PPL)");
          }
          nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 200");
        }
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 300");
        BlockSand.instaFall = false;
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 400");
        //this.world.getServer().getPluginManager().callEvent(new ChunkPopulateEvent(chunk.bukkitChunk));
        PluginManager pm = this.world.getServer().getPluginManager();
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 410");
        org.bukkit.Chunk test = chunk.getBukkitChunk();
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 415");
        nl.hypothermic.btcs.XLogger.generic("BTCS: CHUNCK CL: " + chunk.getClass().getName());
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 417");
        nl.hypothermic.btcs.XLogger.generic("BTCS: BUKKITCHUNCK CL: " + test.getClass().getName());
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 420");
        org.bukkit.World testw = test.getWorld();
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 430");
        ChunkPopulateEvent cpe = new ChunkPopulateEvent(test);
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 450");
        pm.callEvent(cpe);
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 500");
        chunk.e();
        nl.hypothermic.btcs.XLogger.generr("---- BTCS: ChunckProvidorServer.getChunckAt - 600");
      }
    }
  }
  
  public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
    int i = 0;
    
    for (int j = 0; j < this.chunkList.size(); j++) {
      Chunk chunk = (Chunk)this.chunkList.get(j);
      
      if (flag) {
        saveChunkNOP(chunk);
      }
      
      if (chunk.a(flag)) {
        saveChunk(chunk);
        chunk.l = false;
        i++;
        if ((i == 24) && (!flag)) {
          return false;
        }
      }
    }
    
    if (flag) {
      if (this.e == null) {
        return true;
      }
      
      this.e.b();
    }
    
    return true;
  }
  
  public boolean unloadChunks() {
    if (!this.world.savingDisabled)
    {
      Server server = this.world.getServer();
      for (int i = 0; (i < 50) && (!this.unloadQueue.isEmpty()); i++) {
        long chunkcoordinates = this.unloadQueue.popFirst();
        Chunk chunk = (Chunk)this.chunks.get(chunkcoordinates);
        if (chunk != null)
        {
          ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
          server.getPluginManager().callEvent(event);
          if (!event.isCancelled()) {
            chunk.removeEntities();
            saveChunk(chunk);
            saveChunkNOP(chunk);
            
            this.chunks.remove(chunkcoordinates);
            this.chunkList.remove(chunk);
          }
        }
      }
      
      if (this.e != null) {
        this.e.a();
      }
    }
    
    return this.chunkProvider.unloadChunks();
  }
  
  public boolean canSave() {
    return !this.world.savingDisabled;
  }
  
  public String d() {
    return "ServerChunkCache: " + this.chunks.values().size() + " Drop: " + this.unloadQueue.size();
  }
  
  public List getMobsFor(EnumCreatureType enumcreaturetype, int i, int j, int k) {
    return this.chunkProvider.getMobsFor(enumcreaturetype, i, j, k);
  }
  
  public ChunkPosition findNearestMapFeature(World world, String s, int i, int j, int k) {
    return this.chunkProvider.findNearestMapFeature(world, s, i, j, k);
  }
}
