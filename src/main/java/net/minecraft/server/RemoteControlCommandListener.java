package net.minecraft.server;


public class RemoteControlCommandListener
  implements ICommandListener
{
  public static final RemoteControlCommandListener instance = new RemoteControlCommandListener();
  
  private StringBuffer b = new StringBuffer();
  
  public void a() {
    this.b.setLength(0);
  }
  
  public String b() {
    return this.b.toString();
  }
  
  public void sendMessage(String paramString) {
    this.b.append(paramString);
  }
  



  public String getName()
  {
    return "Rcon";
  }
}
