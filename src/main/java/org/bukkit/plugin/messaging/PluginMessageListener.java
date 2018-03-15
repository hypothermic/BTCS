package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;

public abstract interface PluginMessageListener
{
  public abstract void onPluginMessageReceived(String paramString, Player paramPlayer, byte[] paramArrayOfByte);
}
