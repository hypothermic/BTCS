package forge;

import net.minecraft.server.Entity;

public abstract interface IThrowableEntity
{
  public abstract Entity getThrower();
  
  public abstract void setThrower(Entity paramEntity);
}
