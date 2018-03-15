package net.minecraft.server;

class NetworkMonitorThread
  extends Thread
{
	// BTCS start
	final NetworkManager a;
  NetworkMonitorThread(NetworkManager nm) {
	  this.a = nm;
  }
  // BTCS end
 
  public void run()
  {
    try
    {
      Thread.sleep(2000L);
      if (NetworkManager.a(this.a)) {
        NetworkManager.h(this.a).interrupt();
        this.a.a("disconnect.closed", new Object[0]);
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }
}
