package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;



public class Packet40EntityMetadata
  extends Packet
{
  public int a;
  private List b;
  
  public Packet40EntityMetadata() {}
  
  public Packet40EntityMetadata(int paramInt, DataWatcher paramDataWatcher)
  {
    this.a = paramInt;
    this.b = paramDataWatcher.b();
  }
  
  public void a(DataInputStream paramDataInputStream) {
	  try {
    this.a = paramDataInputStream.readInt();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X140 happened in Packet");
		  x.printStackTrace();
		  return;
	  }
    this.b = DataWatcher.a(paramDataInputStream);
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeInt(this.a);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X141 happened in Packet");
		  x.printStackTrace();
		  return;
	  }
    DataWatcher.a(this.b, paramDataOutputStream);
  }
  
  public void handle(NetHandler paramNetHandler) {
    paramNetHandler.a(this);
  }
  
  public int a() {
    return 5;
  }
}
