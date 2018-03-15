package net.minecraft.server;

class MojangStatisticsThread extends Thread
{
	
	final MojangStatisticsGenerator a; // BTCS
	
  MojangStatisticsThread(MojangStatisticsGenerator msg, String paramString) // BTCS added param 'msg'
  {
	  super(paramString);
	  this.a = msg; // BTCS
  }
  
  public void run() { HttpUtilities.a(MojangStatisticsGenerator.a(this.a), MojangStatisticsGenerator.b(this.a), true); }
}
