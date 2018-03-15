package net.minecraft.server;

import java.util.Random;







public class BlockPistonMoving
  extends BlockContainer
{
  public BlockPistonMoving(int paramInt)
  {
    super(paramInt, Material.PISTON);
    c(-1.0F);
  }
  
  public TileEntity a_()
  {
    return null;
  }
  

  public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
  

  public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntity localTileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntity != null) && ((localTileEntity instanceof TileEntityPiston))) {
      ((TileEntityPiston)localTileEntity).g();
    } else {
      super.remove(paramWorld, paramInt1, paramInt2, paramInt3);
    }
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return false;
  }
  
  public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return false;
  }
  
  public int c()
  {
    return -1;
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean b()
  {
    return false;
  }
  


  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman)
  {
    if ((!paramWorld.isStatic) && (paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null))
    {
      paramWorld.setTypeId(paramInt1, paramInt2, paramInt3, 0);
      return true;
    }
    return false;
  }
  
  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return 0;
  }
  
  public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    if (paramWorld.isStatic) { return;
    }
    TileEntityPiston localTileEntityPiston = c(paramWorld, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston == null) {
      return;
    }
    
    Block.byId[localTileEntityPiston.c()].b(paramWorld, paramInt1, paramInt2, paramInt3, localTileEntityPiston.k(), 0);
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!paramWorld.isStatic) && (paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null)) {}
  }
  


  public static TileEntity a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    return new TileEntityPiston(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2);
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntityPiston localTileEntityPiston = c(paramWorld, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston == null) {
      return null;
    }
    

    float f = localTileEntityPiston.a(0.0F);
    if (localTileEntityPiston.e()) {
      f = 1.0F - f;
    }
    return b(paramWorld, paramInt1, paramInt2, paramInt3, localTileEntityPiston.c(), f, localTileEntityPiston.f());
  }
  
  public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3)
  {
    TileEntityPiston localTileEntityPiston = c(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
    if (localTileEntityPiston != null) {
      Block localBlock = Block.byId[localTileEntityPiston.c()];
      if ((localBlock == null) || (localBlock == this)) {
        return;
      }
      localBlock.updateShape(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
      
      float f = localTileEntityPiston.a(0.0F);
      if (localTileEntityPiston.e()) {
        f = 1.0F - f;
      }
      int i = localTileEntityPiston.f();
      this.minX = (localBlock.minX - Facing.b[i] * f);
      this.minY = (localBlock.minY - Facing.c[i] * f);
      this.minZ = (localBlock.minZ - Facing.d[i] * f);
      this.maxX = (localBlock.maxX - Facing.b[i] * f);
      this.maxY = (localBlock.maxY - Facing.c[i] * f);
      this.maxZ = (localBlock.maxZ - Facing.d[i] * f);
    }
  }
  
  public AxisAlignedBB b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    if ((paramInt4 == 0) || (paramInt4 == this.id)) {
      return null;
    }
    AxisAlignedBB localAxisAlignedBB = Block.byId[paramInt4].e(paramWorld, paramInt1, paramInt2, paramInt3);
    
    if (localAxisAlignedBB == null) {
      return null;
    }
    

    if (Facing.b[paramInt5] < 0) {
      localAxisAlignedBB.a -= Facing.b[paramInt5] * paramFloat;
    } else {
      localAxisAlignedBB.d -= Facing.b[paramInt5] * paramFloat;
    }
    if (Facing.c[paramInt5] < 0) {
      localAxisAlignedBB.b -= Facing.c[paramInt5] * paramFloat;
    } else {
      localAxisAlignedBB.e -= Facing.c[paramInt5] * paramFloat;
    }
    if (Facing.d[paramInt5] < 0) {
      localAxisAlignedBB.c -= Facing.d[paramInt5] * paramFloat;
    } else {
      localAxisAlignedBB.f -= Facing.d[paramInt5] * paramFloat;
    }
    return localAxisAlignedBB;
  }
  
  private TileEntityPiston c(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
    TileEntity localTileEntity = paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
    if ((localTileEntity != null) && ((localTileEntity instanceof TileEntityPiston))) {
      return (TileEntityPiston)localTileEntity;
    }
    return null;
  }
}
