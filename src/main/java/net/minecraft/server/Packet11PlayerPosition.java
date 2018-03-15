package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet11PlayerPosition
  extends Packet10Flying
{
  public Packet11PlayerPosition()
  {
    this.hasPos = true;
  }

  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.x = paramDataInputStream.readDouble();
    this.y = paramDataInputStream.readDouble();
    this.stance = paramDataInputStream.readDouble();
    this.z = paramDataInputStream.readDouble();
    super.a(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X63 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeDouble(this.x);
    paramDataOutputStream.writeDouble(this.y);
    paramDataOutputStream.writeDouble(this.stance);
    paramDataOutputStream.writeDouble(this.z);
    super.a(paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X64 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 33;
  }
}
