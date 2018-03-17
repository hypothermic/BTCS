package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import nl.hypothermic.btcs.NBTReadLimiter;

public abstract class NBTBase {
  private String name;
  
  abstract void write(DataOutput paramDataOutput);
  
  abstract void load(DataInput paramDataInput, int depth); // BTCS
  
  public abstract byte getTypeId();
  
  protected NBTBase(String paramString)
  {
    if (paramString == null) {
      this.name = "";
    } else {
      this.name = paramString;
    }
  }
  
  public NBTBase setName(String paramString)
  {
    if (paramString == null) {
      this.name = "";
    } else {
      this.name = paramString;
    }
    return this;
  }
  
  public String getName() {
    if (this.name == null) return "";
    return this.name;
  }
  
  public static NBTBase b(DataInput paramDataInput, int depth) {
    byte b;
	try { // BTCS: try-catch
		b = paramDataInput.readByte();
		String str = paramDataInput.readUTF();
		if (b == 0) { return new NBTTagEnd();
	    }
	    NBTBase localNBTBase = createTag(b, str);
	    localNBTBase.load(paramDataInput, depth);
	    return localNBTBase;
	} catch (IOException e) {
		System.out.println("BTCS: Exception X9 happened in NBTBase");
		e.printStackTrace();
		return new NBTTagEnd();
	}
  }
  
  public static void a(NBTBase paramNBTBase, DataOutput paramDataOutput) {
    try { // BTCS: try-catch
		paramDataOutput.writeByte(paramNBTBase.getTypeId());
		if (paramNBTBase.getTypeId() == 0) { return;
	    }
	    paramDataOutput.writeUTF(paramNBTBase.getName());
	    
	    paramNBTBase.write(paramDataOutput);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X10 happened in NBTBase");
		e.printStackTrace();
	}
  }
  
  public static NBTBase createTag(byte paramByte, String paramString) {
    switch (paramByte) {
    case 0: 
      return new NBTTagEnd();
    case 1: 
      return new NBTTagByte(paramString);
    case 2: 
      return new NBTTagShort(paramString);
    case 3: 
      return new NBTTagInt(paramString);
    case 4: 
      return new NBTTagLong(paramString);
    case 5: 
      return new NBTTagFloat(paramString);
    case 6: 
      return new NBTTagDouble(paramString);
    case 7: 
      return new NBTTagByteArray(paramString);
    case 11: 
      return new NBTTagIntArray(paramString);
    case 8: 
      return new NBTTagString(paramString);
    case 9: 
      return new NBTTagList(paramString);
    case 10: 
      return new NBTTagCompound(paramString);
    }
    return null;
  }
  
  public static String getTagName(byte paramByte) {
    switch (paramByte) {
    case 0: 
      return "TAG_End";
    case 1: 
      return "TAG_Byte";
    case 2: 
      return "TAG_Short";
    case 3: 
      return "TAG_Int";
    case 4: 
      return "TAG_Long";
    case 5: 
      return "TAG_Float";
    case 6: 
      return "TAG_Double";
    case 7: 
      return "TAG_Byte_Array";
    case 11: 
      return "TAG_Int_Array";
    case 8: 
      return "TAG_String";
    case 9: 
      return "TAG_List";
    case 10: 
      return "TAG_Compound";
    }
    return "UNKNOWN";
  }
  
  public abstract NBTBase clone();
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof NBTBase))) {
      return false;
    }
    NBTBase localNBTBase = (NBTBase)paramObject;
    if (getTypeId() != localNBTBase.getTypeId()) {
      return false;
    }
    if (((this.name == null) && (localNBTBase.name != null)) || ((this.name != null) && (localNBTBase.name == null))) {
      return false;
    }
    if ((this.name != null) && (!this.name.equals(localNBTBase.name))) {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    return this.name.hashCode() ^ getTypeId();
  }
}
