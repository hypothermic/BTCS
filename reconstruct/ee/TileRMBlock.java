package ee;

public class TileRMBlock extends TileEE
{
  public boolean canUpdate()
  {
    return false;
  }
  
  public int getTextureForSide(int var1)
  {
    return EEBase.rmBlockSide;
  }
  
  public int getInventoryTexture(int var1)
  {
    return EEBase.rmBlockSide;
  }
}
