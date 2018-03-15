package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntitySign;
import org.bukkit.block.Block;

public class CraftSign extends CraftBlockState implements org.bukkit.block.Sign
{
  private final TileEntitySign sign;
  private final String[] lines;
  
  public CraftSign(Block block)
  {
    super(block);
    
    org.bukkit.craftbukkit.CraftWorld world = (org.bukkit.craftbukkit.CraftWorld)block.getWorld();
    this.sign = ((TileEntitySign)world.getTileEntityAt(getX(), getY(), getZ()));
    this.lines = new String[this.sign.lines.length];
    System.arraycopy(this.sign.lines, 0, this.lines, 0, this.lines.length);
  }
  
  public String[] getLines() {
    return this.lines;
  }
  
  public String getLine(int index) throws IndexOutOfBoundsException {
    return this.lines[index];
  }
  
  public void setLine(int index, String line) throws IndexOutOfBoundsException {
    this.lines[index] = line;
  }
  
  public boolean update(boolean force)
  {
    boolean result = super.update(force);
    
    if (result) {
      System.arraycopy(this.lines, 0, this.sign.lines, 0, this.lines.length);
      this.sign.update();
    }
    
    return result;
  }
}
