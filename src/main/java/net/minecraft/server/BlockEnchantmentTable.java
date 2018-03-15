package net.minecraft.server;





public class BlockEnchantmentTable
  extends BlockContainer
{
  protected BlockEnchantmentTable(int paramInt)
  {
    super(paramInt, 166, Material.STONE);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
    f(0);
  }
  
  public boolean b()
  {
    return false;
  }
  





















  public boolean a()
  {
    return false;
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    return a(paramInt1);
  }
  
  public int a(int paramInt)
  {
    if (paramInt == 0) return this.textureId + 17;
    if (paramInt == 1) return this.textureId;
    return this.textureId + 16;
  }
  
  public TileEntity a_()
  {
    return new TileEntityEnchantTable();
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramWorld.isStatic) {
      return true;
    }
    paramEntityHuman.startEnchanting(paramInt1, paramInt2, paramInt3);
    return true;
  }
}
