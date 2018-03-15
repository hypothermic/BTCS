package forge;

import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;

public abstract interface IConnectionHandler
{
  public abstract void onConnect(NetworkManager paramNetworkManager);
  
  public abstract void onLogin(NetworkManager paramNetworkManager, Packet1Login paramPacket1Login);
  
  public abstract void onDisconnect(NetworkManager paramNetworkManager, String paramString, Object[] paramArrayOfObject);
}
