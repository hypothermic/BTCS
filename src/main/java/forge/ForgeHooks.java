package forge;

import forge.packets.PacketEntitySpawn;
import forge.packets.PacketHandlerBase;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.BaseMod;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.DamageSource;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumBedResult;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.ModLoader;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet131ItemData;
import net.minecraft.server.Packet1Login;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.World;
import net.minecraft.server.mod_MinecraftForge;
import org.bukkit.craftbukkit.util.LongHash;
import org.bukkit.craftbukkit.util.LongHashset;

public class ForgeHooks
{
  public static void onTakenFromCrafting(EntityHuman f, ItemStack stack, IInventory craftMatrix)
  {
    for (ICraftingHandler handler : craftingHandlers)
    {
      handler.onTakenFromCrafting(f, stack, craftMatrix); }
  }
  
  static LinkedList<ICraftingHandler> craftingHandlers = new LinkedList();
  
  public static void onDestroyCurrentItem(EntityHuman player, ItemStack orig)
  {
    for (IDestroyToolHandler handler : destroyToolHandlers)
    {
      handler.onDestroyCurrentItem(player, orig); }
  }
  
  static LinkedList<IDestroyToolHandler> destroyToolHandlers = new LinkedList();
  
  public static boolean onUseBonemeal(World world, int blockID, int x, int y, int z)
  {
    for (IBonemealHandler handler : bonemealHandlers)
    {
      if (handler.onUseBonemeal(world, blockID, x, y, z))
      {
        return true;
      }
    }
    return false; }
  
  static LinkedList<IBonemealHandler> bonemealHandlers = new LinkedList();
  
  public static boolean onUseHoe(ItemStack hoe, EntityHuman entityhuman, World world, int x, int y, int z)
  {
    for (IHoeHandler handler : hoeHandlers)
    {
      if (handler.onUseHoe(hoe, entityhuman, world, x, y, z))
      {
        return true;
      }
    }
    return false; }
  
  static LinkedList<IHoeHandler> hoeHandlers = new LinkedList();
  
  public static EnumBedResult sleepInBedAt(EntityHuman entityHuman, int x, int y, int z)
  {
    for (ISleepHandler handler : sleepHandlers)
    {
      EnumBedResult status = handler.sleepInBedAt(entityHuman, x, y, z);
      if (status != null)
      {
        return status;
      }
    }
    return null; }
  
  static LinkedList<ISleepHandler> sleepHandlers = new LinkedList();
  

  public static void onMinecartUpdate(EntityMinecart minecart, int x, int y, int z)
  {
    for (IMinecartHandler handler : minecartHandlers)
    {
      handler.onMinecartUpdate(minecart, x, y, z);
    }
  }
  
  public static void onMinecartEntityCollision(EntityMinecart minecart, Entity entity)
  {
    for (IMinecartHandler handler : minecartHandlers)
    {
      handler.onMinecartEntityCollision(minecart, entity);
    }
  }
  
  public static boolean onMinecartInteract(EntityMinecart minecart, EntityHuman entityhuman)
  {
    boolean canceled = true;
    for (IMinecartHandler handler : minecartHandlers)
    {
      boolean tmp = handler.onMinecartInteract(minecart, entityhuman, canceled);
      canceled = (canceled) && (tmp);
    }
    return canceled;
  }
  
  static LinkedList<IMinecartHandler> minecartHandlers = new LinkedList();
  
  public static void onConnect(NetworkManager network)
  {
    for (IConnectionHandler handler : connectionHandlers)
    {
      handler.onConnect(network);
    }
  }
  
  public static void onLogin(NetworkManager network, Packet1Login login)
  {
    for (IConnectionHandler handler : connectionHandlers)
    {
      handler.onLogin(network, login);
    }
  }
  
  public static void onDisconnect(NetworkManager network, String message, Object[] args)
  {
    for (IConnectionHandler handler : connectionHandlers)
    {
      handler.onDisconnect(network, message, args); }
  }
  
  static LinkedList<IConnectionHandler> connectionHandlers = new LinkedList();
  
  public static boolean onItemPickup(EntityHuman entityhuman, EntityItem item)
  {
    boolean cont = true;
    for (IPickupHandler handler : pickupHandlers)
    {
      cont = (cont) && (handler.onItemPickup(entityhuman, item));
      if ((!cont) || (item.itemStack.count <= 0))
      {
        return false;
      }
    }
    return cont; }
  
  static LinkedList<IPickupHandler> pickupHandlers = new LinkedList();
  
