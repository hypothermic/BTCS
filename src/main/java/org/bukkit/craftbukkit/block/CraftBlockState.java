package org.bukkit.craftbukkit.block;

import net.minecraft.server.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.metadata.BlockMetadataStore;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CraftBlockState implements org.bukkit.block.BlockState
{
  private final CraftWorld world;
  private final CraftChunk chunk;
  private final int x;
  private final int y;
  private final int z;
  protected int type;
  protected MaterialData data;
  protected byte light;
  
  public CraftBlockState(Block block)
  {
    this.world = ((CraftWorld)block.getWorld());
    this.x = block.getX();
    this.y = block.getY();
    this.z = block.getZ();
    this.type = block.getTypeId();
    this.light = block.getLightLevel();
    this.chunk = ((CraftChunk)block.getChunk());
    
    createData(block.getData());
  }
  
  public static CraftBlockState getBlockState(net.minecraft.server.World world, int x, int y, int z) {
    return new CraftBlockState(world.getWorld().getBlockAt(x, y, z));
  }
  
  public org.bukkit.World getWorld() {
    return this.world;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public int getZ() {
    return this.z;
  }
  
  public Chunk getChunk() {
    return this.chunk;
  }
  
  public void setData(MaterialData data) {
    Material mat = getType();
    
    if ((mat == null) || (mat.getData() == null)) {
      this.data = data;
    }
    else if ((data.getClass() == mat.getData()) || (data.getClass() == MaterialData.class)) {
      this.data = data;
    } else {
      throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
    }
  }
  

  public MaterialData getData()
  {
    return this.data;
  }
  
  public void setType(Material type) {
    setTypeId(type.getId());
  }
  
  public boolean setTypeId(int type) {
    if (this.type != type) {
      this.type = type;
      
      createData((byte)0);
    }
    return true;
  }
  
  public Material getType() {
    return Material.getMaterial(getTypeId());
  }
  
  public int getTypeId() {
    return this.type;
  }
  
  public byte getLightLevel() {
    return this.light;
  }
  
  public Block getBlock() {
    return this.world.getBlockAt(this.x, this.y, this.z);
  }
  
  public boolean update() {
    return update(false);
  }
  
  public boolean update(boolean force) {
    Block block = getBlock();
    
    synchronized (block) {
      if (block.getType() != getType()) {
        if (force) {
          block.setTypeId(getTypeId());
        } else {
          return false;
        }
      }
      
      block.setData(getRawData());
      this.world.getHandle().notify(this.x, this.y, this.z);
    }
    
    return true;
  }
  
  private void createData(byte data) {
    Material mat = getType();
    if ((mat == null) || (mat.getData() == null)) {
      this.data = new MaterialData(this.type, data);
    } else {
      this.data = mat.getNewData(data);
    }
  }
  
  public byte getRawData() {
    return this.data.getData();
  }
  
  public Location getLocation() {
    return new Location(this.world, this.x, this.y, this.z);
  }
  
  public void setRawData(byte data) {
    this.data.setData(data);
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CraftBlockState other = (CraftBlockState)obj;
    if ((this.world != other.world) && ((this.world == null) || (!this.world.equals(other.world)))) {
      return false;
    }
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    if (this.z != other.z) {
      return false;
    }
    if (this.type != other.type) {
      return false;
    }
    if ((this.data != other.data) && ((this.data == null) || (!this.data.equals(other.data)))) {
      return false;
    }
    return true;
  }
  
  public int hashCode()
  {
    int hash = 7;
    hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);
    hash = 73 * hash + this.x;
    hash = 73 * hash + this.y;
    hash = 73 * hash + this.z;
    hash = 73 * hash + this.type;
    hash = 73 * hash + (this.data != null ? this.data.hashCode() : 0);
    return hash;
  }
  
  public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
    this.chunk.getCraftWorld().getBlockMetadata().setMetadata(getBlock(), metadataKey, newMetadataValue);
  }
  
  public java.util.List<MetadataValue> getMetadata(String metadataKey) {
    return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(getBlock(), metadataKey);
  }
  
  public boolean hasMetadata(String metadataKey) {
    return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(getBlock(), metadataKey);
  }
  
  public void removeMetadata(String metadataKey, Plugin owningPlugin) {
    this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(getBlock(), metadataKey, owningPlugin);
  }
}
