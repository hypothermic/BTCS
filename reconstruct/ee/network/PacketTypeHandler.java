package ee.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet250CustomPayload;

public enum PacketTypeHandler
{
  KEY(KeyPressedPacket.class), 
  TILE(TileEntityPacket.class), 
  PEDESTAL(PedestalPacket.class);
  
  private Class clazz;
  
  private PacketTypeHandler(Class var3) {
    this.clazz = var3;
  }
  
  public static EEPacket buildPacket(byte[] var0)
  {
    ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
    int var2 = var1.read();
    DataInputStream var3 = new DataInputStream(var1);
    EEPacket var4 = null;
    
    try
    {
      var4 = (EEPacket)values()[var2].clazz.newInstance();
    }
    catch (Exception var6)
    {
      var6.printStackTrace();
    }
    
    var4.readPopulate(var3);
    return var4;
  }
  
  public static EEPacket buildPacket(PacketTypeHandler var0)
  {
    EEPacket var1 = null;
    
    try
    {
      var1 = (EEPacket)values()[var0.ordinal()].clazz.newInstance();
    }
    catch (Exception var3)
    {
      var3.printStackTrace();
    }
    
    return var1;
  }
  
  public static Packet populatePacket(EEPacket var0)
  {
    byte[] var1 = var0.populate();
    Packet250CustomPayload var2 = new Packet250CustomPayload();
    var2.tag = "EE2";
    var2.data = var1;
    var2.length = var1.length;
    var2.lowPriority = var0.isChunkDataPacket;
    return var2;
  }
}
