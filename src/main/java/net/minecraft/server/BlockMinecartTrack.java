package net.minecraft.server;

import java.util.Random;







public class BlockMinecartTrack
  extends Block
{
  private final boolean a;
  private int renderType = 9;
  
  public void setRenderType(int value) {
    this.renderType = value;
  }
  
  public static final boolean g(World world, int i, int j, int k)
  {
    int l = world.getTypeId(i, j, k);
    
    return Block.byId[l] instanceof BlockMinecartTrack;
  }
  
  public static final boolean d(int i) {
    return Block.byId[i] instanceof BlockMinecartTrack;
  }
  
  protected BlockMinecartTrack(int i, int j, boolean flag) {
    super(i, j, Material.ORIENTABLE);
    this.a = flag;
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
  }
  
  public boolean i() {
    return this.a;
  }
  
  public AxisAlignedBB e(World world, int i, int j, int k) {
    return null;
  }
  
  public boolean a() {
    return false;
  }
  
  public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
    updateShape(world, i, j, k);
    return super.a(world, i, j, k, vec3d, vec3d1);
  }
  
  public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
    int l = iblockaccess.getData(i, j, k);
    
    if ((l >= 2) && (l <= 5)) {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
    } else {
      a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }
  }
  
  public int a(int i, int j) {
    if (this.a) {
      if ((this.id == Block.GOLDEN_RAIL.id) && ((j & 0x8) == 0)) {
        return this.textureId - 16;
      }
    } else if (j >= 6) {
      return this.textureId - 16;
    }
    
    return this.textureId;
  }
  
  public boolean b() {
    return false;
  }
  
  public int c()
  {
    return this.renderType;
  }
  
  public int a(Random random) {
    return 1;
  }
  
  public boolean canPlace(World world, int i, int j, int k)
  {
    return world.isBlockSolidOnSide(i, j - 1, k, 1);
  }
  
  public void onPlace(World world, int i, int j, int k) {
    if (!world.isStatic) {
      a(world, i, j, k, true);
      if (this.id != Block.GOLDEN_RAIL.id) {}
    }
  }
  

  public void doPhysics(World world, int i, int j, int k, int l)
  {
    if (!world.isStatic) {
      int i1 = world.getData(i, j, k);
      int j1 = i1;
      
      if (this.a) {
        j1 = i1 & 0x7;
      }
      
      boolean flag = false;
      
      if (!world.isBlockSolidOnSide(i, j - 1, k, 1)) {
        flag = true;
      }
      

      if ((j1 == 2) && (!world.isBlockSolidOnSide(i + 1, j, k, 1))) {
        flag = true;
      }
      

      if ((j1 == 3) && (!world.isBlockSolidOnSide(i - 1, j, k, 1))) {
        flag = true;
      }
      

      if ((j1 == 4) && (!world.isBlockSolidOnSide(i, j, k - 1, 1))) {
        flag = true;
      }
      

      if ((j1 == 5) && (!world.isBlockSolidOnSide(i, j, k + 1, 1))) {
        flag = true;
      }
      
      if (flag) {
        b(world, i, j, k, world.getData(i, j, k), 0);
        world.setTypeId(i, j, k, 0);
      } else if (this.id == Block.GOLDEN_RAIL.id) {
        boolean flag1 = world.isBlockIndirectlyPowered(i, j, k);
        
        flag1 = (flag1) || (a(world, i, j, k, i1, true, 0)) || (a(world, i, j, k, i1, false, 0));
        boolean flag2 = false;
        
        if ((flag1) && ((i1 & 0x8) == 0)) {
          world.setData(i, j, k, j1 | 0x8);
          flag2 = true;
        } else if ((!flag1) && ((i1 & 0x8) != 0)) {
          world.setData(i, j, k, j1);
          flag2 = true;
        }
        
        if (flag2) {
          world.applyPhysics(i, j - 1, k, this.id);
          if ((j1 == 2) || (j1 == 3) || (j1 == 4) || (j1 == 5)) {
            world.applyPhysics(i, j + 1, k, this.id);
          }
        }
      } else if ((l > 0) && (Block.byId[l].isPowerSource()) && (!this.a) && (MinecartTrackLogic.a(new MinecartTrackLogic(this, world, i, j, k)) == 3)) {
        a(world, i, j, k, false);
      }
    }
  }
  
  private void a(World world, int i, int j, int k, boolean flag) {
    if (!world.isStatic) {
      new MinecartTrackLogic(this, world, i, j, k).a(world.isBlockIndirectlyPowered(i, j, k), flag);
    }
  }
  
  private boolean a(World world, int i, int j, int k, int l, boolean flag, int i1) {
    if (i1 >= 8) {
      return false;
    }
    int j1 = l & 0x7;
    boolean flag1 = true;
    
    switch (j1) {
    case 0: 
      if (flag) {
        k++;
      } else {
        k--;
      }
      break;
    
    case 1: 
      if (flag) {
        i--;
      } else {
        i++;
      }
      break;
    
    case 2: 
      if (flag) {
        i--;
      } else {
        i++;
        j++;
        flag1 = false;
      }
      
      j1 = 1;
      break;
    
    case 3: 
      if (flag) {
        i--;
        j++;
        flag1 = false;
      } else {
        i++;
      }
      
      j1 = 1;
      break;
    
    case 4: 
      if (flag) {
        k++;
      } else {
        k--;
        j++;
        flag1 = false;
      }
      
      j1 = 0;
      break;
    
    case 5: 
      if (flag) {
        k++;
        j++;
        flag1 = false;
      } else {
        k--;
      }
      
      j1 = 0;
    }
    
    return a(world, i, j, k, flag, i1, j1);
  }
  
  private boolean a(World world, int i, int j, int k, boolean flag, int l, int i1)
  {
    int j1 = world.getTypeId(i, j, k);
    
    if (j1 == Block.GOLDEN_RAIL.id) {
      int k1 = world.getData(i, j, k);
      int l1 = k1 & 0x7;
      
      if ((i1 == 1) && ((l1 == 0) || (l1 == 4) || (l1 == 5))) {
        return false;
      }
      
      if ((i1 == 0) && ((l1 == 1) || (l1 == 2) || (l1 == 3))) {
        return false;
      }
      
      if ((k1 & 0x8) != 0) {
        if (world.isBlockIndirectlyPowered(i, j, k)) {
          return true;
        }
        
        return a(world, i, j, k, k1, flag, l + 1);
      }
    }
    
    return false;
  }
  
  public int g() {
    return 0;
  }
  
  @Deprecated
  static boolean a(BlockMinecartTrack blockminecarttrack)
  {
    return blockminecarttrack.a;
  }
  













  public boolean isFlexibleRail(World world, int y, int x, int z)
  {
    return !this.a;
  }
  













  public boolean canMakeSlopes(World world, int x, int y, int z)
  {
    return true;
  }
  






























  public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z)
  {
    int meta = world.getData(x, y, z);
    if (this.a) {
      meta &= 0x7;
    }
    return meta;
  }
  















  public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z)
  {
    return 0.4F;
  }
  
















  public void onMinecartPass(World world, EntityMinecart cart, int y, int x, int z) {}
  















  public boolean hasPowerBit(World world, int x, int y, int z)
  {
    return this.a;
  }
}
