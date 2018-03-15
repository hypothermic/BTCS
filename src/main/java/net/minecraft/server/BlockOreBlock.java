package net.minecraft.server;

public class BlockOreBlock extends Block
{
  public BlockOreBlock(int paramInt1, int paramInt2)
  {
    super(paramInt1, Material.ORE);
    this.textureId = paramInt2;
  }
  
  public int a(int paramInt) {
    return this.textureId;
  }
}
