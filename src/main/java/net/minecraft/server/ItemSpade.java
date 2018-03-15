package net.minecraft.server;

public class ItemSpade
  extends ItemTool
{
  private static Block[] bU = { Block.GRASS, Block.DIRT, Block.SAND, Block.GRAVEL, Block.SNOW, Block.SNOW_BLOCK, Block.CLAY, Block.SOIL, Block.SOUL_SAND, Block.MYCEL };
  

  public ItemSpade(int paramInt, EnumToolMaterial paramEnumToolMaterial)
  {
    super(paramInt, 1, paramEnumToolMaterial, bU);
  }
  
  public boolean canDestroySpecialBlock(Block paramBlock) {
    if (paramBlock == Block.SNOW) return true;
    if (paramBlock == Block.SNOW_BLOCK) return true;
    return false;
  }
}
