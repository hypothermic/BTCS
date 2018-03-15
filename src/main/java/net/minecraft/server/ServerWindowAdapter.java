package net.minecraft.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerWindowAdapter extends WindowAdapter
{
	// BTCS start
	final MinecraftServer a;

    ServerWindowAdapter(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
    }
// BTCS end
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    this.a.safeShutdown();
    while (!this.a.isStopped) {
      try {
        Thread.sleep(100L);
      } catch (InterruptedException localInterruptedException) {
        localInterruptedException.printStackTrace();
      }
    }
    System.exit(0);
  }
}
