package ee;

import ee.network.PacketHandler;
import ee.network.PacketTypeHandler;
import ee.network.TileEntityPacket;
import java.util.Random;
import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;
import net.minecraft.server.TileEntityChest;
import net.minecraft.server.World;


public class TileEE
  extends TileEntity
{
  public byte direction;
  public String player;
  
  public void a(NBTTagCompound var1)
  {
    super.a(var1);
    this.direction = var1.getByte("direction");
    this.player = var1.getString("player");
  }
  



  public void b(NBTTagCompound var1)
  {
    super.b(var1);
    var1.setByte("direction", this.direction);
    
    if ((this.player != null) && (this.player != ""))
    {
      var1.setString("player", this.player);
    }
  }
  
  public int getKleinLevel(int var1)
  {
    return var1 == EEItem.kleinStar6.id ? 6 : var1 == EEItem.kleinStar5.id ? 5 : var1 == EEItem.kleinStar4.id ? 4 : var1 == EEItem.kleinStar3.id ? 3 : var1 == EEItem.kleinStar2.id ? 2 : var1 == EEItem.kleinStar1.id ? 1 : 0;
  }
  
  public float getWOFTReciprocal(float var1)
  {
    float var2 = 1.0F / var1;
    return var2 * (EEBase.getMachineFactor() / 16.0F);
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
    if ((this.world != null) && (!EEProxy.isClient(this.world)))
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
    


    return false;
  }
  

  private boolean isChest(TileEntity var1)
  {
    return ((var1 instanceof TileEntityChest)) || ((var1 instanceof TileAlchChest));
  }
  
  public void setDefaultDirection()
  {
    if (!this.world.isStatic)
    {
      int var1 = this.world.getTypeId(this.x, this.y, this.z - 1);
      int var2 = this.world.getTypeId(this.x, this.y, this.z + 1);
      int var3 = this.world.getTypeId(this.x - 1, this.y, this.z);
      int var4 = this.world.getTypeId(this.x + 1, this.y, this.z);
      byte var5 = 2;
      
      if ((net.minecraft.server.Block.n[var1] != 0) && (net.minecraft.server.Block.n[var2] == 0))
      {
        var5 = 3;
      }
      
      if ((net.minecraft.server.Block.n[var2] != 0) && (net.minecraft.server.Block.n[var1] == 0))
      {
        var5 = 2;
      }
      
      if ((net.minecraft.server.Block.n[var3] != 0) && (net.minecraft.server.Block.n[var4] == 0))
      {
        var5 = 5;
      }
      
      if ((net.minecraft.server.Block.n[var4] != 0) && (net.minecraft.server.Block.n[var3] == 0))
      {
        var5 = 4;
      }
      
      this.direction = var5;
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
    return 0;
  }
  
  public int getInventoryTexture(int var1)
  {
    return 0;
  }
  
  public int getLightValue()
  {
    return 0;
  }
  
  public boolean onBlockActivated(EntityHuman var1)
  {
    return false;
  }
  
  public void onNeighborBlockChange(int var1) {}
  
  public void onBlockClicked(EntityHuman var1) {}
  
  public boolean clientFail()
  {
    return this.world.getTileEntity(this.x, this.y, this.z) != this ? true : this.world == null ? true : EEProxy.isClient(this.world);
  }
  
  public void onBlockAdded() {}
  
  public void onBlockRemoval()
  {
    for (int var1 = 0; var1 < getSizeInventory(); var1++)
    {
      ItemStack var2 = getStackInSlot(var1);
      
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
  
  private ItemStack getStackInSlot(int var1)
  {
    return null;
  }
  
  private int getSizeInventory()
  {
    return 0;
  }
  
  public void randomDisplayTick(Random var1) {}
  
  public void onBlockDestroyedByExplosion() {}
  
  public void onBlockDestroyedByPlayer() {}
  
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
  
  public void setDirection(byte var1)
  {
    this.direction = var1;
  }
  
  public void setPlayerName(String var1)
  {
    this.player = var1;
  }
  



  public Packet d()
  {
    TileEntityPacket var1 = (TileEntityPacket)PacketHandler.getPacket(PacketTypeHandler.TILE);
    var1.setCoords(this.x, this.y, this.z);
    var1.setOrientation(this.direction);
    var1.setPlayerName(this.player);
    return PacketHandler.getPacketForSending(var1);
  }
}
