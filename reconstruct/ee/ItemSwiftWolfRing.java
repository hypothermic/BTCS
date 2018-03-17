package ee;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemSwiftWolfRing extends ItemEECharged
{
  private int ticksLastSpent;
  public boolean itemCharging;
  
  public ItemSwiftWolfRing(int var1)
  {
    super(var1, 0);
  }
  
  public int getIconFromDamage(int var1)
  {
    return (isActivated(var1)) && (!isActivated2(var1)) ? this.textureId + 1 : (isActivated2(var1)) && (!isActivated(var1)) ? this.textureId + 2 : (isActivated(var1)) && (isActivated2(var1)) ? this.textureId + 3 : this.textureId;
  }
  
  public void doGale(ItemStack var1, World var2, EntityHuman var3)
  {
    var2.makeSound(var3, "gust", 0.6F, 1.0F);
    var2.addEntity(new EntityWindEssence(var2, var3));
  }
  
  public void doInterdiction(ItemStack var1, World var2, EntityHuman var3)
  {
    List var4 = var2.a(EntityMonster.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var6 = var4.iterator();
    
    while (var6.hasNext())
    {
      Entity var5 = (Entity)var6.next();
      PushEntities(var5, var3);
    }
    
    List var11 = var2.a(EntityArrow.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var8 = var11.iterator();
    
    while (var8.hasNext())
    {
      Entity var7 = (Entity)var8.next();
      PushEntities(var7, var3);
    }
    
    List var12 = var2.a(EntityFireball.class, AxisAlignedBB.b((float)var3.locX - 5.0F, var3.locY - 5.0D, (float)var3.locZ - 5.0F, (float)var3.locX + 5.0F, var3.locY + 5.0D, (float)var3.locZ + 5.0F));
    Iterator var10 = var12.iterator();
    
    while (var10.hasNext())
    {
      Entity var9 = (Entity)var10.next();
      PushEntities(var9, var3);
    }
  }
  
  private void PushEntities(Entity var1, EntityHuman var2)
  {
    if (!(var1 instanceof EntityHuman))
    {
      double var4 = var2.locX + 0.5D - var1.locX;
      double var6 = var2.locY + 0.5D - var1.locY;
      double var8 = var2.locZ + 0.5D - var1.locZ;
      double var10 = var4 * var4 + var6 * var6 + var8 * var8;
      var10 *= var10;
      
      if (var10 <= Math.pow(6.0D, 4.0D))
      {
        double var12 = -(var4 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        double var14 = -(var6 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        double var16 = -(var8 * 0.019999999552965164D / var10) * Math.pow(6.0D, 3.0D);
        
        if (var12 > 0.0D)
        {
          var12 = 0.12000000000000001D;
        }
        else if (var12 < 0.0D)
        {
          var12 = -0.12000000000000001D;
        }
        
        if (var14 > 0.2D)
        {
          var14 = 0.12000000000000001D;
        }
        else if (var14 < -0.1D)
        {
          var14 = 0.12000000000000001D;
        }
        
        if (var16 > 0.0D)
        {
          var16 = 0.12000000000000001D;
        }
        else if (var16 < 0.0D)
        {
          var16 = -0.12000000000000001D;
        }
        
        var1.motX += var12;
        var1.motY += var14;
        var1.motZ += var16;
      }
    }
  }
  
  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.ConsumeReagentForDuration(var1, var2, var3);
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (var3.fallDistance > 0.0F)
    {
      var3.fallDistance = 0.0F;
    }
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    if (isActivated2(var1.getData()))
    {
      doInterdiction(var1, var2, var3);
    }
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doGale(var1, var2, var3);
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doGale(var1, var2, var3);
    return var1;
  }
  

  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    doToggle2(var1, var2, var3);
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3)
  {
    super.doToggle(var1, var2, var3);
    
    if ((!isActivated(var1)) && (!EEBase.isPlayerInWater(var3)) && (!EEBase.isPlayerInLava(var3)))
    {
      var3.abilities.isFlying = false;
      var3.updateAbilities();
    }
  }
  
  public boolean canActivate()
  {
    return true;
  }
  
  public boolean canActivate2()
  {
    return true;
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
}
