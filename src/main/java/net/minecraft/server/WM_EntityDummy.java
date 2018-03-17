package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WM_EntityDummy extends Entity
{
  private int durability;
  public int dummyCurrentDamage;
  public int dummyTimeSinceHit;
  public int dummyRockDirection;
  
  public WM_EntityDummy(World paramWorld)
  {
    super(paramWorld);
    this.dummyCurrentDamage = 0;
    this.dummyTimeSinceHit = 0;
    this.dummyRockDirection = 1;
    this.bf = true;
    this.pitch = -20.0F;
    c(this.yaw, this.pitch);
    b(1.5F, 1.9F);
    this.height = 0.41F;
    this.durability = 50;
  }
  
  public WM_EntityDummy(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2 + this.height, paramDouble3);
    this.motX = 0.0D;
    this.motY = 0.0D;
    this.motZ = 0.0D;
    this.lastX = paramDouble1;
    this.lastY = paramDouble2;
    this.lastZ = paramDouble3;
  }
  



  protected void b() {}
  



  public AxisAlignedBB b_(Entity paramEntity)
  {
    return paramEntity.boundingBox;
  }
  



  public AxisAlignedBB h()
  {
    return this.boundingBox;
  }
  



  public boolean e_()
  {
    return false;
  }
  



  public boolean damageEntity(DamageSource paramDamageSource, int paramInt)
  {
    if ((this.world.isStatic) || (this.dead) || (paramInt <= 0))
    {
      return false;
    }
    
    this.dummyRockDirection = (-this.dummyRockDirection);
    this.dummyTimeSinceHit = 10;
    this.dummyCurrentDamage += paramInt * 5;
    
    if (this.dummyCurrentDamage > 50)
    {
      this.dummyCurrentDamage = 50;
    }
    
    if (!(paramDamageSource instanceof EntityDamageSource))
    {
      this.durability -= paramInt;
    }
    else if ((paramDamageSource instanceof WM_WeaponDamageSource))
    {
      Entity localEntity = ((WM_WeaponDamageSource)paramDamageSource).getProjectile();
      
      if (Math.sqrt(localEntity.motX * localEntity.motX + localEntity.motY * localEntity.motY + localEntity.motZ * localEntity.motZ) > 0.5D)
      {
        localEntity.motX *= 0.10000000149011612D;
        localEntity.motY *= 0.10000000149011612D;
        localEntity.motZ *= 0.10000000149011612D;
        playRandomHitSound();
      }
      else
      {
        localEntity.motX = (this.random.nextFloat() - 0.5F);
        localEntity.motY = (this.random.nextFloat() - 0.5F);
        localEntity.motZ = (this.random.nextFloat() - 0.5F);
      }
    }
    else
    {
      playRandomHitSound();
    }
    
    if (this.durability <= 0)
    {
      dropAsItem(true);
    }
    
    aW();
    return false;
  }
  
  public void playRandomHitSound()
  {
    int i = this.random.nextInt(2);
    
    if (i == 0)
    {
      this.world.makeSound(this, "step.cloth", 0.7F, 1.0F / this.random.nextFloat() * 0.2F + 0.4F);
    }
    else if (i == 1)
    {
      this.world.makeSound(this, "step.wood", 0.7F, 1.0F / this.random.nextFloat() * 0.2F + 0.2F);
    }
  }
  
  public void performHurtAnimation()
  {
    this.dummyRockDirection = (-this.dummyRockDirection);
    this.dummyTimeSinceHit = 10;
    this.dummyCurrentDamage += this.dummyCurrentDamage * 10;
  }
  



  public boolean o_()
  {
    return !this.dead;
  }
  



  public void F_()
  {
    super.F_();
    
    if (this.dummyTimeSinceHit > 0)
    {
      this.dummyTimeSinceHit -= 1;
    }
    
    if (this.dummyCurrentDamage > 0)
    {
      this.dummyCurrentDamage -= 1;
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    
    if (this.onGround)
    {
      this.motX = 0.0D;
      this.motY = 0.0D;
      this.motZ = 0.0D;
    }
    else
    {
      this.motX *= 0.99D;
      this.motZ *= 0.99D;
      this.motY -= 0.05D;
      this.fallDistance = ((float)(this.fallDistance + -this.motY));
    }
    
    c(this.yaw, this.pitch);
    move(0.0D, this.motY, 0.0D);
    List localList = this.world.getEntities(this, this.boundingBox.grow(0.2D, 0.0D, 0.2D));
    
    if ((localList != null) && (localList.size() > 0))
    {
      for (int i = 0; i < localList.size(); i++)
      {
        Entity localEntity = (Entity)localList.get(i);
        
        if ((localEntity != this.passenger) && (localEntity.e_()))
        {
          localEntity.collide(this);
        }
      }
    }
    
    for (int i = 0; i < 4; i++)
    {
      int j = MathHelper.floor(this.locX + (i % 2 - 0.5D) * 0.8D);
      int k = MathHelper.floor(this.locY);
      int m = MathHelper.floor(this.locZ + (i / 2 - 0.5D) * 0.8D);
      
      if (this.world.getTypeId(j, k, m) == Block.SNOW.id)
      {
        this.world.setTypeId(j, k, m, 0);
      }
    }
  }
  



  protected void a(float paramFloat)
  {
    super.a(paramFloat);
    
    if (!this.onGround)
    {
      return;
    }
    

    int i = MathHelper.d(paramFloat);
    damageEntity(DamageSource.FALL, i);
  }
  


  public void dropAsItem(boolean paramBoolean)
  {
    if (this.world.isStatic)
    {
      return;
    }
    
    if (paramBoolean)
    {
      for (int i = 0; i < this.random.nextInt(8); i++)
      {
        a(Item.LEATHER.id, 1, 0.0F);
      }
      
    }
    else {
      a(mod_WeaponMod.dummy.id, 1, 0.0F);
    }
    
    die();
  }
  



  public boolean b(EntityHuman paramEntityHuman)
  {
    ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
    
    if (localItemStack == null)
    {
      dropAsItem(false);
      return true;
    }
    
    if ((!(localItemStack.getItem() instanceof WM_ItemWeaponMod)) && (!(localItemStack.getItem() instanceof ItemSword)) && (!(localItemStack.getItem() instanceof ItemBow)))
    {
      dropAsItem(false);
      return true;
    }
    

    return false;
  }
  





  protected void b(NBTTagCompound paramNBTTagCompound) {}
  




  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    setPosition(this.locX, this.locY, this.locZ);
    c(this.yaw, this.pitch);
  }
}
