package net.minecraft.server;

import forge.DimensionManager;


public abstract class WorldProvider
{
  public World a;
  public WorldType type;
  public WorldChunkManager c;
  public boolean d = false;
  public boolean e = false;
  public float[] f = new float[16];
  public int dimension = 0;
  private float[] h = new float[4];
  

  public final void a(World world)
  {
    this.a = world;
    this.type = world.getWorldData().getType();
    a();
    g();
  }
  
  protected void g() {
    float f = 0.0F;
    
    for (int i = 0; i <= 15; i++) {
      float f1 = 1.0F - i / 15.0F;
      
      this.f[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
    }
  }
  
  protected void a() {
    this.c = this.a.getWorldData().getType().getChunkManager(this.a);
  }
  
  public IChunkProvider getChunkProvider() {
    return this.type.getChunkGenerator(this.a);
  }
  
  public boolean canSpawn(int i, int j) {
	nl.hypothermic.btcs.XLogger.debug("---- BTCS: WorldProvider.canSpawn - 100");
    int k = this.a.b(i, j);
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: WorldProvider.canSpawn - 200");
    nl.hypothermic.btcs.XLogger.debug("---- BTCS: WorldProvider.canSpawn - Block.GRASS.id = " + Block.GRASS.id);
    return k == Block.GRASS.id;
  }
  
  public float a(long i, float f) {
    int j = (int)(i % 24000L);
    float f1 = (j + f) / 24000.0F - 0.25F;
    
    if (f1 < 0.0F) {
      f1 += 1.0F;
    }
    
    if (f1 > 1.0F) {
      f1 -= 1.0F;
    }
    
    float f2 = f1;
    
    f1 = 1.0F - (float)((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
    f1 = f2 + (f1 - f2) / 3.0F;
    return f1;
  }
  
  public boolean d() {
    return true;
  }
  
  /** canRespawnHere() */
  public boolean c() {
    return true;
  }
  
  public static WorldProvider byDimension(int i) {
    return DimensionManager.createProviderFor(i);
  }
  
  public ChunkCoordinates e() {
    return null;
  }
  
  public int getSeaLevel() {
    return this.type.getMinimumSpawnHeight(this.a);
  }

  public abstract String getSaveFolder();

  public abstract String getWelcomeMessage();

  public abstract String getDepartMessage();

  public double getMovementFactor()
  {
    return 1.0D;
  }
}
