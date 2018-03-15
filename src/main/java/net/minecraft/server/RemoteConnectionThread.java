package net.minecraft.server;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.List;

public abstract class RemoteConnectionThread implements Runnable
{
  protected boolean running = false;
  protected IMinecraftServer server;
  protected Thread thread;
  protected int d = 5;
  protected List e = new java.util.ArrayList();
  protected List f = new java.util.ArrayList();
  
  RemoteConnectionThread(IMinecraftServer paramIMinecraftServer) {
    this.server = paramIMinecraftServer;
    if (this.server.isDebugging()) {
      warning("Debugging is enabled, performance maybe reduced!");
    }
  }
  

  public synchronized void a()
  {
    this.thread = new Thread(this);
    this.thread.start();
    this.running = true;
  }
  
  public boolean b()
  {
    return this.running;
  }
  
  protected void debug(String paramString) {
    this.server.debug(paramString);
  }
  
  protected void info(String paramString) {
    this.server.sendMessage(paramString);
  }
  
  protected void warning(String paramString) {
    this.server.warning(paramString);
  }
  
  protected void error(String paramString) {
    this.server.severe(paramString);
  }
  
  protected int c() {
    return this.server.getPlayerCount();
  }
  
  protected void a(DatagramSocket paramDatagramSocket) {
    debug("registerSocket: " + paramDatagramSocket);
    this.e.add(paramDatagramSocket);
  }

  protected boolean a(DatagramSocket paramDatagramSocket, boolean paramBoolean)
  {
    debug("closeSocket: " + paramDatagramSocket);
    if (null == paramDatagramSocket) {
      return false;
    }
    
    boolean bool = false;
    if (!paramDatagramSocket.isClosed()) {
      paramDatagramSocket.close();
      bool = true;
    }
    
    if (paramBoolean) {
      this.e.remove(paramDatagramSocket);
    }
    
    return bool;
  }
  
  protected boolean a(ServerSocket paramServerSocket) {
    return a(paramServerSocket, true);
  }
  
  protected boolean a(ServerSocket paramServerSocket, boolean paramBoolean) {
    debug("closeSocket: " + paramServerSocket);
    if (null == paramServerSocket) {
      return false;
    }
    
    boolean bool = false;
    try {
      if (!paramServerSocket.isClosed()) {
        paramServerSocket.close();
        bool = true;
      }
    } catch (java.io.IOException localIOException) {
      warning("IO: " + localIOException.getMessage());
    }
    
    if (paramBoolean) {
      this.f.remove(paramServerSocket);
    }
    
    return bool;
  }
  
  protected void d() {
    a(false);
  }
  
  protected void a(boolean paramBoolean) {
    int i = 0;
    Iterator localIterator; // BTCS: moved outside for loop
    for (localIterator = this.e.iterator(); localIterator.hasNext();) { 
    DatagramSocket localObject = (DatagramSocket)localIterator.next(); // BTCS: added decl 'DatagramSocket'
      if (a((DatagramSocket)localObject, false))
        i++;
    }
    Object localObject;
    this.e.clear();
    
    for (localIterator = this.f.iterator(); localIterator.hasNext();) { localObject = (ServerSocket)localIterator.next();
      if (a((ServerSocket)localObject, false)) {
        i++;
      }
    }
    this.f.clear();
    
    if ((paramBoolean) && (0 < i)) {
      warning("Force closed " + i + " sockets");
    }
  }
}
