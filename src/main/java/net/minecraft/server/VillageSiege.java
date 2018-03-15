package net.minecraft.server;

import java.util.Iterator;
import java.util.Random;

public class VillageSiege
{
  private World world;
  private boolean b = false;
  private int c = -1;
  private int d;
  private int e;
  private Village f;
  private int g;
  private int h;
  private int i;
  
  public VillageSiege(World world) {
    this.world = world;
  }
  
  public void a() {
    boolean flag = false;
    
    if (flag) {
      if (this.c == 2) {
        this.d = 100;
      }
    }
    else {
      if (this.world.e()) {
        this.c = 0;
        return;
      }
      
      if (this.c == 2) {
        return;
      }
      
      if (this.c == 0) {
        float f = this.world.b(0.0F);
        
        if ((f < 0.5D) || (f > 0.501D)) {
          return;
        }
        
        this.c = (this.world.random.nextInt(10) == 0 ? 1 : 2);
        this.b = false;
        if (this.c == 2) {
          return;
        }
      }
    }
    
    if (!this.b) {
      if (!b()) {
        return;
      }
      
      this.b = true;
    }
    
    if (this.e > 0) {
      this.e -= 1;
    } else {
      this.e = 2;
      if (this.d > 0) {
        c();
        this.d -= 1;
      } else {
        this.c = 2;
      }
    }
  }
  
  private boolean b() {
    java.util.List list = this.world.players;
    Iterator iterator = list.iterator();
    
    while (iterator.hasNext()) {
      EntityHuman entityhuman = (EntityHuman)iterator.next();
      
      this.f = this.world.villages.getClosestVillage((int)entityhuman.locX, (int)entityhuman.locY, (int)entityhuman.locZ, 1);
      if ((this.f != null) && (this.f.getDoorCount() >= 10) && (this.f.d() >= 20) && (this.f.getPopulationCount() >= 20)) {
        ChunkCoordinates chunkcoordinates = this.f.getCenter();
        float f = this.f.getSize();
        boolean flag = false;
        int i = 0;
        

        while (i < 10) {
          this.g = (chunkcoordinates.x + (int)(MathHelper.cos(this.world.random.nextFloat() * 3.1415927F * 2.0F) * f * 0.9D));
          this.h = chunkcoordinates.y;
          this.i = (chunkcoordinates.z + (int)(MathHelper.sin(this.world.random.nextFloat() * 3.1415927F * 2.0F) * f * 0.9D));
          flag = false;
          Iterator iterator1 = this.world.villages.getVillages().iterator();
          
          while (iterator1.hasNext()) {
            Village village = (Village)iterator1.next();
            
            if ((village != this.f) && (village.a(this.g, this.h, this.i))) {
              flag = true;
              break;
            }
          }
          
          if (!flag) break;
          i++;
        }
        


        if (flag) {
          return false;
        }
        
        Vec3D vec3d = a(this.g, this.h, this.i);
        
        if (vec3d != null) {
          this.e = 0;
          this.d = 20;
          return true;
        }
      }
    }
    


    return false;
  }
  
  private boolean c() {
    Vec3D vec3d = a(this.g, this.h, this.i);
    
    if (vec3d == null) {
      return false;
    }
    EntityZombie entityzombie;
    try
    {
      entityzombie = new EntityZombie(this.world);
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    }
    
    entityzombie.setPositionRotation(vec3d.a, vec3d.b, vec3d.c, this.world.random.nextFloat() * 360.0F, 0.0F);
    this.world.addEntity(entityzombie, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION);
    ChunkCoordinates chunkcoordinates = this.f.getCenter();
    
    entityzombie.b(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, this.f.getSize());
    return true;
  }
  
  private Vec3D a(int i, int j, int k)
  {
    for (int l = 0; l < 10; l++) {
      int i1 = i + this.world.random.nextInt(16) - 8;
      int j1 = j + this.world.random.nextInt(6) - 3;
      int k1 = k + this.world.random.nextInt(16) - 8;
      
      if ((this.f.a(i1, j1, k1)) && (SpawnerCreature.a(EnumCreatureType.MONSTER, this.world, i1, j1, k1))) {
        return Vec3D.create(i1, j1, k1);
      }
    }
    
    return null;
  }
}
