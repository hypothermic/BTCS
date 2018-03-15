package org.bukkit.event.painting;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Painting;

public class PaintingBreakByEntityEvent
  extends PaintingBreakEvent
{
  private final Entity remover;
  
  public PaintingBreakByEntityEvent(Painting painting, Entity remover)
  {
    super(painting, PaintingBreakEvent.RemoveCause.ENTITY);
    this.remover = remover;
  }
  




  public Entity getRemover()
  {
    return this.remover;
  }
}
