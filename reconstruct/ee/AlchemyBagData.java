package ee;

import ee.item.ItemLootBall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityExperienceOrb;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.World;
import net.minecraft.server.WorldMapBase;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;


public class AlchemyBagData
  extends WorldMapBase
  implements IInventory
{
  public boolean voidOn;
  public boolean repairOn;
  public boolean markForUpdate;
  public boolean condenseOn;
  public int repairTimer = 0;
  public int condenseCheckTimer = 0;
  public static final String prefix = "bag";
  public static final String prefix_ = "bag_";
  public ItemStack[] items = new ItemStack[113];
  private int eternalDensity;
  private boolean initialized;
  public static List datas = new LinkedList();
  
  public AlchemyBagData(String var1)
  {
    super(var1);
    datas.add(this);
  }
  
  public void onUpdate(World var1, EntityHuman var2)
  {
    if (!this.initialized)
    {
      this.initialized = true;
      update();
    }
    
    if (this.repairOn)
    {
      doRepair();
    }
    
    if (this.condenseOn)
    {
      doCondense(this.items[this.eternalDensity]);
    }
    
    if (this.voidOn)
    {
      boolean var3 = false;
      
      for (int var4 = 0; var4 <= 15; var4++)
      {
        boolean var5 = true;
        ItemStack[] var6 = var2.inventory.items;
        int var7 = var6.length;
        
        for (int var8 = 0; var8 < var7; var8++)
        {
          ItemStack var9 = var6[var8];
          
          if ((var9 != null) && (var9.doMaterialsMatch(new ItemStack(EEItem.alchemyBag, 1, var4))))
          {
            var5 = false;
          }
        }
        
        if (!var5)
        {
          String var10 = "bag_" + var2.name + var4;
          AlchemyBagData var11 = (AlchemyBagData)var1.a(AlchemyBagData.class, var10);
          
          if (var11 != null)
          {
            if (var3) {
              break;
            }

            if (var11.voidOn)
            {
              var3 = true;
            }
            
            if ((var11 == this) && (var3))
            {
              doAttraction(var2);
              break;
            }
          }
        }
      }
    }
    
    if (this.markForUpdate)
    {
      a();
    }
  }

  public int getSize()
  {
    return 104;
  }

  public ItemStack getItem(int var1)
  {
    return this.items[var1];
  }

  public ItemStack splitStack(int var1, int var2)
  {
    if (this.items[var1] != null)
    {

      if (this.items[var1].count <= var2)
      {
        ItemStack var3 = this.items[var1];
        this.items[var1] = null;
        update();
        return var3;
      }

      ItemStack var3 = this.items[var1].a(var2);
      
      if (this.items[var1].count == 0)
      {
        this.items[var1] = null;
      }
      
      update();
      return var3;
    }
    return null;
  }

  public void setItem(int var1, ItemStack var2)
  {
    this.items[var1] = var2;
    
    if ((var2 != null) && (var2.count > getMaxStackSize()))
    {
      var2.count = getMaxStackSize();
    }
    
    update();
  }

  public String getName()
  {
    return "Bag";
  }

  public int getMaxStackSize()
  {
    return 64;
  }

  public void update()
  {
    this.markForUpdate = true;
    boolean var1 = false;
    boolean var2 = false;
    boolean var3 = false;
    
    for (int var4 = 0; var4 < this.items.length; var4++)
    {
      if (this.items[var4] != null)
      {
        if (this.items[var4].getItem() == EEItem.repairCharm)
        {
          var1 = true;
        }
        
        if (this.items[var4].getItem() == EEItem.voidRing)
        {
          this.eternalDensity = var4;
          
          if ((this.items[var4].getData() & 0x1) == 0)
          {
            this.items[var4].setData(this.items[var4].getData() + 1);
            ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
          }
          
          var3 = true;
          var2 = true;
        }
        
        if (this.items[var4].getItem() == EEItem.eternalDensity)
        {
          this.eternalDensity = var4;
          
          if ((this.items[var4].getData() & 0x1) == 0)
          {
            this.items[var4].setData(this.items[var4].getData() + 1);
            ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
          }
          
          var2 = true;
        }
        
        if (this.items[var4].getItem() == EEItem.attractionRing)
        {
          var3 = true;
          
          if ((this.items[var4].getData() & 0x1) == 0)
          {
            this.items[var4].setData(this.items[var4].getData() + 1);
            ((ItemEECharged)this.items[var4].getItem()).setBoolean(this.items[var4], "active", true);
          }
        }
      }
    }
    
    if (var1 != this.repairOn)
    {
      this.repairOn = var1;
    }
    
    if (var2 != this.condenseOn)
    {
      this.condenseOn = var2;
    }
    
    if (var3 != this.voidOn)
    {
      this.voidOn = var3;
    }
  }
  
  public void doRepair()
  {
    if (this.repairTimer >= 20)
    {
      ItemStack var1 = null;
      boolean var2 = false;
      
      for (int var3 = 0; var3 < getSize(); var3++)
      {
        var2 = false;
        var1 = this.items[var3];
        
        if (var1 != null)
        {
          for (int var4 = 0; var4 < EEMaps.chargedItems.size(); var4++)
          {
            if (((Integer)EEMaps.chargedItems.get(Integer.valueOf(var4))).intValue() == var1.id)
            {
              var2 = true;
              break;
            }
          }
          
          if ((!var2) && (var1.getData() >= 1) && (var1.d()))
          {
            var1.setData(var1.getData() - 1);
          }
        }
      }
      
      this.repairTimer = 0;
    }
    
    this.repairTimer += 1;
    this.markForUpdate = true;
  }
  
  public void doCondense(ItemStack var1)
  {
    if (this.eternalDensity != -1)
    {
      int var2 = 0;
      int var3;

      for (var3 = 0; var3 < this.items.length; var3++)
      {
        if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) > var2))
        {
          var2 = EEMaps.getEMC(this.items[var3]);
        }
      }
      
      for (var3 = 0; var3 < this.items.length; var3++)
      {
        if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
        {
          var2 = EEMaps.getEMC(this.items[var3]);
        }
      }
      
      if ((var2 >= EEMaps.getEMC(EEItem.redMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.redMatter.id))) || (var2 >= EEMaps.getEMC(EEItem.darkMatter.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(EEItem.darkMatter.id))) || (var2 >= EEMaps.getEMC(Item.DIAMOND.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.DIAMOND.id))) || (var2 >= EEMaps.getEMC(Item.GOLD_INGOT.id)) || (AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.GOLD_INGOT.id))) || (var2 >= EEMaps.getEMC(Item.IRON_INGOT.id)) || (!AnalyzeTier(this.items[this.eternalDensity], EEMaps.getEMC(Item.IRON_INGOT.id)))) {}
    }
  }
  



  private boolean AnalyzeTier(ItemStack var1, int var2)
  {
    if (var1 == null)
    {
      return false;
    }
    

    int var3 = 0;
    int var4;

    for (var4 = 0; var4 < this.items.length; var4++)
    {
      if ((this.items[var4] != null) && (isValidMaterial(this.items[var4])) && (EEMaps.getEMC(this.items[var4]) < var2))
      {
        var3 += EEMaps.getEMC(this.items[var4]) * this.items[var4].count;
      }
    }
    
    if (var3 + emc(var1) < var2)
    {
      return false;
    }
    

    var4 = 0;
    
    while ((var3 + emc(var1) >= var2) && (var4 < 10))
    {
      var4++;
      ConsumeMaterialBelowTier(var1, var2);
    }
    
    if ((emc(var1) >= var2) && (roomFor(getProduct(var2))))
    {
      PushStack(getProduct(var2));
      takeEMC(var1, var2);
    }
    
    return true;
  }
  


  private boolean roomFor(ItemStack var1)
  {
    if (var1 == null)
    {
      return false;
    }
    

    for (int var2 = 0; var2 < this.items.length; var2++)
    {
      if (this.items[var2] == null)
      {
        return true;
      }
      
      if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
      {
        return true;
      }
    }
    
    return false;
  }
  

  private ItemStack getProduct(int var1)
  {
    return var1 == EEMaps.getEMC(EEItem.redMatter.id) ? new ItemStack(EEItem.redMatter, 1) : var1 == EEMaps.getEMC(EEItem.darkMatter.id) ? new ItemStack(EEItem.darkMatter, 1) : var1 == EEMaps.getEMC(Item.DIAMOND.id) ? new ItemStack(Item.DIAMOND, 1) : var1 == EEMaps.getEMC(Item.GOLD_INGOT.id) ? new ItemStack(Item.GOLD_INGOT, 1) : var1 == EEMaps.getEMC(Item.IRON_INGOT.id) ? new ItemStack(Item.IRON_INGOT, 1) : null;
  }
  
  public boolean PushStack(ItemStack var1)
  {
    if (var1 == null)
    {
      return true;
    }
    

    for (int var2 = 0; var2 < this.items.length; var2++)
    {
      if (this.items[var2] == null)
      {
        this.items[var2] = var1.cloneItemStack();
        var1 = null;
        return true;
      }
      
      if ((this.items[var2].doMaterialsMatch(var1)) && (this.items[var2].count <= var1.getMaxStackSize() - var1.count))
      {
        this.items[var2].count += var1.count;
        var1 = null;
        return true;
      }
    }
    
    return false;
  }
  

  private void ConsumeMaterialBelowTier(ItemStack var1, int var2)
  {
    for (int var3 = 0; var3 < this.items.length; var3++)
    {
      if ((this.items[var3] != null) && (isValidMaterial(this.items[var3])) && (EEMaps.getEMC(this.items[var3]) < var2))
      {
        addEMC(var1, EEMaps.getEMC(this.items[var3]));
        this.items[var3].count -= 1;
        
        if (this.items[var3].count == 0)
        {
          this.items[var3] = null;
        }
        
        return;
      }
    }
  }
  
  private boolean isValidMaterial(ItemStack var1)
  {
    if (var1 == null)
    {
      return false;
    }
    if (EEMaps.getEMC(var1) == 0)
    {
      return false;
    }
    if ((var1.getItem() instanceof ItemKleinStar))
    {
      return false;
    }
    

    int var2 = var1.id;
    return var2 != EEItem.redMatter.id;
  }
  

  private int emc(ItemStack var1)
  {
    return (var1.getItem() instanceof ItemEternalDensity) ? ((ItemEternalDensity)var1.getItem()).getInteger(var1, "emc") : (!(var1.getItem() instanceof ItemEternalDensity)) && (!(var1.getItem() instanceof ItemVoidRing)) ? 0 : ((ItemVoidRing)var1.getItem()).getInteger(var1, "emc");
  }
  
  private void takeEMC(ItemStack var1, int var2)
  {
    if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
    {
      if ((var1.getItem() instanceof ItemEternalDensity))
      {
        ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
      }
      else
      {
        ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) - var2);
      }
    }
  }
  
  private void addEMC(ItemStack var1, int var2)
  {
    if (((var1.getItem() instanceof ItemEternalDensity)) || ((var1.getItem() instanceof ItemVoidRing)))
    {
      if ((var1.getItem() instanceof ItemEternalDensity))
      {
        ((ItemEternalDensity)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
      }
      else
      {
        ((ItemVoidRing)var1.getItem()).setInteger(var1, "emc", emc(var1) + var2);
      }
    }
  }
  
  public void doAttraction(EntityHuman var1)
  {
    List var2 = var1.world.a(EntityItem.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
    Iterator var4 = var2.iterator();
    
    while (var4.hasNext())
    {
      Entity var3 = (Entity)var4.next();
      PullItems(var3, var1);
    }
    
    List var14 = var1.world.a(EntityItem.class, AxisAlignedBB.b(EEBase.playerX(var1) - 0.55D, EEBase.playerY(var1) - 0.55D, EEBase.playerZ(var1) - 0.55D, EEBase.playerX(var1) + 0.55D, EEBase.playerY(var1) + 0.55D, EEBase.playerZ(var1) + 0.55D));
    Iterator var6 = var14.iterator();
    
    while (var6.hasNext())
    {
      Entity var5 = (Entity)var6.next();
      GrabItems(var5);
    }
    
    List var15 = var1.world.a(EntityLootBall.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
    Iterator var8 = var15.iterator();
    
    while (var8.hasNext())
    {
      Entity var7 = (Entity)var8.next();
      PullItems(var7, var1);
    }
    
    List var16 = var1.world.a(EntityLootBall.class, AxisAlignedBB.b(EEBase.playerX(var1) - 0.55D, EEBase.playerY(var1) - 0.55D, EEBase.playerZ(var1) - 0.55D, EEBase.playerX(var1) + 0.55D, EEBase.playerY(var1) + 0.55D, EEBase.playerZ(var1) + 0.55D));
    Iterator var10 = var16.iterator();
    
    while (var10.hasNext())
    {
      Entity var9 = (Entity)var10.next();
      GrabItems(var9);
    }
    
    List var13 = var1.world.a(EntityExperienceOrb.class, AxisAlignedBB.b(EEBase.playerX(var1) - 10.0D, EEBase.playerY(var1) - 10.0D, EEBase.playerZ(var1) - 10.0D, EEBase.playerX(var1) + 10.0D, EEBase.playerY(var1) + 10.0D, EEBase.playerZ(var1) + 10.0D));
    Iterator var12 = var13.iterator();
    
    while (var12.hasNext())
    {
      Entity var11 = (Entity)var12.next();
      PullItems(var11, var1);
    }
  }
  
  private void PullItems(Entity var1, EntityHuman var2)
  {
    if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
    {
      if ((var1 instanceof EntityLootBall))
      {
        ((EntityLootBall)var1).setBeingPulled(true);
      }
      
      double var4 = EEBase.playerX(var2) + 0.5D - var1.locX;
      double var6 = EEBase.playerY(var2) + 0.5D - var1.locY;
      double var8 = EEBase.playerZ(var2) + 0.5D - var1.locZ;
      double var10 = var4 * var4 + var6 * var6 + var8 * var8;
      var10 *= var10;
      
      if (var10 <= Math.pow(6.0D, 4.0D))
      {
        double var12 = var4 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var14 = var6 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        double var16 = var8 * 0.019999999552965164D / var10 * Math.pow(6.0D, 3.0D);
        
        if (var12 > 0.1D)
        {
          var12 = 0.1D;
        }
        else if (var12 < -0.1D)
        {
          var12 = -0.1D;
        }
        
        if (var14 > 0.1D)
        {
          var14 = 0.1D;
        }
        else if (var14 < -0.1D)
        {
          var14 = -0.1D;
        }
        
        if (var16 > 0.1D)
        {
          var16 = 0.1D;
        }
        else if (var16 < -0.1D)
        {
          var16 = -0.1D;
        }
        
        var1.motX += var12 * 1.2D;
        var1.motY += var14 * 1.2D;
        var1.motZ += var16 * 1.2D;
      }
    }
  }
  
  private void GrabItems(Entity var1)
  {
    if ((var1 != null) && ((var1 instanceof EntityItem)))
    {
      ItemStack var9 = ((EntityItem)var1).itemStack;
      
      if (var9 == null)
      {
        var1.die();
        return;
      }
      
      if ((var9.getItem() instanceof ItemLootBall))
      {
        ItemLootBall var3 = (ItemLootBall)var9.getItem();
        ItemStack[] var4 = var3.getDroplist(var9);
        ItemStack[] var5 = var4;
        int var6 = var4.length;
        
        for (int var7 = 0; var7 < var6; var7++)
        {
          ItemStack var8 = var5[var7];
          PushStack(var8);
        }
        
        var1.die();
      }
      else
      {
        PushStack(var9);
        var1.die();
      }
    }
    else if ((var1 != null) && ((var1 instanceof EntityLootBall)))
    {
      if (((EntityLootBall)var1).items == null)
      {
        var1.die();
      }
      
      ItemStack[] var2 = ((EntityLootBall)var1).items;
      PushDenseStacks((EntityLootBall)var1);
      
      if (((EntityLootBall)var1).isEmpty())
      {
        var1.die();
      }
    }
  }
  
  private void PushDenseStacks(EntityLootBall var1)
  {
    for (int var2 = 0; var2 < var1.items.length; var2++)
    {
      if ((var1.items[var2] != null) && (PushStack(var1.items[var2])))
      {
        var1.items[var2] = null;
      }
    }
  }
  
  public boolean PushStack(EntityItem var1)
  {
    if (var1 == null)
    {
      return false;
    }
    if (var1.itemStack == null)
    {
      var1.die();
      return false;
    }
    if (var1.itemStack.count < 1)
    {
      var1.die();
      return false;
    }
    

    for (int var2 = 0; var2 < this.items.length; var2++)
    {
      if (this.items[var2] == null)
      {
        this.items[var2] = var1.itemStack.cloneItemStack();
        
        for (this.items[var2].count = 0; (var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()); var1.itemStack.count -= 1)
        {
          this.items[var2].count += 1;
        }
        
        var1.die();
        return true;
      }
      
      if ((this.items[var2].doMaterialsMatch(var1.itemStack)) && (this.items[var2].count <= var1.itemStack.getMaxStackSize() - var1.itemStack.count))
      {
        while ((var1.itemStack.count > 0) && (this.items[var2].count < this.items[var2].getMaxStackSize()))
        {
          this.items[var2].count += 1;
          var1.itemStack.count -= 1;
        }
        
        var1.die();
        return true;
      }
    }
    
    return false;
  }
  

  private void PushDenseStacks(EntityLootBall var1, EntityHuman var2)
  {
    for (int var3 = 0; var3 < var1.items.length; var3++)
    {
      if (var1.items[var3] != null)
      {
        PushStack(var1.items[var3], var2);
        var1.items[var3] = null;
      }
    }
  }
  


  public void PushStack(ItemStack var1, EntityHuman var2)
  {
    for (int var3 = 0; var3 < getSize(); var3++)
    {
      if (var1 != null)
      {
        if (this.items[var3] == null)
        {
          this.items[var3] = var1.cloneItemStack();
          var1 = null;
          this.markForUpdate = true;
          return;
        }
        
        if (this.items[var3].doMaterialsMatch(var1))
        {
          do
          {
            this.items[var3].count += 1;
            var1.count -= 1;
            
            if (var1.count == 0)
            {
              var1 = null;
              this.markForUpdate = true; return;
            }
            if (this.items[var3].count >= this.items[var3].getMaxStackSize()) break; } while (var1 != null);
        }
        else if (var3 == this.items.length - 1)
        {
          EntityItem var4 = new EntityItem(var2.world, EEBase.playerX(var2), EEBase.playerY(var2), EEBase.playerZ(var2), var1);
          var4.pickupDelay = 1;
          var2.world.addEntity(var4);
          this.markForUpdate = true;
          return;
        }
      }
    }
    
    if (var1 != null)
    {
      for (int var3 = 0; var3 < this.items.length; var3++)
      {
        if (this.items[var3] == null)
        {
          this.items[var3] = var1.cloneItemStack();
          var1 = null;
          this.markForUpdate = true;
          return;
        }
      }
    }
  }
  



  public boolean a(EntityHuman var1)
  {
    return true;
  }
  

  public void f() {}
  

  public void g() {}
  

  public void a(NBTTagCompound var1)
  {
    this.voidOn = var1.getBoolean("voidOn");
    this.repairOn = var1.getBoolean("repairOn");
    this.condenseOn = var1.getBoolean("condenseOn");
    this.eternalDensity = var1.getShort("eternalDensity");
    NBTTagList var2 = var1.getList("Items");
    this.items = new ItemStack[113];
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
      int var5 = var4.getByte("Slot") & 0xFF;
      
      if ((var5 >= 0) && (var5 < this.items.length))
      {
        this.items[var5] = ItemStack.a(var4);
      }
    }
  }
  



  public void b(NBTTagCompound var1)
  {
    var1.setBoolean("voidOn", this.voidOn);
    var1.setBoolean("repairOn", this.repairOn);
    var1.setBoolean("condenseOn", this.condenseOn);
    var1.setShort("eternalDensity", (short)this.eternalDensity);
    NBTTagList var2 = new NBTTagList();
    
    for (int var3 = 0; var3 < this.items.length; var3++)
    {
      if (this.items[var3] != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var3);
        this.items[var3].save(var4);
        var2.add(var4);
      }
    }
    
    var1.set("Items", var2);
  }
  




  public ItemStack splitWithoutUpdate(int var1)
  {
    return null;
  }
  
  public ItemStack[] getContents()
  {
    return this.items;
  }
  


  public void onOpen(CraftHumanEntity who) {}
  


  public void onClose(CraftHumanEntity who) {}
  


  public List<HumanEntity> getViewers()
  {
    return new ArrayList(0);
  }
  
  public InventoryHolder getOwner()
  {
    return null;
  }
  
  public void setMaxStackSize(int size) {}
}
