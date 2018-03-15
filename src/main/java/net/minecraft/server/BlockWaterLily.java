package net.minecraft.server;






public class BlockWaterLily
  extends BlockFlower
{
  protected BlockWaterLily(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
    float f1 = 0.5F;
    float f2 = 0.015625F;
    a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
  }
  
  public int c() {
    return 23;
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return AxisAlignedBB.b(paramInt1 + this.minX, paramInt2 + this.minY, paramInt3 + this.minZ, paramInt1 + this.maxX, paramInt2 + this.maxY, paramInt3 + this.maxZ);
  }
  









  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  





  protected boolean d(int paramInt)
  {
    return paramInt == Block.STATIONARY_WATER.id;
  }
  
  public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if ((paramInt2 < 0) || (paramInt2 >= 256)) return false;
    return (paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3) == Material.WATER) && (paramWorld.getData(paramInt1, paramInt2 - 1, paramInt3) == 0);
  }
}
