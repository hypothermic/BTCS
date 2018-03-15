package org.bukkit.craftbukkit.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.server.EntityComplexPart;
import net.minecraft.server.EntityEnderDragon;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;

public class CraftEnderDragon
  extends CraftComplexLivingEntity implements EnderDragon
{
  public CraftEnderDragon(CraftServer server, EntityEnderDragon entity)
  {
    super(server, entity);
  }
  
  public Set<ComplexEntityPart> getParts() {
    ImmutableSet.Builder<ComplexEntityPart> builder = ImmutableSet.builder();
    
    for (EntityComplexPart part : getHandle().children) {
      builder.add((ComplexEntityPart)part.getBukkitEntity());
    }
    
    return builder.build();
  }
  
  public EntityEnderDragon getHandle()
  {
    return (EntityEnderDragon)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderDragon";
  }
  
  public EntityType getType() {
    return EntityType.ENDER_DRAGON;
  }
}
