package org.bukkit.block;

public abstract interface Dispenser
  extends BlockState, ContainerBlock
{
  public abstract boolean dispense();
}
