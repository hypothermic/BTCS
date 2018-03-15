package org.bukkit.block;

public abstract interface Sign
  extends BlockState
{
  public abstract String[] getLines();
  
  public abstract String getLine(int paramInt)
    throws IndexOutOfBoundsException;
  
  public abstract void setLine(int paramInt, String paramString)
    throws IndexOutOfBoundsException;
}
