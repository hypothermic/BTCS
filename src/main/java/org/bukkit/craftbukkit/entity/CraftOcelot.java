package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityOcelot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;

public class CraftOcelot extends CraftTameableAnimal implements org.bukkit.entity.Ocelot
{
  public CraftOcelot(org.bukkit.craftbukkit.CraftServer server, EntityOcelot wolf)
  {
    super(server, wolf);
  }
  
  public EntityOcelot getHandle()
  {
    return (EntityOcelot)this.entity;
  }
  
  public Ocelot.Type getCatType() {
    return Ocelot.Type.getType(getHandle().getCatType());
  }
  
  public void setCatType(Ocelot.Type type) {
    org.apache.commons.lang3.Validate.notNull(type, "Cat type cannot be null"); // BTCS
    getHandle().setCatType(type.getId());
  }
  
  public EntityType getType()
  {
    return EntityType.OCELOT;
  }
}
