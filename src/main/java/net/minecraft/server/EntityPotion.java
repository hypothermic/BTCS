package net.minecraft.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.PotionSplashEvent;

public class EntityPotion
  extends EntityProjectile
{
  private int d;
  
  public EntityPotion(World world)
  {
    super(world);
  }
  
  public EntityPotion(World world, EntityLiving entityliving, int i) {
    super(world, entityliving);
    this.d = i;
  }
  
  public EntityPotion(World world, double d0, double d1, double d2, int i) {
    super(world, d0, d1, d2);
    this.d = i;
  }
  
  protected float e() {
    return 0.05F;
  }
  
  protected float c() {
    return 0.5F;
  }
  
  protected float d() {
    return -20.0F;
  }
  
  public int getPotionValue() {
    return this.d;
  }
  
  protected void a(MovingObjectPosition movingobjectposition) {
    if (!this.world.isStatic) {
      List list = Item.POTION.b(this.d);
      PotionSplashEvent event;
      if ((list != null) && (!list.isEmpty())) {
        AxisAlignedBB axisalignedbb = this.boundingBox.grow(4.0D, 2.0D, 4.0D);
        List list1 = this.world.a(EntityLiving.class, axisalignedbb);
        
        if ((list1 != null) && (!list1.isEmpty())) {
          Iterator iterator = list1.iterator();
          

          HashMap<LivingEntity, Double> affected = new HashMap();
          
          while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            double d0 = j(entity);
            
            if (d0 < 16.0D) {
              double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
              
              if (entity == movingobjectposition.entity) {
                d1 = 1.0D;
              }
              

              affected.put((LivingEntity)entity.getBukkitEntity(), Double.valueOf(d1));
            }
          }
          
          event = CraftEventFactory.callPotionSplashEvent(this, affected);
          if (!event.isCancelled()) {
            for (LivingEntity victim : event.getAffectedEntities()) {
              if ((victim instanceof CraftLivingEntity))
              {

                EntityLiving entity = ((CraftLivingEntity)victim).getHandle();
                double d1 = event.getIntensity(victim);
                

                Iterator iterator1 = list.iterator();
                
                while (iterator1.hasNext()) {
                  MobEffect mobeffect = (MobEffect)iterator1.next();
                  int i = mobeffect.getEffectId();
                  

                  if ((this.world.pvpMode) || (!(entity instanceof EntityPlayer)) || (entity == this.shooter) || (
                  
                    (i != 2) && (i != 4) && (i != 7) && (i != 15) && (i != 17) && (i != 18) && (i != 19)))
                  {


                    if (MobEffectList.byId[i].isInstant())
                    {
                      MobEffectList.byId[i].applyInstantEffect(this.shooter, entity, mobeffect.getAmplifier(), d1, this);
                    } else {
                      int j = (int)(d1 * mobeffect.getDuration() + 0.5D);
                      
                      if (j > 20)
                        entity.addEffect(new MobEffect(i, j, mobeffect.getAmplifier()));
                    }
                  }
                }
              }
            }
          }
        }
      }
      this.world.triggerEffect(2002, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), this.d);
      die();
    }
  }
}
