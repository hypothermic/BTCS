package ee;

import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemLifeStone extends ItemEECharged
{
  private int tickCount;
  
  public ItemLifeStone(int var1)
  {
    super(var1, 0);
    this.weaponDamage = -2;
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.updatePlayerEffect(var1.getItem(), 200, var2);
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
  }
  
  public boolean burnFuel(ItemStack var1, EntityHuman var2, boolean var3)
  {
    if (getFuelRemaining(var1) >= 4)
    {
      setFuelRemaining(var1, getFuelRemaining(var1) - 4);
      return true;
    }
    

    super.ConsumeReagent(var1, var2, var3);
    
    if (getFuelRemaining(var1) >= 4)
    {
      setFuelRemaining(var1, getFuelRemaining(var1) - 4);
      return true;
    }
    

    return false;
  }
  


  public void doHeal(ItemStack var1, World var2, EntityHuman var3, int var4)
  {
    if ((EEProxy.getFoodStats(var3).a() < 19) && (burnFuel(var1, var3, false)))
    {
      EEProxy.getFoodStats(var3).eat(1, 1.0F);
      var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
    
    if ((EEProxy.getEntityHealth(var3) < 20) && (burnFuel(var1, var3, true)))
    {
      var3.heal(var4);
      var2.makeSound(var3, "heal", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doHeal(var1, var2, var3, 2);
    return var1;
  }
  

  public void doPassive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    this.tickCount += 1;
    
    if ((EEProxy.getFoodStats(var3).a() < 19) && (this.tickCount % 10 == 0))
    {
      doHeal(var1, var2, var3, 1);
    }
    
    if ((EEProxy.getEntityHealth(var3) < 20) && (this.tickCount % 10 == 0))
    {
      doHeal(var1, var2, var3, 1);
    }
    
    if (this.tickCount > 100)
    {
      this.tickCount = 1;
    }
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doHeal(var1, var2, var3, 2);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
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
  
  public boolean canActivate()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
