package ee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.World;
import net.minecraft.server.WorldMapBase;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;


public class TransTabletData
  extends WorldMapBase
  implements IInventory
{
  public int latentEnergy = 0;
  public int currentEnergy = 0;
  public int learned = 0;
  public ItemStack[] items = new ItemStack[26];
  public boolean isMatterLocked;
  public boolean isFuelLocked;
  public EntityHuman player;
  private boolean readTome;
  private HashMap knowledge = new HashMap();
  public static List datas = new LinkedList();
  
  public TransTabletData(String var1)
  {
    super(var1);
    datas.add(this);
  }
  
  public void onUpdate(World var1, EntityHuman var2)
  {
    if (!EEProxy.isClient(var1))
    {
      if (this.player == null)
      {
        this.player = var2;
      }
      
      if (this.currentEnergy + this.latentEnergy == 0)
      {
        unlock();
      }
      
      calculateEMC();
      displayResults(this.currentEnergy + this.latentEnergy);
    }
  }
  
  public ItemStack target()
  {
    return this.items[8];
  }
  
  public boolean isOnGridBut(ItemStack var1, int var2)
  {
    for (int var3 = 10; var3 < this.items.length; var3++)
    {
      if ((var3 != var2) && (this.items[var3] != null) && (this.items[var3].doMaterialsMatch(var1)))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean isOnGrid(ItemStack var1)
  {
    for (int var2 = 10; var2 < this.items.length; var2++)
    {
      if ((this.items[var2] != null) && (this.items[var2].doMaterialsMatch(var1)))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public int kleinEMCTotal()
  {
    int var1 = 0;
    
    for (int var2 = 0; var2 < 8; var2++)
    {
      if ((this.items[var2] != null) && ((this.items[var2].getItem() instanceof ItemKleinStar)))
      {
        var1 += ((ItemKleinStar)this.items[var2].getItem()).getKleinPoints(this.items[var2]);
      }
    }
    
    return var1;
  }
  
  public void displayResults(int var1)
  {
    for (int var2 = 10; var2 < this.items.length; var2++)
    {
      if ((this.items[var2] != null) && ((var1 < EEMaps.getEMC(this.items[var2])) || (!matchesLock(this.items[var2])) || (isOnGridBut(this.items[var2], var2)) || ((target() != null) && (EEMaps.getEMC(this.items[var2]) > EEMaps.getEMC(target())))))
      {
        this.items[var2] = null;
      }
      
      if ((var2 == 9) && (target() != null) && (EEMaps.getEMC(target()) > 0) && (var1 > EEMaps.getEMC(target())) && (matchesLock(target())))
      {
        this.items[var2] = new ItemStack(target().id, 1, target().getData());
      }
      
      if ((var2 == 10) && (target() != null) && (EEMaps.getEMC(target()) > 0) && (var1 >= EEMaps.getEMC(target())) && (matchesLock(target())))
      {
        this.items[10] = new ItemStack(target().id, 1, target().getData());
      }
      
      for (int var3 = 0; var3 < Item.byId.length; var3++)
      {
        if (Item.byId[var3] != null)
        {
          int var4 = EEMaps.getMeta(var3);
          
          for (int var5 = 0; var5 <= var4; var5++)
          {
            ItemStack var6 = new ItemStack(var3, 1, var5);
            
            if ((!isOnGrid(var6)) && (matchesLock(var6)))
            {
              int var7 = EEMaps.getEMC(var6);
              
              if ((var7 != 0) && ((target() == null) || (var7 <= EEMaps.getEMC(target()))) && (playerKnows(var6.id, var6.getData())) && (var1 >= var7) && (var7 > EEMaps.getEMC(getItem(var2))))
              {
                this.items[var2] = new ItemStack(var3, 1, var5);
              }
            }
          }
        }
      }
    }
    
    update();
  }
  
  public void calculateEMC()
  {
    int var1 = 0;
    boolean var2 = false;
    
    for (int var3 = 0; var3 < 8; var3++)
    {
      if (this.items[var3] != null)
      {
        if ((EEMaps.getEMC(this.items[var3]) == 0) && (!EEBase.isKleinStar(this.items[var3].id)))
        {
          rejectItem(var3);
        }
        else if (EEBase.isKleinStar(this.items[var3].id))
        {
          if ((!playerKnows(this.items[var3].id, this.items[var3].getData())) && (EEMaps.getEMC(this.items[var3]) > 0))
          {
            if (this.items[var3].id == EEItem.alchemyTome.id)
            {
              pushTome();
            }
            
            pushKnowledge(this.items[var3].id, this.items[var3].getData());
            this.learned = 60;
          }
          
          if (this.latentEnergy > 0)
          {
            int var4 = ((ItemKleinStar)this.items[var3].getItem()).getMaxPoints(this.items[var3]) - ((ItemKleinStar)this.items[var3].getItem()).getKleinPoints(this.items[var3]);
            
            if (var4 > 0)
            {
              if (var4 > this.latentEnergy)
              {
                var4 = this.latentEnergy;
              }
              
              this.latentEnergy -= var4;
              EEBase.addKleinStarPoints(this.items[var3], var4);
            }
          }
          
          var1 += ((ItemKleinStar)this.items[var3].getItem()).getKleinPoints(this.items[var3]);
        }
        else
        {
          if ((!playerKnows(this.items[var3].id, this.items[var3].getData())) && (EEMaps.getEMC(this.items[var3]) > 0))
          {
            if (this.items[var3].id == EEItem.alchemyTome.id)
            {
              pushTome();
            }
            
            pushKnowledge(this.items[var3].id, this.items[var3].getData());
            this.learned = 60;
          }
          
          if ((!var2) && (!isFuelLocked()) && (!isMatterLocked()))
          {
            if (EEMaps.isFuel(this.items[var3]))
            {
              fuelLock();
            }
            else
            {
              matterLock();
            }
          }
          
          if (!matchesLock(this.items[var3]))
          {
            rejectItem(var3);
          }
          else
          {
            var1 += EEMaps.getEMC(this.items[var3]);
          }
        }
      }
    }
    
    this.currentEnergy = var1;
  }
  
  public boolean matchesLock(ItemStack var1)
  {
    if (isFuelLocked())
    {
      if (EEMaps.isFuel(var1))
      {
        return true;
      }
    }
    else
    {
      if (!isMatterLocked())
      {
        return true;
      }
      
      if (!EEMaps.isFuel(var1))
      {
        return true;
      }
    }
    
    return false;
  }
  



  public int getSize()
  {
    return this.items.length;
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
    return "Trans Tablet";
  }
  




  public int getMaxStackSize()
  {
    return 64;
  }
  



  public void update()
  {
    a();
  }
  



  public boolean a(EntityHuman var1)
  {
    return true;
  }
  

  public void f() {}
  

  public void g() {}
  

  public void a(NBTTagCompound var1)
  {
    this.isMatterLocked = var1.getBoolean("matterLock");
    this.isFuelLocked = var1.getBoolean("fuelLock");
    this.currentEnergy = var1.getInt("currentEnergy");
    this.latentEnergy = var1.getInt("latentEnergy");
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
    
    NBTTagList var8 = var1.getList("knowledge");
    this.knowledge = new HashMap();
    
    for (int var9 = 0; var9 < var8.size(); var9++)
    {
      NBTTagCompound var10 = (NBTTagCompound)var8.get(var9);
      int var6 = var10.getInt("item");
      int var7 = var10.getInt("meta");
      pushKnowledge(var6, var7);
    }
    
    this.readTome = var1.getBoolean("readTome");
  }
  



  public void b(NBTTagCompound var1)
  {
    var1.setBoolean("matterLock", this.isMatterLocked);
    var1.setBoolean("fuelLock", this.isFuelLocked);
    var1.setInt("currentEnergy", this.currentEnergy);
    var1.setInt("latentEnergy", this.latentEnergy);
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
    var1.setBoolean("readTome", this.readTome);
    
    for (var3 = 0; var3 < this.knowledge.size(); var3++)
    {
      if (this.knowledge.get(Integer.valueOf(var3)) != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setInt("item", ((Integer[])this.knowledge.get(Integer.valueOf(var3)))[0].intValue());
        var4.setInt("meta", ((Integer[])this.knowledge.get(Integer.valueOf(var3)))[1].intValue());
        var2.add(var4);
      }
    }
    
    var1.set("knowledge", var2);
  }
  
  public HashMap getKnowledge()
  {
    return this.knowledge;
  }
  
  public void pushKnowledge(int var1, int var2)
  {
    if (Item.byId[var1] != null)
    {
      if (Item.byId[var1].g())
      {
        var2 = 0;
      }
      
      if (!playerKnows(var1, var2))
      {


        for (int var3 = 0; this.knowledge.get(Integer.valueOf(var3)) != null; var3++) {}
        



        this.knowledge.put(Integer.valueOf(var3), new Integer[] { Integer.valueOf(var1), Integer.valueOf(var2) });
        a();
      }
    }
  }
  
  public boolean playerKnows(int var1, int var2)
  {
    if (this.readTome)
    {
      return true;
    }
    

    ItemStack var3 = new ItemStack(var1, 1, var2);
    
    if (var3.d())
    {
      var2 = 0;
    }
    
    for (int var4 = 0; this.knowledge.get(Integer.valueOf(var4)) != null; var4++)
    {
      int var5 = ((Integer[])this.knowledge.get(Integer.valueOf(var4)))[0].intValue();
      int var6 = ((Integer[])this.knowledge.get(Integer.valueOf(var4)))[1].intValue();
      
      if ((var5 == var1) && (var6 == var2))
      {
        return true;
      }
    }
    
    return false;
  }
  

  public void pushTome()
  {
    this.readTome = true;
    a();
  }
  
  public long getDisplayEnergy()
  {
    return this.latentEnergy + this.currentEnergy;
  }
  
  public int getLatentEnergy()
  {
    return this.latentEnergy;
  }
  
  public void setLatentEnergy(int var1)
  {
    this.latentEnergy = var1;
    a();
  }
  
  public int getCurrentEnergy()
  {
    return this.currentEnergy;
  }
  
  public void setCurrentEnergy(int var1)
  {
    this.currentEnergy = var1;
    a();
  }
  
  public boolean isFuelLocked()
  {
    return this.isFuelLocked;
  }
  
  public void fuelUnlock()
  {
    this.isFuelLocked = false;
    a();
  }
  
  public void fuelLock()
  {
    this.isFuelLocked = true;
    a();
  }
  
  public boolean isMatterLocked()
  {
    return this.isMatterLocked;
  }
  
  public void matterUnlock()
  {
    this.isMatterLocked = false;
    a();
  }
  
  public void matterLock()
  {
    this.isMatterLocked = true;
    a();
  }
  
  public void unlock()
  {
    fuelUnlock();
    matterUnlock();
  }
  
  public void rejectItem(int var1)
  {
    if (this.player != null)
    {
      if (this.player.world != null)
      {
        if (!EEProxy.isClient(this.player.world))
        {
          if (getItem(var1) != null)
          {
            EntityItem var2 = new EntityItem(this.player.world, this.player.locX, this.player.locY - 0.5D, this.player.locZ, getItem(var1));
            nullStack(var1);
            var2.pickupDelay = 1;
            this.player.world.addEntity(var2);
          }
        }
      }
    }
  }
  
  private void nullStack(int var1)
  {
    this.items[var1] = null;
    a();
  }
  




  public ItemStack splitWithoutUpdate(int var1)
  {
    if (var1 <= 8)
    {
      if (this.items[var1] != null)
      {
        ItemStack var2 = this.items[var1];
        this.items[var1] = null;
        return var2;
      }
      

      return null;
    }
    


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
