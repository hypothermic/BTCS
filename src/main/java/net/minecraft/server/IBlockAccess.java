package net.minecraft.server;

public abstract interface IBlockAccess
{
  public abstract int getTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getData(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Material getMaterial(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean e(int paramInt1, int paramInt2, int paramInt3);
}
