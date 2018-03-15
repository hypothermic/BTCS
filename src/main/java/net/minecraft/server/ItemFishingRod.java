package net.minecraft.server;

import org.bukkit.event.player.PlayerFishEvent;

public class ItemFishingRod extends Item
{
  public ItemFishingRod(int i) {
    super(i);
    setMaxDurability(64);
    e(1);
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
    if (entityhuman.hookedFish != null) {
      int i = entityhuman.hookedFish.k();
      
      itemstack.damage(i, entityhuman);
      entityhuman.C_();
    }
    else {
      PlayerFishEvent playerFishEvent = new PlayerFishEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), null, org.bukkit.event.player.PlayerFishEvent.State.FISHING);
      world.getServer().getPluginManager().callEvent(playerFishEvent);
      
      if (playerFishEvent.isCancelled()) {
        return itemstack;
      }
      

      world.makeSound(entityhuman, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
      if (!world.isStatic) {
        world.addEntity(new EntityFishingHook(world, entityhuman));
      }
      
      entityhuman.C_();
    }
    
    return itemstack;
  }
}
