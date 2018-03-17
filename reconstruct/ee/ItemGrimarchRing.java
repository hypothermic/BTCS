package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemGrimarchRing extends ItemEECharged
{
  public ItemGrimarchRing(int var1)
  {
    super(var1, 0);
  }
  
  public int ConsumeReagent(EntityHuman var1)
  {
    return EEBase.Consume(new ItemStack(Block.COBBLESTONE, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Block.SAND, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Block.DIRT, 14), var1, false) ? 1 : EEBase.Consume(new ItemStack(Item.ARROW, 1), var1, false) ? 1 : EEBase.consumeKleinStarPoint(var1, 14) ? 1 : 0;
  }
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3, int var4)
  {
    int var5 = var4;
    int var6;
    for (; var5 > 0; 
        







        var6 > 0)
    {
      var6 = ConsumeReagent(var3);
      
      if (var6 == 0)
      {





        break;var2.makeSound(var3, "random.bow", 0.8F, 0.8F / (c.nextFloat() * 0.4F + 0.8F));
        var2.addEntity(new EntityGrimArrow(var2, var3));
        var5--;
        var6--;
      }
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (net.minecraft.server.EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doBreak(var1, var2, var3, 1);
    return var1;
  }
  

  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.updatePlayerEffect(var1.getItem(), 100, var2);
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3, 1);
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    if (canActivate())
    {
      if ((var1.getData() & 0x1) == 1)
      {
        var1.setData(var1.getData() - 1);
        var2.makeSound(var3, "break", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
      }
      else
      {
        var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
        var1.setData(var1.getData() + 1);
      }
    }
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3, 10);
  }
  
  public boolean canActivate()
  {
    return true;
  }
}
