package org.bukkit.permissions;

public abstract interface ServerOperator
{
  public abstract boolean isOp();
  
  public abstract void setOp(boolean paramBoolean);
}
