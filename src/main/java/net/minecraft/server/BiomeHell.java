package net.minecraft.server;

import java.util.List;

public class BiomeHell extends BiomeBase {
  public BiomeHell(int paramInt) {
    super(paramInt);
    
    this.J.clear();
    this.K.clear();
    this.L.clear();
    
    this.J.add(new BiomeMeta(EntityGhast.class, 50, 4, 4));
    this.J.add(new BiomeMeta(EntityPigZombie.class, 100, 4, 4));
    this.J.add(new BiomeMeta(EntityMagmaCube.class, 1, 4, 4));
  }
}
