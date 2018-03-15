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
		System.out.println("BTCS: Exception X11 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readByte();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X12 happened in NBTBase");
		e.printStackTrace();
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
