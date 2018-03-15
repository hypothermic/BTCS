package net.minecraft.server;

import java.util.Random;

public class BlockObsidian extends BlockStone {
  public BlockObsidian(int paramInt1, int paramInt2) {
    super(paramInt1, paramInt2);
  }
  
  public int a(Random paramRandom) {
    return 1;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Block.OBSIDIAN.id;
  }
}
