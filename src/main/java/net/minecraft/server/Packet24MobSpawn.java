package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.util.NumberConversions;

public class Packet24MobSpawn extends Packet
{
  public int a;
  public int b;
  public int c;
  public int d;
  public int e;
  public byte f;
  public byte g;
  public byte h;
  private DataWatcher i;
  private java.util.List q;
  
  public Packet24MobSpawn() {}
  
  public Packet24MobSpawn(EntityLiving entityliving)
  {
    this.a = entityliving.id;
    this.b = ((byte)EntityTypes.a(entityliving));
    
    this.c = entityliving.size.getXZCoord(entityliving.locX);
    this.d = NumberConversions.floor(entityliving.locY * 32.0D);
    this.e = entityliving.size.getXZCoord(entityliving.locZ);
    
    this.f = ((byte)(int)(entityliving.yaw * 256.0F / 360.0F));
    this.g = ((byte)(int)(entityliving.pitch * 256.0F / 360.0F));
    this.h = ((byte)(int)(entityliving.X * 256.0F / 360.0F));
    this.i = entityliving.getDataWatcher();
  }
  
  public void a(DataInputStream datainputstream) throws IOException {
    this.a = datainputstream.readInt();
    this.b = (datainputstream.readByte() & 0xFF);
    this.c = datainputstream.readInt();
    this.d = datainputstream.readInt();
    this.e = datainputstream.readInt();
    this.f = datainputstream.readByte();
    this.g = datainputstream.readByte();
    this.h = datainputstream.readByte();
    this.q = DataWatcher.a(datainputstream);
  }
  
  public void a(DataOutputStream dataoutputstream) throws IOException {
    dataoutputstream.writeInt(this.a);
    dataoutputstream.writeByte(this.b & 0xFF);
    dataoutputstream.writeInt(this.c);
    dataoutputstream.writeInt(this.d);
    dataoutputstream.writeInt(this.e);
    dataoutputstream.writeByte(this.f);
    dataoutputstream.writeByte(this.g);
    dataoutputstream.writeByte(this.h);
    this.i.a(dataoutputstream);
  }
  
  public void handle(NetHandler nethandler) {
    nethandler.a(this);
  }
  
  public int a() {
    return 20;
  }
}
