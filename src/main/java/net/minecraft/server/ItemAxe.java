package net.minecraft.server;


public class ItemAxe
  extends ItemTool
{
  private static Block[] bU = { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.DOUBLE_STEP, Block.STEP, Block.PUMPKIN, Block.JACK_O_LANTERN };
  

  protected ItemAxe(int paramInt, EnumToolMaterial paramEnumToolMaterial)
  {
    super(paramInt, 3, paramEnumToolMaterial, bU);
  }
  
  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    if ((paramBlock != null) && (paramBlock.material == Material.WOOD)) {
      return this.a;
    }
    return super.getDestroySpeed(paramItemStack, paramBlock);
  }
}
