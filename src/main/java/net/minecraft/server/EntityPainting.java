package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Painting;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingBreakEvent.RemoveCause;
import org.bukkit.plugin.PluginManager;

public class EntityPainting extends Entity
{
  private int f;
  public int direction;
  public int x;
  public int y;
  public int z;
  public EnumArt art;
  
  public EntityPainting(World world)
  {
    super(world);
    this.f = 0;
    this.direction = 0;
    this.height = 0.0F;
    b(0.5F, 0.5F);
    this.art = EnumArt.values()[this.random.nextInt(EnumArt.values().length)];
  }
  
  public EntityPainting(World world, int i, int j, int k, int l) {
    this(world);
    this.x = i;
    this.y = j;
    this.z = k;
    ArrayList arraylist = new ArrayList();
    EnumArt[] aenumart = EnumArt.values();
    int i1 = aenumart.length;
    
    for (int j1 = 0; j1 < i1; j1++) {
      EnumArt enumart = aenumart[j1];
      
      this.art = enumart;
      setDirection(l);
      if (survives()) {
        arraylist.add(enumart);
      }
    }
    
    if (arraylist.size() > 0) {
      this.art = ((EnumArt)arraylist.get(this.random.nextInt(arraylist.size())));
    }
    
    setDirection(l);
  }
  
  protected void b() {}
  
  public void setDirection(int i) {
    this.direction = i;
    this.lastYaw = (this.yaw = i * 90);
    float f = this.art.B;
    float f1 = this.art.C;
    float f2 = this.art.B;
    
    if ((i != 0) && (i != 2)) {
      f = 0.5F;
    } else {
      f2 = 0.5F;
    }
    
    f /= 32.0F;
    f1 /= 32.0F;
    f2 /= 32.0F;
    float f3 = this.x + 0.5F;
    float f4 = this.y + 0.5F;
    float f5 = this.z + 0.5F;
    float f6 = 0.5625F;
    
    if (i == 0) {
      f5 -= f6;
    }
    
    if (i == 1) {
      f3 -= f6;
    }
    
    if (i == 2) {
      f5 += f6;
    }
    
    if (i == 3) {
      f3 += f6;
    }
    
    if (i == 0) {
      f3 -= c(this.art.B);
    }
    
    if (i == 1) {
      f5 += c(this.art.B);
    }
    
    if (i == 2) {
      f3 += c(this.art.B);
    }
    
    if (i == 3) {
      f5 -= c(this.art.B);
    }
    
    f4 += c(this.art.C);
    setPosition(f3, f4, f5);
    float f7 = -0.00625F;
    
    this.boundingBox.c(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
  }
  
  private float c(int i) {
    return i == 64 ? 0.5F : i == 32 ? 0.5F : 0.0F;
  }
  
  public void F_() {
    if ((this.f++ == 100) && (!this.world.isStatic)) {
      this.f = 0;
      if ((!this.dead) && (!survives()))
      {
        Material material = this.world.getMaterial((int)this.locX, (int)this.locY, (int)this.locZ);
        PaintingBreakEvent.RemoveCause cause;
        if (material.equals(Material.WATER)) {
          cause = PaintingBreakEvent.RemoveCause.WATER; } else {
          if (!material.equals(Material.AIR))
          {
            cause = PaintingBreakEvent.RemoveCause.OBSTRUCTION;
          } else
            cause = PaintingBreakEvent.RemoveCause.PHYSICS;
        }
        PaintingBreakEvent event = new PaintingBreakEvent((Painting)getBukkitEntity(), cause);
        this.world.getServer().getPluginManager().callEvent(event);
        
        if ((event.isCancelled()) || (this.dead)) {
          return;
        }
        

        die();
        this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
      }
    }
  }
  
  public boolean survives() {
    if (this.world.getCubes(this, this.boundingBox).size() > 0) {
      return false;
    }
    int i = this.art.B / 16;
    int j = this.art.C / 16;
    int k = this.x;
    int l = this.y;
    int i1 = this.z;
    
    if (this.direction == 0) {
      k = MathHelper.floor(this.locX - this.art.B / 32.0F);
    }
    
    if (this.direction == 1) {
      i1 = MathHelper.floor(this.locZ - this.art.B / 32.0F);
    }
    
    if (this.direction == 2) {
      k = MathHelper.floor(this.locX - this.art.B / 32.0F);
    }
    
    if (this.direction == 3) {
      i1 = MathHelper.floor(this.locZ - this.art.B / 32.0F);
    }
    
    l = MathHelper.floor(this.locY - this.art.C / 32.0F);
    


    for (int k1 = 0; k1 < i; k1++) {
      for (int j1 = 0; j1 < j; j1++) {
        Material material;
        if ((this.direction != 0) && (this.direction != 2)) {
          material = this.world.getMaterial(this.x, l + j1, i1 + k1);
        } else {
          material = this.world.getMaterial(k + k1, l + j1, this.z);
        }
        
        if (!material.isBuildable()) {
          return false;
        }
      }
    }
    
    List list = this.world.getEntities(this, this.boundingBox);
    
    for (int j1 = 0; j1 < list.size(); j1++) {
      if ((list.get(j1) instanceof EntityPainting)) {
        return false;
      }
    }
    
    return true;
  }
  
  public boolean o_()
  {
    return true;
  }
  
  public boolean damageEntity(DamageSource damagesource, int i) {
    if ((!this.dead) && (!this.world.isStatic))
    {
      PaintingBreakEvent event = null;
      if (damagesource.getEntity() != null) {
        event = new org.bukkit.event.painting.PaintingBreakByEntityEvent((Painting)getBukkitEntity(), damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity());
      }
      else if (damagesource == DamageSource.FIRE) {
        event = new PaintingBreakEvent((Painting)getBukkitEntity(), PaintingBreakEvent.RemoveCause.FIRE);
      }
      

      if (event != null) {
        this.world.getServer().getPluginManager().callEvent(event);
        
        if (event.isCancelled()) {
          return true;
        }
      }
      
      if (this.dead) { return true;
      }
      
      die();
      aW();
      this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
    }
    
    return true;
  }
  
  public void b(NBTTagCompound nbttagcompound) {
    nbttagcompound.setByte("Dir", (byte)this.direction);
    nbttagcompound.setString("Motive", this.art.A);
    nbttagcompound.setInt("TileX", this.x);
    nbttagcompound.setInt("TileY", this.y);
    nbttagcompound.setInt("TileZ", this.z);
  }
  
  public void a(NBTTagCompound nbttagcompound) {
    this.direction = nbttagcompound.getByte("Dir");
    this.x = nbttagcompound.getInt("TileX");
    this.y = nbttagcompound.getInt("TileY");
    this.z = nbttagcompound.getInt("TileZ");
    String s = nbttagcompound.getString("Motive");
    EnumArt[] aenumart = EnumArt.values();
    int i = aenumart.length;
    
    for (int j = 0; j < i; j++) {
      EnumArt enumart = aenumart[j];
      
      if (enumart.A.equals(s)) {
        this.art = enumart;
      }
    }
    
    if (this.art == null) {
      this.art = EnumArt.KEBAB;
    }
    
    setDirection(this.direction);
  }
  
  public void move(double d0, double d1, double d2) {
    if ((!this.world.isStatic) && (!this.dead) && (d0 * d0 + d1 * d1 + d2 * d2 > 0.0D)) {
      if (this.dead) { return;
      }
      die();
      this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Item.PAINTING)));
    }
  }
  
  public void b_(double d0, double d1, double d2) {}
}
