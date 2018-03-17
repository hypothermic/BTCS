package net.minecraft.server;

public class WM_WeaponDamageSource extends EntityDamageSourceIndirect
{
  private Entity damageSourceProjectile;
  private Entity a;
  
  public WM_WeaponDamageSource(String paramString, Entity paramEntity1, Entity paramEntity2)
  {
    super(paramString, paramEntity1, paramEntity2);
    this.damageSourceProjectile = paramEntity1;
    this.a = paramEntity2;
  }
  
  public Entity getProjectile()
  {
    return this.damageSourceProjectile;
  }
  
  public Entity getEntity()
  {
    return this.a;
  }
  
  public static DamageSource causeWeaponDamage(Entity paramEntity1, Entity paramEntity2)
  {
    return new WM_WeaponDamageSource("weapon", paramEntity1, paramEntity2).d();
  }
}
