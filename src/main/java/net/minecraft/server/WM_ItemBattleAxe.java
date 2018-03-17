package net.minecraft.server;

public class WM_ItemBattleAxe extends WM_ItemWeaponMod
{
  public WM_ItemBattleAxe(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
  }
  



  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    return paramBlock.id == Block.LOG.id;
  }
  




  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return canDestroySpecialBlock(paramBlock) ? this.strVsBlock * 2.0F : this.strVsBlock;
  }
}
