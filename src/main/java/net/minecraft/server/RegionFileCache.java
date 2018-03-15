package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class RegionFileCache
{
  private static final Map a = new HashMap();
  


  public static synchronized RegionFile a(File paramFile, int paramInt1, int paramInt2)
  {
    File localFile1 = new File(paramFile, "region");
    File localFile2 = new File(localFile1, "r." + (paramInt1 >> 5) + "." + (paramInt2 >> 5) + ".mca");
    
    Reference localReference = (Reference)a.get(localFile2);
    
    if (localReference != null) {
      RegionFile localRegionFile = (RegionFile)localReference.get(); // BTCS: added decl 'RegionFile'
      if (localRegionFile != null) {
        return localRegionFile;
      }
    }
    
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    
    if (a.size() >= 256) {
      a();
    }
    
    RegionFile localRegionFile = new RegionFile(localFile2);
    a.put(localFile2, new SoftReference(localRegionFile));
    return localRegionFile;
  }
  
  public static synchronized void a() {
    for (Reference localReference : (Reference[]) a.values().toArray()) {
      RegionFile localRegionFile = (RegionFile)localReference.get();
	if (localRegionFile != null) {
	  localRegionFile.a();
	  // BTCS: removed try-catch with IOX around here.
	}
    }
    a.clear();
  }
  




  public static DataInputStream b(File paramFile, int paramInt1, int paramInt2)
  {
    RegionFile localRegionFile = a(paramFile, paramInt1, paramInt2);
    return localRegionFile.a(paramInt1 & 0x1F, paramInt2 & 0x1F);
  }
  
  public static DataOutputStream c(File paramFile, int paramInt1, int paramInt2) {
    RegionFile localRegionFile = a(paramFile, paramInt1, paramInt2);
    return localRegionFile.b(paramInt1 & 0x1F, paramInt2 & 0x1F);
  }
}
