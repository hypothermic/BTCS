package net.minecraft.server;

import cpw.mods.fml.server.FMLBukkitHandler;
import forge.DimensionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.PortalTravelAgent;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent; // BTCS
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause; // BTCS
import org.bukkit.plugin.PluginManager;

public class ServerConfigurationManager
{
  public static Logger a = Logger.getLogger("Minecraft");
  public List players = new ArrayList();
  
  public MinecraftServer server;
  public int maxPlayers;
  public Set banByName = new HashSet();
  public Set banByIP = new HashSet();
  public Set operators = new HashSet();
  private Set whitelist = new java.util.LinkedHashSet();
  private File j;
  private File k;
  private File l;
  private File m;
  public PlayerFileData playerFileData;
  public boolean hasWhitelist;
  private int p = 0;
  
  private CraftServer cserver;
  
  public ServerConfigurationManager(MinecraftServer minecraftserver)
  {
    minecraftserver.server = new CraftServer(minecraftserver, this);
    minecraftserver.console = org.bukkit.craftbukkit.command.ColouredConsoleSender.getInstance();
    this.cserver = minecraftserver.server;
    

    this.server = minecraftserver;
    this.j = minecraftserver.a("banned-players.txt");
    this.k = minecraftserver.a("banned-ips.txt");
    this.l = minecraftserver.a("ops.txt");
    this.m = minecraftserver.a("white-list.txt");
    int i = minecraftserver.propertyManager.getInt("view-distance", 10);
    

    this.maxPlayers = minecraftserver.propertyManager.getInt("max-players", 20);
    this.hasWhitelist = minecraftserver.propertyManager.getBoolean("white-list", false);
    l();
    n();
    p();
    r();
    m();
    o();
    q();
    s();
  }
  
  public void setPlayerFileData(WorldServer[] aworldserver) {
    if (this.playerFileData != null) return;
    this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
  }
  
  public void a(EntityPlayer entityplayer)
  {
    for (WorldServer world : this.server.worlds) {
      if (world.manager.managedPlayers.contains(entityplayer)) {
        world.manager.removePlayer(entityplayer);
        break;
      }
    }
    getPlayerManager(entityplayer.dimension).addPlayer(entityplayer);
    WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
    
    worldserver.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
  }
  
  public int a()
  {
    if (this.server.worlds.size() == 0) {
      return this.server.propertyManager.getInt("view-distance", 10) * 16 - 16;
    }
    return ((WorldServer)this.server.worlds.get(0)).manager.getFurthestViewableBlock();
  }
  
  private PlayerManager getPlayerManager(int i)
  {
    return this.server.getWorldServer(i).manager;
  }
  
  public void b(EntityPlayer entityplayer) {
    this.playerFileData.load(entityplayer);
  }
  
  public void c(EntityPlayer entityplayer) {
    this.cserver.detectListNameConflict(entityplayer);
    
    this.players.add(entityplayer);
    WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);
    
    worldserver.chunkProviderServer.getChunkAt((int)entityplayer.locX >> 4, (int)entityplayer.locZ >> 4);
    

    if (!this.cserver.useExactLoginLocation()) {
      while (worldserver.getCubes(entityplayer, entityplayer.boundingBox).size() != 0) {
        entityplayer.setPosition(entityplayer.locX, entityplayer.locY + 1.0D, entityplayer.locZ);
      }
    }
    entityplayer.setPosition(entityplayer.locX, entityplayer.locY + entityplayer.getBukkitEntity().getEyeHeight(), entityplayer.locZ);
    

    PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " joined the game.");
    this.cserver.getPluginManager().callEvent(playerJoinEvent);
    
    String joinMessage = playerJoinEvent.getJoinMessage();
    
    if ((joinMessage != null) && (joinMessage.length() > 0)) {
      this.server.serverConfigurationManager.sendAll(new Packet3Chat(joinMessage));
    }
    this.cserver.onPlayerJoin(playerJoinEvent.getPlayer());
    

    worldserver.addEntity(entityplayer);
    getPlayerManager(entityplayer.dimension).addPlayer(entityplayer);
    u();
    

    Packet201PlayerInfo packet = new Packet201PlayerInfo(entityplayer.listName, true, 1000);
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      
      if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
        entityplayer1.netServerHandler.sendPacket(packet);
      }
    }
    

    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      

      if (entityplayer.getBukkitEntity().canSee(entityplayer1.getBukkitEntity())) {
        entityplayer.netServerHandler.sendPacket(new Packet201PlayerInfo(entityplayer1.listName, true, entityplayer1.ping));
      }
    }
  }
  
  public void d(EntityPlayer entityplayer)
  {
    getPlayerManager(entityplayer.dimension).movePlayer(entityplayer);
  }
  
  public String disconnect(EntityPlayer entityplayer) {
    if (entityplayer.netServerHandler.disconnected) { return null;
    }
    

    getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
    PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayer), "§e" + entityplayer.name + " left the game.");
    this.cserver.getPluginManager().callEvent(playerQuitEvent);
    

    this.playerFileData.save(entityplayer);
    this.server.getWorldServer(entityplayer.dimension).kill(entityplayer);
    this.players.remove(entityplayer);
    getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
    
    Packet201PlayerInfo packet = new Packet201PlayerInfo(entityplayer.listName, false, 9999);
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer1 = (EntityPlayer)this.players.get(i);
      
      if (entityplayer1.getBukkitEntity().canSee(entityplayer.getBukkitEntity())) {
        entityplayer1.netServerHandler.sendPacket(packet);
      }
    }
    

    return playerQuitEvent.getQuitMessage();
  }
  



  public EntityPlayer attemptLogin(NetLoginHandler netloginhandler, String s, String hostname)
  {
    EntityPlayer entity = new EntityPlayer(this.server, this.server.getWorldServer(0), s, new ItemInWorldManager(this.server.getWorldServer(0)));
    Player player = entity.getBukkitEntity();
    PlayerLoginEvent event = new PlayerLoginEvent(player, hostname, netloginhandler.getSocket().getInetAddress());
    
    String s1 = netloginhandler.networkManager.getSocketAddress().toString();
    
    s1 = s1.substring(s1.indexOf("/") + 1);
    s1 = s1.substring(0, s1.indexOf(":"));
    
    if (this.banByName.contains(s.trim().toLowerCase())) {
      event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned from this server!");
    } else if (!isWhitelisted(s)) {
      event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not white-listed on this server!");
    } else if (this.banByIP.contains(s1)) {
      event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Your IP address is banned from this server!");
    } else if (this.players.size() >= this.maxPlayers) {
      event.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
    } else {
      event.disallow(PlayerLoginEvent.Result.ALLOWED, s1);
    }
    
    b(entity);
    this.cserver.getPluginManager().callEvent(event);
    if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
      netloginhandler.disconnect(event.getKickMessage());
      return null;
    }
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
      
      if (entityplayer.name.equalsIgnoreCase(s)) {
        entityplayer.netServerHandler.disconnect("You logged in from another location");
        b(entity);
      }
    }
    
    return entity;
  }
  

  public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag)
  {
    return moveToWorld(entityplayer, i, flag, null);
  }
  
  public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location)
  {
    this.server.getTracker(entityplayer.dimension).untrackPlayer(entityplayer);
    
    getPlayerManager(entityplayer.dimension).removePlayer(entityplayer);
    this.players.remove(entityplayer);
    this.server.getWorldServer(entityplayer.dimension).removeEntity(entityplayer);
    ChunkCoordinates chunkcoordinates = entityplayer.getBed();
    

    EntityPlayer entityplayer1 = entityplayer;
    World fromWorld = entityplayer1.getBukkitEntity().getWorld();
    
    if (flag) {
      entityplayer1.copyTo(entityplayer);
    }
    
    if (location == null) {
      boolean isBedSpawn = false;
      CraftWorld cworld = (CraftWorld)this.server.server.getWorld(entityplayer.spawnWorld);
      if ((cworld != null) && (chunkcoordinates != null)) {
        ChunkCoordinates chunkcoordinates1 = EntityHuman.getBed(cworld.getHandle(), chunkcoordinates);
        if (chunkcoordinates1 != null) {
          isBedSpawn = true;
          location = new Location(cworld, chunkcoordinates1.x + 0.5D, chunkcoordinates1.y, chunkcoordinates1.z + 0.5D);
        } else {
          entityplayer1.netServerHandler.sendPacket(new Packet70Bed(0, 0));
        }
      }
      
      if (location == null) {
        cworld = (CraftWorld)this.server.server.getWorlds().get(0);
        chunkcoordinates = cworld.getHandle().getSpawn();
        location = new Location(cworld, chunkcoordinates.x + 0.5D, chunkcoordinates.y, chunkcoordinates.z + 0.5D);
      }
      
      Player respawnPlayer = this.cserver.getPlayer(entityplayer);
      PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
      this.cserver.getPluginManager().callEvent(respawnEvent);
      
      location = respawnEvent.getRespawnLocation();
      entityplayer.reset();
    } else {
      location.setWorld(this.server.getWorldServer(i).getWorld());
    }
    WorldServer worldserver = ((CraftWorld)location.getWorld()).getHandle();
    entityplayer1.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    

    worldserver.chunkProviderServer.getChunkAt((int)entityplayer1.locX >> 4, (int)entityplayer1.locZ >> 4);
    
    while (worldserver.getCubes(entityplayer1, entityplayer1.boundingBox).size() != 0) {
      entityplayer1.setPosition(entityplayer1.locX, entityplayer1.locY + 1.0D, entityplayer1.locZ);
    }
    


    int actualDimension = worldserver.getWorld().getEnvironment().getId();
    
    entityplayer1.netServerHandler.sendPacket(new Packet9Respawn((byte)(actualDimension >= 0 ? -1 : 0), (byte)worldserver.difficulty, worldserver.getWorldData().getType(), worldserver.getHeight(), entityplayer.itemInWorldManager.getGameMode()));
    entityplayer1.netServerHandler.sendPacket(new Packet9Respawn(actualDimension, (byte)worldserver.difficulty, worldserver.getWorldData().getType(), worldserver.getHeight(), entityplayer.itemInWorldManager.getGameMode()));
    entityplayer1.spawnIn(worldserver);
    entityplayer1.dead = false;
    entityplayer1.netServerHandler.teleport(new Location(worldserver.getWorld(), entityplayer1.locX, entityplayer1.locY, entityplayer1.locZ, entityplayer1.yaw, entityplayer1.pitch));
    
    a(entityplayer1, worldserver);
    getPlayerManager(entityplayer1.dimension).addPlayer(entityplayer1);
    worldserver.addEntity(entityplayer1);
    this.players.add(entityplayer1);
    updateClient(entityplayer1);
    entityplayer1.E();
    
    if (fromWorld != location.getWorld()) {
      PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(entityplayer1.getBukkitEntity(), fromWorld);
      Bukkit.getServer().getPluginManager().callEvent(event);
    }
    

    return entityplayer1;
  }
  
  public void changeDimension(EntityPlayer entityplayer, int i)
  {
    int dimension = i;
    WorldServer fromWorld = this.server.getWorldServer(entityplayer.dimension);
    WorldServer toWorld = (WorldServer)DimensionManager.getWorld(i);
    
    Location fromLocation = new Location(fromWorld.getWorld(), entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
    Location toLocation = null;
    
    if (toWorld != null) {
      if ((DimensionManager.getProvider(fromWorld.dimension) != null) && (DimensionManager.getProvider(toWorld.dimension) != null)) {
        double fromScale = fromWorld.worldProvider.getMovementFactor();
        double toScale = toWorld.worldProvider.getMovementFactor();
        double blockRatio = fromScale / toScale;
        toLocation = toWorld == null ? null : new Location(toWorld.getWorld(), entityplayer.locX * blockRatio, entityplayer.locY, entityplayer.locZ * blockRatio, entityplayer.yaw, entityplayer.pitch);
      } else {
        ChunkCoordinates coords = toWorld.getDimensionSpawn();
        if (coords != null) {
          toLocation = new Location(toWorld.getWorld(), coords.x, coords.y, coords.z, 90.0F, 0.0F);
        }
      }
    }
    
    PlayerTeleportEvent.TeleportCause cause = PlayerTeleportEvent.TeleportCause.UNKNOWN;
    int playerEnvironmentId = entityplayer.getBukkitEntity().getWorld().getEnvironment().getId();
    switch (dimension) {
    case -1: 
      cause = PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
      break;
    case 0: 
      if (playerEnvironmentId == -1) {
        cause = PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
      } else if (playerEnvironmentId == 1) {
        cause = PlayerTeleportEvent.TeleportCause.END_PORTAL;
      }
      
      break;
    case 1: 
      cause = PlayerTeleportEvent.TeleportCause.END_PORTAL;
    }
    
    
    PortalTravelAgent pta = new PortalTravelAgent();
    PlayerPortalEvent event = new PlayerPortalEvent(entityplayer.getBukkitEntity(), fromLocation, toLocation, pta, cause);
    
    if (entityplayer.dimension == 1) {
      event.useTravelAgent(false);
    }
    
    Bukkit.getServer().getPluginManager().callEvent(event);
    if ((event.isCancelled()) || (event.getTo() == null)) {
      return;
    }
    
    Location finalLocation = event.getTo();
    if (event.useTravelAgent()) {
      finalLocation = event.getPortalTravelAgent().findOrCreate(finalLocation);
    }
    toWorld = ((CraftWorld)finalLocation.getWorld()).getHandle();
    moveToWorld(entityplayer, toWorld.dimension, true, finalLocation);
    
    FMLBukkitHandler.instance().announceDimensionChange(entityplayer);
  }
  
  public void tick() {
    if (++this.p > 200) {
      this.p = 0;
    }
    








    for (int i = 0; i < this.server.worlds.size(); i++) {
      ((WorldServer)this.server.worlds.get(i)).manager.flush();
    }
  }
  
  public void flagDirty(int i, int j, int k, int l)
  {
    getPlayerManager(l).flagDirty(i, j, k);
  }
  
  public void sendAll(Packet packet) {
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
      
      entityplayer.netServerHandler.sendPacket(packet);
    }
  }
  
  public void a(Packet packet, int i) {
    for (int j = 0; j < this.players.size(); j++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
      
      if (entityplayer.dimension == i) {
        entityplayer.netServerHandler.sendPacket(packet);
      }
    }
  }
  
  public String c() {
    String s = "";
    
    for (int i = 0; i < this.players.size(); i++) {
      if (i > 0) {
        s = s + ", ";
      }
      
      s = s + ((EntityPlayer)this.players.get(i)).name;
    }
    
    return s;
  }
  
  public String[] d() {
    String[] astring = new String[this.players.size()];
    
    for (int i = 0; i < this.players.size(); i++) {
      astring[i] = ((EntityPlayer)this.players.get(i)).name;
    }
    
    return astring;
  }
  
  public void addUserBan(String s) {
    this.banByName.add(s.toLowerCase());
    m();
  }
  
  public void removeUserBan(String s) {
    this.banByName.remove(s.toLowerCase());
    m();
  }
  
  private void l() {
    try {
      this.banByName.clear();
      BufferedReader bufferedreader = new BufferedReader(new FileReader(this.j));
      String s = "";
      
      while ((s = bufferedreader.readLine()) != null) {
        this.banByName.add(s.trim().toLowerCase());
      }
      
      bufferedreader.close();
    } catch (Exception exception) {
      a.warning("Failed to load ban list: " + exception);
    }
  }
  
  private void m() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(this.j, false));
      Iterator iterator = this.banByName.iterator();
      
      while (iterator.hasNext()) {
        String s = (String)iterator.next();
        
        printwriter.println(s);
      }
      
      printwriter.close();
    } catch (Exception exception) {
      a.warning("Failed to save ban list: " + exception);
    }
  }
  
  public Set getBannedPlayers() {
    return this.banByName;
  }
  
  public Set getBannedAddresses() {
    return this.banByIP;
  }
  
  public void addIpBan(String s) {
    this.banByIP.add(s.toLowerCase());
    o();
  }
  
  public void removeIpBan(String s) {
    this.banByIP.remove(s.toLowerCase());
    o();
  }
  
  private void n() {
    try {
      this.banByIP.clear();
      BufferedReader bufferedreader = new BufferedReader(new FileReader(this.k));
      String s = "";
      
      while ((s = bufferedreader.readLine()) != null) {
        this.banByIP.add(s.trim().toLowerCase());
      }
      
      bufferedreader.close();
    } catch (Exception exception) {
      a.warning("Failed to load ip ban list: " + exception);
    }
  }
  
  private void o() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(this.k, false));
      Iterator iterator = this.banByIP.iterator();
      
      while (iterator.hasNext()) {
        String s = (String)iterator.next();
        
        printwriter.println(s);
      }
      
      printwriter.close();
    } catch (Exception exception) {
      a.warning("Failed to save ip ban list: " + exception);
    }
  }
  
  public void addOp(String s) {
    this.operators.add(s.toLowerCase());
    q();
    

    Player player = this.server.server.getPlayer(s);
    if (player != null) {
      player.recalculatePermissions();
    }
  }
  
  public void removeOp(String s)
  {
    this.operators.remove(s.toLowerCase());
    q();
    

    Player player = this.server.server.getPlayer(s);
    if (player != null) {
      player.recalculatePermissions();
    }
  }
  
  private void p()
  {
    try {
      this.operators.clear();
      BufferedReader bufferedreader = new BufferedReader(new FileReader(this.l));
      String s = "";
      
      while ((s = bufferedreader.readLine()) != null) {
        this.operators.add(s.trim().toLowerCase());
      }
      
      bufferedreader.close();
    } catch (Exception exception) {
      a.warning("Failed to load operators list: " + exception);
    }
  }
  
  private void q() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(this.l, false));
      Iterator iterator = this.operators.iterator();
      
      while (iterator.hasNext()) {
        String s = (String)iterator.next();
        
        printwriter.println(s);
      }
      
      printwriter.close();
    } catch (Exception exception) {
      a.warning("Failed to save operators list: " + exception);
    }
  }
  
  private void r() {
    try {
      this.whitelist.clear();
      BufferedReader bufferedreader = new BufferedReader(new FileReader(this.m));
      String s = "";
      
      while ((s = bufferedreader.readLine()) != null) {
        this.whitelist.add(s.trim().toLowerCase());
      }
      
      bufferedreader.close();
    } catch (Exception exception) {
      a.warning("Failed to load white-list: " + exception);
    }
  }
  
  private void s() {
    try {
      PrintWriter printwriter = new PrintWriter(new FileWriter(this.m, false));
      Iterator iterator = this.whitelist.iterator();
      
      while (iterator.hasNext()) {
        String s = (String)iterator.next();
        
        printwriter.println(s);
      }
      
      printwriter.close();
    } catch (Exception exception) {
      a.warning("Failed to save white-list: " + exception);
    }
  }
  
  public boolean isWhitelisted(String s) {
    s = s.trim().toLowerCase();
    return (!this.hasWhitelist) || (this.operators.contains(s)) || (this.whitelist.contains(s));
  }
  
  public boolean isOp(String s) {
    return this.operators.contains(s.trim().toLowerCase());
  }
  
  public EntityPlayer i(String s) {
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
      
      if (entityplayer.name.equalsIgnoreCase(s)) {
        return entityplayer;
      }
    }
    
    return null;
  }
  
  public void a(String s, String s1) {
    EntityPlayer entityplayer = i(s);
    
    if (entityplayer != null) {
      entityplayer.netServerHandler.sendPacket(new Packet3Chat(s1));
    }
  }
  
  public void sendPacketNearby(double d0, double d1, double d2, double d3, int i, Packet packet) {
    sendPacketNearby((EntityHuman)null, d0, d1, d2, d3, i, packet);
  }
  
  public void sendPacketNearby(EntityHuman entityhuman, double d0, double d1, double d2, double d3, int i, Packet packet) {
    for (int j = 0; j < this.players.size(); j++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
      
      if ((entityplayer != entityhuman) && (entityplayer.dimension == i)) {
        double d4 = d0 - entityplayer.locX;
        double d5 = d1 - entityplayer.locY;
        double d6 = d2 - entityplayer.locZ;
        
        if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
          entityplayer.netServerHandler.sendPacket(packet);
        }
      }
    }
  }
  
  public void j(String s) {
    Packet3Chat packet3chat = new Packet3Chat(s);
    
    for (int i = 0; i < this.players.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);
      
      if (isOp(entityplayer.name)) {
        entityplayer.netServerHandler.sendPacket(packet3chat);
      }
    }
  }
  
  public boolean a(String s, Packet packet) {
    EntityPlayer entityplayer = i(s);
    
    if (entityplayer != null) {
      entityplayer.netServerHandler.sendPacket(packet);
      return true;
    }
    return false;
  }
  
  public void savePlayers()
  {
    for (int i = 0; i < this.players.size(); i++) {
      this.playerFileData.save((EntityHuman)this.players.get(i));
    }
  }
  
  public void a(int i, int j, int k, TileEntity tileentity) {}
  
  public void addWhitelist(String s) {
    this.whitelist.add(s);
    s();
  }
  
  public void removeWhitelist(String s) {
    this.whitelist.remove(s);
    s();
  }
  
  public Set getWhitelisted() {
    return this.whitelist;
  }
  
  public void reloadWhitelist() {
    r();
  }
  
  public void a(EntityPlayer entityplayer, WorldServer worldserver) {
    entityplayer.netServerHandler.sendPacket(new Packet4UpdateTime(worldserver.getTime()));
    if (worldserver.x()) {
      entityplayer.netServerHandler.sendPacket(new Packet70Bed(1, 0));
    }
  }
  
  public void updateClient(EntityPlayer entityplayer) {
    entityplayer.updateInventory(entityplayer.defaultContainer);
    entityplayer.D_();
    entityplayer.lastSentExp = -1;
  }
  
  public int getPlayerCount() {
    return this.players.size();
  }
  
  public int getMaxPlayers() {
    return this.maxPlayers;
  }
  
  public String[] getSeenPlayers() {
    return ((WorldServer)this.server.worlds.get(0)).getDataManager().getPlayerFileData().getSeenPlayers();
  }
  
  private void u() {
    MojangStatisticsGenerator mojangstatisticsgenerator = new MojangStatisticsGenerator("server");
    
    mojangstatisticsgenerator.a("version", this.server.getVersion());
    mojangstatisticsgenerator.a("os_name", System.getProperty("os.name"));
    mojangstatisticsgenerator.a("os_version", System.getProperty("os.version"));
    mojangstatisticsgenerator.a("os_architecture", System.getProperty("os.arch"));
    mojangstatisticsgenerator.a("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
    mojangstatisticsgenerator.a("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
    mojangstatisticsgenerator.a("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
    mojangstatisticsgenerator.a("java_version", System.getProperty("java.version"));
    mojangstatisticsgenerator.a("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
    mojangstatisticsgenerator.a("players_current", Integer.valueOf(getPlayerCount()));
    mojangstatisticsgenerator.a("players_max", Integer.valueOf(getMaxPlayers()));
    mojangstatisticsgenerator.a("players_seen", Integer.valueOf(getSeenPlayers().length));
    mojangstatisticsgenerator.a("uses_auth", Boolean.valueOf(this.server.onlineMode));
    mojangstatisticsgenerator.a("server_brand", this.server.getServerModName());
    mojangstatisticsgenerator.a();
  }
}
