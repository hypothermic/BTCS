package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WM_EntityCannon
  extends Entity
{
  private int loadTimer;
  public boolean isLoading;
  public boolean isLoaded;
  public boolean isSuperPowered;
  public int cannonCurrentDamage;
  public int cannonTimeSinceHit;
  public int cannonRockDirection;
  
  public WM_EntityCannon(World paramWorld)
  {
    super(paramWorld);
    this.isLoaded = false;
    this.isLoading = false;
    this.loadTimer = 0;
    this.isSuperPowered = false;
    this.cannonCurrentDamage = 0;
    this.cannonTimeSinceHit = 0;
    this.cannonRockDirection = 1;
    this.bf = true;
    this.pitch = -20.0F;
    c(this.yaw, this.pitch);
    b(1.5F, 1.0F);
    this.height = (this.length / 2.0F);
  }
  
  public WM_EntityCannon(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this(paramWorld);
    setPosition(paramDouble1, paramDouble2 + this.height, paramDouble3);
    this.motX = (this.motY = this.motZ = 0.0D);
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
  



  public double x_()
  {
    return 0.15D;
  }
  



  public boolean damageEntity(DamageSource paramDamageSource, int paramInt)
  {
    if ((this.world.isStatic) || (this.dead))
    {
      return true;
    }
    
    if (((paramDamageSource instanceof EntityDamageSourceIndirect)) && (((EntityDamageSource)paramDamageSource).getEntity().equals(this.passenger)))
    {
      return true;
    }
    
    this.cannonRockDirection = (-this.cannonRockDirection);
    this.cannonTimeSinceHit = 10;
    this.cannonCurrentDamage += paramInt * 5;
    aW();
    
    if (this.cannonCurrentDamage > 100)
    {
      if (this.passenger != null)
      {
        this.passenger.mount(this);
      }
      
      if ((paramInt < 3) && (!paramDamageSource.k()))
      {
        dropItemWithChance(mod_WeaponMod.cannon.id, paramInt, 1, 0.0F);
      }
      else
      {
        for (int i = 0; i < 6; i++)
        {
          dropItemWithChance(Item.IRON_INGOT.id, paramInt, 1, 0.0F);
        }
        
        dropItemWithChance(Item.FLINT.id, paramInt, 1, 0.0F);
        dropItemWithChance(Block.LOG.id, paramInt, 1, 0.0F);
      }
      
      if ((this.isLoaded) || (this.isLoading))
      {
        dropItemWithChance(mod_WeaponMod.cannonBall.id, paramInt, 1, 0.0F);
        dropItemWithChance(Item.SULPHUR.id, paramInt, 1, 0.0F);
      }
      
      die();
    }
    
    return true;
  }
  
  public void dropItemWithChance(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if (this.random.nextInt(paramInt2) < 10)
    {
      a(paramInt1, paramInt3, paramFloat);
    }
  }
  
  public void performHurtAnimation()
  {
    this.cannonRockDirection = (-this.cannonRockDirection);
    this.cannonTimeSinceHit = 10;
    this.cannonCurrentDamage += this.cannonCurrentDamage * 10;
  }
  



  public boolean o_()
  {
    return !this.dead;
  }
  



  public void F_()
  {
    super.F_();
    
    if (this.cannonTimeSinceHit > 0)
    {
      this.cannonTimeSinceHit -= 1;
    }
    
    if (this.cannonCurrentDamage > 0)
    {
      this.cannonCurrentDamage -= this.random.nextInt(2);
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    
    if (this.onGround)
    {
      this.motX *= 0.1D;
      this.motY = 0.0D;
      this.motZ *= 0.1D;
    }
    else
    {
      this.motX *= 0.9D;
      this.motZ *= 0.9D;
      this.motY -= 0.1D;
      this.fallDistance = ((float)(this.fallDistance + -this.motY));
    }
    
    if (this.motY < -0.1D)
    {
      this.passenger = null;
    }
    
    if (this.passenger == null) {}
    
    c(this.yaw, this.pitch);
    move(this.motX, this.motY, this.motZ);
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
    
    if (this.passenger != null)
    {
      if (this.passenger.dead)
      {
        this.passenger = null;
      }
      else if (((this.passenger instanceof EntityLiving)) && (((EntityLiving)this.passenger).aZ))
      {
        shootCannon();
      }
    }
    
    if (this.isLoading)
    {
      handleReloadTime(this.loadTimer);
      this.loadTimer -= 1;
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
    i *= 2;
    damageEntity(DamageSource.FALL, i);
  }
  


  public void handleReloadTime(int paramInt)
  {
    this.loadTimer = paramInt;
    
    if (this.loadTimer > 0)
    {
      if ((this.loadTimer == 80) || (this.loadTimer == 70) || (this.loadTimer == 60))
      {
        this.world.makeSound(this, "tile.piston.in", 0.5F, 1.2F / (this.random.nextFloat() * 0.8F + 0.6F));
      }
      else if (this.loadTimer == 40)
      {
        this.world.makeSound(this, "random.breath", 0.7F, 1.2F / (this.random.nextFloat() * 0.2F + 10.0F));
      }
      
    }
    else {
      setReloadInfo(true, false, 0);
    }
  }
  
  public void shootCannon()
  {
    if (!this.isLoaded)
    {
      return;
    }
    
    if (!this.world.isStatic)
    {
      WM_EntityCannonBall localWM_EntityCannonBall = new WM_EntityCannonBall(this.world, this, this.isSuperPowered);
      this.world.addEntity(localWM_EntityCannonBall);
    }
    
    setReloadInfo(false, false, this.loadTimer);
    shootEffects();
  }
  
  public void shootEffects()
  {
    damageEntity(DamageSource.EXPLOSION, 2);
    
    if (!this.world.isStatic)
    {
      byte[] arrayOfByte = new byte[4];
      arrayOfByte[0] = ((byte)(this.id & 0xFF));
      arrayOfByte[1] = ((byte)(this.id >> 8 & 0xFF));
      arrayOfByte[2] = ((byte)(this.id >> 16 & 0xFF));
      arrayOfByte[3] = ((byte)(this.id >> 24 & 0xFF));
      Packet250CustomPayload localPacket250CustomPayload = new Packet250CustomPayload();
      localPacket250CustomPayload.tag = "wpnmodFireCann";
      localPacket250CustomPayload.length = arrayOfByte.length;
      localPacket250CustomPayload.data = arrayOfByte;
      ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendAll(localPacket250CustomPayload);
    }
  }
  
  public void setReloadInfo(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    this.isLoaded = paramBoolean1;
    this.isLoading = paramBoolean2;
    this.loadTimer = paramInt;
    sendReloadInfo();
  }
  
  private void sendReloadInfo()
  {
    if (this.world.isStatic)
    {
      return;
    }
    

    byte[] arrayOfByte = new byte[8];
    arrayOfByte[0] = ((byte)(this.id & 0xFF));
    arrayOfByte[1] = ((byte)(this.id >> 8 & 0xFF));
    arrayOfByte[2] = ((byte)(this.id >> 16 & 0xFF));
    arrayOfByte[3] = ((byte)(this.id >> 24 & 0xFF));
    arrayOfByte[4] = ((byte)(this.isLoaded ? 1 : 0));
    arrayOfByte[5] = ((byte)(this.isLoading ? 1 : 0));
    arrayOfByte[6] = ((byte)this.loadTimer);
    Packet250CustomPayload localPacket250CustomPayload = new Packet250CustomPayload();
    localPacket250CustomPayload.tag = "wpnmodCannon";
    localPacket250CustomPayload.length = arrayOfByte.length;
    localPacket250CustomPayload.data = arrayOfByte;
    ModLoader.getMinecraftServerInstance().serverConfigurationManager.sendAll(localPacket250CustomPayload);
  }
  


  public void loadCannon()
  {
    if ((this.isLoaded) && (!this.isLoading))
    {
      return;
    }
    

    setReloadInfo(false, true, 100);
  }
  


  public void i_()
  {
    if (this.passenger == null)
    {
      return;
    }
    

    double d1 = -MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.85F;
    double d2 = MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.85F;
    this.passenger.setPosition(this.locX + d1, this.locY + x_() + this.passenger.W(), this.locZ + d2);
  }
  


  public float getShadowSize()
  {
    return 1.0F;
  }
  



  protected void b(NBTTagCompound paramNBTTagCompound)
  {
    paramNBTTagCompound.setFloat("FallDistance", this.fallDistance);
    paramNBTTagCompound.setBoolean("OnGround", this.onGround);
    paramNBTTagCompound.setBoolean("Powered", this.isSuperPowered);
    paramNBTTagCompound.setBoolean("Loaded", this.isLoaded);
  }
  



  protected void a(NBTTagCompound paramNBTTagCompound)
  {
    this.isSuperPowered = paramNBTTagCompound.getBoolean("Powered");
    setReloadInfo(paramNBTTagCompound.getBoolean("Loaded"), this.isLoading, this.loadTimer);
    setPosition(this.locX, this.locY, this.locZ);
    c(this.yaw, this.pitch);
  }
  



  public boolean b(EntityHuman paramEntityHuman)
  {
    ItemStack localItemStack = paramEntityHuman.U();
    
    if ((localItemStack != null) && (localItemStack.getItem().id == mod_WeaponMod.cannonBall.id) && (!this.isLoaded) && (!this.isLoading) && (paramEntityHuman.inventory.c(Item.SULPHUR.id)))
    {
      if (paramEntityHuman.inventory.c(mod_WeaponMod.cannonBall.id))
      {
        loadCannon();
      }
      else
      {
        a(Item.SULPHUR.id, 1, 0.0F);
      }
      
      return true;
    }
    
    if (this.world.isStatic)
    {
      return true;
    }
    
    if ((this.passenger != null) && ((this.passenger instanceof EntityHuman)) && (this.passenger != paramEntityHuman))
    {
      return true;
    }
    

    paramEntityHuman.mount(this);
    return true;
  }
  




  public void a(EntityWeatherLighting paramEntityWeatherLighting)
  {
    damageEntity(null, 100);
    this.isSuperPowered = true;
  }
}
