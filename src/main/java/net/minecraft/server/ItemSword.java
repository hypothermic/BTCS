package net.minecraft.server;


public class ItemSword
  extends Item
{
  private int damage;
  
  private final EnumToolMaterial b;
  

  public ItemSword(int paramInt, EnumToolMaterial paramEnumToolMaterial)
  {
    super(paramInt);
    this.b = paramEnumToolMaterial;
    this.maxStackSize = 1;
    setMaxDurability(paramEnumToolMaterial.a());
    
    this.damage = (4 + paramEnumToolMaterial.c());
  }
  
  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    if (paramBlock.id == Block.WEB.id)
    {
      return 15.0F;
    }
    return 1.5F;
  }
  
  public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
    paramItemStack.damage(1, paramEntityLiving2);
    return true;
  }
  
  public boolean a(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityLiving paramEntityLiving) {
    paramItemStack.damage(2, paramEntityLiving);
    return true;
  }
  
  public int a(Entity paramEntity) {
    return this.damage;
  }
  



  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.d;
  }
  
  public int c(ItemStack paramItemStack) {
    return 72000;
  }
  
  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
    paramEntityHuman.a(paramItemStack, c(paramItemStack));
    return paramItemStack;
  }
  
  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    return paramBlock.id == Block.WEB.id;
  }
  
  public int c()
  {
    return this.b.e();
  }
}
