package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;














public class DataWatcher
{
  private static final HashMap a = new HashMap();
  
  static { a.put(Byte.class, Integer.valueOf(0));
    a.put(Short.class, Integer.valueOf(1));
    a.put(Integer.class, Integer.valueOf(2));
    a.put(Float.class, Integer.valueOf(3));
    a.put(String.class, Integer.valueOf(4));
    a.put(ItemStack.class, Integer.valueOf(5));
    a.put(ChunkCoordinates.class, Integer.valueOf(6));
  }
  







  private final Map b = new HashMap();
  

  private boolean c;
  

  public void a(int paramInt, Object paramObject)
  {
    Integer localInteger = (Integer)a.get(paramObject.getClass());
    if (localInteger == null) {
      throw new IllegalArgumentException("Unknown data type: " + paramObject.getClass());
    }
    if (paramInt > 31) {
      throw new IllegalArgumentException("Data value id is too big with " + paramInt + "! (Max is " + 31 + ")");
    }
    if (this.b.containsKey(Integer.valueOf(paramInt))) {
      throw new IllegalArgumentException("Duplicate id value for " + paramInt + "!");
    }
    
    WatchableObject localWatchableObject = new WatchableObject(localInteger.intValue(), paramInt, paramObject);
    this.b.put(Integer.valueOf(paramInt), localWatchableObject);
  }
  
  public byte getByte(int paramInt) {
    return ((Byte)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b()).byteValue();
  }
  
  public short b(int paramInt) {
    return ((Short)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b()).shortValue();
  }
  
  public int getInt(int paramInt) {
    return ((Integer)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b()).intValue();
  }
  



  public String getString(int paramInt)
  {
    return (String)((WatchableObject)this.b.get(Integer.valueOf(paramInt))).b();
  }
  







  public void watch(int paramInt, Object paramObject)
  {
    WatchableObject localWatchableObject = (WatchableObject)this.b.get(Integer.valueOf(paramInt));
    

    if (!paramObject.equals(localWatchableObject.b())) {
      localWatchableObject.a(paramObject);
      localWatchableObject.a(true);
      this.c = true;
    }
  }
  




  public boolean a()
  {
    return this.c;
  }
  
  public static void a(List paramList, DataOutputStream paramDataOutputStream)
  {
    if (paramList != null) {
      for (WatchableObject localWatchableObject : (List<WatchableObject>) paramList) { // BTCS: added cast (List<WatchableObject>) 
        a(paramDataOutputStream, localWatchableObject);
      }
    }
    

    try {
		paramDataOutputStream.writeByte(127);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X3 happened in DataWatcher");
		e.printStackTrace();
	}
  }
  
  public ArrayList b()
  {
    ArrayList localArrayList = null;
    
    if (this.c) {
      for (WatchableObject localWatchableObject : (List<WatchableObject>) this.b.values()) { // BTCS: added cast (List<WatchableObject>)
        if (localWatchableObject.d()) {
          localWatchableObject.a(false);
          
          if (localArrayList == null) {
            localArrayList = new ArrayList();
          }
          localArrayList.add(localWatchableObject);
        }
      }
    }
    this.c = false;
    
    return localArrayList;
  }
  
  public void a(DataOutputStream paramDataOutputStream) {
    for (WatchableObject localWatchableObject : (List<WatchableObject>) this.b.values()) { // BTCS: added cast (List<WatchableObject>)
      a(paramDataOutputStream, localWatchableObject);
    }
    

    try {
		paramDataOutputStream.writeByte(127);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X4 happened in DataWatcher");
		e.printStackTrace();
	}
  }
  
  private static void a(DataOutputStream paramDataOutputStream, WatchableObject paramWatchableObject)
  {
    int i = (paramWatchableObject.c() << 5 | paramWatchableObject.a() & 0x1F) & 0xFF;
    try {
		paramDataOutputStream.writeByte(i);
	} catch (IOException e) {
		System.out.println("BTCS: Exception X5 happened in DataWatcher");
		e.printStackTrace();
	}
    
    Object localObject;
    try { // BTCS start
    switch (paramWatchableObject.c()) {
    case 0: 
      paramDataOutputStream.writeByte(((Byte)paramWatchableObject.b()).byteValue());
      break;
    case 1: 
      paramDataOutputStream.writeShort(((Short)paramWatchableObject.b()).shortValue());
      break;
    case 2: 
      paramDataOutputStream.writeInt(((Integer)paramWatchableObject.b()).intValue());
      break;
    case 3: 
      paramDataOutputStream.writeFloat(((Float)paramWatchableObject.b()).floatValue());
      break;
    case 4: 
      Packet.a((String)paramWatchableObject.b(), paramDataOutputStream);
      break;
    case 5: 
      localObject = (ItemStack)paramWatchableObject.b();
      paramDataOutputStream.writeShort(((ItemStack)localObject).getItem().id);
      paramDataOutputStream.writeByte(((ItemStack)localObject).count);
      paramDataOutputStream.writeShort(((ItemStack)localObject).getData());
      
      break;
    case 6: 
      localObject = (ChunkCoordinates)paramWatchableObject.b();
      paramDataOutputStream.writeInt(((ChunkCoordinates)localObject).x);
      paramDataOutputStream.writeInt(((ChunkCoordinates)localObject).y);
      paramDataOutputStream.writeInt(((ChunkCoordinates)localObject).z);
    }} catch (IOException x) {
    	System.out.println("BTCS: Exception X6 happened in DataWatcher");
    	x.printStackTrace();
    } // BTCS end
    
  }
  

  public static List a(DataInputStream paramDataInputStream)
  {
    ArrayList localArrayList = null;
    
    try {
    int i = paramDataInputStream.readByte();
    
    while (i != 127)
    {
      if (localArrayList == null) {
        localArrayList = new ArrayList();
      }
      

      int j = (i & 0xE0) >> 5;
      int k = i & 0x1F;
      WatchableObject localWatchableObject = null;
      int m; int n; int i1; switch (j) {
      case 0: 
        localWatchableObject = new WatchableObject(j, k, Byte.valueOf(paramDataInputStream.readByte()));
        break;
      case 1: 
        localWatchableObject = new WatchableObject(j, k, Short.valueOf(paramDataInputStream.readShort()));
        break;
      case 2: 
        localWatchableObject = new WatchableObject(j, k, Integer.valueOf(paramDataInputStream.readInt()));
        break;
      case 3: 
        localWatchableObject = new WatchableObject(j, k, Float.valueOf(paramDataInputStream.readFloat()));
        break;
      case 4: 
        localWatchableObject = new WatchableObject(j, k, Packet.a(paramDataInputStream, 64));
        break;
      case 5: 
        m = paramDataInputStream.readShort();
        n = paramDataInputStream.readByte();
        i1 = paramDataInputStream.readShort();
        localWatchableObject = new WatchableObject(j, k, new ItemStack(m, n, i1));
        
        break;
      case 6: 
        m = paramDataInputStream.readInt();
        n = paramDataInputStream.readInt();
        i1 = paramDataInputStream.readInt();
        localWatchableObject = new WatchableObject(j, k, new ChunkCoordinates(m, n, i1));
      }
      
      
      localArrayList.add(localWatchableObject);
      
      i = paramDataInputStream.readByte();
    }} catch (IOException x) {
    	System.out.println("BTCS: Exception X7 happened in DataWatcher");
    	x.printStackTrace();
    }
    
    return localArrayList;
  }
}
