package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract interface Messenger
{
  public static final int MAX_MESSAGE_SIZE = 32766;
  public static final int MAX_CHANNEL_SIZE = 16;
  
  public abstract boolean isReservedChannel(String paramString);
  
  public abstract void registerOutgoingPluginChannel(Plugin paramPlugin, String paramString);
  
  public abstract void unregisterOutgoingPluginChannel(Plugin paramPlugin, String paramString);
  
  public abstract void unregisterOutgoingPluginChannel(Plugin paramPlugin);
  
  public abstract PluginMessageListenerRegistration registerIncomingPluginChannel(Plugin paramPlugin, String paramString, PluginMessageListener paramPluginMessageListener);
  
  public abstract void unregisterIncomingPluginChannel(Plugin paramPlugin, String paramString, PluginMessageListener paramPluginMessageListener);
  
  public abstract void unregisterIncomingPluginChannel(Plugin paramPlugin, String paramString);
  
  public abstract void unregisterIncomingPluginChannel(Plugin paramPlugin);
  
  public abstract Set<String> getOutgoingChannels();
  
  public abstract Set<String> getOutgoingChannels(Plugin paramPlugin);
  
  public abstract Set<String> getIncomingChannels();
  
  public abstract Set<String> getIncomingChannels(Plugin paramPlugin);
  
  public abstract Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin paramPlugin);
  
  public abstract Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(String paramString);
  
  public abstract Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin paramPlugin, String paramString);
  
  public abstract boolean isRegistrationValid(PluginMessageListenerRegistration paramPluginMessageListenerRegistration);
  
  public abstract boolean isIncomingChannelRegistered(Plugin paramPlugin, String paramString);
  
  public abstract boolean isOutgoingChannelRegistered(Plugin paramPlugin, String paramString);
  
  public abstract void dispatchIncomingMessage(Player paramPlayer, String paramString, byte[] paramArrayOfByte);
}
