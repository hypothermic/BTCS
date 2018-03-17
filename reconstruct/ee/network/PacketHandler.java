package ee.network;

import forge.IConnectionHandler;
import forge.IPacketHandler;
import forge.MessageManager;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet1Login;

public class PacketHandler implements IPacketHandler, IConnectionHandler
{
  public void onConnect(NetworkManager var1) {}
  
  public void onLogin(NetworkManager var1, Packet1Login var2)
  {
    MessageManager.getInstance().registerChannel(var1, this, "EE2");
  }
  
  public void onDisconnect(NetworkManager var1, String var2, Object[] var3)
  {
    MessageManager.getInstance().removeConnection(var1);
  }
  
  public void onPacketData(NetworkManager var1, String var2, byte[] var3)
  {
    new DataInputStream(new ByteArrayInputStream(var3));
    EEPacket var5 = PacketTypeHandler.buildPacket(var3);
    var5.execute(var1);
  }
  
  public static Packet getPacketForSending(EEPacket var0)
  {
    return PacketTypeHandler.populatePacket(var0);
  }
  
  public static EEPacket getPacket(PacketTypeHandler var0)
  {
    EEPacket var1 = PacketTypeHandler.buildPacket(var0);
    return var1;
  }
}
