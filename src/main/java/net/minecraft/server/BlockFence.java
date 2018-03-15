package net.minecraft.server;


public class BlockFence
  extends Block
{
  public BlockFence(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.WOOD);
  }
  
  public BlockFence(int paramInt1, int paramInt2, Material paramMaterial) {
    super(paramInt1, paramInt2, paramMaterial);
  }
  

  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = c(paramWorld, paramInt1, paramInt2, paramInt3 - 1);
    boolean bool2 = c(paramWorld, paramInt1, paramInt2, paramInt3 + 1);
    boolean bool3 = c(paramWorld, paramInt1 - 1, paramInt2, paramInt3);
    boolean bool4 = c(paramWorld, paramInt1 + 1, paramInt2, paramInt3);
    
    float f1 = 0.375F;
    float f2 = 0.625F;
    float f3 = 0.375F;
    float f4 = 0.625F;
    
    if (bool1) {
      f3 = 0.0F;
    }
    if (bool2) {
      f4 = 1.0F;
    }
    if (bool3) {
      f1 = 0.0F;
    }
    if (bool4) {
      f2 = 1.0F;
    }
    
    return AxisAlignedBB.b(paramInt1 + f1, paramInt2, paramInt3 + f3, paramInt1 + f2, paramInt2 + 1.5F, paramInt3 + f4);
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = c(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1);
    boolean bool2 = c(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1);
    boolean bool3 = c(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3);
    boolean bool4 = c(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3);
    
    float f1 = 0.375F;
    float f2 = 0.625F;
    float f3 = 0.375F;
    float f4 = 0.625F;
    
    if (bool1) {
      f3 = 0.0F;
    }
    if (bool2) {
      f4 = 1.0F;
    }
    if (bool3) {
      f1 = 0.0F;
    }
    if (bool4) {
      f2 = 1.0F;
    }
    
    a(f1, 0.0F, f3, f2, 1.0F, f4);
  }
  



  public boolean a()
  {
    return false;
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    return false;
  }
  
  public int c() {
    return 11;
  }
  
  public boolean c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramIBlockAccess.getTypeId(paramInt1, paramInt2, paramInt3);
    if ((i == this.id) || (i == Block.FENCE_GATE.id)) {
      return true;
    }
    Block localBlock = Block.byId[i];
    if ((localBlock != null) && 
      (localBlock.material.j()) && (localBlock.b())) {
      return localBlock.material != Material.PUMPKIN;
    }
    
    return false;
  }
}
