package org.bukkit.craftbukkit.event;

import java.util.List;
import net.minecraft.server.Container;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityDamageSourceIndirect;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EntityPotion;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;

public class CraftEventFactory
{
  private static boolean canBuild(CraftWorld world, Player player, int x, int z)
  {
    WorldServer worldServer = world.getHandle();
    int spawnSize = Bukkit.getServer().getSpawnRadius();
    
    if (world.getHandle().dimension != 0) return true;
    if (spawnSize <= 0) return true;
    if ((player != null) && (player.isOp())) { return true;
    }
    net.minecraft.server.ChunkCoordinates chunkcoordinates = worldServer.getSpawn();
    
    int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.x), Math.abs(z - chunkcoordinates.z));
    return distanceFromSpawn >= spawnSize;
  }
  
  public static <T extends org.bukkit.event.Event> T callEvent(T event) {
    Bukkit.getServer().getPluginManager().callEvent(event);
    return event;
  }
  


  public static BlockPlaceEvent callBlockPlaceEvent(net.minecraft.server.World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ)
  {
    CraftWorld craftWorld = ((WorldServer)world).getWorld();
    CraftServer craftServer = ((WorldServer)world).getServer();
    
    Player player = who == null ? null : (Player)who.getBukkitEntity();
    
    Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
    Block placedBlock = replacedBlockState.getBlock();
    
    boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());
    
    BlockPlaceEvent event = new BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, player.getItemInHand(), player, canBuild);
    craftServer.getPluginManager().callEvent(event);
    
    return event;
  }
  


  public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, net.minecraft.server.ItemStack itemInHand)
  {
    return (PlayerBucketEmptyEvent)getPlayerBucketEvent(false, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, net.minecraft.server.Item.BUCKET);
  }
  
  public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, net.minecraft.server.ItemStack itemInHand, net.minecraft.server.Item bucket) {
    return (PlayerBucketFillEvent)getPlayerBucketEvent(true, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
  }
  
  private static org.bukkit.event.player.PlayerEvent getPlayerBucketEvent(boolean isFilling, EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, net.minecraft.server.ItemStack itemstack, net.minecraft.server.Item item) {
    Player player = who == null ? null : (Player)who.getBukkitEntity();
    CraftItemStack itemInHand = new CraftItemStack(new net.minecraft.server.ItemStack(item));
    Material bucket = Material.getMaterial(itemstack.id);
    
    CraftWorld craftWorld = (CraftWorld)player.getWorld();
    CraftServer craftServer = (CraftServer)player.getServer();
    
    Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
    org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.block.CraftBlock.notchToBlockFace(clickedFace);
    
    org.bukkit.event.player.PlayerEvent event = null;
    if (isFilling) {
      event = new PlayerBucketFillEvent(player, blockClicked, blockFace, bucket, itemInHand);
      ((PlayerBucketFillEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
    } else {
      event = new PlayerBucketEmptyEvent(player, blockClicked, blockFace, bucket, itemInHand);
      ((PlayerBucketEmptyEvent)event).setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
    }
    
    craftServer.getPluginManager().callEvent(event);
    
    return event;
  }
  


  public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, net.minecraft.server.ItemStack itemstack)
  {
    if ((action != Action.LEFT_CLICK_AIR) && (action != Action.RIGHT_CLICK_AIR)) {
      throw new IllegalArgumentException();
    }
    return callPlayerInteractEvent(who, action, 0, 256, 0, 0, itemstack);
  }
  
  public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, int clickedX, int clickedY, int clickedZ, int clickedFace, net.minecraft.server.ItemStack itemstack) {
    Player player = who == null ? null : (Player)who.getBukkitEntity();
    CraftItemStack itemInHand = new CraftItemStack(itemstack);
    
    CraftWorld craftWorld = (CraftWorld)player.getWorld();
    CraftServer craftServer = (CraftServer)player.getServer();
    
    Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
    org.bukkit.block.BlockFace blockFace = org.bukkit.craftbukkit.block.CraftBlock.notchToBlockFace(clickedFace);
    
    if (clickedY > 255) {
      blockClicked = null;
      switch (action) {
      case LEFT_CLICK_BLOCK: 
        action = Action.LEFT_CLICK_AIR;
        break;
      case RIGHT_CLICK_BLOCK: 
        action = Action.RIGHT_CLICK_AIR;
        break;
      }
      
    }
    
    if ((itemInHand.getType() == Material.AIR) || (itemInHand.getAmount() == 0)) {
      itemInHand = null;
    }
    
    PlayerInteractEvent event = new PlayerInteractEvent(player, action, itemInHand, blockClicked, blockFace);
    craftServer.getPluginManager().callEvent(event);
    
    return event;
  }
  


  public static EntityShootBowEvent callEntityShootBowEvent(EntityLiving who, net.minecraft.server.ItemStack itemstack, EntityArrow entityArrow, float force)
  {
    LivingEntity shooter = (LivingEntity)who.getBukkitEntity();
    CraftItemStack itemInHand = new CraftItemStack(itemstack);
    org.bukkit.entity.Arrow arrow = (org.bukkit.entity.Arrow)entityArrow.getBukkitEntity();
    
    if (itemInHand != null && (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0)) {
      itemInHand = null;
    }
    
    EntityShootBowEvent event = new EntityShootBowEvent(shooter, itemInHand, arrow, force);
    Bukkit.getPluginManager().callEvent(event);
    
    return event;
  }
  


  public static BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, net.minecraft.server.ItemStack itemstack, boolean instaBreak)
  {
    Player player = (who == null) ? null : (Player)who.getBukkitEntity();
    CraftItemStack itemInHand = new CraftItemStack(itemstack);
    
    CraftWorld craftWorld = (CraftWorld)player.getWorld();
    CraftServer craftServer = (CraftServer)player.getServer();
    
    Block blockClicked = craftWorld.getBlockAt(x, y, z);
    
    BlockDamageEvent event = new BlockDamageEvent(player, blockClicked, itemInHand, instaBreak);
    craftServer.getPluginManager().callEvent(event);
    
    return event;
  }
  


  public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason spawnReason)
  {
    LivingEntity entity = (LivingEntity)entityliving.getBukkitEntity();
    CraftServer craftServer = (CraftServer)entity.getServer();
    
    CreatureSpawnEvent event = new CreatureSpawnEvent(entity, spawnReason);
    craftServer.getPluginManager().callEvent(event);
    return event;
  }
  


  public static org.bukkit.event.entity.EntityTameEvent callEntityTameEvent(EntityLiving entity, EntityHuman tamer)
  {
    org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
    org.bukkit.entity.AnimalTamer bukkitTamer = tamer != null ? tamer.getBukkitEntity() : null;
    CraftServer craftServer = (CraftServer)bukkitEntity.getServer();
    
    org.bukkit.event.entity.EntityTameEvent event = new org.bukkit.event.entity.EntityTameEvent((LivingEntity)bukkitEntity, bukkitTamer);
    craftServer.getPluginManager().callEvent(event);
    return event;
  }
  


  public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem)
  {
    org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
    CraftServer craftServer = (CraftServer)entity.getServer();
    
    ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
    
    craftServer.getPluginManager().callEvent(event);
    return event;
  }
  


  public static org.bukkit.event.entity.ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem)
  {
    org.bukkit.entity.Item entity = (org.bukkit.entity.Item)entityitem.getBukkitEntity();
    
    org.bukkit.event.entity.ItemDespawnEvent event = new org.bukkit.event.entity.ItemDespawnEvent(entity, entity.getLocation());
    
    ((CraftServer)entity.getServer()).getPluginManager().callEvent(event);
    return event;
  }
  


  public static PotionSplashEvent callPotionSplashEvent(EntityPotion potion, java.util.Map<LivingEntity, Double> affectedEntities)
  {
    ThrownPotion thrownPotion = (ThrownPotion)potion.getBukkitEntity();
    
    PotionSplashEvent event = new PotionSplashEvent(thrownPotion, affectedEntities);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  


  public static BlockFadeEvent callBlockFadeEvent(Block block, int type)
  {
    BlockState state = block.getState();
    state.setTypeId(type);
    
    BlockFadeEvent event = new BlockFadeEvent(block, state);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim) {
    return callEntityDeathEvent(victim, new java.util.ArrayList(0));
  }
  
  public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim, List<org.bukkit.inventory.ItemStack> drops) {
    CraftLivingEntity entity = (CraftLivingEntity)victim.getBukkitEntity();
    EntityDeathEvent event = new EntityDeathEvent(entity, drops, victim.getExpReward());
    org.bukkit.World world = entity.getWorld();
    Bukkit.getServer().getPluginManager().callEvent(event);
    
    victim.expToDrop = event.getDroppedExp();
    
    for (org.bukkit.inventory.ItemStack stack : event.getDrops()) {
      if ((stack != null) && (stack.getType() != Material.AIR))
      {
        if ((stack instanceof CraftItemStack))
        {
          victim.a(((CraftItemStack)stack).getHandle(), 0.0F);
        }
        else {
          victim.a(new CraftItemStack(stack).getHandle(), 0.0F);
        }
      }
    }
    return event;
  }
  
  public static PlayerDeathEvent callPlayerDeathEvent(EntityPlayer victim, List<org.bukkit.inventory.ItemStack> drops, String deathMessage) {
    CraftPlayer entity = victim.getBukkitEntity();
    PlayerDeathEvent event = new PlayerDeathEvent(entity, drops, victim.getExpReward(), 0, deathMessage);
    org.bukkit.World world = entity.getWorld();
    Bukkit.getServer().getPluginManager().callEvent(event);
    
    victim.keepLevel = event.getKeepLevel();
    victim.newLevel = event.getNewLevel();
    victim.newTotalExp = event.getNewTotalExp();
    victim.expToDrop = event.getDroppedExp();
    victim.newExp = event.getNewExp();
    
    for (org.bukkit.inventory.ItemStack stack : event.getDrops()) {
      if ((stack != null) && (stack.getType() != Material.AIR))
      {
        if ((stack instanceof CraftItemStack))
        {
          victim.a(((CraftItemStack)stack).getHandle(), 0.0F);
        } else {
          world.dropItemNaturally(entity.getLocation(), stack);
        }
      }
    }
    return event;
  }
  


  public static ServerListPingEvent callServerListPingEvent(Server craftServer, java.net.InetAddress address, String motd, int numPlayers, int maxPlayers)
  {
    ServerListPingEvent event = new ServerListPingEvent(address, motd, numPlayers, maxPlayers);
    craftServer.getPluginManager().callEvent(event);
    return event;
  }
  

  public static EntityDamageEvent callEntityDamageEvent(net.minecraft.server.Entity damager, net.minecraft.server.Entity damagee, EntityDamageEvent.DamageCause cause, int damage)
  {
    EntityDamageEvent event;
    if (damager != null) {
      event = new org.bukkit.event.entity.EntityDamageByEntityEvent(damager.getBukkitEntity(), damagee.getBukkitEntity(), cause, damage);
    } else {
      event = new EntityDamageEvent(damagee.getBukkitEntity(), cause, damage);
    }
    
    callEvent(event);
    
    if (!event.isCancelled()) {
      event.getEntity().setLastDamageCause(event);
    }
    
    return event;
  }
  
  public static EntityDamageEvent handleEntityDamageEvent(net.minecraft.server.Entity entity, DamageSource source, int damage) {
    net.minecraft.server.Entity damager = source.getEntity();
    EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
    
    if ((source instanceof EntityDamageSourceIndirect)) {
      damager = ((EntityDamageSourceIndirect)source).getProximateDamageSource();
      if ((damager.getBukkitEntity() instanceof ThrownPotion)) {
        cause = EntityDamageEvent.DamageCause.MAGIC;
      } else if ((damager.getBukkitEntity() instanceof Projectile)) {
        cause = EntityDamageEvent.DamageCause.PROJECTILE;
      }
    }
    
    return callEntityDamageEvent(damager, entity, cause, damage);
  }
  
  public static boolean handleNonLivingEntityDamageEvent(net.minecraft.server.Entity entity, DamageSource source, int damage)
  {
    if (!(source instanceof net.minecraft.server.EntityDamageSource)) {
      return false;
    }
    EntityDamageEvent event = handleEntityDamageEvent(entity, source, damage);
    return (event.isCancelled()) || (event.getDamage() == 0);
  }
  
  public static org.bukkit.event.player.PlayerLevelChangeEvent callPlayerLevelChangeEvent(Player player, int oldLevel, int newLevel) {
    org.bukkit.event.player.PlayerLevelChangeEvent event = new org.bukkit.event.player.PlayerLevelChangeEvent(player, oldLevel, newLevel);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  
  public static PlayerExpChangeEvent callPlayerExpChangeEvent(EntityHuman entity, int expAmount) {
    Player player = (Player)entity.getBukkitEntity();
    PlayerExpChangeEvent event = new PlayerExpChangeEvent(player, expAmount);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  
  public static void handleBlockGrowEvent(net.minecraft.server.World world, int x, int y, int z, int type, int data) {
    Block block = world.getWorld().getBlockAt(x, y, z);
    CraftBlockState state = (CraftBlockState)block.getState();
    state.setTypeId(type);
    state.setRawData((byte)data);
    
    BlockGrowEvent event = new BlockGrowEvent(block, state);
    Bukkit.getPluginManager().callEvent(event);
    
    if (!event.isCancelled()) {
      state.update(true);
    }
  }
  
  public static FoodLevelChangeEvent callFoodLevelChangeEvent(EntityHuman entity, int level) {
    FoodLevelChangeEvent event = new FoodLevelChangeEvent((Player)entity.getBukkitEntity(), level);
    entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityChangeBlockEvent callEntityChangeBlockEvent(org.bukkit.entity.Entity entity, Block block, Material material) {
    EntityChangeBlockEvent event = new EntityChangeBlockEvent((LivingEntity)entity, block, material);
    entity.getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static PigZapEvent callPigZapEvent(net.minecraft.server.Entity pig, net.minecraft.server.Entity lightning, net.minecraft.server.Entity pigzombie) {
    PigZapEvent event = new PigZapEvent((org.bukkit.entity.Pig)pig.getBukkitEntity(), (org.bukkit.entity.LightningStrike)lightning.getBukkitEntity(), (org.bukkit.entity.PigZombie)pigzombie.getBukkitEntity());
    pig.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.server.Entity entity, Block block, Material material) {
    EntityChangeBlockEvent event = new EntityChangeBlockEvent((LivingEntity)entity.getBukkitEntity(), block, material);
    entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityChangeBlockEvent callEntityChangeBlockEvent(net.minecraft.server.Entity entity, int x, int y, int z, int type) {
    Block block = entity.world.getWorld().getBlockAt(x, y, z);
    Material material = Material.getMaterial(type);
    
    return callEntityChangeBlockEvent(entity, block, material);
  }
  
  public static CreeperPowerEvent callCreeperPowerEvent(net.minecraft.server.Entity creeper, net.minecraft.server.Entity lightning, org.bukkit.event.entity.CreeperPowerEvent.PowerCause cause) {
    CreeperPowerEvent event = new CreeperPowerEvent((org.bukkit.entity.Creeper)creeper.getBukkitEntity(), (org.bukkit.entity.LightningStrike)lightning.getBukkitEntity(), cause);
    creeper.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityTargetEvent callEntityTargetEvent(net.minecraft.server.Entity entity, net.minecraft.server.Entity target, EntityTargetEvent.TargetReason reason) {
    EntityTargetEvent event = new EntityTargetEvent(entity.getBukkitEntity(), target == null ? null : target.getBukkitEntity(), reason);
    entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityTargetLivingEntityEvent callEntityTargetLivingEvent(net.minecraft.server.Entity entity, EntityLiving target, EntityTargetEvent.TargetReason reason) {
    EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(entity.getBukkitEntity(), (LivingEntity)target.getBukkitEntity(), reason);
    entity.getBukkitEntity().getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static EntityBreakDoorEvent callEntityBreakDoorEvent(net.minecraft.server.Entity entity, int x, int y, int z) {
    org.bukkit.entity.Entity entity1 = entity.getBukkitEntity();
    Block block = entity1.getWorld().getBlockAt(x, y, z);
    
    EntityBreakDoorEvent event = new EntityBreakDoorEvent((LivingEntity)entity1, block);
    entity1.getServer().getPluginManager().callEvent(event);
    
    return event;
  }
  
  public static Container callInventoryOpenEvent(EntityPlayer player, Container container) {
    if (player.activeContainer != player.defaultContainer) {
      player.netServerHandler.handleContainerClose(new net.minecraft.server.Packet101CloseWindow(player.activeContainer.windowId));
    }
    
    CraftServer server = ((WorldServer)player.world).getServer();
    CraftPlayer craftPlayer = player.getBukkitEntity();
    player.activeContainer.transferTo(container, craftPlayer);
    
    org.bukkit.event.inventory.InventoryOpenEvent event = new org.bukkit.event.inventory.InventoryOpenEvent(container.getBukkitView());
    server.getPluginManager().callEvent(event);
    
    if (event.isCancelled()) {
      container.transferTo(player.activeContainer, craftPlayer);
      return null;
    }
    
    return container;
  }
  
  public static net.minecraft.server.ItemStack callPreCraftEvent(InventoryCrafting matrix, net.minecraft.server.ItemStack result, org.bukkit.inventory.InventoryView lastCraftView, boolean isRepair) {
    org.bukkit.craftbukkit.inventory.CraftInventoryCrafting inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryCrafting(matrix, matrix.resultInventory);
    inventory.setResult(new CraftItemStack(result));
    
    PrepareItemCraftEvent event = new PrepareItemCraftEvent(inventory, lastCraftView, isRepair);
    Bukkit.getPluginManager().callEvent(event);
    
    org.bukkit.inventory.ItemStack bitem = event.getInventory().getResult();
    
    return CraftItemStack.createNMSItemStack(bitem);
  }
  
  public static ProjectileLaunchEvent callProjectileLaunchEvent(net.minecraft.server.Entity entity) {
    Projectile bukkitEntity = (Projectile)entity.getBukkitEntity();
    ProjectileLaunchEvent event = new ProjectileLaunchEvent(bukkitEntity);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  
  public static ExpBottleEvent callExpBottleEvent(net.minecraft.server.Entity entity, int exp) {
    org.bukkit.entity.ThrownExpBottle bottle = (org.bukkit.entity.ThrownExpBottle)entity.getBukkitEntity();
    ExpBottleEvent event = new ExpBottleEvent(bottle, exp);
    Bukkit.getPluginManager().callEvent(event);
    return event;
  }
  
  public static BlockRedstoneEvent callRedstoneChange(net.minecraft.server.World world, int x, int y, int z, int oldCurrent, int newCurrent) {
    BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(x, y, z), oldCurrent, newCurrent);
    world.getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static org.bukkit.event.block.NotePlayEvent callNotePlayEvent(net.minecraft.server.World world, int x, int y, int z, byte instrument, byte note) {
    org.bukkit.event.block.NotePlayEvent event = new org.bukkit.event.block.NotePlayEvent(world.getWorld().getBlockAt(x, y, z), org.bukkit.Instrument.getByType(instrument), new org.bukkit.Note(note));
    world.getServer().getPluginManager().callEvent(event);
    return event;
  }
  
  public static void callPlayerItemBreakEvent(EntityHuman human, net.minecraft.server.ItemStack brokenItem) {
    CraftItemStack item = new CraftItemStack(brokenItem);
    org.bukkit.event.player.PlayerItemBreakEvent event = new org.bukkit.event.player.PlayerItemBreakEvent((Player)human.getBukkitEntity(), item);
    Bukkit.getPluginManager().callEvent(event);
  }
}
