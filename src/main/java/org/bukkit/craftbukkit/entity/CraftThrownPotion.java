package org.bukkit.craftbukkit.entity;

import java.util.Collection;
import net.minecraft.server.EntityPotion;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.PotionEffect;

public class CraftThrownPotion
  extends CraftProjectile implements ThrownPotion
{
  private Collection<PotionEffect> effects = null;
  
  public CraftThrownPotion(CraftServer server, EntityPotion entity) {
    super(server, entity);
  }
  
  public Collection<PotionEffect> getEffects() {
    if (this.effects == null) {
      this.effects = Potion.getBrewer().getEffectsFromDamage(getHandle().getPotionValue());
    }
    
    return this.effects;
  }
  
  public EntityPotion getHandle()
  {
    return (EntityPotion)this.entity;
  }
  
  public String toString()
  {
    return "CraftThrownPotion";
  }
  
  public EntityType getType() {
    return EntityType.SPLASH_POTION;
  }
}
