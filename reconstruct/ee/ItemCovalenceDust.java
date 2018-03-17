package ee;

import java.util.ArrayList;
import net.minecraft.server.ItemStack;

public class ItemCovalenceDust extends ItemStackable
{
  public ItemCovalenceDust(int var1)
  {
    super(var1);
    a(true);
    setMaxDurability(0);
  }
  
  public int getIconFromDamage(int var1)
  {
    return var1 < 3 ? this.textureId + var1 : this.textureId;
  }
  
  public void addCreativeItems(ArrayList var1)
  {
    var1.add(new ItemStack(EEItem.covalenceDust, 1, 0));
    var1.add(new ItemStack(EEItem.covalenceDust, 1, 1));
    var1.add(new ItemStack(EEItem.covalenceDust, 1, 2));
  }
}
