package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class WorldGenNether
  extends StructureGenerator
{
  private List a = new ArrayList();
  

  public WorldGenNether()
  {
    this.a.add(new BiomeMeta(EntityBlaze.class, 10, 2, 3));
    this.a.add(new BiomeMeta(EntityPigZombie.class, 10, 4, 4));
    this.a.add(new BiomeMeta(EntityMagmaCube.class, 3, 4, 4));
  }
  
  public List b() {
    return this.a;
  }
  

  protected boolean a(int paramInt1, int paramInt2)
  {
    int i = paramInt1 >> 4;
    int j = paramInt2 >> 4;
    
    this.c.setSeed(i ^ j << 4 ^ this.d.getSeed());
    this.c.nextInt();
    
    if (this.c.nextInt(3) != 0) {
      return false;
    }
    if (paramInt1 != (i << 4) + 4 + this.c.nextInt(8)) {
      return false;
    }
    if (paramInt2 != (j << 4) + 4 + this.c.nextInt(8)) {
      return false;
    }
    return true;
  }
  

  protected StructureStart b(int paramInt1, int paramInt2)
  {
    return new WorldGenNetherStart(this.d, this.c, paramInt1, paramInt2);
  }
}
