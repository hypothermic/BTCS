package net.minecraft.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart
{
  protected LinkedList a = new LinkedList();
  

  protected StructureBoundingBox b;
  

  public StructureBoundingBox b()
  {
    return this.b;
  }
  
  public LinkedList c() {
    return this.a;
  }
  
  public void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext()) {
      StructurePiece localStructurePiece = (StructurePiece)localIterator.next();
      if ((localStructurePiece.b().a(paramStructureBoundingBox)) && 
        (!localStructurePiece.a(paramWorld, paramRandom, paramStructureBoundingBox)))
      {

        localIterator.remove();
      }
    }
  }

  protected void d()
  {
    this.b = StructureBoundingBox.a();
    
    for (StructurePiece localStructurePiece : (StructurePiece[]) this.a.toArray()) { // BTCS: added cast and .toArray()
      this.b.b(localStructurePiece.b());
    }
  }
  
  protected void a(World paramWorld, Random paramRandom, int paramInt) {
    int i = 63 - paramInt;
    

    int j = this.b.c() + 1;
    
    if (j < i) {
      j += paramRandom.nextInt(i - j);
    }
    

    int k = j - this.b.e;
    this.b.a(0, k, 0);
    for (StructurePiece localStructurePiece : (StructurePiece[]) this.a.toArray()) { // BTCS: added cast and .toArray()
      localStructurePiece.b().a(0, k, 0);
    }
  }
  
  protected void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1 + 1 - this.b.c();
    int j = 1;
    
    if (i > 1) {
      j = paramInt1 + paramRandom.nextInt(i);
    } else {
      j = paramInt1;
    }
    

    int k = j - this.b.b;
    this.b.a(0, k, 0);
    for (StructurePiece localStructurePiece : (StructurePiece[]) this.a.toArray()) { // BTCS: added cast and .toArray()
      localStructurePiece.b().a(0, k, 0);
    }
  }
  
  public boolean a() {
    return true;
  }
}
