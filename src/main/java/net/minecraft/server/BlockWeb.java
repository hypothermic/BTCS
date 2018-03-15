package net.minecraft.server;

import java.util.Random;





public class BlockWeb
  extends Block
{
  public BlockWeb(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2, Material.WEB);
  }
  
  public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity)
  {
    paramEntity.u();
  }
  
  public boolean a()
  {
    return false;
  }
  
  public AxisAlignedBB e(World paramWorld, int paramInt1, int paramInt2, int paramInt3)
  {
    return null;
  }
  
  public int c()
  {
    return 1;
  }
  



  public boolean b()
  {
    return false;
  }
  

  public int getDropType(int paramInt1, Random paramRandom, int paramInt2)
  {
    return Item.STRING.id;
  }
}
