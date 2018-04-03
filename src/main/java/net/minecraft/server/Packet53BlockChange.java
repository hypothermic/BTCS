package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Packet53BlockChange extends Packet
{
  public int a;
  public int b;
  public int c;
  public int material;
  public int data;
  
  public Packet53BlockChange()
  {
    this.lowPriority = true;
  }
  
  public Packet53BlockChange(int i, int j, int k, World world) {
    this.lowPriority = true;
    this.a = i;
    this.b = j;
    this.c = k;
    this.material = world.getTypeId(i, j, k);
    this.data = world.getData(i, j, k);
  }
  
  public void a(DataInputStream datainputstream) throws java.io.IOException {
    this.a = datainputstream.readInt();
    this.b = datainputstream.read();
    this.c = datainputstream.readInt();
    this.material = datainputstream.readInt();
    this.data = datainputstream.read();
  }
  
  public void a(DataOutputStream dataoutputstream) throws java.io.IOException {
    dataoutputstream.writeInt(this.a);
    dataoutputstream.write(this.b);
    dataoutputstream.writeInt(this.c);
    dataoutputstream.writeInt(this.material);
    dataoutputstream.write(this.data);
  }
  
  public void handle(NetHandler nethandler) {
    nethandler.a(this);
  }
  
  public int a() {
    return 11; // BTCS: shouldn't this be 14?
  }
}
