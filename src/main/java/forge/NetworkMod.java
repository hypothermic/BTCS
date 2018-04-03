package forge;

import net.minecraft.server.BaseMod;
import net.minecraft.server.NetworkManager;










public abstract class NetworkMod
  extends BaseMod
{
  public boolean clientSideRequired()
  {
    return false;
  }
  





  public boolean serverSideRequired()
  {
    return false;
  }
  
  public void onPacketData(NetworkManager net, short id, byte[] data) {}
}
