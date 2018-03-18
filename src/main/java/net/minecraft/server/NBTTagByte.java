package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {
  public byte data;
  
  public NBTTagByte(String paramString) {
    super(paramString);
  }
  
  public NBTTagByte(String paramString, byte paramByte) {
    super(paramString);
    this.data = paramByte;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeByte(this.data);
	} catch (IOException e) {
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X1: wrong load method got used, report this error to devs.");
  }
  
  void load(DataInput paramDataInput) {
	try {
		this.data = paramDataInput.readByte();
	} catch (IOException e) {
	}
  }
  
  public byte getTypeId() {
    return 1;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagByte(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagByte localNBTTagByte = (NBTTagByte)paramObject;
      return this.data == localNBTTagByte.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data;
  }
}
