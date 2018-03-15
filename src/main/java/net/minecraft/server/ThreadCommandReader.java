package net.minecraft.server;

import java.io.IOException;
import java.util.logging.Logger;
import jline.console.ConsoleReader;
import org.bukkit.craftbukkit.Main;

public class ThreadCommandReader extends Thread
{
  final MinecraftServer server;
  
  public ThreadCommandReader(MinecraftServer minecraftserver)
  {
    this.server = minecraftserver;
  }
  
  public void run()
  {
    if (!Main.useConsole) {
      return;
    }
    

    ConsoleReader bufferedreader = this.server.reader;
    String s = null;
    
    try
    {
      while ((!this.server.isStopped) && (MinecraftServer.isRunning(this.server))) {
        if (Main.useJline) {
          s = bufferedreader.readLine(">", null);
        } else {
          s = bufferedreader.readLine();
        }
        if (s != null) {
          this.server.issueCommand(s, this.server);
        }
      }
    }
    catch (IOException ioexception)
    {
      Logger.getLogger(ThreadCommandReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ioexception);
    }
  }
}
