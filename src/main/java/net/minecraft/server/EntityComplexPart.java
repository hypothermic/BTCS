package net.minecraft.server;


public class EntityComplexPart
  extends Entity
{
  public final EntityComplex owner;
  
  public final String b;
  
  public EntityComplexPart(EntityComplex paramEntityComplex, String paramString, float paramFloat1, float paramFloat2)
  {
    super(paramEntityComplex.world);
    b(paramFloat1, paramFloat2);
    this.owner = paramEntityComplex;
    this.b = paramString;
  }
  


  protected void b() {}
  

  protected void a(NBTTagCompound paramNBTTagCompound) {}
  

  protected void b(NBTTagCompound paramNBTTagCompound) {}
  

  public boolean o_()
  {
    return true;
  }
  
  public boolean damageEntity(DamageSource paramDamageSource, int paramInt) {
    return this.owner.a(this, paramDamageSource, paramInt);
  }
  
  public boolean a_(Entity paramEntity) {
    return (this == paramEntity) || (this.owner == paramEntity);
  }
}