  public static void addActiveChunks(World world, Set<ChunkCoordIntPair> chunkList)
  {
    for (IChunkLoadHandler loader : chunkLoadHandlers)
    {
      loader.addActiveChunks(world, chunkList);
    }
  }
  
  public static boolean canUnloadChunk(Chunk chunk)
  {
    for (IChunkLoadHandler loader : chunkLoadHandlers)
    {
      if (!loader.canUnloadChunk(chunk))
      {
        return false;
      }
    }
    return true;
  }
  
  public static boolean canUpdateEntity(Entity entity)
  {
    for (IChunkLoadHandler loader : chunkLoadHandlers)
    {
      if (loader.canUpdateEntity(entity))
      {
        return true;
      }
    }
    return false; }
  
  static LinkedList<IChunkLoadHandler> chunkLoadHandlers = new LinkedList();
  
  public static boolean onEntityInteract(EntityHuman entityHuman, Entity entity, boolean isAttack)
  {
    for (IEntityInteractHandler handler : entityInteractHandlers)
    {
      if (!handler.onEntityInteract(entityHuman, entity, isAttack))
      {
        return false;
      }
    }
    return true; }
  
  static LinkedList<IEntityInteractHandler> entityInteractHandlers = new LinkedList();
  
  public static String onServerChat(EntityPlayer player, String message)
  {
    for (IChatHandler handler : chatHandlers)
    {
      message = handler.onServerChat(player, message);
      if (message == null)
      {
        return null;
      }
    }
    return message;
  }
  
  public static boolean onChatCommand(EntityPlayer player, boolean isOp, String command)
  {
    for (IChatHandler handler : chatHandlers)
    {
      if (handler.onChatCommand(player, isOp, command))
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean onServerCommand(Object listener, String username, String command)
  {
    for (IChatHandler handler : chatHandlers)
    {
      if (handler.onServerCommand(listener, username, command))
      {
        return true;
      }
    }
    return false;
  }
  
  public static String onServerCommandSay(Object listener, String username, String message)
  {
    for (IChatHandler handler : chatHandlers)
    {
      message = handler.onServerCommandSay(listener, username, message);
      if (message == null)
      {
        return null;
      }
    }
    return message;
  }
  
  public static String onClientChatRecv(String message)
  {
    for (IChatHandler handler : chatHandlers)
    {
      message = handler.onClientChatRecv(message);
      if (message == null)
      {
        return null;
      }
    }
    return message; }
  
  static LinkedList<IChatHandler> chatHandlers = new LinkedList();
  
  public static void onWorldLoad(World world)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onWorldLoad(world);
    }
  }
  
