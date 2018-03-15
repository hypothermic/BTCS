package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat
  extends Packet
{
  public static int b = 119;
  

  public String message;
  


  public Packet3Chat() {}
  

  public Packet3Chat(String s)
  {
    this.message = s;
  }
  
  public void a(DataInputStream datainputstream) throws IOException {
    this.message = a(datainputstream, b);
  }
  
  public void a(DataOutputStream dataoutputstream) throws IOException {
    a(this.message, dataoutputstream);
  }
  
  public void handle(NetHandler nethandler) {
    nethandler.a(this);
  }
  
  public int a() {
    return 2 + this.message.length() * 2;
  }
}
