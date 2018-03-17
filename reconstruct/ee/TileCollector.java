package ee;

import buildcraft.api.ISpecialInventory;
import buildcraft.api.Orientations;
import ee.core.GuiIds;
import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.World;
import net.minecraft.server.WorldProvider;

public class TileCollector extends TileEE implements ISpecialInventory, forge.ISidedInventory, IEEPowerNet
{
  private ItemStack[] items = new ItemStack[11];
  public int currentSunStatus = 1;
  public int collectorSunTime = 0;
  private int accumulate = 0;
  private float woftFactor = 1.0F;
  public int currentFuelProgress = 0;
  public boolean isUsingPower;
  public int kleinProgressScaled = 0;
  public int sunTimeScaled = 0;
  public int kleinPoints = 0;
  



  public int getSize()
  {
    return this.items.length;
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
        if (EEMaps.isFuel(var1))
        {
          for (int var4 = 0; var4 <= this.items.length - 3; var4++)
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
              }
            }
          }
        } else if ((EEBase.isKleinStar(var1.id)) && (this.items[0] == null))
        {
          if (var2)
          {
            for (this.items[0] = var1.cloneItemStack(); var1.count > 0; var1.count -= 1) {}
          }
          



          return true;
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
        if ((this.items[var3] != null) && (var3 != this.items.length - 1))
        {


          if (var3 == 0)
          {
            if (EEBase.isKleinStar(this.items[var3].id))
            {
              ItemStack var4 = this.items[var3].cloneItemStack();
              
              if (var1)
              {
                this.items[var3] = null;
              }
              
              return var4;
            }
          }
          else if ((this.items[var3].id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[var3].doMaterialsMatch(this.items[(this.items.length - 1)]))))
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
    }
    
    return null;
  }
  




  public String getName()
  {
    return "Energy Collector";
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
    
    this.currentSunStatus = var1.getShort("sunStatus");
    this.woftFactor = var1.getFloat("timeFactor");
    this.accumulate = var1.getInt("accumulate");
    this.collectorSunTime = var1.getInt("sunTime");
  }
  



  public void b(NBTTagCompound var1)
  {
    super.b(var1);
    var1.setInt("sunTime", this.collectorSunTime);
    var1.setFloat("timeFactor", this.woftFactor);
    var1.setInt("accumulate", this.accumulate);
    var1.setShort("sunStatus", (short)this.currentSunStatus);
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
  
  public int getSunProgressScaled(int var1)
  {
    return canUpgrade() ? this.collectorSunTime * var1 / (getFuelDifference() * 80) : this.collectorSunTime * var1 / (getFuelDifference() * 80) > 24 ? 24 : getFuelDifference() <= 0 ? 0 : (this.items[0] != null) && (EEBase.isKleinStar(this.items[0].id)) ? 24 : 0;
  }
  


  public boolean canUpgrade()
  {
    if (this.items[0] == null)
    {
      for (int var1 = this.items.length - 3; var1 >= 1; var1--)
      {
        if ((this.items[var1] != null) && ((this.items[(this.items.length - 1)] == null) || (!this.items[var1].doMaterialsMatch(this.items[(this.items.length - 1)]))) && (EEMaps.isFuel(this.items[var1])) && (this.items[var1].getItem().id != EEItem.aeternalisFuel.id))
        {
          this.items[0] = this.items[var1].cloneItemStack();
          this.items[var1] = null;
          break;
        }
      }
    }
    
    if (this.items[0] == null)
    {
      if (this.items[(this.items.length - 2)] == null)
      {
        return false;
      }
      
      if ((EEMaps.isFuel(this.items[(this.items.length - 2)])) && (this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id))
      {
        this.items[0] = this.items[(this.items.length - 2)].cloneItemStack();
        this.items[(this.items.length - 2)] = null;
      }
    }
    
    if (this.items[0] == null)
    {
      return false;
    }
    

    if (EEBase.isKleinStar(this.items[0].id))
    {
      if (EEBase.canIncreaseKleinStarPoints(this.items[0], this.world))
      {
        return true;
      }
      
      if (this.items[(this.items.length - 2)] == null)
      {
        this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
        this.items[0] = null;
        return false;
      }
      
      for (int var1 = 1; var1 <= this.items.length - 3; var1++)
      {
        if (this.items[var1] == null)
        {
          this.items[var1] = this.items[(this.items.length - 2)].cloneItemStack();
          this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
          this.items[0] = null;
          return false;
        }
      }
    }
    
    return (this.items[0].getItem().id != EEItem.aeternalisFuel.id) && (EEMaps.isFuel(this.items[0]));
  }
  

  public boolean receiveEnergy(int var1, byte var2, boolean var3)
  {
    if (!isUsingPower())
    {
      return false;
    }
    if (var3)
    {
      this.accumulate += var1;
      return true;
    }
    

    return true;
  }
  

  public boolean sendEnergy(int var1, byte var2, boolean var3)
  {
    net.minecraft.server.TileEntity var4 = this.world.getTileEntity(this.x + (var2 == 4 ? 1 : var2 == 5 ? -1 : 0), this.y + (var2 == 0 ? 1 : var2 == 1 ? -1 : 0), this.z + (var2 == 2 ? 1 : var2 == 3 ? -1 : 0));
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
    
    if (var2 == 0)
    {
      if (this.collectorSunTime <= 800000 - var1)
      {
        this.collectorSunTime += var1;
      }
    }
    else
    {
      int var5 = var1 / var2;
      
      if (var5 >= 1)
      {
        for (byte var4 = 0; var4 < 6; var4 = (byte)(var4 + 1))
        {
          sendEnergy(var5, var4, true);
        }
      }
    }
  }
  
  public boolean passEnergy(int var1, byte var2, boolean var3)
  {
    return false;
  }
  
  public int relayBonus()
  {
    return 0;
  }
  
  public int getRealSunStatus()
  {
    if (this.world.worldProvider.d)
    {
      this.currentSunStatus = 16;
    }
    else
    {
      this.currentSunStatus = (this.world.getLightLevel(this.x, this.y + 1, this.z) + 1);
    }
    
    return this.currentSunStatus;
  }
  
  public int getSunStatus(int var1)
  {
    return getRealSunStatus() * var1 / 16;
  }
  




  public void q_()
  {
    if (!clientFail())
    {
      if (!this.world.isStatic)
      {
        if (this.collectorSunTime < 0)
        {
          this.collectorSunTime = 0;
        }
        
        if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
        {
          this.kleinProgressScaled = getKleinProgressScaled(48);
          this.kleinPoints = getKleinPoints(this.items[0]);
        }
        
        this.sunTimeScaled = getSunTimeScaled(48);
        this.currentFuelProgress = getSunProgressScaled(24);
        this.currentSunStatus = getSunStatus(12);
        this.isUsingPower = isUsingPower();
        

        for (int var1 = this.items.length - 3; var1 >= 2; var1--)
        {
          if ((this.items[var1] == null) && (this.items[(var1 - 1)] != null))
          {
            this.items[var1] = this.items[(var1 - 1)].cloneItemStack();
            this.items[(var1 - 1)] = null;
          }
        }
        
        this.woftFactor = (EEBase.getPedestalFactor(this.world) * EEBase.getPlayerWatchFactor());
        
        if (isUsingPower())
        {
          this.collectorSunTime += getFactoredProduction();
          
          if (this.accumulate > 0)
          {
            this.collectorSunTime += this.accumulate;
            this.accumulate = 0;
          }
          
          if (EEBase.isKleinStar(this.items[0].id))
          {
            var1 = getFactoredProduction() * EEBase.getKleinLevel(this.items[0].id);
            do {
              this.collectorSunTime -= 80;var1--;
              if ((var1 <= 0) || (this.collectorSunTime < 80)) break; } while (EEBase.addKleinStarPoints(this.items[0], 1, this.world));

          }
          else
          {

            do
            {

              this.collectorSunTime -= getFuelDifference() * 80;
              uptierFuel();
              if (getFuelDifference() <= 0) break; } while (this.collectorSunTime >= getFuelDifference() * 80);

          }
          

        }
        else
        {

          if (this.accumulate > 0)
          {
            this.collectorSunTime += this.accumulate;
            this.accumulate = 0;
          }
          
          sendAllPackets(getFactoredProduction());
        }
      }
    }
  }
  
  private int getKleinPoints(ItemStack var1)
  {
    return (var1.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var1.getItem()).getKleinPoints(var1) : var1 == null ? 0 : 0;
  }
  
  private int getSunTimeScaled(int var1)
  {
    return this.collectorSunTime * var1 / 800000;
  }
  
  private int getKleinProgressScaled(int var1)
  {
    return (this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)) ? ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * var1 / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]) : 0;
  }
  
  public int getFactoredProduction()
  {
    return (int)(getProduction() * getWOFTReciprocal(this.woftFactor));
  }
  
  public int getProduction()
  {
    return getRealSunStatus();
  }
  
  public boolean isUsingPower()
  {
    return (canUpgrade()) && (canCollect());
  }
  
  private int getFuelDifference()
  {
    return this.items[0] == null ? 0 : getFuelLevel(getNextFuel(this.items[0])) - getFuelLevel(this.items[0]);
  }
  
  private int getFuelLevel(ItemStack var1)
  {
    return EEMaps.getEMC(var1);
  }
  
  private ItemStack getNextFuel(ItemStack var1)
  {
    int var2 = var1.id;
    int var3 = var1.getData();
    
    if (this.items[(this.items.length - 1)] == null)
    {
      if (EEMaps.isFuel(var1))
      {
        if ((var2 == Item.COAL.id) && (var3 == 1))
        {
          return new ItemStack(Item.REDSTONE.id, 1, 0);
        }
        
        if (var2 == Item.REDSTONE.id)
        {
          return new ItemStack(Item.COAL.id, 1, 0);
        }
        
        if (var2 == Item.COAL.id)
        {
          return new ItemStack(Item.SULPHUR.id, 1, 0);
        }
        
        if (var2 == Item.SULPHUR.id)
        {
          return new ItemStack(Item.GLOWSTONE_DUST.id, 1, 0);
        }
        
        if (var2 == Item.GLOWSTONE_DUST.id)
        {
          return new ItemStack(EEItem.alchemicalCoal.id, 1, 0);
        }
        
        if (var2 == EEItem.alchemicalCoal.id)
        {
          return new ItemStack(Item.BLAZE_POWDER.id, 1, 0);
        }
        
        if (var2 == Item.BLAZE_POWDER.id)
        {
          return new ItemStack(Block.GLOWSTONE.id, 1, 0);
        }
        
        if (var2 == Block.GLOWSTONE.id)
        {
          return new ItemStack(EEItem.mobiusFuel.id, 1, 0);
        }
        
        if (var2 == EEItem.mobiusFuel.id)
        {
          return new ItemStack(EEItem.aeternalisFuel.id, 1, 0);
        }
      }
      
      return null;
    }
    if (EEMaps.isFuel(this.items[(this.items.length - 1)]))
    {
      return EEMaps.getEMC(var2, var3) < EEMaps.getEMC(this.items[(this.items.length - 1)].id, this.items[(this.items.length - 1)].getData()) ? this.items[(this.items.length - 1)] : null;
    }
    

    EntityItem var4 = new EntityItem(this.world, this.x, this.y, this.z, this.items[(this.items.length - 1)].cloneItemStack());
    this.items[(this.items.length - 1)] = null;
    var4.pickupDelay = 10;
    this.world.addEntity(var4);
    return null;
  }
  

  private boolean canCollect()
  {
    if (this.items[0] == null)
    {
      for (int var1 = 1; var1 <= this.items.length - 3; var1++)
      {
        if ((this.items[var1] != null) && ((this.items[(this.items.length - 1)] == null) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 1)].doMaterialsMatch(this.items[var1])))))
        {
          this.items[0] = this.items[var1].cloneItemStack();
          this.items[var1] = null;
          break;
        }
      }
      
      if (this.items[0] == null)
      {
        return false;
      }
    }
    
    if (EEBase.isKleinStar(this.items[0].id))
    {
      return true;
    }
    if (getNextFuel(this.items[0]) == null)
    {
      return false;
    }
    

    ItemStack var3 = getNextFuel(this.items[0]).cloneItemStack();
    
    if (this.items[(this.items.length - 2)] == null)
    {
      return true;
    }
    



    if (!this.items[(this.items.length - 2)].doMaterialsMatch(var3))
    {
      for (int var2 = 1; var2 <= this.items.length - 3; var2++)
      {
        if (this.items[var2] == null)
        {
          this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
          this.items[(this.items.length - 2)] = null;
          return true;
        }
        
        if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
        {
          while ((this.items[(this.items.length - 2)] != null) && (this.items[var2].count < 64))
          {
            this.items[(this.items.length - 2)].count -= 1;
            this.items[var2].count += 1;
            
            if (this.items[(this.items.length - 2)].count == 0)
            {
              this.items[(this.items.length - 2)] = null;
              return true;
            }
          }
        }
      }
    }
    
    if ((this.items[(this.items.length - 2)] != null) && (!this.items[(this.items.length - 2)].doMaterialsMatch(var3)))
    {
      return false;
    }
    if ((this.items[(this.items.length - 2)].count < getMaxStackSize()) && (this.items[(this.items.length - 2)].count < this.items[(this.items.length - 2)].getMaxStackSize()))
    {
      return true;
    }
    

    for (int var2 = 1; var2 <= this.items.length - 2; var2++)
    {
      if ((this.items[var2] != null) && ((this.items[var2].getItem().id == EEItem.mobiusFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 1)])))) && (this.items[var2].count >= this.items[var2].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[var2].getItem(), this.items[var2].count))))
      {
        this.items[var2] = null;
      }
    }
    
    if (this.items[(this.items.length - 2)] == null)
    {
      return true;
    }
    

    for (var2 = 1; var2 <= this.items.length - 3; var2++)
    {
      if (this.items[var2] == null)
      {
        this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
        this.items[(this.items.length - 2)] = null;
        return true;
      }
      
      if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
      {
        while ((this.items[(this.items.length - 2)] != null) && (this.items[var2].count < 64))
        {
          this.items[(this.items.length - 2)].count -= 1;
          this.items[var2].count += 1;
          
          if (this.items[(this.items.length - 2)].count == 0)
          {
            this.items[(this.items.length - 2)] = null;
            return true;
          }
        }
      }
    }
    
    return this.items[(this.items.length - 2)].count < var3.getMaxStackSize();
  }
  




  public void uptierFuel()
  {
    if (canCollect())
    {
      if (getNextFuel(this.items[0]) != null)
      {
        ItemStack var1 = getNextFuel(this.items[0]).cloneItemStack();
        var1.count = 1;
        
        if (this.items[(this.items.length - 2)] == null)
        {
          if (((this.items[(this.items.length - 1)] == null) || (!var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) && (var1.getItem() != EEItem.aeternalisFuel))
          {
            this.items[(this.items.length - 2)] = var1.cloneItemStack();
          }
          else if (!tryDropInChest(var1))
          {
            this.items[(this.items.length - 2)] = var1.cloneItemStack();
          }
        }
        else if (this.items[(this.items.length - 2)].id == var1.id)
        {
          if (this.items[(this.items.length - 2)].count == var1.getMaxStackSize())
          {
            if ((this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id) && ((this.items[(this.items.length - 1)] == null) || (!this.items[(this.items.length - 2)].doMaterialsMatch(this.items[(this.items.length - 1)]))))
            {
              for (int var2 = 1; var2 <= this.items.length - 3; var2++)
              {
                if (this.items[var2] == null)
                {
                  this.items[var2] = this.items[(this.items.length - 2)].cloneItemStack();
                  this.items[(this.items.length - 2)] = null;
                  break;
                }
                
                if (this.items[var2].doMaterialsMatch(this.items[(this.items.length - 2)]))
                {
                  while ((this.items[var2].count < this.items[var2].getMaxStackSize()) && (this.items[(this.items.length - 2)] != null))
                  {
                    this.items[(this.items.length - 2)].count -= 1;
                    this.items[var2].count += 1;
                    
                    if (this.items[(this.items.length - 2)].count == 0)
                    {
                      this.items[(this.items.length - 2)] = null;
                    }
                    
                  }
                }
              }
            } else if (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))
            {
              this.items[(this.items.length - 2)] = null;
            }
          }
          else if (((this.items[(this.items.length - 1)] == null) || (!var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) && (var1.getItem() != EEItem.aeternalisFuel))
          {
            this.items[(this.items.length - 2)].count += var1.count;
          }
          else if (!tryDropInChest(var1))
          {
            this.items[(this.items.length - 2)].count += var1.count;
          }
        }
        else if (((this.items[(this.items.length - 1)] != null) && (var1.doMaterialsMatch(this.items[(this.items.length - 1)]))) || ((var1.getItem() == EEItem.aeternalisFuel) && (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))))
        {
          this.items[(this.items.length - 2)] = null;
        }
        
        if (this.items[0].getItem().k())
        {
          this.items[0] = new ItemStack(this.items[0].getItem().j());
        }
        else
        {
          this.items[0].count -= 1;
        }
        
        if (this.items[0].count <= 0)
        {
          this.items[0] = null;
        }
      }
    }
  }
  

  public void f() {}
  

  public void g() {}
  

  public boolean a(EntityHuman var1)
  {
    return this.world.getTileEntity(this.x, this.y, this.z) == this;
  }
  
  public int getStartInventorySide(int var1)
  {
    return var1 == 0 ? 0 : 1;
  }
  
  public int getSizeInventorySide(int var1)
  {
    return var1 == 0 ? 1 : this.items.length - 2;
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    if (!this.world.isStatic)
    {
      var1.openGui(net.minecraft.server.mod_EE.getInstance(), GuiIds.COLLECTOR_1, this.world, this.x, this.y, this.z);
    }
    
    return true;
  }
  
  public int getTextureForSide(int var1)
  {
    if (var1 == 1)
    {
      return EEBase.collectorTop;
    }
    

    byte var2 = this.direction;
    return var1 != var2 ? EEBase.collectorSide : EEBase.collectorFront;
  }
  

  public int getInventoryTexture(int var1)
  {
    return var1 == 3 ? EEBase.collectorFront : var1 == 1 ? EEBase.collectorTop : EEBase.collectorSide;
  }
  
  public int getLightValue()
  {
    return 7;
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
