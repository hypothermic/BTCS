package net.minecraft.server;



public class BlockSlowSand
  extends Block
{
  public BlockSlowSand(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.SAND);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    float f = 0.125F;
    return AxisAlignedBB.b(paramInt1, paramInt2, paramInt3, paramInt1 + 1, paramInt2 + 1 - f, paramInt3 + 1);
  }
  




  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    paramEntity.motX *= 0.4D;
    paramEntity.motZ *= 0.4D;
  }
}
