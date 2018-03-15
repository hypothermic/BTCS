package forge;

import java.util.Hashtable;

public class MessageManager
{
  private Hashtable<net.minecraft.server.NetworkManager, ConnectionInstance> connections;
  private static MessageManager instance;
  
  public MessageManager() {
    this.connections = new Hashtable();
  }
  
  public static MessageManager getInstance()
  {
    if (instance == null)
    {
      instance = new MessageManager();
    }
    return instance;
  }
  
  public class ConnectionInstance
  {
    private net.minecraft.server.NetworkManager network;
    private Hashtable<String, java.util.ArrayList<IPacketHandler>> channelToHandlers = new Hashtable();
    private Hashtable<IPacketHandler, java.util.ArrayList<String>> handlerToChannels = new Hashtable();
    private java.util.HashSet<String> activeChannels = new java.util.HashSet();
    
    public ConnectionInstance(net.minecraft.server.NetworkManager mgr)
    {
      this.network = mgr;
    }
    




    public net.minecraft.server.NetworkManager getNetwork()
    {
      return this.network;
    }
    






    public String[] unregisterAll()
    {
      String[] ret = getRegisteredChannels();
      this.channelToHandlers.clear();
      this.handlerToChannels.clear();
      return ret;
    }
    









    public boolean registerChannel(IPacketHandler handler, String channel)
    {
      java.util.ArrayList<IPacketHandler> handlers = (java.util.ArrayList)this.channelToHandlers.get(channel);
      java.util.ArrayList<String> channels = (java.util.ArrayList)this.handlerToChannels.get(handler);
      boolean ret = false;
      
      if (handlers == null)
      {
        ret = true;
        handlers = new java.util.ArrayList();
        this.channelToHandlers.put(channel, handlers);
      }
      
      if (channels == null)
      {
        channels = new java.util.ArrayList();
        this.handlerToChannels.put(handler, channels);
      }
      
      if (!channels.contains(channel))
      {
        channels.add(channel);
      }
      if (!handlers.contains(handler))
      {
        handlers.add(handler);
      }
      return ret;
    }
    









    public boolean unregisterChannel(IPacketHandler handler, String channel)
    {
      boolean ret = false;
      java.util.ArrayList<IPacketHandler> handlers = (java.util.ArrayList)this.channelToHandlers.get(channel);
      java.util.ArrayList<String> channels = (java.util.ArrayList)this.handlerToChannels.get(handler);
      
      if ((handlers != null) && (handlers.contains(handler)))
      {
        handlers.remove(handler);
        if (handlers.size() == 0)
        {
          ret = true;
          this.channelToHandlers.remove(channel);
        }
      }
      
      if ((channels != null) && (channels.contains(channel)))
      {
        channels.remove(channel);
        if (handlers.size() == 0)
        {
          this.handlerToChannels.remove(handler);
        }
      }
      
      return ret;
    }
    








    public String[] unregisterHandler(IPacketHandler handler)
    {
      java.util.ArrayList<String> tmp = (java.util.ArrayList)this.handlerToChannels.get(handler);
      if (tmp != null)
      {
        String[] channels = (String[])tmp.toArray(new String[0]);
        tmp = new java.util.ArrayList();
        
        for (String channel : channels)
        {
          if (unregisterChannel(handler, channel))
          {
            tmp.add(channel);
          }
        }
        return (String[])tmp.toArray(new String[0]);
      }
      return new String[0];
    }
    





    public String[] getRegisteredChannels()
    {
      int x = 0;
      String[] ret = new String[this.channelToHandlers.size()];
      
      for (String value : this.channelToHandlers.keySet())
      {
        ret[(x++)] = value;
      }
      return ret;
    }
    






    public IPacketHandler[] getChannelHandlers(String channel)
    {
      java.util.ArrayList<IPacketHandler> handlers = (java.util.ArrayList)this.channelToHandlers.get(channel);
      if (handlers != null)
      {
        return (IPacketHandler[])handlers.toArray(new IPacketHandler[0]);
      }
      return new IPacketHandler[0];
    }
    






    public void addActiveChannel(String channel)
    {
      if (!this.activeChannels.contains(channel))
      {
        this.activeChannels.add(channel);
      }
    }
    






    public void removeActiveChannel(String channel)
    {
      if (this.activeChannels.contains(channel))
      {
        this.activeChannels.remove(channel);
      }
    }
    






    public boolean isActiveChannel(String channel)
    {
      return this.activeChannels.contains(channel);
    }
  }
  






  public ConnectionInstance getConnection(net.minecraft.server.NetworkManager manager)
  {
    ConnectionInstance ret = (ConnectionInstance)this.connections.get(manager);
    if (ret == null)
    {
      ret = new ConnectionInstance(manager);
      this.connections.put(manager, ret);
    }
    return ret;
  }
  








  public String[] removeConnection(net.minecraft.server.NetworkManager manager)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      String[] ret = con.unregisterAll();
      this.connections.remove(manager);
      return ret;
    }
    return new String[0];
  }
  










  public boolean registerChannel(net.minecraft.server.NetworkManager manager, IPacketHandler handler, String channel)
  {
    ConnectionInstance con = getConnection(manager);
    return con.registerChannel(handler, channel);
  }
  










  public boolean unregisterChannel(net.minecraft.server.NetworkManager manager, IPacketHandler handler, String channel)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      return con.unregisterChannel(handler, channel);
    }
    return false;
  }
  









  public String[] unregisterHandler(net.minecraft.server.NetworkManager manager, IPacketHandler handler)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      return con.unregisterHandler(handler);
    }
    return new String[0];
  }
  






  public String[] getRegisteredChannels(net.minecraft.server.NetworkManager manager)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      return con.getRegisteredChannels();
    }
    return new String[0];
  }
  







  public IPacketHandler[] getChannelHandlers(net.minecraft.server.NetworkManager manager, String channel)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      return con.getChannelHandlers(channel);
    }
    return new IPacketHandler[0];
  }
  







  public void addActiveChannel(net.minecraft.server.NetworkManager manager, String channel)
  {
    ConnectionInstance con = getConnection(manager);
    con.addActiveChannel(channel);
  }
  







  public void removeActiveChannel(net.minecraft.server.NetworkManager manager, String channel)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      con.removeActiveChannel(channel);
    }
  }
  







  public boolean isActiveChannel(net.minecraft.server.NetworkManager manager, String channel)
  {
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      return con.isActiveChannel(channel);
    }
    return false;
  }
  
  public void dispatchIncomingMessage(net.minecraft.server.NetworkManager manager, String channel, byte[] data)
  {
    if (data == null)
    {
      data = new byte[0];
    }
    
    if (channel.equals("Forge"))
    {
      if (ForgeHooks.getPacketHandler() != null)
      {
        byte[] tmpData = new byte[data.length];
        System.arraycopy(data, 0, tmpData, 0, data.length);
        ForgeHooks.getPacketHandler().onPacketData(manager, channel, tmpData);
      }
    }
    
    if (this.connections.containsKey(manager))
    {
      ConnectionInstance con = getConnection(manager);
      IPacketHandler[] handlers = con.getChannelHandlers(channel);
      byte[] tmpData = new byte[data.length];
      for (IPacketHandler handler : handlers)
      {
        System.arraycopy(data, 0, tmpData, 0, data.length);
        handler.onPacketData(manager, channel, tmpData);
      }
    }
  }
}
