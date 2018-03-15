package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState;

public class ItemRedstone extends Item
{
  public ItemRedstone(int i) {
    super(i);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    int clickedX = i;int clickedY = j;int clickedZ = k;
    
    if (world.getTypeId(i, j, k) != Block.SNOW.id) {
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
      
      if (!world.isEmpty(i, j, k)) {
        return false;
      }
    }
    
    if (!entityhuman.d(i, j, k)) {
      return false;
    }
    if (Block.REDSTONE_WIRE.canPlace(world, i, j, k))
    {
      CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
      
      world.suppressPhysics = true;
      world.setRawTypeId(i, j, k, Block.REDSTONE_WIRE.id);
      
      org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
      blockState.update(true);
      
      world.suppressPhysics = false;
      if ((event.isCancelled()) || (!event.canBuild())) {
        return false;
      }
      

      itemstack.count -= 1;
      world.setTypeId(i, j, k, Block.REDSTONE_WIRE.id);
    }
    
    return true;
  }
}
