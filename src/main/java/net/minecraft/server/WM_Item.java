package net.minecraft.server;

import forge.ITextureProvider;

public class WM_Item extends Item implements ITextureProvider
{
  protected WM_Item(int paramInt)
  {
    super(paramInt);
  }
  
  public String getTextureFile()
  {
    return "/gui/weaponmod/weapons.png";
  }
}
