package org.bukkit.craftbukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityWeatherLighting;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldServer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftMinecart; // BTCS
import org.bukkit.craftbukkit.entity.CraftMinecart.Type;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.util.LongHashset;
import org.bukkit.craftbukkit.util.LongHashtable;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class CraftWorld implements org.bukkit.World
{
  private final WorldServer world;
  private World.Environment environment;
  private final CraftServer server = (CraftServer)org.bukkit.Bukkit.getServer();
  private final ChunkGenerator generator;
  private final List<org.bukkit.generator.BlockPopulator> populators = new ArrayList();
  private final org.bukkit.craftbukkit.metadata.BlockMetadataStore blockMetadata = new org.bukkit.craftbukkit.metadata.BlockMetadataStore(this);
  private int monsterSpawn = -1;
  private int animalSpawn = -1;
  private int waterAnimalSpawn = -1;
  
  private static final Random rand = new Random();
  
  public CraftWorld(WorldServer world, ChunkGenerator gen, World.Environment env) {
    this.world = world;
    this.generator = gen;
    
    this.environment = env;
  }
  
  public org.bukkit.block.Block getBlockAt(int x, int y, int z) {
    return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
  }
  
  public int getBlockTypeIdAt(int x, int y, int z) {
    return this.world.getTypeId(x, y, z);
  }
  
  public int getHighestBlockYAt(int x, int z) {
    if (!isChunkLoaded(x >> 4, z >> 4)) {
      loadChunk(x >> 4, z >> 4);
    }
    
    return this.world.getHighestBlockYAt(x, z);
  }
  
  public Location getSpawnLocation() {
    ChunkCoordinates spawn = this.world.getSpawn();
    return new Location(this, spawn.x, spawn.y, spawn.z);
  }
  
  public boolean setSpawnLocation(int x, int y, int z) {
    try {
      Location previousLocation = getSpawnLocation();
      this.world.worldData.setSpawn(x, y, z);
      

      org.bukkit.event.world.SpawnChangeEvent event = new org.bukkit.event.world.SpawnChangeEvent(this, previousLocation);
      this.server.getPluginManager().callEvent(event);
      
      return true;
    } catch (Exception e) {}
    return false;
  }
  
  public org.bukkit.Chunk getChunkAt(int x, int z)
  {
    return this.world.chunkProviderServer.getChunkAt(x, z).bukkitChunk;
  }
  
  public org.bukkit.Chunk getChunkAt(org.bukkit.block.Block block) {
    return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
  }
  
  public boolean isChunkLoaded(int x, int z) {
    return this.world.chunkProviderServer.isChunkLoaded(x, z);
  }
  
  public org.bukkit.Chunk[] getLoadedChunks() {
    Object[] chunks = this.world.chunkProviderServer.chunks.values().toArray();
    org.bukkit.Chunk[] craftChunks = new CraftChunk[chunks.length];
    
    for (int i = 0; i < chunks.length; i++) {
      net.minecraft.server.Chunk chunk = (net.minecraft.server.Chunk)chunks[i];
      craftChunks[i] = chunk.bukkitChunk;
    }
    
    return craftChunks;
  }
  
  public void loadChunk(int x, int z) {
    loadChunk(x, z, true);
  }
  
  public boolean unloadChunk(org.bukkit.Chunk chunk) {
    return unloadChunk(chunk.getX(), chunk.getZ());
  }
  
  public boolean unloadChunk(int x, int z) {
    return unloadChunk(x, z, true);
  }
  
  public boolean unloadChunk(int x, int z, boolean save) {
    return unloadChunk(x, z, save, false);
  }
  
  public boolean unloadChunkRequest(int x, int z) {
    return unloadChunkRequest(x, z, true);
  }
  
  public boolean unloadChunkRequest(int x, int z, boolean safe) {
    if ((safe) && (isChunkInUse(x, z))) {
      return false;
    }
    
    this.world.chunkProviderServer.queueUnload(x, z);
    
    return true;
  }
  
  public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
    if ((safe) && (isChunkInUse(x, z))) {
      return false;
    }
    
    net.minecraft.server.Chunk chunk = this.world.chunkProviderServer.getOrCreateChunk(x, z);
    
    if ((save) && (!(chunk instanceof net.minecraft.server.EmptyChunk))) {
      chunk.removeEntities();
      this.world.chunkProviderServer.saveChunk(chunk);
      this.world.chunkProviderServer.saveChunkNOP(chunk);
    }
    
    this.world.chunkProviderServer.unloadQueue.remove(x, z);
    this.world.chunkProviderServer.chunks.remove(x, z);
    this.world.chunkProviderServer.chunkList.remove(chunk);
    
    return true;
  }
  
  public boolean regenerateChunk(int x, int z) {
    unloadChunk(x, z, false, false);
    
    this.world.chunkProviderServer.unloadQueue.remove(x, z);
    
    net.minecraft.server.Chunk chunk = null;
    
    if (this.world.chunkProviderServer.chunkProvider == null) {
      chunk = this.world.chunkProviderServer.emptyChunk;
    } else {
      chunk = this.world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);
    }
    
    chunkLoadPostProcess(chunk, x, z);
    
    refreshChunk(x, z);
    
    return chunk != null;
  }
  
  public boolean refreshChunk(int x, int z) {
    if (!isChunkLoaded(x, z)) {
      return false;
    }
    
    int px = x << 4;
    int pz = z << 4;
    



    int height = getMaxHeight() / 16;
    for (int idx = 0; idx < 64; idx++) {
      this.world.notify(px + idx / height, idx % height * 16, pz);
    }
    this.world.notify(px + 15, height * 16 - 1, pz + 15);
    
    return true;
  }
  
  public boolean isChunkInUse(int x, int z) {
    Player[] players = this.server.getOnlinePlayers();
    
    for (Player player : players) {
      Location loc = player.getLocation();
      if (loc.getWorld() == this.world.chunkProviderServer.world.getWorld())
      {





        if ((Math.abs(loc.getBlockX() - (x << 4)) <= 256) && (Math.abs(loc.getBlockZ() - (z << 4)) <= 256))
          return true;
      }
    }
    return false;
  }
  
  public boolean loadChunk(int x, int z, boolean generate) {
    if (generate)
    {
      return this.world.chunkProviderServer.getChunkAt(x, z) != null;
    }
    
    this.world.chunkProviderServer.unloadQueue.remove(x, z);
    net.minecraft.server.Chunk chunk = (net.minecraft.server.Chunk)this.world.chunkProviderServer.chunks.get(x, z);
    
    if (chunk == null) {
      chunk = this.world.chunkProviderServer.loadChunk(x, z);
      
      chunkLoadPostProcess(chunk, x, z);
    }
    return chunk != null;
  }
  
  private void chunkLoadPostProcess(net.minecraft.server.Chunk chunk, int x, int z)
  {
    if (chunk != null) {
      this.world.chunkProviderServer.chunks.put(x, z, chunk);
      this.world.chunkProviderServer.chunkList.add(chunk);
      
      chunk.loadNOP();
      chunk.addEntities();
      
      if ((!chunk.done) && (this.world.chunkProviderServer.isChunkLoaded(x + 1, z + 1)) && (this.world.chunkProviderServer.isChunkLoaded(x, z + 1)) && (this.world.chunkProviderServer.isChunkLoaded(x + 1, z))) {
        this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z);
      }
      
      if ((this.world.chunkProviderServer.isChunkLoaded(x - 1, z)) && (!this.world.chunkProviderServer.getOrCreateChunk(x - 1, z).done) && (this.world.chunkProviderServer.isChunkLoaded(x - 1, z + 1)) && (this.world.chunkProviderServer.isChunkLoaded(x, z + 1)) && (this.world.chunkProviderServer.isChunkLoaded(x - 1, z))) {
        this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z);
      }
      
      if ((this.world.chunkProviderServer.isChunkLoaded(x, z - 1)) && (!this.world.chunkProviderServer.getOrCreateChunk(x, z - 1).done) && (this.world.chunkProviderServer.isChunkLoaded(x + 1, z - 1)) && (this.world.chunkProviderServer.isChunkLoaded(x, z - 1)) && (this.world.chunkProviderServer.isChunkLoaded(x + 1, z))) {
        this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x, z - 1);
      }
      
      if ((this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1)) && (!this.world.chunkProviderServer.getOrCreateChunk(x - 1, z - 1).done) && (this.world.chunkProviderServer.isChunkLoaded(x - 1, z - 1)) && (this.world.chunkProviderServer.isChunkLoaded(x, z - 1)) && (this.world.chunkProviderServer.isChunkLoaded(x - 1, z))) {
        this.world.chunkProviderServer.getChunkAt(this.world.chunkProviderServer, x - 1, z - 1);
      }
    }
  }
  
  public boolean isChunkLoaded(org.bukkit.Chunk chunk) {
    return isChunkLoaded(chunk.getX(), chunk.getZ());
  }
  
  public void loadChunk(org.bukkit.Chunk chunk) {
    loadChunk(chunk.getX(), chunk.getZ());
    ((CraftChunk)getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
  }
  
  public WorldServer getHandle() {
    return this.world;
  }
  
  public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {
    Validate.notNull(item, "Cannot drop a Null item.");
    Validate.isTrue(item.getTypeId() != 0, "Cannot drop AIR.");
    CraftItemStack clone = new CraftItemStack(item);
    net.minecraft.server.EntityItem entity = new net.minecraft.server.EntityItem(this.world, loc.getX(), loc.getY(), loc.getZ(), clone.getHandle());
    entity.pickupDelay = 10;
    this.world.addEntity(entity);
    

    return new org.bukkit.craftbukkit.entity.CraftItem(this.world.getServer(), entity);
  }
  
  public org.bukkit.entity.Item dropItemNaturally(Location loc, ItemStack item) {
    double xs = this.world.random.nextFloat() * 0.7F + 0.15000000596046448D;
    double ys = this.world.random.nextFloat() * 0.7F + 0.15000000596046448D;
    double zs = this.world.random.nextFloat() * 0.7F + 0.15000000596046448D;
    loc = loc.clone();
    loc.setX(loc.getX() + xs);
    loc.setY(loc.getY() + ys);
    loc.setZ(loc.getZ() + zs);
    return dropItem(loc, item);
  }
  
  public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
    EntityArrow arrow = new EntityArrow(this.world);
    arrow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), 0.0F, 0.0F);
    this.world.addEntity(arrow);
    arrow.shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
    return (Arrow)arrow.getBukkitEntity();
  }
  
  @Deprecated
  public LivingEntity spawnCreature(Location loc, CreatureType creatureType) {
    return spawnCreature(loc, creatureType.toEntityType());
  }
  
  @Deprecated
  public LivingEntity spawnCreature(Location loc, EntityType creatureType) {
    Validate.isTrue(creatureType.isAlive(), "EntityType not instance of LivingEntity");
    return (LivingEntity)spawnEntity(loc, creatureType);
  }
  
  public org.bukkit.entity.Entity spawnEntity(Location loc, EntityType entityType) {
    return spawn(loc, entityType.getEntityClass());
  }
  
  public LightningStrike strikeLightning(Location loc) {
    EntityWeatherLighting lightning = new EntityWeatherLighting(this.world, loc.getX(), loc.getY(), loc.getZ());
    this.world.strikeLightning(lightning);
    return new org.bukkit.craftbukkit.entity.CraftLightningStrike(this.server, lightning);
  }
  
  public LightningStrike strikeLightningEffect(Location loc) {
    EntityWeatherLighting lightning = new EntityWeatherLighting(this.world, loc.getX(), loc.getY(), loc.getZ(), true);
    this.world.strikeLightning(lightning);
    return new org.bukkit.craftbukkit.entity.CraftLightningStrike(this.server, lightning);
  }
  
  public boolean generateTree(Location loc, TreeType type) {
    return generateTree(loc, type, this.world);
  }
  
  public boolean generateTree(Location loc, TreeType type, org.bukkit.BlockChangeDelegate delegate) {
    net.minecraft.server.BlockSapling.TreeGenerator gen;
    switch (type) {
    case BIG_TREE: 
      gen = new net.minecraft.server.WorldGenBigTree(true);
      break;
    case BIRCH: 
      gen = new net.minecraft.server.WorldGenForest(true);
      break;
    case REDWOOD: 
      gen = new net.minecraft.server.WorldGenTaiga2(true);
      break;
    case TALL_REDWOOD: 
      gen = new net.minecraft.server.WorldGenTaiga1();
      break;
    case JUNGLE: 
      gen = new net.minecraft.server.WorldGenMegaTree(true, 10 + rand.nextInt(20), 3, 3);
      break;
    case SMALL_JUNGLE: 
      gen = new net.minecraft.server.WorldGenTrees(true, 4 + rand.nextInt(7), 3, 3, false);
      break;
    case JUNGLE_BUSH: 
      gen = new net.minecraft.server.WorldGenGroundBush(3, 0);
      break;
    case RED_MUSHROOM: 
      gen = new net.minecraft.server.WorldGenHugeMushroom(1);
      break;
    case BROWN_MUSHROOM: 
      gen = new net.minecraft.server.WorldGenHugeMushroom(0);
      break;
    case SWAMP: 
      gen = new net.minecraft.server.WorldGenSwampTree();
      break;
    case TREE: 
    default: 
      gen = new net.minecraft.server.WorldGenTrees(true);
    }
    
    
    return gen.generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
  }
  
  public net.minecraft.server.TileEntity getTileEntityAt(int x, int y, int z) {
    return this.world.getTileEntity(x, y, z);
  }
  
  public String getName() {
    return this.world.worldData.name;
  }
  
  @Deprecated
  public long getId() {
    return this.world.worldData.getSeed();
  }
  
  public java.util.UUID getUID() {
    return this.world.getUUID();
  }
  
  public String toString()
  {
    return "CraftWorld{name=" + getName() + '}';
  }
  
  public long getTime() {
    long time = getFullTime() % 24000L;
    if (time < 0L) time += 24000L;
    return time;
  }
  
  public void setTime(long time) {
    long margin = (time - getFullTime()) % 24000L;
    if (margin < 0L) margin += 24000L;
    setFullTime(getFullTime() + margin);
  }
  
  public long getFullTime() {
    return this.world.getTime();
  }
  
  public void setFullTime(long time) {
    this.world.setTime(time);
    

    for (Player p : getPlayers()) {
      CraftPlayer cp = (CraftPlayer)p;
      if (cp.getHandle().netServerHandler != null)
      {
        cp.getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet4UpdateTime(cp.getHandle().getPlayerTime())); }
    }
  }
  
  public boolean createExplosion(double x, double y, double z, float power) {
    return createExplosion(x, y, z, power, false);
  }
  
  public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
    return !this.world.createExplosion(null, x, y, z, power, setFire).wasCanceled;
  }
  
  public boolean createExplosion(Location loc, float power) {
    return createExplosion(loc, power, false);
  }
  
  public boolean createExplosion(Location loc, float power, boolean setFire) {
    return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
  }
  
  public World.Environment getEnvironment() {
    return this.environment;
  }
  
  public void setEnvironment(World.Environment env) {
    if (this.environment != env) {
      this.environment = env;
      this.world.worldProvider = net.minecraft.server.WorldProvider.byDimension(this.environment.getId());
    }
  }
  
  public org.bukkit.block.Block getBlockAt(Location location) {
    return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }
  
  public int getBlockTypeIdAt(Location location) {
    return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }
  
  public int getHighestBlockYAt(Location location) {
    return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
  }
  
  public org.bukkit.Chunk getChunkAt(Location location) {
    return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
  }
  
  public ChunkGenerator getGenerator() {
    return this.generator;
  }
  
  public List<org.bukkit.generator.BlockPopulator> getPopulators() {
    return this.populators;
  }
  
  public org.bukkit.block.Block getHighestBlockAt(int x, int z) {
    return getBlockAt(x, getHighestBlockYAt(x, z), z);
  }
  
  public org.bukkit.block.Block getHighestBlockAt(Location location) {
    return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
  }
  
  public Biome getBiome(int x, int z) {
    return org.bukkit.craftbukkit.block.CraftBlock.biomeBaseToBiome(this.world.getBiome(x, z));
  }
  
  public void setBiome(int x, int z, Biome bio) {
    BiomeBase bb = org.bukkit.craftbukkit.block.CraftBlock.biomeToBiomeBase(bio);
    if (this.world.isLoaded(x, 0, z)) {
      net.minecraft.server.Chunk chunk = this.world.getChunkAtWorldCoords(x, z);
      
      if (chunk != null) {
        byte[] biomevals = chunk.l();
        biomevals[((z & 0xF) << 4 | x & 0xF)] = ((byte)bb.id);
      }
    }
  }
  
  public double getTemperature(int x, int z) {
    return this.world.getBiome(x, z).F;
  }
  
  public double getHumidity(int x, int z) {
    return this.world.getBiome(x, z).G;
  }
  
  public List<org.bukkit.entity.Entity> getEntities() {
    List<org.bukkit.entity.Entity> list = new ArrayList();
    
    for (Object o : this.world.entityList) {
      if ((o instanceof net.minecraft.server.Entity)) {
        net.minecraft.server.Entity mcEnt = (net.minecraft.server.Entity)o;
        org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
        

        if (bukkitEntity != null) {
          list.add(bukkitEntity);
        }
      }
    }
    
    return list;
  }
  
  public List<LivingEntity> getLivingEntities() {
    List<LivingEntity> list = new ArrayList();
    
    for (Object o : this.world.entityList) {
      if ((o instanceof net.minecraft.server.Entity)) {
        net.minecraft.server.Entity mcEnt = (net.minecraft.server.Entity)o;
        org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
        

        if ((bukkitEntity != null) && ((bukkitEntity instanceof LivingEntity))) {
          list.add((LivingEntity)bukkitEntity);
        }
      }
    }
    
    return list;
  }
  
  @Deprecated
  public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T>... classes)
  {
    return (Collection<T>) getEntitiesByClasses(classes); // BTCS: added cast (Collection<T>)
  }
  
  public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T> clazz)
  {
    Collection<T> list = new ArrayList();
    
    for (Object entity : this.world.entityList) {
      if ((entity instanceof net.minecraft.server.Entity)) {
        org.bukkit.entity.Entity bukkitEntity = ((net.minecraft.server.Entity)entity).getBukkitEntity();
        
        if (bukkitEntity != null)
        {


          Class<?> bukkitClass = bukkitEntity.getClass();
          
          if (clazz.isAssignableFrom(bukkitClass)) {
            list.add((T) bukkitEntity); // BTCS: added cast (T)
          }
        }
      }
    }
    return list;
  }
  
  public Collection<org.bukkit.entity.Entity> getEntitiesByClasses(Class<?>... classes) {
    Collection<org.bukkit.entity.Entity> list = new ArrayList();
    
    for (Object entity : this.world.entityList) {
      if ((entity instanceof net.minecraft.server.Entity)) {
        org.bukkit.entity.Entity bukkitEntity = ((net.minecraft.server.Entity)entity).getBukkitEntity();
        
        if (bukkitEntity != null)
        {


          Class<?> bukkitClass = bukkitEntity.getClass();
          
          for (Class<?> clazz : classes) {
            if (clazz.isAssignableFrom(bukkitClass)) {
              list.add(bukkitEntity);
              break;
            }
          }
        }
      }
    }
    return list;
  }
  
  public List<Player> getPlayers() {
    List<Player> list = new ArrayList();
    
    for (Object o : this.world.entityList) {
      if ((o instanceof net.minecraft.server.Entity)) {
        net.minecraft.server.Entity mcEnt = (net.minecraft.server.Entity)o;
        org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
        
        if ((bukkitEntity != null) && ((bukkitEntity instanceof Player))) {
          list.add((Player)bukkitEntity);
        }
      }
    }
    
    return list;
  }
  
  public void save() {
    boolean oldSave = this.world.savingDisabled;
    
    this.world.savingDisabled = false;
    this.world.save(true, null);
    
    this.world.savingDisabled = oldSave;
  }
  
  public boolean isAutoSave() {
    return !this.world.savingDisabled;
  }
  
  public void setAutoSave(boolean value) {
    this.world.savingDisabled = (!value);
  }
  
  public void setDifficulty(Difficulty difficulty) {
    getHandle().difficulty = difficulty.getValue();
  }
  
  public Difficulty getDifficulty() {
    return Difficulty.getByValue(getHandle().difficulty);
  }
  
  public org.bukkit.craftbukkit.metadata.BlockMetadataStore getBlockMetadata() {
    return this.blockMetadata;
  }
  
  public boolean hasStorm() {
    return this.world.worldData.hasStorm();
  }
  
  public void setStorm(boolean hasStorm) {
    CraftServer server = this.world.getServer();
    
    WeatherChangeEvent weather = new WeatherChangeEvent(this, hasStorm);
    server.getPluginManager().callEvent(weather);
    if (!weather.isCancelled()) {
      this.world.worldData.setStorm(hasStorm);
      

      if (hasStorm) {
        setWeatherDuration(rand.nextInt(12000) + 12000);
      } else {
        setWeatherDuration(rand.nextInt(168000) + 12000);
      }
    }
  }
  
  public int getWeatherDuration() {
    return this.world.worldData.getWeatherDuration();
  }
  
  public void setWeatherDuration(int duration) {
    this.world.worldData.setWeatherDuration(duration);
  }
  
  public boolean isThundering() {
    return (hasStorm()) && (this.world.worldData.isThundering());
  }
  
  public void setThundering(boolean thundering) {
    if ((thundering) && (!hasStorm())) setStorm(true);
    CraftServer server = this.world.getServer();
    
    ThunderChangeEvent thunder = new ThunderChangeEvent(this, thundering);
    server.getPluginManager().callEvent(thunder);
    if (!thunder.isCancelled()) {
      this.world.worldData.setThundering(thundering);
      

      if (thundering) {
        setThunderDuration(rand.nextInt(12000) + 3600);
      } else {
        setThunderDuration(rand.nextInt(168000) + 12000);
      }
    }
  }
  
  public int getThunderDuration() {
    return this.world.worldData.getThunderDuration();
  }
  
  public void setThunderDuration(int duration) {
    this.world.worldData.setThunderDuration(duration);
  }
  
  public long getSeed() {
    return this.world.worldData.getSeed();
  }
  
  public boolean getPVP() {
    return this.world.pvpMode;
  }
  
  public void setPVP(boolean pvp) {
    this.world.pvpMode = pvp;
  }
  
  public void playEffect(Player player, Effect effect, int data) {
    playEffect(player.getLocation(), effect, data, 0);
  }
  
  public void playEffect(Location location, Effect effect, int data) {
    playEffect(location, effect, data, 64);
  }
  
  public <T> void playEffect(Location loc, Effect effect, T data) {
    playEffect(loc, effect, data, 64);
  }
  
  public <T> void playEffect(Location loc, Effect effect, T data, int radius) {
    if (data != null) {
      Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
    } else {
      Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
    }
    
    int datavalue = data == null ? 0 : CraftEffect.getDataValue(effect, data);
    playEffect(loc, effect, datavalue, radius);
  }
  
  public void playEffect(Location location, Effect effect, int data, int radius) {
    Validate.notNull(location, "Location cannot be null");
    Validate.notNull(effect, "Effect cannot be null");
    Validate.notNull(location.getWorld(), "World cannot be null");
    int packetData = effect.getId();
    net.minecraft.server.Packet61WorldEvent packet = new net.minecraft.server.Packet61WorldEvent(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), data);
    
    radius *= radius;
    
    for (Player player : getPlayers()) {
      if ((((CraftPlayer)player).getHandle().netServerHandler != null) && 
        (location.getWorld().equals(player.getWorld())))
      {
        int distance = (int)player.getLocation().distanceSquared(location);
        if (distance <= radius)
          ((CraftPlayer)player).getHandle().netServerHandler.sendPacket(packet);
      }
    }
  }
  
  public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
    return spawn(location, clazz, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CUSTOM);
  }
  
  public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException
  {
    if ((location == null) || (clazz == null)) {
      throw new IllegalArgumentException("Location or entity class cannot be null");
    }
    
    net.minecraft.server.Entity entity = null;
    
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    float pitch = location.getPitch();
    float yaw = location.getYaw();
    

    if (org.bukkit.entity.Boat.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityBoat(this.world, x, y, z);
    } else if (org.bukkit.entity.FallingSand.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityFallingBlock(this.world, x, y, z, 0, 0);
    } else if (org.bukkit.entity.Projectile.class.isAssignableFrom(clazz)) {
      if (org.bukkit.entity.Snowball.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntitySnowball(this.world, x, y, z);
      } else if (org.bukkit.entity.Egg.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityEgg(this.world, x, y, z);
      } else if (org.bukkit.entity.EnderPearl.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityEnderPearl(this.world, x, y, z);
      } else if (Arrow.class.isAssignableFrom(clazz)) {
        entity = new EntityArrow(this.world);
        entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
      } else if (org.bukkit.entity.ThrownExpBottle.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityThrownExpBottle(this.world);
        entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
      } else if (org.bukkit.entity.Fireball.class.isAssignableFrom(clazz)) {
        if (org.bukkit.entity.SmallFireball.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySmallFireball(this.world);
        } else {
          entity = new EntityFireball(this.world);
        }
        ((EntityFireball)entity).setPositionRotation(x, y, z, yaw, pitch);
        Vector direction = location.getDirection().multiply(10);
        ((EntityFireball)entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
      }
    } else if (org.bukkit.entity.Minecart.class.isAssignableFrom(clazz)) {
      if (org.bukkit.entity.PoweredMinecart.class.isAssignableFrom(clazz)) {
        entity = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.PoweredMinecart.getId());
      } else if (org.bukkit.entity.StorageMinecart.class.isAssignableFrom(clazz)) {
        entity = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.StorageMinecart.getId());
      } else {
        entity = new EntityMinecart(this.world, x, y, z, CraftMinecart.Type.Minecart.getId());
      }
    } else if (org.bukkit.entity.EnderSignal.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityEnderSignal(this.world, x, y, z);
    } else if (org.bukkit.entity.EnderCrystal.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityEnderCrystal(this.world);
      entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
    } else if (LivingEntity.class.isAssignableFrom(clazz)) {
      if (org.bukkit.entity.Chicken.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityChicken(this.world);
      } else if (org.bukkit.entity.Cow.class.isAssignableFrom(clazz)) {
        if (org.bukkit.entity.MushroomCow.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityMushroomCow(this.world);
        } else {
          entity = new net.minecraft.server.EntityCow(this.world);
        }
      } else if (org.bukkit.entity.Golem.class.isAssignableFrom(clazz)) {
        if (org.bukkit.entity.Snowman.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySnowman(this.world);
        } else if (org.bukkit.entity.IronGolem.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityIronGolem(this.world);
        }
      } else if (org.bukkit.entity.Creeper.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityCreeper(this.world);
      } else if (org.bukkit.entity.Ghast.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityGhast(this.world);
      } else if (org.bukkit.entity.Pig.class.isAssignableFrom(clazz)) {
        entity = new net.minecraft.server.EntityPig(this.world);
      } else if (!Player.class.isAssignableFrom(clazz))
      {
        if (org.bukkit.entity.Sheep.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySheep(this.world);
        } else if (org.bukkit.entity.Skeleton.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySkeleton(this.world);
        } else if (org.bukkit.entity.Slime.class.isAssignableFrom(clazz)) {
          if (org.bukkit.entity.MagmaCube.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.server.EntityMagmaCube(this.world);
          } else {
            entity = new net.minecraft.server.EntitySlime(this.world);
          }
        } else if (org.bukkit.entity.Spider.class.isAssignableFrom(clazz)) {
          if (org.bukkit.entity.CaveSpider.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.server.EntityCaveSpider(this.world);
          } else {
            entity = new net.minecraft.server.EntitySpider(this.world);
          }
        } else if (org.bukkit.entity.Squid.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySquid(this.world);
        } else if (org.bukkit.entity.Tameable.class.isAssignableFrom(clazz)) {
          if (org.bukkit.entity.Wolf.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.server.EntityWolf(this.world);
          } else if (org.bukkit.entity.Ocelot.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.server.EntityOcelot(this.world);
          }
        } else if (org.bukkit.entity.PigZombie.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityPigZombie(this.world);
        } else if (org.bukkit.entity.Zombie.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityZombie(this.world);
        } else if (org.bukkit.entity.Giant.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityGiantZombie(this.world);
        } else if (org.bukkit.entity.Silverfish.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntitySilverfish(this.world);
        } else if (org.bukkit.entity.Enderman.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityEnderman(this.world);
        } else if (org.bukkit.entity.Blaze.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityBlaze(this.world);
        } else if (org.bukkit.entity.Villager.class.isAssignableFrom(clazz)) {
          entity = new net.minecraft.server.EntityVillager(this.world);
        } else if ((org.bukkit.entity.ComplexLivingEntity.class.isAssignableFrom(clazz)) && 
          (org.bukkit.entity.EnderDragon.class.isAssignableFrom(clazz))) {
          entity = new net.minecraft.server.EntityEnderDragon(this.world);
        }
      }
      
      if (entity != null) {
        entity.setLocation(x, y, z, pitch, yaw);
      }
    } else if (org.bukkit.entity.Painting.class.isAssignableFrom(clazz)) {
      org.bukkit.block.Block block = getBlockAt(location);
      BlockFace face = BlockFace.SELF;
      if (block.getRelative(BlockFace.EAST).getTypeId() == 0) {
        face = BlockFace.EAST;
      } else if (block.getRelative(BlockFace.NORTH).getTypeId() == 0) {
        face = BlockFace.NORTH;
      } else if (block.getRelative(BlockFace.WEST).getTypeId() == 0) {
        face = BlockFace.WEST;
      } else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 0) {
        face = BlockFace.SOUTH;
      }
      int dir;
      switch (face) {
      case EAST: 
      default: 
        dir = 0;
        break;
      case NORTH: 
        dir = 1;
        break;
      case WEST: 
        dir = 2;
        break;
      case SOUTH: 
        dir = 3;
      }
      
      
      entity = new net.minecraft.server.EntityPainting(this.world, (int)x, (int)y, (int)z, dir);
      if (!((net.minecraft.server.EntityPainting)entity).survives()) {
        entity = null;
      }
    } else if (org.bukkit.entity.TNTPrimed.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityTNTPrimed(this.world, x, y, z);
    } else if (org.bukkit.entity.ExperienceOrb.class.isAssignableFrom(clazz)) {
      entity = new net.minecraft.server.EntityExperienceOrb(this.world, x, y, z, 0);
    } else if (org.bukkit.entity.Weather.class.isAssignableFrom(clazz))
    {
      entity = new EntityWeatherLighting(this.world, x, y, z);
    } else if (!LightningStrike.class.isAssignableFrom(clazz))
    {
      if (org.bukkit.entity.Fish.class.isAssignableFrom(clazz))
      {
        entity = new net.minecraft.server.EntityFishingHook(this.world);
        entity.setLocation(x, y, z, pitch, yaw);
      }
    }
    if (entity != null) {
      this.world.addEntity(entity, reason);
      return (T) entity.getBukkitEntity(); // BTCS: add cast (T)
    }
    
    throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
  }
  
  public org.bukkit.ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
    return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
  }
  
  public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
    this.world.setSpawnFlags(allowMonsters, allowAnimals);
  }
  
  public boolean getAllowAnimals() {
    return this.world.allowAnimals;
  }
  
  public boolean getAllowMonsters() {
    return this.world.allowMonsters;
  }
  
  public int getMaxHeight() {
    return this.world.getHeight();
  }
  
  public int getSeaLevel() {
    return 64;
  }
  
  public boolean getKeepSpawnInMemory() {
    return this.world.keepSpawnInMemory;
  }
  
  public void setKeepSpawnInMemory(boolean keepLoaded) {
    this.world.keepSpawnInMemory = keepLoaded;
    
    ChunkCoordinates chunkcoordinates = this.world.getSpawn();
    int chunkCoordX = chunkcoordinates.x >> 4;
    int chunkCoordZ = chunkcoordinates.z >> 4;
    
    for (int x = -12; x <= 12; x++) {
      for (int z = -12; z <= 12; z++) {
        if (keepLoaded) {
          loadChunk(chunkCoordX + x, chunkCoordZ + z);
        }
        else if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
          if ((getHandle().getChunkAt(chunkCoordX + x, chunkCoordZ + z) instanceof net.minecraft.server.EmptyChunk)) {
            unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
          } else {
            unloadChunk(chunkCoordX + x, chunkCoordZ + z);
          }
        }
      }
    }
  }
  

  public int hashCode()
  {
    return getUID().hashCode();
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    
    CraftWorld other = (CraftWorld)obj;
    
    return getUID() == other.getUID();
  }
  
  public java.io.File getWorldFolder() {
    return ((net.minecraft.server.WorldNBTStorage)this.world.getDataManager()).getDirectory();
  }
  
  public void explodeBlock(org.bukkit.block.Block block, float yield)
  {
    if ((block.getType().equals(Material.AIR)) || (block.getType().equals(Material.FIRE))) {
      return;
    }
    int blockId = block.getTypeId();
    int blockX = block.getX();
    int blockY = block.getY();
    int blockZ = block.getZ();
    
    net.minecraft.server.Block.byId[blockId].dropNaturally(this.world, blockX, blockY, blockZ, block.getData(), yield, 0);
    block.setType(Material.AIR);
    

    net.minecraft.server.Block.byId[blockId].wasExploded(this.world, blockX, blockY, blockZ);
  }
  
  public void sendPluginMessage(Plugin source, String channel, byte[] message) {
    org.bukkit.plugin.messaging.StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
    
    for (Player player : getPlayers()) {
      player.sendPluginMessage(source, channel, message);
    }
  }
  
  public java.util.Set<String> getListeningPluginChannels() {
    java.util.Set<String> result = new java.util.HashSet();
    
    for (Player player : getPlayers()) {
      result.addAll(player.getListeningPluginChannels());
    }
    
    return result;
  }
  
  public org.bukkit.WorldType getWorldType() {
    return org.bukkit.WorldType.getByName(this.world.getWorldData().getType().name());
  }
  
  public boolean canGenerateStructures() {
    return this.world.getWorldData().shouldGenerateMapFeatures();
  }
  
  public long getTicksPerAnimalSpawns() {
    return this.world.ticksPerAnimalSpawns;
  }
  
  public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
    this.world.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
  }
  
  public long getTicksPerMonsterSpawns() {
    return this.world.ticksPerMonsterSpawns;
  }
  
  public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
    this.world.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
  }
  
  public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
    this.server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
  }
  
  public List<MetadataValue> getMetadata(String metadataKey) {
    return this.server.getWorldMetadata().getMetadata(this, metadataKey);
  }
  
  public boolean hasMetadata(String metadataKey) {
    return this.server.getWorldMetadata().hasMetadata(this, metadataKey);
  }
  
  public void removeMetadata(String metadataKey, Plugin owningPlugin) {
    this.server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
  }
  
  public int getMonsterSpawnLimit() {
    if (this.monsterSpawn < 0) {
      return this.server.getMonsterSpawnLimit();
    }
    
    return this.monsterSpawn;
  }
  
  public void setMonsterSpawnLimit(int limit) {
    this.monsterSpawn = limit;
  }
  
  public int getAnimalSpawnLimit() {
    if (this.animalSpawn < 0) {
      return this.server.getAnimalSpawnLimit();
    }
    
    return this.animalSpawn;
  }
  
  public void setAnimalSpawnLimit(int limit) {
    this.animalSpawn = limit;
  }
  
  public int getWaterAnimalSpawnLimit() {
    if (this.waterAnimalSpawn < 0) {
      return this.server.getWaterAnimalSpawnLimit();
    }
    
    return this.waterAnimalSpawn;
  }
  
  public void setWaterAnimalSpawnLimit(int limit) {
    this.waterAnimalSpawn = limit;
  }
}
