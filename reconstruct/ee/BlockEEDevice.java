package ee;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Item;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class BlockEEDevice extends BlockContainer implements forge.ITextureProvider
{
  private Class[] tileEntityMap = new Class[15];
  
  protected BlockEEDevice(int var1)
  {
    super(var1, Material.STONE);
    a(0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 1.0F);
  }
  
  public String getTextureFile()
  {
    return "/eqex/eqexterra.png";
  }
  



  public TileEntity a_()
  {
    return null;
  }
  




  public boolean a()
  {
    return false;
  }
  



  public boolean b()
  {
    return false;
  }
  
  public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
  {
    TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    return var5 == null ? lightEmission[this.id] : var5.getLightValue();
  }
  




  public void doPhysics(World var1, int var2, int var3, int var4, int var5)
  {
    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    
    if (var6 == null)
    {
      var1.setTypeId(var2, var3, var4, 0);
    }
    else
    {
      var6.onNeighborBlockChange(var5);
    }
  }
  



  public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
  {
    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    
    if (var6 != null)
    {
      var6.onBlockPlacedBy(var5);
    }
  }
  
  public float getHardness(int var1)
  {
    switch (var1)
    {
    case 0: 
      return 1.5F;
    case 1: 
      return 3.5F;
    }
    return 3.0F;
  }
  




  public int getDropType(int var1, Random var2, int var3)
  {
    return this.id;
  }
  



  public int a(Random var1)
  {
    return 1;
  }
  



  public int getDropData(int var1)
  {
    return var1;
  }
  
  public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
  {
    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    
    if (var6 != null)
    {
      var6.randomDisplayTick(var5);
    }
  }
  
  public void addTileEntityMapping(int var1, Class var2)
  {
    this.tileEntityMap[var1] = var2;
  }
  



  public int a(int var1, int var2)
  {
    return ((TileEE)getBlockEntity(var2)).getInventoryTexture(var1);
  }
  



  public int a(int var1)
  {
    return a(var1, 0);
  }
  
  public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
  {
    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    return var6 == null ? 0 : var6.getTextureForSide(var5);
  }
  



  public void remove(World var1, int var2, int var3, int var4)
  {
    TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    
    if (var5 != null)
    {
      var5.onBlockRemoval();
      super.remove(var1, var2, var3, var4);
    }
  }
  



  public void onPlace(World var1, int var2, int var3, int var4)
  {
    super.onPlace(var1, var2, var3, var4);
    TileEE var5 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    
    if (var5 != null)
    {
      var5.setDefaultDirection();
    }
  }
  




  public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
  {
    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    return var6 == null ? false : var6.onBlockActivated(var5);
  }
  
  public void addCreativeItems(ArrayList var1)
  {
    var1.add(EEBlock.transTablet);
  }
  
  public TileEntity getBlockEntity(int var1)
  {
    try
    {
      return (TileEntity)this.tileEntityMap[var1].getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    }
    catch (Exception var3) {}
    
    return null;
  }
  

  public void setItemName(int var1, String var2)
  {
    Item var3 = Item.byId[this.id];
    ((ItemBlockEEDevice)var3).setMetaName(var1, "tile." + var2);
  }
}
