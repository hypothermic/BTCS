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
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readFloat();
	} catch (IOException e) {
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X7: wrong load method got used, report this error to devs.");
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
