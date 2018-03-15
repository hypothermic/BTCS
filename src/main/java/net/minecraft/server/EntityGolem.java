package net.minecraft.server;


public abstract class EntityGolem
  extends EntityCreature
  implements IAnimal
{
  public EntityGolem(World paramWorld)
  {
    super(paramWorld);
  }
  
  protected void a(float paramFloat) {}
  
  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound) {
    super.a(paramNBTTagCompound);
  }
  
  protected String i() {
    return "none";
  }
  
  protected String j() {
    return "none";
  }
  
  protected String k() {
    return "none";
  }
  
  public int m() {
    return 120;
  }
  
  protected boolean n()
  {
    return false;
  }
}
