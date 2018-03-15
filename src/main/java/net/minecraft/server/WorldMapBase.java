package net.minecraft.server;

public abstract class WorldMapBase
{
  public final String id;
  private boolean b;
  
  public WorldMapBase(String paramString)
  {
    this.id = paramString;
  }
  
  public abstract void a(NBTTagCompound paramNBTTagCompound);
  
  public abstract void b(NBTTagCompound paramNBTTagCompound);
  
  public void a() {
    a(true);
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean b() {
    return this.b;
  }
}
