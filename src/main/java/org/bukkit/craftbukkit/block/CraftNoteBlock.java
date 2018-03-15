package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityNote;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftNoteBlock extends CraftBlockState implements org.bukkit.block.NoteBlock
{
  private final CraftWorld world;
  private final TileEntityNote note;
  
  public CraftNoteBlock(Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.note = ((TileEntityNote)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public Note getNote() {
    return new Note(this.note.note);
  }
  
  public byte getRawNote() {
    return this.note.note;
  }
  
  public void setNote(Note n) {
    this.note.note = n.getId();
  }
  
  public void setRawNote(byte n) {
    this.note.note = n;
  }
  
  public boolean play() {
    Block block = getBlock();
    
    synchronized (block) {
      if (block.getType() == Material.NOTE_BLOCK) {
        this.note.play(this.world.getHandle(), getX(), getY(), getZ());
        return true;
      }
      return false;
    }
  }
  
  public boolean play(byte instrument, byte note)
  {
    Block block = getBlock();
    
    synchronized (block) {
      if (block.getType() == Material.NOTE_BLOCK) {
        this.world.getHandle().playNote(getX(), getY(), getZ(), instrument, note);
        return true;
      }
      return false;
    }
  }
  
  public boolean play(Instrument instrument, Note note)
  {
    Block block = getBlock();
    
    synchronized (block) {
      if (block.getType() == Material.NOTE_BLOCK) {
        this.world.getHandle().playNote(getX(), getY(), getZ(), instrument.getType(), note.getId());
        return true;
      }
      return false;
    }
  }
}
