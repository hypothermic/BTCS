package net.minecraft.server;

import org.bukkit.event.player.PlayerInteractEvent;

public class ItemMinecart extends Item {
  public int a;
  
  public ItemMinecart(int i, int j) { super(i);
    this.maxStackSize = 1;
    this.a = j;
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    int i1 = world.getTypeId(i, j, k);
    
    if (BlockMinecartTrack.d(i1)) {
      if (!world.isStatic)
      {
        PlayerInteractEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerInteractEvent(entityhuman, org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
        
        if (event.isCancelled()) {
          return false;
        }
        

        world.addEntity(new EntityMinecart(world, i + 0.5F, j + 0.5F, k + 0.5F, this.a));
      }
      
      itemstack.count -= 1;
      return true;
    }
    return false;
  }
}
