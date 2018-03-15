package net.minecraft.server;

import java.util.Random;

public class TileEntityMobSpawner extends TileEntity
{
  public int spawnDelay = -1;
  public String mobName = "Pig";
  public double b;
  public double c = 0.0D;
  
  public TileEntityMobSpawner() {
    this.spawnDelay = 20;
  }
  
  public void a(String s) {
    this.mobName = s;
  }
  
  public boolean c() {
    return this.world.findNearbyPlayer(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D, 16.0D) != null;
  }
  
  public void q_() {
    this.c = this.b;
    if (c()) {
      double d0 = this.x + this.world.random.nextFloat();
      double d1 = this.y + this.world.random.nextFloat();
      double d2 = this.z + this.world.random.nextFloat();
      
      this.world.a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
      this.world.a("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
      
      for (this.b += 1000.0F / (this.spawnDelay + 200.0F); this.b > 360.0D; this.c -= 360.0D) {
        this.b -= 360.0D;
      }
      
      if (!this.world.isStatic) {
        if (this.spawnDelay == -1) {
          e();
        }
        
        if (this.spawnDelay > 0) {
          this.spawnDelay -= 1;
          return;
        }
        
        byte b0 = 4;
        
        for (int i = 0; i < b0; i++)
        {
          Entity mob = EntityTypes.createEntityByName(this.mobName, this.world);
          
          if (!(mob instanceof EntityLiving)) {
            this.mobName = "Pig";
            return;
          }
          
          EntityLiving entityliving = (EntityLiving)mob;
          

          if (entityliving == null) {
            return;
          }
          
          int j = this.world.a(entityliving.getClass(), AxisAlignedBB.b(this.x, this.y, this.z, this.x + 1, this.y + 1, this.z + 1).grow(8.0D, 4.0D, 8.0D)).size();
          
          if (j >= 6) {
            e();
            return;
          }
          
          if (entityliving != null) {
            double d3 = this.x + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;
            double d4 = this.y + this.world.random.nextInt(3) - 1;
            double d5 = this.z + (this.world.random.nextDouble() - this.world.random.nextDouble()) * 4.0D;
            
            entityliving.setPositionRotation(d3, d4, d5, this.world.random.nextFloat() * 360.0F, 0.0F);
            if (entityliving.canSpawn()) {
              this.world.addEntity(entityliving, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER);
              this.world.triggerEffect(2004, this.x, this.y, this.z, 0);
              entityliving.aC();
              e();
            }
          }
        }
      }
      
      super.q_();
    }
  }
  
  private void e() {
    this.spawnDelay = (200 + this.world.random.nextInt(600));
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    super.a(nbttagcompound);
    this.mobName = nbttagcompound.getString("EntityId");
    this.spawnDelay = nbttagcompound.getShort("Delay");
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    super.b(nbttagcompound);
    nbttagcompound.setString("EntityId", this.mobName);
    nbttagcompound.setShort("Delay", (short)this.spawnDelay);
  }
  
  public Packet d() {
    int i = EntityTypes.a(this.mobName);
    
    return new Packet132TileEntityData(this.x, this.y, this.z, 1, i);
  }
}
