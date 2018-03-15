package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;














public class BlockStep
  extends Block
{
  public static final String[] a = { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick" };
  
  private boolean b;
  

  public BlockStep(int paramInt, boolean paramBoolean)
  {
    super(paramInt, 6, Material.STONE);
    this.b = paramBoolean;
    
    if (!paramBoolean) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    } else {
      n[paramInt] = true;
    }
    f(255);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.b) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      int i = (paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) & 0x8) != 0 ? 1 : 0;
      if (i != 0) {
        a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
        a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
    }
  }
  
  public void f()
  {
    if (this.b) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList)
  {
    updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    int i = paramInt2 & 0x7;
    if (i == 0) {
      if (paramInt1 <= 1) return 6;
      return 5; }
    if (i == 1) {
      if (paramInt1 == 0) return 208;
      if (paramInt1 == 1) return 176;
      return 192; }
    if (i == 2)
      return 4;
    if (i == 3)
      return 16;
    if (i == 4)
      return Block.BRICK.textureId;
    if (i == 5) {
      return Block.SMOOTH_BRICK.textureId;
    }
    return 6;
  }
  
  public int a(int paramInt)
  {
    return a(paramInt, 0);
  }
  
  public boolean a() {
    return this.b;
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt4 == 0) && (!this.b)) {
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x7;
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x8);
    }
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Block.STEP.id;
  }
  
  public int a(Random paramRandom)
  {
    if (this.b) {
      return 2;
    }
    return 1;
  }
  
  protected int getDropData(int paramInt)
  {
    return paramInt & 0x7;
  }
  
  public boolean b() {
    return this.b;
  }
  
























  protected ItemStack a_(int paramInt)
  {
    return new ItemStack(Block.STEP.id, 1, paramInt & 0x7);
  }
}
