package forge.packets;

import forge.IPacketHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;




public abstract class PacketHandlerBase
  implements IPacketHandler
{
  public static boolean DEBUG = false;
  
  public abstract void sendPacket(NetworkManager paramNetworkManager, Packet paramPacket);
}
