package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;







public class BlockStairs
  extends Block
{
  private Block a;
  
  protected BlockStairs(int paramInt, Block paramBlock)
  {
    super(paramInt, paramBlock.textureId, paramBlock.material);
    this.a = paramBlock;
    c(paramBlock.strength);
    b(paramBlock.durability / 3.0F);
    a(paramBlock.stepSound);
    f(255);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    return super.e(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean b() {
    return false;
  }
  
  public int c() {
    return 10;
  }
  




  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList)
  {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    int j = i & 0x3;
    float f1 = 0.0F;
    float f2 = 0.5F;
    float f3 = 0.5F;
    float f4 = 1.0F;
    
    if ((i & 0x4) != 0) {
      f1 = 0.5F;
      f2 = 1.0F;
      f3 = 0.0F;
      f4 = 0.5F;
    }
    
    a(0.0F, f1, 0.0F, 1.0F, f2, 1.0F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    
    if (j == 0) {
      a(0.5F, f3, 0.0F, 1.0F, f4, 1.0F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    } else if (j == 1) {
      a(0.0F, f3, 0.0F, 0.5F, f4, 1.0F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    } else if (j == 2) {
      a(0.0F, f3, 0.5F, 1.0F, f4, 1.0F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    } else if (j == 3) {
      a(0.0F, f3, 0.0F, 1.0F, f4, 0.5F);
      super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    }
    
    a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  







































  public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    this.a.attack(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
  }
  
  public void postBreak(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.a.postBreak(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  







  public float a(Entity paramEntity)
  {
    return this.a.a(paramEntity);
  }
  




  public int a(int paramInt1, int paramInt2)
  {
    return this.a.a(paramInt1, 0);
  }
  
  public int a(int paramInt)
  {
    return this.a.a(paramInt, 0);
  }
  
  public int d() {
    return this.a.d();
  }
  



  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D)
  {
    this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity, paramVec3D);
  }
  
  public boolean E_() {
    return this.a.E_();
  }
  
  public boolean a(int paramInt, boolean paramBoolean) {
    return this.a.a(paramInt, paramBoolean);
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    return this.a.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, 0);
    this.a.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    this.a.remove(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  



  public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    this.a.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
    this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    return this.a.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
  }
  
  public void wasExploded(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    this.a.wasExploded(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
    int i = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    int j = paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x4;
    
    if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x2 | j);
    if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x1 | j);
    if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x3 | j);
    if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x0 | j);
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt4 == 0) {
      int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
      paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x4);
    }
  }
}
