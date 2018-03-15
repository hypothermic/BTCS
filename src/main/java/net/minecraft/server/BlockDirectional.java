package net.minecraft.server;




public abstract class BlockDirectional
  extends Block
{
  protected BlockDirectional(int paramInt1, int paramInt2, Material paramMaterial)
  {
    super(paramInt1, paramInt2, paramMaterial);
  }
  
  protected BlockDirectional(int paramInt, Material paramMaterial) {
    super(paramInt, paramMaterial);
  }
  
  public static int b(int paramInt) {
    return paramInt & 0x3;
  }
}
