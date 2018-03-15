package net.minecraft.server;

import java.util.Random;

public class BlockOre
  extends Block
{
  public BlockOre(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.STONE);
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    if (this.id == Block.COAL_ORE.id) return Item.COAL.id;
    if (this.id == Block.DIAMOND_ORE.id) return Item.DIAMOND.id;
    if (this.id == Block.LAPIS_ORE.id) return Item.INK_SACK.id;
    return this.id;
  }
  
  public int a(Random paramRandom) {
    if (this.id == Block.LAPIS_ORE.id) return 4 + paramRandom.nextInt(5);
    return 1;
  }
  
  public int getDropCount(int paramInt, Random paramRandom)
  {
    if ((paramInt > 0) && (this.id != getDropType(0, paramRandom, paramInt))) {
      int i = paramRandom.nextInt(paramInt + 2) - 1;
      if (i < 0) {
        i = 0;
      }
      return a(paramRandom) * (i + 1);
    }
    return a(paramRandom);
  }
  

  protected int getDropData(int paramInt)
  {
    if (this.id == Block.LAPIS_ORE.id) return 4;
    return 0;
  }
}
