package net.minecraft.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

public class WM_Properties
{
  public boolean cannonDoesBlockDamage;
  public boolean dynamiteDoesBlockDamage;
  public boolean canThrowKnife;
  public boolean canThrowSpear;
  private mod_WeaponMod baseMod;
  
  public WM_Properties(mod_WeaponMod parammod_WeaponMod)
  {
    this.baseMod = parammod_WeaponMod;
  }
  
  public void loadAllProperties()
  {
    Properties localProperties = new Properties();
    
    if (loadFromFile(localProperties))
    {
      readProperties(localProperties);
      processProperties(localProperties);
    }
    else
    {
      setStandardSettings();
      createFile(localProperties);
      processProperties(localProperties);
    }
  }
  
  private boolean loadFromFile(Properties paramProperties)
  {
    try
    {
      String str = mod_WeaponMod.getServerLocation().getCanonicalPath() + "/mods/weaponmod/weaponmod.properties";
      java.io.FileInputStream localFileInputStream = new java.io.FileInputStream(str);
      paramProperties.load(localFileInputStream);
      localFileInputStream.close();
    }
    catch (IOException localIOException)
    {
      MinecraftServer.log.warning("[WeaponMod] Unable to read from properties.");
      localIOException.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  private void readProperties(Properties paramProperties)
  {
    int i = 0;
    Iterator localIterator = WM_WeaponStorage.propsMap.values().iterator();
    


    while (localIterator.hasNext())
    {



      Object localObject = (WM_WeaponStorage)localIterator.next();
      String str1 = paramProperties.getProperty(((WM_WeaponStorage)localObject).weaponName);
      
      if ((str1 == null) || (str1.length() == 0))
      {
        if (((WM_WeaponStorage)localObject).isIndependant)
        {
          i++;
        }
        
        ((WM_WeaponStorage)localObject).isEnabled = true;
      }
      else
      {
        ((WM_WeaponStorage)localObject).isEnabled = Boolean.parseBoolean(str1);
      }
      
      if ((((WM_WeaponStorage)localObject).isEnabled) || (!((WM_WeaponStorage)localObject).isIndependant))
      {
        if (((WM_WeaponStorage)localObject).useMaterials)
        {
          int[] arrayOfInt = new int[WM_WeaponStorage.materials.length];
          
          for (int j = 0; j < arrayOfInt.length; j++)
          {
            String str2 = WM_WeaponStorage.materials[j] + "-" + ((WM_WeaponStorage)localObject).weaponName + "-id";
            
            try
            {
              arrayOfInt[j] = Integer.parseInt(paramProperties.getProperty(str2));
            }
            catch (NumberFormatException localNumberFormatException3)
            {
              i++;
            }
          }
          
          ((WM_WeaponStorage)localObject).setItemIDs(arrayOfInt);
        }
        else
        {
          try
          {
            ((WM_WeaponStorage)localObject).setItemID(Integer.parseInt(paramProperties.getProperty(((WM_WeaponStorage)localObject).weaponName + "-id")));
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            i++;
          }
        }
        
        if (((WM_WeaponStorage)localObject).isEntity)
        {
          try
          {
            ((WM_WeaponStorage)localObject).entityID = Integer.parseInt(paramProperties.getProperty(((WM_WeaponStorage)localObject).weaponName + "-entity"));
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            i++;
          }
        }
      }
    }
    

    Object localObject = paramProperties.getProperty("cannon-block-damage");
    
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      i++;
    }
    else
    {
      this.cannonDoesBlockDamage = Boolean.parseBoolean((String)localObject);
    }
    
    localObject = paramProperties.getProperty("dynamite-block-damage");
    
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      i++;
    }
    else
    {
      this.dynamiteDoesBlockDamage = Boolean.parseBoolean((String)localObject);
    }
    
    localObject = paramProperties.getProperty("can-throw-knife");
    
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      i++;
    }
    else
    {
      this.canThrowKnife = Boolean.parseBoolean((String)localObject);
    }
    
    localObject = paramProperties.getProperty("can-throw-spear");
    
    if ((localObject == null) || (((String)localObject).length() == 0))
    {
      i++;
    }
    else
    {
      this.canThrowSpear = Boolean.parseBoolean((String)localObject);
    }
    
