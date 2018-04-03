package cpw.mods.fml.common;

import java.util.Random;

public abstract interface IWorldGenerator
{
  public abstract void generate(Random paramRandom, int paramInt1, int paramInt2, Object... paramVarArgs);
}
