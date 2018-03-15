package net.minecraft.server;
class NetworkMasterThread extends Thread
{
	// BTCS start
	final NetworkManager a;
  NetworkMasterThread(NetworkManager nm) {
	  this.a = nm;
  }
  // BTCS end

  public void run()
  {
    try
    {
      Thread.sleep(5000L);
      if (NetworkManager.g(this.a).isAlive()) {
        try {
          NetworkManager.g(this.a).stop();
        }
        catch (Throwable localThrowable1) {}
      }
      if (NetworkManager.h(this.a).isAlive()) {
        try {
          NetworkManager.h(this.a).stop();
        }
        catch (Throwable localThrowable2) {}
      }
    } catch (InterruptedException localInterruptedException) {
      localInterruptedException.printStackTrace();
    }
  }
}
