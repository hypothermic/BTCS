package net.minecraft.server;

import org.bukkit.craftbukkit.block.CraftBlockState;

public class ItemSign extends Item
{
  public ItemSign(int i) {
    super(i);
    this.maxStackSize = 1;
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    if (l == 0)
      return false;
    if (!world.getMaterial(i, j, k).isBuildable()) {
      return false;
    }
    int clickedX = i;int clickedY = j;int clickedZ = k;
    
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
    
    if (!entityhuman.d(i, j, k))
      return false;
    if (!Block.SIGN_POST.canPlace(world, i, j, k)) {
      return false;
    }
    CraftBlockState blockState = CraftBlockState.getBlockState(world, i, j, k);
    
    if (l == 1) {
      int i1 = MathHelper.floor((entityhuman.yaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
      
      world.setTypeIdAndData(i, j, k, Block.SIGN_POST.id, i1);
    } else {
      world.setTypeIdAndData(i, j, k, Block.WALL_SIGN.id, l);
    }
    

    org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blockState, clickedX, clickedY, clickedZ);
    
    if ((event.isCancelled()) || (!event.canBuild())) {
      event.getBlockPlaced().setTypeIdAndData(blockState.getTypeId(), blockState.getRawData(), false);
      return false;
    }
    

    itemstack.count -= 1;
    TileEntitySign tileentitysign = (TileEntitySign)world.getTileEntity(i, j, k);
    
    if (tileentitysign != null) {
      entityhuman.a(tileentitysign);
    }
    
    return true;
  }
}
