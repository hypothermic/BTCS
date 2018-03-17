package ee;

import forge.ITextureProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Item;
import net.minecraft.server.Material;
import net.minecraft.server.MovingObjectPosition;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class BlockEETorch extends Block implements ITextureProvider
{
  private int powerCycle;
  
  protected BlockEETorch(int var1)
  {
    super(var1, Material.ORIENTABLE);
    a(true);
    this.textureId = 16;
  }
  




  public AxisAlignedBB e(World var1, int var2, int var3, int var4)
  {
    return null;
  }
  




  public boolean a()
  {
    return false;
  }
  
  public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
  {
    return 15;
  }
  



  public boolean b()
  {
    return false;
  }
  



  public int c()
  {
    return 2;
  }
  
  public void setItemName(int var1, String var2)
  {
    Item var3 = Item.byId[this.id];
    ((ItemBlockEETorch)var3).setMetaName(var1, "tile." + var2);
  }
  
  public void addCreativeItems(ArrayList var1)
  {
    var1.add(EEBlock.iTorch);
  }
  
  public String getTextureFile()
  {
    return "/eqex/eqexterra.png";
  }
  
  private boolean canPlaceTorchOn(World var1, int var2, int var3, int var4)
  {
    return (var1.e(var2, var3, var4)) || (var1.getTypeId(var2, var3, var4) == Block.FENCE.id);
  }
  



  public boolean canPlace(World var1, int var2, int var3, int var4)
  {
    return var1.e(var2, var3, var4 + 1) ? true : var1.e(var2, var3, var4 - 1) ? true : var1.e(var2 + 1, var3, var4) ? true : var1.e(var2 - 1, var3, var4) ? true : canPlaceTorchOn(var1, var2, var3 - 1, var4);
  }
  




  public void postPlace(World var1, int var2, int var3, int var4, int var5)
  {
    var1.c(var2, var3, var4, this.id, 1);
    int var6 = var1.getData(var2, var3, var4);
    
    if ((var5 == 1) && (canPlaceTorchOn(var1, var2, var3 - 1, var4)))
    {
      var6 = 5;
    }
    
    if ((var5 == 2) && (var1.e(var2, var3, var4 + 1)))
    {
      var6 = 4;
    }
    
    if ((var5 == 3) && (var1.e(var2, var3, var4 - 1)))
    {
      var6 = 3;
    }
    
    if ((var5 == 4) && (var1.e(var2 + 1, var3, var4)))
    {
      var6 = 2;
    }
    
    if ((var5 == 5) && (var1.e(var2 - 1, var3, var4)))
    {
      var6 = 1;
    }
    
    var1.setData(var2, var3, var4, var6);
  }
  



  public void a(World var1, int var2, int var3, int var4, Random var5)
  {
    super.a(var1, var2, var3, var4, var5);
    
    if (this.powerCycle > 0)
    {
      doInterdiction(var1, var2, var3, var4, true);
      this.powerCycle -= 1;
    }
    
    doInterdiction(var1, var2, var3, var4, false);
    
    if (var1.getData(var2, var3, var4) == 0)
    {
      onPlace(var1, var2, var3, var4);
    }
    
    var1.c(var2, var3, var4, this.id, 1);
  }
  



  public void onPlace(World var1, int var2, int var3, int var4)
  {
    var1.c(var2, var3, var4, this.id, 1);
    
    if (var1.e(var2 - 1, var3, var4))
    {
      var1.setData(var2, var3, var4, 1);
    }
    else if (var1.e(var2 + 1, var3, var4))
    {
      var1.setData(var2, var3, var4, 2);
    }
    else if (var1.e(var2, var3, var4 - 1))
    {
      var1.setData(var2, var3, var4, 3);
    }
    else if (var1.e(var2, var3, var4 + 1))
    {
      var1.setData(var2, var3, var4, 4);
    }
    else if (canPlaceTorchOn(var1, var2, var3 - 1, var4))
    {
      var1.setData(var2, var3, var4, 5);
    }
    
    dropTorchIfCantStay(var1, var2, var3, var4);
  }
  




  public void doPhysics(World var1, int var2, int var3, int var4, int var5)
  {
    var1.c(var2, var3, var4, this.id, 1);
    

    if (var1.isBlockIndirectlyPowered(var2, var3, var4))
    {
      for (int var6 = 0; var6 <= 2; var6++)
      {
        doInterdiction(var1, var2, var3, var4, true);
      }
      
      this.powerCycle = 16;
    }
    
    if (dropTorchIfCantStay(var1, var2, var3, var4))
    {
      int var6 = var1.getData(var2, var3, var4);
      boolean var7 = false;
      
      if ((!var1.e(var2 - 1, var3, var4)) && (var6 == 1))
      {
        var7 = true;
      }
      
      if ((!var1.e(var2 + 1, var3, var4)) && (var6 == 2))
      {
        var7 = true;
      }
      
      if ((!var1.e(var2, var3, var4 - 1)) && (var6 == 3))
      {
        var7 = true;
      }
      
      if ((!var1.e(var2, var3, var4 + 1)) && (var6 == 4))
      {
        var7 = true;
      }
      
      if ((!canPlaceTorchOn(var1, var2, var3 - 1, var4)) && (var6 == 5))
      {
        var7 = true;
      }
      
      if (var7)
      {
        b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 1);
        var1.setTypeId(var2, var3, var4, 0);
      }
    }
  }
  
  private boolean dropTorchIfCantStay(World var1, int var2, int var3, int var4)
  {
    if (!canPlace(var1, var2, var3, var4))
    {
      b(var1, var2, var3, var4, var1.getData(var2, var3, var4), 1);
      var1.setTypeId(var2, var3, var4, 0);
      return false;
    }
    

    return true;
  }
  





  public MovingObjectPosition a(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6)
  {
    int var7 = var1.getData(var2, var3, var4) & 0x7;
    float var8 = 0.15F;
    
    if (var7 == 1)
    {
      a(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
    }
    else if (var7 == 2)
    {
      a(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
    }
    else if (var7 == 3)
    {
      a(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
    }
    else if (var7 == 4)
    {
      a(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
    }
    else
    {
      float var9 = 0.1F;
      a(0.5F - var9, 0.0F, 0.5F - var9, 0.5F + var9, 0.6F, 0.5F + var9);
    }
    
    return super.a(var1, var2, var3, var4, var5, var6);
  }
  
  public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
  {
    var1.c(var2, var3, var4, this.id, 1);
    int var6 = var1.getData(var2, var3, var4);
    double var7 = var2 + 0.5F;
    double var9 = var3 + 0.7F;
    double var11 = var4 + 0.5F;
    double var13 = 0.2199999988079071D;
    double var15 = 0.27000001072883606D;
    
    if (var6 == 1)
    {
      var1.a("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
    }
    else if (var6 == 2)
    {
      var1.a("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
    }
    else if (var6 == 3)
    {
      var1.a("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
    }
    else if (var6 == 4)
    {
      var1.a("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
    }
    else
    {
      var1.a("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
    }
  }
  
  public void doInterdiction(World var1, int var2, int var3, int var4, boolean var5)
  {
    float var6 = 5.0F;
    List var7 = var1.a(EntityMonster.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
    Iterator var9 = var7.iterator();
    
    while (var9.hasNext())
    {
      Entity var8 = (Entity)var9.next();
      PushEntities(var8, var2, var3, var4);
    }
    
    List var15 = var1.a(EntityArrow.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
    Iterator var11 = var15.iterator();
    
    while (var11.hasNext())
    {
      Entity var10 = (Entity)var11.next();
      PushEntities(var10, var2, var3, var4);
    }
    
    List var14 = var1.a(EntityFireball.class, AxisAlignedBB.b(var2 - var6, var3 - var6, var4 - var6, var2 + var6, var3 + var6, var4 + var6));
    Iterator var13 = var14.iterator();
    
    while (var13.hasNext())
    {
      Entity var12 = (Entity)var13.next();
      PushEntities(var12, var2, var3, var4);
    }
  }
  
  private void PushEntities(Entity var1, int var2, int var3, int var4)
  {
    if (!(var1 instanceof EntityHuman))
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
}
