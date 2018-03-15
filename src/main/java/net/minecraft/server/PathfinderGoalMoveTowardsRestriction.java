package net.minecraft.server;


public class PathfinderGoalMoveTowardsRestriction
  extends PathfinderGoal
{
  private EntityCreature a;
  
  private double b;
  private double c;
  private double d;
  private float e;
  
  public PathfinderGoalMoveTowardsRestriction(EntityCreature paramEntityCreature, float paramFloat)
  {
    this.a = paramEntityCreature;
    this.e = paramFloat;
    a(1);
  }
  
  public boolean a()
  {
    if (this.a.au()) return false;
    ChunkCoordinates localChunkCoordinates = this.a.av();
    Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.create(localChunkCoordinates.x, localChunkCoordinates.y, localChunkCoordinates.z));
    if (localVec3D == null) return false;
    this.b = localVec3D.a;
    this.c = localVec3D.b;
    this.d = localVec3D.c;
    return true;
  }
  
  public boolean b()
  {
    return !this.a.al().e();
  }
  
  public void c()
  {
    this.a.al().a(this.b, this.c, this.d, this.e);
  }
}
