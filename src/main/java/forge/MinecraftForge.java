package forge;

import forge.oredict.OreDictionary;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import net.minecraft.server.Achievement;
import net.minecraft.server.BaseMod;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityMinecart;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet132TileEntityData;
import net.minecraft.server.World;

public class MinecraftForge
{
  private static LinkedList<IBucketHandler> bucketHandlers = new LinkedList();

  public static void registerCustomBucketHandler(IBucketHandler handler)
  {
    bucketHandlers.add(handler);
  }
  
  public static void registerSleepHandler(ISleepHandler handler)
  {
    ForgeHooks.sleepHandlers.add(handler);
  }
  
  public static void registerBonemealHandler(IBonemealHandler handler)
  {
    ForgeHooks.bonemealHandlers.add(handler);
  }

  public static void registerHoeHandler(IHoeHandler handler)
  {
    ForgeHooks.hoeHandlers.add(handler);
  }

  public static void registerDestroyToolHandler(IDestroyToolHandler handler)
  {
    ForgeHooks.destroyToolHandlers.add(handler);
  }
  
  public static void registerCraftingHandler(ICraftingHandler handler)
  {
    ForgeHooks.craftingHandlers.add(handler);
  }

  public static void registerMinecartHandler(IMinecartHandler handler)
  {
    ForgeHooks.minecartHandlers.add(handler);
  }

  public static void registerConnectionHandler(IConnectionHandler handler)
  {
    ForgeHooks.connectionHandlers.add(handler);
  }
  
  public static void registerChunkLoadHandler(IChunkLoadHandler handler)
  {
    ForgeHooks.chunkLoadHandlers.add(handler);
  }

  public static void registerPickupHandler(IPickupHandler handler)
  {
    ForgeHooks.pickupHandlers.add(handler);
  }

  public static void registerEntityInteractHandler(IEntityInteractHandler handler)
  {
    ForgeHooks.entityInteractHandlers.add(handler);
  }

  public static void registerChatHandler(IChatHandler handler)
  {
    ForgeHooks.chatHandlers.add(handler);
  }

  public static void registerSaveHandler(ISaveEventHandler handler)
  {
    ForgeHooks.saveHandlers.add(handler);
  }

  public static void registerFuelHandler(IFuelHandler handler)
  {
    ForgeHooks.fuelHandlers.add(handler);
  }
  

  @Deprecated
  public static void registerSpecialMobSpawnHandler(ISpecialMobSpawnHandler handler)
  {
    ForgeHooks.specialMobSpawnHandlers.add(handler);
  }

  public static void registerEntityLivingHandler(IEntityLivingHandler handler)
  {
    ForgeHooks.entityLivingHandlers.add(handler);
  }

  public static ItemStack fillCustomBucket(World world, int X, int Y, int Z)
  {
    for (IBucketHandler handler : bucketHandlers)
    {
      ItemStack stack = handler.fillCustomBucket(world, X, Y, Z);
      
      if (stack != null)
      {
        return stack;
      }
    }
    
    return null;
  }
  



  @Deprecated
  public static void registerOreHandler(IOreHandler handler)
  {
    OreDictionary.registerOreHandler(handler);
  }
  

  @Deprecated
  public static void registerOre(String oreClass, ItemStack ore)
  {
    OreDictionary.registerOre(oreClass, ore);
  }
  

  @Deprecated
  public static List<ItemStack> getOreClass(String oreClass)
  {
    return OreDictionary.getOres(oreClass);
  }
  
  @Deprecated
  public static class OreQuery implements Iterable<Object[]>
  {
    Object[] proto;
    
    public class OreQueryIterator implements Iterator<Object[]>
    {
      LinkedList itering = new LinkedList();
      LinkedList output = new LinkedList();
      
      private OreQueryIterator()
      {
        for (Object input : MinecraftForge.OreQuery.this.proto)
        {
          if ((input instanceof Collection))
          {
            Iterator it = ((Collection)input).iterator();
            if (!it.hasNext())
            {
              this.output = null;
              break;
            }
            this.itering.addLast(it);
            this.output.addLast(it.next());
          }
          else
          {
            this.itering.addLast(input);
            this.output.addLast(input);
          }
        }
      }
      
