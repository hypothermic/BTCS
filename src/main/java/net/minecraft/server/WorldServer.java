package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.generator.InternalChunkGenerator;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.generator.ChunkGenerator;

public class WorldServer extends World implements forge.bukkit.BukkitForgeHooks.ForgeBlockChangeDelegate
{
  public ChunkProviderServer chunkProviderServer;
  public boolean weirdIsOpCache = false;
  public boolean savingDisabled;
  public final MinecraftServer server;
  private IntHashMap entitiesById;
  
  public CraftWorld getWorld() {
	  nl.hypothermic.btcs.XLogger.debug("RETR2: Now inside WorldServer.getWorld()");
	  nl.hypothermic.btcs.XLogger.debug("RETR2: Getting world from super");
	  CraftWorld x = super.getWorld();
	  if (x == null) {
		  nl.hypothermic.btcs.XLogger.gencrit("RETR2: CraftWorld == null");
	  } else {
		  nl.hypothermic.btcs.XLogger.debug("RETR2: CraftWorld != null");
	  }
	  //nl.hypothermic.btcs.XLogger.generr("RETR2: Returning it to " + nl.hypothermic.btcs.XLogger.getCallerClassName());
	  nl.hypothermic.btcs.XLogger.generr("RETR2: Returning it to " + Thread.currentThread().getStackTrace()[2].getClassName());
	  return x;
  }
  
