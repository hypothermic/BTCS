package ee.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.NetworkManager;

public class EEPacket
{
  protected PacketTypeHandler packetType;
  protected boolean isChunkDataPacket;
  
  public EEPacket(PacketTypeHandler var1, boolean var2)
  {
    this.packetType = var1;
    this.isChunkDataPacket = var2;
  }
  
  public byte[] populate()
  {
    ByteArrayOutputStream var1 = new ByteArrayOutputStream();
    DataOutputStream var2 = new DataOutputStream(var1);
    
    try
    {
      var2.writeByte(this.packetType.ordinal());
      writeData(var2);
    }
    catch (IOException var4)
    {
      var4.printStackTrace();
    }
    
    return var1.toByteArray();
  }
  
  public void readPopulate(DataInputStream var1)
  {
    try
    {
      readData(var1);
    }
    catch (IOException var3)
    {
      var3.printStackTrace();
    }
  }
  
  public void execute(NetworkManager var1) {}
  
  public void writeData(DataOutputStream var1)
    throws IOException
  {}
  
  public void readData(DataInputStream var1)
    throws IOException
  {}
  
  public void setKey(int var1) {}
  
  public void setCoords(int var1, int var2, int var3) {}
  
  public void setOrientation(byte var1) {}
  
  public void setPlayerName(String var1) {}
  
  public void setItem(int var1) {}
  
  public void setState(boolean var1) {}
}
