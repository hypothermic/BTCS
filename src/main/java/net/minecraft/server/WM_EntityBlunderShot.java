package net.minecraft.server;

import java.util.Random;

public class WM_EntityBlunderShot extends WM_EntityProjectile
{
  private double startPosX;
  private double startPosY;
  private double startPosZ;
  
  public WM_EntityBlunderShot(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityBlunderShot(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
    this.height = 0.0F;
    this.startPosX = this.locX;
    this.startPosY = this.locY;
    this.startPosZ = this.locZ;
  }
  
  public WM_EntityBlunderShot(World paramWorld, EntityLiving paramEntityLiving)
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
    this.startPosX = this.locX;
    this.startPosY = this.locY;
    this.startPosZ = this.locZ;
    setArrowHeading(this.motX, this.motY, this.motZ, 5.0F, 15.0F);
  }
  



  public void F_()
  {
    super.F_();
    
    if (this.ticksInAir > 4)
    {
      die();
    }
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    float f = (float)getPassedDistance(paramEntity.locX, paramEntity.locY, paramEntity.locZ);
    int i = MathHelper.d(25.0F - f);
    
    if (i < 1)
    {
      i = 1;
    }
    
    if (i > 20)
    {
      i = 20;
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
      die();
    }
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
  
  public int getMaxArrowShake()
  {
    return 0;
  }
  
  public float getGravity()
  {
    return getTotalVelocity() >= 2.0D ? 0.0F : 0.04F;
  }
  
  public double getPassedDistance(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d1 = paramDouble1 - this.startPosX;
    double d2 = paramDouble2 - this.startPosY;
    double d3 = paramDouble3 - this.startPosZ;
    return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
  }
  
  public static void fireSpreadShot(World paramWorld, EntityLiving paramEntityLiving)
  {
    for (int i = 0; i < 10; i++)
    {
      paramWorld.addEntity(new WM_EntityBlunderShot(paramWorld, paramEntityLiving));
    }
  }
  
  public static void fireSpreadShot(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    for (int i = 0; i < 10; i++)
    {
      paramWorld.addEntity(new WM_EntityBlunderShot(paramWorld, paramDouble1, paramDouble2, paramDouble3));
    }
  }
  
  public static void fireFromDispenser(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < 10; i++)
    {
      WM_EntityBlunderShot localWM_EntityBlunderShot = new WM_EntityBlunderShot(paramWorld, paramDouble1, paramDouble2, paramDouble3);
      localWM_EntityBlunderShot.setArrowHeading(paramInt1, 0.0D, paramInt2, 5.0F, 15.0F);
      paramWorld.addEntity(localWM_EntityBlunderShot);
    }
  }
}
