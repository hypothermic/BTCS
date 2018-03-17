package ee;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import net.minecraft.server.mod_EE;

public class BlockEEChest extends BlockContainer implements forge.ITextureProvider
{
  private Random random = new Random();
  private Class[] tileEntityMap = new Class[15];
  
  protected BlockEEChest(int var1)
  {
    super(var1, Material.STONE);
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
  



  public int c()
  {
    return mod_EE.chestModelID;
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
    if ((var1.getTileEntity(var2, var3, var4) instanceof TileAlchChest))
    {
      TileAlchChest var5 = (TileAlchChest)var1.getTileEntity(var2, var3, var4);
      
      if (var5 != null)
      {
        for (int var6 = 0; var6 < var5.getSize(); var6++)
        {
          ItemStack var7 = var5.getItem(var6);
          
          if (var7 != null)
          {
            float var8 = this.random.nextFloat() * 0.8F + 0.1F;
            float var9 = this.random.nextFloat() * 0.8F + 0.1F;
            EntityItem var10;
            for (float var11 = this.random.nextFloat() * 0.8F + 0.1F; var7.count > 0; var1.addEntity(var10))
            {
              int var12 = this.random.nextInt(21) + 10;
              
              if (var12 > var7.count)
              {
                var12 = var7.count;
              }
              
              var7.count -= var12;
              var10 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var11, new ItemStack(var7.id, var12, var7.getData()));
              float var13 = 0.05F;
              var10.motX = ((float)this.random.nextGaussian() * var13);
              var10.motY = ((float)this.random.nextGaussian() * var13 + 0.2F);
              var10.motZ = ((float)this.random.nextGaussian() * var13);
              
              if (var7.hasTag())
              {
                var10.itemStack.setTag((NBTTagCompound)var7.getTag().clone());
              }
            }
          }
        }
      }
    }
    else if ((var1.getTileEntity(var2, var3, var4) instanceof TileCondenser))
    {
      TileCondenser var14 = (TileCondenser)var1.getTileEntity(var2, var3, var4);
      
      if (var14 != null)
      {
        for (int var6 = 0; var6 < var14.getSize(); var6++)
        {
          ItemStack var7 = var14.getItem(var6);
          
          if (var7 != null)
          {
            float var8 = this.random.nextFloat() * 0.8F + 0.1F;
            float var9 = this.random.nextFloat() * 0.8F + 0.1F;
            EntityItem var10;
            for (float var11 = this.random.nextFloat() * 0.8F + 0.1F; var7.count > 0; var1.addEntity(var10))
            {
              int var12 = this.random.nextInt(21) + 10;
              
              if (var12 > var7.count)
              {
                var12 = var7.count;
              }
              
              var7.count -= var12;
              var10 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var11, new ItemStack(var7.id, var12, var7.getData()));
              float var13 = 0.05F;
              var10.motX = ((float)this.random.nextGaussian() * var13);
              var10.motY = ((float)this.random.nextGaussian() * var13 + 0.2F);
              var10.motZ = ((float)this.random.nextGaussian() * var13);
              
              if (var7.hasTag())
              {
                var10.itemStack.setTag((NBTTagCompound)var7.getTag().clone());
              }
            }
          }
        }
      }
    }
    
    super.remove(var1, var2, var3, var4);
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
    if (var5.isSneaking())
    {
      return false;
    }
    

    TileEE var6 = (TileEE)EEProxy.getTileEntity(var1, var2, var3, var4, TileEE.class);
    return var6 == null ? false : var6.onBlockActivated(var5);
  }
  

  public void addCreativeItems(ArrayList var1)
  {
    var1.add(EEBlock.alchChest);
    var1.add(EEBlock.condenser);
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
    ((ItemBlockEEChest)var3).setMetaName(var1, "tile." + var2);
  }
}
