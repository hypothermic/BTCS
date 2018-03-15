package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook
  extends Packet10Flying
{
  public Packet12PlayerLook()
  {
    this.hasLook = true;
  }

  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.yaw = paramDataInputStream.readFloat();
    this.pitch = paramDataInputStream.readFloat();
    super.a(paramDataInputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X65 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
	  try {
    paramDataOutputStream.writeFloat(this.yaw);
    paramDataOutputStream.writeFloat(this.pitch);
    super.a(paramDataOutputStream);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X66 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public int a() {
    return 9;
  }
}
