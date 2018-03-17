package net.minecraft.server;

import java.io.DataInput;

public class NBTTagEnd extends NBTBase
{
  public NBTTagEnd() {
    super(null);
  }
  

  void load(DataInput paramDataInput) {}
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X6: wrong load method got used, report this error to devs.");
  }
  
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
