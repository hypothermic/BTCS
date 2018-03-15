package net.minecraft.server;

import java.util.Random;

public class PathfinderGoalJumpOnBlock
  extends PathfinderGoal
{
  private final EntityOcelot a;
  private final float b;
  private int c = 0;
  private int h = 0;
  private int d = 0;
  private int e = 0;
  private int f = 0;
  private int g = 0;
  
  public PathfinderGoalJumpOnBlock(EntityOcelot paramEntityOcelot, float paramFloat) {
    this.a = paramEntityOcelot;
    this.b = paramFloat;
    a(5);
  }
  
  public boolean a()
  {
    return (this.a.isTamed()) && (!this.a.isSitting()) && (this.a.an().nextDouble() <= 0.006500000134110451D) && (f());
  }
  
  public boolean b()
  {
    return (this.c <= this.d) && (this.h <= 60) && (a(this.a.world, this.e, this.f, this.g));
  }
  
  public void c()
  {
    this.a.al().a(this.e + 0.5D, this.f + 1, this.g + 0.5D, this.b);
    this.c = 0;
    this.h = 0;
    this.d = (this.a.an().nextInt(this.a.an().nextInt(1200) + 1200) + 1200);
    this.a.C().a(false);
  }
  
  public void d()
  {
    this.a.setSitting(false);
  }
  
  public void e()
  {
    this.c += 1;
    this.a.C().a(false);
    if (this.a.e((double)this.e, this.f + 1, this.g) > 1.0D) { //BTCS: added cast (double)
      this.a.setSitting(false);
      this.a.al().a(this.e + 0.5D, this.f + 1, this.g + 0.5D, this.b);
      this.h += 1;
    } else if (!this.a.isSitting()) {
      this.a.setSitting(true);
    } else {
      this.h -= 1;
    }
  }
  
  private boolean f() {
    int i = (int)this.a.locY;
    double d1 = 2.147483647E9D;
    
    for (int j = (int)this.a.locX - 8; j < this.a.locX + 8.0D; j++) {
      for (int k = (int)this.a.locZ - 8; k < this.a.locZ + 8.0D; k++) {
        if ((a(this.a.world, j, i, k)) && (this.a.world.isEmpty(j, i + 1, k))) {
          double d2 = this.a.e((double)j, i, k); // BTCS: added cast (double)
          
          if (d2 < d1) {
            this.e = j;
            this.f = i;
            this.g = k;
            d1 = d2;
          }
        }
      }
    }
    
    return d1 < 2.147483647E9D;
  }
  
  private boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
    int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if (i == Block.CHEST.id) {
      TileEntityChest localTileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      
      if (localTileEntityChest.h < 1)
        return true;
    } else {
      if (i == Block.BURNING_FURNACE.id)
        return true;
      if ((i == Block.BED.id) && (!BlockBed.d(j))) {
        return true;
      }
    }
    return false;
  }
}
