package ee;

import buildcraft.api.ISpecialInventory;
import buildcraft.api.Orientations;
import ee.core.GuiIds;
import java.util.Random;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;
import net.minecraft.server.mod_EE;

public class TileRelay extends TileEE implements ISpecialInventory, forge.ISidedInventory, IEEPowerNet
{
  private ItemStack[] items = new ItemStack[8];
  public int scaledEnergy;
  public int accumulate;
  public int arrayCounter;
  private float woftFactor;
  private int in = 0;
  private int klein;
  private boolean isSending;
  public int burnTimeRemainingScaled;
  public int cookProgressScaled;
  public int kleinDrainingScaled;
  public int kleinChargingScaled;
  public int relayEnergyScaled;
  public int kleinDrainPoints;
  public int kleinChargePoints;
  
  public TileRelay()
  {
    this.klein = (this.items.length - 1);
    this.arrayCounter = 0;
    this.accumulate = 0;
    this.woftFactor = 1.0F;
    this.kleinDrainPoints = 0;
    this.kleinChargePoints = 0;
    this.kleinDrainingScaled = 0;
    this.kleinChargingScaled = 0;
    this.relayEnergyScaled = 0;
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
  
  private boolean isChest(TileEntity var1)
  {
    return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
  }
  
  public static boolean putInChest(TileEntity var0, ItemStack var1)
  {
    if ((var1 != null) && (var1.id != 0))
    {
      if (var0 == null)
      {
        return false;
      }
      




      if ((var0 instanceof TileEntityChest))
      {
        for (int var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
        {
          ItemStack var3 = ((TileEntityChest)var0).getItem(var2);
          
          if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()))
          {
            var3.count += var1.count;
            return true;
          }
        }
        
        for (var2 = 0; var2 < ((TileEntityChest)var0).getSize(); var2++)
        {
          if (((TileEntityChest)var0).getItem(var2) == null)
          {
            ((TileEntityChest)var0).setItem(var2, var1);
            return true;
          }
        }
      }
      else if ((var0 instanceof TileAlchChest))
      {
        for (int var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
        {
          ItemStack var3 = ((TileAlchChest)var0).getItem(var2);
          
          if ((var3 != null) && (var3.doMaterialsMatch(var1)) && (var3.count + var1.count <= var3.getMaxStackSize()) && (var3.getData() == var1.getData()))
          {
            var3.count += var1.count;
            return true;
          }
        }
        
        for (var2 = 0; var2 < ((TileAlchChest)var0).getSize(); var2++)
        {
          if (((TileAlchChest)var0).getItem(var2) == null)
          {
            ((TileAlchChest)var0).setItem(var2, var1);
            return true;
          }
        }
      }
      
      return false;
    }
    


    return true;
  }
  

  public boolean tryDropInChest(ItemStack var1)
  {
    TileEntity var2 = null;
    
    if (isChest(this.world.getTileEntity(this.x, this.y + 1, this.z)))
    {
      var2 = this.world.getTileEntity(this.x, this.y + 1, this.z);
      return putInChest(var2, var1);
    }
    if (isChest(this.world.getTileEntity(this.x, this.y - 1, this.z)))
    {
      var2 = this.world.getTileEntity(this.x, this.y - 1, this.z);
      return putInChest(var2, var1);
    }
    if (isChest(this.world.getTileEntity(this.x + 1, this.y, this.z)))
    {
      var2 = this.world.getTileEntity(this.x + 1, this.y, this.z);
      return putInChest(var2, var1);
    }
    if (isChest(this.world.getTileEntity(this.x - 1, this.y, this.z)))
    {
      var2 = this.world.getTileEntity(this.x - 1, this.y, this.z);
      return putInChest(var2, var1);
    }
    if (isChest(this.world.getTileEntity(this.x, this.y, this.z + 1)))
    {
      var2 = this.world.getTileEntity(this.x, this.y, this.z + 1);
      return putInChest(var2, var1);
    }
    if (isChest(this.world.getTileEntity(this.x, this.y, this.z - 1)))
    {
      var2 = this.world.getTileEntity(this.x, this.y, this.z - 1);
      return putInChest(var2, var1);
    }
    

    return false;
  }
  




  public int getSize()
  {
    return this.items.length;
  }
  




  public int getMaxStackSize()
  {
    return 64;
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
        return var3;
      }
      

      ItemStack var3 = this.items[var1].a(var2);
      
      if (this.items[var1].count == 0)
      {
        this.items[var1] = null;
      }
      
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
  }
  
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
        for (int var4 = 0; var4 <= this.items.length - 2; var4++)
        {
          if (this.items[var4] != null)
          {
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
              }
            }
          }
          else {
            if ((var1.getItem() instanceof ItemKleinStar))
            {


              if (!var3.equals(Orientations.YPos))
              {
                ItemKleinStar var5 = (ItemKleinStar)var1.getItem();
                
                if (var5.getKleinPoints(var1) == 0) continue; if (var4 != 0) {
                  continue;
                }
                
              }
              else
              {
                ItemKleinStar var5 = (ItemKleinStar)var1.getItem();
                
                if ((var5.getKleinPoints(var1) == var5.getMaxPoints(var1)) || (var4 != this.items.length - 1)) {
                  continue;
                }
              }
            }
            

            if (var2)
            {
              for (this.items[var4] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1) {}
            }
            



            return true;
          } }
      }
      break;
    }
    
    return false;
  }
  



  public ItemStack extractItem(boolean var1, Orientations var2)
  {
    switch (var2)
    {

    case XNeg: 
      if (this.items[(this.items.length - 1)] == null)
      {
        return null;
      }
      if ((this.items[(this.items.length - 1)].getItem() instanceof ItemKleinStar))
      {
        ItemStack var3 = this.items[(this.items.length - 1)].cloneItemStack();
        
        if (var1)
        {
          this.items[(this.items.length - 1)] = null;
        }
        
        return var3;
      }
    

    case Unknown: 
    case XPos: 
    case YNeg: 
    case YPos: 
    case ZNeg: 
    case ZPos: 
      if (this.items[0] == null)
      {
        return null;
      }
      if ((this.items[0].getItem() instanceof ItemKleinStar))
      {
        ItemStack var3 = this.items[0].cloneItemStack();
        
        if (var1)
        {
          this.items[0] = null;
        }
        
        return var3;
      }
      break; }
    
    return null;
  }
  




  public String getName()
  {
    return "AM Array";
  }
  



  public void a(NBTTagCompound var1)
  {
    super.a(var1);
    NBTTagList var2 = var1.getList("Items");
    this.items = new ItemStack[getSize()];
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
      byte var5 = var4.getByte("Slot");
      
      if ((var5 >= 0) && (var5 < this.items.length))
      {
        this.items[var5] = ItemStack.a(var4);
      }
    }
    
    this.scaledEnergy = var1.getInt("scaledEnergy");
    this.woftFactor = var1.getFloat("timeFactor");
    this.arrayCounter = var1.getShort("arrayCounter");
  }
  



  public void b(NBTTagCompound var1)
  {
    super.b(var1);
    var1.setInt("scaledEnergy", this.scaledEnergy);
    var1.setShort("arrayCounter", (short)this.arrayCounter);
    var1.setFloat("timeFactor", this.woftFactor);
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
  
  public int getCookProgressScaled(int var1)
  {
    return (!EEBase.isKleinStar(this.items[this.klein].id)) && (!this.isSending) ? 0 : this.items[this.klein] == null ? 0 : var1;
  }
  
  public int getBurnTimeRemainingScaled(int var1)
  {
    return EEMaps.getEMC(this.items[0].id, this.items[this.in].getData()) > 0 ? var1 : this.items[0] == null ? 0 : 0;
  }
  
  public int latentEnergy()
  {
    return this.scaledEnergy / 80;
  }
  
  public boolean receiveEnergy(int var1, byte var2, boolean var3)
  {
    if (passAllPackets(var1, var3))
    {
      return true;
    }
    if (this.scaledEnergy <= scaledMaximum() - var1)
    {
      if (var3)
      {
        this.accumulate += var1;
      }
      
      return true;
    }
    

    return false;
  }
  

  private boolean passAllPackets(int var1, boolean var2)
  {
    int var3 = 0;
    
    for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
    {
      if (passEnergy(var1, var4, false))
      {
        var3++;
      }
    }
    
    if (var3 == 0)
    {
      return false;
    }
    if (!var2)
    {
      return true;
    }
    

    int var6 = var1 / var3;
    
    if (var6 < 1)
    {
      return false;
    }
    

    for (byte var5 = 0; var5 < 6; var5 = (byte)(var5 + 1))
    {
      passEnergy(var6, var5, true);
    }
    
    return true;
  }
  


  public boolean passEnergy(int var1, byte var2, boolean var3)
  {
    TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
    
    if (var4 == null)
    {
      return false;
    }
    if ((!(var4 instanceof TileRelay)) && (!(var4 instanceof TileRelay2)) && (!(var4 instanceof TileRelay3)))
    {
      return ((var4 instanceof IEEPowerNet)) && (((IEEPowerNet)var4).receiveEnergy(var1, var2, var3));
    }
    

    IEEPowerNet var10000 = (IEEPowerNet)var4;
    return false;
  }
  

  public boolean sendEnergy(int var1, byte var2, boolean var3)
  {
    TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
    return var4 != null;
  }
  
  public void sendAllPackets(int var1)
  {
    int var2 = 0;
    
    for (byte var3 = 0; var3 < 6; var3 = (byte)(var3 + 1))
    {
      if (sendEnergy(var1, var3, false))
      {
        var2++;
      }
    }
    
    if (var2 != 0)
    {
      int var5 = var1 / var2;
      
      if (var5 >= 1)
      {
        for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
        {
          if (this.scaledEnergy - var5 <= 0)
          {
            return;
          }
          
          if (sendEnergy(var5, var4, true))
          {
            this.scaledEnergy -= var5;
          }
        }
      }
    }
  }
  
  public int relayBonus()
  {
    return 4;
  }
  
  private float getRelayOutput()
  {
    return 64.0F;
  }
  
  private int relayMaximum()
  {
    return 100000;
  }
  
  private int scaledMaximum()
  {
    return relayMaximum() * 80;
  }
  
  public int getRelayProductivity()
  {
    return (int)(getRelayOutput() * getWOFTReciprocal(this.woftFactor));
  }
  




  public void q_()
  {
    if (!clientFail())
    {
      boolean var1 = false;
      this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
      
      if (!this.world.isStatic)
      {
        this.burnTimeRemainingScaled = getBurnTimeRemainingScaled(12);
        this.cookProgressScaled = getCookProgressScaled(24);
        this.kleinDrainingScaled = getKleinDrainingScaled(30);
        this.kleinChargingScaled = getKleinChargingScaled(30);
        this.relayEnergyScaled = getRelayEnergyScaled(102);
        
        if (this.accumulate > 0)
        {
          this.scaledEnergy += this.accumulate;
          this.accumulate = 0;
        }
        


        if ((this.items[0] != null) && (EEBase.isKleinStar(this.items[0].id)))
        {
          for (int var2 = getRelayProductivity() * EEBase.getKleinLevel(this.items[0].id); var2 > 0; var2--)
          {
            if ((latentEnergy() < relayMaximum()) && (EEBase.takeKleinStarPoints(this.items[0], 1, this.world)))
            {
              this.scaledEnergy += 80;
            }
          }
        }
        
        if ((this.arrayCounter <= 0) && (canDestroy()))
        {
          this.arrayCounter = 20;
          var1 = true;
          destroyItem();
        }
        
        if (this.scaledEnergy >= getRelayProductivity())
        {
          sendAllPackets(getRelayProductivity());
          
          if ((this.items[(this.items.length - 1)] != null) && (EEBase.isKleinStar(this.items[(this.items.length - 1)].id)) && (this.scaledEnergy > 80))
          {
            for (int var2 = getRelayProductivity() * EEBase.getKleinLevel(this.items[(this.items.length - 1)].id); var2 > 0; var2--)
            {
              if ((this.scaledEnergy >= 80) && (EEBase.addKleinStarPoints(this.items[(this.items.length - 1)], 1, this.world)))
              {
                this.scaledEnergy -= 80;
              }
            }
          }
        }
        
        if (this.arrayCounter > 0)
        {
          this.arrayCounter -= 1;
        }
      }
      
      if (var1)
      {
        this.world.notify(this.x, this.y, this.z);
      }
    }
  }
  
  private int getRelayEnergyScaled(int var1)
  {
    return latentEnergy() * var1 / relayMaximum();
  }
  
  private int getKleinChargingScaled(int var1)
  {
    if ((this.items[(this.items.length - 1)] != null) && ((this.items[(this.items.length - 1)].getItem() instanceof ItemKleinStar)))
    {
      this.kleinChargePoints = ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getKleinPoints(this.items[(this.items.length - 1)]);
      return ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getKleinPoints(this.items[(this.items.length - 1)]) * var1 / ((ItemKleinStar)this.items[(this.items.length - 1)].getItem()).getMaxPoints(this.items[(this.items.length - 1)]);
    }
    

    this.kleinChargePoints = 0;
    return 0;
  }
  

  private int getKleinDrainingScaled(int var1)
  {
    if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
    {
      this.kleinDrainPoints = ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]);
      return ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * var1 / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]);
    }
    

    this.kleinDrainPoints = 0;
    return 0;
  }
  

  private boolean canDestroy()
  {
    if (this.items[0] == null)
    {
      for (int var1 = this.items.length - 2; var1 >= 1; var1--)
      {
        if ((this.items[var1] != null) && (EEMaps.getEMC(this.items[var1]) > 0))
        {
          this.items[0] = this.items[var1].cloneItemStack();
          this.items[var1] = null;
          break;
        }
      }
    }
    
    return this.items[0] != null;
  }
  
  public void destroyItem()
  {
    if (canDestroy())
    {
      if (!EEBase.isKleinStar(this.items[0].id))
      {
        this.scaledEnergy += getCorrectValue(this.items[0]) * 80;
        this.items[0].count -= 1;
        
        if (this.items[0].count <= 0)
        {
          this.items[0] = null;
        }
      }
    }
  }
  
  private int getCorrectValue(ItemStack var1)
  {
    return EEMaps.getEMC(var1);
  }
  
  private int getItemBurnTime(ItemStack var1)
  {
    if (var1 == null)
    {
      return 0;
    }
    if (EEBase.isKleinStar(var1.id))
    {
      return 0;
    }
    if (EEMaps.getEMC(var1) == 0)
    {
      EntityItem var2 = new EntityItem(this.world, this.x, this.y, this.z, var1.cloneItemStack());
      var2.pickupDelay = 10;
      this.world.addEntity(var2);
      var1 = null;
      return 0;
    }
    

    return var1.d() ? (int)(EEMaps.getEMC(var1.id) * (var1.i() - var1.getData()) / var1.i()) : EEMaps.getEMC(var1);
  }
  


  public void f() {}
  

  public void g() {}
  

  public boolean a(EntityHuman var1)
  {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
  
  public int getStartInventorySide(int var1)
  {
    return var1 == 1 ? this.items.length - 1 : 0;
  }
  
  public int getSizeInventorySide(int var1)
  {
    return var1 == 1 ? 1 : this.items.length - 1;
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    if (!this.world.isStatic)
    {
      var1.openGui(mod_EE.getInstance(), GuiIds.RELAY_1, this.world, this.x, this.y, this.z);
    }
    
    return true;
  }
  
  public int getTextureForSide(int var1)
  {
    if (var1 == 1)
    {
      return EEBase.relayTop;
    }
    

    byte var2 = this.direction;
    return var1 == var2 ? EEBase.relayFront : EEBase.relaySide;
  }
  

  public int getInventoryTexture(int var1)
  {
    return var1 == 3 ? EEBase.relayFront : var1 == 1 ? EEBase.relayTop : EEBase.relaySide;
  }
  
  public int getLightValue()
  {
    return 7;
  }
  


  public void onNeighborBlockChange(int var1) {}
  

  public void randomDisplayTick(Random var1) {}
  

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
