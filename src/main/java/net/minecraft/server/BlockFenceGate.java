package net.minecraft.server;









public class BlockFenceGate
  extends BlockDirectional
{
  public BlockFenceGate(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.WOOD);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (!paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3).isBuildable()) return false;
    return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (d(i)) {
      return null;
    }
    if ((i == 2) || (i == 0)) {
      return AxisAlignedBB.b(paramInt1, paramInt2, paramInt3 + 0.375F, paramInt1 + 1, paramInt2 + 1.5F, paramInt3 + 0.625F);
    }
    return AxisAlignedBB.b(paramInt1 + 0.375F, paramInt2, paramInt3, paramInt1 + 0.625F, paramInt2 + 1.5F, paramInt3 + 1);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
    if ((i == 2) || (i == 0)) {
      a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
    } else {
      a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
    }
  }
  



  public boolean a()
  {
    return false;
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    return d(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
  }
  
  public int c() {
    return 21;
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving)
  {
    int i = (MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3) % 4;
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (d(i)) {
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFFB);
    }
    else {
      int j = (MathHelper.floor(paramEntityHuman.yaw * 4.0F / 360.0F + 0.5D) & 0x3) % 4;
      int k = b(i);
      if (k == (j + 2) % 4) {
        i = j;
      }
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x4);
    }
    paramWorld.a(paramEntityHuman, 1003, paramInt1, paramInt2, paramInt3, 0);
    return true;
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramWorld.isStatic) { return;
    }
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    boolean bool = paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3);
    if ((bool) || ((paramInt4 > 0) && (Block.byId[paramInt4].isPowerSource())) || (paramInt4 == 0)) {
      if ((bool) && (!d(i))) {
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x4);
        paramWorld.a(null, 1003, paramInt1, paramInt2, paramInt3, 0);
      } else if ((!bool) && (d(i))) {
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFFB);
        paramWorld.a(null, 1003, paramInt1, paramInt2, paramInt3, 0);
      }
    }
  }
  
  public static boolean d(int paramInt) {
    return (paramInt & 0x4) != 0;
  }
}
