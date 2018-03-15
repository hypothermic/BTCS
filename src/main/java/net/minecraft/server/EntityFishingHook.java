package net.minecraft.server;

import java.util.Random;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.plugin.PluginManager;

public class EntityFishingHook extends Entity
{
  private int d = -1;
  private int e = -1;
  private int f = -1;
  private int g = 0;
  private boolean h = false;
  public int a = 0;
  public EntityHuman owner;
  private int i;
  private int j = 0;
  private int k = 0;
  public Entity hooked = null;
  private int l;
  private double m;
  private double n;
  private double o;
  private double p;
  private double q;
  
  public EntityFishingHook(World world) {
    super(world);
    b(0.25F, 0.25F);
    this.cd = true;
  }
  
  public EntityFishingHook(World world, EntityHuman entityhuman) {
    super(world);
    this.cd = true;
    this.owner = entityhuman;
    this.owner.hookedFish = this;
    b(0.25F, 0.25F);
    setPositionRotation(entityhuman.locX, entityhuman.locY + 1.62D - entityhuman.height, entityhuman.locZ, entityhuman.yaw, entityhuman.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.10000000149011612D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    float f = 0.4F;
    
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
    a(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
  }
  
  protected void b() {}
  
  public void a(double d0, double d1, double d2, float f, float f1) {
    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    
    d0 /= f2;
    d1 /= f2;
    d2 /= f2;
    d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
    d0 *= f;
    d1 *= f;
    d2 *= f;
    this.motX = d0;
    this.motY = d1;
    this.motZ = d2;
    float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    
    this.lastYaw = (this.yaw = (float)(Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.1415927410125732D));
    this.i = 0;
  }
  
  public void F_() {
    super.F_();
    if (this.l > 0) {
      double d0 = this.locX + (this.m - this.locX) / this.l;
      double d1 = this.locY + (this.n - this.locY) / this.l;
      double d2 = this.locZ + (this.o - this.locZ) / this.l;
      

      double d3; // BTCS: moved outside for loop
      for (d3 = this.p - this.yaw; d3 < -180.0D; d3 += 360.0D) {}
      


      while (d3 >= 180.0D) {
        d3 -= 360.0D;
      }
      
      this.yaw = ((float)(this.yaw + d3 / this.l));
      this.pitch = ((float)(this.pitch + (this.q - this.pitch) / this.l));
      this.l -= 1;
      setPosition(d0, d1, d2);
      c(this.yaw, this.pitch);
    } else {
      if (!this.world.isStatic) {
        ItemStack itemstack = this.owner.U();
        
        if ((this.owner.dead) || (!this.owner.isAlive()) || (itemstack == null) || (itemstack.getItem() != Item.FISHING_ROD) || (j(this.owner) > 1024.0D)) {
          die();
          this.owner.hookedFish = null;
          return;
        }
        
        if (this.hooked != null) {
          if (!this.hooked.dead) {
            this.locX = this.hooked.locX;
            this.locY = (this.hooked.boundingBox.b + this.hooked.length * 0.8D);
            this.locZ = this.hooked.locZ;
            return;
          }
          
          this.hooked = null;
        }
      }
      
      if (this.a > 0) {
        this.a -= 1;
      }
      
      if (this.h) {
        int i = this.world.getTypeId(this.d, this.e, this.f);
        
        if (i == this.g) {
          this.i += 1;
          if (this.i == 1200) {
            die();
          }
          
          return;
        }
        
        this.h = false;
        this.motX *= this.random.nextFloat() * 0.2F;
        this.motY *= this.random.nextFloat() * 0.2F;
        this.motZ *= this.random.nextFloat() * 0.2F;
        this.i = 0;
        this.j = 0;
      } else {
        this.j += 1;
      }
      
      Vec3D vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
      Vec3D vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      MovingObjectPosition movingobjectposition = this.world.a(vec3d, vec3d1);
      
      vec3d = Vec3D.create(this.locX, this.locY, this.locZ);
      vec3d1 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      if (movingobjectposition != null) {
        vec3d1 = Vec3D.create(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
      }
      
      Entity entity = null;
      java.util.List list = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double d4 = 0.0D;
      


      for (int j = 0; j < list.size(); j++) {
        Entity entity1 = (Entity)list.get(j);
        
        if ((entity1.o_()) && ((entity1 != this.owner) || (this.j >= 5))) {
          float f = 0.3F;
          AxisAlignedBB axisalignedbb = entity1.boundingBox.grow(f, f, f);
          MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
          
          if (movingobjectposition1 != null) {
            double d5 = vec3d.distanceSquared(movingobjectposition1.pos);
            if ((d5 < d4) || (d4 == 0.0D)) {
              entity = entity1;
              d4 = d5;
            }
          }
        }
      }
      
      if (entity != null) {
        movingobjectposition = new MovingObjectPosition(entity);
      }
      
      if (movingobjectposition != null) {
        if (movingobjectposition.entity != null) {
          if (movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.owner), 0)) {
            this.hooked = movingobjectposition.entity;
          }
        } else {
          this.h = true;
        }
      }
      
      if (!this.h) {
        move(this.motX, this.motY, this.motZ);
        float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        
        this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
        
        for (this.pitch = ((float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
        


        while (this.pitch - this.lastPitch >= 180.0F) {
          this.lastPitch += 360.0F;
        }
        
        while (this.yaw - this.lastYaw < -180.0F) {
          this.lastYaw -= 360.0F;
        }
        
        while (this.yaw - this.lastYaw >= 180.0F) {
          this.lastYaw += 360.0F;
        }
        
        this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
        this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
        float f2 = 0.92F;
        
        if ((this.onGround) || (this.positionChanged)) {
          f2 = 0.5F;
        }
        
        byte b0 = 5;
        double d6 = 0.0D;
        
        for (int k = 0; k < b0; k++) {
          double d7 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 0) / b0 - 0.125D + 0.125D;
          double d8 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (k + 1) / b0 - 0.125D + 0.125D;
          AxisAlignedBB axisalignedbb1 = AxisAlignedBB.b(this.boundingBox.a, d7, this.boundingBox.c, this.boundingBox.d, d8, this.boundingBox.f);
          
          if (this.world.b(axisalignedbb1, Material.WATER)) {
            d6 += 1.0D / b0;
          }
        }
        
        if (d6 > 0.0D) {
          if (this.k > 0) {
            this.k -= 1;
          } else {
            short short1 = 500;
            
            if (this.world.y(MathHelper.floor(this.locX), MathHelper.floor(this.locY) + 1, MathHelper.floor(this.locZ))) {
              short1 = 300;
            }
            
            if (this.random.nextInt(short1) == 0) {
              this.k = (this.random.nextInt(30) + 10);
              this.motY -= 0.20000000298023224D;
              this.world.makeSound(this, "random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
              float f3 = MathHelper.floor(this.boundingBox.b);
              




              for (int l = 0; l < 1.0F + this.width * 20.0F; l++) {
                float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                this.world.a("bubble", this.locX + f5, f3 + 1.0F, this.locZ + f4, this.motX, this.motY - this.random.nextFloat() * 0.2F, this.motZ);
              }
              
              for (l = 0; l < 1.0F + this.width * 20.0F; l++) {
                float f5 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                float f4 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
                this.world.a("splash", this.locX + f5, f3 + 1.0F, this.locZ + f4, this.motX, this.motY, this.motZ);
              }
            }
          }
        }
        
        if (this.k > 0) {
          this.motY -= this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat() * 0.2D;
        }
        
        double d5 = d6 * 2.0D - 1.0D;
        this.motY += 0.03999999910593033D * d5;
        if (d6 > 0.0D) {
          f2 = (float)(f2 * 0.9D);
          this.motY *= 0.8D;
        }
        
        this.motX *= f2;
        this.motY *= f2;
        this.motZ *= f2;
        setPosition(this.locX, this.locY, this.locZ);
      }
    }
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setShort("xTile", (short)this.d);
    nbttagcompound.setShort("yTile", (short)this.e);
    nbttagcompound.setShort("zTile", (short)this.f);
    nbttagcompound.setByte("inTile", (byte)this.g);
    nbttagcompound.setByte("shake", (byte)this.a);
    nbttagcompound.setByte("inGround", (byte)(this.h ? 1 : 0));
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.d = nbttagcompound.getShort("xTile");
    this.e = nbttagcompound.getShort("yTile");
    this.f = nbttagcompound.getShort("zTile");
    this.g = (nbttagcompound.getByte("inTile") & 0xFF);
    this.a = (nbttagcompound.getByte("shake") & 0xFF);
    this.h = (nbttagcompound.getByte("inGround") == 1);
  }
  
  public int k() {
    byte b0 = 0;
    
    if (this.hooked != null)
    {
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), this.hooked.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
      this.world.getServer().getPluginManager().callEvent(playerFishEvent);
      
      if (playerFishEvent.isCancelled()) {
        die();
        this.owner.hookedFish = null;
        return 0;
      }
      

      double d0 = this.owner.locX - this.locX;
      double d1 = this.owner.locY - this.locY;
      double d2 = this.owner.locZ - this.locZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      double d4 = 0.1D;
      
      this.hooked.motX += d0 * d4;
      this.hooked.motY += d1 * d4 + MathHelper.sqrt(d3) * 0.08D;
      this.hooked.motZ += d2 * d4;
      b0 = 3;
    } else if (this.k > 0) {
      EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.RAW_FISH));
      
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), entityitem.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
      this.world.getServer().getPluginManager().callEvent(playerFishEvent);
      
      if (playerFishEvent.isCancelled()) {
        die();
        this.owner.hookedFish = null;
        return 0;
      }
      

      double d5 = this.owner.locX - this.locX;
      double d6 = this.owner.locY - this.locY;
      double d7 = this.owner.locZ - this.locZ;
      double d8 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
      double d9 = 0.1D;
      
      entityitem.motX = (d5 * d9);
      entityitem.motY = (d6 * d9 + MathHelper.sqrt(d8) * 0.08D);
      entityitem.motZ = (d7 * d9);
      this.world.addEntity(entityitem);
      this.owner.a(StatisticList.B, 1);
      b0 = 1;
    }
    
    if (this.h)
    {
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, PlayerFishEvent.State.IN_GROUND);
      this.world.getServer().getPluginManager().callEvent(playerFishEvent);
      
      if (playerFishEvent.isCancelled()) {
        die();
        this.owner.hookedFish = null;
        return 0;
      }
      
      b0 = 2;
    }
    

    if (b0 == 0) {
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, PlayerFishEvent.State.FAILED_ATTEMPT);
      this.world.getServer().getPluginManager().callEvent(playerFishEvent);
    }
    
    die();
    this.owner.hookedFish = null;
    return b0;
  }
}
