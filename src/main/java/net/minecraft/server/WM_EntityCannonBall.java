package net.minecraft.server;

import java.util.Random;

public class WM_EntityCannonBall extends WM_EntityProjectile
{
  public WM_EntityCannonBall(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityCannonBall(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
    this.height = 0.0F;
  }
  
  public WM_EntityCannonBall(World paramWorld, WM_EntityCannon paramWM_EntityCannon, boolean paramBoolean)
  {
    this(paramWorld);
    this.shootingEntity = paramWM_EntityCannon.passenger;
    this.doesArrowBelongToPlayer = (paramWM_EntityCannon.passenger instanceof EntityHuman);
    b(0.5F, 0.5F);
    setPositionRotation(paramWM_EntityCannon.locX, paramWM_EntityCannon.locY + 1.0D, paramWM_EntityCannon.locZ, paramWM_EntityCannon.passenger.yaw, paramWM_EntityCannon.passenger.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    this.locX += this.motX * 1.2000000476837158D;
    this.locY += this.motY * 1.2000000476837158D;
    this.locZ += this.motZ * 1.2000000476837158D;
    setPosition(this.locX, this.locY, this.locZ);
    this.isCritical = paramBoolean;
    setArrowHeading(this.motX, this.motY, this.motZ, paramBoolean ? 4.0F : 2.0F, paramBoolean ? 0.1F : 2.0F);
  }
  



  public void F_()
  {
    super.F_();
    double d1 = Math.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
    double d2 = 8.0D;
    
    if (d1 > 1.0D)
    {
      for (int i = 1; i < d2; i++)
      {
        this.world.a("smoke", this.locX + this.motX * i / d2, this.locY + this.motY * i / d2, this.locZ + this.motZ * i / d2, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  
  public void createCrater()
  {
    if ((this.world.isStatic) || (!this.inGround) || (aU()))
    {
      return;
    }
    

    die();
    float f = this.isCritical ? 5.0F : 2.5F;
    WM_PhysHelper.createAdvancedExplosion(this.world, this, this.locX, this.locY, this.locZ, f, false, mod_WeaponMod.instance.properties.cannonDoesBlockDamage, true);
  }
  


  public void onEntityHit(Entity paramEntity)
  {
    DamageSource localDamageSource = null;
    
    if (this.shootingEntity == null)
    {
      localDamageSource = WM_WeaponDamageSource.causeWeaponDamage(this, this);
    }
    else
    {
      localDamageSource = WM_WeaponDamageSource.causeWeaponDamage(this, this.shootingEntity);
    }
    
    if (paramEntity.damageEntity(localDamageSource, 30))
    {
      this.world.makeSound(this, "random.damage.hurtflesh", 1.0F, 1.2F / (this.random.nextFloat() * 0.4F + 0.7F));
    }
  }
  
  public void onGroundHit(MovingObjectPosition paramMovingObjectPosition)
  {
    this.xTile = paramMovingObjectPosition.b;
    this.yTile = paramMovingObjectPosition.c;
    this.zTile = paramMovingObjectPosition.d;
    this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
    this.inData = this.world.getData(this.xTile, this.yTile, this.zTile);
    this.motX = ((float)(paramMovingObjectPosition.pos.a - this.locX));
    this.motY = ((float)(paramMovingObjectPosition.pos.b - this.locY));
    this.motZ = ((float)(paramMovingObjectPosition.pos.c - this.locZ));
    float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
    this.locX -= this.motX / f * 0.05D;
    this.locY -= this.motY / f * 0.05D;
    this.locZ -= this.motZ / f * 0.05D;
    this.inGround = true;
    createCrater();
  }
  
  public float getAirResistance()
  {
    return 0.97F;
  }
  
  public float getGravity()
  {
    return 0.03F;
  }
  
  public ItemStack getPickupItem()
  {
    return new ItemStack(mod_WeaponMod.cannonBall, 1);
  }
  
  public float getShadowSize()
  {
    return 0.5F;
  }
}
