package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {
  public short data;
  
  public NBTTagShort(String paramString) {
    super(paramString);
  }
  
  public NBTTagShort(String paramString, short paramShort) {
    super(paramString);
    this.data = paramShort;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeShort(this.data);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X29 happened in NBT");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readShort();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X30 happened in NBT");
		e.printStackTrace();
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X11: wrong load method got used, report this error to devs.");
  }
  
  public byte getTypeId() {
    return 2;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagShort(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagShort localNBTTagShort = (NBTTagShort)paramObject;
      return this.data == localNBTTagShort.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data;
  }
}
