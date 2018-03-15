package net.minecraft.server;

import java.util.Random;



public class WorldGenMineshaft
  extends StructureGenerator
{
  protected boolean a(int paramInt1, int paramInt2)
  {
    return (this.c.nextInt(100) == 0) && (this.c.nextInt(80) < Math.max(Math.abs(paramInt1), Math.abs(paramInt2)));
  }
  
  protected StructureStart b(int paramInt1, int paramInt2)
  {
    return new WorldGenMineshaftStart(this.d, this.c, paramInt1, paramInt2);
  }
}
