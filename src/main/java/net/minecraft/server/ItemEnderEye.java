package net.minecraft.server;

import java.util.Random;




public class ItemEnderEye
  extends Item
{
  public ItemEnderEye(int paramInt)
  {
    super(paramInt);
  }
  
  public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
    int j = paramWorld.getData(paramInt1, paramInt2, paramInt3);
    
    if ((paramEntityHuman.d(paramInt1, paramInt2, paramInt3)) && (i == Block.ENDER_PORTAL_FRAME.id) && (!BlockEnderPortalFrame.d(j)))
    {


      if (paramWorld.isStatic) return true;
      paramWorld.setData(paramInt1, paramInt2, paramInt3, j + 4);
      paramItemStack.count -= 1;
      
      for (int k = 0; k < 16; k++) {
        double d1 = paramInt1 + (5.0F + c.nextFloat() * 6.0F) / 16.0F;
        double d2 = paramInt2 + 0.8125F;
        double d3 = paramInt3 + (5.0F + c.nextFloat() * 6.0F) / 16.0F;
        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        
        paramWorld.a("smoke", d1, d2, d3, d4, d5, d6);
      }
      

      int k = j & 0x3; // BTCS: added 'int ' decl
      int m = 0;
      int n = 0;
      int i1 = 0;
      int i2 = 1;
      int i3 = Direction.f[k];
      int i5; int i6; int i7; int i8; for (int i4 = -2; i4 <= 2; i4++) {
        i5 = paramInt1 + Direction.a[i3] * i4;
        i6 = paramInt3 + Direction.b[i3] * i4;
        
        i7 = paramWorld.getTypeId(i5, paramInt2, i6);
        if (i7 == Block.ENDER_PORTAL_FRAME.id) {
          i8 = paramWorld.getData(i5, paramInt2, i6);
          if (!BlockEnderPortalFrame.d(i8)) {
            i2 = 0;
            break;
          }
          if (i1 == 0) {
            m = i4;
            n = i4;
            i1 = 1;
          } else {
            n = i4;
          }
        }
      }
      

      if ((i2 != 0) && (n == m + 2))
      {

        for (int i4 = m; i4 <= n; i4++) { // BTCS: added 'int ' decl
          i5 = paramInt1 + Direction.a[i3] * i4;
          i6 = paramInt3 + Direction.b[i3] * i4;
          i5 += Direction.a[k] * 4;
          i6 += Direction.b[k] * 4;
          
          i7 = paramWorld.getTypeId(i5, paramInt2, i6);
          i8 = paramWorld.getData(i5, paramInt2, i6);
          if ((i7 != Block.ENDER_PORTAL_FRAME.id) || (!BlockEnderPortalFrame.d(i8))) {
            i2 = 0;
            break;
          }
        }
        
        for (int i4 = m - 1; i4 <= n + 1; i4 += 4) { // BTCS: added 'int ' decl
          for (i5 = 1; i5 <= 3; i5++) {
            i6 = paramInt1 + Direction.a[i3] * i4;
            i7 = paramInt3 + Direction.b[i3] * i4;
            i6 += Direction.a[k] * i5;
            i7 += Direction.b[k] * i5;
            
            i8 = paramWorld.getTypeId(i6, paramInt2, i7);
            int i9 = paramWorld.getData(i6, paramInt2, i7);
            if ((i8 != Block.ENDER_PORTAL_FRAME.id) || (!BlockEnderPortalFrame.d(i9))) {
              i2 = 0;
              break;
            }
          }
        }
        if (i2 != 0)
        {
          for (int i4 = m; i4 <= n; i4++) { // BTCS: added 'int ' decl
            for (i5 = 1; i5 <= 3; i5++) {
              i6 = paramInt1 + Direction.a[i3] * i4;
              i7 = paramInt3 + Direction.b[i3] * i4;
              i6 += Direction.a[k] * i5;
              i7 += Direction.b[k] * i5;
              
              paramWorld.setTypeId(i6, paramInt2, i7, Block.ENDER_PORTAL.id);
            }
          }
        }
      }
      
      return true;
    }
    return false;
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, false);
    if ((localMovingObjectPosition != null) && (localMovingObjectPosition.type == EnumMovingObjectType.TILE)) {
      int i = paramWorld.getTypeId(localMovingObjectPosition.b, localMovingObjectPosition.c, localMovingObjectPosition.d);
      if (i == Block.ENDER_PORTAL_FRAME.id) {
        return paramItemStack;
      }
    }
    

    if (!paramWorld.isStatic) {
      ChunkPosition localChunkPosition = paramWorld.b("Stronghold", (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ);
      if (localChunkPosition != null) {
        EntityEnderSignal localEntityEnderSignal = new EntityEnderSignal(paramWorld, paramEntityHuman.locX, paramEntityHuman.locY + 1.62D - paramEntityHuman.height, paramEntityHuman.locZ);
        localEntityEnderSignal.a(localChunkPosition.x, localChunkPosition.y, localChunkPosition.z);
        paramWorld.addEntity(localEntityEnderSignal);
        
        paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (c.nextFloat() * 0.4F + 0.8F));
        paramWorld.a(null, 1002, (int)paramEntityHuman.locX, (int)paramEntityHuman.locY, (int)paramEntityHuman.locZ, 0);
        if (!paramEntityHuman.abilities.canInstantlyBuild) {
          paramItemStack.count -= 1;
        }
      }
    }
    return paramItemStack;
  }
}
