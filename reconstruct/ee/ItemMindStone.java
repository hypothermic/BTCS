package ee;

import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemMindStone extends ItemEECharged
{
  private int tickCount;
  
  public ItemMindStone(int var1)
  {
    super(var1, 0);
    this.weaponDamage = 1;
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    playerTakeLevel(var1, var2, var3, 1);
    return var1;
  }
  

  public void doPassive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    stoneTakeXP(var1, var2, var3);
  }
  
  private void stoneTakeXP(ItemStack var1, World var2, EntityHuman var3)
  {
    if ((var3.expLevel > 0) && (var3.exp < 1.0F / var3.getExpToLevel()))
    {
      var3.levelDown(1);
      setLong(var1, "experience", getLong(var1, "experience") + var3.getExpToLevel());
    }
    else
    {
      var3.exp -= 1.0F / var3.getExpToLevel();
      setLong(var1, "experience", getLong(var1, "experience") + 1L);
    }
  }
  
  private void playerTakeLevel(ItemStack var1, World var2, EntityHuman var3, int var4)
  {
    boolean var5 = false;
    
    while ((getLong(var1, "experience") > 0L) && (!var5))
    {
      setLong(var1, "experience", getLong(var1, "experience") - 1L);
      var3.exp += 1.0F / var3.getExpToLevel();
      
      if (var3.exp >= 1.0F)
      {
        var3.exp -= 1.0F;
        var3.expLevel += 1;
        var5 = true;
      }
    }
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public boolean canActivate()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
