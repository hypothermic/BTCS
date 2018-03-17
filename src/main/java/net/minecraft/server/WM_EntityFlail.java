package net.minecraft.server;

import java.util.Random;

public class WM_EntityFlail
  extends WM_EntityProjectile
{
  private WM_ItemFlail itemFlail;
  public EnumToolMaterial enumToolMaterial;
  public boolean isSwinging;
  private int weaponDamage;
  private double distanceTotal;
  private double distanceX;
  private double distanceY;
  private double distanceZ;
  
  public WM_EntityFlail(World paramWorld)
  {
    super(paramWorld);
    setSourceItem(mod_WeaponMod.flailWood.id);
    this.distanceTotal = 0.0D;
  }
  
  public WM_EntityFlail(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public WM_EntityFlail(World paramWorld, EntityLiving paramEntityLiving, int paramInt)
  {
    this(paramWorld);
    setSourceItem(paramInt);
    this.shootingEntity = paramEntityLiving;
    this.doesArrowBelongToPlayer = (paramEntityLiving instanceof EntityHuman);
    this.distanceTotal = 0.0D;
    setPositionRotation(paramEntityLiving.locX, paramEntityLiving.locY + paramEntityLiving.getHeadHeight(), paramEntityLiving.locZ, paramEntityLiving.yaw, paramEntityLiving.pitch);
    this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    this.locY -= 0.4D;
    this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.locX, this.locY, this.locZ);
    swing();
    sendItemData();
  }
  
  public void setSourceItem(int paramInt)
  {
    this.itemFlail = ((Item.byId[paramInt] instanceof WM_ItemFlail) ? (WM_ItemFlail)Item.byId[paramInt] : null);
    
    if (this.itemFlail != null)
    {
      this.enumToolMaterial = this.itemFlail.enumToolMaterial;
    }
    else
    {
      this.enumToolMaterial = EnumToolMaterial.WOOD;
    }
    
    this.weaponDamage = ((int)(4.0F + this.enumToolMaterial.c() * 2.0F));
  }
  
  public void sendItemData()
  {
    Packet250CustomPayload localPacket250CustomPayload = new Packet250CustomPayload();
    localPacket250CustomPayload.tag = "wpnmodFlail";
    localPacket250CustomPayload.data = new byte[8];
    localPacket250CustomPayload.length = localPacket250CustomPayload.data.length;
    localPacket250CustomPayload.data[0] = ((byte)(this.id & 0xFF));
    localPacket250CustomPayload.data[1] = ((byte)(this.id >> 8 & 0xFF));
    localPacket250CustomPayload.data[2] = ((byte)(this.id >> 16 & 0xFF));
    localPacket250CustomPayload.data[3] = ((byte)(this.id >> 24 & 0xFF));
    localPacket250CustomPayload.data[4] = ((byte)(this.itemFlail.id & 0xFF));
    localPacket250CustomPayload.data[5] = ((byte)(this.itemFlail.id >> 8 & 0xFF));
    localPacket250CustomPayload.data[6] = ((byte)(this.itemFlail.id >> 16 & 0xFF));
    localPacket250CustomPayload.data[7] = ((byte)(this.itemFlail.id >> 24 & 0xFF));
    ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendAll(localPacket250CustomPayload);
  }
  



  public void F_()
  {
    super.F_();
    
    if (this.shootingEntity != null)
    {
      this.distanceX = (this.shootingEntity.locX - this.locX);
      this.distanceY = (this.shootingEntity.locY - this.locY);
      this.distanceZ = (this.shootingEntity.locZ - this.locZ);
      this.distanceTotal = Math.sqrt(this.distanceX * this.distanceX + this.distanceY * this.distanceY + this.distanceZ * this.distanceZ);
      
      if (this.distanceTotal > 3.0D)
      {
        returnToOwner(this.shootingEntity, true);
      }
      
      if ((this.shootingEntity instanceof EntityHuman))
      {
        ItemStack localItemStack = ((EntityHuman)this.shootingEntity).U();
        
        if ((localItemStack == null) || (localItemStack.getItem() != this.itemFlail))
        {
          pickUpByOwner();
        }
      }
    }
    
    if (this.inGround)
    {
      this.inGround = false;
      return;
    }
    

    returnToOwner(this.shootingEntity, false);
  }
  


  public void returnToOwner(Entity paramEntity, boolean paramBoolean)
  {
    if (paramEntity == null)
    {
      return;
    }
    
    if (paramBoolean)
    {
      this.inGround = false;
    }
    
    double d1 = this.shootingEntity.locX;
    double d2 = this.shootingEntity.boundingBox.b + 0.4000000059604645D;
    double d3 = this.shootingEntity.locZ;
    float f1 = 27.0F;
    float f2 = 2.0F;
    d1 += -Math.sin((this.shootingEntity.yaw + f1) / 180.0F * 3.141592653589793D) * Math.cos(this.shootingEntity.pitch / 180.0F * 3.141592653589793D) * f2;
    d3 += Math.cos((this.shootingEntity.yaw + f1) / 180.0F * 3.141592653589793D) * Math.cos(this.shootingEntity.pitch / 180.0F * 3.141592653589793D) * f2;
    this.distanceX = (d1 - this.locX);
    this.distanceY = (d2 - this.locY);
    this.distanceZ = (d3 - this.locZ);
    this.distanceTotal = Math.sqrt(this.distanceX * this.distanceX + this.distanceY * this.distanceY + this.distanceZ * this.distanceZ);
    
    if (this.distanceTotal > 3.0D)
    {
      this.locX = d1;
      this.locY = d2;
      this.locZ = d3;
    }
    else if (this.distanceTotal > 2.5D)
    {
      this.isSwinging = false;
      this.motX *= -0.5D;
      this.motY *= -0.5D;
      this.motZ *= -0.5D;
    }
    
    if (!this.isSwinging)
    {
      float f3 = 0.2F;
      this.motX = (this.distanceX * f3 * this.distanceTotal);
      this.motY = (this.distanceY * f3 * this.distanceTotal);
      this.motZ = (this.distanceZ * f3 * this.distanceTotal);
    }
  }
  
  public void pickUpByOwner()
  {
    die();
    this.itemFlail.setThrown(false);
  }
  
  public void swing()
  {
    if (this.isSwinging)
    {
      return;
    }
    

    this.world.makeSound(this.shootingEntity, "random.bow", 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));
    this.motX = (-MathHelper.sin(this.shootingEntity.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.shootingEntity.pitch / 180.0F * 3.1415927F));
    this.motY = (-MathHelper.sin(this.shootingEntity.pitch / 180.0F * 3.1415927F));
    this.motZ = (MathHelper.cos(this.shootingEntity.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.shootingEntity.pitch / 180.0F * 3.1415927F));
    setArrowHeading(this.motX, this.motY, this.motZ, 0.75F, 3.0F);
    this.isSwinging = true;
    this.inGround = false;
  }
  


  public void onEntityHit(Entity paramEntity)
  {
    if (paramEntity == this.shootingEntity)
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
      localDamageSource = DamageSource.mobAttack((EntityLiving)this.shootingEntity);
    }
    
    if (paramEntity.damageEntity(localDamageSource, this.weaponDamage))
    {
      playHitSound();
      returnToOwner(this.shootingEntity, true);
    }
    else
    {
      bounceBack();
    }
  }
  
  public void bounceBack()
  {
    this.motX *= -0.8D;
    this.motY *= -0.8D;
    this.motZ *= -0.8D;
    this.yaw += 180.0F;
    this.lastYaw += 180.0F;
    this.ticksInAir = 0;
  }
  
  public void playHitSound()
  {
    if (this.inGround)
    {
      return;
    }
    

    this.world.makeSound(this, "damage.hurtflesh", 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
  }
  


  public float getGravity()
  {
    return 0.03F;
  }
  



  public void b(NBTTagCompound paramNBTTagCompound)
  {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setByte("damage", (byte)this.weaponDamage);
  }
  



  public void a(NBTTagCompound paramNBTTagCompound)
  {
    super.a(paramNBTTagCompound);
    this.weaponDamage = paramNBTTagCompound.getByte("damage");
  }
  



  public void a_(EntityHuman paramEntityHuman) {}
  


  public int getMaxArrowShake()
  {
    return 0;
  }
  
  public float getShadowSize()
  {
    return 0.2F;
  }
}
