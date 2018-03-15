package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public abstract interface PluginMessageRecipient
{
  public abstract void sendPluginMessage(Plugin paramPlugin, String paramString, byte[] paramArrayOfByte);
  
  public abstract Set<String> getListeningPluginChannels();
}
