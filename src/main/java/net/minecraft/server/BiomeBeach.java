package net.minecraft.server;

import java.util.List;

public class BiomeBeach extends BiomeBase
{
  public BiomeBeach(int paramInt) {
    super(paramInt);
    

    this.K.clear();
    this.A = ((byte)Block.SAND.id);
    this.B = ((byte)Block.SAND.id);
    
    this.I.z = 64537;
    this.I.C = 0;
    this.I.E = 0;
    this.I.F = 0;
  }
}
