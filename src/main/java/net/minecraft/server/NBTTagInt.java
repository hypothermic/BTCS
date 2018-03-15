package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {
  public int data;
  
  public NBTTagInt(String paramString) {
    super(paramString);
  }
  
  public NBTTagInt(String paramString, int paramInt) {
    super(paramString);
    this.data = paramInt;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeInt(this.data);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X19 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readInt();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X20 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  public byte getTypeId() {
    return 3;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagInt(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagInt localNBTTagInt = (NBTTagInt)paramObject;
      return this.data == localNBTTagInt.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data;
  }
}
