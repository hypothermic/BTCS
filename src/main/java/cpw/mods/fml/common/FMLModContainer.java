package cpw.mods.fml.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FMLModContainer
  implements ModContainer
{
  private Mod modDescriptor;
  private Object modInstance;
  private File source;
  private ModMetadata modMetadata;
  
  public FMLModContainer(String dummy)
  {
    this(new File(dummy));
  }
  
  public FMLModContainer(File source) {
    this.source = source;
  }
  
  public FMLModContainer(Class<?> clazz)
  {
    if (clazz == null)
    {
      return;
    }
    
    this.modDescriptor = ((Mod)clazz.getAnnotation(Mod.class));
    
    try
    {
      this.modInstance = clazz.newInstance();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  

  public boolean wantsPreInit()
  {
    return this.modDescriptor.wantsPreInit();
  }
  

  public boolean wantsPostInit()
  {
    return this.modDescriptor.wantsPostInit();
  }
  



  public void preInit() {}
  



  public void init() {}
  



  public void postInit() {}
  


  public static ModContainer buildFor(Class<?> clazz)
  {
    return new FMLModContainer(clazz);
  }
  


  public String getName()
  {
    return null;
  }
  


  public ModContainer.ModState getModState()
  {
    return null;
  }
  



  public void nextState() {}
  



  public String getSortingRules()
  {
    return null;
  }
  


  public boolean matches(Object mod)
  {
    return false;
  }
  

  public File getSource()
  {
    return this.source;
  }
  


  public Object getMod()
  {
    return null;
  }
  


  public int lookupFuelValue(int itemId, int itemDamage)
  {
    return 0;
  }
  


  public boolean wantsPickupNotification()
  {
    return false;
  }
  


  public IPickupNotifier getPickupNotifier()
  {
    return null;
  }
  





  public boolean wantsToDispense()
  {
    return false;
  }
  





  public IDispenseHandler getDispenseHandler()
  {
    return null;
  }
  





  public boolean wantsCraftingNotification()
  {
    return false;
  }
  





  public ICraftingHandler getCraftingHandler()
  {
    return null;
  }
  





  public List<String> getDependencies()
  {
    return new ArrayList(0);
  }
  





  public List<String> getPreDepends()
  {
    return new ArrayList(0);
  }
  





  public List<String> getPostDepends()
  {
    return new ArrayList(0);
  }
  




  public String toString()
  {
    return getSource().getName();
  }
  





  public boolean wantsNetworkPackets()
  {
    return false;
  }
  





  public INetworkHandler getNetworkHandler()
  {
    return null;
  }
  





  public boolean ownsNetworkChannel(String channel)
  {
    return false;
  }
  





  public boolean wantsConsoleCommands()
  {
    return false;
  }
  





  public IConsoleHandler getConsoleHandler()
  {
    return null;
  }
  





  public boolean wantsPlayerTracking()
  {
    return false;
  }
  





  public IPlayerTracker getPlayerTracker()
  {
    return null;
  }
  




  public List<IKeyHandler> getKeys()
  {
    return null;
  }
  




  public ModContainer.SourceType getSourceType()
  {
    return null;
  }
  





  public void setSourceType(ModContainer.SourceType type) {}
  





  public ModMetadata getMetadata()
  {
    return this.modMetadata;
  }
  



  public void setMetadata(ModMetadata meta)
  {
    this.modMetadata = meta;
  }
  






  public void gatherRenderers(Map renderers) {}
  






  public void requestAnimations() {}
  






  public String getVersion()
  {
    return null;
  }
  




  public ProxyInjector findSidedProxy()
  {
    return null;
  }
  
  public void keyBindEvent(Object keyBinding) {}
}
