package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;







public class BlockEnderPortalFrame
  extends Block
{
  public BlockEnderPortalFrame(int paramInt)
  {
    super(paramInt, 159, Material.SHATTERABLE);
  }
  



  public int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1) {
      return this.textureId - 1;
    }
    if (paramInt1 == 0) {
      return this.textureId + 16;
    }
    return this.textureId;
  }
  
  public boolean a()
  {
    return false;
  }
  
  public int c()
  {
    return 26;
  }
  
  public void f()
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList)
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    if (d(i)) {
      a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    }
    f();
  }
  
  public static boolean d(int paramInt) {
    return (paramInt & 0x4) != 0;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return 0;
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving)
  {
    int i = ((MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
  }
}
