package forge.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map; // BTCS: see ln 39
import java.util.Map.Entry;

public class PacketModList
  extends ForgePacket
{
  private boolean isServer = false;
  public String[] Mods;
  public Hashtable<Integer, String> ModIDs = new Hashtable();
  public int Length = -1;
  public boolean has4096 = false;
  
  public PacketModList(boolean server)
  {
    this.isServer = server;
  }
  
  public void writeData(DataOutputStream data)
    throws IOException
  {
    if (!this.isServer)
    {
      data.writeInt(this.Mods.length);
      for (String mod : this.Mods)
      {
        data.writeUTF(mod);
      }
    }
    else
    {
      data.writeInt(this.ModIDs.size());
      // BTCS: imported java.util.Map to resolve error in ln below.
      for (Map.Entry<Integer, String> entry : this.ModIDs.entrySet())
      {
        data.writeInt(((Integer)entry.getKey()).intValue());
        data.writeUTF((String)entry.getValue());
      }
    }
    data.writeBoolean(true);
  }
  
  public void readData(DataInputStream data)
    throws IOException
  {
    if (this.isServer)
    {
      this.Length = data.readInt();
      if (this.Length >= 0)
      {
        this.Mods = new String[this.Length];
        for (int x = 0; x < this.Length; x++)
        {
          this.Mods[x] = data.readUTF();
        }
      }
    }
    else
    {
      this.Length = data.readInt();
      for (int x = 0; x < this.Length; x++)
      {
        this.ModIDs.put(Integer.valueOf(data.readInt()), data.readUTF());
      }
    }
    try {
      this.has4096 = data.readBoolean();
    }
    catch (EOFException e)
    {
      this.has4096 = false;
    }
  }
  

  public int getID()
  {
    return 2;
  }
  

  public String toString(boolean full)
  {
    if (full)
    {
      StringBuilder ret = new StringBuilder();
      ret.append(toString()).append('\n');
      if (this.Mods != null)
      {
        for (String mod : this.Mods)
        {
          ret.append("    " + mod + '\n');
        }
        
      } else if (this.ModIDs.size() != 0)
      {
        for (Map.Entry<Integer, String> mod : this.ModIDs.entrySet())
        {
          ret.append(String.format("    %03d ", new Object[] { mod.getKey() }) + (String)mod.getValue() + '\n');
        }
      }
      return ret.toString();
    }
    

    return toString();
  }
}
