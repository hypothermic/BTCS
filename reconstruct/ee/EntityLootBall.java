package ee;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.DamageSource;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.World;

public class EntityLootBall extends Entity
{
  public ItemStack[] items;
  private int field_803_e;
  public int age = 0;
  public int delayBeforeCanPickup;
  private int health = 5;
  public float field_804_d = (float)(Math.random() * 3.141592653589793D * 2.0D);
  private boolean beingPulled;
  
  public EntityLootBall(World var1, double var2, double var4, double var6, ItemStack[] var8)
  {
    super(var1);
    b(0.25F, 0.25F);
    this.height = (this.length / 2.0F);
    setPosition(var2, var4, var6);
    this.items = var8;
    this.yaw = ((float)(Math.random() * 360.0D));
    this.motX = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
    this.motY = 0.2000000029802322D;
    this.motZ = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
  }

  protected boolean g_()
  {
    return false;
  }
  
  public EntityLootBall(World var1, double var2, double var4, double var6)
  {
    super(var1);
    b(0.25F, 0.25F);
    this.height = (this.length / 2.0F);
    setPosition(var2, var4, var6);
  }
  
  public EntityLootBall(World var1)
  {
    super(var1);
    b(0.25F, 0.25F);
    this.height = (this.length / 2.0F);
  }

  protected void b() {}

  public void F_()
  {
    super.F_();
    
    if (this.delayBeforeCanPickup > 0)
    {
      this.delayBeforeCanPickup -= 1;
    }
    
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.motY -= 0.03999999910593033D;
    g(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
    move(this.motX, this.motY, this.motZ);
    float var1 = 0.98F;
    
    if (this.onGround)
    {
      var1 = 0.5880001F;
      int var2 = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
      
      if (var2 > 0)
      {
        var1 = net.minecraft.server.Block.byId[var2].frictionFactor * 0.98F;
      }
    }
    
    this.motX *= var1;
    this.motY *= 0.9800000190734863D;
    this.motZ *= var1;
    
    if (this.onGround)
    {
      this.motY *= -0.5D;
    }
    
    this.field_803_e += 1;
    this.age += 1;
    
    if (this.age >= 12000)
    {
      die();
    }
  }
  



  public boolean h_()
  {
    return this.world.a(this.boundingBox, net.minecraft.server.Material.WATER, this);
  }
  




  protected void burn(int var1) {}
  



  public boolean damageEntity(DamageSource var1, int var2)
  {
    return false;
  }
  



  public void b(NBTTagCompound var1)
  {
    var1.setShort("Health", (short)(byte)this.health);
    var1.setShort("Age", (short)this.age);
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
  



  public void a(NBTTagCompound var1)
  {
    this.health = (var1.getShort("Health") & 0xFF);
    this.age = var1.getShort("Age");
    NBTTagList var2 = var1.getList("Items");
    this.items = new ItemStack[var2.size()];
    
    for (int var3 = 0; var3 < var2.size(); var3++)
    {
      NBTTagCompound var4 = (NBTTagCompound)var2.get(var3);
      byte var5 = var4.getByte("Slot");
      
      if ((var5 >= 0) && (var5 < this.items.length))
      {
        this.items[var5] = ItemStack.a(var4);
      }
    }
  }
  



  public void a_(EntityHuman var1)
  {
    if (!this.world.isStatic)
    {
      if ((this.delayBeforeCanPickup == 0) && (!isBeingPulled()))
      {
        for (int var2 = 0; var2 < this.items.length; var2++)
        {
          if ((this.items[var2] != null) && (roomFor(this.items[var2], var1)))
          {
            this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            pushStack(this.items[var2], var1);
            this.items[var2] = null;
          }
        }
        
        if (isEmpty())
        {
          die();
        }
      }
    }
  }
  
  public boolean roomFor(ItemStack var1, EntityHuman var2)
  {
    if (var1 == null)
    {
      return false;
    }
    

    for (int var3 = 0; var3 < var2.inventory.items.length; var3++)
    {
      if (var2.inventory.items[var3] == null)
      {
        return true;
      }
      
      if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
      {
        return true;
      }
    }
    
    return false;
  }
  

  public boolean isBeingPulled()
  {
    return this.beingPulled;
  }
  
  public boolean setBeingPulled(boolean var1)
  {
    return this.beingPulled = var1;
  }
  
  public void pushStack(ItemStack var1, EntityHuman var2)
  {
    if (var1 != null)
    {

      int var3;
      for (var3 = 0; var3 < var2.inventory.items.length; var3++)
      {
        if (var2.inventory.items[var3] != null)
        {
          if ((var2.inventory.items[var3].doMaterialsMatch(var1)) && (var2.inventory.items[var3].count <= var1.getMaxStackSize() - var1.count))
          {
            var2.inventory.items[var3].count += var1.count;
            var1 = null;
            return;
          }
          
          if (var2.inventory.items[var3].doMaterialsMatch(var1))
          {
            while ((var2.inventory.items[var3].count < var2.inventory.items[var3].getMaxStackSize()) && (var1 != null))
            {
              var2.inventory.items[var3].count += 1;
              var1.count -= 1;
              
              if (var1.count <= 0)
              {
                var1 = null;
                return;
              }
            }
          }
        }
      }
      
      if (var1 != null)
      {
        for (var3 = 0; var3 < var2.inventory.items.length; var3++)
        {
          if (var2.inventory.items[var3] == null)
          {
            var2.inventory.items[var3] = var1.cloneItemStack();
            var1 = null;
            return;
          }
        }
      }
    }
  }
  
  public boolean isEmpty()
  {
    boolean var1 = true;
    
    for (int var2 = 0; var2 < this.items.length; var2++)
    {
      if (this.items[var2] != null)
      {
        var1 = false;
      }
    }
    
    return var1;
  }
}
