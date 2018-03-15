package org.bukkit.craftbukkit;

import java.io.File;
import java.util.List;
import java.util.Map;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.WorldNBTStorage;
import net.minecraft.server.WorldServer;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

@org.bukkit.configuration.serialization.SerializableAs("Player")
public class CraftOfflinePlayer implements OfflinePlayer, org.bukkit.configuration.serialization.ConfigurationSerializable
{
  private final String name;
  private final CraftServer server;
  private final WorldNBTStorage storage;
  
  protected CraftOfflinePlayer(CraftServer server, String name)
  {
    this.server = server;
    this.name = name;
    this.storage = ((WorldNBTStorage)((WorldServer)server.console.worlds.get(0)).getDataManager());
  }
  
  public boolean isOnline() {
    return getPlayer() != null;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Server getServer() {
    return this.server;
  }
  
  public boolean isOp() {
    return this.server.getHandle().isOp(getName().toLowerCase());
  }
  
  public void setOp(boolean value) {
    if (value == isOp()) { return;
    }
    if (value) {
      this.server.getHandle().addOp(getName().toLowerCase());
    } else {
      this.server.getHandle().removeOp(getName().toLowerCase());
    }
  }
  
  public boolean isBanned() {
    return this.server.getHandle().banByName.contains(this.name.toLowerCase());
  }
  
  public void setBanned(boolean value) {
    if (value) {
      this.server.getHandle().addUserBan(this.name.toLowerCase());
    } else {
      this.server.getHandle().removeUserBan(this.name.toLowerCase());
    }
  }
  
  public boolean isWhitelisted() {
    return this.server.getHandle().getWhitelisted().contains(this.name.toLowerCase());
  }
  
  public void setWhitelisted(boolean value) {
    if (value) {
      this.server.getHandle().addWhitelist(this.name.toLowerCase());
    } else {
      this.server.getHandle().removeWhitelist(this.name.toLowerCase());
    }
  }
  
  public Map<String, Object> serialize() {
    Map<String, Object> result = new java.util.LinkedHashMap();
    
    result.put("name", this.name);
    
    return result;
  }
  
  public static OfflinePlayer deserialize(Map<String, Object> args) {
    return org.bukkit.Bukkit.getServer().getOfflinePlayer((String)args.get("name"));
  }
  
  public String toString()
  {
    return getClass().getSimpleName() + "[name=" + this.name + "]";
  }
  
  public Player getPlayer() {
    for (Object obj : this.server.getHandle().players) {
      EntityPlayer player = (EntityPlayer)obj;
      if (player.name.equalsIgnoreCase(getName())) {
        return player.netServerHandler != null ? player.netServerHandler.getPlayer() : null;
      }
    }
    
    return null;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof OfflinePlayer)) {
      return false;
    }
    OfflinePlayer other = (OfflinePlayer)obj;
    if ((getName() == null) || (other.getName() == null)) {
      return false;
    }
    return getName().equalsIgnoreCase(other.getName());
  }
  
  public int hashCode()
  {
    int hash = 5;
    hash = 97 * hash + (getName() != null ? getName().toLowerCase().hashCode() : 0);
    return hash;
  }
  
  private NBTTagCompound getData() {
    return this.storage.getPlayerData(getName());
  }
  
  private NBTTagCompound getBukkitData() {
    NBTTagCompound result = getData();
    
    if (result != null) {
      if (!result.hasKey("bukkit")) {
        result.setCompound("bukkit", new NBTTagCompound());
      }
      result = result.getCompound("bukkit");
    }
    
    return result;
  }
  
  private File getDataFile() {
    return new File(this.storage.getPlayerDir(), this.name + ".dat");
  }
  
  public long getFirstPlayed() {
    Player player = getPlayer();
    if (player != null) { return player.getFirstPlayed();
    }
    NBTTagCompound data = getBukkitData();
    
    if (data != null) {
      if (data.hasKey("firstPlayed")) {
        return data.getLong("firstPlayed");
      }
      File file = getDataFile();
      return file.lastModified();
    }
    
    return 0L;
  }
  
  public long getLastPlayed()
  {
    Player player = getPlayer();
    if (player != null) { return player.getLastPlayed();
    }
    NBTTagCompound data = getBukkitData();
    
    if (data != null) {
      if (data.hasKey("lastPlayed")) {
        return data.getLong("lastPlayed");
      }
      File file = getDataFile();
      return file.lastModified();
    }
    
    return 0L;
  }
  
  public boolean hasPlayedBefore()
  {
    return getData() != null;
  }
  
  public org.bukkit.Location getBedSpawnLocation() {
    NBTTagCompound data = getData();
    if ((data.hasKey("SpawnX")) && (data.hasKey("SpawnY")) && (data.hasKey("SpawnZ"))) {
      String spawnWorld = data.getString("SpawnWorld");
      if (spawnWorld.equals("")) {
        spawnWorld = ((org.bukkit.World)this.server.getWorlds().get(0)).getName();
      }
      return new org.bukkit.Location(this.server.getWorld(spawnWorld), data.getInt("SpawnX"), data.getInt("SpawnY"), data.getInt("SpawnZ"));
    }
    return null;
  }
  
  public void setMetadata(String metadataKey, MetadataValue metadataValue) {
    this.server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
  }
  
  public List<MetadataValue> getMetadata(String metadataKey) {
    return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
  }
  
  public boolean hasMetadata(String metadataKey) {
    return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
  }
  
  public void removeMetadata(String metadataKey, Plugin plugin) {
    this.server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
  }
}
