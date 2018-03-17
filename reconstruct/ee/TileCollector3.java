package ee;

import java.util.Random;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class TileCollector3 extends TileEE implements buildcraft.api.ISpecialInventory, forge.ISidedInventory, IEEPowerNet
{
  private ItemStack[] items;
  public int currentSunStatus;
  public int collectorSunTime;
  private int accumulate;
  private float woftFactor;
  public int currentFuelProgress;
  public boolean isUsingPower;
  public int sunTimeScaled;
  public int kleinProgressScaled;
  public int kleinPoints;
  
  public TileCollector3()
  {
    this.items = new ItemStack[19];
    this.kleinPoints = 0;
    this.currentSunStatus = 1;
    this.collectorSunTime = 0;
    this.woftFactor = 1.0F;
    this.accumulate = 0;
    this.currentFuelProgress = 0;
    this.kleinProgressScaled = 0;
    this.sunTimeScaled = 0;
  }
  


  public void onBlockRemoval()
  {
    for (int i = 0; i < getSize(); i++)
    {
      ItemStack itemstack = getItem(i);
      
      if (itemstack != null)
      {



        float f = this.world.random.nextFloat() * 0.8F + 0.1F;
        float f1 = this.world.random.nextFloat() * 0.8F + 0.1F;
        float f2 = this.world.random.nextFloat() * 0.8F + 0.1F;
        






        while (itemstack.count > 0)
        {



          int j = this.world.random.nextInt(21) + 10;
          
          if (j > itemstack.count)
          {
            j = itemstack.count;
          }
          
          itemstack.count -= j;
          EntityItem entityitem = new EntityItem(this.world, this.x + f, this.y + f1, this.z + f2, new ItemStack(itemstack.id, j, itemstack.getData()));
          
          if (entityitem != null)
          {
            float f3 = 0.05F;
            entityitem.motX = ((float)this.world.random.nextGaussian() * f3);
            entityitem.motY = ((float)this.world.random.nextGaussian() * f3 + 0.2F);
            entityitem.motZ = ((float)this.world.random.nextGaussian() * f3);
            
            if ((entityitem.itemStack.getItem() instanceof ItemKleinStar))
            {
              ((ItemKleinStar)entityitem.itemStack.getItem()).setKleinPoints(entityitem.itemStack, ((ItemKleinStar)itemstack.getItem()).getKleinPoints(itemstack));
            }
            
            this.world.addEntity(entityitem);
          }
        }
      }
    }
  }
  


  public int getSize()
  {
    return this.items.length;
  }
  




  public int getMaxStackSize()
  {
    return 64;
  }
  



  public ItemStack getItem(int i)
  {
    return this.items[i];
  }
  




  public ItemStack splitStack(int i, int j)
  {
    if (this.items[i] != null)
    {
      if (this.items[i].count <= j)
      {
        ItemStack itemstack = this.items[i];
        this.items[i] = null;
        return itemstack;
      }
      
      ItemStack itemstack1 = this.items[i].a(j);
      
      if (this.items[i].count == 0)
      {
        this.items[i] = null;
      }
      
      return itemstack1;
    }
    

    return null;
  }
  




  public void setItem(int i, ItemStack itemstack)
  {
    this.items[i] = itemstack;
    
    if ((itemstack != null) && (itemstack.count > getMaxStackSize()))
    {
      itemstack.count = getMaxStackSize();
    }
  }
  
  public boolean addItem(ItemStack var1, boolean var2, buildcraft.api.Orientations var3)
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
  

  public ItemStack extractItem(boolean flag, buildcraft.api.Orientations orientations)
  {
    switch (orientations)
    {

    case Unknown: 
    case XNeg: 
    case XPos: 
    case YNeg: 
    case YPos: 
    case ZNeg: 
    case ZPos: 
      for (int i = 0; i < this.items.length; i++)
      {
        if ((this.items[i] != null) && (i != this.items.length - 1))
        {



          if (i == 0)
          {
            if (EEBase.isKleinStar(this.items[i].id))
            {



              ItemStack itemstack = this.items[i].cloneItemStack();
              
              if (flag)
              {
                this.items[i] = null;
              }
              
              return itemstack;
            }
          }
          else if ((this.items[i].id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[i].doMaterialsMatch(this.items[(this.items.length - 1)]))))
          {



            ItemStack itemstack1 = this.items[i].cloneItemStack();
            itemstack1.count = 1;
            
            if (flag)
            {
              this.items[i].count -= 1;
              
              if (this.items[i].count < 1)
              {
                this.items[i] = null;
              }
            }
            
            return itemstack1;
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
  



  public void a(NBTTagCompound nbttagcompound)
  {
    super.a(nbttagcompound);
    net.minecraft.server.NBTTagList nbttaglist = nbttagcompound.getList("Items");
    this.items = new ItemStack[getSize()];
    
    for (int i = 0; i < nbttaglist.size(); i++)
    {
      NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.get(i);
      byte byte0 = nbttagcompound1.getByte("Slot");
      
      if ((byte0 >= 0) && (byte0 < this.items.length))
      {
        this.items[byte0] = ItemStack.a(nbttagcompound1);
      }
    }
    
    this.currentSunStatus = nbttagcompound.getShort("sunStatus");
    this.woftFactor = nbttagcompound.getFloat("timeFactor");
    this.accumulate = nbttagcompound.getInt("accumulate");
    this.collectorSunTime = nbttagcompound.getInt("sunTime");
  }
  



  public void b(NBTTagCompound nbttagcompound)
  {
    super.b(nbttagcompound);
    nbttagcompound.setInt("sunTime", this.collectorSunTime);
    nbttagcompound.setFloat("timeFactor", this.woftFactor);
    nbttagcompound.setInt("accumulate", this.accumulate);
    nbttagcompound.setShort("sunStatus", (short)this.currentSunStatus);
    net.minecraft.server.NBTTagList nbttaglist = new net.minecraft.server.NBTTagList();
    
    for (int i = 0; i < this.items.length; i++)
    {
      if (this.items[i] != null)
      {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.items[i].save(nbttagcompound1);
        nbttaglist.add(nbttagcompound1);
      }
    }
    
    nbttagcompound.set("Items", nbttaglist);
  }
  
  public int getSunProgressScaled(int i)
  {
    if (canUpgrade())
    {
      if (getFuelDifference() <= 0)
      {
        return (this.items[0] == null) || (!EEBase.isKleinStar(this.items[0].id)) ? 0 : 24;
      }
      
      if (this.collectorSunTime * i / (getFuelDifference() * 80) > 24)
      {
        return 24;
      }
      

      return this.collectorSunTime * i / (getFuelDifference() * 80);
    }
    


    return 0;
  }
  

  public boolean canUpgrade()
  {
    if (this.items[0] == null)
    {
      int i = this.items.length - 3;
      


      while (i >= 1)
      {



        if ((this.items[i] != null) && ((this.items[(this.items.length - 1)] == null) || (!this.items[i].doMaterialsMatch(this.items[(this.items.length - 1)]))) && (EEMaps.isFuel(this.items[i])) && (this.items[i].getItem().id != EEItem.aeternalisFuel.id))
        {
          this.items[0] = this.items[i].cloneItemStack();
          this.items[i] = null;
          break;
        }
        
        i--;
      }
    }
    

    if (this.items[0] == null)
    {
      if (this.items[(this.items.length - 2)] != null)
      {
        if ((EEMaps.isFuel(this.items[(this.items.length - 2)])) && (this.items[(this.items.length - 2)].getItem().id != EEItem.aeternalisFuel.id))
        {
          this.items[0] = this.items[(this.items.length - 2)].cloneItemStack();
          this.items[(this.items.length - 2)] = null;
        }
        
      }
      else {
        return false;
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
      
      for (int j = 1; j <= this.items.length - 3; j++)
      {
        if (this.items[j] == null)
        {
          this.items[j] = this.items[(this.items.length - 2)].cloneItemStack();
          this.items[(this.items.length - 2)] = this.items[0].cloneItemStack();
          this.items[0] = null;
          return false;
        }
      }
    }
    
    if ((this.items[0].getItem().id != EEItem.aeternalisFuel.id) && (EEMaps.isFuel(this.items[0])))
    {
      return true;
    }
    
    return this.items[0].getItem().id == EEItem.darkMatter.id;
  }
  
  public boolean receiveEnergy(int i, byte byte0, boolean flag)
  {
    if (!isUsingPower())
    {
      return false;
    }
    
    if (flag)
    {
      this.accumulate += i;
      return true;
    }
    

    return true;
  }
  

  public boolean sendEnergy(int i, byte byte0, boolean flag)
  {
    net.minecraft.server.TileEntity tileentity = this.world.getTileEntity(this.x + (byte0 != 5 ? 1 : byte0 != 4 ? 0 : -1), this.y + (byte0 != 1 ? 1 : byte0 != 0 ? 0 : -1), this.z + (byte0 != 3 ? 1 : byte0 != 2 ? 0 : -1));
    
    if (tileentity == null)
    {
      return false;
    }
    
    return ((tileentity instanceof IEEPowerNet)) && (((IEEPowerNet)tileentity).receiveEnergy(i + ((IEEPowerNet)tileentity).relayBonus(), byte0, flag));
  }
  
  public void sendAllPackets(int i)
  {
    int j = 0;
    
    for (byte byte0 = 0; byte0 < 6; byte0 = (byte)(byte0 + 1))
    {
      if (sendEnergy(i, byte0, false))
      {
        j++;
      }
    }
    
    if (j == 0)
    {
      if (this.collectorSunTime <= 4800000 - i)
      {
        this.collectorSunTime += i;
      }
      
      return;
    }
    
    int k = i / j;
    
    if (k < 1)
    {
      return;
    }
    
    for (byte byte1 = 0; byte1 < 6; byte1 = (byte)(byte1 + 1))
    {
      sendEnergy(k, byte1, true);
    }
  }
  
  public boolean passEnergy(int i, byte byte0, boolean flag)
  {
    return false;
  }
  
  public int relayBonus()
  {
    return 0;
  }
  
  public int getRealSunStatus()
  {
    if (this.world == null)
    {
      System.out.println("World object is turning a null for collectors..");
      return 0;
    }
    
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
  
  public int getSunStatus(int i)
  {
    return getRealSunStatus() * i / 16;
  }
  




  public void q_()
  {
    if (clientFail())
    {
      return;
    }
    
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
      
      for (int i = this.items.length - 3; i >= 2; i--)
      {
        if ((this.items[i] == null) && (this.items[(i - 1)] != null))
        {
          this.items[i] = this.items[(i - 1)].cloneItemStack();
          this.items[(i - 1)] = null;
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
          int j = getFactoredProduction() * EEBase.getKleinLevel(this.items[0].id);
          do {
            this.collectorSunTime -= 80;j--;
            if ((j <= 0) || (this.collectorSunTime < 80)) break; } while (EEBase.addKleinStarPoints(this.items[0], 1, this.world));

        }
        else
        {

          do
          {

            this.collectorSunTime -= getFuelDifference() * 80;uptierFuel();
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
  
  private int getKleinPoints(ItemStack itemstack)
  {
    if (itemstack == null)
    {
      return 0;
    }
    
    if ((itemstack.getItem() instanceof ItemKleinStar))
    {
      return ((ItemKleinStar)itemstack.getItem()).getKleinPoints(itemstack);
    }
    

    return 0;
  }
  

  private int getSunTimeScaled(int i)
  {
    return this.collectorSunTime * i / 4800000;
  }
  
  private int getKleinProgressScaled(int i)
  {
    if ((this.items[0] != null) && ((this.items[0].getItem() instanceof ItemKleinStar)))
    {
      return ((ItemKleinStar)this.items[0].getItem()).getKleinPoints(this.items[0]) * i / ((ItemKleinStar)this.items[0].getItem()).getMaxPoints(this.items[0]);
    }
    

    return 0;
  }
  

  public int getFactoredProduction()
  {
    return (int)(getProduction() * getWOFTReciprocal(this.woftFactor));
  }
  
  public int getProduction()
  {
    return getRealSunStatus() * 10;
  }
  
  public boolean isUsingPower()
  {
    return (canUpgrade()) && (canCollect());
  }
  
  private int getFuelDifference()
  {
    if (this.items[0] == null)
    {
      return 0;
    }
    

    return getFuelLevel(getNextFuel(this.items[0])) - getFuelLevel(this.items[0]);
  }
  

  private int getFuelLevel(ItemStack itemstack)
  {
    return EEMaps.getEMC(itemstack);
  }
  
  private ItemStack getNextFuel(ItemStack itemstack)
  {
    int i = itemstack.id;
    int j = itemstack.getData();
    
    if (this.items[(this.items.length - 1)] == null)
    {
      if (EEMaps.isFuel(itemstack))
      {
        if ((i == Item.COAL.id) && (j == 1))
        {
          return new ItemStack(Item.REDSTONE.id, 1, 0);
        }
        
        if (i == Item.REDSTONE.id)
        {
          return new ItemStack(Item.COAL.id, 1, 0);
        }
        
        if (i == Item.COAL.id)
        {
          return new ItemStack(Item.SULPHUR.id, 1, 0);
        }
        
        if (i == Item.SULPHUR.id)
        {
          return new ItemStack(Item.GLOWSTONE_DUST.id, 1, 0);
        }
        
        if (i == Item.GLOWSTONE_DUST.id)
        {
          return new ItemStack(EEItem.alchemicalCoal.id, 1, 0);
        }
        
        if (i == EEItem.alchemicalCoal.id)
        {
          return new ItemStack(Item.BLAZE_POWDER.id, 1, 0);
        }
        
        if (i == Item.BLAZE_POWDER.id)
        {
          return new ItemStack(net.minecraft.server.Block.GLOWSTONE.id, 1, 0);
        }
        
        if (i == net.minecraft.server.Block.GLOWSTONE.id)
        {
          return new ItemStack(EEItem.mobiusFuel.id, 1, 0);
        }
        
        if (i == EEItem.mobiusFuel.id)
        {
          return new ItemStack(EEItem.aeternalisFuel.id, 1, 0);
        }
      }
    } else {
      if (EEMaps.isFuel(this.items[(this.items.length - 1)]))
      {
        if (EEMaps.getEMC(i, j) < EEMaps.getEMC(this.items[(this.items.length - 1)].id, this.items[(this.items.length - 1)].getData()))
        {
          return this.items[(this.items.length - 1)];
        }
        

        return null;
      }
      


      EntityItem entityitem = new EntityItem(this.world, this.x, this.y, this.z, this.items[(this.items.length - 1)].cloneItemStack());
      this.items[(this.items.length - 1)] = null;
      entityitem.pickupDelay = 10;
      this.world.addEntity(entityitem);
      return null;
    }
    
    return null;
  }
  
  private boolean canCollect()
  {
    if (this.items[0] == null)
    {
      int i = 1;
      


      while (i <= this.items.length - 3)
      {



        if ((this.items[i] != null) && ((this.items[(this.items.length - 1)] == null) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 1)].doMaterialsMatch(this.items[i])))))
        {
          this.items[0] = this.items[i].cloneItemStack();
          this.items[i] = null;
          break;
        }
        
        i++;
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
    
    ItemStack itemstack = getNextFuel(this.items[0]).cloneItemStack();
    
    if (this.items[(this.items.length - 2)] == null)
    {
      return true;
    }
    
    if (!this.items[(this.items.length - 2)].doMaterialsMatch(itemstack))
    {


      for (int j = 1; j <= this.items.length - 3; j++)
      {
        if (this.items[j] != null)
        {
          if (this.items[j].doMaterialsMatch(this.items[(this.items.length - 2)]))
          {


            do
            {

              if ((this.items[(this.items.length - 2)] == null) || (this.items[j].count >= 64)) {
                break;
              }
              

              this.items[(this.items.length - 2)].count -= 1;
              this.items[j].count += 1;
            }
            while (this.items[(this.items.length - 2)].count != 0);
            
            this.items[(this.items.length - 2)] = null;
            return true;
          }
        }
        else {
          this.items[j] = this.items[(this.items.length - 2)].cloneItemStack();
          this.items[(this.items.length - 2)] = null;
          return true;
        }
      }
    }
    
    if ((this.items[(this.items.length - 2)] != null) && (!this.items[(this.items.length - 2)].doMaterialsMatch(itemstack)))
    {
      return false;
    }
    
    if ((this.items[(this.items.length - 2)].count < getMaxStackSize()) && (this.items[(this.items.length - 2)].count < this.items[(this.items.length - 2)].getMaxStackSize()))
    {
      return true;
    }
    
    for (int k = 1; k <= this.items.length - 2; k++)
    {
      if ((this.items[k] != null) && ((this.items[k].getItem().id == EEItem.mobiusFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[k].doMaterialsMatch(this.items[(this.items.length - 1)])))) && (this.items[k].count >= this.items[k].getMaxStackSize()) && (tryDropInChest(new ItemStack(this.items[k].getItem(), this.items[k].count))))
      {
        this.items[k] = null;
      }
    }
    
    if (this.items[(this.items.length - 2)] == null)
    {
      return true;
    }
    


    for (int l = 1; l <= this.items.length - 3; l++)
    {
      if (this.items[l] != null)
      {
        if (this.items[l].doMaterialsMatch(this.items[(this.items.length - 2)]))
        {


          do
          {

            if ((this.items[(this.items.length - 2)] == null) || (this.items[l].count >= 64)) {
              break;
            }
            

            this.items[(this.items.length - 2)].count -= 1;
            this.items[l].count += 1;
          }
          while (this.items[(this.items.length - 2)].count != 0);
          
          this.items[(this.items.length - 2)] = null;
          return true;
        }
      }
      else {
        this.items[l] = this.items[(this.items.length - 2)].cloneItemStack();
        this.items[(this.items.length - 2)] = null;
        return true;
      }
    }
    
    return this.items[(this.items.length - 2)].count < itemstack.getMaxStackSize();
  }
  
  public void uptierFuel()
  {
    if (!canCollect())
    {
      return;
    }
    
    if (getNextFuel(this.items[0]) == null)
    {
      return;
    }
    
    ItemStack itemstack = getNextFuel(this.items[0]).cloneItemStack();
    itemstack.count = 1;
    
    if (this.items[(this.items.length - 2)] == null)
    {
      if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || (itemstack.getItem() == EEItem.aeternalisFuel))
      {
        if (!tryDropInChest(itemstack))
        {
          this.items[(this.items.length - 2)] = itemstack.cloneItemStack();
        }
        
      }
      else {
        this.items[(this.items.length - 2)] = itemstack.cloneItemStack();
      }
    }
    else if (this.items[(this.items.length - 2)].id == itemstack.id)
    {
      if (this.items[(this.items.length - 2)].count == itemstack.getMaxStackSize())
      {
        if ((this.items[(this.items.length - 2)].getItem().id == EEItem.aeternalisFuel.id) || ((this.items[(this.items.length - 1)] != null) && (this.items[(this.items.length - 2)].doMaterialsMatch(this.items[(this.items.length - 1)]))))
        {
          if (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))
          {
            this.items[(this.items.length - 2)] = null;
          }
        }
        else
        {
          int i = 1;
          


          while (i <= this.items.length - 3)
          {



            if (this.items[i] == null)
            {
              this.items[i] = this.items[(this.items.length - 2)].cloneItemStack();
              this.items[(this.items.length - 2)] = null;
              break;
            }
            
            if (this.items[i].doMaterialsMatch(this.items[(this.items.length - 2)]))
            {


              while ((this.items[i].count < this.items[i].getMaxStackSize()) && (this.items[(this.items.length - 2)] != null))
              {



                this.items[(this.items.length - 2)].count -= 1;
                this.items[i].count += 1;
                
                if (this.items[(this.items.length - 2)].count == 0)
                {
                  this.items[(this.items.length - 2)] = null;
                }
              }
            }
            

            i++;
          }
          
        }
      }
      else if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || (itemstack.getItem() == EEItem.aeternalisFuel))
      {
        if (!tryDropInChest(itemstack))
        {
          this.items[(this.items.length - 2)].count += itemstack.count;
        }
        
      }
      else {
        this.items[(this.items.length - 2)].count += itemstack.count;
      }
    }
    else if (((this.items[(this.items.length - 1)] != null) && (itemstack.doMaterialsMatch(this.items[(this.items.length - 1)]))) || ((itemstack.getItem() == EEItem.aeternalisFuel) && (tryDropInChest(this.items[(this.items.length - 2)].cloneItemStack()))))
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
  



  public void f() {}
  


  public void g() {}
  


  public boolean a(EntityHuman entityhuman)
  {
    if (this.world.getTileEntity(this.x, this.y, this.z) != this)
    {
      return false;
    }
    

    return entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D;
  }
  

  public int getStartInventorySide(int i)
  {
    return i != 0 ? 1 : 0;
  }
  
  public int getSizeInventorySide(int i)
  {
    if (i == 0)
    {
      return 1;
    }
    

    return this.items.length - 2;
  }
  

  public boolean onBlockActivated(EntityHuman entityhuman)
  {
    if (!this.world.isStatic)
    {
      entityhuman.openGui(net.minecraft.server.mod_EE.getInstance(), ee.core.GuiIds.COLLECTOR_3, this.world, this.x, this.y, this.z);
    }
    
    return true;
  }
  
  public int getTextureForSide(int i)
  {
    if (i == 1)
    {
      return EEBase.collector3Top;
    }
    
    byte byte0 = this.direction;
    
    if (i != byte0)
    {
      return EEBase.collectorSide;
    }
    

    return EEBase.collectorFront;
  }
  

  public int getInventoryTexture(int i)
  {
    if (i == 1)
    {
      return EEBase.collector3Top;
    }
    
    if (i == 3)
    {
      return EEBase.collectorFront;
    }
    

    return EEBase.collectorSide;
  }
  

  public int getLightValue()
  {
    return 15;
  }
  



  public void onNeighborBlockChange(int i) {}
  



  public ItemStack splitWithoutUpdate(int i)
  {
    return null;
  }
  
  public ItemStack[] getContents()
  {
    return this.items;
  }
  
  public void setMaxStackSize(int size) {}
}
