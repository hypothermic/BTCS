package ee;

import ee.core.GuiIds;
import forge.ITextureProvider;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.IInventory;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;
import net.minecraft.server.MathHelper;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import net.minecraft.server.mod_EE;

public class BlockEEPedestal extends BlockContainer implements ITextureProvider
{
  public boolean isActive;
  private Random furnaceRand;
  
  public BlockEEPedestal(int var1)
  {
    super(var1, Material.ORE);
    c(EEBlock.eeStone.getHardness(5));
    a(0.2F, 0.15F, 0.2F, 0.8F, 0.7F, 0.8F);
    this.textureId = EEBase.dmBlockSide;
  }
  
  public String getTextureFile()
  {
    return "/eqex/eqexterra.png";
  }
  



  public int c()
  {
    return mod_EE.pedestalModelID;
  }
  
  public void setItemName(int var1, String var2)
  {
    Item var3 = Item.byId[this.id];
    ((ItemBlockEEPedestal)var3).setMetaName(var1, "tile." + var2);
  }
  
  public void addCreativeItems(ArrayList var1)
  {
    var1.add(EEBlock.pedestal);
  }
  
  public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
  {
    return isBurning(var1, var2, var3, var4) ? 15 : lightEmission[this.id];
  }
  
  public boolean isBurning(IBlockAccess var1, int var2, int var3, int var4)
  {
    TileEntity var5 = var1.getTileEntity(var2, var3, var4);
    return ((var5 instanceof TilePedestal)) && (((TilePedestal)var5).isInterdicting());
  }
  




  public boolean a()
  {
    return false;
  }
  



  public boolean b()
  {
    return false;
  }
  



  public TileEntity a_()
  {
    return new TilePedestal();
  }
  



  public void postPlace(World var1, int var2, int var3, int var4, EntityLiving var5)
  {
    EntityHuman var6 = null;
    
    if ((var5 instanceof EntityHuman))
    {
      var6 = (EntityHuman)var5;
    }
    
    int var7 = MathHelper.floor(var5.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    int var8 = var1.getData(var2, var3, var4) & 0xC;
    
    if (var7 == 0)
    {
      var8 |= 0x2;
    }
    
    if (var7 == 1)
    {
      var8 |= 0x1;
    }
    
    if (var7 == 2)
    {
      var8 |= 0x3;
    }
    
    if (var7 == 3)
    {
      var8 |= 0x0;
    }
    
    var1.setData(var2, var3, var4, var8);
    
    if (var6 != null)
    {
      TilePedestal var9 = (TilePedestal)EEProxy.getTileEntity(var1, var2, var3, var4, TilePedestal.class);
      var9.setPlayer(var6);
    }
  }
  




  public void a(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6)
  {
    int var7 = var1.getData(var2, var3, var4) & 0x3;
    
    if ((var7 >= 0) && (var7 <= 3))
    {
      a(0.2F, 0.0F, 0.2F, 0.8F, 0.15F, 0.8F);
      super.a(var1, var2, var3, var4, var5, var6);
      a(0.4F, 0.15F, 0.4F, 0.6F, 0.65F, 0.6F);
      super.a(var1, var2, var3, var4, var5, var6);
      a(0.3F, 0.65F, 0.3F, 0.7F, 0.7F, 0.7F);
      super.a(var1, var2, var3, var4, var5, var6);
    }
    
    a(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
  }
  




  public void doPhysics(World var1, int var2, int var3, int var4, int var5)
  {
    TilePedestal var6 = (TilePedestal)var1.getTileEntity(var2, var3, var4);
    
    if ((var6 != null) && (var1.isBlockIndirectlyPowered(var2, var3, var4)) && (var6.activationCooldown <= 0))
    {
      var6.activate();
    }
  }
  




  public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
  {
    if (EEProxy.isClient(var1))
    {
      return true;
    }
    

    TilePedestal var6 = (TilePedestal)var1.getTileEntity(var2, var3, var4);
    
    if (var6 != null)
    {
      if (var5.isSneaking())
      {
        var5.openGui(mod_EE.getInstance(), GuiIds.PEDESTAL, var1, var2, var3, var4);
      }
      else
      {
        var6.activate(var5);
      }
    }
    
    return true;
  }
  




  protected int getDropData(int var1)
  {
    return var1 & 0xC;
  }
  



  public void remove(World var1, int var2, int var3, int var4)
  {
    IInventory var5 = (IInventory)var1.getTileEntity(var2, var3, var4);
    
    for (int var6 = 0; var6 < var5.getSize(); var6++)
    {
      ItemStack var7 = var5.getItem(var6);
      
      if (var7 != null)
      {
        float var8 = var1.random.nextFloat() * 0.8F + 0.1F;
        float var9 = var1.random.nextFloat() * 0.8F + 0.1F;
        float var10 = var1.random.nextFloat() * 0.8F + 0.1F;
        
        while (var7.count > 0)
        {
          int var11 = var1.random.nextInt(21) + 10;
          
          if (var11 > var7.count)
          {
            var11 = var7.count;
          }
          
          var7.count -= var11;
          EntityItem var12 = new EntityItem(var1, var2 + var8, var3 + var9, var4 + var10, new ItemStack(var7.id, var11, var7.getData()));
          float var13 = 0.05F;
          var12.motX = ((float)var1.random.nextGaussian() * var13);
          var12.motY = ((float)var1.random.nextGaussian() * var13 + 0.2F);
          var12.motZ = ((float)var1.random.nextGaussian() * var13);
          var1.addEntity(var12);
        }
      }
    }
  }
}
