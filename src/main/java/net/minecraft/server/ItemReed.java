package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState;

public class ItemReed extends Item
{
  private int id;
  
  public ItemReed(int i, Block block) {
    super(i);
    this.id = block.id;
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    int clickedX = i;int clickedY = j;int clickedZ = k;
    int i1 = world.getTypeId(i, j, k);
    
    if (i1 == Block.SNOW.id) {
      l = 1;
    } else if ((i1 != Block.VINE.id) && (i1 != Block.LONG_GRASS.id) && (i1 != Block.DEAD_BUSH.id)) {
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
    }
    
    if (!entityhuman.d(i, j, k))
      return false;
    if (itemstack.count == 0) {
      return false;
    }
    if (world.mayPlace(this.id, i, j, k, false, l)) {
      Block block = Block.byId[this.id];
      

      CraftBlockState replacedBlockState = CraftBlockState.getBlockState(world, i, j, k);
      









      if (world.setRawTypeId(i, j, k, this.id)) {
        org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, replacedBlockState, clickedX, clickedY, clickedZ);
        
        if ((event.isCancelled()) || (!event.canBuild()))
        {
          world.setTypeIdAndData(i, j, k, replacedBlockState.getTypeId(), replacedBlockState.getRawData());
          
          return true;
        }
        
        world.update(i, j, k, this.id);
        

        if (world.getTypeId(i, j, k) == this.id) {
          Block.byId[this.id].postPlace(world, i, j, k, l);
          Block.byId[this.id].postPlace(world, i, j, k, entityhuman);
        }
        
        world.makeSound(i + 0.5F, j + 0.5F, k + 0.5F, block.stepSound.getName(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
        itemstack.count -= 1;
      }
    }
    
    return true;
  }
}
