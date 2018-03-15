package net.minecraft.server;

import java.util.ArrayList;
import java.util.Random;







public class BlockBrewingStand
  extends BlockContainer
{
  private Random a = new Random();
  
  public BlockBrewingStand(int paramInt) {
    super(paramInt, Material.ORE);
    this.textureId = 157;
  }
  
  public boolean a()
  {
    return false;
  }
  
  public int c()
  {
    return 25;
  }
  
  public TileEntity a_()
  {
    return new TileEntityBrewingStand();
  }
  
  public boolean b()
  {
    return false;
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, ArrayList paramArrayList)
  {
    a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
    f();
    super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramArrayList);
  }
  
  public void f()
  {
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
  }
  

  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    if (paramWorld.isStatic) {
      return true;
    }
    TileEntityBrewingStand localTileEntityBrewingStand = (TileEntityBrewingStand)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityBrewingStand != null) { paramEntityHuman.openBrewingStand(localTileEntityBrewingStand);
    }
    
























































    return true;
  }
  









  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntity != null) && ((localTileEntity instanceof TileEntityBrewingStand))) {
      TileEntityBrewingStand localTileEntityBrewingStand = (TileEntityBrewingStand)localTileEntity;
      for (int i = 0; i < localTileEntityBrewingStand.getSize(); i++) {
        ItemStack localItemStack = localTileEntityBrewingStand.getItem(i);
        if (localItemStack != null) {
          float f1 = this.a.nextFloat() * 0.8F + 0.1F;
          float f2 = this.a.nextFloat() * 0.8F + 0.1F;
          float f3 = this.a.nextFloat() * 0.8F + 0.1F;
          
          while (localItemStack.count > 0) {
            int j = this.a.nextInt(21) + 10;
            if (j > localItemStack.count) j = localItemStack.count;
            localItemStack.count -= j;
            
            EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + f1, paramInt2 + f2, paramInt3 + f3, new ItemStack(localItemStack.id, j, localItemStack.getData()));
            float f4 = 0.05F;
            localEntityItem.motX = ((float)this.a.nextGaussian() * f4);
            localEntityItem.motY = ((float)this.a.nextGaussian() * f4 + 0.2F);
            localEntityItem.motZ = ((float)this.a.nextGaussian() * f4);
            paramWorld.addEntity(localEntityItem);
          }
        }
      }
    }
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.BREWING_STAND.id;
  }
}
