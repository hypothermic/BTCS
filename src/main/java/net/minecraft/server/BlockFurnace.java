package net.minecraft.server;

import java.util.Random;











public class BlockFurnace
  extends BlockContainer
{
  private Random a = new Random();
  private final boolean b;
  private static boolean c = false;
  
  protected BlockFurnace(int paramInt, boolean paramBoolean) {
    super(paramInt, Material.STONE);
    this.b = paramBoolean;
    this.textureId = 45;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2) {
    return Block.FURNACE.id;
  }
  
  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    super.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
    g(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  private void g(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramWorld.isStatic) {
      return;
    }
    
    int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 - 1);
    int j = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3 + 1);
    int k = paramWorld.getTypeId(paramInt1 - 1, paramInt2, paramInt3);
    int m = paramWorld.getTypeId(paramInt1 + 1, paramInt2, paramInt3);
    
    int n = 3;
    // BTCS start
    /*if ((Block.n[i] != 0) && (Block.n[j] == 0)) n = 3;
    if ((Block.n[j] != 0) && (Block.n[i] == 0)) n = 2;
    if ((Block.n[k] != 0) && (Block.n[m] == 0)) n = 5;
    if ((Block.n[m] != 0) && (Block.n[k] == 0)) n = 4;*/
    if ((Block.n[i]) && (Block.n[j])) n = 3;
    if ((Block.n[j]) && (Block.n[i])) n = 2;
    if ((Block.n[k]) && (Block.n[m])) n = 5;
    if ((Block.n[m]) && (Block.n[k])) n = 4;
    // BTCS end
    paramWorld.setData(paramInt1, paramInt2, paramInt3, n);
  }
  




































  public int a(int paramInt)
  {
    if (paramInt == 1) return this.textureId + 17;
    if (paramInt == 0) return this.textureId + 17;
    if (paramInt == 3) return this.textureId - 1;
    return this.textureId;
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramWorld.isStatic) {
      return true;
    }
    TileEntityFurnace localTileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityFurnace != null) paramEntityHuman.openFurnace(localTileEntityFurnace);
    return true;
  }
  
  public static void a(boolean paramBoolean, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    
    c = true;
    if (paramBoolean) paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.BURNING_FURNACE.id); else
      paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, Block.FURNACE.id);
    c = false;
    
    paramWorld.setData(paramInt1, paramInt2, paramInt3, i);
    
    if (localTileEntity != null) {
      localTileEntity.m();
      paramWorld.setTileEntity(paramInt1, paramInt2, paramInt3, localTileEntity);
    }
  }
  
  public TileEntity a_() {
    return new TileEntityFurnace();
  }
  
  public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving) {
    int i = MathHelper.floor(paramEntityLiving.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 2);
    if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 5);
    if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 3);
    if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 4);
  }
  
  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
    if (!c) {
      TileEntityFurnace localTileEntityFurnace = (TileEntityFurnace)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if (localTileEntityFurnace != null) {
        for (int i = 0; i < localTileEntityFurnace.getSize(); i++) {
          ItemStack localItemStack = localTileEntityFurnace.getItem(i);
          if (localItemStack != null) {
            float f1 = this.a.nextFloat() * 0.8F + 0.1F;
            float f2 = this.a.nextFloat() * 0.8F + 0.1F;
            float f3 = this.a.nextFloat() * 0.8F + 0.1F;
            
            while (localItemStack.count > 0) {
              int j = this.a.nextInt(21) + 10;
              if (j > localItemStack.count) j = localItemStack.count;
              localItemStack.count -= j;
              
              EntityItem localEntityItem = new EntityItem(paramWorld, paramInt1 + f1, paramInt2 + f2, paramInt3 + f3, new ItemStack(localItemStack.id, j, localItemStack.getData()));
              
              if (localItemStack.hasTag()) {
                localEntityItem.itemStack.setTag((NBTTagCompound)localItemStack.getTag().clone());
              }
              
              float f4 = 0.05F;
              localEntityItem.motX = ((float)this.a.nextGaussian() * f4);
              localEntityItem.motY = ((float)this.a.nextGaussian() * f4 + 0.2F);
              localEntityItem.motZ = ((float)this.a.nextGaussian() * f4);
              paramWorld.addEntity(localEntityItem);
            }
          }
        }
      }
    }
    super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
  }
}
