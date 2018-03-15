package org.bukkit.craftbukkit;

import jline.console.ConsoleReader; // BTCS

import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import forge.DimensionManager;
import forge.bukkit.ForgeCommandMap;
import forge.bukkit.ForgePluginManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.OptionSet;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.ConvertProgressUpdater;
import net.minecraft.server.Convertable;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.Item;
import net.minecraft.server.ItemWorldMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.MobEffectList;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.PropertyManager;
import net.minecraft.server.ServerCommand;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.ServerNBTManager;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldLoaderServer;
import net.minecraft.server.WorldManager;
import net.minecraft.server.WorldMap;
import net.minecraft.server.WorldMapCollection;
import net.minecraft.server.WorldNBTStorage;
import net.minecraft.server.WorldServer;
import net.minecraft.server.WorldSettings;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.Conversable;
import org.bukkit.craftbukkit.help.SimpleHelpMap;
import org.bukkit.craftbukkit.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.inventory.RecipeIterator;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.potion.CraftPotionBrewer;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.updater.ArtifactDetails;
import org.bukkit.craftbukkit.updater.AutoUpdater;
import org.bukkit.craftbukkit.updater.BukkitDLUpdaterService;
import org.bukkit.craftbukkit.util.DatFileFilter;
import org.bukkit.craftbukkit.util.Versioning;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.util.permissions.DefaultPermissions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;

public final class CraftServer implements Server
{
  private final String serverName = "BTCS";
  private final String serverVersion;
  private final String bukkitVersion = Versioning.getBukkitVersion();
  private final ServicesManager servicesManager = new SimpleServicesManager();
  private final BukkitScheduler scheduler = new CraftScheduler();
  private final ForgeCommandMap commandMap;
  private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
  private final StandardMessenger messenger = new StandardMessenger();
  private final PluginManager pluginManager;
  protected final MinecraftServer console;
  protected final ServerConfigurationManager server;
  private final Map<String, org.bukkit.World> worlds = new LinkedHashMap();
  private YamlConfiguration configuration;
  private final Yaml yaml = new Yaml(new SafeConstructor());
  private final Map<String, OfflinePlayer> offlinePlayers = new MapMaker().softValues().makeMap();
  private AutoUpdater updater;
  private final EntityMetadataStore entityMetadata = new EntityMetadataStore();
  private final PlayerMetadataStore playerMetadata = new PlayerMetadataStore();
  private final WorldMetadataStore worldMetadata = new WorldMetadataStore();
  private int monsterSpawn = -1;
  private int animalSpawn = -1;
  private int waterAnimalSpawn = -1;
  
  static {
    ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
  }
  
  public CraftServer(MinecraftServer console, ServerConfigurationManager server) {
    this.commandMap = new ForgeCommandMap(this, console);
    this.pluginManager = new ForgePluginManager(this, this.commandMap);
    this.console = console;
    this.server = server;
    this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
    
    Bukkit.setServer(this);
    

    net.minecraft.server.Enchantment.DAMAGE_ALL.getClass();
    org.bukkit.enchantments.Enchantment.stopAcceptingRegistrations();
    
    Potion.setPotionBrewer(new CraftPotionBrewer());
    MobEffectList.BLINDNESS.getClass();
    PotionEffectType.stopAcceptingRegistrations();
    

    if (!nl.hypothermic.btcs.Launcher.useConsole) { // BTCS: 'Main' --> 'Launcher'
      getLogger().info("Console input is disabled due to --noconsole command argument");
    }
    
    this.configuration = YamlConfiguration.loadConfiguration(getConfigFile());
    this.configuration.options().copyDefaults(true);
    this.configuration.setDefaults(YamlConfiguration.loadConfiguration(getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml")));
    saveConfig();
    ((SimplePluginManager)this.pluginManager).useTimings(this.configuration.getBoolean("settings.plugin-profiling"));
    this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
    this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
    this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
    
    this.updater = new AutoUpdater(new BukkitDLUpdaterService(this.configuration.getString("auto-updater.host")), getLogger(), this.configuration.getString("auto-updater.preferred-channel"));
    this.updater.setEnabled(this.configuration.getBoolean("auto-updater.enabled"));
    this.updater.setSuggestChannels(this.configuration.getBoolean("auto-updater.suggest-channels"));
    this.updater.getOnBroken().addAll(this.configuration.getStringList("auto-updater.on-broken"));
    this.updater.getOnUpdate().addAll(this.configuration.getStringList("auto-updater.on-update"));
    this.updater.check(this.serverVersion);
    
    loadPlugins();
    enablePlugins(PluginLoadOrder.STARTUP);
    
    ChunkCompressionThread.startThread();
  }
  
  private File getConfigFile() {
    return (File)this.console.options.valueOf("bukkit-settings");
  }
  
  private void saveConfig() {
    try {
      this.configuration.save(getConfigFile());
    } catch (IOException ex) {
      Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getConfigFile(), ex);
    }
  }
  
  public void loadPlugins() {
    this.pluginManager.registerInterface(JavaPluginLoader.class);
    
    File pluginFolder = (File)this.console.options.valueOf("plugins");
    
    if (pluginFolder.exists()) {
      Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
      for (Plugin plugin : plugins) {
        try {
          String message = String.format("Loading %s", new Object[] { plugin.getDescription().getFullName() });
          plugin.getLogger().info(message);
          plugin.onLoad();
        } catch (Throwable ex) {
          Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
        }
      }
    } else {
      pluginFolder.mkdir();
    }
  }
  
  public void enablePlugins(PluginLoadOrder type) {
    if (type == PluginLoadOrder.STARTUP) {
      this.helpMap.clear();
      this.helpMap.initializeGeneralTopics();
    }
    
    Plugin[] plugins = this.pluginManager.getPlugins();
    
    for (Plugin plugin : plugins) {
      if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type)) {
        loadPlugin(plugin);
      }
    }
    
