package net.minecraft.server;

import java.util.Random;







public abstract class EntityTameableAnimal
  extends EntityAnimal
{
  protected PathfinderGoalSit a = new PathfinderGoalSit(this);
  
  public EntityTameableAnimal(World paramWorld) {
    super(paramWorld);
  }
  
  protected void b() {
    super.b();
    this.datawatcher.a(16, Byte.valueOf((byte)0));
    this.datawatcher.a(17, "");
  }
  
  public void b(NBTTagCompound paramNBTTagCompound) {
    super.b(paramNBTTagCompound);
    if (getOwnerName() == null) {
      paramNBTTagCompound.setString("Owner", "");
    } else {
      paramNBTTagCompound.setString("Owner", getOwnerName());
    }
    paramNBTTagCompound.setBoolean("Sitting", isSitting());
  }
  
  public void a(NBTTagCompound paramNBTTagCompound) {
    super.a(paramNBTTagCompound);
    String str = paramNBTTagCompound.getString("Owner");
    if (str.length() > 0) {
      setOwnerName(str);
      setTamed(true);
    }
    this.a.a(paramNBTTagCompound.getBoolean("Sitting"));
  }
  
  protected void a(boolean paramBoolean) {
    String str = "heart";
    if (!paramBoolean) {
      str = "smoke";
    }
    for (int i = 0; i < 7; i++) {
      double d1 = this.random.nextGaussian() * 0.02D;
      double d2 = this.random.nextGaussian() * 0.02D;
      double d3 = this.random.nextGaussian() * 0.02D;
      this.world.a(str, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3);
    }
  }
  









  public boolean isTamed()
  {
    return (this.datawatcher.getByte(16) & 0x4) != 0;
  }
  
  public void setTamed(boolean paramBoolean) {
    int i = this.datawatcher.getByte(16);
    if (paramBoolean) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x4)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFB)));
    }
  }
  
  public boolean isSitting() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setSitting(boolean paramBoolean) {
    int i = this.datawatcher.getByte(16);
    if (paramBoolean) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
    }
  }
  
  public String getOwnerName() {
    return this.datawatcher.getString(17);
  }
  
  public void setOwnerName(String paramString) {
    this.datawatcher.watch(17, paramString);
  }
  
  public EntityLiving getOwner() {
    return this.world.a(getOwnerName());
  }
  
  public PathfinderGoalSit C() {
    return this.a;
  }
}
