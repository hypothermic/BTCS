package ee;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFire;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;

public class TileNovaCatalyst
  extends TileEE
{
  public boolean fuseLit;
  
  public void a(NBTTagCompound var1)
  {
    super.a(var1);
    this.fuseLit = var1.getBoolean("fuseLit");
  }
  



  public void b(NBTTagCompound var1)
  {
    super.b(var1);
    var1.setBoolean("fuseLit", this.fuseLit);
  }
  




  public void q_()
  {
    for (int var1 = -1; var1 <= 1; var1 += 2)
    {
      if (this.world.getTypeId(this.x + var1, this.y, this.z) == Block.FIRE.id)
      {
        lightFuse();
      }
      
      if (this.world.getTypeId(this.x, this.y + var1, this.z) == Block.FIRE.id)
      {
        lightFuse();
      }
      
      if (this.world.getTypeId(this.x, this.y, this.z + var1) == Block.FIRE.id)
      {
        lightFuse();
      }
    }
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
  

  private boolean isChest(TileEntity var1)
  {
    return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
  }
  
  public void setDefaultDirection(int var1, int var2, int var3)
  {
    if (!this.world.isStatic)
    {
      int var4 = this.world.getTypeId(var1, var2, var3 - 1);
      int var5 = this.world.getTypeId(var1, var2, var3 + 1);
      int var6 = this.world.getTypeId(var1 - 1, var2, var3);
      int var7 = this.world.getTypeId(var1 + 1, var2, var3);
      byte var8 = 2;
      
      if ((Block.n[var4] != 0) && (Block.n[var5] == 0))
      {
        var8 = 3;
      }
      
      if ((Block.n[var5] != 0) && (Block.n[var4] == 0))
      {
        var8 = 2;
      }
      
      if ((Block.n[var6] != 0) && (Block.n[var7] == 0))
      {
        var8 = 5;
      }
      
      if ((Block.n[var7] != 0) && (Block.n[var6] == 0))
      {
        var8 = 4;
      }
      
      this.direction = var8;
    }
  }
  
  public void lightFuse()
  {
    if (!this.fuseLit)
    {
      this.fuseLit = true;
      this.world.setTypeId(this.x, this.y, this.z, 0);
      onBlockDestroyedByPlayer();
    }
  }
  
  public void onBlockPlacedBy(EntityLiving var1)
  {
    if ((var1 instanceof EntityHuman))
    {
      this.player = ((EntityHuman)var1).name;
    }
    
    int var2 = MathHelper.floor(var1.yaw * 4.0F / 360.0F + 0.5D) & 0x3;
    
    if (var2 == 0)
    {
      this.direction = 2;
    }
    
    if (var2 == 1)
    {
      this.direction = 5;
    }
    
    if (var2 == 2)
    {
      this.direction = 3;
    }
    
    if (var2 == 3)
    {
      this.direction = 4;
    }
  }
  
  public int getTextureForSide(int var1)
  {
    return var1 == 1 ? EEBase.novaCatalystTop : var1 == 0 ? EEBase.novaCatalystBottom : EEBase.novaCatalystSide;
  }
  
  public int getInventoryTexture(int var1)
  {
    return getTextureForSide(var1);
  }
  
  public int getLightValue()
  {
    return 4;
  }
  
  public void onBlockAdded()
  {
    super.onBlockAdded();
    
    if (this.world.isBlockIndirectlyPowered(this.x, this.y, this.z))
    {
      lightFuse();
    }
  }
  
  public void onNeighborBlockChange(int var1)
  {
    if ((var1 > 0) && (Block.byId[var1].isPowerSource()) && (this.world.isBlockIndirectlyPowered(this.x, this.y, this.z)))
    {
      lightFuse();
    }
  }
  
  public void onBlockClicked(EntityHuman var1)
  {
    if ((var1.U() != null) && (var1.U().id == Item.FLINT_AND_STEEL.id))
    {
      lightFuse();
    }
    
    super.onBlockClicked(var1);
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    return false;
  }
  
  public void onBlockRemoval() {}
  
  public void randomDisplayTick(Random var1) {}
  
  public void onBlockDestroyedByExplosion()
  {
    EntityNovaPrimed var1 = null;
    
    if ((this.player != null) && (this.player != "") && (this.world.a(this.player) != null))
    {
      var1 = new EntityNovaPrimed(this.world, this.world.a(this.player), this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
    }
    else
    {
      var1 = new EntityNovaPrimed(this.world, this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
    }
    
    var1.fuse = 0;
    this.world.addEntity(var1);
  }
  
  public void onBlockDestroyedByPlayer()
  {
    if (!this.world.isStatic)
    {
      if (!this.fuseLit)
      {
        dropBlockAsItem_do(new ItemStack(EEBlock.eeStone.id, 1, 10));
      }
      else
      {
        EntityNovaPrimed var1 = null;
        
        if ((this.player != null) && (this.player != "") && (this.world.a(this.player) != null))
        {
          var1 = new EntityNovaPrimed(this.world, this.world.a(this.player), this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
        }
        else
        {
          var1 = new EntityNovaPrimed(this.world, this.x + 0.5F, this.y + 0.5F, this.z + 0.5F);
        }
        
        var1.fuse = (this.world.random.nextInt(var1.fuse / 2) + var1.fuse / 4);
        this.world.addEntity(var1);
      }
    }
  }
  
  protected void dropBlockAsItem_do(ItemStack var1)
  {
    if (!this.world.isStatic)
    {
      float var2 = 0.7F;
      double var3 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
      double var5 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
      double var7 = this.world.random.nextFloat() * var2 + (1.0F - var2) * 0.5D;
      EntityItem var9 = new EntityItem(this.world, this.x + var3, this.y + var5, this.z + var7, var1);
      var9.pickupDelay = 10;
      this.world.addEntity(var9);
    }
  }
}
