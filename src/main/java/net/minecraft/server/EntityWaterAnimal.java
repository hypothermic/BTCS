package net.minecraft.server;

import java.util.Random;











public abstract class EntityWaterAnimal
  extends EntityCreature
  implements IAnimal
{
  public EntityWaterAnimal(World paramWorld)
  {
    super(paramWorld);
  }
  

  public boolean f_()
  {
    return true;
  }
  
  public void b(NBTTagCompound paramNBTTagCompound) {
    super.b(paramNBTTagCompound);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound) {
    super.a(paramNBTTagCompound);
  }
  
  public boolean canSpawn() {
    return this.world.containsEntity(this.boundingBox);
  }
  
  public int m() {
    return 120;
  }
  
  protected boolean n() {
    return true;
  }
  
  protected int getExpValue(EntityHuman paramEntityHuman)
  {
    return 1 + this.world.random.nextInt(3);
  }
}
