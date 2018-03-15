package org.bukkit.craftbukkit.map;

import java.util.ArrayList;

public class RenderData
{
  public final byte[] buffer;
  public final ArrayList<org.bukkit.map.MapCursor> cursors;
  
  public RenderData()
  {
    this.buffer = new byte['䀀'];
    this.cursors = new ArrayList();
  }
}
