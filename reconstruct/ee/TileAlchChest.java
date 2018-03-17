package ee;

import buildcraft.api.ISpecialInventory;
import buildcraft.api.Orientations;
import ee.core.GuiIds;
import ee.item.ItemLootBall;
import forge.ISidedInventory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.World;
import net.minecraft.server.mod_EE;

public class TileAlchChest extends TileEE implements ISpecialInventory, ISidedInventory
{
  private ItemStack[] items = new ItemStack[113];
  private int repairTimer = 0;
  private int eternalDensity;
  private boolean repairOn;
  private boolean condenseOn;
  private boolean interdictionOn;
  public boolean timeWarp;
  public float lidAngle;
  public float prevLidAngle;
  public int numUsingPlayers;
  private int ticksSinceSync;
  private boolean initialized;
  private boolean attractionOn;
  
  public boolean addItem(ItemStack var1, boolean var2, Orientations var3)
  {
    switch (var3)
    {

    case Unknown: 
    case XNeg: 
    case XPos: 
    case YNeg: 
    case YPos: 
    case ZNeg: 
    case ZPos: 
      if (var1 != null)
      {
        for (int var4 = 0; var4 < this.items.length; var4++)
        {
          if (this.items[var4] == null)
          {
            if (var2)
            {
              for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1) {}
            }
            



            return true;
          }
          
          if ((this.items[var4].doMaterialsMatch(var1)) && (this.items[var4].count < this.items[var4].getMaxStackSize()))
          {
            if (var2)
            {
              while ((this.items[var4].count < this.items[var4].getMaxStackSize()) && (var1.count > 0))
              {
                this.items[var4].count += 1;
                var1.count -= 1;
              }
              
              if (var1.count != 0) {}

            }
            else
            {

              return true;
            } }
        } }
      break;
    }
    
