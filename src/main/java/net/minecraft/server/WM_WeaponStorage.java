package net.minecraft.server;

import java.util.Map;

public class WM_WeaponStorage
{
  public final boolean isIndependant;
  public final boolean isEntity;
  public final boolean useMaterials;
  public final String weaponName;
  public final int networkID;
  public Item weaponItem;
  public int entityID;
  public boolean isEnabled;
  private int itemID;
  private Map itemIDsWithMaterial;
  public static final String[] materials = { "wood", "stone", "iron", "diamond", "gold" };
  


  public static Map propsMap = new java.util.HashMap();
  private static int registeredWeaponAmount = 0;
  
  private WM_WeaponStorage(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.weaponName = paramString;
    this.isEntity = paramBoolean2;
    this.useMaterials = paramBoolean1;
    this.isIndependant = paramBoolean3;
    
    if (this.isEntity)
    {
      this.networkID = (++registeredWeaponAmount);
    }
    else
    {
      this.networkID = 0;
    }
    
    if (this.useMaterials)
    {
      this.itemIDsWithMaterial = new java.util.HashMap();
    }
  }
  
  public void setItemID(int paramInt)
  {
    if (this.useMaterials)
    {
      throw new IllegalStateException("Weapon uses materials. Use setItemIDs instead.");
    }
    

    this.itemID = paramInt;
  }
  


  public int getItemID()
  {
    if (this.useMaterials)
    {
      throw new IllegalStateException("Weapon uses materials. Use getItemIDs instead.");
    }
    

    return this.itemID;
  }
  

  public void setItemIDs(int[] paramArrayOfInt)
  {
    if (!this.useMaterials)
    {
      throw new IllegalStateException("Weapon does not use materials. Use setItemID instead.");
    }
    
    for (int i = 0; i < materials.length; i++)
    {
      this.itemIDsWithMaterial.put(materials[i], Integer.valueOf(paramArrayOfInt[i]));
    }
  }
  
  public int[] getItemIDs()
  {
    if (!this.useMaterials)
    {
      throw new IllegalStateException("Weapon does not use materials. Use getItemID instead.");
    }
    
    int[] arrayOfInt = new int[materials.length];
    
    for (int i = 0; i < materials.length; i++)
    {
      try
      {
        arrayOfInt[i] = ((Integer)this.itemIDsWithMaterial.get(materials[i])).intValue();
      }
      catch (NullPointerException localNullPointerException)
      {
        arrayOfInt[i] = 0;
      }
    }
    
    return arrayOfInt;
  }
  
  public static void clear()
  {
    WM_WeaponStorage[] arrayOfWM_WeaponStorage1 = (WM_WeaponStorage[])propsMap.values().toArray(new WM_WeaponStorage[0]);
    WM_WeaponStorage[] arrayOfWM_WeaponStorage2 = arrayOfWM_WeaponStorage1;
    int i = arrayOfWM_WeaponStorage2.length;
    
    for (int j = 0; j < i; j++)
    {
      WM_WeaponStorage localWM_WeaponStorage = arrayOfWM_WeaponStorage2[j];
      
      if (localWM_WeaponStorage.useMaterials)
      {
        localWM_WeaponStorage.itemIDsWithMaterial.clear();
      }
    }
    
    propsMap.clear();
  }
  
  public static boolean isEnabled(String paramString)
  {
    return getWeapon(paramString).isEnabled;
  }
  
  public static void addWeapon(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    propsMap.put(paramString, new WM_WeaponStorage(paramString, paramBoolean1, paramBoolean2, paramBoolean3));
  }
  
  public static WM_WeaponStorage getWeapon(String paramString)
  {
    return (WM_WeaponStorage)propsMap.get(paramString);
  }
}
