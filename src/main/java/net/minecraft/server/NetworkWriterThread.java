package net.minecraft.server;

import java.io.IOException;

class NetworkWriterThread extends Thread
{
  final NetworkManager a;
  
  NetworkWriterThread(NetworkManager networkmanager, String s) {
    super(s);
    this.a = networkmanager;
  }
  
  public void run() {
    Object object = NetworkManager.a;
    
    synchronized (NetworkManager.a) {
      NetworkManager.c += 1;
    }
    for (;;)
    {
      boolean flag = false;
      try
      {
        flag = true;
        if (!NetworkManager.a(this.a)) {
          flag = false;
          
          if (!flag) break;
          Object object1 = NetworkManager.a;
          
          synchronized (NetworkManager.a) {
            NetworkManager.c -= 1;
          }
          break;
        }
        while (NetworkManager.d(this.a)) {}
        

        try
        {
          if (NetworkManager.e(this.a) != null) {
            NetworkManager.e(this.a).flush();
          }
        } catch (IOException ioexception) {
          if (!NetworkManager.f(this.a)) {
            NetworkManager.a(this.a, ioexception);
          }
        }
        

        try
        {
          sleep(2L);
        }
        catch (InterruptedException interruptedexception) {}
      } finally {
        Object object1;
        if (flag) {
          object1 = NetworkManager.a; // BTCS: rm decl 'object1'
          
          synchronized (NetworkManager.a) {
            NetworkManager.c -= 1;
          }
        }
      }
    }
    
    object = NetworkManager.a;
    synchronized (NetworkManager.a) {
      NetworkManager.c -= 1;
    }
  }
}
