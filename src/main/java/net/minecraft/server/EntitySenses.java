package net.minecraft.server;

import java.util.ArrayList;


public class EntitySenses
{
  EntityLiving entity;
  ArrayList seenEntities = new ArrayList();
  ArrayList unseenEntities = new ArrayList();
  
  public EntitySenses(EntityLiving paramEntityLiving) {
    this.entity = paramEntityLiving;
  }
  
  public void a() {
    this.seenEntities.clear();
    this.unseenEntities.clear();
  }
  
  public boolean canSee(Entity paramEntity) {
    if (this.seenEntities.contains(paramEntity)) return true;
    if (this.unseenEntities.contains(paramEntity)) { return false;
    }
    MethodProfiler.a("canSee");
    boolean bool = this.entity.h(paramEntity);
    MethodProfiler.a();
    if (bool) this.seenEntities.add(paramEntity); else
      this.unseenEntities.add(paramEntity);
    return bool;
  }
}
