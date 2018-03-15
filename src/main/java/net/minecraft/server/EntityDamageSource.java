package net.minecraft.server;


public class EntityDamageSource
  extends DamageSource
{
  protected Entity a;
  
  public EntityDamageSource(String paramString, Entity paramEntity)
  {
    super(paramString);
    this.a = paramEntity;
  }
  
  public Entity getEntity() {
    return this.a;
  }
  
  public String getLocalizedDeathMessage(EntityHuman paramEntityHuman) {
    return LocaleI18n.get("death." + this.translationIndex, new Object[] { paramEntityHuman.name, this.a.getLocalizedName() });
  }
}
