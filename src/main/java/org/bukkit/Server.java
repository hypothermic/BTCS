package org.bukkit;

import com.avaje.ebean.config.ServerConfig;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.scheduler.BukkitScheduler;

public abstract interface Server
  extends PluginMessageRecipient
{
  public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
  public static final String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";
  
  public abstract String getName();
  
  public abstract String getVersion();
  
  public abstract String getBukkitVersion();
  
  public abstract Player[] getOnlinePlayers();
  
  public abstract int getMaxPlayers();
  
  public abstract int getPort();
  
  public abstract int getViewDistance();
  
  public abstract String getIp();
  
  public abstract String getServerName();
  
  public abstract String getServerId();
  
  public abstract String getWorldType();
  
  public abstract boolean getGenerateStructures();
  
  public abstract boolean getAllowEnd();
  
  public abstract boolean getAllowNether();
  
  public abstract boolean hasWhitelist();
  
  public abstract void setWhitelist(boolean paramBoolean);
  
  public abstract Set<OfflinePlayer> getWhitelistedPlayers();
  
  public abstract void reloadWhitelist();
  
  public abstract int broadcastMessage(String paramString);
  
  public abstract String getUpdateFolder();
  
  public abstract File getUpdateFolderFile();
  
  public abstract long getConnectionThrottle();
  
  public abstract int getTicksPerAnimalSpawns();
  
  public abstract int getTicksPerMonsterSpawns();
  
  public abstract Player getPlayer(String paramString);
  
  public abstract Player getPlayerExact(String paramString);
  
  public abstract List<Player> matchPlayer(String paramString);
  
  public abstract PluginManager getPluginManager();
  
  public abstract BukkitScheduler getScheduler();
  
  public abstract ServicesManager getServicesManager();
  
  public abstract List<World> getWorlds();
  
  public abstract World createWorld(WorldCreator paramWorldCreator);
  
  public abstract boolean unloadWorld(String paramString, boolean paramBoolean);
  
  public abstract boolean unloadWorld(World paramWorld, boolean paramBoolean);
  
  public abstract World getWorld(String paramString);
  
  public abstract World getWorld(UUID paramUUID);
  
  public abstract MapView getMap(short paramShort);
  
  public abstract MapView createMap(World paramWorld);
  
  public abstract void reload();
  
  public abstract Logger getLogger();
  
  public abstract PluginCommand getPluginCommand(String paramString);
  
  public abstract void savePlayers();
  
  public abstract boolean dispatchCommand(CommandSender paramCommandSender, String paramString)
    throws CommandException;
  
  public abstract void configureDbConfig(ServerConfig paramServerConfig);
  
  public abstract boolean addRecipe(Recipe paramRecipe);
  
  public abstract List<Recipe> getRecipesFor(ItemStack paramItemStack);
  
  public abstract Iterator<Recipe> recipeIterator();
  
  public abstract void clearRecipes();
  
  public abstract void resetRecipes();
  
  public abstract Map<String, String[]> getCommandAliases();
  
  public abstract int getSpawnRadius();
  
  public abstract void setSpawnRadius(int paramInt);
  
  public abstract boolean getOnlineMode();
  
  public abstract boolean getAllowFlight();
  
  public abstract boolean useExactLoginLocation();
  
  public abstract void shutdown();
  
  public abstract int broadcast(String paramString1, String paramString2);
  
  public abstract OfflinePlayer getOfflinePlayer(String paramString);
  
  public abstract Set<String> getIPBans();
  
  public abstract void banIP(String paramString);
  
  public abstract void unbanIP(String paramString);
  
  public abstract Set<OfflinePlayer> getBannedPlayers();
  
  public abstract Set<OfflinePlayer> getOperators();
  
  public abstract GameMode getDefaultGameMode();
  
  public abstract void setDefaultGameMode(GameMode paramGameMode);
  
  public abstract ConsoleCommandSender getConsoleSender();
  
  public abstract File getWorldContainer();
  
  public abstract OfflinePlayer[] getOfflinePlayers();
  
  public abstract Messenger getMessenger();
  
  public abstract HelpMap getHelpMap();
  
  public abstract Inventory createInventory(InventoryHolder paramInventoryHolder, InventoryType paramInventoryType);
  
  public abstract Inventory createInventory(InventoryHolder paramInventoryHolder, int paramInt);
  
  public abstract Inventory createInventory(InventoryHolder paramInventoryHolder, int paramInt, String paramString);
  
  public abstract int getMonsterSpawnLimit();
  
  public abstract int getAnimalSpawnLimit();
  
  public abstract int getWaterAnimalSpawnLimit();
  
  public abstract boolean isPrimaryThread();
  
  public abstract String getMotd();
}
