package ee;

public class TileDMBlock extends TileEE
{
  public boolean canUpdate()
  {
    return false;
  }
  
  public int getTextureForSide(int var1)
  {
    return EEBase.dmBlockSide;
  }
  
  public int getInventoryTexture(int var1)
  {
    return EEBase.dmBlockSide;
  }
}
