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
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readInt();
	} catch (IOException e) {
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X8: wrong load method got used, report this error to devs.");
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
