package forge;

import net.minecraft.server.EntityHuman;

public abstract interface IChatHandler
{
  public abstract String onServerChat(EntityHuman paramEntityHuman, String paramString);
  
  public abstract boolean onChatCommand(EntityHuman paramEntityHuman, boolean paramBoolean, String paramString);
  
  public abstract boolean onServerCommand(Object paramObject, String paramString1, String paramString2);
  
  public abstract String onServerCommandSay(Object paramObject, String paramString1, String paramString2);
  
  public abstract String onClientChatRecv(String paramString);
}
