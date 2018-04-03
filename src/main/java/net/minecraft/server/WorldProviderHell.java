package net.minecraft.server;

public class WorldProviderHell extends WorldProvider {
  public void a()
  {
    this.c = new WorldChunkManagerHell(BiomeBase.HELL, 1.0F, 0.0F);
    this.d = true;
    this.e = true;
    this.dimension = -1;
  }
  
  protected void g() {
    float f = 0.1F;
    
    for (int i = 0; i <= 15; i++) {
      float f1 = 1.0F - i / 15.0F;
      
      this.f[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
    }
  }
  
  public IChunkProvider getChunkProvider() {
    return new ChunkProviderHell(this.a, this.a.getSeed());
  }
  
  public boolean d() {
    return false;
  }
  
  public boolean canSpawn(int i, int j) {
    return false;
  }
  
  public float a(long i, float f) {
    return 0.5F;
  }
  
  public boolean c() {
    return false;
  }
  

  public String getSaveFolder()
  {
    return "DIM-1";
  }
  

  public String getWelcomeMessage()
  {
    return "Entering the Nether";
  }
  

  public String getDepartMessage()
  {
    return "Leaving the Nether";
  }
  

  public double getMovementFactor()
  {
    return 8.0D;
  }
}
