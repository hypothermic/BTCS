package net.minecraft.server;






public class BlockWood
  extends Block
{
  public BlockWood(int paramInt)
  {
    super(paramInt, 4, Material.WOOD);
  }
  
  public int a(int paramInt1, int paramInt2)
  {
    switch (paramInt2) {
    default: 
      return 4;
    case 1: 
      return 198;
    case 2: 
      return 214;
    }
    //return 199; // BTCS comment: unreachable code (hello minecraft devs bicc bois?!)
  }
  

  protected int getDropData(int paramInt)
  {
    return paramInt;
  }
}
