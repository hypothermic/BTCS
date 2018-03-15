package net.minecraft.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Iterator;

public class RemoteControlListener extends RemoteConnectionThread
{
  private int g;
  private int h;
  private String i;
  private ServerSocket j = null;
  private String k;
  private HashMap l;
  
  public RemoteControlListener(IMinecraftServer paramIMinecraftServer) {
    super(paramIMinecraftServer);
    this.g = paramIMinecraftServer.getProperty("rcon.port", 0);
    this.k = paramIMinecraftServer.a("rcon.password", "");
    this.i = paramIMinecraftServer.getMotd();
    this.h = paramIMinecraftServer.getPort();
    if (0 == this.g)
    {
      this.g = (this.h + 10);
      info("Setting default rcon port to " + this.g);
      paramIMinecraftServer.a("rcon.port", Integer.valueOf(this.g));
      if (0 == this.k.length()) {
        paramIMinecraftServer.a("rcon.password", "");
      }
      paramIMinecraftServer.c();
    }
    
    if (0 == this.i.length()) {
      this.i = "0.0.0.0";
    }
    
    e();
    this.j = null;
  }
  
  private void e() {
    this.l = new HashMap();
  }
  
  private void f()
  {
    Iterator localIterator = this.l.entrySet().iterator();
    while (localIterator.hasNext()) {
      java.util.Map.Entry localEntry = (java.util.Map.Entry)localIterator.next();
      if (!((RemoteControlSession)localEntry.getValue()).b()) {
        localIterator.remove();
      }
    }
  }
  
  public void run() {
    info("RCON running on " + this.i + ":" + this.g);
    try {
      while (this.running) {
        try
        {
          java.net.Socket localSocket = this.j.accept();
          localSocket.setSoTimeout(500);
          RemoteControlSession localRemoteControlSession = new RemoteControlSession(this.server, localSocket);
          localRemoteControlSession.a();
          this.l.put(localSocket.getRemoteSocketAddress(), localRemoteControlSession);
          

          f();
        }
        catch (java.net.SocketTimeoutException localSocketTimeoutException) {
          f();
        } catch (IOException localIOException) {
          if (this.running) {
            info("IO: " + localIOException.getMessage());
          }
        }
      }
    } finally {
      a(this.j);
    }
  }
  
  public void a() {
    if (0 == this.k.length()) {
      warning("No rcon password set in '" + this.server.getPropertiesFile() + "', rcon disabled!");
      return;
    }
    
    if ((0 >= this.g) || (65535 < this.g)) {
      warning("Invalid rcon port " + this.g + " found in '" + this.server.getPropertiesFile() + "', rcon disabled!");
      return;
    }
    
    if (this.running) {
      return;
    }
    try
    {
      this.j = new ServerSocket(this.g, 0, java.net.InetAddress.getByName(this.i));
      this.j.setSoTimeout(500);
      super.a();
    } catch (IOException localIOException) {
      warning("Unable to initialise rcon on " + this.i + ":" + this.g + " : " + localIOException.getMessage());
    }
  }
}
