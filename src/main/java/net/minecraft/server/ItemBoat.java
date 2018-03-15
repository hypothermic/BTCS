package net.minecraft.server;

import java.util.List;

public class ItemBoat extends Item
{
  public ItemBoat(int i) {
    super(i);
    this.maxStackSize = 1;
  }
  
  public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
    float f = 1.0F;
    float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
    float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
    double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
    double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62D - entityhuman.height;
    double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
    Vec3D vec3d = Vec3D.create(d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
    MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, true);
    
    if (movingobjectposition == null) {
      return itemstack;
    }
    Vec3D vec3d2 = entityhuman.f(f);
    boolean flag = false;
    float f9 = 1.0F;
    List list = world.getEntities(entityhuman, entityhuman.boundingBox.a(vec3d2.a * d3, vec3d2.b * d3, vec3d2.c * d3).grow(f9, f9, f9));
    
    for (int i = 0; i < list.size(); i++) {
      Entity entity = (Entity)list.get(i);
      
      if (entity.o_()) {
        float f10 = entity.j_();
        AxisAlignedBB axisalignedbb = entity.boundingBox.grow(f10, f10, f10);
        
        if (axisalignedbb.a(vec3d)) {
          flag = true;
        }
      }
    }
    
    if (flag) {
      return itemstack;
    }
    if (movingobjectposition.type == EnumMovingObjectType.TILE) {
      int j = movingobjectposition.b;
      int k = movingobjectposition.c;
      int l = movingobjectposition.d;
      
      if (!world.isStatic)
      {
        org.bukkit.event.player.PlayerInteractEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerInteractEvent(entityhuman, org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK, j, k, l, movingobjectposition.face, itemstack);
        
        if (event.isCancelled()) {
          return itemstack;
        }
        

        if (world.getTypeId(j, k, l) == Block.SNOW.id) {
          k--;
        }
        
        world.addEntity(new EntityBoat(world, j + 0.5F, k + 1.0F, l + 0.5F));
      }
      
      if (!entityhuman.abilities.canInstantlyBuild) {
        itemstack.count -= 1;
      }
    }
    
    return itemstack;
  }
}
