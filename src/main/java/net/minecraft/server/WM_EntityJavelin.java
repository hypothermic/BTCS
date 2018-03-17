package net.minecraft.server;

import java.util.Random;

public class WM_EntityJavelin extends WM_EntityProjectile
{
  public WM_EntityJavelin(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityJavelin(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public WM_EntityJavelin(World paramWorld, EntityLiving paramEntityLiving, float paramFloat)
  {
    this(paramWorld);
    this.shootingEntity = paramEntityLiving;
    this.doesArrowBelongToPlayer = (paramEntityLiving instanceof EntityHuman);
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.10000000149011612D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, paramFloat * 1.1F, 3.0F);
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
    int i = (int)Math.ceil(f * 3.0D);
    
    if (this.isCritical)
    {
      i = i * 3 / 2 + 2;
    }
    
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
    this.world.makeSound(this, "random.bowhit", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.9F));
  }
  
  public int getMaxArrowShake()
  {
    return 10;
  }
  
  public float getGravity()
  {
    return 0.03F;
  }
  
  public ItemStack getPickupItem()
  {
    return new ItemStack(mod_WeaponMod.javelin, 1);
  }
}
