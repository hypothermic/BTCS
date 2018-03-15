package net.minecraft.server;

class NetworkReaderThread
  extends Thread
{
	// BTCS start
	final NetworkManager a;
  NetworkReaderThread(NetworkManager nm, String s) { 
	  super(s);
	  this.a = nm;
  } // BTCS end
  
  public void run() { synchronized (NetworkManager.a) {
      NetworkManager.b += 1;
    }
    try {
      while ((NetworkManager.a(this.a)) && (!NetworkManager.b(this.a))) {
        while (NetworkManager.c(this.a)) {}
        
        try
        {
          sleep(2L);
        }
        catch (InterruptedException x) {} // BTCS: added formal parameter, rm '???'
      }
    } finally {
      synchronized (NetworkManager.a) {
        NetworkManager.b -= 1;
      }
    }
  }
}