  public static void onWorldSave(World world)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onWorldSave(world);
    }
  }
  
  public static void onChunkLoad(World world, Chunk chunk)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onChunkLoad(world, chunk);
    }
  }
  
  public static void onChunkUnload(World world, Chunk chunk)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onChunkUnload(world, chunk);
    }
  }
  
  public static void onChunkLoadData(World world, Chunk chunk, NBTTagCompound data)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onChunkLoadData(world, chunk, data);
    }
  }
  
  public static void onChunkSaveData(World world, Chunk chunk, NBTTagCompound data)
  {
    for (ISaveEventHandler handler : saveHandlers)
    {
      handler.onChunkSaveData(world, chunk, data); }
  }
  
  static LinkedList<ISaveEventHandler> saveHandlers = new LinkedList();
  
  public static int getItemBurnTime(ItemStack stack)
  {
    for (IFuelHandler handler : fuelHandlers)
    {
      int ret = handler.getItemBurnTime(stack);
      if (ret > 0)
      {
        return ret;
      }
    }
    return 0; }
  
  static LinkedList<IFuelHandler> fuelHandlers = new LinkedList();
  

  public static boolean onEntitySpawnSpecial(EntityLiving entity, World world, float x, float y, float z)
  {
    for (ISpecialMobSpawnHandler handler : specialMobSpawnHandlers)
    {
      if (handler.onSpecialEntitySpawn(entity, world, x, y, z))
      {
        return true;
      }
    }
    return false;
  }
  
  static LinkedList<ISpecialMobSpawnHandler> specialMobSpawnHandlers = new LinkedList();
  

  public static boolean onEntityLivingSpawn(EntityLiving entity, World world, float x, float y, float z)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      if (handler.onEntityLivingSpawn(entity, world, x, y, z))
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean onEntityLivingDeath(EntityLiving entity, DamageSource killer)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      if (handler.onEntityLivingDeath(entity, killer))
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean onEntityLivingUpdate(EntityLiving entity)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      if (handler.onEntityLivingUpdate(entity))
      {
        return true;
      }
    }
    return false;
  }
  
  public static void onEntityLivingJump(EntityLiving entity)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      handler.onEntityLivingJump(entity);
    }
  }
  
  public static boolean onEntityLivingFall(EntityLiving entity, float distance)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      if (handler.onEntityLivingFall(entity, distance))
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean onEntityLivingAttacked(EntityLiving entity, DamageSource attack, int damage)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      if (handler.onEntityLivingAttacked(entity, attack, damage))
      {
        return true;
      }
    }
    return false;
  }
  
  public static void onEntityLivingSetAttackTarget(EntityLiving entity, EntityLiving target)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      handler.onEntityLivingSetAttackTarget(entity, target);
    }
  }
  
  public static int onEntityLivingHurt(EntityLiving entity, DamageSource source, int damage)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      damage = handler.onEntityLivingHurt(entity, source, damage);
      if (damage == 0)
      {
        return 0;
      }
    }
    return damage;
  }
  
  public static void onEntityLivingDrops(EntityLiving entity, DamageSource source, ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit, int specialDropValue)
  {
    for (IEntityLivingHandler handler : entityLivingHandlers)
    {
      handler.onEntityLivingDrops(entity, source, drops, lootingLevel, recentlyHit, specialDropValue);
    }
  }
  
  static LinkedList<IEntityLivingHandler> entityLivingHandlers = new LinkedList();
  static List<ProbableItem> plantGrassList;
  static int plantGrassWeight;
  static List<ProbableItem> seedGrassList;
  static int seedGrassWeight;
  
  static class ProbableItem { 
	int WeightStart;
    
    public ProbableItem(int item, int metadata, int quantity, int start, int end) { 
      this.WeightStart = start;
      this.WeightEnd = end;
      this.ItemID = item;
      this.Metadata = metadata;
      this.Quantity = quantity;
    }
    
    int WeightEnd;
    int ItemID;
    int Metadata;
    int Quantity;
  }
  
  static ProbableItem getRandomItem(List<ProbableItem> list, int prop) { int n = Collections.binarySearch(list, Integer.valueOf(prop), new Comparator()
    {
      public int compare(Object o1, Object o2)
      {
        ForgeHooks.ProbableItem pi = (ForgeHooks.ProbableItem)o1;
        Integer i1 = (Integer)o2;
        if (i1.intValue() < pi.WeightStart)
        {
          return 1;
        }
        if (i1.intValue() >= pi.WeightEnd)
        {
          return -1;
        }
        return 0;
      }
    });
    if (n < 0)
    {
      return null;
    }
    return (ProbableItem)list.get(n);
  }
  






  public static void plantGrassPlant(World world, int x, int y, int z)
  {
    int index = world.random.nextInt(plantGrassWeight);
    ProbableItem item = getRandomItem(plantGrassList, index);
    if ((item == null) || (Block.byId[item.ItemID] == null))
    {
      return;
    }
    if ((mod_MinecraftForge.DISABLE_DARK_ROOMS) && (!Block.byId[item.ItemID].f(world, x, y, z)))
    {
      return;
    }
    world.setTypeIdAndData(x, y, z, item.ItemID, item.Metadata);
  }
  
  public static void addPlantGrass(int item, int metadata, int probability)
  {
    plantGrassList.add(new ProbableItem(item, metadata, 1, plantGrassWeight, plantGrassWeight + probability));
    plantGrassWeight += probability;
  }
  
  public static ItemStack getGrassSeed(World world)
  {
    int index = world.random.nextInt(seedGrassWeight);
    ProbableItem item = getRandomItem(seedGrassList, index);
    if (item == null)
    {
      return null;
    }
    return new ItemStack(item.ItemID, item.Quantity, item.Metadata);
  }
  
  public static void addGrassSeed(int item, int metadata, int quantity, int probability)
  {
    seedGrassList.add(new ProbableItem(item, metadata, quantity, seedGrassWeight, seedGrassWeight + probability));
    seedGrassWeight += probability;
  }
  


  public static boolean canHarvestBlock(Block block, EntityHuman player, int metadata)
  {
    if (block.material.isAlwaysDestroyable())
    {
      return true;
    }
    ItemStack stack = player.inventory.getItemInHand();
    if (stack == null)
    {
      return player.b(block);
    }
    
    List info = (List)toolClasses.get(Integer.valueOf(stack.id));
    if (info == null)
    {
      return player.b(block);
    }
    Object[] tmp = info.toArray();
    String toolClass = (String)tmp[0];
    int harvestLevel = ((Integer)tmp[1]).intValue();
    
    Integer blockHarvestLevel = (Integer)toolHarvestLevels.get(Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass }));
    if (blockHarvestLevel == null)
    {
      return player.b(block);
    }
    if (blockHarvestLevel.intValue() > harvestLevel)
    {
      return false;
    }
    return true;
  }
  
  public static float blockStrength(Block block, EntityHuman player, int metadata)
  {
    float hardness = block.getHardness(metadata);
    if (hardness < 0.0F)
    {
      return 0.0F;
    }
    
    if (!canHarvestBlock(block, player, metadata))
    {
      return 1.0F / hardness / 100.0F;
    }
    

    return player.getCurrentPlayerStrVsBlock(block, metadata) / hardness / 30.0F;
  }
  

  public static boolean isToolEffective(ItemStack stack, Block block, int metadata)
  {
    List toolClass = (List)toolClasses.get(Integer.valueOf(stack.id));
    if (toolClass == null)
    {
      return false;
    }
    return toolEffectiveness.contains(Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), (String)toolClass.get(0) }));
  }
  
  static void initTools()
  {
    if (toolInit)
    {
      return;
    }
    toolInit = true;
    
    MinecraftForge.setToolClass(Item.WOOD_PICKAXE, "pickaxe", 0);
    MinecraftForge.setToolClass(Item.STONE_PICKAXE, "pickaxe", 1);
    MinecraftForge.setToolClass(Item.IRON_PICKAXE, "pickaxe", 2);
    MinecraftForge.setToolClass(Item.GOLD_PICKAXE, "pickaxe", 0);
    MinecraftForge.setToolClass(Item.DIAMOND_PICKAXE, "pickaxe", 3);
    
    MinecraftForge.setToolClass(Item.WOOD_AXE, "axe", 0);
    MinecraftForge.setToolClass(Item.STONE_AXE, "axe", 1);
    MinecraftForge.setToolClass(Item.IRON_AXE, "axe", 2);
    MinecraftForge.setToolClass(Item.GOLD_AXE, "axe", 0);
    MinecraftForge.setToolClass(Item.DIAMOND_AXE, "axe", 3);
    
    MinecraftForge.setToolClass(Item.WOOD_SPADE, "shovel", 0);
    MinecraftForge.setToolClass(Item.STONE_SPADE, "shovel", 1);
    MinecraftForge.setToolClass(Item.IRON_SPADE, "shovel", 2);
    MinecraftForge.setToolClass(Item.GOLD_SPADE, "shovel", 0);
    MinecraftForge.setToolClass(Item.DIAMOND_SPADE, "shovel", 3);
    
    MinecraftForge.setBlockHarvestLevel(Block.OBSIDIAN, "pickaxe", 3);
    MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_ORE, "pickaxe", 2);
    MinecraftForge.setBlockHarvestLevel(Block.DIAMOND_BLOCK, "pickaxe", 2);
    MinecraftForge.setBlockHarvestLevel(Block.GOLD_ORE, "pickaxe", 2);
    MinecraftForge.setBlockHarvestLevel(Block.GOLD_BLOCK, "pickaxe", 2);
    MinecraftForge.setBlockHarvestLevel(Block.IRON_ORE, "pickaxe", 1);
    MinecraftForge.setBlockHarvestLevel(Block.IRON_BLOCK, "pickaxe", 1);
    MinecraftForge.setBlockHarvestLevel(Block.LAPIS_ORE, "pickaxe", 1);
    MinecraftForge.setBlockHarvestLevel(Block.LAPIS_BLOCK, "pickaxe", 1);
    MinecraftForge.setBlockHarvestLevel(Block.REDSTONE_ORE, "pickaxe", 2);
    MinecraftForge.setBlockHarvestLevel(Block.GLOWING_REDSTONE_ORE, "pickaxe", 2);
    MinecraftForge.removeBlockEffectiveness(Block.REDSTONE_ORE, "pickaxe");
    MinecraftForge.removeBlockEffectiveness(Block.OBSIDIAN, "pickaxe");
    MinecraftForge.removeBlockEffectiveness(Block.GLOWING_REDSTONE_ORE, "pickaxe");
    
    Block[] pickeff = { Block.COBBLESTONE, Block.COBBLESTONE_STAIRS, Block.STEP, Block.STONE, Block.SANDSTONE, Block.MOSSY_COBBLESTONE, Block.COAL_ORE, Block.ICE, Block.NETHERRACK, Block.LAPIS_ORE, Block.LAPIS_BLOCK };
    







    for (Block block : pickeff)
    {
      MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 0);
    }
    
    Block[] spadeEff = { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, Block.SOUL_SAND, Block.MYCEL };
    






    for (Block block : spadeEff)
    {
      MinecraftForge.setBlockHarvestLevel(block, "shovel", 0);
    }
    
    Block[] axeEff = { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DOUBLE_STEP, Block.STEP, Block.PUMPKIN, Block.JACK_O_LANTERN };
    





    for (Block block : axeEff)
    {
      MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
    }
  }
  

  public static HashMap<Class, EntityTrackerInfo> entityTrackerMap = new HashMap();
  







  public static Packet getEntitySpawnPacket(Entity entity)
  {
    EntityTrackerInfo info = MinecraftForge.getEntityTrackerInfo(entity, false);
    if (info == null)
    {
      return null;
    }
    
    PacketEntitySpawn pkt = new PacketEntitySpawn(entity, info.Mod, info.ID);
    return pkt.getPacket();
  }
  
  public static Hashtable<Integer, NetworkMod> networkMods = new Hashtable();
  public static Hashtable<BaseMod, IGuiHandler> guiHandlers = new Hashtable();
  
  public static boolean onArrowLoose(ItemStack itemstack, World world, EntityHuman entityhuman, int heldTime)
  {
    for (IArrowLooseHandler handler : arrowLooseHandlers)
    {
      if (handler.onArrowLoose(itemstack, world, entityhuman, heldTime))
      {
        return true;
      }
    }
    return false;
  }
  
  public static ArrayList<IArrowLooseHandler> arrowLooseHandlers = new ArrayList();
  
  public static ItemStack onArrowNock(ItemStack itemstack, World world, EntityHuman entityhuman)
  {
    for (IArrowNockHandler handler : arrowNockHandlers)
    {
      ItemStack ret = handler.onArrowNock(itemstack, world, entityhuman);
      if (ret != null)
      {
        return ret;
      }
    }
    return null; }
  
  public static ArrayList<IArrowNockHandler> arrowNockHandlers = new ArrayList();
  
  public static final int majorVersion = 3;
  
  public static final int minorVersion = 3;
  
  public static final int revisionVersion = 8;
  
  public static final int buildVersion = 152;
  

  public static int getMajorVersion()
  {
    return 3;
  }
  
  public static int getMinorVersion()
  {
    return 3;
  }
  
  public static int getRevisionVersion()
  {
    return 8;
  }
  
  public static int getBuildVersion()
  {
    return 152;
  }
  
  static
  {
    plantGrassList = new ArrayList();
    plantGrassList.add(new ProbableItem(Block.YELLOW_FLOWER.id, 0, 1, 0, 20));
    plantGrassList.add(new ProbableItem(Block.RED_ROSE.id, 0, 1, 20, 30));
    plantGrassWeight = 30;
    
    seedGrassList = new ArrayList();
    seedGrassList.add(new ProbableItem(Item.SEEDS.id, 0, 1, 0, 10));
    seedGrassWeight = 10;
    
    System.out.println(String.format("MinecraftForge v%d.%d.%d.%d Initialized\n", new Object[] { Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(8), Integer.valueOf(152) }));
    ModLoader.getLogger().info(String.format("MinecraftForge v%d.%d.%d.%d Initialized\n", new Object[] { Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(8), Integer.valueOf(152) }));
  }
  
  static boolean toolInit = false;
  static HashMap toolClasses = new HashMap();
  static HashMap toolHarvestLevels = new HashMap();
  static HashSet toolEffectiveness = new HashSet();
  
  private static PacketHandlerBase forgePacketHandler = null;
  
  public static void setPacketHandler(PacketHandlerBase handler) {
    if (forgePacketHandler != null)
    {
      throw new RuntimeException("Attempted to set Forge's Internal packet handler after it was already set");
    }
    forgePacketHandler = handler;
  }
  
  public static PacketHandlerBase getPacketHandler() {
    return forgePacketHandler;
  }
  
  public static boolean onItemDataPacket(NetworkManager net, Packet131ItemData pkt)
  {
    NetworkMod mod = MinecraftForge.getModByID(pkt.a);
    if (mod == null)
    {
      ModLoader.getLogger().log(Level.WARNING, String.format("Received Unknown MapData packet %d:%d", new Object[] { Short.valueOf(pkt.a), Short.valueOf(pkt.b) }));
      return false;
    }
    mod.onPacketData(net, pkt.b, pkt.c);
    return true;
  }
  
  public static void addActiveChunks(World world, LongHashset chunkTickList) {
    Set<ChunkCoordIntPair> s = new HashSet();
    addActiveChunks(world, s);
    for (ChunkCoordIntPair c : s) {
      chunkTickList.add(LongHash.toLong(c.x, c.z));
    }
  }
}
