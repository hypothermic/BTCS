package org.bukkit.craftbukkit.block;

import net.minecraft.server.BlockJukeBox;
import net.minecraft.server.TileEntityRecordPlayer;
import net.minecraft.server.WorldServer;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftJukebox extends CraftBlockState implements Jukebox
{
  private final CraftWorld world;
  private final TileEntityRecordPlayer jukebox;
  
  public CraftJukebox(org.bukkit.block.Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.jukebox = ((TileEntityRecordPlayer)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public Material getPlaying() {
    return Material.getMaterial(this.jukebox.record);
  }
  
  public void setPlaying(Material record) {
    if (record == null) {
      record = Material.AIR;
    }
    this.jukebox.record = record.getId();
    this.jukebox.update();
    if (record == Material.AIR) {
      this.world.getHandle().setData(getX(), getY(), getZ(), 0);
    } else {
      this.world.getHandle().setData(getX(), getY(), getZ(), 1);
    }
    this.world.playEffect(getLocation(), org.bukkit.Effect.RECORD_PLAY, record.getId());
  }
  
  public boolean isPlaying() {
    return getRawData() == 1;
  }
  
  public boolean eject() {
    boolean result = isPlaying();
    ((BlockJukeBox)net.minecraft.server.Block.JUKEBOX).dropRecord(this.world.getHandle(), getX(), getY(), getZ());
    return result;
  }
}
