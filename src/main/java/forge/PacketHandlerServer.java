package forge;

import com.google.common.base.Joiner;
import forge.packets.PacketMissingMods;
import forge.packets.PacketModList;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.MobEffect;
import net.minecraft.server.ModLoader;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.WorldServer;
import org.bukkit.event.player.PlayerKickEvent;

public class PacketHandlerServer extends forge.packets.PacketHandlerBase
{
  public static boolean DEBUG = false;
  
  public void onPacketData(NetworkManager network, String channel, byte[] bytes)
  {
    NetServerHandler net = (NetServerHandler)network.getNetHandler();
    DataInputStream data = new DataInputStream(new java.io.ByteArrayInputStream(bytes));
    forge.packets.ForgePacket pkt = null;
    
    try
    {
      int packetID = data.read();
      switch (packetID)
      {
      case 2: 
        pkt = new PacketModList(true);
        pkt.readData(data);
        onModListResponse(net, (PacketModList)pkt);
      }
      
    }
    catch (IOException e)
    {
      ModLoader.getLogger().log(Level.SEVERE, "Exception in PacketHandlerServer.onPacketData", e);
      e.printStackTrace();
    }
  }
  
  private void onModListResponse(NetServerHandler net, PacketModList pkt) throws IOException
  {
    if (DEBUG)
    {
      System.out.println("C->S: " + pkt.toString(true));
    }
    if (pkt.Length < 0)
    {
      net.disconnect("Invalid mod list response, Size: " + pkt.Length);
      return;
    }
    if (!pkt.has4096)
    {
      net.disconnect("Must have Forge build #136+ (4096 fix) to connect to this server");
      return;
    }
    if (pkt.Mods.length == 0)
    {
      ModLoader.getLogger().log(Level.INFO, net.getName() + " joined with no mods");
    }
    else
    {
      ModLoader.getLogger().log(Level.INFO, net.getName() + " joined with: " + java.util.Arrays.toString(pkt.Mods).replaceAll("mod_", ""));
    }
    


    NetworkMod[] serverMods = MinecraftForge.getNetworkMods();
    ArrayList<NetworkMod> missing = new ArrayList();
    for (NetworkMod mod : serverMods)
    {
      if (mod.clientSideRequired())
      {


        boolean found = false;
        for (String modName : pkt.Mods)
        {
          if (modName.equals(mod.toString()))
          {
            found = true;
            break;
          }
        }
        if (!found)
        {
          missing.add(mod); }
      }
    }
    if (missing.size() > 0)
    {
      doMissingMods(net, missing);
    }
    else
    {
      finishLogin(net);
    }
  }
  




  private void doMissingMods(NetServerHandler net, ArrayList<NetworkMod> list)
  {
    PacketMissingMods pkt = new PacketMissingMods(true);
    pkt.Mods = new String[list.size()];
    int x = 0;
    for (NetworkMod mod : list)
    {
      pkt.Mods[(x++)] = mod.toString();
    }
    if (DEBUG)
    {
      System.out.println("S->C: " + pkt.toString(true));
    }
    net.sendPacket(pkt.getPacket());
    disconnectUser(net, "Missing mods " + Joiner.on(", ").join(list));
    cpw.mods.fml.common.FMLCommonHandler.instance().getMinecraftLogger().log(Level.INFO, String.format("%s was disconnected because they are missing the following mods %s.\n", new Object[] { net.getName(), Joiner.on(", ").join(list) }));
  }
  




  void disconnectUser(NetServerHandler net, String list)
  {
    PlayerKickEvent event = new PlayerKickEvent(net.getServer().getPlayer(net.player), list, "§e" + net.getName() + " left the game.");
    net.getServer().getPluginManager().callEvent(event);
    net.player.I();
    net.networkManager.d();
    String leaveMessage = event.getLeaveMessage();
    if ((leaveMessage != null) && (leaveMessage.length() > 0)) {
      net.minecraftServer.serverConfigurationManager.sendAll(new net.minecraft.server.Packet3Chat(leaveMessage));
    }
    net.getPlayer().disconnect(event.getReason());
    net.minecraftServer.serverConfigurationManager.disconnect(net.player);
    net.disconnected = true;
  }
  
  private void finishLogin(NetServerHandler netserverhandler) {
    EntityPlayer entityplayer = netserverhandler.getPlayerEntity();
    WorldServer worldserver = (WorldServer)entityplayer.world;
    ChunkCoordinates chunkcoordinates = worldserver.getSpawn();
    int maxPlayers = netserverhandler.minecraftServer.serverConfigurationManager.getMaxPlayers();
    if (maxPlayers > 60) {
      maxPlayers = 60;
    }
    netserverhandler.sendPacket(new net.minecraft.server.Packet1Login("", entityplayer.id, worldserver.getWorldData().getType(), entityplayer.itemInWorldManager.getGameMode(), worldserver.worldProvider.dimension, (byte)worldserver.difficulty, (byte)worldserver.getHeight(), (byte)maxPlayers));
    entityplayer.getBukkitEntity().sendSupportedChannels();
    

    netserverhandler.sendPacket(new net.minecraft.server.Packet6SpawnPosition(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z));
    netserverhandler.sendPacket(new net.minecraft.server.Packet202Abilities(entityplayer.abilities));
    netserverhandler.minecraftServer.serverConfigurationManager.a(entityplayer, worldserver);
    
    netserverhandler.minecraftServer.serverConfigurationManager.c(entityplayer);
    netserverhandler.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
    netserverhandler.sendPacket(new net.minecraft.server.Packet4UpdateTime(entityplayer.getPlayerTime()));
    Iterator iterator = entityplayer.getEffects().iterator();
    
    while (iterator.hasNext()) {
      MobEffect mobeffect = (MobEffect)iterator.next();
      
      netserverhandler.sendPacket(new net.minecraft.server.Packet41MobEffect(entityplayer.id, mobeffect));
    }
    
    entityplayer.syncInventory();
    cpw.mods.fml.server.FMLBukkitHandler.instance().announceLogin(entityplayer);
  }
  

  public void sendPacket(NetworkManager network, net.minecraft.server.Packet packet)
  {
    NetServerHandler net = (NetServerHandler)network.getNetHandler();
    net.sendPacket(packet);
  }
}
