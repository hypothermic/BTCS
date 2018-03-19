package forge;

import net.minecraft.server.NetworkManager;

public abstract interface IPacketHandler
{
  public abstract void onPacketData(NetworkManager paramNetworkManager, String paramString, byte[] paramArrayOfByte);
}
