package net.minecraft.server;

import java.util.Random;




public class BlockCake
  extends Block
{
  protected BlockCake(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.CAKE);
    a(true);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
    float f1 = 0.0625F;
    float f2 = (1 + i * 2) / 16.0F;
    float f3 = 0.5F;
    a(f2, 0.0F, f1, 1.0F - f1, f3, 1.0F - f1);
  }
  
  public void f() {
    float f1 = 0.0625F;
    float f2 = 0.5F;
    a(f1, 0.0F, f1, 1.0F - f1, f2, 1.0F - f1);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    float f1 = 0.0625F;
    float f2 = (1 + i * 2) / 16.0F;
    float f3 = 0.5F;
    return AxisAlignedBB.b(paramInt1 + f2, paramInt2, paramInt3 + f1, paramInt1 + 1 - f1, paramInt2 + f3 - f1, paramInt3 + 1 - f1);
  }
  







  public int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1) return this.textureId;
    if (paramInt1 == 0) return this.textureId + 3;
    if ((paramInt2 > 0) && (paramInt1 == 4)) return this.textureId + 2;
    return this.textureId + 1;
  }
  
  public int a(int paramInt) {
    if (paramInt == 1) return this.textureId;
    if (paramInt == 0) return this.textureId + 3;
    return this.textureId + 1;
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean a() {
    return false;
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    c(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
    return true;
  }
  
  public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    c(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
  }
  
  private void c(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramEntityHuman.b(false)) {
      paramEntityHuman.getFoodData().eat(2, 0.1F);
      
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3) + 1;
      if (i >= 6) {
        paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
      } else {
        paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
        paramWorld.k(paramInt1, paramInt2, paramInt3);
      }
    }
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (!super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3)) { return false;
    }
    return f(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!f(paramWorld, paramInt1, paramInt2, paramInt3)) {
      b(paramWorld, paramInt1, paramInt2, paramInt3, paramWorld.getData(paramInt1, paramInt2, paramInt3), 0);
      paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
    }
  }
  
  public boolean f(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    return paramWorld.getMaterial(paramInt1, paramInt2 - 1, paramInt3).isBuildable();
  }
  
  public int a(Random paramRandom) {
    return 0;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return 0;
  }
}
