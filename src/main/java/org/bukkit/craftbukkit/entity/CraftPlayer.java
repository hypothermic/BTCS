package org.bukkit.craftbukkit.entity;

import com.google.common.collect.MapMaker;
import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.EntityTrackerEntry;
import net.minecraft.server.FoodMetaData;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.Packet201PlayerInfo;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.Packet53BlockChange;
import net.minecraft.server.Packet61WorldEvent;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.WorldServer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.conversations.ConversationTracker;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

@org.bukkit.configuration.serialization.DelegateDeserialization(org.bukkit.craftbukkit.CraftOfflinePlayer.class)
public class CraftPlayer extends CraftHumanEntity implements Player
{
  private long firstPlayed = 0L;
  private long lastPlayed = 0L;
  private boolean hasPlayedBefore = false;
  private ConversationTracker conversationTracker = new ConversationTracker();
  private Set<String> channels = new java.util.HashSet();
  private Map<String, Player> hiddenPlayers = new MapMaker().softValues().makeMap();
  private int hash = 0;
  
  public CraftPlayer(CraftServer server, EntityPlayer entity) {
    super(server, entity);
    
    this.firstPlayed = System.currentTimeMillis();
  }
  
  public boolean isOp()
  {
    return this.server.getHandle().isOp(getName());
  }
  
  public void setOp(boolean value)
  {
    if (value == isOp()) { return;
    }
    if (value) {
      this.server.getHandle().addOp(getName());
    } else {
      this.server.getHandle().removeOp(getName());
    }
    
    this.perm.recalculatePermissions();
  }
  
  public boolean isOnline() {
    for (Object obj : this.server.getHandle().players) {
      EntityPlayer player = (EntityPlayer)obj;
      if (player.name.equalsIgnoreCase(getName())) {
        return true;
      }
    }
    return false;
  }
  
  public InetSocketAddress getAddress() {
    if (getHandle().netServerHandler == null) { return null;
    }
    java.net.SocketAddress addr = getHandle().netServerHandler.networkManager.getSocketAddress();
    if ((addr instanceof InetSocketAddress)) {
      return (InetSocketAddress)addr;
    }
    return null;
  }
  

  public double getEyeHeight()
  {
    return getEyeHeight(false);
  }
  
  public double getEyeHeight(boolean ignoreSneaking)
  {
    if (ignoreSneaking) {
      return 1.62D;
    }
    if (isSneaking()) {
      return 1.54D;
    }
    return 1.62D;
  }
  

