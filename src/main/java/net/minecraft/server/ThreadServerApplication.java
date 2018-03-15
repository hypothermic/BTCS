package net.minecraft.server;

public final class ThreadServerApplication extends Thread
{
	// BTCS start
	final MinecraftServer a;
  public ThreadServerApplication(String paramString, MinecraftServer mcs) { 
	  super(paramString); 
	  this.a = mcs;
  }
  // BTCS end
  
  public void run() {
      this.a.run(); 
  }
}
