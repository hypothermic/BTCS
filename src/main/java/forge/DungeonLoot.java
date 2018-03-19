package forge;

import java.util.Random;
import net.minecraft.server.ItemStack;






public class DungeonLoot
{
  private ItemStack itemStack;
  private int minCount = 1;
  private int maxCount = 1;
  





  public DungeonLoot(ItemStack item, int min, int max)
  {
    this.itemStack = item;
    this.minCount = min;
    this.maxCount = max;
  }
  






  public ItemStack generateStack(Random rand)
  {
    ItemStack ret = this.itemStack.cloneItemStack();
    ret.count = (this.minCount + rand.nextInt(this.maxCount - this.minCount + 1));
    return ret;
  }
  
  public boolean equals(ItemStack item, int min, int max)
  {
    return (min == this.minCount) && (max == this.maxCount) && (item.c(this.itemStack));
  }
  
  public boolean equals(ItemStack item)
  {
    return item.c(this.itemStack);
  }
}
