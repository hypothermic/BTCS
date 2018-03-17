package net.minecraft.server;

public class WM_ItemCannon extends WM_Item
{
  public WM_ItemCannon(int paramInt)
  {
    super(paramInt);
    this.maxStackSize = 1;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    float f1 = 1.0F;
    float f2 = paramEntityHuman.lastPitch + (paramEntityHuman.pitch - paramEntityHuman.lastPitch) * f1;
    float f3 = paramEntityHuman.lastYaw + (paramEntityHuman.yaw - paramEntityHuman.lastYaw) * f1;
    double d1 = paramEntityHuman.lastX + (paramEntityHuman.locX - paramEntityHuman.lastX) * f1;
    double d2 = paramEntityHuman.lastY + (paramEntityHuman.locY - paramEntityHuman.lastY) * f1 + 1.62D - paramEntityHuman.height;
    double d3 = paramEntityHuman.lastZ + (paramEntityHuman.locZ - paramEntityHuman.lastZ) * f1;
    Vec3D localVec3D1 = Vec3D.create(d1, d2, d3);
    float f4 = MathHelper.cos(-f3 * 0.01745329F - 3.1415927F);
    float f5 = MathHelper.sin(-f3 * 0.01745329F - 3.1415927F);
    float f6 = -MathHelper.cos(-f2 * 0.01745329F);
    float f7 = MathHelper.sin(-f2 * 0.01745329F);
    float f8 = f5 * f6;
    float f9 = f7;
    float f10 = f4 * f6;
    double d4 = 5.0D;
    Vec3D localVec3D2 = localVec3D1.add(f8 * d4, f9 * d4, f10 * d4);
    MovingObjectPosition localMovingObjectPosition = paramWorld.rayTrace(localVec3D1, localVec3D2, true);
    
    if (localMovingObjectPosition == null)
    {
      return paramItemStack;
    }
    
    if (localMovingObjectPosition.type == EnumMovingObjectType.TILE)
    {
      int i = localMovingObjectPosition.b;
      int j = localMovingObjectPosition.c;
      int k = localMovingObjectPosition.d;
      
      if (!paramWorld.isStatic)
      {
        if (paramWorld.getTypeId(i, j, k) == Block.SNOW.id)
        {
          j--;
        }
        
        paramWorld.addEntity(new WM_EntityCannon(paramWorld, i + 0.5F, j + 1.0F, k + 0.5F));
      }
      
      if (!paramEntityHuman.abilities.canInstantlyBuild)
      {
        paramItemStack.count -= 1;
      }
    }
    
    return paramItemStack;
  }
}
