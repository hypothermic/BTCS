package forge;

import forge.packets.PacketModList;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityTracker;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;
import net.minecraft.server.Packet250CustomPayload;
import net.minecraft.server.mod_MinecraftForge;

public class ForgeHooksServer
{
  public static boolean OnTrackEntity(EntityTracker tracker, Entity entity)
  {
    EntityTrackerInfo info = MinecraftForge.getEntityTrackerInfo(entity, true);
    if (info != null)
    {
      tracker.addEntity(entity, info.Range, info.UpdateFrequency, info.SendVelocityInfo);
      return true;
    }
    return false;
  }
  
  public static void sendModListRequest(NetworkManager net)
  {
    NetworkMod[] list = MinecraftForge.getNetworkMods();
    PacketModList pkt = new PacketModList(true);
    
    for (NetworkMod mod : list)
    {
      pkt.ModIDs.put(Integer.valueOf(MinecraftForge.getModID(mod)), mod.toString());
    }
    
    ((NetServerHandler)net.getNetHandler()).sendPacket(pkt.getPacket());
    // BTCS start
    /*((PacketHandlerServer)ForgeHooks.getPacketHandler()); if (PacketHandlerServer.DEBUG)*/
    if (((PacketHandlerServer)ForgeHooks.getPacketHandler()).DEBUG)
    // BTCS end
    {
      System.out.println("S->C: " + pkt.toString(true));
    }
  }
  
  public static void handleLoginPacket(Packet1Login pktLogin, NetServerHandler net, NetworkManager manager)
  {
    
    if (pktLogin.d == 68066119)
    {
      ForgeHooks.onLogin(manager, pktLogin);
      
      String[] channels = MessageManager.getInstance().getRegisteredChannels(manager);
      StringBuilder tmp = new StringBuilder();
      tmp.append("Forge");
      for (String channel : channels)
      {
        tmp.append("\000");
        tmp.append(channel);
      }
      Packet250CustomPayload pkt = new Packet250CustomPayload();
      pkt.tag = "REGISTER";
      try {
        pkt.data = tmp.toString().getBytes("UTF8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      pkt.length = pkt.data.length;
      net.sendPacket(pkt);
      sendModListRequest(manager);
    }
    else
    {
      net.disconnect(mod_MinecraftForge.NO_FORGE_KICK_MESSAGE);
    }
  }
  

  private static boolean hasInit = false;
  
  public static void init() {
    if (hasInit)
    {
      return;
    }
    hasInit = true;
    ForgeHooks.setPacketHandler(new PacketHandlerServer());
  }
  
  static
  {
    init();
  }
}
