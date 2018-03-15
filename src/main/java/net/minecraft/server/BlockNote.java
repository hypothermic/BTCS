package net.minecraft.server;



public class BlockNote
  extends BlockContainer
{
  public BlockNote(int paramInt)
  {
    super(paramInt, 74, Material.WOOD);
  }
  
  public int a(int paramInt) {
    return this.textureId;
  }
  
  public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramInt4 > 0) {
      boolean bool = paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3);
      TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
      if ((localTileEntityNote != null) && (localTileEntityNote.b != bool)) {
        if (bool) {
          localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
        }
        localTileEntityNote.b = bool;
      }
    }
  }
  
  public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramWorld.isStatic) return true;
    TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityNote != null) {
      localTileEntityNote.c();
      localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
    }
    return true;
  }
  
  public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
    if (paramWorld.isStatic) return;
    TileEntityNote localTileEntityNote = (TileEntityNote)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
    if (localTileEntityNote != null) localTileEntityNote.play(paramWorld, paramInt1, paramInt2, paramInt3);
  }
  
  public TileEntity a_() {
    return new TileEntityNote();
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    float f = (float)Math.pow(2.0D, (paramInt5 - 12) / 12.0D);
    
    String str = "harp";
    if (paramInt4 == 1) str = "bd";
    if (paramInt4 == 2) str = "snare";
    if (paramInt4 == 3) str = "hat";
    if (paramInt4 == 4) { str = "bassattack";
    }
    paramWorld.makeSound(paramInt1 + 0.5D, paramInt2 + 0.5D, paramInt3 + 0.5D, "note." + str, 3.0F, f);
    paramWorld.a("note", paramInt1 + 0.5D, paramInt2 + 1.2D, paramInt3 + 0.5D, paramInt5 / 24.0D, 0.0D, 0.0D);
  }
}
