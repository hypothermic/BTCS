package org.bukkit;

import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;









public class Location
  implements Cloneable
{
  private World world;
  private double x;
  private double y;
  private double z;
  private float pitch;
  private float yaw;
  
  public Location(World world, double x, double y, double z)
  {
    this(world, x, y, z, 0.0F, 0.0F);
  }
  









  public Location(World world, double x, double y, double z, float yaw, float pitch)
  {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }
  




  public void setWorld(World world)
  {
    this.world = world;
  }
  




  public World getWorld()
  {
    return this.world;
  }
  




  public Chunk getChunk()
  {
    return this.world.getChunkAt(this);
  }
  




  public Block getBlock()
  {
    return this.world.getBlockAt(this);
  }
  




  public void setX(double x)
  {
    this.x = x;
  }
  




  public double getX()
  {
    return this.x;
  }
  





  public int getBlockX()
  {
    return locToBlock(this.x);
  }
  




  public void setY(double y)
  {
    this.y = y;
  }
  




  public double getY()
  {
    return this.y;
  }
  





  public int getBlockY()
  {
    return locToBlock(this.y);
  }
  




  public void setZ(double z)
  {
    this.z = z;
  }
  




  public double getZ()
  {
    return this.z;
  }
  





  public int getBlockZ()
  {
    return locToBlock(this.z);
  }
  




  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
  




  public float getYaw()
  {
    return this.yaw;
  }
  




  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
  




  public float getPitch()
  {
    return this.pitch;
  }
  




  public Vector getDirection()
  {
    Vector vector = new Vector();
    
    double rotX = getYaw();
    double rotY = getPitch();
    
    vector.setY(-Math.sin(Math.toRadians(rotY)));
    
    double h = Math.cos(Math.toRadians(rotY));
    
    vector.setX(-h * Math.sin(Math.toRadians(rotX)));
    vector.setZ(h * Math.cos(Math.toRadians(rotX)));
    
    return vector;
  }
  







  public Location add(Location vec)
  {
    if ((vec == null) || (vec.getWorld() != getWorld())) {
      throw new IllegalArgumentException("Cannot add Locations of differing worlds");
    }
    
    this.x += vec.x;
    this.y += vec.y;
    this.z += vec.z;
    return this;
  }
  






  public Location add(Vector vec)
  {
    this.x += vec.getX();
    this.y += vec.getY();
    this.z += vec.getZ();
    return this;
  }
  








  public Location add(double x, double y, double z)
  {
    this.x += x;
    this.y += y;
    this.z += z;
    return this;
  }
  







  public Location subtract(Location vec)
  {
    if ((vec == null) || (vec.getWorld() != getWorld())) {
      throw new IllegalArgumentException("Cannot add Locations of differing worlds");
    }
    
    this.x -= vec.x;
    this.y -= vec.y;
    this.z -= vec.z;
    return this;
  }
  






  public Location subtract(Vector vec)
  {
    this.x -= vec.getX();
    this.y -= vec.getY();
    this.z -= vec.getZ();
    return this;
  }
  









  public Location subtract(double x, double y, double z)
  {
    this.x -= x;
    this.y -= y;
    this.z -= z;
    return this;
  }
  










  public double length()
  {
    return Math.sqrt(Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D));
  }
  






  public double lengthSquared()
  {
    return Math.pow(this.x, 2.0D) + Math.pow(this.y, 2.0D) + Math.pow(this.z, 2.0D);
  }
  











  public double distance(Location o)
  {
    return Math.sqrt(distanceSquared(o));
  }
  







  public double distanceSquared(Location o)
  {
    if (o == null)
      throw new IllegalArgumentException("Cannot measure distance to a null location");
    if ((o.getWorld() == null) || (getWorld() == null))
      throw new IllegalArgumentException("Cannot measure distance to a null world");
    if (o.getWorld() != getWorld()) {
      throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getName() + " and " + o.getWorld().getName());
    }
    
    return Math.pow(this.x - o.x, 2.0D) + Math.pow(this.y - o.y, 2.0D) + Math.pow(this.z - o.z, 2.0D);
  }
  







  public Location multiply(double m)
  {
    this.x *= m;
    this.y *= m;
    this.z *= m;
    return this;
  }
  





  public Location zero()
  {
    this.x = 0.0D;
    this.y = 0.0D;
    this.z = 0.0D;
    return this;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Location other = (Location)obj;
    
    if ((this.world != other.world) && ((this.world == null) || (!this.world.equals(other.world)))) {
      return false;
    }
    if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
      return false;
    }
    if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
      return false;
    }
    if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
      return false;
    }
    if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
      return false;
    }
    if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    int hash = 3;
    
    hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
    hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
    hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
    hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
    hash = 19 * hash + Float.floatToIntBits(this.pitch);
    hash = 19 * hash + Float.floatToIntBits(this.yaw);
    return hash;
  }
  
  public String toString()
  {
    return "Location{world=" + this.world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",pitch=" + this.pitch + ",yaw=" + this.yaw + '}';
  }
  




  public Vector toVector()
  {
    return new Vector(this.x, this.y, this.z);
  }
  
  public Location clone()
  {
    try {
      return (Location)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }
  





  public static int locToBlock(double loc)
  {
    return NumberConversions.floor(loc);
  }
}
