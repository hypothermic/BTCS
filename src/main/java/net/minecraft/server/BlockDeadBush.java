package net.minecraft.server;

import java.util.Random;





public class BlockDeadBush
  extends BlockFlower
{
  protected BlockDeadBush(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.REPLACEABLE_PLANT);
    float f = 0.4F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
  }
  
  protected boolean d(int paramInt) {
    return paramInt == Block.SAND.id;
  }
  
  public int a(int paramInt1, int paramInt2) {
    return this.textureId;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return -1;
  }
  
  public void a(World paramWorld, EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!paramWorld.isStatic) && (paramEntityHuman.U() != null) && (paramEntityHuman.U().id == Item.SHEARS.id)) {
      paramEntityHuman.a(StatisticList.C[this.id], 1);
      

      a(paramWorld, paramInt1, paramInt2, paramInt3, new ItemStack(Block.DEAD_BUSH, 1, paramInt4));
    } else {
      super.a(paramWorld, paramEntityHuman, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
}
