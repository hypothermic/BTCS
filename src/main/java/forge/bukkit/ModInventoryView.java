package forge.bukkit;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class ModInventoryView extends InventoryView
{
  private Container container;
  private EntityHuman player;
  
  public ModInventoryView(Container container, EntityHuman entityHuman)
  {
    this.container = container;
    this.player = entityHuman;
  }
  
  public Inventory getTopInventory()
  {
    return new CraftInventory(this.container.getInventory());
  }
  
  public Inventory getBottomInventory()
  {
    return this.player.getBukkitEntity().getInventory();
  }
  
  public HumanEntity getPlayer()
  {
    return this.player.getBukkitEntity();
  }
  
  public InventoryType getType()
  {
    return InventoryType.MOD;
  }
}
