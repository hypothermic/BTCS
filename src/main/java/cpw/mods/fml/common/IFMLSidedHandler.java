package cpw.mods.fml.common;

import cpw.mods.fml.common.modloader.BaseMod;
import cpw.mods.fml.common.modloader.ModProperty;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public abstract interface IFMLSidedHandler
{
  public abstract Logger getMinecraftLogger();
  
  public abstract File getMinecraftRootDirectory();
  
  public abstract boolean isModLoaderMod(Class<?> paramClass);
  
  public abstract ModContainer loadBaseModMod(Class<?> paramClass, File paramFile);
  
  public abstract Object getMinecraftInstance();
  
  public abstract String getCurrentLanguage();
  
  public abstract Properties getCurrentLanguageTable();
  
  public abstract String getObjectName(Object paramObject);
  
  public abstract ModMetadata readMetadataFrom(InputStream paramInputStream, ModContainer paramModContainer)
    throws Exception;
  
  public abstract void profileStart(String paramString);
  
  public abstract void profileEnd();
  
  public abstract ModProperty getModLoaderPropertyFor(Field paramField);
  
  public abstract List<String> getAdditionalBrandingInformation();
  
  public abstract Side getSide();
  
  public abstract ProxyInjector findSidedProxyOn(BaseMod paramBaseMod);
}
