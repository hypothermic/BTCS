package net.minecraft.server;

import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;

public class ItemFireball extends Item
{
  public ItemFireball(int i)
  {
    super(i);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    if (world.isStatic) {
      return true;
    }
    if (l == 0) {
      j--;
    }
    
    if (l == 1) {
      j++;
    }
    
    if (l == 2) {
      k--;
    }
    
    if (l == 3) {
      k++;
    }
    
    if (l == 4) {
      i--;
    }
    
    if (l == 5) {
      i++;
    }
    
    if (!entityhuman.d(i, j, k)) {
      return false;
    }
    int i1 = world.getTypeId(i, j, k);
    
    if (i1 == 0)
    {
      org.bukkit.block.Block blockClicked = world.getWorld().getBlockAt(i, j, k);
      Player thePlayer = (Player)entityhuman.getBukkitEntity();
      
      BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FIREBALL, thePlayer);
      world.getServer().getPluginManager().callEvent(eventIgnite);
      
      if (eventIgnite.isCancelled()) {
        if (!entityhuman.abilities.canInstantlyBuild) {
          itemstack.count -= 1;
        }
        return false;
      }
      

      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, c.nextFloat() * 0.4F + 0.8F);
      world.setTypeId(i, j, k, Block.FIRE.id);
    }
    
    if (!entityhuman.abilities.canInstantlyBuild) {
      itemstack.count -= 1;
    }
    
    return true;
  }
}
