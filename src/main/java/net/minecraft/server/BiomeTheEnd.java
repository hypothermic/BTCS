package net.minecraft.server;

import java.util.List;

public class BiomeTheEnd extends BiomeBase
{
  public BiomeTheEnd(int paramInt) {
    super(paramInt);
    
    this.J.clear();
    this.K.clear();
    this.L.clear();
    
    this.J.add(new BiomeMeta(EntityEnderman.class, 10, 4, 4));
    this.A = ((byte)Block.DIRT.id);
    this.B = ((byte)Block.DIRT.id);
    
    this.I = new BiomeTheEndDecorator(this);
  }
}