    if (type == PluginLoadOrder.POSTWORLD) {
      this.commandMap.registerServerAliases();
      loadCustomPermissions();
      DefaultPermissions.registerCorePermissions();
      this.helpMap.initializeCommands();
    }
  }
  
  public void disablePlugins() {
    this.pluginManager.disablePlugins();
  }
  
  private void loadPlugin(Plugin plugin) {
    try {
      this.pluginManager.enablePlugin(plugin);
      
      List<Permission> perms = plugin.getDescription().getPermissions();
      
      for (Permission perm : perms) {
        try {
          this.pluginManager.addPermission(perm);
        } catch (IllegalArgumentException ex) {
          getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
        }
      }
    } catch (Throwable ex) {
      Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
    }
  }
  
  public String getName() {
    return "BTCS";
  }
  
  public String getVersion() {
    return this.serverVersion + " (MC: " + this.console.getVersion() + ")";
  }
  
  public String getBukkitVersion() {
    return this.bukkitVersion;
  }
  
  public Player[] getOnlinePlayers()
  {
    List<EntityPlayer> online = this.server.players;
    Player[] players = new Player[online.size()];
    
    for (int i = 0; i < players.length; i++) {
      players[i] = ((EntityPlayer)online.get(i)).netServerHandler.getPlayer();
    }
    
    return players;
  }
  
  public Player getPlayer(String name) {
    Player[] players = getOnlinePlayers();
    
    Player found = null;
    String lowerName = name.toLowerCase();
    int delta = Integer.MAX_VALUE;
    for (Player player : players)
      if (player.getName().toLowerCase().startsWith(lowerName)) {
        int curDelta = player.getName().length() - lowerName.length();
        if (curDelta < delta) {
          found = player;
          delta = curDelta;
        }
        if (curDelta == 0)
          break;
      }
    return found;
  }
  
  public Player getPlayerExact(String name) {
    String lname = name.toLowerCase();
    
    for (Player player : getOnlinePlayers()) {
      if (player.getName().equalsIgnoreCase(lname)) {
        return player;
      }
    }
    
    return null;
  }
  
  public int broadcastMessage(String message) {
    return broadcast(message, "bukkit.broadcast.user");
  }
  
  public Player getPlayer(EntityPlayer entity) {
    return entity.netServerHandler.getPlayer();
  }
  
  public List<Player> matchPlayer(String partialName) {
    List<Player> matchedPlayers = new ArrayList();
    
    for (Player iterPlayer : getOnlinePlayers()) {
      String iterPlayerName = iterPlayer.getName();
      
      if (partialName.equalsIgnoreCase(iterPlayerName))
      {
        matchedPlayers.clear();
        matchedPlayers.add(iterPlayer);
        break;
      }
      if (iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase()) != -1)
      {
        matchedPlayers.add(iterPlayer);
      }
    }
    
    return matchedPlayers;
  }
  
  public int getMaxPlayers() {
    return this.server.maxPlayers;
  }
  

  public int getPort()
  {
    return getConfigInt("server-port", 25565);
  }
  
  public int getViewDistance() {
    return getConfigInt("view-distance", 10);
  }
  
  public String getIp() {
    return getConfigString("server-ip", "");
  }
  
  public String getServerName() {
    return getConfigString("server-name", "Unknown Server");
  }
  
  public String getServerId() {
    return getConfigString("server-id", "unnamed");
  }
  
  public String getWorldType() {
    return getConfigString("level-type", "DEFAULT");
  }
  
  public boolean getGenerateStructures() {
    return getConfigBoolean("generate-structures", true);
  }
  
  public boolean getAllowEnd() {
    return this.configuration.getBoolean("settings.allow-end");
  }
  
  public boolean getAllowNether() {
    return getConfigBoolean("allow-nether", true);
  }
  
  public boolean getWarnOnOverload() {
    return this.configuration.getBoolean("settings.warn-on-overload");
  }
  
  public boolean getKickOnSpeedHack()
  {
    return this.configuration.getBoolean("settings.kick-on-speedhack");
  }
  
  public String getOutdatedServerMessage() {
    return this.configuration.getString("settings.outdated-server-message", "Outdated server!");
  }
  
  public String getOutdatedClientMessage() {
    return this.configuration.getString("settings.outdated-client-message", "Outdated client!");
  }
  

  public boolean getQueryPlugins()
  {
    return this.configuration.getBoolean("settings.query-plugins");
  }
  
  public boolean hasWhitelist() {
    return getConfigBoolean("white-list", false);
  }
  
  private String getConfigString(String variable, String defaultValue)
  {
    return this.console.propertyManager.getString(variable, defaultValue);
  }
  
  private int getConfigInt(String variable, int defaultValue) {
    return this.console.propertyManager.getInt(variable, defaultValue);
  }
  
  private boolean getConfigBoolean(String variable, boolean defaultValue) {
    return this.console.propertyManager.getBoolean(variable, defaultValue);
  }
  

  public String getUpdateFolder()
  {
    return this.configuration.getString("settings.update-folder", "update");
  }
  
  public File getUpdateFolderFile() {
    return new File((File)this.console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
  }
  
  public int getPingPacketLimit() {
    return this.configuration.getInt("settings.ping-packet-limit", 100);
  }
  
  public long getConnectionThrottle() {
    return this.configuration.getInt("settings.connection-throttle");
  }
  
  public int getTicksPerAnimalSpawns() {
    return this.configuration.getInt("ticks-per.animal-spawns");
  }
  
  public int getTicksPerMonsterSpawns() {
    return this.configuration.getInt("ticks-per.monster-spawns");
  }
  
  public PluginManager getPluginManager() {
    return this.pluginManager;
  }
  
  public BukkitScheduler getScheduler() {
    return this.scheduler;
  }
  
  public ServicesManager getServicesManager() {
    return this.servicesManager;
  }
  
  public List<org.bukkit.World> getWorlds() {
    return new ArrayList(this.worlds.values());
  }
  
  public ServerConfigurationManager getHandle() {
    return this.server;
  }
  
  public boolean dispatchServerCommand(CommandSender sender, ServerCommand serverCommand)
  {
    if ((sender instanceof Conversable)) {
      Conversable conversable = (Conversable)sender;
      
      if (conversable.isConversing()) {
        conversable.acceptConversationInput(serverCommand.command);
        return true;
      }
    }
    return dispatchCommand(sender, serverCommand.command);
  }
  
  public boolean dispatchCommand(CommandSender sender, String commandLine) {
    if (this.commandMap.dispatch(sender, commandLine)) {
      return true;
    }
    
    sender.sendMessage("Unknown command. Type \"help\" for help.");
    
    return false;
  }
  
  public void reload() {
    this.configuration = YamlConfiguration.loadConfiguration(getConfigFile());
    PropertyManager config = new PropertyManager(this.console.options);
    
    this.console.propertyManager = config;
    
    boolean animals = config.getBoolean("spawn-animals", this.console.spawnAnimals);
    boolean monsters = config.getBoolean("spawn-monsters", ((WorldServer)this.console.worlds.get(0)).difficulty > 0);
    int difficulty = config.getInt("difficulty", ((WorldServer)this.console.worlds.get(0)).difficulty);
    
    this.console.onlineMode = config.getBoolean("online-mode", this.console.onlineMode);
    this.console.spawnAnimals = config.getBoolean("spawn-animals", this.console.spawnAnimals);
    this.console.pvpMode = config.getBoolean("pvp", this.console.pvpMode);
    this.console.allowFlight = config.getBoolean("allow-flight", this.console.allowFlight);
    this.console.motd = config.getString("motd", this.console.motd);
    this.monsterSpawn = this.configuration.getInt("spawn-limits.monsters");
    this.animalSpawn = this.configuration.getInt("spawn-limits.animals");
    this.waterAnimalSpawn = this.configuration.getInt("spawn-limits.water-animals");
    
    for (WorldServer world : this.console.worlds) {
      world.difficulty = difficulty;
      world.setSpawnFlags(monsters, animals);
      if (getTicksPerAnimalSpawns() < 0) {
        world.ticksPerAnimalSpawns = 400L;
      } else {
        world.ticksPerAnimalSpawns = getTicksPerAnimalSpawns();
      }
      
      if (getTicksPerMonsterSpawns() < 0) {
        world.ticksPerMonsterSpawns = 1L;
      } else {
        world.ticksPerMonsterSpawns = getTicksPerMonsterSpawns();
      }
    }
    
    this.pluginManager.clearPlugins();
    this.commandMap.clearCommands();
    resetRecipes();
    
    int pollCount = 0;
    

    while ((pollCount < 50) && (getScheduler().getActiveWorkers().size() > 0)) {
      try {
        Thread.sleep(50L);
      } catch (InterruptedException e) {}
      pollCount++;
    }
    
    List<BukkitWorker> overdueWorkers = getScheduler().getActiveWorkers();
    for (BukkitWorker worker : overdueWorkers) {
      Plugin plugin = worker.getOwner();
      String author = "<NoAuthorGiven>";
      if (plugin.getDescription().getAuthors().size() > 0) {
        author = (String)plugin.getDescription().getAuthors().get(0);
      }
      getLogger().log(Level.SEVERE, String.format("Nag author: '%s' of '%s' about the following: %s", new Object[] { author, plugin.getDescription().getName(), "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin" }));
    }
    




    loadPlugins();
    enablePlugins(PluginLoadOrder.STARTUP);
    enablePlugins(PluginLoadOrder.POSTWORLD);
  }
  
  private void loadCustomPermissions()
  {
    File file = new File(this.configuration.getString("settings.permissions-file"));
    FileInputStream stream;
    try
    {
      stream = new FileInputStream(file);
    } catch (FileNotFoundException ex) {
      try {
        file.createNewFile();
      }
      finally
      {
        return;
      }
    }
    Map<String, Map<String, Object>> perms;
    try {
      perms = (Map)this.yaml.load(stream);
      try
      {
        stream.close();
      }
      catch (IOException ex) {}
      
      if (perms != null) {
    	  // BTCS start: replaced break with label with function
    	  List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION); // BTCS: Object --> List<Permission>
    	    
    	  for (Permission perm : permsList) {
    		  try {
    	          this.pluginManager.addPermission(perm);
    	      } catch (IllegalArgumentException ex) {
    	    	  getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
    	      }
    	  }
    	  return;
    	  // BTCS end
      }
    }
    catch (MarkedYAMLException ex)
    {
      getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString()); return;
    }
    catch (Throwable ex) {
      getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex); return;
    }
    finally {
      try {
        stream.close();
      }
      catch (IOException ex) {}
    }
    
    getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
    
    // BTCS: moved function to ln 608.
  }
  
  public String toString()
  {
    return "CraftServer{serverName=CraftBukkit,serverVersion=" + this.serverVersion + ",minecraftVersion=" + this.console.getVersion() + '}';
  }
  
  // BTCS: until line 663: replaced every World with org.bukkit.World
  public org.bukkit.World createWorld(String name, org.bukkit.World.Environment environment) {
    return WorldCreator.name(name).environment(environment).createWorld();
  }
  
  public org.bukkit.World createWorld(String name, org.bukkit.World.Environment environment, long seed) {
    return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
  }
  
  public org.bukkit.World createWorld(String name, org.bukkit.World.Environment environment, ChunkGenerator generator) {
    return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
  }
  
  public org.bukkit.World createWorld(String name, org.bukkit.World.Environment environment, long seed, ChunkGenerator generator) {
    return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
  }
  
  public org.bukkit.World createWorld(WorldCreator creator) {
    if (creator == null) {
      throw new IllegalArgumentException("Creator may not be null");
    }
    
    String name = creator.name();
    ChunkGenerator generator = creator.generator();
    File folder = new File(getWorldContainer(), name);
    org.bukkit.World world = getWorld(name);
    net.minecraft.server.WorldType type = net.minecraft.server.WorldType.getType(creator.type().getName());
    boolean generateStructures = creator.generateStructures();
    
    if (world != null) {
      return world;
    }
    
    if ((folder.exists()) && (!folder.isDirectory())) {
      throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
    }
    
    if (generator == null) {
      generator = getGenerator(name);
    }
    
    Convertable converter = new WorldLoaderServer(getWorldContainer());
    if (converter.isConvertable(name)) {
      getLogger().info("Converting world '" + name + "'");
      converter.convert(name, new ConvertProgressUpdater(this.console));
    }
    
    int dimension = 0;
    for (net.minecraft.server.World w : DimensionManager.getWorlds())
    {
      WorldServer ws = (WorldServer)w;
      dimension = Math.max(ws.dimension, dimension);
    }
    dimension++;
    boolean hardcore = false;
    
    WorldServer internal = new WorldServer(this.console, new ServerNBTManager(getWorldContainer(), name, true), name, dimension, new WorldSettings(creator.seed(), getDefaultGameMode().getValue(), generateStructures, hardcore, type), creator.environment(), generator);
    
    if (!this.worlds.containsKey(name.toLowerCase())) {
      return null;
    }
    
    internal.worldMaps = ((WorldServer)this.console.worlds.get(0)).worldMaps;
    
    internal.tracker = new EntityTracker(this.console, internal);
    internal.addIWorldAccess(new WorldManager(this.console, internal));
    internal.difficulty = 1;
    internal.setSpawnFlags(true, true);
    this.console.worlds.add(internal);
    
    if (generator != null) {
      internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
    }
    
    this.pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
    System.out.print("Preparing start region for level " + (this.console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
    
    if (internal.getWorld().getKeepSpawnInMemory()) {
      short short1 = 196;
      long i = System.currentTimeMillis();
      for (int j = -short1; j <= short1; j += 16) {
        for (int k = -short1; k <= short1; k += 16) {
          long l = System.currentTimeMillis();
          
          if (l < i) {
            i = l;
          }
          
          if (l > i + 1000L) {
            int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
            int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;
            
            System.out.println("Preparing spawn area for " + name + ", " + j1 * 100 / i1 + "%");
            i = l;
          }
          
          ChunkCoordinates chunkcoordinates = internal.getSpawn();
          internal.chunkProviderServer.getChunkAt(chunkcoordinates.x + j >> 4, chunkcoordinates.z + k >> 4);
          
          while (internal.updateLights()) {}
        }
      }
    }
    

    this.pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
    return internal.getWorld();
  }
  
  public boolean unloadWorld(String name, boolean save) {
    return unloadWorld(getWorld(name), save);
  }
  
  public boolean unloadWorld(org.bukkit.World world, boolean save) {
    if (world == null) {
      return false;
    }
    
    WorldServer handle = ((CraftWorld)world).getHandle();
    
    if (!this.console.worlds.contains(handle)) {
      return false;
    }
    
    if (handle.dimension <= 1) {
      return false;
    }
    
    if (handle.players.size() > 0) {
      return false;
    }
    
    WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
    this.pluginManager.callEvent(e);
    
    if (e.isCancelled()) {
      return false;
    }
    
    if (save) {
      handle.save(true, (IProgressUpdate)null);
      handle.saveLevel();
      WorldSaveEvent event = new WorldSaveEvent(handle.getWorld());
      getPluginManager().callEvent(event);
    }
    
    this.worlds.remove(world.getName().toLowerCase());
    this.console.worlds.remove(this.console.worlds.indexOf(handle));
    
    return true;
  }
  
  public MinecraftServer getServer() {
    return this.console;
  }
  
  public org.bukkit.World getWorld(String name) {
    return (org.bukkit.World)this.worlds.get(name.toLowerCase());
  }
  
  public org.bukkit.World getWorld(UUID uid) {
    for (org.bukkit.World world : this.worlds.values()) {
      if (world.getUID().equals(uid)) {
        return world;
      }
    }
    return null;
  }
  
  public void addWorld(org.bukkit.World world)
  {
    if (getWorld(world.getUID()) != null) {
      System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
      return;
    }
    this.worlds.put(world.getName().toLowerCase(), world);
  }
  
  public Logger getLogger() {
    return MinecraftServer.log;
  }
  
  public ConsoleReader getReader() {
    return this.console.reader;
  }
  
  public PluginCommand getPluginCommand(String name) {
    Command command = this.commandMap.getCommand(name);
    
    if ((command instanceof PluginCommand)) {
      return (PluginCommand)command;
    }
    return null;
  }
  
  public void savePlayers()
  {
    this.server.savePlayers();
  }
  
  public void configureDbConfig(ServerConfig config) {
    DataSourceConfig ds = new DataSourceConfig();
    ds.setDriver(this.configuration.getString("database.driver"));
    ds.setUrl(this.configuration.getString("database.url"));
    ds.setUsername(this.configuration.getString("database.username"));
    ds.setPassword(this.configuration.getString("database.password"));
    // BTCS start: add this back if it gives errors.
    /*ds.setIsolationLevel(TransactionIsolation.getLevel(this.configuration.getString("database.isolation")));*/
    // BTCS end
    
    if (ds.getDriver().contains("sqlite")) {
      config.setDatabasePlatform(new SQLitePlatform());
      config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
    }
    
    config.setDataSourceConfig(ds);
  }
  
  public boolean addRecipe(Recipe recipe) {
    CraftRecipe toAdd;
    // BTCS start: general improvements
    if ((recipe instanceof CraftRecipe)) {
      toAdd = (CraftRecipe)recipe;
    } else {
      if ((recipe instanceof ShapedRecipe)) {
        toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe); } else {
        if ((recipe instanceof ShapelessRecipe)) {
          toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe); } else {
          if ((recipe instanceof FurnaceRecipe)) {
            toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
          } else
            return false;
        } } }
    // BTCS end
    toAdd.addToCraftingManager();
    CraftingManager.getInstance().sort();
    return true;
  }
  
  public List<Recipe> getRecipesFor(org.bukkit.inventory.ItemStack result) {
    List<Recipe> results = new ArrayList();
    Iterator<Recipe> iter = recipeIterator();
    while (iter.hasNext()) {
      Recipe recipe = (Recipe)iter.next();
      org.bukkit.inventory.ItemStack stack = recipe.getResult();
      if (stack.getType() == result.getType())
      {

        if ((result.getDurability() == -1) || (result.getDurability() == stack.getDurability()))
          results.add(recipe);
      }
    }
    return results;
  }
  
  public Iterator<Recipe> recipeIterator() {
    return new RecipeIterator();
  }
  
  public void clearRecipes() {
    CraftingManager.getInstance().recipies.clear();
    FurnaceRecipes.getInstance().recipies.clear();
  }
  
  public void resetRecipes() {
    CraftingManager.getInstance().recipies = new CraftingManager().recipies;
    FurnaceRecipes.getInstance().recipies = new FurnaceRecipes().recipies;
  }
  
  public Map<String, String[]> getCommandAliases() {
    ConfigurationSection section = this.configuration.getConfigurationSection("aliases");
    Map<String, String[]> result = new LinkedHashMap();
    
    if (section != null) {
      for (String key : section.getKeys(false)) {
        List<String> commands = null;
        
        if (section.isList(key)) {
          commands = section.getStringList(key);
        } else {
          commands = ImmutableList.of(section.getString(key));
        }
        
        result.put(key, commands.toArray(new String[0]));
      }
    }
    
    return result;
  }
  
  public int getSpawnRadius() {
    return this.configuration.getInt("settings.spawn-radius", 16);
  }
  
  public void setSpawnRadius(int value) {
    this.configuration.set("settings.spawn-radius", Integer.valueOf(value));
    saveConfig();
  }
  
  public boolean getOnlineMode() {
    return this.console.onlineMode;
  }
  
  public boolean getAllowFlight() {
    return this.console.allowFlight;
  }
  
  public boolean useExactLoginLocation() {
    return this.configuration.getBoolean("settings.use-exact-login-location");
  }
  
  public ChunkGenerator getGenerator(String world) {
    ConfigurationSection section = this.configuration.getConfigurationSection("worlds");
    ChunkGenerator result = null;
    
    if (section != null) {
      section = section.getConfigurationSection(world);
      
      if (section != null) {
        String name = section.getString("generator");
        
        if ((name != null) && (!name.equals(""))) {
          String[] split = name.split(":", 2);
          String id = split.length > 1 ? split[1] : null;
          Plugin plugin = this.pluginManager.getPlugin(split[0]);
          
          if (plugin == null) {
            getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
          } else if (!plugin.isEnabled()) {
            getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' is not enabled yet (is it load:STARTUP?)");
          } else {
            result = plugin.getDefaultWorldGenerator(world, id);
          }
        }
      }
    }
    
    return result;
  }
  
  public CraftMapView getMap(short id) {
    WorldMapCollection collection = ((WorldServer)this.console.worlds.get(0)).worldMaps;
    WorldMap worldmap = (WorldMap)collection.get(WorldMap.class, "map_" + id);
    if (worldmap == null) {
      return null;
    }
    return worldmap.mapView;
  }
  
  public CraftMapView createMap(org.bukkit.World world) {
    net.minecraft.server.ItemStack stack = new net.minecraft.server.ItemStack(Item.MAP, 1, -1);
    WorldMap worldmap = Item.MAP.getSavedMap(stack, ((CraftWorld)world).getHandle());
    return worldmap.mapView;
  }
  
  public void shutdown() {
    this.console.safeShutdown();
  }
  
  public int broadcast(String message, String permission) {
    int count = 0;
    Set<Permissible> permissibles = getPluginManager().getPermissionSubscriptions(permission);
    
    for (Permissible permissible : permissibles) {
      if (((permissible instanceof CommandSender)) && (permissible.hasPermission(permission))) {
        CommandSender user = (CommandSender)permissible;
        user.sendMessage(message);
        count++;
      }
    }
    
    return count;
  }
  
  public OfflinePlayer getOfflinePlayer(String name) {
    OfflinePlayer result = getPlayerExact(name);
    String lname = name.toLowerCase();
    
    if (result == null) {
      result = (OfflinePlayer)this.offlinePlayers.get(lname);
      
      if (result == null) {
        result = new CraftOfflinePlayer(this, name);
        this.offlinePlayers.put(lname, result);
      }
    } else {
      this.offlinePlayers.remove(lname);
    }
    
    return result;
  }
  
  public Set<String> getIPBans()
  {
    return new HashSet(this.server.banByIP);
  }
  
  public void banIP(String address) {
    this.server.addIpBan(address);
  }
  
  public void unbanIP(String address) {
    this.server.removeIpBan(address);
  }
  
  public Set<OfflinePlayer> getBannedPlayers() {
    Set<OfflinePlayer> result = new HashSet();
    
    for (Object name : this.server.banByName) {
      result.add(getOfflinePlayer((String)name));
    }
    
    return result;
  }
  
  public void setWhitelist(boolean value) {
    this.server.hasWhitelist = value;
    this.console.propertyManager.setBoolean("white-list", value);
  }
  
  public Set<OfflinePlayer> getWhitelistedPlayers() {
    Set<OfflinePlayer> result = new LinkedHashSet();
    
    for (Object name : this.server.getWhitelisted()) {
      if ((((String)name).length() != 0) && (!((String)name).startsWith("#")))
      {

        result.add(getOfflinePlayer((String)name));
      }
    }
    return result;
  }
  
  public Set<OfflinePlayer> getOperators() {
    Set<OfflinePlayer> result = new HashSet();
    
    for (Object name : this.server.operators) {
      result.add(getOfflinePlayer((String)name));
    }
    
    return result;
  }
  
  public void reloadWhitelist() {
    this.server.reloadWhitelist();
  }
  
  public GameMode getDefaultGameMode() {
    return GameMode.getByValue(((WorldServer)this.console.worlds.get(0)).worldData.getGameType());
  }
  
  public void setDefaultGameMode(GameMode mode) {
    if (mode == null) {
      throw new IllegalArgumentException("Mode cannot be null");
    }
    
    for (org.bukkit.World world : getWorlds()) {
      ((CraftWorld)world).getHandle().worldData.setGameType(mode.getValue());
    }
  }
  
  public ConsoleCommandSender getConsoleSender() {
    return this.console.console;
  }
  
  public EntityMetadataStore getEntityMetadata() {
    return this.entityMetadata;
  }
  
  public PlayerMetadataStore getPlayerMetadata() {
    return this.playerMetadata;
  }
  
  public WorldMetadataStore getWorldMetadata() {
    return this.worldMetadata;
  }
  
  public void detectListNameConflict(EntityPlayer entityPlayer)
  {
    for (int i = 0; i < getHandle().players.size(); i++) {
      EntityPlayer testEntityPlayer = (EntityPlayer)getHandle().players.get(i);
      

      if ((testEntityPlayer != entityPlayer) && (testEntityPlayer.listName.equals(entityPlayer.listName))) {
        String oldName = entityPlayer.listName;
        int spaceLeft = 16 - oldName.length();
        
        if (spaceLeft <= 1) {
          entityPlayer.listName = (oldName.subSequence(0, oldName.length() - 2 - spaceLeft) + String.valueOf(System.currentTimeMillis() % 99L));
        } else {
          entityPlayer.listName = (oldName + String.valueOf(System.currentTimeMillis() % 99L));
        }
        
        return;
      }
    }
  }
  
  public File getWorldContainer() {
    return new File(this.configuration.getString("settings.world-container", "."));
  }
  
  public OfflinePlayer[] getOfflinePlayers() {
    WorldNBTStorage storage = (WorldNBTStorage)((WorldServer)this.console.worlds.get(0)).getDataManager();
    String[] files = storage.getPlayerDir().list(new DatFileFilter());
    Set<OfflinePlayer> players = new HashSet();
    
    for (int i = 0; i < files.length; i++) {
      players.add(getOfflinePlayer(files[i].substring(0, files[i].length() - 4)));
    }
    players.addAll(Arrays.asList(getOnlinePlayers()));
    
    return (OfflinePlayer[])players.toArray(new OfflinePlayer[players.size()]);
  }
  
  public Messenger getMessenger() {
    return this.messenger;
  }
  
  public void sendPluginMessage(Plugin source, String channel, byte[] message) {
    StandardMessenger.validatePluginMessage(getMessenger(), source, channel, message);
    
    for (Player player : getOnlinePlayers()) {
      player.sendPluginMessage(source, channel, message);
    }
  }
  
  public Set<String> getListeningPluginChannels() {
    Set<String> result = new HashSet();
    
    for (Player player : getOnlinePlayers()) {
      result.addAll(player.getListeningPluginChannels());
    }
    
    return result;
  }
  
  public void onPlayerJoin(Player player) {
    if ((this.updater.isEnabled()) && (this.updater.getCurrent() != null) && (player.hasPermission("bukkit.broadcast.admin"))) {
      if ((this.updater.getCurrent().isBroken()) && (this.updater.getOnBroken().contains("warn-ops"))) {
        player.sendMessage(ChatColor.DARK_RED + "The version of CraftBukkit that this server is running is known to be broken. Please consider updating to the latest version at dl.bukkit.org.");
      } else if ((this.updater.isUpdateAvailable()) && (this.updater.getOnUpdate().contains("warn-ops"))) {
        player.sendMessage(ChatColor.DARK_PURPLE + "The version of CraftBukkit that this server is running is out of date. Please consider updating to the latest version at dl.bukkit.org.");
      }
    }
  }
  
  public Inventory createInventory(InventoryHolder owner, InventoryType type)
  {
    return new CraftInventoryCustom(owner, type);
  }
  
  public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
    Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
    return new CraftInventoryCustom(owner, size);
  }
  
  public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
    Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
    return new CraftInventoryCustom(owner, size, title);
  }
  
  public HelpMap getHelpMap() {
    return this.helpMap;
  }
  
  public SimpleCommandMap getCommandMap() {
    return this.commandMap;
  }
  
  public int getMonsterSpawnLimit() {
    return this.monsterSpawn;
  }
  
  public int getAnimalSpawnLimit() {
    return this.animalSpawn;
  }
  
  public int getWaterAnimalSpawnLimit() {
    return this.waterAnimalSpawn;
  }
  
  public boolean isPrimaryThread() {
    return Thread.currentThread().equals(this.console.primaryThread);
  }
  
  public String getMotd() {
    return this.console.motd;
  }
}
