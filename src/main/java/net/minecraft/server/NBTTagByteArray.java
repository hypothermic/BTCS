package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;

public class NBTTagByteArray extends NBTBase {
  public byte[] data;
  
  public NBTTagByteArray(String paramString) {
    super(paramString);
  }
  
  public NBTTagByteArray(String paramString, byte[] paramArrayOfByte) {
    super(paramString);
    this.data = paramArrayOfByte;
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    try { // BTCS: try-catch
		paramDataOutput.writeInt(this.data.length);
		paramDataOutput.write(this.data);
	} catch (IOException e) {
	}
  }
  
  void load(DataInput paramDataInput) {
	try { // BTCS: try-catch
		int i = paramDataInput.readInt();
		this.data = new byte[i];
	    paramDataInput.readFully(this.data);
	} catch (IOException e) {
	}
  }
  
  void load(java.io.DataInput x1, int x2) { // BTCS: we don't need this in the byte tag, empty method.
	  System.out.println("BTCS DEBUG-X2: wrong load method got used, report this error to devs.");
  }
  
  public byte getTypeId() {
    return 7;
  }
  
  public String toString() {
    return "[" + this.data.length + " bytes]";
  }
  
  public NBTBase clone()
  {
    byte[] arrayOfByte = new byte[this.data.length];
    System.arraycopy(this.data, 0, arrayOfByte, 0, this.data.length);
    return new NBTTagByteArray(getName(), arrayOfByte);
  }
  
  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      return java.util.Arrays.equals(this.data, ((NBTTagByteArray)paramObject).data);
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ java.util.Arrays.hashCode(this.data);
  }
}
