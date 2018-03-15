package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorldMapCollection
{
  private IDataManager a;
  private Map b = new HashMap();
  private List c = new ArrayList();
  private Map d = new HashMap();
  
  public WorldMapCollection(IDataManager paramIDataManager) {
    this.a = paramIDataManager;
    b();
  }
  
  public WorldMapBase get(Class paramClass, String paramString) {
    WorldMapBase localWorldMapBase = (WorldMapBase)this.b.get(paramString);
    if (localWorldMapBase != null) { return localWorldMapBase;
    }
    if (this.a != null) {
      try {
        File localFile = this.a.getDataFile(paramString);
        if ((localFile != null) && (localFile.exists())) {
          try {
            localWorldMapBase = (WorldMapBase)paramClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
          } catch (Exception localException2) {
            throw new RuntimeException("Failed to instantiate " + paramClass.toString(), localException2);
          }
          
          FileInputStream localFileInputStream = new FileInputStream(localFile);
          NBTTagCompound localNBTTagCompound = NBTCompressedStreamTools.a(localFileInputStream);
          localFileInputStream.close();
          
          localWorldMapBase.a(localNBTTagCompound.getCompound("data"));
        }
      } catch (Exception localException1) {
        localException1.printStackTrace();
      }
    }
    
    if (localWorldMapBase != null) {
      this.b.put(paramString, localWorldMapBase);
      this.c.add(localWorldMapBase);
    }
    return localWorldMapBase;
  }
  
  public void a(String paramString, WorldMapBase paramWorldMapBase) {
    if (paramWorldMapBase == null) throw new RuntimeException("Can't set null data");
    if (this.b.containsKey(paramString)) {
      this.c.remove(this.b.remove(paramString));
    }
    this.b.put(paramString, paramWorldMapBase);
    this.c.add(paramWorldMapBase);
  }
  
  public void a() {
    for (int i = 0; i < this.c.size(); i++) {
      WorldMapBase localWorldMapBase = (WorldMapBase)this.c.get(i);
      if (localWorldMapBase.b()) {
        a(localWorldMapBase);
        localWorldMapBase.a(false);
      }
    }
  }
  
  private void a(WorldMapBase paramWorldMapBase) {
    if (this.a == null) return;
    try {
      File localFile = this.a.getDataFile(paramWorldMapBase.id);
      if (localFile != null) {
        NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
        paramWorldMapBase.b(localNBTTagCompound1);
        
        NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
        localNBTTagCompound2.setCompound("data", localNBTTagCompound1);
        
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
        NBTCompressedStreamTools.a(localNBTTagCompound2, localFileOutputStream, false); // BTCS: added arg 2 to match new method
        localFileOutputStream.close();
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  private void b() {
    try {
      this.d.clear();
      if (this.a == null) return;
      File localFile = this.a.getDataFile("idcounts");
      if ((localFile != null) && (localFile.exists())) {
        DataInputStream localDataInputStream = new DataInputStream(new FileInputStream(localFile));
        NBTTagCompound localNBTTagCompound = NBTCompressedStreamTools.a(localDataInputStream);
        localDataInputStream.close();
        
        for (NBTBase localNBTBase : (NBTBase[]) localNBTTagCompound.d().toArray()) { // BTCS: added cast and .toArray()
          if ((localNBTBase instanceof NBTTagShort)) {
            NBTTagShort localNBTTagShort = (NBTTagShort)localNBTBase;
            String str = localNBTTagShort.getName();
            short s = localNBTTagShort.data;
            this.d.put(str, Short.valueOf(s));
          }
        }
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }
  
  public int a(String paramString) {
    Short localShort = (Short)this.d.get(paramString);
    Object localObject1; Object localObject2; if (localShort == null) {
      localShort = Short.valueOf((short)0);
    } else {
      localObject1 = localShort;localObject2 = localShort = Short.valueOf((short)(localShort.shortValue() + 1));
    }
    
    this.d.put(paramString, localShort);
    if (this.a == null) return localShort.shortValue();
    try {
      localObject1 = this.a.getDataFile("idcounts");
      if (localObject1 != null) {
        localObject2 = new NBTTagCompound();
        
        for (Object localObject3 = this.d.keySet().iterator(); ((Iterator)localObject3).hasNext();) { String str = (String)((Iterator)localObject3).next();
          short s = ((Short)this.d.get(str)).shortValue();
          ((NBTTagCompound)localObject2).setShort(str, s);
        }
        
        Object localObject3 = new DataOutputStream(new FileOutputStream((File)localObject1)); // BTCS: added decl 'Object'
        NBTCompressedStreamTools.a((NBTTagCompound)localObject2, (DataOutput)localObject3);
        ((DataOutputStream)localObject3).close();
      }
    } catch (Exception localException) {
      localException.printStackTrace();
    }
    return localShort.shortValue();
  }
}
