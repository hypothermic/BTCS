package net.minecraft.server;


public class BlockWorkbench
  extends Block
{
  protected BlockWorkbench(int paramInt)
  {
    super(paramInt, Material.WOOD);
    this.textureId = 59;
  }
  
  public int a(int paramInt) {
    if (paramInt == 1) return this.textureId - 16;
    if (paramInt == 0) return Block.WOOD.a(0);
    if ((paramInt == 2) || (paramInt == 4)) return this.textureId + 1;
    return this.textureId;
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramWorld.isStatic) {
      return true;
    }
    paramEntityHuman.startCrafting(paramInt1, paramInt2, paramInt3);
    return true;
  }
}
