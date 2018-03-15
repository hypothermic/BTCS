package net.minecraft.server;

import java.util.Random;

public class BlockBookshelf
  extends Block
{
  public BlockBookshelf(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.WOOD);
  }
  
  public int a(int paramInt) {
    if (paramInt <= 1) return 4;
    return this.textureId;
  }
  
  public int a(Random paramRandom) {
    return 3;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.BOOK.id;
  }
}
