package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet202Abilities
  extends Packet
{
  public boolean a = false;
  public boolean b = false;
  public boolean c = false;
  public boolean d = false;
  
  public Packet202Abilities() {}
  
  public Packet202Abilities(PlayerAbilities paramPlayerAbilities)
  {
    this.a = paramPlayerAbilities.isInvulnerable;
    this.b = paramPlayerAbilities.isFlying;
    this.c = paramPlayerAbilities.canFly;
    this.d = paramPlayerAbilities.canInstantlyBuild;
  }
  
  public void a(DataInputStream paramDataInputStream)
  {
	  try {
    this.a = paramDataInputStream.readBoolean();
    this.b = paramDataInputStream.readBoolean();
    this.c = paramDataInputStream.readBoolean();
    this.d = paramDataInputStream.readBoolean();
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X98 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void a(DataOutputStream paramDataOutputStream)
  {
	  try {
    paramDataOutputStream.writeBoolean(this.a);
    paramDataOutputStream.writeBoolean(this.b);
    paramDataOutputStream.writeBoolean(this.c);
    paramDataOutputStream.writeBoolean(this.d);
	  } catch (IOException x) {
		  System.out.println("BTCS: Exception X99 happened in Packet");
		  x.printStackTrace();
	  }
  }
  
  public void handle(NetHandler paramNetHandler)
  {
    paramNetHandler.a(this);
  }
  
  public int a()
  {
    return 1;
  }
}
