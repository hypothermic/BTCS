package net.minecraft.server;

import java.util.Random;

public class EntityCow extends EntityAnimal
{
  public EntityCow(World world) {
    super(world);
    this.texture = "/mob/cow.png";
    b(0.9F, 1.3F);
    al().a(true);
    this.goalSelector.a(0, new PathfinderGoalFloat(this));
    this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
    this.goalSelector.a(2, new PathfinderGoalBreed(this, 0.2F));
    this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.WHEAT.id, false));
    this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25F));
    this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2F));
    this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
    this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
  }
  
  public boolean c_() {
    return true;
  }
  
  public int getMaxHealth() {
    return 10;
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
  }
  
  protected String i() {
    return "mob.cow";
  }
  
  protected String j() {
    return "mob.cowhurt";
  }
  
  protected String k() {
    return "mob.cowhurt";
  }
  
  protected float p() {
    return 0.4F;
  }
  
  protected int getLootId() {
    return Item.LEATHER.id;
  }
  
  protected void dropDeathLoot(boolean flag, int i)
  {
    java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList();
    int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
    
    if (j > 0) {
      loot.add(new org.bukkit.inventory.ItemStack(Item.LEATHER.id, j));
    }
    
    j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
    
    if (j > 0) {
      loot.add(new org.bukkit.inventory.ItemStack(isBurning() ? Item.COOKED_BEEF.id : Item.RAW_BEEF.id, j));
    }
    
    org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
  }
  
  public boolean b(EntityHuman entityhuman)
  {
    ItemStack itemstack = entityhuman.inventory.getItemInHand();
    
    if ((itemstack != null) && (itemstack.id == Item.BUCKET.id))
    {
      org.bukkit.Location loc = getBukkitEntity().getLocation();
      org.bukkit.event.player.PlayerBucketFillEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.MILK_BUCKET);
      
      if (event.isCancelled()) {
        return false;
      }
      
      entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, org.bukkit.craftbukkit.inventory.CraftItemStack.createNMSItemStack(event.getItemStack()));
      

      return true;
    }
    return super.b(entityhuman);
  }
  
  public EntityAnimal createChild(EntityAnimal entityanimal)
  {
    return new EntityCow(this.world);
  }
}
