package ee;

public class TilePortalDevice extends TileEE
{
  public int getTextureForSide(int var1)
  {
    return var1 == 1 ? EEBase.portalDeviceTop : var1 == 0 ? EEBase.portalDeviceBottom : EEBase.portalDeviceSide;
  }
  
  public int getInventoryTexture(int var1)
  {
    return var1 == 1 ? EEBase.portalDeviceTop : EEBase.portalDeviceSide;
  }
}
