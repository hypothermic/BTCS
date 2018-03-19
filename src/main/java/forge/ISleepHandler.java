package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EnumBedResult;

public abstract interface ISleepHandler
{
  public abstract EnumBedResult sleepInBedAt(EntityHuman paramEntityHuman, int paramInt1, int paramInt2, int paramInt3);
}
