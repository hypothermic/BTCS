package net.minecraft.server;

import java.util.Random;

public class BlockHugeMushroom extends Block
{
  private int a;
  
  public BlockHugeMushroom(int paramInt1, Material paramMaterial, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2, paramMaterial);
    this.a = paramInt3;
  }
  



  public int a(int paramInt1, int paramInt2)
  {
    if ((paramInt2 == 10) && (paramInt1 > 1)) return this.textureId - 1;
    if ((paramInt2 >= 1) && (paramInt2 <= 9) && (paramInt1 == 1)) return this.textureId - 16 - this.a;
    if ((paramInt2 >= 1) && (paramInt2 <= 3) && (paramInt1 == 2)) return this.textureId - 16 - this.a;
    if ((paramInt2 >= 7) && (paramInt2 <= 9) && (paramInt1 == 3)) { return this.textureId - 16 - this.a;
    }
    if (((paramInt2 == 1) || (paramInt2 == 4) || (paramInt2 == 7)) && (paramInt1 == 4)) return this.textureId - 16 - this.a;
    if (((paramInt2 == 3) || (paramInt2 == 6) || (paramInt2 == 9)) && (paramInt1 == 5)) { return this.textureId - 16 - this.a;
    }
    
    if (paramInt2 == 14) {
      return this.textureId - 16 - this.a;
    }
    if (paramInt2 == 15) {
      return this.textureId - 1;
    }
    
    return this.textureId;
  }
  
  public int a(Random paramRandom) {
    int i = paramRandom.nextInt(10) - 7;
    if (i < 0) i = 0;
    return i;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Block.BROWN_MUSHROOM.id + this.a;
  }
}
