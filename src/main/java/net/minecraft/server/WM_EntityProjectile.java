package net.minecraft.server;

import java.util.List;
import java.util.Random;

public abstract class WM_EntityProjectile extends Entity
{
  protected int xTile;
  protected int yTile;
  protected int zTile;
  protected int inTile;
  protected int inData;
  protected boolean inGround;
  public boolean doesArrowBelongToPlayer;
  public int arrowShake;
  public Entity shootingEntity;
  protected int ticksInGround;
  protected int ticksInAir;
  public boolean beenInGround;
  public boolean isCritical;
  
  public WM_EntityProjectile(World paramWorld)
  {
    super(paramWorld);
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inData = 0;
    this.inGround = false;
    this.doesArrowBelongToPlayer = false;
    this.isCritical = false;
    this.arrowShake = 0;
    this.ticksInAir = 0;
    this.height = 0.0F;
    b(0.5F, 0.5F);
  }
  

  protected void b() {}
  

  public void setArrowHeading(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2)
  {
    float f1 = MathHelper.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 + paramDouble3 * paramDouble3);
    paramDouble1 /= f1;
    paramDouble2 /= f1;
    paramDouble3 /= f1;
    paramDouble1 += this.random.nextGaussian() * 0.0075D * paramFloat2;
    paramDouble2 += this.random.nextGaussian() * 0.0075D * paramFloat2;
    paramDouble3 += this.random.nextGaussian() * 0.0075D * paramFloat2;
    paramDouble1 *= paramFloat1;
    paramDouble2 *= paramFloat1;
    paramDouble3 *= paramFloat1;
    this.motX = paramDouble1;
    this.motY = paramDouble2;
    this.motZ = paramDouble3;
    float f2 = MathHelper.sqrt(paramDouble1 * paramDouble1 + paramDouble3 * paramDouble3);
    this.lastYaw = (this.yaw = (float)(Math.atan2(paramDouble1, paramDouble3) * 180.0D / 3.141592653589793D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(paramDouble2, f2) * 180.0D / 3.141592653589793D));
    this.ticksInGround = 0;
  }
  
  public void setVelocity(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.motX = paramDouble1;
    this.motY = paramDouble2;
    this.motZ = paramDouble3;
    
    if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
    {
      if (aimRotation())
      {
        float f = MathHelper.sqrt(paramDouble1 * paramDouble1 + paramDouble3 * paramDouble3);
        this.lastYaw = (this.yaw = (float)(Math.atan2(paramDouble1, paramDouble3) * 180.0D / 3.141592653589793D));
        this.lastPitch = (this.pitch = (float)(Math.atan2(paramDouble2, f) * 180.0D / 3.141592653589793D));
        this.lastPitch = this.pitch;
        this.lastYaw = this.yaw;
      }
      
      setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      this.ticksInGround = 0;
    }
  }
  



  public void F_()
  {
    super.F_();
    
    if ((aimRotation()) && (this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
    {
      float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      this.lastYaw = (this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
      this.lastPitch = (this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.141592653589793D));
    }
    
    int i = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
    
    if (i > 0)
    {
      Block.byId[i].updateShape(this.world, this.xTile, this.yTile, this.zTile);
      AxisAlignedBB localAxisAlignedBB1 = Block.byId[i].e(this.world, this.xTile, this.yTile, this.zTile);
      
      if ((localAxisAlignedBB1 != null) && (localAxisAlignedBB1.a(Vec3D.create(this.locX, this.locY, this.locZ))))
      {
        this.inGround = true;
      }
    }
    
    if (this.arrowShake > 0)
    {
      this.arrowShake -= 1;
    }
    
    if (this.inGround)
    {
      int j = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
      int k = this.world.getData(this.xTile, this.yTile, this.zTile);
      
      if ((j != this.inTile) || (k != this.inData))
      {
        this.inGround = false;
        this.motX *= this.random.nextFloat() * 0.2F;
        this.motY *= this.random.nextFloat() * 0.2F;
        this.motZ *= this.random.nextFloat() * 0.2F;
        this.ticksInGround = 0;
        this.ticksInAir = 0;
      }
      else
      {
        this.ticksInGround += 1;
        
        if (this.ticksInGround >= (this.doesArrowBelongToPlayer ? 200 : 800))
        {
          die();
        }
      }
      
      return;
    }
    
    this.ticksInAir += 1;
    Vec3D localVec3D1 = Vec3D.create(this.locX, this.locY, this.locZ);
    Vec3D localVec3D2 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    MovingObjectPosition localMovingObjectPosition1 = this.world.rayTrace(localVec3D1, localVec3D2, false, true);
    localVec3D1 = Vec3D.create(this.locX, this.locY, this.locZ);
    localVec3D2 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    
    if (localMovingObjectPosition1 != null)
    {
      localVec3D2 = Vec3D.create(localMovingObjectPosition1.pos.a, localMovingObjectPosition1.pos.b, localMovingObjectPosition1.pos.c);
    }
    
    Object localObject = null;
    List localList = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
    double d1 = 0.0D;
    
    int m;
    for (m = 0; m < localList.size(); m++)
    {
      Entity localEntity = (Entity)localList.get(m);
      
      if ((localEntity.o_()) && ((localEntity != this.shootingEntity) || (this.ticksInAir >= 5)))
      {



        float f4 = 0.3F;
        AxisAlignedBB localAxisAlignedBB2 = localEntity.boundingBox.grow(f4, f4, f4);
        MovingObjectPosition localMovingObjectPosition2 = localAxisAlignedBB2.a(localVec3D1, localVec3D2);
        
        if (localMovingObjectPosition2 != null)
        {



          double d2 = localVec3D1.b(localMovingObjectPosition2.pos);
          
          if ((d2 < d1) || (d1 == 0.0D))
          {
            localObject = localEntity;
            d1 = d2;
          }
        }
      } }
    if (localObject != null)
    {
      localMovingObjectPosition1 = new MovingObjectPosition((Entity)localObject);
    }
    
    if (localMovingObjectPosition1 != null)
    {
      if (localMovingObjectPosition1.entity != null)
      {
        onEntityHit(localMovingObjectPosition1.entity);
      }
      else
      {
        onGroundHit(localMovingObjectPosition1);
      }
    }
    
    if (this.isCritical)
    {
      for (m = 0; m < 2; m++)
      {
        this.world.a("crit", this.locX + this.motX * m / 4.0D, this.locY + this.motY * m / 4.0D, this.locZ + this.motZ * m / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ);
      }
    }
    
    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    
    if (aimRotation())
    {
      float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
      
      for (this.pitch = ((float)(Math.atan2(this.motY, f2) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
      
      while (this.pitch - this.lastPitch >= 180.0F) { this.lastPitch += 360.0F;
      }
      while (this.yaw - this.lastYaw < -180.0F) { this.lastYaw -= 360.0F;
      }
      while (this.yaw - this.lastYaw >= 180.0F) { this.lastYaw += 360.0F;
      }
      this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
      this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
    }
    
    float f2 = getAirResistance();
    float f3 = getGravity();
    
    if (aU())
    {
      this.beenInGround = true;
      
      for (int n = 0; n < 4; n++)
      {
        float f5 = 0.25F;
        this.world.a("bubble", this.locX - this.motX * f5, this.locY - this.motY * f5, this.locZ - this.motZ * f5, this.motX, this.motY, this.motZ);
      }
      
      f2 = 0.8F;
    }
    
    this.motX *= f2;
    this.motY *= f2;
    this.motZ *= f2;
    this.motY -= f3;
    setPosition(this.locX, this.locY, this.locZ);
  }
  
  public final double getTotalVelocity()
  {
    return Math.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
  }
  
  public boolean aimRotation()
  {
    return true;
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    bounceBack();
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
    this.beenInGround = true;
    this.isCritical = false;
    this.arrowShake = getMaxArrowShake();
    playHitSound();
  }
  
  protected void bounceBack()
  {
    this.motX *= -0.1D;
    this.motY *= -0.1D;
    this.motZ *= -0.1D;
    this.yaw += 180.0F;
    this.lastYaw += 180.0F;
    this.ticksInAir = 0;
  }
  
  public ItemStack getPickupItem()
  {
    return null;
  }
  
  public float getAirResistance()
  {
    return 0.99F;
  }
  
  public float getGravity()
  {
    return 0.05F;
  }
  
  public int getMaxArrowShake()
  {
    return 7;
  }
  



  public void playHitSound() {}
  


  public void a_(EntityHuman paramEntityHuman)
  {
    if (this.world.isStatic)
    {
      return;
    }
    
    ItemStack localItemStack = getPickupItem();
    
    if (localItemStack == null)
    {
      return;
    }
    
    if ((this.inGround) && (this.doesArrowBelongToPlayer) && (this.arrowShake <= 0) && (paramEntityHuman.inventory.pickup(localItemStack)))
    {
      this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
      paramEntityHuman.receive(this, 1);
      die();
    }
  }
  
  public float getShadowSize()
  {
    return 0.0F;
  }
  



  public void b(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setShort("xTile", (short)this.xTile);
    paramNBTTagCompound.setShort("yTile", (short)this.yTile);
    paramNBTTagCompound.setShort("zTile", (short)this.zTile);
    paramNBTTagCompound.setByte("inTile", (byte)this.inTile);
    paramNBTTagCompound.setByte("inData", (byte)this.inData);
    paramNBTTagCompound.setByte("shake", (byte)this.arrowShake);
    paramNBTTagCompound.setBoolean("inGround", this.inGround);
    paramNBTTagCompound.setBoolean("player", this.doesArrowBelongToPlayer);
    paramNBTTagCompound.setBoolean("crit", this.isCritical);
    paramNBTTagCompound.setBoolean("beenInGround", this.beenInGround);
  }
  



  public void a(NBTTagCompound paramNBTTagCompound)
  {
    this.xTile = paramNBTTagCompound.getShort("xTile");
    this.yTile = paramNBTTagCompound.getShort("yTile");
    this.zTile = paramNBTTagCompound.getShort("zTile");
    this.inTile = (paramNBTTagCompound.getByte("inTile") & 0xFF);
    this.inData = (paramNBTTagCompound.getByte("inData") & 0xFF);
    this.arrowShake = (paramNBTTagCompound.getByte("shake") & 0xFF);
    this.inGround = paramNBTTagCompound.getBoolean("inGround");
    this.doesArrowBelongToPlayer = paramNBTTagCompound.getBoolean("player");
    this.isCritical = paramNBTTagCompound.getBoolean("crit");
    this.beenInGround = paramNBTTagCompound.getBoolean("beenInGrond");
  }
}
