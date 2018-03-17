package net.minecraft.server;

import java.util.Random;

public class WM_EntityKnife extends WM_EntityProjectile
{
  protected ItemStack thrownItem;
  private int soundTimer;
  
  public WM_EntityKnife(World paramWorld)
  {
    super(paramWorld);
  }
  
  public WM_EntityKnife(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public WM_EntityKnife(World paramWorld, EntityLiving paramEntityLiving, ItemStack paramItemStack)
  {
    this(paramWorld);
    this.thrownItem = paramItemStack;
    this.shootingEntity = paramEntityLiving;
    this.doesArrowBelongToPlayer = (paramEntityLiving instanceof EntityHuman);
    this.soundTimer = 0;
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.1D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    this.height = 0.0F;
    this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, 0.8F, 3.0F);
  }
  



  public void F_()
  {
    super.F_();
    
    if ((this.inGround) || (this.beenInGround))
    {
      return;
    }
    
    this.pitch -= 70.0F;
    
    if (this.soundTimer >= 3)
    {
      if (!a(Material.WATER))
      {
        this.world.makeSound(this, "random.bow", 0.6F, 1.0F / (this.random.nextFloat() * 0.2F + 0.6F + this.ticksInAir / 15.0F));
      }
      
      this.soundTimer = 0;
    }
    
    this.soundTimer += 1;
  }
  
  public void onEntityHit(Entity paramEntity)
  {
    if (this.world.isStatic)
    {
      return;
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
    
    if (paramEntity.damageEntity(localDamageSource, this.thrownItem.a(paramEntity)))
    {
      if (this.thrownItem.getData() + 2 > this.thrownItem.i())
      {
        this.thrownItem.count -= 1;
        die();
      }
      else
      {
        this.thrownItem.damage(2, null);
        setVelocity(0.2D * this.random.nextDouble() - 0.1D, 0.2D * this.random.nextDouble() - 0.1D, 0.2D * this.random.nextDouble() - 0.1D);
      }
      
    }
    else {
      bounceBack();
    }
  }
  
  public boolean aimRotation()
  {
    return this.beenInGround;
  }
  
  public int getMaxArrowShake()
  {
    return 4;
  }
  
  public float getGravity()
  {
    return 0.03F;
  }
  
  public ItemStack getPickupItem()
  {
    return this.thrownItem;
  }
  



  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    
    if (this.thrownItem != null)
    {
      paramNBTTagCompound.setCompound("thrownItem", this.thrownItem.save(new NBTTagCompound()));
    }
  }
  



  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.thrownItem = ItemStack.a(paramNBTTagCompound.getCompound("thrownItem"));
  }
}
