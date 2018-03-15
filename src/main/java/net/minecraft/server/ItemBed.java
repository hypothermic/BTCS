package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState;

public class ItemBed extends Item
{
  public ItemBed(int i) {
    super(i);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    if (l != 1) {
      return false;
    }
    int clickedX = i;int clickedY = j;int clickedZ = k;
    
    j++;
    BlockBed blockbed = (BlockBed)Block.BED;
    int i1 = MathHelper.floor(entityhuman.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    byte b0 = 0;
    byte b1 = 0;
    
    if (i1 == 0) {
      b1 = 1;
    }
    
    if (i1 == 1) {
      b0 = -1;
    }
    
    if (i1 == 2) {
      b1 = -1;
    }
    
    if (i1 == 3) {
      b0 = 1;
    }
    
    if ((entityhuman.d(i, j, k)) && (entityhuman.d(i + b0, j, k + b1))) {
      if ((world.isEmpty(i, j, k)) && (world.isEmpty(i + b0, j, k + b1)) && (world.e(i, j - 1, k)) && (world.e(i + b0, j - 1, k + b1))) {
        CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
        
        world.setTypeIdAndData(i, j, k, blockbed.id, i1);
        

        org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
        
        if ((event.isCancelled()) || (!event.canBuild())) {
          event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
          return false;
        }
        

        if (world.getTypeId(i, j, k) == blockbed.id) {
          world.setTypeIdAndData(i + b0, j, k + b1, blockbed.id, i1 + 8);
        }
        
        itemstack.count -= 1;
        return true;
      }
      return false;
    }
    
    return false;
  }
}
