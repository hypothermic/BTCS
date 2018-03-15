package net.minecraft.server;

import java.util.Random;

public class BiomeTheEndDecorator
  extends BiomeDecorator
{
  public BiomeTheEndDecorator(BiomeBase paramBiomeBase)
  {
    super(paramBiomeBase);
  }
  
  protected WorldGenerator L = new WorldGenEnder(Block.WHITESTONE.id);
  
  protected void a() {
    b();
    
    if (this.b.nextInt(5) == 0) {
      int i = this.c + this.b.nextInt(16) + 8;
      int j = this.d + this.b.nextInt(16) + 8;
      int k = this.a.g(i, j);
      if (k > 0) {}
      
      this.L.a(this.a, this.b, i, k, j);
    }
    
    if ((this.c == 0) && (this.d == 0)) {
      EntityEnderDragon localEntityEnderDragon = new EntityEnderDragon(this.a);
      localEntityEnderDragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.b.nextFloat() * 360.0F, 0.0F);
      this.a.addEntity(localEntityEnderDragon);
    }
  }
}
