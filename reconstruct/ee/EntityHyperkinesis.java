package ee;

import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityHyperkinesis extends Entity
{
  private float powerLevel;
  private int xTile;
  private int yTile;
  private int zTile;
  private int inTile;
  private int yawDir;
  private int cost;
  public static boolean grab = false;
  private boolean inGround;
  private EntityHuman player;
  private int ticksInAir;
  
  public EntityHyperkinesis(World var1)
  {
    super(var1);
    this.bf = true;
    b(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
  }
  
  public EntityHyperkinesis(World var1, EntityHuman var2, int var3, int var4)
  {
    super(var1);
    this.player = var2;
    this.powerLevel = var3;
    this.cost = var4;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.ticksInAir = 0;
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
  
  public EntityHyperkinesis(World var1, double var2, double var4, double var6)
  {
    this(var1);
    setPosition(var2, var4, var6);
    this.ticksInAir = 0;
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
    
    float var16 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
    this.yaw = ((float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.141592653589793D));
    
    for (this.pitch = ((float)(Math.atan2(this.motY, var16) * 180.0D / 3.141592653589793D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
    



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
      
      this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
      die();
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.locX += this.motX;
    this.locY += this.motY;
    this.locZ += this.motZ;
    setPosition(this.locX, this.locY, this.locZ);
    
    if (this.ticksInAir >= 3)
    {
      Vec3D var17 = Vec3D.create(this.locX, this.locY, this.locZ);
      Vec3D var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      MovingObjectPosition var4 = this.world.rayTrace(var17, var18, grab);
      var17 = Vec3D.create(this.locX, this.locY, this.locZ);
      var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      
      if (var4 != null)
      {
        var18 = Vec3D.create(var4.pos.a, var4.pos.b, var4.pos.c);
      }
      
      Entity var5 = null;
      List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double var7 = 0.0D;
      

      for (int var9 = 0; var9 < var6.size(); var9++)
      {
        Entity var10 = (Entity)var6.get(var9);
        
        if ((var10.o_()) && (this.ticksInAir >= 2))
        {
          float var11 = 0.3F;
          AxisAlignedBB var12 = var10.boundingBox.grow(var11, var11, var11);
          MovingObjectPosition var13 = var12.a(var17, var18);
          
          if (var13 != null)
          {
            double var14 = var17.b(var13.pos);
            
            if ((var14 < var7) || (var7 == 0.0D))
            {
              var5 = var10;
              var7 = var14;
            }
          }
        }
      }
      
      if (var5 != null)
      {
        var4 = new MovingObjectPosition(var5);
      }
      
      if (var4 != null)
      {
        List var19 = this.world.a(EntityHuman.class, AxisAlignedBB.b(this.locX - (2.0F + this.powerLevel), this.locY - (2.0F + this.powerLevel), this.locZ - (2.0F + this.powerLevel), this.locX + (2.0F + this.powerLevel), this.locY + (2.0F + this.powerLevel), this.locZ + (2.0F + this.powerLevel)));
        
        if (var19.size() > 0)
        {
          for (int var20 = 0; var20 < 4; var20++)
          {
            float var11 = 0.25F;
            this.world.a("smoke", this.locX - this.motX * var11, this.locY - this.motY * var11, this.locZ - this.motZ * var11, this.motX, this.motY, this.motZ);
          }
          
          this.world.makeSound(this, "random.fizz", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
          die();
          return;
        }
        
        if (ConsumeReagent(true))
        {
          explode();
        }
        
        die();
      }
    }
  }
  
  public boolean ConsumeReagent(boolean var1)
  {
    return EEBase.Consume(new ItemStack(Item.REDSTONE, this.cost * 6), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, this.cost * 3, 0), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, this.cost * 2), this.player, var1) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, this.cost), this.player, var1) ? true : EEBase.consumeKleinStarPoint(this.player, this.cost * 384) ? true : net.minecraft.server.EEProxy.isClient(this.world) ? false : EEBase.Consume(new ItemStack(Item.COAL, this.cost * 12, 1), this.player, var1);
  }
  
  private void explode()
  {
    float var1 = 1.0F + 2.0F * (this.powerLevel / 3.0F);
    newCombustion(null, this.locX, this.locY, this.locZ, var1);
  }
  
  public Combustion newCombustion(EntityHuman var1, double var2, double var4, double var6, float var8)
  {
    Combustion var9 = new Combustion(this.world, var1, var2, var4, var6, var8);
    var9.doExplosionA();
    var9.doExplosionB(true);
    return var9;
  }
  


  public void b(NBTTagCompound var1) {}
  


  public void a(NBTTagCompound var1) {}
  


  public float getShadowSize()
  {
    return 0.0F;
  }
}
