package ee.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.EEProxy;
import net.minecraft.server.NetworkManager;

public class TileEntityPacket extends EEPacket
{
  public int x;
  public int y;
  public int z;
  public byte direction;
  String player;
  
  public TileEntityPacket()
  {
    super(PacketTypeHandler.TILE, true);
  }
  
  public void setCoords(int var1, int var2, int var3)
  {
    this.x = var1;
    this.y = var2;
    this.z = var3;
  }
  
  public void setOrientation(byte var1)
  {
    this.direction = var1;
  }
  
  public void setPlayerName(String var1)
  {
    this.player = var1;
  }
  
  public void writeData(DataOutputStream var1) throws IOException
  {
    var1.writeInt(this.x);
    var1.writeInt(this.y);
    var1.writeInt(this.z);
    var1.writeByte(this.direction);
    if (this.player != null) {
      var1.writeUTF(this.player);
    } else {
      var1.writeUTF("");
    }
  }
  
  public void readData(DataInputStream var1) throws IOException
  {
    this.x = var1.readInt();
    this.y = var1.readInt();
    this.z = var1.readInt();
    this.direction = var1.readByte();
    this.player = var1.readUTF();
  }
  
  public void execute(NetworkManager var1)
  {
    EEProxy.handleTEPacket(this.x, this.y, this.z, this.direction, this.player);
  }
}