    return false;
  }
  

  public ItemStack extractItem(boolean var1, Orientations var2)
  {
    switch (var2)
    {

    case Unknown: 
    case XNeg: 
    case XPos: 
    case YNeg: 
    case YPos: 
    case ZNeg: 
    case ZPos: 
      for (int var3 = 0; var3 < this.items.length; var3++)
      {
        if (this.items[var3] != null)
        {
          ItemStack var4 = this.items[var3].cloneItemStack();
          var4.count = 1;
          
          if (var1)
          {
            this.items[var3].count -= 1;
            
            if (this.items[var3].count < 1)
            {
              this.items[var3] = null;
            }
          }
          
          return var4;
        }
      }
    }
    
    return null;
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
    return "Chest";
  }
  



  public void a(NBTTagCompound var1)
  {
    super.a(var1);
    NBTTagList var2 = var1.getList("Items");
    this.items = new ItemStack[getSize()];
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
      int var5 = var4.getByte("Slot") & 0xFF;
      
      if ((var5 >= 0) && (var5 < this.items.length))
      {
        this.items[var5] = ItemStack.a(var4);
      }
    }
    
    this.condenseOn = var1.getBoolean("condenseOn");
    this.repairOn = var1.getBoolean("repairOn");
    this.eternalDensity = var1.getShort("eternalDensity");
    this.timeWarp = var1.getBoolean("timeWarp");
    this.interdictionOn = var1.getBoolean("interdictionOn");
  }
  



  public void b(NBTTagCompound var1)
  {
    super.b(var1);
    var1.setBoolean("timeWarp", this.timeWarp);
    var1.setBoolean("condenseOn", this.condenseOn);
    var1.setBoolean("repairOn", this.repairOn);
    var1.setShort("eternalDensity", (short)this.eternalDensity);
    var1.setBoolean("interdictionOn", this.interdictionOn);
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
  




  public int getMaxStackSize()
  {
    return 64;
  }
  



  public void update()
  {
    super.update();
    
    if ((this.world != null) && (!net.minecraft.server.EEProxy.isClient(this.world)))
    {
      boolean var1 = false;
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      
      for (int var6 = 0; var6 < getSize(); var6++)
      {
        if (this.items[var6] != null)
        {
          if (this.items[var6].getItem().id == EEItem.watchOfTime.id)
          {
            var4 = true;
          }
          
          if (this.items[var6].getItem().id == EEItem.repairCharm.id)
          {
            var1 = true;
          }
          
          if ((this.items[var6].getItem() instanceof ItemVoidRing))
          {
            this.eternalDensity = var6;
            
            if ((this.items[var6].getData() & 0x1) == 0)
            {
              this.items[var6].setData(this.items[var6].getData() + 1);
              ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
            }
            
            var2 = true;
            var5 = true;
          }
          
          if (this.items[var6].getItem().id == EEItem.eternalDensity.id)
          {
            this.eternalDensity = var6;
            
            if ((this.items[var6].getData() & 0x1) == 0)
            {
              this.items[var6].setData(this.items[var6].getData() + 1);
              ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
            }
            
            var2 = true;
          }
          
          if ((this.items[var6].getItem() instanceof ItemAttractionRing))
          {
            if ((this.items[var6].getData() & 0x1) == 0)
            {
              this.items[var6].setData(this.items[var6].getData() + 1);
              ((ItemEECharged)this.items[var6].getItem()).setBoolean(this.items[var6], "active", true);
            }
            
            var5 = true;
          }
          
          if ((this.items[var6].getItem().id == EEBlock.eeTorch.id) && (this.items[var6].getData() == 0))
          {
            var3 = true;
          }
        }
      }
      
      if (var4 != this.timeWarp)
      {
        this.timeWarp = var4;
      }
      
      if (var1 != this.repairOn)
      {
        this.repairOn = var1;
      }
      
      if (var5 != this.attractionOn)
      {
        this.attractionOn = var5;
      }
      
      if (var2 != this.condenseOn)
      {
        this.condenseOn = var2;
      }
      else if (!var2)
      {
        this.eternalDensity = -1;
      }
      
      if (var3 != this.interdictionOn)
      {
        this.world.notify(this.x, this.y, this.z);
        this.interdictionOn = var3;
      }
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
  }
  
  public void doCondense(ItemStack var1)
  {
    if (this.eternalDensity != -1)
    {
      int var2 = 0;
      

      for (int var3 = 0; var3 < this.items.length; var3++)
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
    

    for (int var4 = 0; var4 < this.items.length; var4++)
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
  




  public void q_()
  {
    if (++this.ticksSinceSync % 20 * 4 == 0)
    {
      this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
    }
    
    this.prevLidAngle = this.lidAngle;
    float var1 = 0.1F;
    

    if ((this.numUsingPlayers > 0) && (this.lidAngle == 0.0F))
    {
      double var4 = this.x + 0.5D;
      double var2 = this.z + 0.5D;
      this.world.makeSound(var4, this.y + 0.5D, var2, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
    
    if (((this.numUsingPlayers == 0) && (this.lidAngle > 0.0F)) || ((this.numUsingPlayers > 0) && (this.lidAngle < 1.0F)))
    {
      float var8 = this.lidAngle;
      
      if (this.numUsingPlayers > 0)
      {
        this.lidAngle += var1;
      }
      else
      {
        this.lidAngle -= var1;
      }
      
      if (this.lidAngle > 1.0F)
      {
        this.lidAngle = 1.0F;
      }
      
      float var5 = 0.5F;
      
      if ((this.lidAngle < var5) && (var8 >= var5))
      {
        double var2 = this.x + 0.5D;
        double var6 = this.z + 0.5D;
        this.world.makeSound(var2, this.y + 0.5D, var6, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
      }
      
      if (this.lidAngle < 0.0F)
      {
        this.lidAngle = 0.0F;
      }
    }
    
    if (this.repairOn)
    {
      doRepair();
    }
    
    if (this.attractionOn)
    {
      doAttraction();
    }
    
    if ((this.condenseOn) && (this.eternalDensity >= 0))
    {
      doCondense(this.items[this.eternalDensity]);
    }
  }
  
  private void doAttraction()
  {
    List var1 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
    Iterator var3 = var1.iterator();
    
    while (var3.hasNext())
    {
      Entity var2 = (Entity)var3.next();
      PullItems(var2);
    }
    
    List var12 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
    Iterator var5 = var12.iterator();
    
    while (var5.hasNext())
    {
      Entity var4 = (Entity)var5.next();
      PullItems(var4);
    }
    
    List var13 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 10, this.y - 10, this.z - 10, this.x + 10, this.y + 10, this.z + 10));
    Iterator var7 = var13.iterator();
    
    while (var7.hasNext())
    {
      Entity var6 = (Entity)var7.next();
      PullItems(var6);
    }
    
    List var14 = this.world.a(EntityLootBall.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
    Iterator var9 = var14.iterator();
    
    while (var9.hasNext())
    {
      Entity var8 = (Entity)var9.next();
      GrabItems(var8);
    }
    
    List var15 = this.world.a(EntityItem.class, AxisAlignedBB.b(this.x - 0.5D, this.y - 0.5D, this.z - 0.5D, this.x + 1.25D, this.y + 1.25D, this.z + 1.25D));
    Iterator var11 = var15.iterator();
    
    while (var11.hasNext())
    {
      Entity var10 = (Entity)var11.next();
      GrabItems(var10);
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
  

  public boolean PushStack(ItemStack var1)
  {
    if (var1 == null)
    {
      return false;
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
  
  private void PullItems(Entity var1)
  {
    if (((var1 instanceof EntityItem)) || ((var1 instanceof EntityLootBall)))
    {
      if ((var1 instanceof EntityLootBall))
      {
        ((EntityLootBall)var1).setBeingPulled(true);
      }
      
      double var3 = this.x + 0.5D - var1.locX;
      double var5 = this.y + 0.5D - var1.locY;
      double var7 = this.z + 0.5D - var1.locZ;
      double var9 = var3 * var3 + var5 * var5 + var7 * var7;
      var9 *= var9;
      
      if (var9 <= Math.pow(6.0D, 4.0D))
      {
        double var11 = var3 * 0.019999999552965164D / var9 * Math.pow(6.0D, 3.0D);
        double var13 = var5 * 0.019999999552965164D / var9 * Math.pow(6.0D, 3.0D);
        double var15 = var7 * 0.019999999552965164D / var9 * Math.pow(6.0D, 3.0D);
        
        if (var11 > 0.1D)
        {
          var11 = 0.1D;
        }
        else if (var11 < -0.1D)
        {
          var11 = -0.1D;
        }
        
        if (var13 > 0.1D)
        {
          var13 = 0.1D;
        }
        else if (var13 < -0.1D)
        {
          var13 = -0.1D;
        }
        
        if (var15 > 0.1D)
        {
          var15 = 0.1D;
        }
        else if (var15 < -0.1D)
        {
          var15 = -0.1D;
        }
        
        var1.motX += var11 * 1.2D;
        var1.motY += var13 * 1.2D;
        var1.motZ += var15 * 1.2D;
      }
    }
  }
  
  public boolean isInterdicting()
  {
    return this.interdictionOn;
  }
  
  private void PushEntities(Entity var1, int var2, int var3, int var4)
  {
    if ((!(var1 instanceof EntityHuman)) && (!(var1 instanceof EntityItem)))
    {
      double var6 = var2 - var1.locX;
      double var8 = var3 - var1.locY;
      double var10 = var4 - var1.locZ;
      double var12 = var6 * var6 + var8 * var8 + var10 * var10;
      var12 *= var12;
      
      if (var12 <= Math.pow(6.0D, 4.0D))
      {
        double var14 = -(var6 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);
        double var16 = -(var8 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);
        double var18 = -(var10 * 0.019999999552965164D / var12) * Math.pow(6.0D, 3.0D);
        
        if (var14 > 0.0D)
        {
          var14 = 0.22D;
        }
        else if (var14 < 0.0D)
        {
          var14 = -0.22D;
        }
        
        if (var16 > 0.2D)
        {
          var16 = 0.12000000000000001D;
        }
        else if (var16 < -0.1D)
        {
          var16 = 0.12000000000000001D;
        }
        
        if (var18 > 0.0D)
        {
          var18 = 0.22D;
        }
        else if (var18 < 0.0D)
        {
          var18 = -0.22D;
        }
        
        var1.motX += var14;
        var1.motY += var16;
        var1.motZ += var18;
      }
    }
  }
  
  public void b(int var1, int var2)
  {
    if (var1 == 1)
    {
      this.numUsingPlayers = var2;
    }
  }
  
  public void f()
  {
    this.numUsingPlayers += 1;
    this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
  }
  
  public void g()
  {
    this.numUsingPlayers -= 1;
    this.world.playNote(this.x, this.y, this.z, 1, this.numUsingPlayers);
  }
  



  public boolean a(EntityHuman var1)
  {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
  
  public int getStartInventorySide(int var1)
  {
    return 0;
  }
  
  public int getSizeInventorySide(int var1)
  {
    return getSize();
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    if (!this.world.isStatic)
    {
      var1.openGui(mod_EE.getInstance(), GuiIds.ALCH_CHEST, this.world, this.x, this.y, this.z);
    }
    
    return true;
  }
  
  public void onBlockRemoval()
  {
    for (int var1 = 0; var1 < getSize(); var1++)
    {
      ItemStack var2 = getItem(var1);
      
      if (var2 != null)
      {
        float var3 = this.world.random.nextFloat() * 0.8F + 0.1F;
        float var4 = this.world.random.nextFloat() * 0.8F + 0.1F;
        float var5 = this.world.random.nextFloat() * 0.8F + 0.1F;
        
        while (var2.count > 0)
        {
          int var6 = this.world.random.nextInt(21) + 10;
          
          if (var6 > var2.count)
          {
            var6 = var2.count;
          }
          
          var2.count -= var6;
          EntityItem var7 = new EntityItem(this.world, this.x + var3, this.y + var4, this.z + var5, new ItemStack(var2.id, var6, var2.getData()));
          
          if (var7 != null)
          {
            float var8 = 0.05F;
            var7.motX = ((float)this.world.random.nextGaussian() * var8);
            var7.motY = ((float)this.world.random.nextGaussian() * var8 + 0.2F);
            var7.motZ = ((float)this.world.random.nextGaussian() * var8);
            
            if ((var7.itemStack.getItem() instanceof ItemKleinStar))
            {
              ((ItemKleinStar)var7.itemStack.getItem()).setKleinPoints(var7.itemStack, ((ItemKleinStar)var2.getItem()).getKleinPoints(var2));
            }
            
            this.world.addEntity(var7);
          }
        }
      }
    }
  }
  
  public int getTextureForSide(int var1)
  {
    if ((var1 != 1) && (var1 != 0))
    {
      byte var2 = this.direction;
      return var1 != var2 ? EEBase.alchChestSide : EEBase.alchChestFront;
    }
    

    return EEBase.alchChestTop;
  }
  

  public int getInventoryTexture(int var1)
  {
    return var1 == 3 ? EEBase.alchChestFront : var1 == 1 ? EEBase.alchChestTop : EEBase.alchChestSide;
  }
  
  public int getLightValue()
  {
    return isInterdicting() ? 15 : 0;
  }
  


  public void onNeighborBlockChange(int var1) {}
  


  public ItemStack splitWithoutUpdate(int var1)
  {
    return null;
  }
  
  public ItemStack[] getContents()
  {
    return this.items;
  }
  
  public void setMaxStackSize(int size) {}
}
