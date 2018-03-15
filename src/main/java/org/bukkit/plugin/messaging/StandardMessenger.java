package org.bukkit.plugin.messaging;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class StandardMessenger
  implements Messenger
{
  private final Map<String, Set<PluginMessageListenerRegistration>> incomingByChannel = new HashMap();
  private final Map<Plugin, Set<PluginMessageListenerRegistration>> incomingByPlugin = new HashMap();
  private final Map<String, Set<Plugin>> outgoingByChannel = new HashMap();
  private final Map<Plugin, Set<String>> outgoingByPlugin = new HashMap();
  private final Object incomingLock = new Object();
  private final Object outgoingLock = new Object();
  
  private void addToOutgoing(Plugin plugin, String channel) {
    synchronized (this.outgoingLock) {
      Set<Plugin> plugins = (Set)this.outgoingByChannel.get(channel);
      Set<String> channels = (Set)this.outgoingByPlugin.get(plugin);
      
      if (plugins == null) {
        plugins = new HashSet();
        this.outgoingByChannel.put(channel, plugins);
      }
      
      if (channels == null) {
        channels = new HashSet();
        this.outgoingByPlugin.put(plugin, channels);
      }
      
      plugins.add(plugin);
      channels.add(channel);
    }
  }
  
  private void removeFromOutgoing(Plugin plugin, String channel) {
    synchronized (this.outgoingLock) {
      Set<Plugin> plugins = (Set)this.outgoingByChannel.get(channel);
      Set<String> channels = (Set)this.outgoingByPlugin.get(plugin);
      
      if (plugins != null) {
        plugins.remove(plugin);
        
        if (plugins.isEmpty()) {
          this.outgoingByChannel.remove(channel);
        }
      }
      
      if (channels != null) {
        channels.remove(channel);
        
        if (channels.isEmpty()) {
          this.outgoingByChannel.remove(channel);
        }
      }
    }
  }
  
  private void removeFromOutgoing(Plugin plugin) {
    synchronized (this.outgoingLock) {
      Set<String> channels = (Set)this.outgoingByPlugin.get(plugin);
      
      if (channels != null) {
        String[] toRemove = (String[])channels.toArray(new String[0]);
        
        this.outgoingByPlugin.remove(plugin);
        
        for (String channel : toRemove) {
          removeFromOutgoing(plugin, channel);
        }
      }
    }
  }
  
  private void addToIncoming(PluginMessageListenerRegistration registration) {
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByChannel.get(registration.getChannel());
      
      if (registrations == null) {
        registrations = new HashSet();
        this.incomingByChannel.put(registration.getChannel(), registrations);
      }
      else if (registrations.contains(registration)) {
        throw new IllegalArgumentException("This registration already exists");
      }
      

      registrations.add(registration);
      
      registrations = (Set)this.incomingByPlugin.get(registration.getPlugin());
      
      if (registrations == null) {
        registrations = new HashSet();
        this.incomingByPlugin.put(registration.getPlugin(), registrations);
      }
      else if (registrations.contains(registration)) {
        throw new IllegalArgumentException("This registration already exists");
      }
      

      registrations.add(registration);
    }
  }
  
  private void removeFromIncoming(PluginMessageListenerRegistration registration) {
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByChannel.get(registration.getChannel());
      
      if (registrations != null) {
        registrations.remove(registration);
        
        if (registrations.isEmpty()) {
          this.incomingByChannel.remove(registration.getChannel());
        }
      }
      
      registrations = (Set)this.incomingByPlugin.get(registration.getPlugin());
      
      if (registrations != null) {
        registrations.remove(registration);
        
        if (registrations.isEmpty()) {
          this.incomingByPlugin.remove(registration.getPlugin());
        }
      }
    }
  }
  
  private void removeFromIncoming(Plugin plugin, String channel) {
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        PluginMessageListenerRegistration[] toRemove = (PluginMessageListenerRegistration[])registrations.toArray(new PluginMessageListenerRegistration[0]);
        
        for (PluginMessageListenerRegistration registration : toRemove) {
          if (registration.getChannel().equals(channel)) {
            removeFromIncoming(registration);
          }
        }
      }
    }
  }
  
  private void removeFromIncoming(Plugin plugin) {
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        PluginMessageListenerRegistration[] toRemove = (PluginMessageListenerRegistration[])registrations.toArray(new PluginMessageListenerRegistration[0]);
        
        this.incomingByPlugin.remove(plugin);
        
        for (PluginMessageListenerRegistration registration : toRemove) {
          removeFromIncoming(registration);
        }
      }
    }
  }
  
  public boolean isReservedChannel(String channel) {
    validateChannel(channel);
    
    return (channel.equals("REGISTER")) || (channel.equals("UNREGISTER"));
  }
  
  public void registerOutgoingPluginChannel(Plugin plugin, String channel) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    if (isReservedChannel(channel)) {
      throw new ReservedChannelException(channel);
    }
    
    addToOutgoing(plugin, channel);
  }
  
  public void unregisterOutgoingPluginChannel(Plugin plugin, String channel) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    
    removeFromOutgoing(plugin, channel);
  }
  
  public void unregisterOutgoingPluginChannel(Plugin plugin) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    
    removeFromOutgoing(plugin);
  }
  
  public PluginMessageListenerRegistration registerIncomingPluginChannel(Plugin plugin, String channel, PluginMessageListener listener) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    if (isReservedChannel(channel)) {
      throw new ReservedChannelException(channel);
    }
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    
    PluginMessageListenerRegistration result = new PluginMessageListenerRegistration(this, plugin, channel, listener);
    
    addToIncoming(result);
    
    return result;
  }
  
  public void unregisterIncomingPluginChannel(Plugin plugin, String channel, PluginMessageListener listener) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    validateChannel(channel);
    
    removeFromIncoming(new PluginMessageListenerRegistration(this, plugin, channel, listener));
  }
  
  public void unregisterIncomingPluginChannel(Plugin plugin, String channel) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    
    removeFromIncoming(plugin, channel);
  }
  
  public void unregisterIncomingPluginChannel(Plugin plugin) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    
    removeFromIncoming(plugin);
  }
  
  public Set<String> getOutgoingChannels() {
    synchronized (this.outgoingLock) {
      Set<String> keys = this.outgoingByChannel.keySet();
      return ImmutableSet.copyOf(keys);
    }
  }
  
  public Set<String> getOutgoingChannels(Plugin plugin) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    
    synchronized (this.outgoingLock) {
      Set<String> channels = (Set)this.outgoingByPlugin.get(plugin);
      
      if (channels != null) {
        return ImmutableSet.copyOf(channels);
      }
      return ImmutableSet.of();
    }
  }
  
  public Set<String> getIncomingChannels()
  {
    synchronized (this.incomingLock) {
      Set<String> keys = this.incomingByChannel.keySet();
      return ImmutableSet.copyOf(keys);
    }
  }
  
  public Set<String> getIncomingChannels(Plugin plugin) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        
        for (PluginMessageListenerRegistration registration : registrations) {
          builder.add(registration.getChannel());
        }
        
        return builder.build();
      }
      return ImmutableSet.of();
    }
  }
  
  public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin plugin)
  {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        return ImmutableSet.copyOf(registrations);
      }
      return ImmutableSet.of();
    }
  }
  
  public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(String channel)
  {
    validateChannel(channel);
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByChannel.get(channel);
      
      if (registrations != null) {
        return ImmutableSet.copyOf(registrations);
      }
      return ImmutableSet.of();
    }
  }
  
  public Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(Plugin plugin, String channel)
  {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        ImmutableSet.Builder<PluginMessageListenerRegistration> builder = ImmutableSet.builder();
        
        for (PluginMessageListenerRegistration registration : registrations) {
          if (registration.getChannel().equals(channel)) {
            builder.add(registration);
          }
        }
        
        return builder.build();
      }
      return ImmutableSet.of();
    }
  }
  
  public boolean isRegistrationValid(PluginMessageListenerRegistration registration)
  {
    if (registration == null) {
      throw new IllegalArgumentException("Registration cannot be null");
    }
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(registration.getPlugin());
      
      if (registrations != null) {
        return registrations.contains(registration);
      }
      
      return false;
    }
  }
  
  public boolean isIncomingChannelRegistered(Plugin plugin, String channel) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    
    synchronized (this.incomingLock) {
      Set<PluginMessageListenerRegistration> registrations = (Set)this.incomingByPlugin.get(plugin);
      
      if (registrations != null) {
        for (PluginMessageListenerRegistration registration : registrations) {
          if (registration.getChannel().equals(channel)) {
            return true;
          }
        }
      }
      
      return false;
    }
  }
  
  public boolean isOutgoingChannelRegistered(Plugin plugin, String channel) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    validateChannel(channel);
    
    synchronized (this.outgoingLock) {
      Set<String> channels = (Set)this.outgoingByPlugin.get(plugin);
      
      if (channels != null) {
        return channels.contains(channel);
      }
      
      return false;
    }
  }
  
  public void dispatchIncomingMessage(Player source, String channel, byte[] message) {
    if (source == null) {
      throw new IllegalArgumentException("Player source cannot be null");
    }
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    validateChannel(channel);
    
    Set<PluginMessageListenerRegistration> registrations = getIncomingChannelRegistrations(channel);
    
    for (PluginMessageListenerRegistration registration : registrations) {
      registration.getListener().onPluginMessageReceived(channel, source, message);
    }
  }
  




  public static void validateChannel(String channel)
  {
    if (channel == null) {
      throw new IllegalArgumentException("Channel cannot be null");
    }
    if (channel.length() > 16) {
      throw new ChannelNameTooLongException(channel);
    }
  }
  












  public static void validatePluginMessage(Messenger messenger, Plugin source, String channel, byte[] message)
  {
    if (messenger == null) {
      throw new IllegalArgumentException("Messenger cannot be null");
    }
    if (source == null) {
      throw new IllegalArgumentException("Plugin source cannot be null");
    }
    if (!source.isEnabled()) {
      throw new IllegalArgumentException("Plugin must be enabled to send messages");
    }
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    if (!messenger.isOutgoingChannelRegistered(source, channel)) {
      throw new ChannelNotRegisteredException(channel);
    }
    if (message.length > 32766) {
      throw new MessageTooLargeException(message);
    }
    validateChannel(channel);
  }
}
