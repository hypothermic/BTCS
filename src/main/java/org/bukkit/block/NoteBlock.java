package org.bukkit.block;

import org.bukkit.Instrument;
import org.bukkit.Note;

public abstract interface NoteBlock
  extends BlockState
{
  public abstract Note getNote();
  
  public abstract byte getRawNote();
  
  public abstract void setNote(Note paramNote);
  
  public abstract void setRawNote(byte paramByte);
  
  public abstract boolean play();
  
  public abstract boolean play(byte paramByte1, byte paramByte2);
  
  public abstract boolean play(Instrument paramInstrument, Note paramNote);
}
