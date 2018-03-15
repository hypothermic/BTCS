package net.minecraft.server;



public class BlockCloth
  extends Block
{
  public BlockCloth()
  {
    super(35, 64, Material.CLOTH);
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
    {
      return this.textureId;
    }
    

    paramInt2 = paramInt2 & 0xF ^ 0xFFFFFFFF;
    return 113 + ((paramInt2 & 0x8) >> 3) + (paramInt2 & 0x7) * 16;
  }
  
  protected int getDropData(int paramInt)
  {
    return paramInt;
  }
  
  public static int d(int paramInt) {
    return (paramInt ^ 0xFFFFFFFF) & 0xF;
  }
  
  public static int e(int paramInt) {
    return (paramInt ^ 0xFFFFFFFF) & 0xF;
  }
}
