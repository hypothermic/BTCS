package net.minecraft.server;

import cpw.mods.fml.common.TickType;
import java.util.Map;
import java.util.Random;
import org.bukkit.craftbukkit.generator.NetherChunkGenerator;
import org.bukkit.craftbukkit.generator.NormalChunkGenerator;




















public abstract class BaseMod
  implements cpw.mods.fml.common.modloader.BaseMod
{
  public final boolean doTickInGame(TickType tick, boolean tickEnd, Object minecraftInstance, Object... data)
  {
    if ((tick == TickType.GAME) && (tickEnd)) {
      return onTickInGame((MinecraftServer)minecraftInstance);
    }
    return true;
  }
  


  public final void onCrafting(Object... craftingParameters)
  {
    takenFromCrafting((EntityHuman)craftingParameters[0], (ItemStack)craftingParameters[1], (IInventory)craftingParameters[2]);
  }
  

  public final void onSmelting(Object... smeltingParameters)
  {
    takenFromFurnace((EntityHuman)smeltingParameters[0], (ItemStack)smeltingParameters[1]);
  }
  

  public final boolean dispense(double x, double y, double z, byte xVelocity, byte zVelocity, Object... data)
  {
    return dispenseEntity((World)data[0], x, y, z, xVelocity, zVelocity, (ItemStack)data[1]);
  }
  

  public final boolean onChat(Object... data)
  {
    return onChatMessageReceived((EntityHuman)data[1], (Packet3Chat)data[0]);
  }
  


  public final void onServerLogin(Object handler) {}
  


  public final void onServerLogout() {}
  

  public final void onPlayerLogin(Object player)
  {
    onClientLogin((EntityHuman)player);
  }
  

  public final void onPlayerLogout(Object player)
  {
    onClientLogout((EntityHuman)player);
  }
  

  public final void onPlayerChangedDimension(Object player)
  {
    onClientDimensionChanged((EntityHuman)player);
  }
  

  public final void onPacket250Packet(Object... data)
  {
    onPacket250Received((EntityHuman)data[1], (Packet250CustomPayload)data[0]);
  }
  

  public final void notifyPickup(Object... pickupData)
  {
    EntityItem item = (EntityItem)pickupData[0];
    EntityHuman player = (EntityHuman)pickupData[1];
    onItemPickup(player, item.itemStack);
  }
  

  public final void generate(Random random, int chunkX, int chunkZ, Object... additionalData)
  {
    World w = (World)additionalData[0];
    IChunkProvider cp = (IChunkProvider)additionalData[1];
    
    if (((cp instanceof ChunkProviderHell)) || ((cp instanceof NetherChunkGenerator)))
    {
      generateNether(w, random, chunkX << 4, chunkZ << 4);
    }
    else if (((cp instanceof ChunkProviderGenerate)) || ((cp instanceof NormalChunkGenerator)))
    {
      generateSurface(w, random, chunkX << 4, chunkZ << 4);
    }
  }
  




  public final boolean handleCommand(String command, Object... data)
  {
    return onServerCommand(command, (String)data[0], (ICommandListener)data[1]);
  }
  





  public void onRegisterAnimations() {}
  




  public void onRenderHarvest(Map renderers) {}
  




  public int addFuel(int id, int metadata)
  {
    return 0;
  }
  








  public void addRenderer(Map<Class<? extends Entity>, Object> renderers) {}
  







  public boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item)
  {
    return false;
  }
  









  public void generateNether(World world, Random random, int chunkX, int chunkZ) {}
  








  public void generateSurface(World world, Random random, int chunkX, int chunkZ) {}
  








  public String getName()
  {
    return getClass().getSimpleName();
  }
  





  public String getPriorities()
  {
    return "";
  }
  





  public abstract String getVersion();
  





  public void keyboardEvent(Object event) {}
  





  public abstract void load();
  





  public void modsLoaded() {}
  





  public void onItemPickup(EntityHuman player, ItemStack item) {}
  





  public boolean onTickInGame(MinecraftServer minecraftServer)
  {
    return false;
  }
  
  public boolean onTickInGUI(float tick, Object game, Object gui)
  {
    return false;
  }
  





  public void receiveChatPacket(String text) {}
  





  public void receiveCustomPacket(Packet250CustomPayload packet) {}
  





  public void registerAnimation(Object game) {}
  





  public void renderInvBlock(Object renderer, Block block, int metadata, int modelID) {}
  




  public boolean renderWorldBlock(Object renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID)
  {
    return false;
  }
  








  public void takenFromCrafting(EntityHuman player, ItemStack item, IInventory matrix) {}
  







  public void takenFromFurnace(EntityHuman player, ItemStack item) {}
  







  public String toString()
  {
    return getName() + " " + getVersion();
  }
  








  public void onPacket250Received(EntityHuman source, Packet250CustomPayload payload) {}
  







  public boolean onChatMessageReceived(EntityHuman source, Packet3Chat chat)
  {
    return false;
  }
  




  public boolean onServerCommand(String command, String sender, ICommandListener listener)
  {
    return false;
  }
  
  public void onClientLogin(EntityHuman player) {}
  
  public void onClientLogout(EntityHuman player) {}
  
  public void onClientDimensionChanged(EntityHuman player) {}
  
  public void keyBindingEvent(Object keybinding) {}
}
