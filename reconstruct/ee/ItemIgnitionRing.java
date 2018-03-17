package ee;

import java.util.List;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFire;
import net.minecraft.server.BlockLeaves;
import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemIgnitionRing extends ItemEECharged
{
  public boolean itemCharging;
  
  public ItemIgnitionRing(int var1)
  {
    super(var1, 4);
  }
  
  public int getIconFromDamage(int var1)
  {
    return !isActivated(var1) ? this.textureId : this.textureId + 1;
  }
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3)
  {
    if (chargeLevel(var1) > 0)
    {
      int var4 = chargeLevel(var1);
      var2.makeSound(var3, "wall", 1.0F, 1.0F);
      int var5 = (int)EEBase.playerX(var3);
      int var6 = (int)EEBase.playerY(var3);
      int var7 = (int)EEBase.playerZ(var3);
      double var8 = net.minecraft.server.MathHelper.floor(var3.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
      
      for (int var10 = -1; var10 <= 1; var10++)
      {
        for (int var11 = -2; var11 <= 1; var11++)
        {
          for (int var12 = -var4 * 3; var12 <= var4 * 3; var12++)
          {
            if (var8 == 3.0D)
            {
              if (((var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 + var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 + var10, var6 + var11 - 1, var7 + var12) != 0))
              {
                var2.setTypeId(var5 + var10, var6 + var11, var7 + var12, Block.FIRE.id);
              }
            }
            else if (var8 == 2.0D)
            {
              if (((var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 - var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 - var10) != 0))
              {
                var2.setTypeId(var5 + var12, var6 + var11, var7 - var10, Block.FIRE.id);
              }
            }
            else if (var8 == 1.0D)
            {
              if (((var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 0) || (var2.getTypeId(var5 - var10, var6 + var11, var7 + var12) == 78)) && (var2.getTypeId(var5 - var10, var6 + var11 - 1, var7 + var12) != 0))
              {
                var2.setTypeId(var5 - var10, var6 + var11, var7 + var12, Block.FIRE.id);
              }
            }
            else if ((var8 == 0.0D) && ((var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 0) || (var2.getTypeId(var5 + var12, var6 + var11, var7 + var10) == 78)) && (var2.getTypeId(var5 + var12, var6 + var11 - 1, var7 + var10) != 0))
            {
              var2.setTypeId(var5 + var12, var6 + var11, var7 + var10, Block.FIRE.id);
            }
          }
        }
      }
    }
  }
  
  public void doBurn(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = (int)EEBase.playerX(var3);
    int var5 = (int)EEBase.playerY(var3);
    int var6 = (int)EEBase.playerZ(var3);
    List var7 = var2.a(net.minecraft.server.EntityMonster.class, net.minecraft.server.AxisAlignedBB.b(var3.locX - 5.0D, var3.locY - 5.0D, var3.locZ - 5.0D, var3.locX + 5.0D, var3.locY + 5.0D, var3.locZ + 5.0D));
    

    for (int var8 = 0; var8 < var7.size(); var8++)
    {
      if (var2.random.nextInt(30) == 0)
      {
        Entity var9 = (Entity)var7.get(var8);
        EEProxy.dealFireDamage(var9, 5);
        var9.setOnFire(60);
      }
    }
    
    for (var8 = -4; var8 <= 4; var8++)
    {
      for (int var13 = -4; var13 <= 4; var13++)
      {
        for (int var10 = -4; var10 <= 4; var10++)
        {
          if (((var8 <= -2) || (var8 >= 2) || (var13 != 0)) && ((var10 <= -2) || (var10 >= 2) || (var13 != 0)) && (var2.random.nextInt(120) == 0))
          {
            if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10) == 0) && (var2.getTypeId(var4 + var8, var5 + var13 - 1, var6 + var10) != 0))
            {
              var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
            }
            else
            {
              boolean var11 = false;
              

              for (int var12 = -1; var12 <= 1; var12++)
              {
                if ((var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8 + var12, var5 + var13, var6 + var10) == Block.LOG.id))
                {
                  var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                  var11 = true;
                  break;
                }
              }
              
              if (!var11)
              {
                for (var12 = -1; var12 <= 1; var12++)
                {
                  if ((var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13 + var12, var6 + var10) == Block.LOG.id))
                  {
                    var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                    var11 = true;
                    break;
                  }
                }
              }
              
              if (!var11)
              {
                for (var12 = -1; var12 <= 1; var12++)
                {
                  if ((var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LEAVES.id) || (var2.getTypeId(var4 + var8, var5 + var13, var6 + var10 + var12) == Block.LOG.id))
                  {
                    var2.setTypeId(var4 + var8, var5 + var13, var6 + var10, Block.FIRE.id);
                    var11 = true;
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doBreak(var1, var2, var3);
    return var1;
  }
  

  public void ConsumeReagent(ItemStack var1, EntityHuman var2, boolean var3)
  {
    EEBase.ConsumeReagentForDuration(var1, var2, var3);
  }
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3)
  {
    for (int var4 = -1; var4 <= 1; var4++)
    {
      for (int var5 = -1; var5 <= 1; var5++)
      {
        if (var2.getTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5) == Block.FIRE.id)
        {
          var2.setTypeId((int)EEBase.playerX(var3) + var4, (int)EEBase.playerY(var3) - 1, (int)EEBase.playerZ(var3) + var5, 0);
        }
      }
    }
  }
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3)
  {
    doBurn(var1, var2, var3);
  }
  
  public void doHeld(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    doBreak(var1, var2, var3);
  }
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.C_();
    var2.makeSound(var3, "wall", 1.0F, 1.0F);
    var2.addEntity(new EntityPyrokinesis(var2, var3));
  }
  
  public boolean canActivate()
  {
    return true;
  }
}
