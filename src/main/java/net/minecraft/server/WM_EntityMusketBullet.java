package net.minecraft.server;

import java.util.Random;

public class WM_EntityMusketBullet extends WM_EntityProjectile
{
  public WM_EntityMusketBullet(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityMusketBullet(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public WM_EntityMusketBullet(World paramWorld, EntityLiving paramEntityLiving, float paramFloat)
  {
    this(paramWorld);
    this.shootingEntity = paramEntityLiving;
    this.doesArrowBelongToPlayer = (paramEntityLiving instanceof EntityHuman);
    b(0.5F, 0.5F);
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, 5.0F, paramFloat);
  }
  



  public void F_()
  {
    super.F_();
    
    if (this.inGround)
    {
      if (this.random.nextInt(4) == 0)
      {
        this.world.a("smoke", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
      }
      
      return;
    }
    
    double d1 = getTotalVelocity();
    double d2 = 16.0D;
    
    if (d1 > 2.0D)
    {
      for (int i = 1; i < d2; i++)
      {
        this.world.a("explode", this.locX + this.motX * i / d2, this.locY + this.motY * i / d2, this.locZ + this.motZ * i / d2, 0.0D, 0.0D, 0.0D);
      }
    }
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    int i = 25 - this.ticksInAir;
    
    if (i < 1)
    {
      i = 1;
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
    
    paramEntity.damageEntity(localDamageSource, i);
    die();
  }
  
  public void onGroundHit(MovingObjectPosition paramMovingObjectPosition)
  {
    this.xTile = paramMovingObjectPosition.b;
    this.yTile = paramMovingObjectPosition.c;
    this.zTile = paramMovingObjectPosition.d;
    this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
    this.inData = this.world.getData(this.xTile, this.yTile, this.zTile);
    Material localMaterial = this.world.getMaterial(this.xTile, this.yTile, this.zTile);
    boolean bool1 = localMaterial.equals(Material.STONE);
    boolean bool2 = localMaterial.equals(Material.ORE);
    
    if ((this.ticksInAir > 0) && ((bool1) || (bool2)))
    {
      if (bool2)
      {
        this.locX = this.xTile;
        this.locY = this.yTile;
        this.locZ = this.zTile;
        
        if ((paramMovingObjectPosition.face == 0) || (paramMovingObjectPosition.face == 1))
        {
          this.motY *= -1.0D;
        }
        else if ((paramMovingObjectPosition.face == 2) || (paramMovingObjectPosition.face == 3))
        {
          this.motZ *= -1.0D;
        }
        else if ((paramMovingObjectPosition.face == 4) || (paramMovingObjectPosition.face == 5))
        {
          this.motX *= -1.0D;
        }
        
        this.motX *= 0.6000000238418579D;
        this.motY *= 0.6000000238418579D;
        this.motZ *= 0.6000000238418579D;
      }
      else if (bool1)
      {
        this.motX *= (0.5F - this.random.nextFloat());
        this.motY *= (0.5F - this.random.nextFloat());
        this.motZ *= (0.5F - this.random.nextFloat());
      }
      
      this.world.makeSound(this.xTile, this.yTile, this.zTile, "note.hat", 2.0F, this.random.nextFloat() * 0.4F + 0.4F);
    }
    else
    {
      this.motX = ((float)(paramMovingObjectPosition.pos.a - this.locX));
      this.motY = ((float)(paramMovingObjectPosition.pos.b - this.locY));
      this.motZ = ((float)(paramMovingObjectPosition.pos.c - this.locZ));
      float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
      this.locX -= this.motX / f * 0.05D;
      this.locY -= this.motY / f * 0.05D;
      this.locZ -= this.motZ / f * 0.05D;
      this.inGround = true;
    }
  }
  
  public boolean aimRotation()
  {
    return false;
  }
  
  public float getAirResistance()
  {
    return 0.98F;
  }
  
  public float getGravity()
  {
    return getTotalVelocity() >= 3.0D ? 0.0F : 0.07F;
  }
  
  public int getMaxArrowShake()
  {
    return 0;
  }
}