  public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i, WorldSettings worldsettings, org.bukkit.World.Environment env, ChunkGenerator gen)
  {
    super(idatamanager, s, worldsettings, WorldProvider.byDimension(env.getId()), gen, env);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: WorldServer.java - 100");
    this.server = minecraftserver;
    if (this.entitiesById == null) {
      this.entitiesById = new IntHashMap();
    }
    
    this.dimension = i;
    forge.ForgeHooks.onWorldLoad(this);
    forge.DimensionManager.setWorld(i, this);
    this.pvpMode = minecraftserver.pvpMode;
    this.manager = new PlayerManager(minecraftserver, this.dimension, minecraftserver.propertyManager.getInt("view-distance", 10));
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: WorldServer.java - 200");
  }
  
  public TileEntity getTileEntity(int i, int j, int k)
  {
    TileEntity result = super.getTileEntity(i, j, k);
    int type = getTypeId(i, j, k);
    
    if (type == Block.CHEST.id) {
      if (!(result instanceof TileEntityChest)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Block.FURNACE.id) {
      if (!(result instanceof TileEntityFurnace)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Block.DISPENSER.id) {
      if (!(result instanceof TileEntityDispenser)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Block.JUKEBOX.id) {
      if (!(result instanceof TileEntityRecordPlayer)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Block.NOTE_BLOCK.id) {
      if (!(result instanceof TileEntityNote)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (type == Block.MOB_SPAWNER.id) {
      if (!(result instanceof TileEntityMobSpawner)) {
        result = fixTileEntity(i, j, k, type, result);
      }
    } else if (((type == Block.SIGN_POST.id) || (type == Block.WALL_SIGN.id)) && 
      (!(result instanceof TileEntitySign))) {
      result = fixTileEntity(i, j, k, type, result);
    }

    return result;
  }
  
  private TileEntity fixTileEntity(int x, int y, int z, int type, TileEntity found) {
    getServer().getLogger().severe("Block at " + x + "," + y + "," + z + " is " + Material.getMaterial(type).toString() + " but has " + found + ". " + "Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.");

    if ((Block.byId[type] instanceof BlockContainer)) {
      TileEntity replacement = ((BlockContainer)Block.byId[type]).a_();
      setTileEntity(x, y, z, replacement);
      return replacement;
    }
    getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
    return found;
  }

  public final int dimension;
  public EntityTracker tracker;
  public PlayerManager manager;
  
  public void entityJoinedWorld(Entity entity, boolean flag)
  {
    if ((!this.server.spawnNPCs) && ((entity instanceof NPC))) {
      entity.die();
    }
    
    if ((entity.passenger == null) || (!(entity.passenger instanceof EntityHuman))) {
      super.entityJoinedWorld(entity, flag);
    }
  }
  
  public void vehicleEnteredWorld(Entity entity, boolean flag) {
    super.entityJoinedWorld(entity, flag);
  }
  
  protected IChunkProvider b() {
    IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
    
    // BTCS start: general improvements
    InternalChunkGenerator gen;
    
    if (this.generator != null) {
      gen = new org.bukkit.craftbukkit.generator.CustomChunkGenerator(this, getSeed(), this.generator); } else {
      if ((this.worldProvider instanceof WorldProviderHell)) {
        gen = new org.bukkit.craftbukkit.generator.NetherChunkGenerator(this, getSeed()); } else {
        if ((this.worldProvider instanceof WorldProviderTheEnd)) {
          gen = new org.bukkit.craftbukkit.generator.SkyLandsChunkGenerator(this, getSeed());
        } else
          gen = new org.bukkit.craftbukkit.generator.NormalChunkGenerator(this, getSeed());
      }
    }
    // BTCS end
    this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, gen);
    

    return this.chunkProviderServer;
  }
  
  public List getTileEntities(int i, int j, int k, int l, int i1, int j1) {
    ArrayList arraylist = new ArrayList();
    
    for (int x = i >> 4; x <= l >> 4; x++)
    {
      for (int z = k >> 4; z <= j1 >> 4; z++)
      {
        Chunk chunk = getChunkAt(x, z);
        if (chunk != null)
        {
          for (Object obj : chunk.tileEntities.values())
          {
            TileEntity entity = (TileEntity)obj;
            if (!entity.l())
            {
              if ((entity.x >= i) && (entity.y >= j) && (entity.z >= k) && (entity.x <= l) && (entity.y <= i1) && (entity.z <= j1))
              {

                arraylist.add(entity);
              }
            }
          }
        }
      }
    }
    
    return arraylist;
  }
  
  public boolean a(EntityHuman entityhuman, int i, int j, int k) {
    int l = MathHelper.a(i - this.worldData.c());
    int i1 = MathHelper.a(k - this.worldData.e());
    
    if (l > i1) {
      i1 = l;
    }

    return i1 > getServer().getSpawnRadius() || (this.server.serverConfigurationManager.isOp(entityhuman.name));
  }
  
  protected void c() {
    if (this.entitiesById == null) {
      this.entitiesById = new IntHashMap();
    }
    
    super.c();
  }
  
  protected void c(Entity entity) {
    super.c(entity);
    this.entitiesById.a(entity.id, entity);
    Entity[] aentity = entity.bb();
    
    if (aentity != null) {
      for (int i = 0; i < aentity.length; i++) {
        this.entitiesById.a(aentity[i].id, aentity[i]);
      }
    }
  }
  
  protected void d(Entity entity) {
    super.d(entity);
    this.entitiesById.d(entity.id);
    Entity[] aentity = entity.bb();
    
    if (aentity != null) {
      for (int i = 0; i < aentity.length; i++) {
        this.entitiesById.d(aentity[i].id);
      }
    }
  }
  
  public Entity getEntity(int i) {
    return (Entity)this.entitiesById.get(i);
  }
  
  public boolean strikeLightning(Entity entity)
  {
    LightningStrikeEvent lightning = new LightningStrikeEvent(getWorld(), (org.bukkit.entity.LightningStrike)entity.getBukkitEntity());
    getServer().getPluginManager().callEvent(lightning);
    
    if (lightning.isCancelled()) {
      return false;
    }
    
    if (super.strikeLightning(entity)) {
      this.server.serverConfigurationManager.sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new Packet71Weather(entity));
      
      return true;
    }
    return false;
  }
  
  public void broadcastEntityEffect(Entity entity, byte b0)
  {
    Packet38EntityStatus packet38entitystatus = new Packet38EntityStatus(entity.id, b0);
    

    this.server.getTracker(this.dimension).sendPacketToEntity(entity, packet38entitystatus);
  }
  
  public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag)
  {
    Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag);
    
    if (explosion.wasCanceled) {
      return explosion;
    }

    this.server.serverConfigurationManager.sendPacketNearby(d0, d1, d2, 64.0D, this.dimension, new Packet60Explosion(d0, d1, d2, f, explosion.blocks));
    
    return explosion;
  }
  
  public void playNote(int i, int j, int k, int l, int i1) {
    super.playNote(i, j, k, l, i1);
    
    this.server.serverConfigurationManager.sendPacketNearby(i, j, k, 64.0D, this.dimension, new Packet54PlayNoteBlock(i, j, k, l, i1));
  }
  
  public void saveLevel() {
    this.dataManager.e();
  }
  
  protected void i() {
    boolean flag = x();
    
    super.i();
    if (flag != x())
    {
      for (int i = 0; i < this.players.size(); i++) {
        if (((EntityPlayer)this.players.get(i)).world == this) {
          ((EntityPlayer)this.players.get(i)).netServerHandler.sendPacket(new Packet70Bed(flag ? 2 : 1, 0));
        }
      }
    }
  }
  

  public World unwrap()
  {
    return this;
  }
}
