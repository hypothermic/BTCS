package ee.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.EEProxy;
import net.minecraft.server.NetworkManager;

public class PedestalPacket extends EEPacket
{
  int x;
  int y;
  int z;
  public int itemId;
  public boolean activated;
  
  public PedestalPacket()
  {
    super(PacketTypeHandler.PEDESTAL, true);
  }
  
  public void setCoords(int var1, int var2, int var3)
  {
    this.x = var1;
    this.y = var2;
    this.z = var3;
  }
  
  public void setItem(int var1)
  {
    this.itemId = var1;
  }
  
  public void setState(boolean var1)
  {
    this.activated = var1;
  }
  
  public void writeData(DataOutputStream var1) throws IOException
  {
    var1.writeInt(this.x);
    var1.writeInt(this.y);
    var1.writeInt(this.z);
    var1.writeInt(this.itemId);
    var1.writeBoolean(this.activated);
  }
  
  public void readData(DataInputStream var1) throws IOException
  {
    this.x = var1.readInt();
    this.y = var1.readInt();
    this.z = var1.readInt();
    this.itemId = var1.readInt();
    this.activated = var1.readBoolean();
  }
  
  public void execute(NetworkManager var1)
  {
    EEProxy.handlePedestalPacket(this.x, this.y, this.z, this.itemId, this.activated);
  }
}
