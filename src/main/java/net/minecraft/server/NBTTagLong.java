package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {
  public long data;
  
  public NBTTagLong(String paramString) {
    super(paramString);
  }
  
  public NBTTagLong(String paramString, long paramLong) {
    super(paramString);
    this.data = paramLong;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeLong(this.data);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X27 happened in NBT");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readLong();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X28 happened in NBT");
		e.printStackTrace();
	}
  }
  
  public byte getTypeId() {
    return 4;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagLong(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagLong localNBTTagLong = (NBTTagLong)paramObject;
      return this.data == localNBTTagLong.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
  }
}
