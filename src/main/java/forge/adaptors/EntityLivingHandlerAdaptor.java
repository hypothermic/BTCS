package forge.adaptors;

import forge.IEntityLivingHandler;
import java.util.ArrayList;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;



public class EntityLivingHandlerAdaptor
  implements IEntityLivingHandler
{
  public boolean onEntityLivingSpawn(EntityLiving entity, World world, float x, float y, float z)
  {
    return false;
  }
  

  public boolean onEntityLivingDeath(EntityLiving entity, DamageSource killer)
  {
    return false;
  }
  


  public void onEntityLivingSetAttackTarget(EntityLiving entity, EntityLiving target) {}
  


  public boolean onEntityLivingAttacked(EntityLiving entity, DamageSource attack, int damage)
  {
    return false;
  }
  


  public void onEntityLivingJump(EntityLiving entity) {}
  


  public boolean onEntityLivingFall(EntityLiving entity, float distance)
  {
    return false;
  }
  

  public boolean onEntityLivingUpdate(EntityLiving entity)
  {
    return false;
  }
  

  public int onEntityLivingHurt(EntityLiving entity, DamageSource source, int damage)
  {
    return damage;
  }
  
  public void onEntityLivingDrops(EntityLiving entity, DamageSource source, ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit, int specialDropValue) {}
}
