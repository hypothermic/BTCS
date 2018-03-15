package net.minecraft.server;

public class BlockSmoothBrick extends Block
{
  public BlockSmoothBrick(int paramInt)
  {
    super(paramInt, 54, Material.STONE);
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    switch (paramInt2) {
    default: 
      return 54;
    case 1: 
      return 100;
    case 2: 
      return 101;
    }
    //return 213; // BTCS comment: unreachable code (Minecraft devs LUL)
  }
  

  protected int getDropData(int paramInt)
  {
    return paramInt;
  }
}
