package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {
  public float data;
  
  public NBTTagFloat(String paramString) {
    super(paramString);
  }
  
  public NBTTagFloat(String paramString, float paramFloat) {
    super(paramString);
    this.data = paramFloat;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeFloat(this.data);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X17 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readFloat();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X18 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  public byte getTypeId() {
    return 5;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagFloat(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagFloat localNBTTagFloat = (NBTTagFloat)paramObject;
      return this.data == localNBTTagFloat.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ Float.floatToIntBits(this.data);
  }
}
