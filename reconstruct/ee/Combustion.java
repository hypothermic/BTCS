package ee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.server.Block;
import net.minecraft.server.BlockFire;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.DamageSource;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

public class Combustion
{
  public boolean isFlaming = false;
  private Random ExplosionRNG = new Random();
  private World worldObj;
  private ItemStack[] dropList = new ItemStack[64];
  public double explosionX;
  public double explosionY;
  public double explosionZ;
  public EntityHuman exploder;
  public float explosionSize;
  public Set destroyedBlockPositions = new HashSet();
  
  public Combustion(World var1, EntityHuman var2, double var3, double var5, double var7, float var9)
  {
    this.worldObj = var1;
    this.exploder = var2;
    this.explosionSize = var9;
    this.explosionX = var3;
    this.explosionY = var5;
    this.explosionZ = var7;
  }
  
  public void doExplosionA()
  {
    float var1 = this.explosionSize;
    byte var2 = 16;
    






    int var3;
    for (var3 = 0; var3 < var2; var3++)
    {
      for (int var4 = 0; var4 < var2; var4++)
      {
        for (int var5 = 0; var5 < var2; var5++)
        {
          if ((var3 == 0) || (var3 == var2 - 1) || (var4 == 0) || (var4 == var2 - 1) || (var5 == 0) || (var5 == var2 - 1))
          {
            double var6 = var3 / (var2 - 1.0F) * 2.0F - 1.0F;
            double var8 = var4 / (var2 - 1.0F) * 2.0F - 1.0F;
            double var10 = var5 / (var2 - 1.0F) * 2.0F - 1.0F;
            double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
            var6 /= var12;
            var8 /= var12;
            var10 /= var12;
            float var14 = this.explosionSize * (0.7F + this.worldObj.random.nextFloat() * 0.6F);
            double var15 = this.explosionX;
            double var17 = this.explosionY;
            double var19 = this.explosionZ;
            
            for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
            {
              int var22 = MathHelper.floor(var15);
              int var23 = MathHelper.floor(var17);
              int var24 = MathHelper.floor(var19);
              int var25 = this.worldObj.getTypeId(var22, var23, var24);
              
              if ((var25 > 0) && (Block.byId[var25].a(this.exploder) > 12.0F))
              {
                var14 -= Block.byId[var25].a(this.exploder) * var21;
              }
              
              if (var14 > 0.0F)
              {
                this.destroyedBlockPositions.add(new ChunkPosition(var22, var23, var24));
              }
              
              var15 += var6 * var21;
              var17 += var8 * var21;
              var19 += var10 * var21;
            }
          }
        }
      }
    }
    
    this.explosionSize *= 2.0F;
    var3 = MathHelper.floor(this.explosionX - this.explosionSize - 1.0D);
    int var4 = MathHelper.floor(this.explosionX + this.explosionSize + 1.0D);
    int var5 = MathHelper.floor(this.explosionY - this.explosionSize - 1.0D);
    int var29 = MathHelper.floor(this.explosionY + this.explosionSize + 1.0D);
    int var7 = MathHelper.floor(this.explosionZ - this.explosionSize - 1.0D);
    int var30 = MathHelper.floor(this.explosionZ + this.explosionSize + 1.0D);
    List var9 = this.worldObj.getEntities(this.exploder, net.minecraft.server.AxisAlignedBB.b(var3, var5, var7, var4, var29, var30));
    Vec3D var31 = Vec3D.create(this.explosionX, this.explosionY, this.explosionZ);
    
    for (int var11 = 0; var11 < var9.size(); var11++)
    {
      Entity var32 = (Entity)var9.get(var11);
      double var13 = var32.f(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
      
      if (var13 <= 1.0D)
      {
        double var15 = var32.locX - this.explosionX;
        double var17 = var32.locY - this.explosionY;
        double var19 = var32.locZ - this.explosionZ;
        double var40 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
        var15 /= var40;
        var17 /= var40;
        var19 /= var40;
        double var39 = this.worldObj.a(var31, var32.boundingBox);
        double var41 = (1.0D - var13) * var39;
        var32.damageEntity(DamageSource.EXPLOSION, (int)((var41 * var41 + var41) / 2.0D * 8.0D * this.explosionSize + 1.0D));
        var32.motX += var15 * var41;
        var32.motY += var17 * var41;
        var32.motZ += var19 * var41;
      }
    }
    
    this.explosionSize = var1;
    ArrayList var34 = new ArrayList();
    var34.addAll(this.destroyedBlockPositions);
    
    if (this.isFlaming)
    {
      for (int var33 = var34.size() - 1; var33 >= 0; var33--)
      {
        ChunkPosition var35 = (ChunkPosition)var34.get(var33);
        int var36 = var35.x;
        int var37 = var35.y;
        int var16 = var35.z;
        int var38 = this.worldObj.getTypeId(var36, var37, var16);
        int var18 = this.worldObj.getTypeId(var36, var37 - 1, var16);
        
        if ((var38 == 0) && (Block.n[var18]) && (this.ExplosionRNG.nextInt(3) == 0)) // BTCS: rm ' != 0'
        {
          this.worldObj.setTypeId(var36, var37, var16, Block.FIRE.id);
        }
      }
    }
  }
  
  public void doExplosionB(boolean var1)
  {
    for (int var2 = 0; var2 <= 63; var2++)
    {
      this.dropList[var2] = null;
    }
    
    this.worldObj.makeSound(this.explosionX, this.explosionY, this.explosionZ, "kinesis", 4.0F, (1.0F + (this.worldObj.random.nextFloat() - this.worldObj.random.nextFloat()) * 0.2F) * 0.7F);
    this.worldObj.a("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 0.0D, 0.0D, 0.0D);
    ArrayList var25 = new ArrayList();
    var25.addAll(this.destroyedBlockPositions);
    
    for (int var3 = var25.size() - 1; var3 >= 0; var3--)
    {
      ChunkPosition var4 = (ChunkPosition)var25.get(var3);
      int var5 = var4.x;
      int var6 = var4.y;
      int var7 = var4.z;
      int var8 = this.worldObj.getTypeId(var5, var6, var7);
      
      if (var1)
      {
        double var9 = var5 + this.worldObj.random.nextFloat();
        double var11 = var6 + this.worldObj.random.nextFloat();
        double var13 = var7 + this.worldObj.random.nextFloat();
        double var15 = var9 - this.explosionX;
        double var17 = var11 - this.explosionY;
        double var19 = var13 - this.explosionZ;
        double var21 = MathHelper.sqrt(var15 * var15 + var17 * var17 + var19 * var19);
        var15 /= var21;
        var17 /= var21;
        var19 /= var21;
        double var23 = 0.5D / (var21 / this.explosionSize + 0.1D);
        var23 *= (this.worldObj.random.nextFloat() * this.worldObj.random.nextFloat() + 0.3F);
        var15 *= var23;
        var17 *= var23;
        var19 *= var23;
        
        if (this.worldObj.random.nextInt(8) == 0)
        {
          this.worldObj.a("explode", (var9 + this.explosionX * 1.0D) / 2.0D, (var11 + this.explosionY * 1.0D) / 2.0D, (var13 + this.explosionZ * 1.0D) / 2.0D, var15, var17, var19);
        }
        
        if (this.worldObj.random.nextInt(8) == 0)
        {
          this.worldObj.a("smoke", var9, var11, var13, var15, var17, var19);
        }
      }
      
      if (var8 > 0)
      {
        int var27 = this.worldObj.getData(var5, var6, var7);
        ArrayList var10 = Block.byId[var8].getBlockDropped(this.worldObj, var5, var6, var7, var27, 0);
        Iterator var28 = var10.iterator();
        
        while (var28.hasNext())
        {
          ItemStack var12 = (ItemStack)var28.next();
          
          for (int var29 = 0; var29 < this.dropList.length; var29++)
          {
            if (this.dropList[var29] == null)
            {
              this.dropList[var29] = var12.cloneItemStack();
              var12 = null;
            }
            else if ((this.dropList[var29].doMaterialsMatch(var12)) && (this.dropList[var29].count < this.dropList[var29].getMaxStackSize()))
            {
              while ((this.dropList[var29].count < this.dropList[var29].getMaxStackSize()) && (var12 != null))
              {
                this.dropList[var29].count += 1;
                var12.count -= 1;
                
                if (var12.count == 0)
                {
                  var12 = null;
                }
              }
            }
            
            if (var12 == null) {
              break;
            }
          }
        }
        

        this.worldObj.setTypeId(var5, var6, var7, 0);
        Block.byId[var8].wasExploded(this.worldObj, var5, var6, var7);
      }
    }
    


    if (this.exploder != null)
    {
      if (this.dropList != null)
      {
        EntityLootBall var26 = new EntityLootBall(this.worldObj, EEBase.playerX(this.exploder), EEBase.playerY(this.exploder), EEBase.playerZ(this.exploder), this.dropList);
        
        if (var26 != null)
        {
          this.worldObj.addEntity(var26);
        }
      }
    }
    else if (this.dropList != null)
    {
      EntityLootBall var26 = new EntityLootBall(this.worldObj, this.explosionX, this.explosionY, this.explosionZ, this.dropList);
      
      if (var26 != null)
      {
        this.worldObj.addEntity(var26);
      }
    }
  }
}
