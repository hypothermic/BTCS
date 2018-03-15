package net.minecraft.server;

import java.util.Random;





public class WorldGenDesertWell
  extends WorldGenerator
{
  public boolean a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    while ((paramWorld.isEmpty(paramInt1, paramInt2, paramInt3)) && (paramInt2 > 2)) {
      paramInt2--;
    }
    int i = paramWorld.getTypeId(paramInt1, paramInt2, paramInt3);
    if (i != Block.SAND.id) {
      return false;
    }
    
    int k;
    for (int j = -2; j <= 2; j++) {
      for (k = -2; k <= 2; k++) {
        if ((paramWorld.isEmpty(paramInt1 + j, paramInt2 - 1, paramInt3 + k)) && (paramWorld.isEmpty(paramInt1 + j, paramInt2 - 2, paramInt3 + k))) {
          return false;
        }
      }
    }
    

    for (int j = -1; j <= 0; j++) { // BTCS: added decl 'int '
      for (k = -2; k <= 2; k++) {
        for (int m = -2; m <= 2; m++) {
          paramWorld.setRawTypeId(paramInt1 + k, paramInt2 + j, paramInt3 + m, Block.SANDSTONE.id);
        }
      }
    }
    

    paramWorld.setRawTypeId(paramInt1, paramInt2, paramInt3, Block.WATER.id);
    paramWorld.setRawTypeId(paramInt1 - 1, paramInt2, paramInt3, Block.WATER.id);
    paramWorld.setRawTypeId(paramInt1 + 1, paramInt2, paramInt3, Block.WATER.id);
    paramWorld.setRawTypeId(paramInt1, paramInt2, paramInt3 - 1, Block.WATER.id);
    paramWorld.setRawTypeId(paramInt1, paramInt2, paramInt3 + 1, Block.WATER.id);
    

    for (int j = -2; j <= 2; j++) { // BTCS: added decl 'int '
      for (k = -2; k <= 2; k++) {
        if ((j == -2) || (j == 2) || (k == -2) || (k == 2)) {
          paramWorld.setRawTypeId(paramInt1 + j, paramInt2 + 1, paramInt3 + k, Block.SANDSTONE.id);
        }
      }
    }
    paramWorld.setRawTypeIdAndData(paramInt1 + 2, paramInt2 + 1, paramInt3, Block.STEP.id, 1);
    paramWorld.setRawTypeIdAndData(paramInt1 - 2, paramInt2 + 1, paramInt3, Block.STEP.id, 1);
    paramWorld.setRawTypeIdAndData(paramInt1, paramInt2 + 1, paramInt3 + 2, Block.STEP.id, 1);
    paramWorld.setRawTypeIdAndData(paramInt1, paramInt2 + 1, paramInt3 - 2, Block.STEP.id, 1);
    

    for (int j = -1; j <= 1; j++) { // BTCS: added decl 'int '
      for (k = -1; k <= 1; k++) {
        if ((j == 0) && (k == 0)) {
          paramWorld.setRawTypeId(paramInt1 + j, paramInt2 + 4, paramInt3 + k, Block.SANDSTONE.id);
        } else {
          paramWorld.setRawTypeIdAndData(paramInt1 + j, paramInt2 + 4, paramInt3 + k, Block.STEP.id, 1);
        }
      }
    }
    

    for (int j = 1; j <= 3; j++) { // BTCS: added decl 'int '
      paramWorld.setRawTypeId(paramInt1 - 1, paramInt2 + j, paramInt3 - 1, Block.SANDSTONE.id);
      paramWorld.setRawTypeId(paramInt1 - 1, paramInt2 + j, paramInt3 + 1, Block.SANDSTONE.id);
      paramWorld.setRawTypeId(paramInt1 + 1, paramInt2 + j, paramInt3 - 1, Block.SANDSTONE.id);
      paramWorld.setRawTypeId(paramInt1 + 1, paramInt2 + j, paramInt3 + 1, Block.SANDSTONE.id);
    }
    
    return true;
  }
}
