package forge.packets;

import forge.ISpawnHandler;
import forge.IThrowableEntity;
import forge.MinecraftForge;
import forge.NetworkMod;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.MathHelper;

public class PacketEntitySpawn extends ForgePacket
{
  public int modID;
  public int entityID;
  public int typeID;
  public int posX;
  public int posY;
  public int posZ;
  public byte yaw;
  public byte pitch;
  public byte yawHead;
  public int throwerID;
  public int speedX;
  public int speedY;
  public int speedZ;
  public Object metadata;
  private ISpawnHandler handler;
  
  public PacketEntitySpawn() {}
  
  public PacketEntitySpawn(Entity ent, NetworkMod mod, int type)
  {
    this.entityID = ent.id;
    
    this.posX = MathHelper.floor(ent.locX * 32.0D);
    this.posY = MathHelper.floor(ent.locY * 32.0D);
    this.posZ = MathHelper.floor(ent.locZ * 32.0D);
    
    this.typeID = type;
    this.modID = MinecraftForge.getModID(mod);
    
    this.yaw = ((byte)(int)(ent.yaw * 256.0F / 360.0F));
    this.pitch = ((byte)(int)(ent.pitch * 256.0F / 360.0F));
    this.yawHead = ((byte)(int)((ent instanceof EntityLiving) ? ((EntityLiving)ent).X * 256.0F / 360.0F : 0.0F));
    this.metadata = ent.getDataWatcher();
    
    if ((ent instanceof IThrowableEntity))
    {
      Entity owner = ((IThrowableEntity)ent).getThrower();
      this.throwerID = (owner == null ? ent.id : owner.id);
      double maxVel = 3.9D;
      double mX = ent.motX;
      double mY = ent.motY;
      double mZ = ent.motZ;
      if (mX < -maxVel) mX = -maxVel;
      if (mY < -maxVel) mY = -maxVel;
      if (mZ < -maxVel) mZ = -maxVel;
      if (mX > maxVel) mX = maxVel;
      if (mY > maxVel) mY = maxVel;
      if (mZ > maxVel) mZ = maxVel;
      this.speedX = ((int)(mX * 8000.0D));
      this.speedY = ((int)(mY * 8000.0D));
      this.speedZ = ((int)(mZ * 8000.0D));
    }
    if ((ent instanceof ISpawnHandler))
    {
      this.handler = ((ISpawnHandler)ent);
    }
  }
  
  public void writeData(DataOutputStream data) throws IOException {
    data.writeInt(this.modID);
    data.writeInt(this.entityID);
    data.writeByte(this.typeID);
    data.writeInt(this.posX);
    data.writeInt(this.posY);
    data.writeInt(this.posZ);
    data.writeByte(this.yaw);
    data.writeByte(this.pitch);
    data.writeByte(this.yawHead);
    ((DataWatcher)this.metadata).a(data);
    data.writeInt(this.throwerID);
    if (this.throwerID != 0)
    {
      data.writeShort(this.speedX);
      data.writeShort(this.speedY);
      data.writeShort(this.speedZ);
    }
    if (this.handler != null)
    {
      this.handler.writeSpawnData(data);
    }
  }
  
  public void readData(DataInputStream data) throws IOException
  {
    this.modID = data.readInt();
    this.entityID = data.readInt();
    this.typeID = (data.readByte() & 0xFF);
    this.posX = data.readInt();
    this.posY = data.readInt();
    this.posZ = data.readInt();
    this.yaw = data.readByte();
    this.pitch = data.readByte();
    this.yawHead = data.readByte();
    this.metadata = DataWatcher.a(data);
    this.throwerID = data.readInt();
    if (this.throwerID != 0)
    {
      this.speedX = data.readShort();
      this.speedY = data.readShort();
      this.speedZ = data.readShort();
    }
  }
  
  public int getID()
  {
    return 1;
  }
}
