package net.minecraft.server;

import org.bukkit.map.MapCursor;

public class WorldMapHumanTracker {
  public final EntityHuman trackee;
  public int[] b;
  public int[] c;
  private int e;
  private int f;
  private byte[] g;
  final WorldMap worldMap;
  
  public WorldMapHumanTracker(WorldMap worldmap, EntityHuman entityhuman) {
    this.worldMap = worldmap;
    this.b = new int[''];
    this.c = new int[''];
    this.e = 0;
    this.f = 0;
    this.trackee = entityhuman;
    
    for (int i = 0; i < this.b.length; i++) {
      this.b[i] = 0;
      this.c[i] = 127;
    }
  }
  


  public byte[] a(ItemStack itemstack)
  {
    org.bukkit.craftbukkit.map.RenderData render = this.worldMap.mapView.render((org.bukkit.craftbukkit.entity.CraftPlayer)this.trackee.getBukkitEntity());
    
    if (--this.f < 0) {
      this.f = 4;
      byte[] abyte = new byte[render.cursors.size() * 3 + 1];
      
      abyte[0] = 1;
      

      for (int i = 0; i < render.cursors.size(); i++) {
        MapCursor cursor = (MapCursor)render.cursors.get(i);
        if (cursor.isVisible())
        {
          byte value = (byte)((((cursor.getRawType() == 0) || (cursor.getDirection() < 8) ? cursor.getDirection() : cursor.getDirection() - 1) & 0xF) * 16);
          abyte[(i * 3 + 1)] = ((byte)(value | ((cursor.getRawType() != 0) && (value < 0) ? 16 - cursor.getRawType() : cursor.getRawType())));
          abyte[(i * 3 + 2)] = cursor.getX();
          abyte[(i * 3 + 3)] = cursor.getY();
        }
      }
      
      boolean flag = true;
      int j;
      if ((this.g != null) && (this.g.length == abyte.length)) {
        for (j = 0; j < abyte.length;)
          if (abyte[j] != this.g[j]) {
            flag = false;
          }
          else
          {
            j++; continue;
            //flag = false; // BTCS: unreachable code, commented
          }
      }
      if (!flag) {
        this.g = abyte;
        return abyte;
      }
    }
    
    for (int k = 0; k < 10; k++) {
      int i = this.e * 11 % 128;
      this.e += 1;
      if (this.b[i] >= 0) {
        int j = this.c[i] - this.b[i] + 1;
        int l = this.b[i];
        byte[] abyte1 = new byte[j + 3];
        
        abyte1[0] = 0;
        abyte1[1] = ((byte)i);
        abyte1[2] = ((byte)l);
        
        for (int i1 = 0; i1 < abyte1.length - 3; i1++) {
          abyte1[(i1 + 3)] = render.buffer[((i1 + l) * 128 + i)];
        }
        
        this.c[i] = -1;
        this.b[i] = -1;
        return abyte1;
      }
    }
    
    return null;
  }
}
