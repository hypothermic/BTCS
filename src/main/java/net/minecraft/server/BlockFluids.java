package net.minecraft.server;

import java.util.Random;




public abstract class BlockFluids
  extends Block
{
  protected BlockFluids(int paramInt, Material paramMaterial)
  {
    super(paramInt, (paramMaterial == Material.LAVA ? 14 : 12) * 16 + 13, paramMaterial);
    float f1 = 0.0F;
    float f2 = 0.0F;
    
    a(0.0F + f2, 0.0F + f1, 0.0F + f2, 1.0F + f2, 1.0F + f1, 1.0F + f2);
    a(true);
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    return this.material != Material.LAVA;
  }
  





























  public static float d(int paramInt)
  {
    if (paramInt >= 8) paramInt = 0;
    float f = (paramInt + 1) / 9.0F;
    return f;
  }
  
  public int a(int paramInt) {
    if ((paramInt == 0) || (paramInt == 1)) {
      return this.textureId;
    }
    return this.textureId + 1;
  }
  
  protected int g(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.getMaterial(paramInt1, paramInt2, paramInt3) != this.material) return -1;
    return paramWorld.getData(paramInt1, paramInt2, paramInt3);
  }
  
  protected int c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    if (paramIBlockAccess.getMaterial(paramInt1, paramInt2, paramInt3) != this.material) return -1;
    int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
    if (i >= 8) i = 0;
    return i;
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean a() {
    return false;
  }
  
  public boolean a(int paramInt, boolean paramBoolean) {
    return (paramBoolean) && (paramInt == 0);
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Material localMaterial = paramIBlockAccess.getMaterial(paramInt1, paramInt2, paramInt3);
    if (localMaterial == this.material) return false;
    if (paramInt4 == 1) return true;
    if (localMaterial == Material.ICE) return false;
    return super.b(paramIBlockAccess, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  









  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }
  
  public int c() {
    return 4;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return 0;
  }
  
  public int a(Random paramRandom) {
    return 0;
  }
  
  private Vec3D d(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    Vec3D localVec3D = Vec3D.create(0.0D, 0.0D, 0.0D);
    int i = c(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
    int j; // BTCS
    for (j = 0; j < 4; j++)
    {
      int k = paramInt1;
      int m = paramInt2;
      int n = paramInt3;
      
      if (j == 0) k--;
      if (j == 1) n--;
      if (j == 2) k++;
      if (j == 3) { n++;
      }
      int i1 = c(paramIBlockAccess, k, m, n);
      int i2; if (i1 < 0) {
        if (!paramIBlockAccess.getMaterial(k, m, n).isSolid()) {
          i1 = c(paramIBlockAccess, k, m - 1, n);
          if (i1 >= 0) {
            i2 = i1 - (i - 8);
            localVec3D = localVec3D.add((k - paramInt1) * i2, (m - paramInt2) * i2, (n - paramInt3) * i2);
          }
        }
      }
      else if (i1 >= 0) {
        i2 = i1 - i;
        localVec3D = localVec3D.add((k - paramInt1) * i2, (m - paramInt2) * i2, (n - paramInt3) * i2);
      }
    }
    

    if (paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) >= 8) {
      j = 0;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, 2))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, 3))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, 4))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, 5))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1, paramInt2 + 1, paramInt3 - 1, 2))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1, paramInt2 + 1, paramInt3 + 1, 3))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1 - 1, paramInt2 + 1, paramInt3, 4))) j = 1;
      if ((j != 0) || (b(paramIBlockAccess, paramInt1 + 1, paramInt2 + 1, paramInt3, 5))) j = 1;
      if (j != 0) localVec3D = localVec3D.b().add(0.0D, -6.0D, 0.0D);
    }
    localVec3D = localVec3D.b();
    return localVec3D;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D) {
    Vec3D localVec3D = d(paramWorld, paramInt1, paramInt2, paramInt3);
    paramVec3D.a += localVec3D.a;
    paramVec3D.b += localVec3D.b;
    paramVec3D.c += localVec3D.c;
  }
  
  public int d() {
    if (this.material == Material.WATER) return 5;
    if (this.material == Material.LAVA) return 30;
    return 0;
  }
  

















  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom)
  {
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
  }
  




















































































  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    i(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    i(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  private void i(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (paramWorld.getTypeId(paramInt1, paramInt2, paramInt3) != this.id) return;
    if (this.material == Material.LAVA) {
      int i = 0;
      if ((i != 0) || (paramWorld.getMaterial(paramInt1, paramInt2, paramInt3 - 1) == Material.WATER)) i = 1;
      if ((i != 0) || (paramWorld.getMaterial(paramInt1, paramInt2, paramInt3 + 1) == Material.WATER)) i = 1;
      if ((i != 0) || (paramWorld.getMaterial(paramInt1 - 1, paramInt2, paramInt3) == Material.WATER)) i = 1;
      if ((i != 0) || (paramWorld.getMaterial(paramInt1 + 1, paramInt2, paramInt3) == Material.WATER)) { i = 1;
      }
      
      if ((i != 0) || (paramWorld.getMaterial(paramInt1, paramInt2 + 1, paramInt3) == Material.WATER)) i = 1;
      if (i != 0) {
        int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
        if (j == 0) {
          paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.OBSIDIAN.id);
        } else if (j <= 4) {
          paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.COBBLESTONE.id);
        }
        fizz(paramWorld, paramInt1, paramInt2, paramInt3);
      }
    }
  }
  
  protected void fizz(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    paramWorld.makeSound(paramInt1 + 0.5F, paramInt2 + 0.5F, paramInt3 + 0.5F, "random.fizz", 0.5F, 2.6F + (paramWorld.random.nextFloat() - paramWorld.random.nextFloat()) * 0.8F);
    for (int i = 0; i < 8; i++) {
      paramWorld.a("largesmoke", paramInt1 + Math.random(), paramInt2 + 1.2D, paramInt3 + Math.random(), 0.0D, 0.0D, 0.0D);
    }
  }
}
