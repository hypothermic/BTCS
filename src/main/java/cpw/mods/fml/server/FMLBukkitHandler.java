package cpw.mods.fml.server;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IConsoleHandler;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IDispenseHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.INetworkHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.ProxyInjector;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.modloader.ModProperty;
import cpw.mods.fml.common.registry.FMLRegistry;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import net.minecraft.server.BaseMod;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Block;
import net.minecraft.server.BukkitRegistry;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.LocaleLanguage;
import net.minecraft.server.MLProp;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.SidedProxy;
import net.minecraft.server.World;
import net.minecraft.server.WorldType;
import org.bukkit.Material;

public class FMLBukkitHandler
  implements IFMLSidedHandler
{
  private static final FMLBukkitHandler INSTANCE = new FMLBukkitHandler();

  private MinecraftServer server;

  private BiomeBase[] defaultOverworldBiomes;

  FMLBukkitProfiler profiler;

  public void onPreLoad(MinecraftServer minecraftServer)
  {
    try
    {
      Class.forName("BaseModMp", false, getClass().getClassLoader());
      MinecraftServer.log.severe("Forge Mod Loader has detected that this server has an ModLoaderMP installed alongside Forge Mod Loader.\nThis will cause a serious problem with compatibility. To protect your worlds, this minecraft server will now shutdown.\nYou should follow the installation instructions of either Minecraft Forge of Forge Mod Loader and NOT install ModLoaderMP \ninto the minecraft_server.jar file before this server will be allowed to start up.\n\nFailure to do so will simply result in more startup failures.\n\nThe authors of Minecraft Forge and Forge Mod Loader strongly suggest you talk to your mod's authors and get them to\nupdate their requirements. ModLoaderMP is not compatible with Minecraft Forge on the server and they will need to update their mod\nfor Minecraft Forge and other server compatibility, unless they are Minecraft Forge mods, in which case they already\ndon't need ModLoaderMP and the mod author simply has failed to update his requirements and should be informed appropriately.\n\nThe authors of Forge Mod Loader would like to be compatible with ModLoaderMP but it is closed source and owned by SDK.\nSDK, the author of ModLoaderMP, has a standing invitation to submit compatibility patches \nto the open source community project that is Forge Mod Loader so that this incompatibility doesn't last. \nUsers who wish to enjoy mods of both types are encouraged to request of SDK that he submit a\ncompatibility patch to the Forge Mod Loader project at \nhttp://github.com/cpw/FML.\nPosting on the minecraft forums at\nhttp://www.minecraftforum.net/topic/86765- (the MLMP thread)\nmay encourage him in this effort. However, I ask that your requests be polite.\nNow, the server has to shutdown so you can reinstall your minecraft_server.jar\nproperly, until such time as we can work together.");

      throw new RuntimeException("This FML based server has detected an installation of ModLoaderMP alongside. This will cause serious compatibility issues, so the server will now shut down.");

    }
    catch (ClassNotFoundException e)
    {

      this.server = minecraftServer;
      FMLCommonHandler.instance().beginLoading(this);
      FMLRegistry.registerRegistry(new BukkitRegistry());
      Loader.instance().loadMods();
    }
  }
  


  public void onLoadComplete()
  {
    Loader.instance().initializeMods();
    for (Item i : Item.byId) {
      if ((i != null) && (Material.getMaterial(i.id).name().startsWith("X"))) {
        Material.setMaterialName(i.id, i.l());
      }
    }
  }
  
  public void onWorldLoadTick()
  {
    FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.WORLDLOAD), new Object[0]);
  }
  



  public void onPreWorldTick(World world)
  {
    FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.WORLD), new Object[] { world });
  }
  



  public void onPostWorldTick(World world)
  {
    FMLCommonHandler.instance().tickEnd(EnumSet.of(TickType.WORLD), new Object[] { world });
  }
  





  public MinecraftServer getServer()
  {
    return this.server;
  }
  



  public Logger getMinecraftLogger()
  {
    return MinecraftServer.log;
  }
  












  public void onChunkPopulate(IChunkProvider chunkProvider, int chunkX, int chunkZ, World world, IChunkProvider generator)
  {
    FMLCommonHandler.instance().handleWorldGeneration(chunkX, chunkZ, world.getSeed(), new Object[] { world, generator, chunkProvider });
  }
  







  public int fuelLookup(int itemId, int itemDamage)
  {
    int fv = 0;
    
    for (ModContainer mod : Loader.getModList())
    {
      fv = Math.max(fv, mod.lookupFuelValue(itemId, itemDamage));
    }
    
    return fv;
  }
  



  public boolean isModLoaderMod(Class<?> clazz)
  {
    return net.minecraft.server.BaseMod.class.isAssignableFrom(clazz);
  }
  




  public ModContainer loadBaseModMod(Class<?> clazz, File canonicalFile)
  {
    Class<? extends net.minecraft.server.BaseMod> bmClazz = (Class<? extends BaseMod>) clazz;
    return new ModLoaderModContainer(bmClazz, canonicalFile);
  }
  






  public void notifyItemPickup(EntityItem entityItem, EntityHuman entityPlayer)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsPickupNotification())
      {
        mod.getPickupNotifier().notifyPickup(new Object[] { entityItem, entityPlayer });
      }
    }
  }
  







  public void raiseException(Throwable exception, String message, boolean stopGame)
  {
    FMLCommonHandler.instance().getFMLLogger().throwing("FMLHandler", "raiseException", exception);
    throw new RuntimeException(exception);
  }
  












  public boolean tryDispensingEntity(World world, double x, double y, double z, byte xVelocity, byte zVelocity, ItemStack item)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsToDispense()) { if (mod.getDispenseHandler().dispense(x, y, z, xVelocity, zVelocity, new Object[] { world, item }))
        {
          return true;
        }
      }
    }
    return false;
  }
  



  public static FMLBukkitHandler instance()
  {
    return INSTANCE;
  }
  





  public BiomeBase[] getDefaultOverworldBiomes()
  {
    if (this.defaultOverworldBiomes == null)
    {
      ArrayList<BiomeBase> biomes = new ArrayList(20);
      
      for (int i = 0; i < 23; i++)
      {
        if ((!"Sky".equals(BiomeBase.biomes[i].y)) && (!"Hell".equals(BiomeBase.biomes[i].y)))
        {



          biomes.add(BiomeBase.biomes[i]);
        }
      }
      this.defaultOverworldBiomes = new BiomeBase[biomes.size()];
      biomes.toArray(this.defaultOverworldBiomes);
    }
    
    return this.defaultOverworldBiomes;
  }
  







  public void onItemCrafted(EntityHuman player, ItemStack craftedItem, IInventory craftingGrid)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsCraftingNotification())
      {
        mod.getCraftingHandler().onCrafting(new Object[] { player, craftedItem, craftingGrid });
      }
    }
  }
  






  public void onItemSmelted(EntityHuman player, ItemStack smeltedItem)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsCraftingNotification())
      {
        mod.getCraftingHandler().onSmelting(new Object[] { player, smeltedItem });
      }
    }
  }
  







  public boolean handleChatPacket(Packet3Chat chat, EntityHuman player)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsNetworkPackets()) { if (mod.getNetworkHandler().onChat(new Object[] { chat, player }))
        {
          return true;
        }
      }
    }
    return false;
  }
  






  public void handlePacket250(Packet250CustomPayload packet, EntityHuman player)
  {
    if (("REGISTER".equals(packet.tag)) || ("UNREGISTER".equals(packet.tag)))
    {
      handleClientRegistration(packet, player);
      return;
    }
    
    ModContainer mod = FMLCommonHandler.instance().getModForChannel(packet.tag);
    
    if (mod != null)
    {
      mod.getNetworkHandler().onPacket250Packet(new Object[] { packet, player });
    }
  }
  





  private void handleClientRegistration(Packet250CustomPayload packet, EntityHuman player)
  {
    if (packet.data == null) {
      return;
    }
    try
    {
      for (String channel : new String(packet.data, "UTF8").split("\000"))
      {

        if (FMLCommonHandler.instance().getModForChannel(channel) != null)
        {



          if ("REGISTER".equals(packet.tag))
          {
            FMLCommonHandler.instance().activateChannel(player, channel);
          }
          else
          {
            FMLCommonHandler.instance().deactivateChannel(player, channel);
          }
        }
      }
    }
    catch (UnsupportedEncodingException e) {
      getMinecraftLogger().warning("Received invalid registration packet");
    }
  }
  






  public void handleLogin(Packet1Login loginPacket, NetworkManager networkManager)
  {
    Packet250CustomPayload packet = new Packet250CustomPayload();
    packet.tag = "REGISTER";
    packet.data = FMLCommonHandler.instance().getPacketRegistry();
    packet.length = packet.data.length;
    if (packet.length > 0) {
      networkManager.queue(packet);
    }
  }
  
  public void announceLogin(EntityHuman player) {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsPlayerTracking())
      {
        mod.getPlayerTracker().onPlayerLogin(player);
      }
    }
  }
  
  public File getMinecraftRootDirectory()
  {
    try
    {
      return this.server.a(".").getCanonicalFile();
    } catch (IOException ioe) {}
    return new File(".");
  }
  





  public boolean handleServerCommand(String command, String player, ICommandListener listener)
  {
    for (ModContainer mod : Loader.getModList()) {
      if (mod.wantsConsoleCommands()) if (mod.getConsoleHandler().handleCommand(command, new Object[] { player, listener })) {
          return true;
        }
    }
    return false;
  }
  



  public void announceLogout(EntityHuman player)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsPlayerTracking())
      {
        mod.getPlayerTracker().onPlayerLogout(player);
      }
    }
  }
  



  public void announceDimensionChange(EntityHuman player)
  {
    for (ModContainer mod : Loader.getModList())
    {
      if (mod.wantsPlayerTracking())
      {
        mod.getPlayerTracker().onPlayerChangedDimension(player);
      }
    }
  }
  



  public void addBiomeToDefaultWorldGenerator(BiomeBase biome)
  {
    WorldType.NORMAL.addNewBiome(biome);
  }
  



  public void removeBiomeFromDefaultWorldGenerator(BiomeBase biome)
  {
    WorldType.NORMAL.removeBiome(biome);
  }
  
  public Object getMinecraftInstance()
  {
    return this.server;
  }
  

  public String getCurrentLanguage()
  {
    return LocaleLanguage.a().getCurrentLanguage();
  }
  

  public Properties getCurrentLanguageTable()
  {
    return LocaleLanguage.a().getCurrentLanguageTable();
  }
  

  public String getObjectName(Object instance)
  {
    String objectName;
    // BTCS start
    /*if ((instance instanceof Item)) {
      objectName = ((Item)instance).getName(); } else { 
      String objectName;
      if ((instance instanceof Block)) {
        objectName = ((Block)instance).getName(); } else { String objectName;
        if ((instance instanceof ItemStack)) {
          objectName = Item.byId[((ItemStack)instance).id].a((ItemStack)instance);
        } else
          throw new IllegalArgumentException(String.format("Illegal object for naming %s", new Object[] { instance }));
      } }*/
    if (instance instanceof Item) {
        objectName=((Item)instance).getName();
    } else if (instance instanceof Block) {
        objectName=((Block)instance).getName();
    } else if (instance instanceof ItemStack) {
        objectName=Item.byId[((ItemStack)instance).id].a((ItemStack)instance);
    } else {
        throw new IllegalArgumentException(String.format("Illegal object for naming %s",instance));
    }
    // BTCS end
    objectName+=".name";
    return objectName;
  }
  
  public ModMetadata readMetadataFrom(InputStream input, ModContainer mod)
    throws Exception
  {
    return null;
  }
  
  public void profileStart(String profileLabel)
  {
    if (this.profiler != null)
    {
      this.profiler.start(profileLabel);
    }
  }
  
  public void profileEnd()
  {
    if (this.profiler != null)
    {
      this.profiler.end();
    }
  }
  
  public ModProperty getModLoaderPropertyFor(Field f)
  {
    if (f.isAnnotationPresent(MLProp.class)) {
      MLProp prop = (MLProp)f.getAnnotation(MLProp.class);
      return new ModProperty(prop.info(), prop.min(), prop.max(), prop.name());
    }
    return null;
  }
  
  public List<String> getAdditionalBrandingInformation()
  {
    return null;
  }
  
  public Side getSide()
  {
    return Side.BUKKIT;
  }
  
  public ProxyInjector findSidedProxyOn(cpw.mods.fml.common.modloader.BaseMod mod)
  {
    for (Field f : mod.getClass().getDeclaredFields())
    {
      if (f.isAnnotationPresent(SidedProxy.class))
      {
        SidedProxy sp = (SidedProxy)f.getAnnotation(SidedProxy.class);
        return new ProxyInjector(sp.clientSide(), sp.serverSide(), sp.bukkitSide(), f);
      }
    }
    return null;
  }
  
  public void onServerPostTick() {
    FMLCommonHandler.instance().tickEnd(EnumSet.of(TickType.GAME), new Object[0]);
  }
  
  public void onServerPreTick() {
    FMLCommonHandler.instance().tickStart(EnumSet.of(TickType.GAME), new Object[0]);
  }
}
