package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagIntArray extends NBTBase
{
  public int[] data;
  
  public NBTTagIntArray(String paramString) {
    super(paramString);
  }
  
  public NBTTagIntArray(String paramString, int[] paramArrayOfInt) {
    super(paramString);
    this.data = paramArrayOfInt;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try {
		paramDataOutput.writeInt(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			paramDataOutput.writeInt(this.data[i]);
		}
	} catch (IOException e) {
		System.out.println("BTCS: Exception X21 happened in NBT");
		e.printStackTrace(); 
	}
  }
  
  void load(DataInput paramDataInput) {
	try {
		int i = paramDataInput.readInt();
		this.data = new int[i];
	    for (int j = 0; j < i; j++) {
	      this.data[j] = paramDataInput.readInt();
	    }
	} catch (IOException e) {
		System.out.println("BTCS: Exception X22 happened in NBT");
		e.printStackTrace();
	}
  }
  
  public byte getTypeId() {
    return 11;
  }
  
  public String toString() {
    return "[" + this.data.length + " bytes]";
  }
  
  public NBTBase clone()
  {
    int[] arrayOfInt = new int[this.data.length];
    System.arraycopy(this.data, 0, arrayOfInt, 0, this.data.length);
    return new NBTTagIntArray(getName(), arrayOfInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagIntArray localNBTTagIntArray = (NBTTagIntArray)paramObject;
      return ((this.data == null) && (localNBTTagIntArray.data == null)) || ((this.data != null) && (this.data.equals(localNBTTagIntArray.data)));
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ java.util.Arrays.hashCode(this.data);
  }
}
