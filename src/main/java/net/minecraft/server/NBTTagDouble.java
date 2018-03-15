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
		System.out.println("BTCS: Exception X15 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try { // BTCS
		this.data = paramDataInput.readDouble();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X16 happened in NBTBase");
		e.printStackTrace();
	}
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
