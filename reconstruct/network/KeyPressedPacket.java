package ee.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.EEProxy;
import net.minecraft.server.NetworkManager;

public class KeyPressedPacket extends EEPacket
{
  public int key;
  
  public KeyPressedPacket()
  {
    super(PacketTypeHandler.KEY, false);
  }
  
  public void writeData(DataOutputStream var1) throws IOException
  {
    var1.writeInt(this.key);
  }
  
  public void readData(DataInputStream var1) throws IOException
  {
    this.key = var1.readInt();
  }
  
  public void setKey(int var1)
  {
    this.key = var1;
  }
  
  public void execute(NetworkManager var1)
  {
    EEProxy.handleControl(var1, this.key);
  }
}
