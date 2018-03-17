package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;

public class WM_Explosion extends Explosion
{
  private World worldObj;
  private static final Random rand = new Random();
  
  public WM_Explosion(World paramWorld, Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat)
  {
    super(paramWorld, paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat);
    this.worldObj = paramWorld;
  }
  



  public void a()
  {
    super.a();
  }
  
  public void doExplosionB(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.worldObj.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
    
    if (paramBoolean2)
    {
      this.worldObj.a("hugeexplosion", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    }
    
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.blocks);
    
    for (int i = localArrayList.size() - 1; i >= 0; i--)
    {
      ChunkPosition localChunkPosition = (ChunkPosition)localArrayList.get(i);
      int j = localChunkPosition.x;
      int k = localChunkPosition.y;
      int m = localChunkPosition.z;
      int n = this.worldObj.getTypeId(j, k, m);
      
      if (paramBoolean1)
      {
        double d1 = j + this.worldObj.random.nextFloat();
        double d2 = k + this.worldObj.random.nextFloat();
        double d3 = m + this.worldObj.random.nextFloat();
        double d4 = d1 - this.posX;
        double d5 = d2 - this.posY;
        double d6 = d3 - this.posZ;
        double d7 = MathHelper.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        d4 /= d7;
        d5 /= d7;
        d6 /= d7;
        double d8 = 0.5D / (d7 / this.size + 0.1D);
        d8 *= (this.worldObj.random.nextFloat() * this.worldObj.random.nextFloat() + 0.3F);
        d4 *= d8;
        d5 *= d8;
        d6 *= d8;
        this.worldObj.a("explode", (d1 + this.posX * 1.0D) / 2.0D, (d2 + this.posY * 1.0D) / 2.0D, (d3 + this.posZ * 1.0D) / 2.0D, d4, d5, d6);
        this.worldObj.a("smoke", d1, d2, d3, d4, d5, d6);
      }
      
      if ((paramBoolean3) && (n > 0))
      {
        Block.byId[n].dropNaturally(this.worldObj, j, k, m, this.worldObj.getData(j, k, m), 0.3F, 0);
        this.worldObj.setTypeId(j, k, m, 0);
        Block.byId[n].wasExploded(this.worldObj, j, k, m);
      }
    }
  }
}
