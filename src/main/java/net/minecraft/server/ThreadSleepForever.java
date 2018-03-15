package net.minecraft.server;



































































public class ThreadSleepForever
  extends Thread
{
  public ThreadSleepForever(MinecraftServer paramMinecraftServer)
  {
    setDaemon(true);
    start();
  }
  
  public void run() {
    try {
      for (;;) {
        Thread.sleep(2147483647L);
      }
    }
    catch (InterruptedException localInterruptedException) {}
  }
}
