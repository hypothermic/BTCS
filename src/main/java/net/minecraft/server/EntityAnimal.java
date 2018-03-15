package net.minecraft.server;

import java.util.List;
import java.util.Random;








public abstract class EntityAnimal
  extends EntityAgeable
  implements IAnimal
{
  private int love;
  private int b = 0;
  
  public EntityAnimal(World paramWorld) {
    super(paramWorld);
  }
  
  protected void g() {
    if (getAge() != 0) this.love = 0;
    super.g();
  }
  
  public void e() {
    super.e();
    
    if (getAge() != 0) { this.love = 0;
    }
    if (this.love > 0) {
      this.love -= 1;
      String str = "heart";
      if (this.love % 10 == 0) {
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        double d3 = this.random.nextGaussian() * 0.02D;
        this.world.a(str, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
      }
    } else {
      this.b = 0;
    }
  }
  
  protected void a(Entity paramEntity, float paramFloat) { 
	  
    if ((paramEntity instanceof EntityHuman)) {
      Object localObject; // BTCS: moved Object declaration to within if statement (from ln 49)
      if (paramFloat < 3.0F) {
        double d1 = paramEntity.locX - this.locX;
        double d2 = paramEntity.locZ - this.locZ;
        this.yaw = ((float)(Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F);
        
        this.e = true;
      }
      
      localObject = (EntityHuman)paramEntity;
      if ((((EntityHuman)localObject).U() == null) || (!a(((EntityHuman)localObject).U())))
      {
        this.target = null;
      }
    }
    else if ((paramEntity instanceof EntityAnimal)) {
      EntityAnimal localObject = (EntityAnimal)paramEntity; // BTCS: moved EntityAnimal declaration to within if statement (from ln 49)
      if ((getAge() > 0) && (((EntityAnimal)localObject).getAge() < 0)) {
        if (paramFloat < 2.5D) {
          this.e = true;
        }
      } else if ((this.love > 0) && (((EntityAnimal)localObject).love > 0)) {
        if (((EntityAnimal)localObject).target == null) { ((EntityAnimal)localObject).target = this;
        }
        if ((((EntityAnimal)localObject).target == this) && (paramFloat < 3.5D)) {
          localObject.love += 1;
          this.love += 1;
          this.b += 1;
          if (this.b % 4 == 0) {
            this.world.a("heart", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D);
          }
          
          if (this.b == 60) c((EntityAnimal)paramEntity);
        } else { this.b = 0;
        }
      } else { this.b = 0;
        this.target = null;
      }
    }
  }
  
  private void c(EntityAnimal paramEntityAnimal)
  {
    EntityAnimal localEntityAnimal = createChild(paramEntityAnimal);
    if (localEntityAnimal != null) {
      setAge(6000);
      paramEntityAnimal.setAge(6000);
      this.love = 0;
      this.b = 0;
      this.target = null;
      paramEntityAnimal.target = null;
      paramEntityAnimal.b = 0;
      paramEntityAnimal.love = 0;
      localEntityAnimal.setAge(41536);
      localEntityAnimal.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
      for (int i = 0; i < 7; i++) {
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        double d3 = this.random.nextGaussian() * 0.02D;
        this.world.a("heart", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
      }
      this.world.addEntity(localEntityAnimal);
    }
  }
  

  public abstract EntityAnimal createChild(EntityAnimal paramEntityAnimal);
  
  protected void b(Entity paramEntity, float paramFloat) {}
  
  public boolean damageEntity(DamageSource paramDamageSource, int paramInt)
  {
    this.f = 60;
    this.target = null;
    this.love = 0;
    return super.damageEntity(paramDamageSource, paramInt);
  }
  
  public float a(int paramInt1, int paramInt2, int paramInt3) {
    if (this.world.getTypeId(paramInt1, paramInt2 - 1, paramInt3) == Block.GRASS.id) return 10.0F;
    return this.world.p(paramInt1, paramInt2, paramInt3) - 0.5F;
  }
  
  public void b(NBTTagCompound paramNBTTagCompound) {
    super.b(paramNBTTagCompound);
    paramNBTTagCompound.setInt("InLove", this.love);
  }
  
  public void a(NBTTagCompound paramNBTTagCompound) {
    super.a(paramNBTTagCompound);
    this.love = paramNBTTagCompound.getInt("InLove");
  }
  
  protected Entity findTarget() {
    if (this.f > 0) { return null;
    }
    float f = 8.0F;
    List localList; int i; Object localObject; if (this.love > 0) {
      localList = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityAnimal)localList.get(i);
        if ((localObject != this) && (((EntityAnimal)localObject).love > 0)) {
          return (Entity)localObject;
        }
      }
    }
    else if (getAge() == 0) {
      localList = this.world.a(EntityHuman.class, this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityHuman)localList.get(i);
        if ((((EntityHuman)localObject).U() != null) && (a(((EntityHuman)localObject).U()))) {
          return (Entity)localObject;
        }
      }
    } else if (getAge() > 0) {
      localList = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
      for (i = 0; i < localList.size(); i++) {
        localObject = (EntityAnimal)localList.get(i);
        if ((localObject != this) && (((EntityAnimal)localObject).getAge() < 0)) {
          return (Entity)localObject;
        }
      }
    }
    
    return null;
  }
  
  public boolean canSpawn() {
    int i = MathHelper.floor(this.locX);
    int j = MathHelper.floor(this.boundingBox.b);
    int k = MathHelper.floor(this.locZ);
    return (this.world.getTypeId(i, j - 1, k) == Block.GRASS.id) && (this.world.m(i, j, k) > 8) && (super.canSpawn());
  }
  
  public int m() {
    return 120;
  }
  
  protected boolean n()
  {
    return false;
  }
  
  protected int getExpValue(EntityHuman paramEntityHuman)
  {
    return 1 + this.world.random.nextInt(3);
  }
  
  public boolean a(ItemStack paramItemStack) {
    return paramItemStack.id == Item.WHEAT.id;
  }
  
  public boolean b(EntityHuman paramEntityHuman)
  {
    ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
    if ((localItemStack != null) && (a(localItemStack)) && (getAge() == 0)) {
      if (!paramEntityHuman.abilities.canInstantlyBuild) {
        localItemStack.count -= 1;
        if (localItemStack.count <= 0) {
          paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
        }
      }
      this.love = 600;
      
      this.target = null;
      for (int i = 0; i < 7; i++) {
        double d1 = this.random.nextGaussian() * 0.02D;
        double d2 = this.random.nextGaussian() * 0.02D;
        double d3 = this.random.nextGaussian() * 0.02D;
        this.world.a("heart", this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
      }
      
      return true;
    }
    return super.b(paramEntityHuman);
  }
  
  public boolean r_() {
    return this.love > 0;
  }
  
  public void s_() {
    this.love = 0;
  }
  
  public boolean mate(EntityAnimal paramEntityAnimal) {
    if (paramEntityAnimal == this) return false;
    if (paramEntityAnimal.getClass() != getClass()) return false;
    return (r_()) && (paramEntityAnimal.r_());
  }
}
