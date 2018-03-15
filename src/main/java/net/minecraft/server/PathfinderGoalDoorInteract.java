package net.minecraft.server;

public abstract class PathfinderGoalDoorInteract
  extends PathfinderGoal
{
  protected EntityLiving a;
  protected int b;
  protected int c;
  protected int d;
  protected BlockDoor e;
  boolean f;
  float g;
  float h;
  
  public PathfinderGoalDoorInteract(EntityLiving paramEntityLiving)
  {
    this.a = paramEntityLiving;
  }
  
  public boolean a() {
    if (!this.a.positionChanged) return false;
    Navigation localNavigation = this.a.al();
    PathEntity localPathEntity = localNavigation.c();
    if ((localPathEntity == null) || (localPathEntity.b()) || (!localNavigation.b())) { return false;
    }
    for (int i = 0; i < Math.min(localPathEntity.e() + 2, localPathEntity.d()); i++) {
      PathPoint localPathPoint = localPathEntity.a(i);
      this.b = localPathPoint.a;
      this.c = (localPathPoint.b + 1);
      this.d = localPathPoint.c;
      if (this.a.e(this.b, this.a.locY, this.d) <= 2.25D) {
        this.e = a(this.b, this.c, this.d);
        if (this.e != null)
          return true;
      }
    }
    this.b = MathHelper.floor(this.a.locX);
    this.c = MathHelper.floor(this.a.locY + 1.0D);
    this.d = MathHelper.floor(this.a.locZ);
    this.e = a(this.b, this.c, this.d);
    return this.e != null;
  }
  
  public boolean b() {
    return !this.f;
  }
  
  public void c() {
    this.f = false;
    this.g = ((float)(this.b + 0.5F - this.a.locX));
    this.h = ((float)(this.d + 0.5F - this.a.locZ));
  }
  
  public void e() {
    float f1 = (float)(this.b + 0.5F - this.a.locX);
    float f2 = (float)(this.d + 0.5F - this.a.locZ);
    float f3 = this.g * f1 + this.h * f2;
    if (f3 < 0.0F) {
      this.f = true;
    }
  }
  
  private BlockDoor a(int paramInt1, int paramInt2, int paramInt3) {
    int i = this.a.world.getTypeId(paramInt1, paramInt2, paramInt3);
    if (i != Block.WOODEN_DOOR.id) return null;
    BlockDoor localBlockDoor = (BlockDoor)Block.byId[i];
    return localBlockDoor;
  }
}