  public void sendRawMessage(String message)
  {
    if (getHandle().netServerHandler == null) { return;
    }
    getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet3Chat(message));
  }
  
  public void sendMessage(String message) {
    if (!this.conversationTracker.isConversingModaly()) {
      sendRawMessage(message);
    }
  }
  
  public void sendMessage(String[] messages) {
    for (String message : messages) {
      sendMessage(message);
    }
  }
  
  public String getDisplayName() {
    return getHandle().displayName;
  }
  
  public void setDisplayName(String name) {
    getHandle().displayName = name;
  }
  
  public String getPlayerListName() {
    return getHandle().listName;
  }
  
  public void setPlayerListName(String name) {
    String oldName = getHandle().listName;
    
    if (name == null) {
      name = getName();
    }
    
    if (oldName.equals(name)) {
      return;
    }
    
    if (name.length() > 16) {
      throw new IllegalArgumentException("Player list names can only be a maximum of 16 characters long");
    }
    

    for (int i = 0; i < this.server.getHandle().players.size(); i++) {
      if (((EntityPlayer)this.server.getHandle().players.get(i)).listName.equals(name)) {
        throw new IllegalArgumentException(name + " is already assigned as a player list name for someone");
      }
    }
    
    getHandle().listName = name;
    

    Packet201PlayerInfo oldpacket = new Packet201PlayerInfo(oldName, false, 9999);
    Packet201PlayerInfo packet = new Packet201PlayerInfo(name, true, getHandle().ping);
    for (int i = 0; i < this.server.getHandle().players.size(); i++) {
      EntityPlayer entityplayer = (EntityPlayer)this.server.getHandle().players.get(i);
      if (entityplayer.netServerHandler != null)
      {
        if (entityplayer.getBukkitEntity().canSee(this)) {
          entityplayer.netServerHandler.sendPacket(oldpacket);
          entityplayer.netServerHandler.sendPacket(packet);
        }
      }
    }
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof OfflinePlayer)) {
      return false;
    }
    OfflinePlayer other = (OfflinePlayer)obj;
    if ((getName() == null) || (other.getName() == null)) {
      return false;
    }
    
    boolean nameEquals = getName().equalsIgnoreCase(other.getName());
    boolean idEquals = true;
    
    if ((other instanceof CraftPlayer)) {
      idEquals = getEntityId() == ((CraftPlayer)other).getEntityId();
    }
    
    return (nameEquals) && (idEquals);
  }
  
  public void kickPlayer(String message) {
    if (getHandle().netServerHandler == null) { return;
    }
    getHandle().netServerHandler.disconnect(message == null ? "" : message);
  }
  
  public void setCompassTarget(Location loc) {
    if (getHandle().netServerHandler == null) { return;
    }
    
    getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet6SpawnPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
  }
  
  public Location getCompassTarget() {
    return getHandle().compassTarget;
  }
  
  public void chat(String msg) {
    if (getHandle().netServerHandler == null) { return;
    }
    getHandle().netServerHandler.chat(msg);
  }
  
  public boolean performCommand(String command) {
    return this.server.dispatchCommand(this, command);
  }
  
  public void playNote(Location loc, byte instrument, byte note) {
    if (getHandle().netServerHandler == null) { return;
    }
    getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), instrument, note));
  }
  
  public void playNote(Location loc, Instrument instrument, Note note) {
    if (getHandle().netServerHandler == null) { return;
    }
    getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet54PlayNoteBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), instrument.getType(), note.getId()));
  }
  
  public void playEffect(Location loc, Effect effect, int data) {
    if (getHandle().netServerHandler == null) { return;
    }
    int packetData = effect.getId();
    Packet61WorldEvent packet = new Packet61WorldEvent(packetData, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), data);
    getHandle().netServerHandler.sendPacket(packet);
  }
  
  public <T> void playEffect(Location loc, Effect effect, T data) {
    if (data != null) {
      Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
    } else {
      Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
    }
    
    int datavalue = data == null ? 0 : org.bukkit.craftbukkit.CraftEffect.getDataValue(effect, data);
    playEffect(loc, effect, datavalue);
  }
  
  public void sendBlockChange(Location loc, Material material, byte data) {
    sendBlockChange(loc, material.getId(), data);
  }
  
  public void sendBlockChange(Location loc, int material, byte data) {
    if (getHandle().netServerHandler == null) { return;
    }
    Packet53BlockChange packet = new Packet53BlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld)loc.getWorld()).getHandle());
    
    packet.material = material;
    packet.data = data;
    getHandle().netServerHandler.sendPacket(packet);
  }
  
  public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
    if (getHandle().netServerHandler == null) { return false;
    }
    throw new org.apache.commons.lang3.NotImplementedException("Chunk changes do not yet work");
  }
  
  public void sendMap(MapView map) {
    if (getHandle().netServerHandler == null) { return;
    }
    org.bukkit.craftbukkit.map.RenderData data = ((org.bukkit.craftbukkit.map.CraftMapView)map).render(this);
    for (int x = 0; x < 128; x++) {
      byte[] bytes = new byte[''];
      bytes[1] = ((byte)x);
      for (int y = 0; y < 128; y++) {
        bytes[(y + 3)] = data.buffer[(y * 128 + x)];
      }
      net.minecraft.server.Packet131ItemData packet = new net.minecraft.server.Packet131ItemData((short)Material.MAP.getId(), map.getId(), bytes);
      getHandle().netServerHandler.sendPacket(packet);
    }
  }
  
  public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause)
  {
    if (getHandle().netServerHandler == null) { return false;
    }
    
    Location from = getLocation();
    
    Location to = location;
    
    PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to, cause);
    this.server.getPluginManager().callEvent(event);
    
    if (event.isCancelled() == true) {
      return false;
    }
    
    from = event.getFrom();
    
    to = event.getTo();
    
    WorldServer fromWorld = ((CraftWorld)from.getWorld()).getHandle();
    WorldServer toWorld = ((CraftWorld)to.getWorld()).getHandle();
    
    EntityPlayer entity = getHandle();
    

    if (fromWorld == toWorld) {
      entity.netServerHandler.teleport(to);
    }
    else {
      if (getHandle().activeContainer != getHandle().defaultContainer) {
        getHandle().closeInventory();
      }
      this.server.getHandle().moveToWorld(entity, toWorld.dimension, true, to);
    }
    return true;
  }
  
  public void setSneaking(boolean sneak) {
    getHandle().setSneak(sneak);
  }
  
  public boolean isSneaking() {
    return getHandle().isSneaking();
  }
  
  public boolean isSprinting() {
    return getHandle().isSprinting();
  }
  
  public void setSprinting(boolean sprinting) {
    getHandle().setSprinting(sprinting);
  }
  
  public void loadData() {
    this.server.getHandle().playerFileData.load(getHandle());
  }
  
  public void saveData() {
    this.server.getHandle().playerFileData.save(getHandle());
  }
  
  public void updateInventory() {
    getHandle().updateInventory(getHandle().activeContainer);
  }
  
  public void setSleepingIgnored(boolean isSleeping) {
    getHandle().fauxSleeping = isSleeping;
    ((CraftWorld)getWorld()).getHandle().checkSleepStatus();
  }
  
  public boolean isSleepingIgnored() {
    return getHandle().fauxSleeping;
  }
  
  public void awardAchievement(Achievement achievement) {
    sendStatistic(achievement.getId(), 1);
  }
  
  public void incrementStatistic(Statistic statistic) {
    incrementStatistic(statistic, 1);
  }
  
  public void incrementStatistic(Statistic statistic, int amount) {
    sendStatistic(statistic.getId(), amount);
  }
  
  public void incrementStatistic(Statistic statistic, Material material) {
    incrementStatistic(statistic, material, 1);
  }
  
  public void incrementStatistic(Statistic statistic, Material material, int amount) {
    if (!statistic.isSubstatistic()) {
      throw new IllegalArgumentException("Given statistic is not a substatistic");
    }
    if (statistic.isBlock() != material.isBlock()) {
      throw new IllegalArgumentException("Given material is not valid for this substatistic");
    }
    
    int mat = material.getId();
    
    if (!material.isBlock()) {
      mat -= 255;
    }
    
    sendStatistic(statistic.getId() + mat, amount);
  }
  
  private void sendStatistic(int id, int amount) {
    if (getHandle().netServerHandler == null) { return;
    }
    while (amount > 127) {
      sendStatistic(id, 127);
      amount -= 127;
    }
    
    getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet200Statistic(id, amount));
  }
  
  public void setPlayerTime(long time, boolean relative) {
    getHandle().timeOffset = time;
    getHandle().relativeTime = relative;
  }
  
  public long getPlayerTimeOffset() {
    return getHandle().timeOffset;
  }
  
  public long getPlayerTime() {
    return getHandle().getPlayerTime();
  }
  
  public boolean isPlayerTimeRelative() {
    return getHandle().relativeTime;
  }
  
  public void resetPlayerTime() {
    setPlayerTime(0L, true);
  }
  
  public boolean isBanned() {
    return this.server.getHandle().banByName.contains(getName().toLowerCase());
  }
  
  public void setBanned(boolean value) {
    if (value) {
      this.server.getHandle().addUserBan(getName().toLowerCase());
    } else {
      this.server.getHandle().removeUserBan(getName().toLowerCase());
    }
  }
  
  public boolean isWhitelisted() {
    return this.server.getHandle().getWhitelisted().contains(getName().toLowerCase());
  }
  
  public void setWhitelisted(boolean value) {
    if (value) {
      this.server.getHandle().addWhitelist(getName().toLowerCase());
    } else {
      this.server.getHandle().removeWhitelist(getName().toLowerCase());
    }
  }
  
  public void setGameMode(GameMode mode)
  {
    if (getHandle().netServerHandler == null) { return;
    }
    if (mode == null) {
      throw new IllegalArgumentException("Mode cannot be null");
    }
    
    if (mode != getGameMode()) {
      PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode);
      this.server.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
        return;
      }
      
      getHandle().itemInWorldManager.setGameMode(mode.getValue());
      getHandle().netServerHandler.sendPacket(new net.minecraft.server.Packet70Bed(3, mode.getValue()));
    }
  }
  
  public GameMode getGameMode()
  {
    return GameMode.getByValue(getHandle().itemInWorldManager.getGameMode());
  }
  
  public void giveExp(int exp) {
    getHandle().giveExp(exp);
  }
  
  public float getExp() {
    return getHandle().exp;
  }
  
  public void setExp(float exp) {
    getHandle().exp = exp;
    getHandle().lastSentExp = -1;
  }
  
  public int getLevel() {
    return getHandle().expLevel;
  }
  
  public void setLevel(int level) {
    getHandle().expLevel = level;
    getHandle().lastSentExp = -1;
  }
  
  public int getTotalExperience() {
    return getHandle().expTotal;
  }
  
  public void setTotalExperience(int exp) {
    getHandle().expTotal = exp;
  }
  
  public float getExhaustion() {
    return getHandle().getFoodData().exhaustionLevel;
  }
  
  public void setExhaustion(float value) {
    getHandle().getFoodData().exhaustionLevel = value;
  }
  
  public float getSaturation() {
    return getHandle().getFoodData().saturationLevel;
  }
  
  public void setSaturation(float value) {
    getHandle().getFoodData().saturationLevel = value;
  }
  
  public int getFoodLevel() {
    return getHandle().getFoodData().foodLevel;
  }
  
  public void setFoodLevel(int value) {
    getHandle().getFoodData().foodLevel = value;
  }
  
  public Location getBedSpawnLocation() {
    org.bukkit.World world = getServer().getWorld(getHandle().spawnWorld);
    if ((world != null) && (getHandle().getBed() != null)) {
      return new Location(world, getHandle().getBed().x, getHandle().getBed().y, getHandle().getBed().z);
    }
    return null;
  }
  
  public void setBedSpawnLocation(Location location) {
    getHandle().setRespawnPosition(new ChunkCoordinates(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    getHandle().spawnWorld = location.getWorld().getName();
  }
  
  public void hidePlayer(Player player) {
    Validate.notNull(player, "hidden player cannot be null");
    if (getHandle().netServerHandler == null) return;
    if (equals(player)) return;
    if (this.hiddenPlayers.containsKey(player.getName())) return;
    this.hiddenPlayers.put(player.getName(), player);
    

    EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
    EntityPlayer other = ((CraftPlayer)player).getHandle();
    EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.id);
    if (entry != null) {
      entry.clear(getHandle());
    }
    

    getHandle().netServerHandler.sendPacket(new Packet201PlayerInfo(player.getPlayerListName(), false, 9999));
  }
  
  public void showPlayer(Player player) {
    Validate.notNull(player, "shown player cannot be null");
    if (getHandle().netServerHandler == null) return;
    if (equals(player)) return;
    if (!this.hiddenPlayers.containsKey(player.getName())) return;
    this.hiddenPlayers.remove(player.getName());
    
    EntityTracker tracker = ((WorldServer)this.entity.world).tracker;
    EntityPlayer other = ((CraftPlayer)player).getHandle();
    EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(other.id);
    if ((entry != null) && (!entry.trackedPlayers.contains(getHandle()))) {
      entry.updatePlayer(getHandle());
    }
    
    getHandle().netServerHandler.sendPacket(new Packet201PlayerInfo(player.getPlayerListName(), true, getHandle().ping));
  }
  
  public boolean canSee(Player player) {
    return !this.hiddenPlayers.containsKey(player.getName());
  }
  
  public Map<String, Object> serialize() {
    Map<String, Object> result = new java.util.LinkedHashMap();
    
    result.put("name", getName());
    
    return result;
  }
  
  public Player getPlayer() {
    return this;
  }
  
  public EntityPlayer getHandle()
  {
    return (EntityPlayer)this.entity;
  }
  
  public void setHandle(EntityPlayer entity) {
    super.setHandle(entity);
  }
  
  public String toString()
  {
    return "CraftPlayer{name=" + getName() + '}';
  }
  
  public int hashCode()
  {
    if ((this.hash == 0) || (this.hash == 485)) {
      this.hash = ('ǥ' + (getName() != null ? getName().toLowerCase().hashCode() : 0));
    }
    return this.hash;
  }
  
  public long getFirstPlayed() {
    return this.firstPlayed;
  }
  
  public long getLastPlayed() {
    return this.lastPlayed;
  }
  
  public boolean hasPlayedBefore() {
    return this.hasPlayedBefore;
  }
  
  public void setFirstPlayed(long firstPlayed) {
    this.firstPlayed = firstPlayed;
  }
  
  public void readExtraData(NBTTagCompound nbttagcompound) {
    this.hasPlayedBefore = true;
    if (nbttagcompound.hasKey("bukkit")) {
      NBTTagCompound data = nbttagcompound.getCompound("bukkit");
      
      if (data.hasKey("firstPlayed")) {
        this.firstPlayed = data.getLong("firstPlayed");
        this.lastPlayed = data.getLong("lastPlayed");
      }
      
      if (data.hasKey("newExp")) {
        EntityPlayer handle = getHandle();
        handle.newExp = data.getInt("newExp");
        handle.newTotalExp = data.getInt("newTotalExp");
        handle.newLevel = data.getInt("newLevel");
        handle.expToDrop = data.getInt("expToDrop");
        handle.keepLevel = data.getBoolean("keepLevel");
      }
    }
  }
  
  public void setExtraData(NBTTagCompound nbttagcompound) {
    if (!nbttagcompound.hasKey("bukkit")) {
      nbttagcompound.setCompound("bukkit", new NBTTagCompound());
    }
    
    NBTTagCompound data = nbttagcompound.getCompound("bukkit");
    EntityPlayer handle = getHandle();
    data.setInt("newExp", handle.newExp);
    data.setInt("newTotalExp", handle.newTotalExp);
    data.setInt("newLevel", handle.newLevel);
    data.setInt("expToDrop", handle.expToDrop);
    data.setBoolean("keepLevel", handle.keepLevel);
    data.setLong("firstPlayed", getFirstPlayed());
    data.setLong("lastPlayed", System.currentTimeMillis());
  }
  
  public boolean beginConversation(Conversation conversation) {
    return this.conversationTracker.beginConversation(conversation);
  }
  
  public void abandonConversation(Conversation conversation) {
    this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new org.bukkit.conversations.ManuallyAbandonedConversationCanceller()));
  }
  
  public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
    this.conversationTracker.abandonConversation(conversation, details);
  }
  
  public void acceptConversationInput(String input) {
    this.conversationTracker.acceptConversationInput(input);
  }
  
  public boolean isConversing() {
    return this.conversationTracker.isConversing();
  }
  
  public void sendPluginMessage(Plugin source, String channel, byte[] message) {
    org.bukkit.plugin.messaging.StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
    if (getHandle().netServerHandler == null) { return;
    }
    if (this.channels.contains(channel)) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.tag = channel;
      packet.length = message.length;
      packet.data = message;
      getHandle().netServerHandler.sendPacket(packet);
    }
  }
  
  public void addChannel(String channel) {
    if (this.channels.add(channel)) {
      this.server.getPluginManager().callEvent(new org.bukkit.event.player.PlayerRegisterChannelEvent(this, channel));
    }
  }
  
  public void removeChannel(String channel) {
    if (this.channels.remove(channel)) {
      this.server.getPluginManager().callEvent(new org.bukkit.event.player.PlayerUnregisterChannelEvent(this, channel));
    }
  }
  
  public Set<String> getListeningPluginChannels() {
    return com.google.common.collect.ImmutableSet.copyOf(this.channels);
  }
  
  public void sendSupportedChannels() {
    if (getHandle().netServerHandler == null) return;
    Set<String> listening = this.server.getMessenger().getIncomingChannels();
    
    if (!listening.isEmpty()) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      
      packet.tag = "REGISTER";
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      
      for (String channel : listening) {
        try {
          stream.write(channel.getBytes("UTF8"));
          stream.write(0);
        } catch (java.io.IOException ex) {
          java.util.logging.Logger.getLogger(CraftPlayer.class.getName()).log(java.util.logging.Level.SEVERE, "Could not send Plugin Channel REGISTER to " + getName(), ex);
        }
      }
      
      packet.data = stream.toByteArray();
      packet.length = packet.data.length;
      
      getHandle().netServerHandler.sendPacket(packet);
    }
  }
  
  public org.bukkit.entity.EntityType getType() {
    return org.bukkit.entity.EntityType.PLAYER;
  }
  
  public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
    this.server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
  }
  
  public List<MetadataValue> getMetadata(String metadataKey)
  {
    return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
  }
  
  public boolean hasMetadata(String metadataKey)
  {
    return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
  }
  
  public void removeMetadata(String metadataKey, Plugin owningPlugin)
  {
    this.server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
  }
  
  public boolean setWindowProperty(InventoryView.Property prop, int value)
  {
    net.minecraft.server.Container container = getHandle().activeContainer;
    if (container.getBukkitView().getType() != prop.getType()) {
      return false;
    }
    getHandle().setContainerData(container, prop.getId(), value);
    return true;
  }
  
  public void disconnect(String reason) {
    this.conversationTracker.abandonAllConversations();
    this.perm.clearPermissions();
  }
  
  public boolean isFlying() {
    return getHandle().abilities.isFlying;
  }
  
  public void setFlying(boolean value) {
    if ((!getAllowFlight()) && (value)) {
      throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
    }
    
    getHandle().abilities.isFlying = value;
    getHandle().updateAbilities();
  }
  
  public boolean getAllowFlight() {
    return getHandle().abilities.canFly;
  }
  
  public void setAllowFlight(boolean value) {
    if ((isFlying()) && (!value)) {
      getHandle().abilities.isFlying = false;
    }
    
    getHandle().abilities.canFly = value;
    getHandle().updateAbilities();
  }
  
  public int getNoDamageTicks()
  {
    if (getHandle().invulnerableTicks > 0) {
      return Math.max(getHandle().invulnerableTicks, getHandle().noDamageTicks);
    }
    return getHandle().noDamageTicks;
  }
}
