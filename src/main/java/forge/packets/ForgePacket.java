package forge.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet250CustomPayload;





public abstract class ForgePacket
{
  public static final int FORGE_ID = 68066119;
  public static final int SPAWN = 1;
  public static final int MODLIST = 2;
  public static final int MOD_MISSING = 3;
  public static final int OPEN_GUI = 5;
  
  public Packet getPacket()
  {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    DataOutputStream data = new DataOutputStream(bytes);
    try
    {
      data.writeByte(getID());
      writeData(data);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    Packet250CustomPayload pkt = new Packet250CustomPayload();
    pkt.tag = "Forge";
    pkt.data = bytes.toByteArray();
    pkt.length = pkt.data.length;
    return pkt; }
  
  public abstract void writeData(DataOutputStream paramDataOutputStream) throws IOException;
  
  public abstract void readData(DataInputStream paramDataInputStream) throws IOException;
  
  public abstract int getID();
  
  public String toString(boolean full) { return toString(); }
  


  public String toString()
  {
    return getID() + " " + getClass().getSimpleName();
  }
}
