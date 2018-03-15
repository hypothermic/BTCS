package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;





public class BlockEnderPortal
  extends BlockContainer
{
  public static boolean a = false;
  
  protected BlockEnderPortal(int paramInt, Material paramMaterial) {
    super(paramInt, 0, paramMaterial);
    a(1.0F);
  }
  
  public TileEntity a_() {
    return new TileEntityEnderPortal();
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    float f = 0.0625F;
    a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
  }
  




  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList) {}
  



  public boolean a()
  {
    return false;
  }
  
  public boolean b() {
    return false;
  }
  
  public int a(Random paramRandom) {
    return 0;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
    if ((paramEntity.vehicle == null) && (paramEntity.passenger == null) && 
      ((paramEntity instanceof EntityHuman)) && 
      (!paramWorld.isStatic)) {
      ((EntityHuman)paramEntity).e(1);
    }
  }
  













  public int c()
  {
    return -1;
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (a) { return;
    }
    if (paramWorld.worldProvider.dimension != 0) {
      paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
      return;
    }
  }
}
