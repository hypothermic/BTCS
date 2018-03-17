package ee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

public class MercurialEyeData
  extends WorldMapBase implements IInventory
{
  public boolean markForUpdate;
  public static final String prefix = "eye";
  public static final String prefix_ = "eye_";
  public ItemStack[] eyeContents = new ItemStack[2];
  public static List datas = new LinkedList();
  public EntityHuman player;
  
  public MercurialEyeData(String var1)
  {
    super(var1);
    datas.add(this);
  }
  
  public void onUpdate(World var1, EntityHuman var2)
  {
    this.player = var2;
    
    if (this.markForUpdate)
    {
      a();
    }
  }
  



  public int getSize()
  {
    return 2;
  }
  



  public ItemStack getItem(int var1)
  {
    return this.eyeContents[var1];
  }
  




  public ItemStack splitStack(int var1, int var2)
  {
    if (this.eyeContents[var1] != null)
    {


      if (this.eyeContents[var1].count <= var2)
      {
        ItemStack var3 = this.eyeContents[var1];
        this.eyeContents[var1] = null;
        update();
        return var3;
      }
      

      ItemStack var3 = this.eyeContents[var1].a(var2);
      
      if (this.eyeContents[var1].count == 0)
      {
        this.eyeContents[var1] = null;
      }
      
      update();
      return var3;
    }
    


    return null;
  }
  




  public void setItem(int var1, ItemStack var2)
  {
    if ((var2 != null) && (var2.id == EEItem.mercurialEye.id) && (this.player != null))
    {
      EntityItem var3 = new EntityItem(this.player.world, this.player.locX, this.player.locY, this.player.locZ, var2);
      this.player.world.addEntity(var3);
      var2 = null;
    }
    else
    {
      this.eyeContents[var1] = var2;
      
      if ((var2 != null) && (var2.count > getMaxStackSize()))
      {
        var2.count = getMaxStackSize();
      }
      
      update();
    }
  }
  



  public String getName()
  {
    return "Mercurial Eye";
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
    NBTTagList var2 = var1.getList("Items");
    this.eyeContents = new ItemStack[2];
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
      int var5 = var4.getByte("Slot") & 0xFF;
      
      if ((var5 >= 0) && (var5 < this.eyeContents.length))
      {
        this.eyeContents[var5] = ItemStack.a(var4);
      }
    }
  }
  



  public void b(NBTTagCompound var1)
  {
    NBTTagList var2 = new NBTTagList();
    
    for (int var3 = 0; var3 < this.eyeContents.length; var3++)
    {
      if (this.eyeContents[var3] != null)
      {
        NBTTagCompound var4 = new NBTTagCompound();
        var4.setByte("Slot", (byte)var3);
        this.eyeContents[var3].save(var4);
        var2.add(var4);
      }
    }
    
    var1.set("Items", var2);
  }
  




  public ItemStack splitWithoutUpdate(int var1)
  {
    return null;
  }
  
  public ItemStack[] getContents() {
    return this.eyeContents;
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
