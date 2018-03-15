package net.minecraft.server;

public class BlockTransparant
  extends Block
{
  protected boolean b;
  
  protected BlockTransparant(int paramInt1, int paramInt2, Material paramMaterial, boolean paramBoolean)
  {
    super(paramInt1, paramInt2, paramMaterial);
    this.b = paramBoolean;
  }
  
  public boolean a() {
    return false;
  }
}
