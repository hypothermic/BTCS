package ee;

import ee.core.GuiIds;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.mod_EE;

public class TileTransTablet extends TileEE
{
  public int getTextureForSide(int var1)
  {
    return var1 == 1 ? EEBase.transTabletTop : var1 == 0 ? EEBase.transTabletBottom : EEBase.transTabletSide;
  }
  
  public int getInventoryTexture(int var1)
  {
    return var1 == 1 ? EEBase.transTabletTop : EEBase.transTabletSide;
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    if (!this.world.isStatic)
    {
      var1.openGui(mod_EE.getInstance(), GuiIds.TRANS_TABLE, this.world, this.x, this.y, this.z);
    }
    
    return true;
  }
}
