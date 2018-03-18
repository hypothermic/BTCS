package net.minecraft.server;

import java.io.DataInput;

public class NBTTagEnd extends NBTBase
{
  public NBTTagEnd() {
    super(null);
  }
  

  void load(DataInput paramDataInput) {}
  
  void write(java.io.DataOutput paramDataOutput) {}
  
  public byte getTypeId()
  {
    return 0;
  }
  
  public String toString() {
    return "END";
  }
  
  public NBTBase clone()
  {
    return new NBTTagEnd();
  }
  
  public boolean equals(Object paramObject)
  {
    return super.equals(paramObject);
  }
}
