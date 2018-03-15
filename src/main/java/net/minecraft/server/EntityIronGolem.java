package net.minecraft.server;

import java.util.Random;

public class EntityIronGolem extends EntityGolem
{
  private int b = 0;
  Village a = null;
  private int c;
  private int g;
  
  public EntityIronGolem(World world) {
    super(world);
    this.texture = "/mob/villager_golem.png";
    b(1.4F, 2.9F);
    al().a(true);
    this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 0.25F, true));
    this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.22F, 32.0F));
    this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.16F, true));
    this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.16F));
    this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
    this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.16F));
    this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
    this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
    this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityMonster.class, 16.0F, 0, false, true));
  }
  
  protected void b() {
    super.b();
    this.datawatcher.a(16, Byte.valueOf((byte)0));
  }
  
  public boolean c_() {
    return true;
  }
  
  protected void g() {
    if (--this.b <= 0) {
      this.b = (70 + this.random.nextInt(50));
      this.a = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
      if (this.a == null) {
        ax();
      } else {
        ChunkCoordinates chunkcoordinates = this.a.getCenter();
        
        b(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, this.a.getSize());
      }
    }
    
    super.g();
  }
  
  public int getMaxHealth() {
    return 100;
  }
  
  protected int b_(int i) {
    return i;
  }
  
  public void e() {
    super.e();
    if (this.c > 0) {
      this.c -= 1;
    }
    
    if (this.g > 0) {
      this.g -= 1;
    }
    
    if ((this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D) && (this.random.nextInt(5) == 0)) {
      int i = MathHelper.floor(this.locX);
      int j = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
      int k = MathHelper.floor(this.locZ);
      int l = this.world.getTypeId(i, j, k);
      
      if (l > 0) {
        this.world.a("tilecrack_" + l, this.locX + (this.random.nextFloat() - 0.5D) * this.width, this.boundingBox.b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, 4.0D * (this.random.nextFloat() - 0.5D), 0.5D, (this.random.nextFloat() - 0.5D) * 4.0D);
      }
    }
  }
  
  public boolean a(Class oclass) {
    return (n_()) && (EntityHuman.class.isAssignableFrom(oclass)) ? false : super.a(oclass);
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setBoolean("PlayerCreated", n_());
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    b(nbttagcompound.getBoolean("PlayerCreated"));
  }
  
  public boolean a(Entity entity) {
    this.c = 10;
    this.world.broadcastEntityEffect(this, (byte)4);
    boolean flag = entity.damageEntity(DamageSource.mobAttack(this), 7 + this.random.nextInt(15));
    
    if (flag) {
      entity.motY += 0.4000000059604645D;
    }
    
    this.world.makeSound(this, "mob.irongolem.throw", 1.0F, 1.0F);
    return flag;
  }
  
  public Village l_() {
    return this.a;
  }
  
  public void a(boolean flag) {
    this.g = (flag ? 400 : 0);
    this.world.broadcastEntityEffect(this, (byte)11);
  }
  
  protected String i() {
    return "none";
  }
  
  protected String j() {
    return "mob.irongolem.hit";
  }
  
  protected String k() {
    return "mob.irongolem.death";
  }
  
  protected void a(int i, int j, int k, int l) {
    this.world.makeSound(this, "mob.irongolem.walk", 1.0F, 1.0F);
  }
  
  protected void dropDeathLoot(boolean flag, int i)
  {
    java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList();
    int j = this.random.nextInt(3);
    


    for (int k = 0; k < j; k++) {
      loot.add(new org.bukkit.craftbukkit.inventory.CraftItemStack(Block.RED_ROSE.id, 1));
    }
    
    int k = 3 + this.random.nextInt(3); // BTCS: added 'int ' decl
    
    for (int l = 0; l < k; l++) {
      loot.add(new org.bukkit.craftbukkit.inventory.CraftItemStack(Item.IRON_INGOT.id, 1));
    }
    
    org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
  }
  
  public int m_()
  {
    return this.g;
  }
  
  public boolean n_() {
    return (this.datawatcher.getByte(16) & 0x1) != 0;
  }
  
  public void b(boolean flag) {
    byte b0 = this.datawatcher.getByte(16);
    
    if (flag) {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
    } else {
      this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
    }
  }
}
