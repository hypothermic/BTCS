package net.minecraft.server;










public class BlockSandStone
  extends Block
{
  public BlockSandStone(int paramInt)
  {
    super(paramInt, 192, Material.STONE);
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 1) || ((paramInt1 == 0) && ((paramInt2 == 1) || (paramInt2 == 2)))) {
      return 176;
    }
    if (paramInt1 == 0) {
      return 208;
    }
    if (paramInt2 == 1) {
      return 229;
    }
    if (paramInt2 == 2) {
      return 230;
    }
    return 192;
  }
  
  public int a(int paramInt)
  {
    if (paramInt == 1) {
      return this.textureId - 16;
    }
    if (paramInt == 0) {
      return this.textureId + 16;
    }
    return this.textureId;
  }
  
  protected int getDropData(int paramInt)
  {
    return paramInt;
  }
}
