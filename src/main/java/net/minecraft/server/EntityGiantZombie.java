package net.minecraft.server;

public class EntityGiantZombie extends EntityMonster
{
  public EntityGiantZombie(World paramWorld)
  {
    super(paramWorld);
    this.texture = "/mob/zombie.png";
    this.bb = 0.5F;
    this.damage = 50;
    this.height *= 6.0F;
    b(this.width * 6.0F, this.length * 6.0F);
  }
  
  public int getMaxHealth()
  {
    return 100;
  }
  
  public float a(int paramInt1, int paramInt2, int paramInt3) {
    return this.world.p(paramInt1, paramInt2, paramInt3) - 0.5F;
  }
}
