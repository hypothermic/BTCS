package ee;

import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityBlaze;
import net.minecraft.server.EntityCaveSpider;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityEnderman;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityIronGolem;
import net.minecraft.server.EntityMagmaCube;
import net.minecraft.server.EntityMushroomCow;
import net.minecraft.server.EntityOcelot;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySlime;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.EnumMovingObjectType;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;
import net.minecraft.server.WorldProvider;

public class EntityPhilosopherEssence extends Entity
{
  private EntityHuman player;
  private int xTile;
  private int yTile;
  private int zTile;
  private int inTile;
  private int yawDir;
  private int ticksInAir;
  public int chargeLevel;
  public static boolean grab = false;
  private boolean inGround;
  
  public EntityPhilosopherEssence(World var1)
  {
    super(var1);
    this.bf = true;
    b(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
  }
  
  public EntityPhilosopherEssence(World var1, double var2, double var4, double var6)
  {
    this(var1);
    setPosition(var2, var4, var6);
  }
  
  public EntityPhilosopherEssence(World var1, EntityHuman var2, int var3)
  {
    super(var1);
    this.chargeLevel = var3;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inGround = false;
    this.player = var2;
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
    this.ticksInAir += 1;
    
    if (this.ticksInAir >= 120)
    {
      die();
    }
    
    if (this.ticksInAir >= 3)
    {
      Vec3D var17 = Vec3D.create(this.locX, this.locY, this.locZ);
      Vec3D var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      MovingObjectPosition var4 = this.world.rayTrace(var17, var18, true);
      var17 = Vec3D.create(this.locX, this.locY, this.locZ);
      var18 = Vec3D.create(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
      Entity var5 = null;
      List var6 = this.world.getEntities(this, this.boundingBox.a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
      double var7 = 0.0D;
      
      for (int var9 = 0; var9 < var6.size(); var9++)
      {
        Entity var10 = (Entity)var6.get(var9);
        
        if (var10.o_())
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
        if (var4.type == EnumMovingObjectType.ENTITY)
        {
          transmuteMob(var4.entity);
          die();
        }
        else if (var4.type == EnumMovingObjectType.TILE)
        {
          net.minecraft.server.TileEntity var19 = this.world.getTileEntity(var4.b, var4.c, var4.d);
          
          if ((var19 instanceof TilePedestal))
          {
            if (this.player != null)
            {
              ((TilePedestal)var19).activate(this.player);
            }
            
            die();
          }
          else
          {
            die();
          }
        }
      }
    }
  }
  
  public void transmuteMob(Entity var1)
  {
    Object var2 = null;
    int var3 = this.world.random.nextInt(100);
    
    if (EEBase.isNeutralEntity(var1))
    {
      if ((var1 instanceof net.minecraft.server.EntitySnowman))
      {
        var2 = new EntityIronGolem(this.world);
      }
      else if (!(var1 instanceof EntityIronGolem))
      {
        if ((var3 < 10) && (!(var1 instanceof EntityWolf)))
        {
          var2 = new EntityWolf(this.world);
        }
        else if ((var3 < 30) && (!(var1 instanceof EntityChicken)))
        {
          var2 = new EntityChicken(this.world);
        }
        else if ((var3 < 50) && (!(var1 instanceof EntityCow)))
        {
          var2 = new EntityCow(this.world);
        }
        else if ((var3 < 70) && (!(var1 instanceof EntityPig)))
        {
          var2 = new EntityPig(this.world);
        }
        else if ((var3 < 90) && (!(var1 instanceof EntitySheep)))
        {
          var2 = new EntitySheep(this.world);
          ((EntitySheep)var2).setColor(this.world.random.nextInt(16));
        }
        else if ((var3 < 94) && (!(var1 instanceof EntityVillager)))
        {
          var2 = new EntityVillager(this.world);
        }
        else if ((var3 < 97) && (!(var1 instanceof EntityMushroomCow)))
        {
          var2 = new EntityMushroomCow(this.world);
        }
        else if ((var3 < 100) && (!(var1 instanceof EntityOcelot)))
        {
          var2 = new EntityOcelot(this.world);
        }
      }
    }
    else if (EEBase.isHostileEntity(var1))
    {
      if (!this.world.worldProvider.d)
      {
        if ((var3 < 15) && (!(var1 instanceof EntityCreeper)))
        {
          var2 = new EntityCreeper(this.world);
        }
        else if ((var3 < 30) && (!(var1 instanceof EntitySkeleton)))
        {
          var2 = new EntitySkeleton(this.world);
        }
        else if ((var3 < 45) && (!(var1 instanceof EntitySpider)))
        {
          var2 = new EntitySpider(this.world);
        }
        else if ((var3 < 60) && (!(var1 instanceof EntityZombie)))
        {
          var2 = new EntityZombie(this.world);
        }
        else if ((var3 < 75) && (!(var1 instanceof EntitySlime)))
        {
          var2 = new EntitySlime(this.world);
          ((EntitySlime)var2).setSize(1 << this.world.random.nextInt(3));
        }
        else if ((var3 < 90) && (!(var1 instanceof EntityEnderman)))
        {
          var2 = new EntityEnderman(this.world);
        }
        else if ((var3 < 100) && (!(var1 instanceof EntityCaveSpider)))
        {
          var2 = new EntityCaveSpider(this.world);
        }
      }
      else if ((var3 < 50) && (!(var1 instanceof EntityPigZombie)))
      {
        var2 = new EntityPigZombie(this.world);
      }
      else if ((var3 < 80) && (!(var1 instanceof EntityGhast)))
      {
        var2 = new EntityGhast(this.world);
      }
      else if ((var3 < 95) && (!(var1 instanceof EntityMagmaCube)))
      {
        var2 = new EntityMagmaCube(this.world);
      }
      else if ((var3 < 100) && (!(var1 instanceof EntityBlaze)))
      {
        var2 = new EntityBlaze(this.world);
      }
    }
    
    if (var2 != null)
    {
      int var4 = 1;
      

      if ((var2 instanceof EntityIronGolem))
      {
        int var5 = 4 * EEMaps.getEMC(Block.IRON_BLOCK.id) + EEMaps.getEMC(Block.PUMPKIN.id);
        int var6 = 2 * EEMaps.getEMC(Block.SNOW.id) + EEMaps.getEMC(Block.PUMPKIN.id);
        var4 = Math.round((var5 - var6) / EEMaps.getEMC(Item.GLOWSTONE_DUST.id));
      }
      
      if (ConsumeGlowstone(var4, true))
      {
        ((Entity)var2).setPosition(var1.locX, var1.locY, var1.locZ);
        var1.die();
        this.world.makeSound((Entity)var2, "transmute", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.world.addEntity((Entity)var2);
        
        for (int var5 = 0; var5 < 4; var5++)
        {
          this.world.a("largesmoke", ((Entity)var2).locX, ((Entity)var2).locY, ((Entity)var2).locZ, 0.0D, 0.1D, 0.0D);
        }
      }
    }
  }
  
  public boolean ConsumeRedstone(int var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.REDSTONE, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 64 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 2, 1), this.player, true);
  }
  
  public boolean ConsumeGlowstone(int var1, boolean var2)
  {
    return EEBase.Consume(new ItemStack(Item.REDSTONE, var1 * 12), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 6, 0), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.SULPHUR, var1 * 2), this.player, false) ? true : EEBase.Consume(new ItemStack(Item.GLOWSTONE_DUST, var1), this.player, false) ? true : EEBase.consumeKleinStarPoint(this.player, 384 * var1) ? true : EEBase.Consume(new ItemStack(Item.COAL, var1 * 24, 1), this.player, true);
  }
  


  public void b(NBTTagCompound var1) {}
  


  public void a(NBTTagCompound var1) {}
  


  public float getShadowSize()
  {
    return 0.0F;
  }
}
