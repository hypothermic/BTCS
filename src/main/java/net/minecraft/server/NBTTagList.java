package net.minecraft.server;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

import nl.hypothermic.btcs.Launcher;
import nl.hypothermic.btcs.NBTReadLimiter;

public class NBTTagList extends NBTBase {
  private List list = new java.util.ArrayList();
  private byte type;
  
  public NBTTagList() {
    super("");
  }
  
  public NBTTagList(String paramString) {
    super(paramString);
  }
  
  void write(java.io.DataOutput paramDataOutput) {
    if (this.list.size() > 0) this.type = ((NBTBase)this.list.get(0)).getTypeId(); else {
      this.type = 1;
    }
    try {
		paramDataOutput.writeByte(this.type);
		paramDataOutput.writeInt(this.list.size());
		for (int i = 0; i < this.list.size(); i++) {
		      ((NBTBase)this.list.get(i)).write(paramDataOutput);
		}
	} catch (IOException e) {
	}
  }
  
  // EDIT: undid all changes to load(...) in BTCS v1.30
  void load(java.io.DataInput paramDataInput) { // BTCS: added depth, see https://hub.spigotmc.org/stash/projects/SPIGOT/repos/Spigot/commits/52df9dd70f0#CraftBukkit-Patches/0139-Apply-NBTReadLimiter-to-more-things.patch, except we didn't implement a limiter yet
    try {
    	/*if (depth > 512) { // BTCS TODO: figure out best value for maxdepth, this value may interfere with mods
    		throw new IOException("BTCS: Exception X310 happened in NBT: tag is too complex.");
    	}*/
		this.type = paramDataInput.readByte();
		int i = paramDataInput.readInt();
		this.list = new java.util.ArrayList();
	    for (int j = 0; j < i; j++) {
	      NBTBase localNBTBase = NBTBase.createTag(this.type, null);
	      // BTCS TODO: implement NBTReadLimiter and streamlimiter for large tags with low depth
	      localNBTBase.load(paramDataInput);
	      this.list.add(localNBTBase);
	    }
	} catch (IOException e) {
	}
  }
  
  public byte getTypeId() {
    return 9;
  }
  
  public String toString() {
    return "" + this.list.size() + " entries of type " + NBTBase.getTagName(this.type);
  }

  public void add(NBTBase paramNBTBase)
  {
    this.type = paramNBTBase.getTypeId();
    this.list.add(paramNBTBase);
  }
  
  public NBTBase get(int paramInt) {
    return (NBTBase)this.list.get(paramInt);
  }
  
  public int size() {
    return this.list.size();
  }
  
  public NBTBase clone()
  {
    NBTTagList localNBTTagList = new NBTTagList(getName());
    localNBTTagList.type = this.type;
    for (NBTBase localNBTBase1 : (NBTBase[]) this.list.toArray()) // BTCS: added cast and .toArray()
    {
      NBTBase localNBTBase2 = localNBTBase1.clone();
      localNBTTagList.list.add(localNBTBase2);
    }
    return localNBTTagList;
  }
  

  public boolean equals(Object paramObject)
  {
    if (super.equals(paramObject)) {
      NBTTagList localNBTTagList = (NBTTagList)paramObject;
      if (this.type == localNBTTagList.type) {
        return this.list.equals(localNBTTagList.list);
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    return super.hashCode() ^ this.list.hashCode();
  }
}
