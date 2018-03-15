package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ItemFlintAndSteel extends Item
{
  public ItemFlintAndSteel(int i)
  {
    super(i);
    this.maxStackSize = 1;
    setMaxDurability(64);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    int clickedX = i;int clickedY = j;int clickedZ = k;
    
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
      
      BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, org.bukkit.event.block.BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, thePlayer);
      world.getServer().getPluginManager().callEvent(eventIgnite);
      
      if (eventIgnite.isCancelled()) {
        itemstack.damage(1, entityhuman);
        return false;
      }
      
      CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
      

      world.makeSound(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, c.nextFloat() * 0.4F + 0.8F);
      world.setTypeId(i, j, k, Block.FIRE.id);
      

      BlockPlaceEvent placeEvent = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
      
      if ((placeEvent.isCancelled()) || (!placeEvent.canBuild())) {
        placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
        return false;
      }
    }
    

    itemstack.damage(1, entityhuman);
    return true;
  }
}
