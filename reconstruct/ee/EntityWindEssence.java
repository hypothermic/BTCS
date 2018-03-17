package ee;

import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;
import net.minecraft.server.WorldData;

public class EntityWindEssence extends Entity
{
  private int xTile;
  private int yTile;
  private int zTile;
  private int inTile;
  private int yawDir;
  public static boolean grab = true;
  private boolean inGround;
  private EntityHuman player;
  private int ticksInAir;
  
  public EntityWindEssence(World var1)
  {
    super(var1);
    this.bf = true;
    b(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
  }
  
  public EntityWindEssence(World var1, double var2, double var4, double var6)
  {
    this(var1);
    setPosition(var2, var4, var6);
  }
  
  public EntityWindEssence(World var1, EntityHuman var2)
  {
    super(var1);
    this.player = var2;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inGround = false;
    this.yawDir = ((MathHelper.floor((var2.yaw + 180.0F) * 4.0F / 360.0F - 0.5D) & 0x3) + 1);
    b(0.5F, 0.5F);
    setPositionRotation(var2.locX, var2.locY + var2.getHeadHeight(), var2.locZ, var2.yaw, var2.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.length = 0.0F;
    this.be = 10.0D;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    calcVelo(this.motX, this.motY, this.motZ, 1.991F, 1.0F);
  }
  
  protected void b() {}
  
  public void calcVelo(double var1, double var3, double var5, float var7, float var8)
  {
    float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
    var1 /= var9;
    var3 /= var9;
    var5 /= var9;
    var1 *= var7;
    var3 *= var7;
    var5 *= var7;
    this.motX = var1;
    this.motY = var3;
    this.motZ = var5;
    float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
    this.lastYaw = (this.yaw = (float)(Math.atan2(var1, var5) * 180.0D / 3.141592653589793D));
    this.lastPitch = (this.pitch = (float)(Math.atan2(var3, var10) * 180.0D / 3.141592653589793D));
  }
  



  public void F_()
  {
    super.F_();
    
    if ((!this.world.isStatic) && ((this.player == null) || (this.player.dead)))
    {
      die();
    }
    
    if (this.inGround)
    {
      int var1 = this.world.getTypeId(this.xTile, this.yTile, this.zTile);
      
      if (var1 == this.inTile)
      {
        die();
        return;
      }
      
      this.inGround = false;
      this.motX *= this.random.nextFloat() * 0.2F;
      this.motY *= this.random.nextFloat() * 0.2F;
      this.motZ *= this.random.nextFloat() * 0.2F;
    }
    else
    {
      this.ticksInAir += 1;
    }
    
    float var19 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
    
    for (this.pitch = ((float)(Math.atan2(this.motY, var19) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
    



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
    

    if (h_())
    {
      for (int var2 = 0; var2 < 4; var2++)
      {
        float var3 = 0.25F;
        this.world.a("smoke", this.locX - this.motX * var3, this.locY - this.motY * var3, this.locZ - this.motZ * var3, this.motX, this.motY, this.motZ);
      }
      
      this.world.makeSound(this, "random.fizzle", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
      die();
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    setPosition(this.locX, this.locY, this.locZ);
    
    if ((this.locY >= 127.0D) && (this.motY > 0.0D))
    {
      if (EEProxy.getWorldInfo(this.world).hasStorm())
      {
        if (EEBase.consumeKleinStarPoint(this.player, 64))
        {
          if (!EEProxy.getWorldInfo(this.world).isThundering())
          {
            EEProxy.getWorldInfo(this.world).setThundering(true);
            EEProxy.getWorldInfo(this.world).setThunderDuration(300);
          }
          else
          {
            EEProxy.getWorldInfo(this.world).setThunderDuration(EEProxy.getWorldInfo(this.world).getThunderDuration() + 300);
          }
          
          die();
        }
        else if (EEBase.Consume(new ItemStack(Item.REDSTONE, 1), this.player, true))
        {
          if (!EEProxy.getWorldInfo(this.world).isThundering())
          {
            EEProxy.getWorldInfo(this.world).setThundering(true);
            EEProxy.getWorldInfo(this.world).setThunderDuration(300);
          }
          else
          {
            EEProxy.getWorldInfo(this.world).setThunderDuration(EEProxy.getWorldInfo(this.world).getThunderDuration() + 300);
          }
          
          die();
        }
        else
        {
          die();
        }
        
      }
      else {
        die();
      }
    }
    
    if (this.ticksInAir >= 3)
    {
      int var2 = MathHelper.floor(this.locX);
      int var20 = MathHelper.floor(this.locY);
      int var4 = MathHelper.floor(this.locZ);
      Vec3D var5 = Vec3D.create(this.locX, this.locY, this.locZ);
      Vec3D var6 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      MovingObjectPosition var7 = this.world.rayTrace(var5, var6, true);
      var5 = Vec3D.create(this.locX, this.locY, this.locZ);
      var6 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      Entity var8 = null;
      List var9 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double var10 = 0.0D;
      

      for (int var12 = 0; var12 < var9.size(); var12++)
      {
        Entity var13 = (Entity)var9.get(var12);
        
        if (var13.o_())
        {
          float var14 = 0.3F;
          AxisAlignedBB var15 = var13.boundingBox.grow(var14, var14, var14);
          MovingObjectPosition var16 = var15.a(var5, var6);
          
          if (var16 != null)
          {
            double var17 = var5.b(var16.pos);
            
            if ((var17 < var10) || (var10 == 0.0D))
            {
              var8 = var13;
              var10 = var17;
            }
          }
        }
      }
      
      if (var8 != null)
      {
        var7 = new MovingObjectPosition(var8);
      }
      
      int var12;
      
      if (var7 != null)
      {
        if (var7.type == net.minecraft.server.EnumMovingObjectType.ENTITY)
        {
          var2 = MathHelper.floor(var7.entity.locX);
          var20 = MathHelper.floor(var7.entity.locY);
          var4 = MathHelper.floor(var7.entity.locZ);
          
          if (this.world.isChunkLoaded(var2, var20, var4))
          {
            if (EEProxy.getWorldInfo(this.world).isThundering())
            {
              if (ConsumeGSD(2))
              {
                callLightning(var2, var20, var4);
                
                for (var12 = 0; var12 <= this.world.random.nextInt(3); var12++)
                {
                  callLightning(var2, var20, var4);
                }
              }
            }
            else if (EEProxy.getWorldInfo(this.world).hasStorm())
            {
              if (ConsumeGSD(2))
              {
                callLightning(var2, var20, var4);
              }
            }
            else if (ConsumeRSD(1))
            {
              var7.entity.motX += this.motX * 2.0D;
              var7.entity.motY += 1.0D;
              var7.entity.motZ += this.motZ * 2.0D;
            }
          }
          else if (ConsumeRSD(1))
          {
            var7.entity.motX += this.motX * 2.0D;
            var7.entity.motY += 1.0D;
            var7.entity.motZ += this.motZ * 2.0D;
          }
          
          die();
        }
        
        if (EEProxy.getWorldInfo(this.world).isThundering())
        {
          if ((this.world.isChunkLoaded(var2, var20, var4)) && (ConsumeGSD(2)))
          {
            callLightning(var2, var20, var4);
            
            for (var12 = 0; var12 <= this.world.random.nextInt(3); var12++)
            {
              callLightning(var2, var20, var4);
            }
          }
        }
        else if ((EEProxy.getWorldInfo(this.world).hasStorm()) && (this.world.isChunkLoaded(var2, var20, var4)) && (ConsumeGSD(2)))
        {
          callLightning(var2, var20, var4);
        }
        
        die();
      }
    }
  }
  
  private boolean ConsumeRSD(int var1)
  {
    return EEBase.Consume(new ItemStack(Item.REDSTONE, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 64 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 2, 1), this.player, true);
  }
  
  private boolean ConsumeGSD(int var1)
  {
    return EEBase.Consume(new ItemStack(Item.REDSTONE, var1 * 12), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 6, 0), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, var1 * 2), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 384 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 24, 1), this.player, true);
  }
  
  public void callLightning(int var1, int var2, int var3)
  {
    this.world.strikeLightning(new net.minecraft.server.EntityWeatherLighting(this.world, var1, var2, var3));
  }
  


  public void b(NBTTagCompound var1) {}
  


  public void a(NBTTagCompound var1) {}
  


  public float getShadowSize()
  {
    return 0.0F;
  }
}