      public boolean hasNext()
      {
        return this.output != null;
      }
      
      public Object[] next()
      {
        Object[] tr = this.output.toArray();
        
        for (;;)
        {
          if (this.itering.size() == 0)
          {
            this.output = null;
            return tr;
          }
          Object to = this.itering.getLast();
          this.output.removeLast();
          if ((to instanceof Iterator))
          {
            Iterator it = (Iterator)to;
            if (it.hasNext())
            {
              this.output.addLast(it.next());
              break;
            }
          }
          this.itering.removeLast();
        }
        for (int i = this.itering.size(); i < MinecraftForge.OreQuery.this.proto.length; i++)
        {
          if ((MinecraftForge.OreQuery.this.proto[i] instanceof Collection))
          {
            Iterator it = ((Collection)MinecraftForge.OreQuery.this.proto[i]).iterator();
            if (!it.hasNext())
            {
              this.output = null;
              break;
            }
            this.itering.addLast(it);
            this.output.addLast(it.next());
          }
          else
          {
            this.itering.addLast(MinecraftForge.OreQuery.this.proto[i]);
            this.output.addLast(MinecraftForge.OreQuery.this.proto[i]);
          }
        }
        return tr;
      }
      
      public void remove() {}
    }
    
    private OreQuery(Object[] pattern)
    {
      this.proto = pattern;
    }
    
    public Iterator<Object[]> iterator()
    {
      // BTCS start
      /*return new OreQueryIterator(null);*/
      return new OreQueryIterator();
      // BTCS end
    }
  }

  @Deprecated
  public static OreQuery generateRecipes(Object... pattern)
  {
	// BTCS start
    /*return new OreQuery(pattern, null);*/
	return new OreQuery(pattern);
	// BTCS end
  }

  public static void addGrassPlant(int blockID, int metadata, int probability)
  {
    ForgeHooks.addPlantGrass(blockID, metadata, probability);
  }

  public static void addGrassSeed(int itemID, int metadata, int quantity, int probability)
  {
    ForgeHooks.addGrassSeed(itemID, metadata, quantity, probability);
  }
  








  public static void setToolClass(Item tool, String toolClass, int harvestLevel)
  {
    ForgeHooks.initTools();
    ForgeHooks.toolClasses.put(Integer.valueOf(tool.id), Arrays.asList(new Serializable[] { toolClass, Integer.valueOf(harvestLevel) }));
  }
  













