package net.minecraft.server;



public class BlockSponge
  extends Block
{
  protected BlockSponge(int paramInt)
  {
    super(paramInt, Material.SPONGE);
    this.textureId = 48;
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
}
