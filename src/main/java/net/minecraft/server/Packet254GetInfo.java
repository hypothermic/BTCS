package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class Packet254GetInfo
  extends Packet
{
  public void a(DataInputStream paramDataInputStream) {}
  
  public void a(DataOutputStream paramDataOutputStream) {}
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 0;
  }
}
