package forge.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketOpenGUI extends ForgePacket
{
  public int WindowID;
  public int ModID;
  public int GuiID;
  public int X;
  public int Y;
  public int Z;
  
  public PacketOpenGUI() {}
  
  public PacketOpenGUI(int window, int mod, int id, int x, int y, int z)
  {
    this.WindowID = window;
    this.ModID = mod;
    this.GuiID = id;
    this.X = x;
    this.Y = y;
    this.Z = z;
  }
  
  public void writeData(DataOutputStream data)
    throws IOException
  {
    data.writeInt(this.WindowID);
    data.writeInt(this.ModID);
    data.writeInt(this.GuiID);
    data.writeInt(this.X);
    data.writeInt(this.Y);
    data.writeInt(this.Z);
  }
  
  public void readData(DataInputStream data)
    throws IOException
  {
    this.WindowID = data.readInt();
    this.ModID = data.readInt();
    this.GuiID = data.readInt();
    this.X = data.readInt();
    this.Y = data.readInt();
    this.Z = data.readInt();
  }
  

  public int getID()
  {
    return 5;
  }
  

  public String toString(boolean full)
  {
    if (full)
    {
      StringBuilder ret = new StringBuilder();
      ret.append(toString() + '\n');
      ret.append("    Window: " + this.WindowID + '\n');
      ret.append("    Mod:    " + this.ModID + '\n');
      ret.append("    Gui:    " + this.GuiID + '\n');
      ret.append("    X:      " + this.X + '\n');
      ret.append("    Y:      " + this.Y + '\n');
      ret.append("    Z:      " + this.Z + '\n');
      return ret.toString();
    }
    

    return toString();
  }
}
