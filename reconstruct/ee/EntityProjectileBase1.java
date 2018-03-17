package ee;

import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.DamageSource;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public abstract class EntityProjectileBase1 extends Entity
{
  public float speed;
  public float slowdown;
  public float curvature;
  public float precision;
  public float hitBox;
  public int dmg;
  public ItemStack item;
  public int ttlInGround;
  public int xTile;
  public int yTile;
  public int zTile;
  public int inTile;
  public int inData;
  public boolean inGround;
  public int arrowShake;
  public EntityLiving shooter;
  public int ticksInGround;
  public int ticksFlying;
  public boolean shotByPlayer;
  
  public EntityProjectileBase1(World var1)
  {
    super(var1);
  }
  
  public EntityProjectileBase1(World var1, double var2, double var4, double var6)
  {
    this(var1);
    setPosition(var2, var4, var6);
  }
  
  public EntityProjectileBase1(World var1, EntityLiving var2)
  {
    this(var1);
    this.shooter = var2;
    this.shotByPlayer = (var2 instanceof EntityHuman);
    setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw + this.world.random.nextFloat(), var2.pitch + this.world.random.nextFloat());
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.10000000149011612D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, this.speed, this.precision);
  }
  
  protected void b()
  {
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inGround = false;
    this.arrowShake = 0;
    this.ticksFlying = 0;
    b(0.5F, 0.5F);
    this.height = 0.0F;
    this.hitBox = 0.3F;
    this.speed = 1.0F;
    this.slowdown = 0.99F;
    this.curvature = 0.03F;
    this.dmg = 4;
    this.precision = 1.0F;
    this.ttlInGround = 1200;
    this.item = null;
  }
  



  public void die()
  {
    this.shooter = null;
    super.die();
  }
  
  public void setArrowHeading(double var1, double var3, double var5, float var7, float var8)
  {
    float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
    var1 /= var9;
    var3 /= var9;
    var5 /= var9;
    var1 += this.random.nextGaussian() * 0.007499999832361937D * var8;
    var3 += this.random.nextGaussian() * 0.007499999832361937D * var8;
    var5 += this.random.nextGaussian() * 0.007499999832361937D * var8;
    var1 *= var7;
    var3 *= var7;
    var5 *= var7;
    this.motX = var1;
    this.motY = var3;
    this.motZ = var5;
    float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
    this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
    this.ticksInGround = 0;
  }
  
  public void setVelocity(double var1, double var3, double var5)
  {
    this.motX = var1;
    this.motY = var3;
    this.motZ = var5;
    
    if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
    {
      float var7 = MathHelper.sqrt(var1 * var1 + var5 * var5);
      this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
      this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var7) * 180.0D / 3.141592653589793D));
    }
  }
  



  public void F_()
  {
    super.F_();
    
    if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F))
    {
      float var1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
      this.lastYaw = (this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
      this.lastPitch = (this.pitch = (float)(Math.atan2(this.motY, var1) * 180.0D / 3.141592653589793D));
    }
    
    if (this.arrowShake > 0)
    {
      this.arrowShake -= 1;
    }
    
    if (this.inGround)
    {
      int var15 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
      int var2 = this.world.getData(this.xTile, this.yTile, this.zTile);
      
      if ((var15 == this.inTile) && (var2 == this.inData))
      {
        this.ticksInGround += 1;
        tickInGround();
        
        if (this.ticksInGround == this.ttlInGround)
        {
          die();
        }
        
        return;
      }
      
      this.inGround = false;
      this.motX *= this.random.nextFloat() * 0.2F;
      this.motY *= this.random.nextFloat() * 0.2F;
      this.motZ *= this.random.nextFloat() * 0.2F;
      this.ticksInGround = 0;
      this.ticksFlying = 0;
    }
    else
    {
      this.ticksFlying += 1;
    }
    
    tickFlying();
    Vec3D var16 = Vec3D.create(this.locX, this.locY, this.locZ);
    Vec3D var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    MovingObjectPosition var3 = this.world.a(var16, var17);
    var16 = Vec3D.create(this.locX, this.locY, this.locZ);
    var17 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
    
    if (var3 != null)
    {
      var17 = Vec3D.create(var3.pos.a, var3.pos.b, var3.pos.c);
    }
    
    Entity var4 = null;
    List var5 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
    double var6 = 0.0D;
    
    for (int var8 = 0; var8 < var5.size(); var8++)
    {
      Entity var9 = (Entity)var5.get(var8);
      
      if (canBeShot(var9))
      {
        float var10 = this.hitBox;
        AxisAlignedBB var11 = var9.boundingBox.grow(var10, var10, var10);
        MovingObjectPosition var12 = var11.a(var16, var17);
        
        if (var12 != null)
        {
          double var13 = var16.b(var12.pos);
          
          if ((var13 < var6) || (var6 == 0.0D))
          {
            var4 = var9;
            var6 = var13;
          }
        }
      }
    }
    
    if (var4 != null)
    {
      var3 = new MovingObjectPosition(var4);
    }
    
    if ((var3 != null) && (onHit()))
    {
      Entity var18 = var3.entity;
      
      if (var18 != null)
      {
        if (onHitTarget(var18))
        {
          if (((var18 instanceof EntityLiving)) && (!(var18 instanceof EntityHuman)))
          {
            net.minecraft.server.EEProxy.setArmorRating((EntityLiving)var18, 0);
          }
          
          var18.damageEntity(DamageSource.mobAttack(this.shooter), this.dmg);
          die();
        }
      }
      else
      {
        this.xTile = var3.b;
        this.yTile = var3.c;
        this.zTile = var3.d;
        this.inTile = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
        this.inData = this.world.getData(this.xTile, this.yTile, this.zTile);
        
        if (onHitBlock(var3))
        {
          this.motX = ((float)(var3.pos.a - this.locX));
          this.motY = ((float)(var3.pos.b - this.locY));
          this.motZ = ((float)(var3.pos.c - this.locZ));
          float var19 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
          this.locX -= this.motX / var19 * 0.05000000074505806D;
          this.locY -= this.motY / var19 * 0.05000000074505806D;
          this.locZ -= this.motZ / var19 * 0.05000000074505806D;
          this.inGround = true;
          this.arrowShake = 7;
        }
        else
        {
          this.inTile = 0;
          this.inData = 0;
        }
      }
    }
    
    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    handleMotionUpdate();
    float var20 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
    
    for (this.pitch = ((float)(Math.atan2(this.motY, var20) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
    



    while (this.pitch - this.lastPitch >= 180.0F)
    {
      this.lastPitch += 360.0F;
    }
    
    while (this.yaw - this.lastYaw < -180.0F)
    {
      this.lastYaw -= 360.0F;
    }
    
    while (this.yaw - this.lastYaw >= 180.0F)
    {
      this.lastYaw += 360.0F;
    }
    
    this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
    this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
    setPosition(this.locX, this.locY, this.locZ);
  }
  
  public void handleMotionUpdate()
  {
    float var1 = this.slowdown;
    
    if (h_())
    {
      for (int var2 = 0; var2 < 4; var2++)
      {
        float var3 = 0.25F;
        this.world.a("bubble", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
      }
      
      var1 *= 0.8F;
    }
    
    this.motX *= var1;
    this.motY *= var1;
    this.motZ *= var1;
    this.motY -= this.curvature;
  }
  



  public void b(NBTTagCompound var1)
  {
    var1.setShort("xTile", (short)this.xTile);
    var1.setShort("yTile", (short)this.yTile);
    var1.setShort("zTile", (short)this.zTile);
    var1.setByte("inTile", (byte)this.inTile);
    var1.setByte("inData", (byte)this.inData);
    var1.setByte("shake", (byte)this.arrowShake);
    var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    var1.setBoolean("player", this.shotByPlayer);
  }
  



  public void a(NBTTagCompound var1)
  {
    this.xTile = var1.getShort("xTile");
    this.yTile = var1.getShort("yTile");
    this.zTile = var1.getShort("zTile");
    this.inTile = (var1.getByte("inTile") & 0xFF);
    this.inData = (var1.getByte("inData") & 0xFF);
    this.arrowShake = (var1.getByte("shake") & 0xFF);
    this.inGround = (var1.getByte("inGround") == 1);
    this.shotByPlayer = var1.getBoolean("player");
  }
  



  public void a_(EntityHuman var1)
  {
    if (this.item != null)
    {
      if (!this.world.isStatic)
      {
        if ((this.inGround) && (this.shotByPlayer) && (this.arrowShake <= 0) && (var1.inventory.pickup(this.item.cloneItemStack())))
        {
          this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
          var1.receive(this, 1);
          die();
        }
      }
    }
  }
  
  public boolean canBeShot(Entity var1)
  {
    return (var1.o_()) && ((var1 != this.shooter) || (this.ticksFlying >= 5)) && ((!(var1 instanceof EntityLiving)) || (((EntityLiving)var1).deathTicks <= 0));
  }
  
  public boolean onHit()
  {
    return true;
  }
  
  public boolean onHitTarget(Entity var1)
  {
    this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    return true;
  }
  
  public void tickFlying() {}
  
  public void tickInGround() {}
  
  public boolean onHitBlock(MovingObjectPosition var1)
  {
    return onHitBlock();
  }
  
  public boolean onHitBlock()
  {
    this.world.makeSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    return true;
  }
  
  public float getShadowSize()
  {
    return 0.0F;
  }
}
