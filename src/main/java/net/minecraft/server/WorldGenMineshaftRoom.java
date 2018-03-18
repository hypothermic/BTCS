package net.minecraft.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldGenMineshaftRoom extends StructurePiece
{
  private LinkedList<StructureBoundingBox> a = new LinkedList(); // BTCS: added decl <StructureBoundingBox>
  
  public WorldGenMineshaftRoom(int paramInt1, Random paramRandom, int paramInt2, int paramInt3) {
    super(paramInt1);
    
    this.g = new StructureBoundingBox(paramInt2, 50, paramInt3, paramInt2 + 7 + paramRandom.nextInt(6), 54 + paramRandom.nextInt(6), paramInt3 + 7 + paramRandom.nextInt(6));
  }
  

  public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom)
  {
    int i = c();
    


    int j = this.g.c() - 3 - 1;
    if (j <= 0) {
      j = 1;
    }
    

    int k = 0;
    StructurePiece localStructurePiece; StructureBoundingBox localStructureBoundingBox; while (k < this.g.b()) {
      k += paramRandom.nextInt(this.g.b());
      if (k + 3 > this.g.b()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + k, this.g.b + paramRandom.nextInt(j) + 1, this.g.c - 1, 2, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.b();
        this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.g.c, localStructureBoundingBox.d, localStructureBoundingBox.e, this.g.c + 1));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.g.b()) {
      k += paramRandom.nextInt(this.g.b());
      if (k + 3 > this.g.b()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a + k, this.g.b + paramRandom.nextInt(j) + 1, this.g.f + 1, 0, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.b();
        this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.g.f - 1, localStructureBoundingBox.d, localStructureBoundingBox.e, this.g.f));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.g.d()) {
      k += paramRandom.nextInt(this.g.d());
      if (k + 3 > this.g.d()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.a - 1, this.g.b + paramRandom.nextInt(j) + 1, this.g.c + k, 1, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.b();
        this.a.add(new StructureBoundingBox(this.g.a, localStructureBoundingBox.b, localStructureBoundingBox.c, this.g.a + 1, localStructureBoundingBox.e, localStructureBoundingBox.f));
      }
      k += 4;
    }
    
    k = 0;
    while (k < this.g.d()) {
      k += paramRandom.nextInt(this.g.d());
      if (k + 3 > this.g.d()) {
        break;
      }
      localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.g.d + 1, this.g.b + paramRandom.nextInt(j) + 1, this.g.c + k, 3, i);
      
      if (localStructurePiece != null) {
        localStructureBoundingBox = localStructurePiece.b();
        this.a.add(new StructureBoundingBox(this.g.d - 1, localStructureBoundingBox.b, localStructureBoundingBox.c, this.g.d, localStructureBoundingBox.e, localStructureBoundingBox.f));
      }
      k += 4;
    }
  }
  

  public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
  {
    if (a(paramWorld, paramStructureBoundingBox)) {
      return false;
    }
    




    a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.b, this.g.c, this.g.d, this.g.b, this.g.f, Block.DIRT.id, 0, true);
    

    a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.b + 1, this.g.c, this.g.d, Math.min(this.g.b + 3, this.g.e), this.g.f, 0, 0, false);
    for (StructureBoundingBox localStructureBoundingBox : this.a.toArray(new StructureBoundingBox[this.a.size()])) { // BTCS: added .toArray()
      a(paramWorld, paramStructureBoundingBox, localStructureBoundingBox.a, localStructureBoundingBox.e - 2, localStructureBoundingBox.c, localStructureBoundingBox.d, localStructureBoundingBox.e, localStructureBoundingBox.f, 0, 0, false);
    }
    a(paramWorld, paramStructureBoundingBox, this.g.a, this.g.b + 4, this.g.c, this.g.d, this.g.e, this.g.f, 0, false);
    
    return true;
  }
}
