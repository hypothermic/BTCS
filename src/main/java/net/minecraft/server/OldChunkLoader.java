package net.minecraft.server;








public class OldChunkLoader
{
  public static OldChunk a(NBTTagCompound paramNBTTagCompound)
  {
    int i = paramNBTTagCompound.getInt("xPos");
    int j = paramNBTTagCompound.getInt("zPos");
    
    OldChunk localOldChunk = new OldChunk(i, j);
    localOldChunk.g = paramNBTTagCompound.getByteArray("Blocks");
    localOldChunk.f = new OldNibbleArray(paramNBTTagCompound.getByteArray("Data"), 7);
    localOldChunk.e = new OldNibbleArray(paramNBTTagCompound.getByteArray("SkyLight"), 7);
    localOldChunk.d = new OldNibbleArray(paramNBTTagCompound.getByteArray("BlockLight"), 7);
    localOldChunk.c = paramNBTTagCompound.getByteArray("HeightMap");
    localOldChunk.b = paramNBTTagCompound.getBoolean("TerrainPopulated");
    localOldChunk.h = paramNBTTagCompound.getList("Entities");
    localOldChunk.i = paramNBTTagCompound.getList("TileEntities");
    localOldChunk.j = paramNBTTagCompound.getList("TileTicks");
    
    try
    {
      localOldChunk.a = paramNBTTagCompound.getLong("LastUpdate");
    } catch (ClassCastException localClassCastException) {
      localOldChunk.a = paramNBTTagCompound.getInt("LastUpdate");
    }
    
    return localOldChunk;
  }
  
  public static void a(OldChunk paramOldChunk, NBTTagCompound paramNBTTagCompound, WorldChunkManager paramWorldChunkManager)
  {
    paramNBTTagCompound.setInt("xPos", paramOldChunk.k);
    paramNBTTagCompound.setInt("zPos", paramOldChunk.l);
    paramNBTTagCompound.setLong("LastUpdate", paramOldChunk.a);
    int[] arrayOfInt = new int[paramOldChunk.c.length];
    for (int i = 0; i < paramOldChunk.c.length; i++) {
      arrayOfInt[i] = paramOldChunk.c[i];
    }
    paramNBTTagCompound.setIntArray("HeightMap", arrayOfInt);
    paramNBTTagCompound.setBoolean("TerrainPopulated", paramOldChunk.b);
    
    NBTTagList localNBTTagList = new NBTTagList("Sections");
    for (int j = 0; j < 8; j++)
    {

      int k = 1; // BTCS: added decl 'int '
      int i4; for (int m = 0; (m < 16) && (k != 0); m++) {
        for (int i1 = 0; (i1 < 16) && (k != 0); i1++) {
          for (int i2 = 0; i2 < 16; i2++) {
            int i3 = m << 11 | i2 << 7 | i1 + (j << 4);
            i4 = paramOldChunk.g[i3];
            if (i4 != 0) {
              k = 0;
              break;
            }
          }
        }
      }
      
      if (k == 0)
      {



        byte[] arrayOfByte2 = new byte['က'];
        NibbleArray localNibbleArray1 = new NibbleArray(arrayOfByte2.length, 4);
        NibbleArray localNibbleArray2 = new NibbleArray(arrayOfByte2.length, 4);
        NibbleArray localNibbleArray3 = new NibbleArray(arrayOfByte2.length, 4);
        
        for (i4 = 0; i4 < 16; i4++) {
          for (int i5 = 0; i5 < 16; i5++) {
            for (int i6 = 0; i6 < 16; i6++) {
              int i7 = i4 << 11 | i6 << 7 | i5 + (j << 4);
              int i8 = paramOldChunk.g[i7];
              
              arrayOfByte2[(i5 << 8 | i6 << 4 | i4)] = ((byte)(i8 & 0xFF));
              localNibbleArray1.a(i4, i5, i6, paramOldChunk.f.a(i4, i5 + (j << 4), i6));
              localNibbleArray2.a(i4, i5, i6, paramOldChunk.e.a(i4, i5 + (j << 4), i6));
              localNibbleArray3.a(i4, i5, i6, paramOldChunk.d.a(i4, i5 + (j << 4), i6));
            }
          }
        }
        
        NBTTagCompound localNBTTagCompound = new NBTTagCompound();
        
        localNBTTagCompound.setByte("Y", (byte)(j & 0xFF));
        localNBTTagCompound.setByteArray("Blocks", arrayOfByte2);
        localNBTTagCompound.setByteArray("Data", localNibbleArray1.a);
        localNBTTagCompound.setByteArray("SkyLight", localNibbleArray2.a);
        localNBTTagCompound.setByteArray("BlockLight", localNibbleArray3.a);
        
        localNBTTagList.add(localNBTTagCompound);
      } }
    paramNBTTagCompound.set("Sections", localNBTTagList);
    

    byte[] arrayOfByte1 = new byte['Ā'];
    for (int k = 0; k < 16; k++) {
      for (int n = 0; n < 16; n++) {
        arrayOfByte1[(n << 4 | k)] = ((byte)(paramWorldChunkManager.getBiome(paramOldChunk.k << 4 | k, paramOldChunk.l << 4 | n).id & 0xFF));
      }
    }
    paramNBTTagCompound.setByteArray("Biomes", arrayOfByte1);
    
    paramNBTTagCompound.set("Entities", paramOldChunk.h);
    
    paramNBTTagCompound.set("TileEntities", paramOldChunk.i);
    
    if (paramOldChunk.j != null) {
      paramNBTTagCompound.set("TileTicks", paramOldChunk.j);
    }
  }
}
