package net.minecraft.server;

import java.util.Random;

// BTCS: aka BlockRedstoneRepeater in forge patches. Imported in v1.07

public class BlockDiode extends BlockDirectional {
	
  public static final double[] a = { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
  private static final int[] b = { 1, 2, 3, 4 };
  private final boolean c;
  
  protected BlockDiode(int i, boolean flag) {
    super(i, 6, Material.ORIENTABLE);
    this.c = flag;
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
  }
  
  public boolean b() {
    return false;
  }
  
  public boolean canPlace(World world, int i, int j, int k) {
    return !world.isBlockSolidOnSide(i, j - 1, k, 1) ? false : super.canPlace(world, i, j, k);
  }
  
  public boolean f(World world, int i, int j, int k) {
    return !world.isBlockSolidOnSide(i, j - 1, k, 1) ? false : super.f(world, i, j, k);
  }
  
  public void a(World world, int i, int j, int k, Random random) {
    int l = world.getData(i, j, k);
    boolean flag = f(world, i, j, k, l);
    
    if ((this.c) && (!flag)) {
      world.setTypeIdAndData(i, j, k, Block.DIODE_OFF.id, l);
    } else if (!this.c) {
      world.setTypeIdAndData(i, j, k, Block.DIODE_ON.id, l);
      if (!flag) {
        int i1 = (l & 0xC) >> 2;
        
        world.c(i, j, k, Block.DIODE_ON.id, b[i1] * 2);
      }
    }
  }
  
  public int a(int i, int j) {
    return i == 1 ? 131 : this.c ? 147 : i == 0 ? 115 : this.c ? 99 : 5;
  }
  
  public int c() {
    return 15;
  }
  
  public int a(int i) {
    return a(i, 0);
  }
  
  public boolean d(World world, int i, int j, int k, int l) {
    return a(world, i, j, k, l);
  }
  
  public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
    if (!this.c) {
      return false;
    }
    int i1 = b(iblockaccess.getData(i, j, k));
    
    return (i1 == 0) && (l == 3);
  }
  
  public void doPhysics(World world, int i, int j, int k, int l) {
    if (!f(world, i, j, k)) {
      b(world, i, j, k, world.getData(i, j, k), 0);
      world.setTypeId(i, j, k, 0);
    } else {
      int i1 = world.getData(i, j, k);
      boolean flag = f(world, i, j, k, i1);
      int j1 = (i1 & 0xC) >> 2;
      
      if ((this.c) && (!flag)) {
        world.c(i, j, k, this.id, b[j1] * 2);
      } else if ((!this.c) && (flag)) {
        world.c(i, j, k, this.id, b[j1] * 2);
      }
    }
  }
  
  private boolean f(World world, int i, int j, int k, int l) {
    int i1 = b(l);
    
    switch (i1) {
    case 0: 
      return (world.isBlockFaceIndirectlyPowered(i, j, k + 1, 3)) || ((world.getTypeId(i, j, k + 1) == Block.REDSTONE_WIRE.id) && (world.getData(i, j, k + 1) > 0));
    
    case 1: 
      return (world.isBlockFaceIndirectlyPowered(i - 1, j, k, 4)) || ((world.getTypeId(i - 1, j, k) == Block.REDSTONE_WIRE.id) && (world.getData(i - 1, j, k) > 0));
    
    case 2: 
      return (world.isBlockFaceIndirectlyPowered(i, j, k - 1, 2)) || ((world.getTypeId(i, j, k - 1) == Block.REDSTONE_WIRE.id) && (world.getData(i, j, k - 1) > 0));
    
    case 3: 
      return (world.isBlockFaceIndirectlyPowered(i + 1, j, k, 5)) || ((world.getTypeId(i + 1, j, k) == Block.REDSTONE_WIRE.id) && (world.getData(i + 1, j, k) > 0));
    }
    
    return false;
  }
  
  public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
    int l = world.getData(i, j, k);
    int i1 = (l & 0xC) >> 2;
    
    i1 = i1 + 1 << 2 & 0xC;
    world.setData(i, j, k, i1 | l & 0x3);
    return true;
  }
  
  public boolean isPowerSource() {
    return true;
  }
  
  public void postPlace(World world, int i, int j, int k, EntityLiving entityliving) {
    int l = ((MathHelper.floor(entityliving.yaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
    
    world.setData(i, j, k, l);
    boolean flag = f(world, i, j, k, l);
    
    if (flag) {
      world.c(i, j, k, this.id, 1);
    }
  }
  
  public void onPlace(World world, int i, int j, int k) {
    world.applyPhysics(i + 1, j, k, this.id);
    world.applyPhysics(i - 1, j, k, this.id);
    world.applyPhysics(i, j, k + 1, this.id);
    world.applyPhysics(i, j, k - 1, this.id);
    world.applyPhysics(i, j - 1, k, this.id);
    world.applyPhysics(i, j + 1, k, this.id);
  }
  
  public void postBreak(World world, int i, int j, int k, int l) {
    if (this.c) {
      world.applyPhysics(i, j + 1, k, this.id);
    }
    
    super.postBreak(world, i, j, k, l);
  }
  
  public boolean a() {
    return false;
  }
  
  public int getDropType(int i, Random random, int j) {
    return Item.DIODE.id;
  }
}
