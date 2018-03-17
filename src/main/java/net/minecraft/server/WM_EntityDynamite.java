package net.minecraft.server;

import java.util.Random;

public class WM_EntityDynamite extends WM_EntityProjectile
{
  private int explodefuse;
  private boolean extinguished;
  
  public WM_EntityDynamite(World paramWorld)
  {
    super(paramWorld);
    this.extinguished = false;
    this.explodefuse = (this.random.nextInt(30) + 20);
  }
  
  public WM_EntityDynamite(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public WM_EntityDynamite(World paramWorld, EntityLiving paramEntityLiving, int paramInt)
  {
    this(paramWorld);
    this.shootingEntity = paramEntityLiving;
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, 0.7F, 4.0F);
    this.explodefuse = paramInt;
  }
  



  protected void b() {}
  


  public void F_()
  {
    super.F_();
    
    if ((!this.onGround) && (!this.beenInGround))
    {
      this.pitch -= 50.0F;
    }
    else
    {
      this.pitch = 180.0F;
    }
    
    if ((aU()) && (!this.extinguished))
    {
      this.extinguished = true;
      this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
      
      for (int i = 0; i < 8; i++)
      {
        float f = 0.25F;
        this.world.a("explode", this.locX - this.motX * f, this.locY - this.motY * f, this.locZ - this.motZ * f, this.motX, this.motY, this.motZ);
      }
    }
    
    this.explodefuse -= 1;
    
    if (!this.extinguished)
    {
      if (this.explodefuse <= 0)
      {
        detonate();
        die();
      }
      else if (this.explodefuse > 0)
      {
        this.world.a("smoke", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
      }
    }
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
    
    paramEntity.damageEntity(localDamageSource, 2);
    this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    this.ticksInAir = 0;
    setVelocity(this.motX * this.random.nextDouble(), this.motY * this.random.nextDouble(), this.motZ * this.random.nextDouble());
  }
  
  public void onGroundHit(MovingObjectPosition paramMovingObjectPosition)
  {
    this.xTile = paramMovingObjectPosition.b;
    this.yTile = paramMovingObjectPosition.c;
    this.zTile = paramMovingObjectPosition.d;
    this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
    this.motX = ((float)(paramMovingObjectPosition.pos.a - this.locX));
    this.motY = ((float)(paramMovingObjectPosition.pos.b - this.locY));
    this.motZ = ((float)(paramMovingObjectPosition.pos.c - this.locZ));
    float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
    this.locX -= this.motX / f * 0.05D;
    this.locY -= this.motY / f * 0.05D;
    this.locZ -= this.motZ / f * 0.05D;
    this.motX *= -0.2D;
    this.motZ *= -0.2D;
    
    if (paramMovingObjectPosition.face == 1)
    {
      this.inGround = true;
      this.beenInGround = true;
    }
    else
    {
      this.inGround = false;
      this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }
  }
  
  private void detonate()
  {
    if (this.world.isStatic)
    {
      return;
    }
    
    if ((this.extinguished) && ((this.ticksInGround >= 200) || (this.ticksInAir >= 200)))
    {
      die();
    }
    
    float f = 2.0F;
    
    if (mod_WeaponMod.instance.properties.dynamiteDoesBlockDamage)
    {
      this.world.explode(this, this.locX, this.locY, this.locZ, f);
    }
    else
    {
      WM_PhysHelper.createAdvancedExplosion(this.world, this, this.locX, this.locY, this.locZ, f, false, false, true);
    }
  }
  
  public boolean aimRotation()
  {
    return false;
  }
  
  public int getMaxArrowShake()
  {
    return 0;
  }
  
  public ItemStack getPickupItem()
  {
    return new ItemStack(mod_WeaponMod.dynamite, 1);
  }
  
  public float getShadowSize()
  {
    return 0.2F;
  }
  



  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setByte("fuse", (byte)this.explodefuse);
    paramNBTTagCompound.setBoolean("off", this.extinguished);
  }
  



  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.explodefuse = paramNBTTagCompound.getByte("fuse");
    this.extinguished = paramNBTTagCompound.getBoolean("off");
  }
}
