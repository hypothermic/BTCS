package net.minecraft.server;

import java.util.Random;

public class ItemMonsterEgg extends Item {
  public ItemMonsterEgg(int i) { super(i);
    a(true);
  }
  
  public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l) {
    if ((world.isStatic) || (itemstack.getData() == 48) || (itemstack.getData() == 49) || (itemstack.getData() == 63)) {
      return true;
    }
    int i1 = world.getTypeId(i, j, k);
    
    i += Facing.b[l];
    j += Facing.c[l];
    k += Facing.d[l];
    double d0 = 0.0D;
    
    if (((l == 1) && (i1 == Block.FENCE.id)) || (i1 == Block.NETHER_FENCE.id)) {
      d0 = 0.5D;
    }
    
    if ((a(world, itemstack.getData(), i + 0.5D, j + d0, k + 0.5D)) && (!entityhuman.abilities.canInstantlyBuild)) {
      itemstack.count -= 1;
    }
    
    return true;
  }
  
  public static boolean a(World world, int i, double d0, double d1, double d2)
  {
    if (!EntityTypes.a.containsKey(Integer.valueOf(i))) {
      return false;
    }
    Entity entity = EntityTypes.a(i, world);
    
    if ((entity != null) && ((entity instanceof EntityLiving))) {
      entity.setPositionRotation(d0, d1, d2, world.random.nextFloat() * 360.0F, 0.0F);
      world.addEntity(entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
      ((EntityLiving)entity).az();
    }
    
    return entity != null;
  }
}
