package net.minecraft.server;

import java.util.Random;

public class WM_EntityCrossbowBolt extends WM_EntityProjectile
{
  public WM_EntityCrossbowBolt(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityCrossbowBolt(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
    this.height = 0.0F;
  }
  
  public WM_EntityCrossbowBolt(World paramWorld, EntityLiving paramEntityLiving, float paramFloat)
  {
    this(paramWorld);
    this.shootingEntity = paramEntityLiving;
    this.doesArrowBelongToPlayer = (paramEntityLiving instanceof EntityHuman);
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, 4.0F, paramFloat);
  }
  



  protected void b() {}
  


  public void F_()
  {
    super.F_();
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
    int i = (int)Math.ceil(f * 4.0D);
    DamageSource localDamageSource = null;
    
    if (this.shootingEntity == null)
    {
      localDamageSource = WM_WeaponDamageSource.causeWeaponDamage(this, this);
    }
    else
    {
      localDamageSource = WM_WeaponDamageSource.causeWeaponDamage(this, this.shootingEntity);
    }
    
    if (paramEntity.damageEntity(localDamageSource, i))
    {
      if ((paramEntity instanceof EntityLiving))
      {
        ((EntityLiving)paramEntity).aI += 1;
      }
      
      playHitSound();
      die();
    }
    else
    {
      bounceBack();
    }
  }
  
  public void playHitSound()
  {
    this.world.makeSound(this, "random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.4F));
  }
  
  public int getMaxArrowShake()
  {
    return 4;
  }
  
  public ItemStack getPickupItem()
  {
    return new ItemStack(mod_WeaponMod.bolt, 1);
  }
}
