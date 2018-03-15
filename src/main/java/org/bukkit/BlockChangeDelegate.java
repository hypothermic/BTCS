package org.bukkit;

public abstract interface BlockChangeDelegate
{
  public abstract boolean setRawTypeId(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract boolean setRawTypeIdAndData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract boolean setTypeId(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract boolean setTypeIdAndData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract int getTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getHeight();
  
  public abstract boolean isEmpty(int paramInt1, int paramInt2, int paramInt3);
}