  public static void setBlockHarvestLevel(Block block, int metadata, String toolClass, int harvestLevel)
  {
    ForgeHooks.initTools();
    List key = Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass });
    ForgeHooks.toolHarvestLevels.put(key, Integer.valueOf(harvestLevel));
    ForgeHooks.toolEffectiveness.add(key);
  }
  










  public static void removeBlockEffectiveness(Block block, int metadata, String toolClass)
  {
    ForgeHooks.initTools();
    List key = Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass });
    ForgeHooks.toolEffectiveness.remove(key);
  }
  






  public static void setBlockHarvestLevel(Block block, String toolClass, int harvestLevel)
  {
    
    




    for (int metadata = 0; metadata < 16; metadata++)
    {
      List key = Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass });
      ForgeHooks.toolHarvestLevels.put(key, Integer.valueOf(harvestLevel));
      ForgeHooks.toolEffectiveness.add(key);
    }
  }
  








  public static int getBlockHarvestLevel(Block block, int metadata, String toolClass)
  {
    ForgeHooks.initTools();
    List key = Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass });
    Integer harvestLevel = (Integer)ForgeHooks.toolHarvestLevels.get(key);
    if (harvestLevel == null)
    {
      return -1;
    }
    return harvestLevel.intValue();
  }
  





  public static void removeBlockEffectiveness(Block block, String toolClass)
  {
    
    



    for (int metadata = 0; metadata < 16; metadata++)
    {
      List key = Arrays.asList(new Serializable[] { Integer.valueOf(block.id), Integer.valueOf(metadata), toolClass });
      ForgeHooks.toolEffectiveness.remove(key);
    }
  }
  



  public static void killMinecraft(String mod, String message)
  {
    throw new RuntimeException(mod + ": " + message);
  }
  






  public static void versionDetect(String mod, int major, int minor, int revision)
  {
    if (major != 3)
    {
      killMinecraft(mod, "MinecraftForge Major Version Mismatch, expecting " + major + ".x.x");
    }
    else if (minor != 3)
    {
      if (minor > 3)
      {
        killMinecraft(mod, "MinecraftForge Too Old, need at least " + major + "." + minor + "." + revision);
      }
      else
      {
        System.out.println(mod + ": MinecraftForge minor version mismatch, expecting " + major + "." + minor + ".x, may lead to unexpected behavior");
      }
    }
    else if (revision > 8)
    {
      killMinecraft(mod, "MinecraftForge Too Old, need at least " + major + "." + minor + "." + revision);
    }
  }
  







  public static void versionDetectStrict(String mod, int major, int minor, int revision)
  {
    if (major != 3)
    {
      killMinecraft(mod, "MinecraftForge Major Version Mismatch, expecting " + major + ".x.x");
    }
    else if (minor != 3)
    {
      if (minor > 3)
      {
        killMinecraft(mod, "MinecraftForge Too Old, need at least " + major + "." + minor + "." + revision);
      }
      else
      {
        killMinecraft(mod, "MinecraftForge minor version mismatch, expecting " + major + "." + minor + ".x");
      }
    }
    else if (revision > 8)
    {
      killMinecraft(mod, "MinecraftForge Too Old, need at least " + major + "." + minor + "." + revision);
    }
  }
  
  private static int dungeonLootAttempts = 8;
  private static ArrayList<ObjectPair<Float, String>> dungeonMobs = new ArrayList();
  private static ArrayList<ObjectPair<Float, DungeonLoot>> dungeonLoot = new ArrayList();
  




  public static void setDungeonLootTries(int number)
  {
    dungeonLootAttempts = number;
  }
  



  public static int getDungeonLootTries()
  {
    return dungeonLootAttempts;
  }
  














  public static float addDungeonMob(String name, float rarity)
  {
    if (rarity <= 0.0F)
    {
      throw new IllegalArgumentException("Rarity must be greater then zero");
    }
    
    for (ObjectPair<Float, String> mob : dungeonMobs)
    {
      if (name.equals(mob.getValue2()))
      {
        mob.setValue1(Float.valueOf(((Float)mob.getValue1()).floatValue() + rarity));
        return ((Float)mob.getValue1()).floatValue();
      }
    }
    
    dungeonMobs.add(new ObjectPair(Float.valueOf(rarity), name));
    return rarity;
  }
  






  public static float removeDungeonMob(String name)
  {
    for (ObjectPair<Float, String> mob : dungeonMobs)
    {
      if (name.equals(name))
      {
        dungeonMobs.remove(mob);
        return ((Float)mob.getValue1()).floatValue();
      }
    }
    return 0.0F;
  }
  





  public static String getRandomDungeonMob(Random rand)
  {
    float maxRarity = 0.0F;
    for (ObjectPair<Float, String> mob : dungeonMobs)
    {
      maxRarity += ((Float)mob.getValue1()).floatValue();
    }
    
    float targetRarity = rand.nextFloat() * maxRarity;
    for (ObjectPair<Float, String> mob : dungeonMobs)
    {
      if (targetRarity < ((Float)mob.getValue1()).floatValue())
      {
        return (String)mob.getValue2();
      }
      targetRarity -= ((Float)mob.getValue1()).floatValue();
    }
    
    return "";
  }
  









  public static void addDungeonLoot(ItemStack item, float rarity)
  {
    addDungeonLoot(item, rarity, 1, 1);
  }
  













  public static float addDungeonLoot(ItemStack item, float rarity, int minCount, int maxCount)
  {
    for (ObjectPair<Float, DungeonLoot> loot : dungeonLoot)
    {
      if (((DungeonLoot)loot.getValue2()).equals(item, minCount, maxCount))
      {
        loot.setValue1(Float.valueOf(((Float)loot.getValue1()).floatValue() + rarity));
        return ((Float)loot.getValue1()).floatValue();
      }
    }
    
    dungeonLoot.add(new ObjectPair(Float.valueOf(rarity), new DungeonLoot(item, minCount, maxCount)));
    return rarity;
  }
  







  public static float removeDungeonLoot(ItemStack item)
  {
    return removeDungeonLoot(item, -1, 0);
  }
  











  public static float removeDungeonLoot(ItemStack item, int minCount, int maxCount)
  {
    float rarity = 0.0F;
    ArrayList<ObjectPair<Float, DungeonLoot>> lootTmp = (ArrayList)dungeonLoot.clone();
    if (minCount < 0)
    {
      for (ObjectPair<Float, DungeonLoot> loot : lootTmp)
      {
        if (((DungeonLoot)loot.getValue2()).equals(item))
        {
          dungeonLoot.remove(loot);
          rarity += ((Float)loot.getValue1()).floatValue();
        }
        
      }
      
    } else {
      for (ObjectPair<Float, DungeonLoot> loot : lootTmp)
      {
        if (((DungeonLoot)loot.getValue2()).equals(item, minCount, maxCount))
        {
          dungeonLoot.remove(loot);
          rarity += ((Float)loot.getValue1()).floatValue();
        }
      }
    }
    
    return rarity;
  }
  





  public static ItemStack getRandomDungeonLoot(Random rand)
  {
    float maxRarity = 0.0F;
    for (ObjectPair<Float, DungeonLoot> loot : dungeonLoot)
    {
      maxRarity += ((Float)loot.getValue1()).floatValue();
    }
    
    float targetRarity = rand.nextFloat() * maxRarity;
    for (ObjectPair<Float, DungeonLoot> loot : dungeonLoot)
    {
      if (targetRarity < ((Float)loot.getValue1()).floatValue())
      {
        return ((DungeonLoot)loot.getValue2()).generateStack(rand);
      }
      targetRarity -= ((Float)loot.getValue1()).floatValue();
    }
    
    return null;
  }
  

  private static LinkedList<AchievementPage> achievementPages = new LinkedList();
  




  public static void registerAchievementPage(AchievementPage page)
  {
    if (getAchievementPage(page.getName()) != null)
    {
      throw new RuntimeException("Duplicate achievement page name \"" + page.getName() + "\"!");
    }
    achievementPages.add(page);
  }

  public static AchievementPage getAchievementPage(int index)
  {
    return (AchievementPage)achievementPages.get(index);
  }

  public static AchievementPage getAchievementPage(String name)
  {
    for (AchievementPage page : achievementPages)
    {
      if (page.getName().equals(name))
      {
        return page;
      }
    }
    return null;
  }

  public static Set<AchievementPage> getAchievementPages()
  {
    return new HashSet(achievementPages);
  }

  public static boolean isAchievementInPages(Achievement achievement)
  {
    for (AchievementPage page : achievementPages)
    {
      if (page.getAchievements().contains(achievement))
      {
        return true;
      }
    }
    return false;
  }

  private static Map<MinecartKey, ItemStack> itemForMinecart = new HashMap();
  private static Map<ItemStack, MinecartKey> minecartForItem = new HashMap();

  public static void registerMinecart(Class<? extends EntityMinecart> cart, ItemStack item)
  {
    registerMinecart(cart, 0, item);
  }

  public static void registerMinecart(Class<? extends EntityMinecart> minecart, int type, ItemStack item)
  {
    MinecartKey key = new MinecartKey(minecart, type);
    itemForMinecart.put(key, item);
    minecartForItem.put(item, key);
  }

  public static void removeMinecart(Class<? extends EntityMinecart> minecart, int type)
  {
    MinecartKey key = new MinecartKey(minecart, type);
    ItemStack item = (ItemStack)itemForMinecart.remove(key);
    if (item != null)
    {
      minecartForItem.remove(item);
    }
  }

  public static ItemStack getItemForCart(Class<? extends EntityMinecart> minecart)
  {
    return getItemForCart(minecart, 0);
  }

  public static ItemStack getItemForCart(Class<? extends EntityMinecart> minecart, int type)
  {
    ItemStack item = (ItemStack)itemForMinecart.get(new MinecartKey(minecart, type));
    if (item == null)
    {
      return null;
    }
    return item.cloneItemStack();
  }

  public static ItemStack getItemForCart(EntityMinecart cart)
  {
    return getItemForCart(cart.getClass(), cart.type); // BTCS: cart.getMinecartType() --> cart.type
  }

  public static Class<? extends EntityMinecart> getCartClassForItem(ItemStack item)
  {
    MinecartKey key = null;
    for (Map.Entry<ItemStack, MinecartKey> entry : minecartForItem.entrySet())
    {
      if (((ItemStack)entry.getKey()).c(item))
      {
        key = (MinecartKey)entry.getValue();
        break;
      }
    }
    if (key != null)
    {
      return key.minecart;
    }
    return null;
  }
  







  public static int getCartTypeForItem(ItemStack item)
  {
    MinecartKey key = null;
    for (Map.Entry<ItemStack, MinecartKey> entry : minecartForItem.entrySet())
    {
      if (((ItemStack)entry.getKey()).c(item))
      {
        key = (MinecartKey)entry.getValue();
        break;
      }
    }
    if (key != null)
    {
      return key.type;
    }
    return -1;
  }
  




  public static Set<ItemStack> getAllCartItems()
  {
    Set<ItemStack> ret = new HashSet();
    for (ItemStack item : minecartForItem.keySet())
    {
      ret.add(item.cloneItemStack());
    }
    return ret;
  }
  












  public static boolean registerEntity(Class entityClass, NetworkMod mod, int ID, int range, int updateFrequency, boolean sendVelocityInfo)
  {
    if (ForgeHooks.entityTrackerMap.containsKey(entityClass))
    {
      return false;
    }
    ForgeHooks.entityTrackerMap.put(entityClass, new EntityTrackerInfo(mod, ID, range, updateFrequency, sendVelocityInfo));
    return true;
  }
  







  public static EntityTrackerInfo getEntityTrackerInfo(Entity entity, boolean checkSupers)
  {
    for (Map.Entry<Class, EntityTrackerInfo> entry : ForgeHooks.entityTrackerMap.entrySet())
    {
      if (((Class)entry.getKey()).isInstance(entity))
      {
        if ((!checkSupers) || (entry.getKey() == entity.getClass()))
        {
          return (EntityTrackerInfo)entry.getValue();
        }
      }
    }
    return null;
  }
  







  public static Class getEntityClass(int modID, int type)
  {
    for (Map.Entry<Class, EntityTrackerInfo> entry : ForgeHooks.entityTrackerMap.entrySet())
    {
      EntityTrackerInfo info = (EntityTrackerInfo)entry.getValue();
      if ((type == info.ID) && (modID == getModID(info.Mod)))
      {
        return (Class)entry.getKey();
      }
    }
    return null;
  }
  






  public static NetworkMod getModByID(int id)
  {
    return (NetworkMod)ForgeHooks.networkMods.get(Integer.valueOf(id));
  }
  






  public static int getModID(NetworkMod mod)
  {
    for (Map.Entry<Integer, NetworkMod> entry : ForgeHooks.networkMods.entrySet())
    {
      if (entry.getValue() == mod)
      {
        return ((Integer)entry.getKey()).intValue();
      }
    }
    return -1;
  }
  




  public static NetworkMod[] getNetworkMods()
  {
    ArrayList<NetworkMod> ret = new ArrayList();
    for (BaseMod mod : net.minecraft.server.ModLoader.getLoadedMods())
    {
      if ((mod instanceof NetworkMod))
      {
        ret.add((NetworkMod)mod);
      }
    }
    return (NetworkMod[])ret.toArray(new NetworkMod[0]);
  }
  






  public static void setGuiHandler(BaseMod mod, IGuiHandler handler)
  {
    ForgeHooks.guiHandlers.put(mod, handler);
  }
  






  public static IGuiHandler getGuiHandler(BaseMod mod)
  {
    return (IGuiHandler)ForgeHooks.guiHandlers.get(mod);
  }
  




  public static void registerArrowNockHandler(IArrowNockHandler handler)
  {
    ForgeHooks.arrowNockHandlers.add(handler);
  }
  




  public static void registerArrowLooseHandler(IArrowLooseHandler handler)
  {
    ForgeHooks.arrowLooseHandlers.add(handler);
  }
  






  public static void sendPacket(NetworkManager net, Packet packet)
  {
    ForgeHooks.getPacketHandler().sendPacket(net, packet);
  }
  










  public static void sendPacket(NetworkManager net, NetworkMod mod, short id, byte[] data)
  {
    if (data == null)
    {
      data = new byte[0];
    }
    
    if (data.length > 255)
    {
      throw new IllegalArgumentException(String.format("Data argument was to long, must not be longer then 255 bytes was %d", new Object[] { Integer.valueOf(data.length) }));
    }
    
    net.minecraft.server.Packet131ItemData pkt = new net.minecraft.server.Packet131ItemData();
    pkt.a = ((short)getModID(mod));
    pkt.b = id;
    pkt.c = data;
    sendPacket(net, pkt);
  }
  













  public static void sendTileEntityPacket(NetworkManager net, int x, short y, int z, byte action, int par1, int par2, int par3)
  {
    Packet132TileEntityData pkt = new Packet132TileEntityData();
    pkt.a = x;
    pkt.b = y;
    pkt.c = z;
    pkt.d = action;
    pkt.e = par1;
    pkt.f = par2;
    pkt.g = par3;
    sendPacket(net, pkt);
  }
  
  private static int isClient = -1;
  
  public static boolean isClient() {
    return false;
  }
  




  public static void initialize()
  {
    Block.STONE.getTextureFile();
    Item.GOLDEN_APPLE.getTextureFile();
    
    Block filler = null;
    try
    {
      filler = (Block)Block.class.getConstructor(new Class[] { Integer.TYPE, net.minecraft.server.Material.class }).newInstance(new Object[] { Integer.valueOf(256), net.minecraft.server.Material.AIR });
    }
    catch (Exception e) {}
    if (filler == null)
    {
      throw new RuntimeException("Could not create Forge filler block");
    }
    
    for (int x = 256; x < 4096; x++)
    {
      if (Item.byId[(x - 256)] != null)
      {
        Block.byId[x] = filler;
      }
    }
  }
  
  static
  {
    addDungeonMob("Skeleton", 1.0F);
    addDungeonMob("Zombie", 2.0F);
    addDungeonMob("Spider", 1.0F);
    
    addDungeonLoot(new ItemStack(Item.SADDLE), 1.0F);
    addDungeonLoot(new ItemStack(Item.IRON_INGOT), 1.0F, 1, 4);
    addDungeonLoot(new ItemStack(Item.BREAD), 1.0F);
    addDungeonLoot(new ItemStack(Item.WHEAT), 1.0F, 1, 4);
    addDungeonLoot(new ItemStack(Item.SULPHUR), 1.0F, 1, 4);
    addDungeonLoot(new ItemStack(Item.STRING), 1.0F, 1, 4);
    addDungeonLoot(new ItemStack(Item.BUCKET), 1.0F);
    addDungeonLoot(new ItemStack(Item.GOLDEN_APPLE), 0.01F);
    addDungeonLoot(new ItemStack(Item.REDSTONE), 0.5F, 1, 4);
    addDungeonLoot(new ItemStack(Item.RECORD_1), 0.05F);
    addDungeonLoot(new ItemStack(Item.RECORD_2), 0.05F);
    addDungeonLoot(new ItemStack(Item.INK_SACK, 1, 3), 1.0F);
    
    registerMinecart(EntityMinecart.class, 0, new ItemStack(Item.MINECART));
    registerMinecart(EntityMinecart.class, 1, new ItemStack(Item.STORAGE_MINECART));
    registerMinecart(EntityMinecart.class, 2, new ItemStack(Item.POWERED_MINECART));
  }
}
