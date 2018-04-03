package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.World;

public abstract interface IGuiHandler
{
  public abstract Object getGuiElement(int paramInt1, EntityHuman paramEntityHuman, World paramWorld, int paramInt2, int paramInt3, int paramInt4);
}