    if (i == 0)
    {
      MinecraftServer.log.info("[WeaponMod] Properties file read succesfully!");
    }
    else
    {
      MinecraftServer.log.warning("[WeaponMod] Properties file read with " + i + " warnings.");
    }
  }
  
  private void processProperties(Properties paramProperties)
  {
    Iterator localIterator = WM_WeaponStorage.propsMap.values().iterator();
    


    while (localIterator.hasNext())
    {



      WM_WeaponStorage localWM_WeaponStorage = (WM_WeaponStorage)localIterator.next();
      
      if (localWM_WeaponStorage.useMaterials)
      {
        int[] arrayOfInt = localWM_WeaponStorage.getItemIDs();
        
        for (int j = 0; j < arrayOfInt.length; j++)
        {
          if (arrayOfInt[j] == 0)
          {
            arrayOfInt[j] = ModLoader.getUniqueEntityId();
          }
          else
          {
            arrayOfInt[j] -= Block.byId.length;
            
            if (arrayOfInt[j] < 0)
            {
              MinecraftServer.log.warning("[WeaponMod] Item ID is below 4096.");
            }
          }
        }
        localWM_WeaponStorage.setItemIDs(arrayOfInt);
      }
      else
      {
        int i = localWM_WeaponStorage.getItemID();
        
        if (i == 0)
        {
          i = ModLoader.getUniqueEntityId();
        }
        else
        {
          i -= Block.byId.length;
          
          if (i < 0)
          {
            MinecraftServer.log.warning("[WeaponMod] Item ID is below 4096.");
          }
        }
        
        localWM_WeaponStorage.setItemID(i);
      }
      
      if ((localWM_WeaponStorage.isEntity) && (localWM_WeaponStorage.entityID == 0))
      {
        localWM_WeaponStorage.entityID = ModLoader.getUniqueEntityId();
      }
    }
  }
  

  private void setStandardSettings()
  {
    MinecraftServer.log.warning("[WeaponMod] Using standard settings.");
    Iterator localIterator = WM_WeaponStorage.propsMap.values().iterator();
    


    while (localIterator.hasNext())
    {



      WM_WeaponStorage localWM_WeaponStorage = (WM_WeaponStorage)localIterator.next();
      localWM_WeaponStorage.isEnabled = true;
      
      if (localWM_WeaponStorage.useMaterials)
      {
        localWM_WeaponStorage.setItemIDs(new int[] { 0, 0, 0, 0, 0 });


      }
      else
      {

        localWM_WeaponStorage.setItemID(0);
      }
      
      if (localWM_WeaponStorage.isEntity)
      {
        localWM_WeaponStorage.entityID = 0;
      }
    }
    

    this.cannonDoesBlockDamage = true;
    this.dynamiteDoesBlockDamage = true;
    this.canThrowKnife = true;
    this.canThrowSpear = true;
  }
  
  private void createFile(Properties paramProperties)
  {
    MinecraftServer.log.info("[WeaponMod] Creating new properties file.\n");
    
    try
    {
      String str1 = mod_WeaponMod.getServerLocation().getCanonicalPath() + "/mods/weaponmod/";
      String str2 = mod_WeaponMod.getServerLocation().getCanonicalPath() + "/mods/weaponmod/weaponmod.properties";
      new java.io.File(str1).mkdirs();
      java.io.FileOutputStream localFileOutputStream = new java.io.FileOutputStream(str2);
      Iterator localIterator = WM_WeaponStorage.propsMap.values().iterator();
      


      while (localIterator.hasNext())
      {



        WM_WeaponStorage localWM_WeaponStorage = (WM_WeaponStorage)localIterator.next();
        
        if (localWM_WeaponStorage.isIndependant)
        {
          paramProperties.setProperty(localWM_WeaponStorage.weaponName, String.valueOf(localWM_WeaponStorage.isEnabled));
        }
        
        if (localWM_WeaponStorage.useMaterials)
        {
          int[] arrayOfInt = localWM_WeaponStorage.getItemIDs();
          
          for (int i = 0; i < WM_WeaponStorage.materials.length; i++)
          {
            paramProperties.setProperty(WM_WeaponStorage.materials[i] + "-" + localWM_WeaponStorage.weaponName + "-id", String.valueOf(arrayOfInt[i]));
          }
        }
        else
        {
          paramProperties.setProperty(localWM_WeaponStorage.weaponName + "-id", String.valueOf(localWM_WeaponStorage.getItemID()));
        }
        
        if (localWM_WeaponStorage.isEntity)
        {
          paramProperties.setProperty(localWM_WeaponStorage.weaponName + "-entity", String.valueOf(localWM_WeaponStorage.entityID));
        }
      }
      

      paramProperties.setProperty("cannon-block-damage", String.valueOf(this.cannonDoesBlockDamage));
      paramProperties.setProperty("dynamite-block-damage", String.valueOf(this.dynamiteDoesBlockDamage));
      paramProperties.setProperty("can-throw-knife", String.valueOf(this.canThrowKnife));
      paramProperties.setProperty("can-throw-spear", String.valueOf(this.canThrowSpear));
      paramProperties.store(localFileOutputStream, this.baseMod.getName() + " " + this.baseMod.getVersion() + " - Auto-generated properties file");
      localFileOutputStream.close();
      MinecraftServer.log.info("[WeaponMod] New properties file created at " + str2 + "\n");
    }
    catch (IOException localIOException)
    {
      MinecraftServer.log.warning("[WeaponMod] Unable to create new properties file. Move the properties file included in the download to the .minecraft/mods/weaponmod/ folder.\n");
      localIOException.printStackTrace();
    }
  }
}
