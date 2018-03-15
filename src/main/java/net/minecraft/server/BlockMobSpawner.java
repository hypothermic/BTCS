package net.minecraft.server;

import java.util.Random;

public class BlockMobSpawner
  extends BlockContainer
{
  protected BlockMobSpawner(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.STONE);
  }
  
  public TileEntity a_() {
    return new TileEntityMobSpawner();
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return 0;
  }
  
  public int a(Random paramRandom) {
    return 0;
  }
  
  public boolean a() {
    return false;
  }
}
