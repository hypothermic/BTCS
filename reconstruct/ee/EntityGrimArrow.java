package ee;

import java.util.List;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityCreature;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.Item;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class EntityGrimArrow extends EntityEEProjectile
{
  public String name;
  public int itemId;
  public int craftingResults;
  public Object tip;
  public String spriteFile;
  public Entity target;
  public boolean homing;
  public static final int ticksBeforeCollidable = 2;
  public static int[][] candidates = { new int[3], { 0, -1 }, { 0, 1 }, { -1 }, { 1 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, -1 }, { -1, 0, -1 }, { -1, 0, 1 }, { -1, 1 }, { 0, -1, -1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, 1, 1 }, { 1, -1 }, { 1, 0, -1 }, { 1, 0, 1 }, { 1, 1 }, { -1, -1, -1 }, { -1, -1, 1 }, { -1, 1, -1 }, { -1, 1, 1 }, { 1, -1, -1 }, { 1, -1, 1 }, { 1, 1, -1 }, { 1, 1, 1 } };
  
  public void b()
  {
    super.b();
    this.homing = true;
    this.name = "Arrow";
    this.itemId = Item.ARROW.id;
    this.craftingResults = 4;
    this.tip = Item.FLINT;
    this.spriteFile = null;
    this.curvature = 0.03F;
    this.slowdown = 0.99F;
    this.precision = (0.35F + this.world.random.nextFloat() * 5.0F);
    this.speed = 1.5F;
    this.height = 0.0F;
    b(0.5F, 0.5F);
    this.item = new net.minecraft.server.ItemStack(this.itemId, 1, 0);
  }
  
  public EntityGrimArrow newArrow(World var1, EntityLiving var2)
  {
    try
    {
      return (EntityGrimArrow)getClass().getConstructor(new Class[] { World.class, EntityLiving.class }).newInstance(new Object[] { var1, var2 });
    }
    catch (Throwable var4)
    {
      throw new RuntimeException("Could not construct arrow instance", var4);
    }
  }
  
  public EntityGrimArrow newArrow(World var1)
  {
    try
    {
      return (EntityGrimArrow)getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { var1 });
    }
    catch (Throwable var3)
    {
      throw new RuntimeException("Could not construct arrow instance", var3);
    }
  }
  
  public void setupConfig() {}
  
  public EntityGrimArrow(World var1)
  {
    super(var1);
    this.homing = true;
    b();
  }
  
  public EntityGrimArrow(World var1, double var2, double var4, double var6)
  {
    super(var1, var2, var4, var6);
    this.homing = true;
    b();
  }
  
  public EntityGrimArrow(World var1, EntityLiving var2)
  {
    super(var1, var2);
    this.homing = (this.shooter instanceof EntityHuman);
    b();
  }
  
  public boolean attackEntityFrom(Entity var1, int var2)
  {
    if (var2 < 8)
    {
      return false;
    }
    

    if ((!this.inGround) && (var1 != null))
    {
      Vec3D var3 = var1.aJ();
      
      if (var3 != null)
      {
        if (this.homing)
        {
          this.target = this.shooter;
          
          if ((var1 instanceof EntityLiving))
          {
            this.shooter = ((EntityLiving)var1);
          }
        }
        
        this.motX = var3.a;
        this.motY = var3.b;
        this.motZ = var3.c;
        return true;
      }
    }
    
    return false;
  }
  

  public boolean isInSight(Entity var1)
  {
    return this.world.a(Vec3D.create(this.locX, this.locY + getHeadHeight(), this.locZ), Vec3D.create(var1.locX, var1.locY + var1.getHeadHeight(), var1.locZ)) == null;
  }
  
  public void handleMotionUpdate()
  {
    if (!this.homing)
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
    }
    
    if (this.target == null)
    {
      this.motY -= this.curvature;
    }
  }
  
  public void tickFlying()
  {
    if ((this.ticksFlying > 3) && (this.homing))
    {
      if ((this.target != null) && (!this.target.dead) && ((!(this.target instanceof EntityLiving)) || (((EntityLiving)this.target).deathTicks == 0)))
      {
        if (((this.shooter instanceof EntityHuman)) && (!isInSight(this.target)))
        {
          this.target = getTarget(this.locX, this.locY, this.locZ, 16.0D);
        }
      }
      else if ((this.shooter instanceof EntityCreature))
      {
        this.target = ((EntityCreature)this.shooter).I();
      }
      else
      {
        this.target = getTarget(this.locX, this.locY, this.locZ, 16.0D);
      }
      
      if (this.target != null)
      {
        double var1 = this.target.boundingBox.a + (this.target.boundingBox.d - this.target.boundingBox.a) / 2.0D - this.locX;
        double var3 = this.target.boundingBox.b + (this.target.boundingBox.e - this.target.boundingBox.b) / 2.0D - this.locY;
        double var5 = this.target.boundingBox.c + (this.target.boundingBox.f - this.target.boundingBox.c) / 2.0D - this.locZ;
        setArrowHeading(var1, var3, var5, this.speed, this.precision);
      }
    }
  }
  



  public boolean o_()
  {
    return (!this.dead) && (!this.inGround) && (this.ticksFlying >= 2);
  }
  
  public boolean canBeShot(Entity var1)
  {
    return (!(var1 instanceof EntityGrimArrow)) && (super.canBeShot(var1));
  }
  
  private Entity getTarget(double var1, double var3, double var5, double var7)
  {
    float var9 = -1.0F;
    Entity var10 = null;
    List var11 = this.world.getEntities(this, this.boundingBox.grow(var7, var7, var7));
    
    for (int var12 = 0; var12 < var11.size(); var12++)
    {
      Entity var13 = (Entity)var11.get(var12);
      
      if (canTarget(var13))
      {
        float var14 = var13.i(this);
        
        if ((var9 == -1.0F) || (var14 < var9))
        {
          var9 = var14;
          var10 = var13;
        }
      }
    }
    
    return var10;
  }
  
  public boolean canTarget(Entity var1)
  {
    return ((var1 instanceof EntityLiving)) && (var1 != this.shooter) && (isInSight(var1));
  }
}
