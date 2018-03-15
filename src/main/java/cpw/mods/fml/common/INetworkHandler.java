package cpw.mods.fml.common;

public abstract interface INetworkHandler
{
  public abstract boolean onChat(Object... paramVarArgs);
  
  public abstract void onPacket250Packet(Object... paramVarArgs);
  
  public abstract void onServerLogin(Object paramObject);
}
