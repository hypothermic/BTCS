package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {
  public double data;
  
  public NBTTagDouble(String paramString) {
    super(paramString);
  }
  
  public NBTTagDouble(String paramString, double paramDouble) {
    super(paramString);
    this.data = paramDouble;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try { // BTCS
		paramDataOutput.writeDouble(this.data);
	} catch (IOException e) {
	}
  }
  
  void load(DataInput paramDataInput) {
    try { // BTCS
		this.data = paramDataInput.readDouble();
	} catch (IOException e) {
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X5: wrong load method got used, report this error to devs.");
  }
  
  public byte getTypeId() {
    return 6;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagDouble(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagDouble localNBTTagDouble = (NBTTagDouble)paramObject;
      return this.data == localNBTTagDouble.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.data);
    return super.hashCode() ^ (int)(l ^ l >>> 32);
  }
}
