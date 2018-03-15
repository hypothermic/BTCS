package net.minecraft.server;

import java.util.List;

public abstract interface ICrafting
{
  public abstract void a(Container paramContainer, List paramList);
  
  public abstract void a(Container paramContainer, int paramInt, ItemStack paramItemStack);
  
  public abstract void setContainerData(Container paramContainer, int paramInt1, int paramInt2);
}
