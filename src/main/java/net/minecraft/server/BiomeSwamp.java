package net.minecraft.server;

import java.util.Random;

public class BiomeSwamp
  extends BiomeBase
{
  protected BiomeSwamp(int paramInt)
  {
    super(paramInt);
    this.I.z = 2;
    this.I.A = 64537;
    this.I.C = 1;
    this.I.D = 8;
    this.I.E = 10;
    this.I.I = 1;
    this.I.y = 4;
    
    this.H = 14745518;
  }
  
  public WorldGenerator a(Random paramRandom)
  {
    return this.Q;
  }
}
