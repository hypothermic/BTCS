package ee;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.MathHelper;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class EntityCataclysmPrimed extends Entity
{
  private EntityHuman player;
  public int fuse;
  
  public EntityCataclysmPrimed(World var1)
  {
    super(var1);
    this.fuse = 160;
    this.bf = true;
    b(0.98F, 0.98F);
    this.height = (this.length / 2.0F);
  }
  
  public EntityCataclysmPrimed(World var1, EntityHuman var2, double var3, double var5, double var7)
  {
    this(var1);
    this.player = var2;
    setPosition(var3, var5, var7);
    float var9 = (float)(Math.random() * 3.141592653589793D * 2.0D);
    this.motX = (-MathHelper.sin(var9 * 3.1415927F / 180.0F) * 0.02F);
    this.motY = 0.20000000298023224D;
    this.motZ = (-MathHelper.cos(var9 * 3.1415927F / 180.0F) * 0.02F);
    this.fuse = 160;
    this.lastX = var3;
    this.lastY = var5;
    this.lastZ = var7;
  }
  
  public EntityCataclysmPrimed(World var1, double var2, double var4, double var6)
  {
    this(var1);
    setPosition(var2, var4, var6);
  }
  


  protected void b() {}
  

  public boolean o_()
  {
    return !this.dead;
  }
  



  public void F_()
  {
    this.lastX = this.locX;
    this.lastY = this.locY;
    this.lastZ = this.locZ;
    this.motY -= 0.03999999910593033D;
    move(this.motX, this.motY, this.motZ);
    this.motX *= 0.9800000190734863D;
    this.motY *= 0.9800000190734863D;
    this.motZ *= 0.9800000190734863D;
    
    if (this.onGround)
    {
      this.motX *= 0.699999988079071D;
      this.motZ *= 0.699999988079071D;
      this.motY *= -0.5D;
    }
    
    if (this.fuse-- <= 0)
    {
      die();
      explode();
    }
    else
    {
      this.world.a("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
    }
  }
  
  private void explode()
  {
    float var1 = 16.0F;
    newExplosionNova(null, this.locX, this.locY, this.locZ, var1, false);
  }
  
  public ExplosionNova newExplosionNova(Entity var1, double var2, double var4, double var6, float var8, boolean var9)
  {
    ExplosionNova var10 = new ExplosionNova(this.world, this.player, var2, var4, var6, var8);
    var10.isFlaming = var9;
    var10.doExplosionA();
    var10.doExplosionB();
    return var10;
  }
  



  protected void b(NBTTagCompound var1)
  {
    var1.setByte("Fuse", (byte)this.fuse);
  }
  



  protected void a(NBTTagCompound var1)
  {
    this.fuse = var1.getByte("Fuse");
  }
  
  public float getShadowSize()
  {
    return 0.0F;
  }
}
