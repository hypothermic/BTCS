package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
  public String data;
  
  public NBTTagString(String paramString) {
    super(paramString);
  }
  
  public NBTTagString(String paramString1, String paramString2) {
    super(paramString1);
    this.data = paramString2;
    if (paramString2 == null) throw new IllegalArgumentException("Empty string not allowed");
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeUTF(this.data);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X30 happened in NBT");
		e.printStackTrace();
	}
  }
  
  void load(DataInput paramDataInput) {
    try {
		this.data = paramDataInput.readUTF();
	} catch (IOException e) {
		System.out.println("BTCS: Exception X30 happened in NBT");
		e.printStackTrace();
	}
  }
  
  public byte getTypeId() {
    return 8;
  }
  
  public String toString() {
    return "" + this.data;
  }
  
  public NBTBase clone()
  {
    return new NBTTagString(getName(), this.data);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagString localNBTTagString = (NBTTagString)paramObject;
      return ((this.data == null) && (localNBTTagString.data == null)) || ((this.data != null) && (this.data.equals(localNBTTagString.data)));
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.data.hashCode();
  }
}
