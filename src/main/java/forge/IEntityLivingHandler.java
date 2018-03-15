package forge;

import java.util.ArrayList;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;

public abstract interface IEntityLivingHandler
{
  public abstract boolean onEntityLivingSpawn(EntityLiving paramEntityLiving, World paramWorld, float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract boolean onEntityLivingDeath(EntityLiving paramEntityLiving, DamageSource paramDamageSource);
  
  public abstract void onEntityLivingSetAttackTarget(EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2);
  
  public abstract boolean onEntityLivingAttacked(EntityLiving paramEntityLiving, DamageSource paramDamageSource, int paramInt);
  
  public abstract void onEntityLivingJump(EntityLiving paramEntityLiving);
  
  public abstract boolean onEntityLivingFall(EntityLiving paramEntityLiving, float paramFloat);
  
  public abstract boolean onEntityLivingUpdate(EntityLiving paramEntityLiving);
  
  public abstract int onEntityLivingHurt(EntityLiving paramEntityLiving, DamageSource paramDamageSource, int paramInt);
  
  public abstract void onEntityLivingDrops(EntityLiving paramEntityLiving, DamageSource paramDamageSource, ArrayList<EntityItem> paramArrayList, int paramInt1, boolean paramBoolean, int paramInt2);
}
